package com.tianshang.common.base;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.tianshang.common.BuildConfig;
import com.tianshang.common.R;
import com.tianshang.common.utils.AbsolutePathUtil;
import com.tianshang.common.utils.Helper;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.UUID;


/**
 * 照相Activity基类
 */
public abstract class BaseCaptureActivity extends BaseActivity {
    private String TAG = BaseCaptureActivity.class.getSimpleName();
    //用户选取的原始图片
    protected File CapturePhotoFile;
    //用户选取的裁剪后的图片
    protected File CropPhotoFile;
    /* 用来标识请求照相功能的activity */
    protected static final int CAMERA_WITH_DATA = 3023;
    /* 用来标识请求gallery的activity */
    protected static final int PHOTO_PICKED_WITH_DATA = 3021;
    /**
     * 裁剪图片返回
     */
    protected static final int PHOTO_CROP = 3022;
    /* 拍照的照片存储位置 */
    protected File PHOTO_DIR = null;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PHOTO_DIR = getExternalCacheDir();
        CapturePhotoFile = new File(PHOTO_DIR, "tmp_capture.jpg");
    }

    /**
     * 从相册选择照片
     */
    public void pickPhoto() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Helper.showToast("没有找到照片");
        }
    }

    /**
     * 拍照获取图片
     */
    public void takePhoto() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", CapturePhotoFile);
        } else {
            imageUri = Uri.fromFile(CapturePhotoFile);
        }
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if (componentName != null) {
            startActivityForResult(intent, CAMERA_WITH_DATA);
        }
    }

    /**
     * 图片拍照以后回调
     *
     * @param photoPath
     */
    protected abstract void onPhotoTook(String photoPath);

    /**
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况, 他们启动时是这样的startActivityForResult
     */
    protected void onActivityResult(int requestCode, int resultCode,Intent mIntent) {
        super.onActivityResult(requestCode, resultCode, mIntent);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //从相册选择图片返回
            case PHOTO_PICKED_WITH_DATA:
                try {
                    Uri uri = mIntent.getData();
                    final String absolutePath = AbsolutePathUtil.getAbsolutePath(BaseCaptureActivity.this, uri);
                    String str = startUCrop(BaseCaptureActivity.this, absolutePath, 6, 1, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //拍照返回
            case CAMERA_WITH_DATA:
                try {
                    final String absolutePath = CapturePhotoFile.getAbsolutePath();
                    String str = startUCrop(BaseCaptureActivity.this, absolutePath, 6, 1, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 6:
                Uri resultUri = UCrop.getOutput(mIntent);
                if (null != resultUri) {
                    String path = AbsolutePathUtil.getAbsolutePath(BaseCaptureActivity.this, resultUri);
                    if (!TextUtils.isEmpty(path)) {
                        onPhotoTook(path);
                    }
                }
                break;
        }
    }

    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    public String getPath(Uri uri) {
        if (uri.getAuthority() == null || uri.getAuthority().length() == 0) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }

    /**
     * 裁剪图片
     */
    public void cropImage() {
        Log.i(TAG, ">>>>>>>>>>>>>\n裁剪图片\n>>>>>>>>>>>>>>>>");
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            //intent.setType("image/*");
            intent.setDataAndType(Uri.fromFile(CapturePhotoFile), "image/*");
            intent.putExtra("crop", "true");
            //intent.putExtra("aspectX", 1);
            //intent.putExtra("aspectY", 1);
            //intent.putExtra("outputX", 222);
            //intent.putExtra("outputY", 222);
            intent.putExtra("return-data", false);
            //intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            CropPhotoFile = new File(PHOTO_DIR, UUID.randomUUID() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(CropPhotoFile));
            startActivityForResult(intent, PHOTO_CROP);
        } catch (Exception e) {
            e.printStackTrace();
            if (CapturePhotoFile.exists()) {
                String path = CapturePhotoFile.getAbsolutePath();
                Log.i("i", "获取的图片的路径是 = " + path);
                onPhotoTook(path);
            }
        }
    }

    /**
     * 启动裁剪
     */
    public String startUCrop(Activity activity, String sourceFilePath, int requestCode, float aspectRatioX, float aspectRatioY) {
        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
        String cameraScalePath = outFile.getAbsolutePath();
        Uri destinationUri = Uri.fromFile(outFile);
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        UCrop.Options options = new UCrop.Options();
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(true);
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setCircleDimmedLayer(false);
        options.setShowCropFrame(true);
        options.setShowCropGrid(true);
        options.setMaxBitmapSize(1000);
        options.setCompressionQuality(100);
        uCrop.withOptions(options);
        uCrop.withAspectRatio(aspectRatioX, aspectRatioY);
        uCrop.start(activity, requestCode);
        return cameraScalePath;
    }
    /**
     * //设置Toolbar标题
     void setToolbarTitle(@Nullable String text)
     //设置裁剪的图片格式
     void setCompressionFormat(@NonNull Bitmap.CompressFormat format)
     //设置裁剪的图片质量，取值0-100
     void setCompressionQuality(@IntRange(from = 0) int compressQuality)
     //设置最多缩放的比例尺
     void setMaxScaleMultiplier(@FloatRange(from = 1.0, fromInclusive = false) float maxScaleMultiplier)
     //动画时间
     void setImageToCropBoundsAnimDuration(@IntRange(from = 100) int durationMillis)
     //设置图片压缩最大值
     void setMaxBitmapSize(@IntRange(from = 100) int maxBitmapSize)
     //是否显示椭圆裁剪框阴影
     void setOvalDimmedLayer(boolean isOval)
     //设置椭圆裁剪框阴影颜色
     void setDimmedLayerColor(@ColorInt int color)
     //是否显示裁剪框
     void setShowCropFrame(boolean show)
     //设置裁剪框边的宽度
     void setCropFrameStrokeWidth(@IntRange(from = 0) int width)
     //是否显示裁剪框网格
     void setShowCropGrid(boolean show)
     //设置裁剪框网格颜色
     void setCropGridColor(@ColorInt int color)
     //设置裁剪框网格宽
     void setCropGridStrokeWidth(@IntRange(from = 0) int width)
     */
}

