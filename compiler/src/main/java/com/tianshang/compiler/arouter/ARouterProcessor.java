package com.tianshang.compiler.arouter;



import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.annotation.arouter.model.RouterBean;
import com.tianshang.compiler.arouter.utils.Constants;
import com.tianshang.compiler.arouter.utils.EmptyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件

@AutoService(Processor.class)
// 允许/支持的注解类型，让注解处理器处理
@SupportedAnnotationTypes(Constants.AROUTER_ANNOTATION_TYPES)
// 指定JDK编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions({Constants.MODULE, Constants.APT_PACKAGE})
public class ARouterProcessor extends AbstractProcessor {

    //操作Element工具类
    private Elements elementsUtils;
    //type(信息类)工具类
    private Types typesUtils;
    //用来输出警告和错误日志
    private Messager messager;
    //文件生成器 类/资源，Filter用来创建新的源文件，class文件以及辅助文件
    private Filer filer;
    //字谜快名，如：app/order/personal,需要拼接类名时用到（必传）ARouter$$Group$$order
    private String moduleName;
    //包名用于存放APT生成的类文件
    private String packageNameForAPT;


    // 临时map存储，用来存放路由Group信息，生成路由组类文件时遍历
    // key:组名"app", value:类名"ARouter$$Path$$app.class"
    private Map<String, List<RouterBean>> tempPathMap = new HashMap<>();
    // 临时map存储，用来存放路由Group信息，生成路由组类文件时遍历
    // key:组名"app", value:类名"ARouter$$Path$$app.class"
    private Map<String, String> tempGroupMap = new HashMap<>();


    // 该方法主要用于一些初始化的操作，通过该方法的参数ProcessingEnvironment可以获取一些列有用的工具类
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementsUtils = processingEnvironment.getElementUtils();
        typesUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

        Map<String, String> options = processingEnvironment.getOptions();
        if (!EmptyUtils.isEmpty(options)) {
            moduleName = processingEnvironment.getOptions().get(Constants.MODULE);
            packageNameForAPT = processingEnvironment.getOptions().get(Constants.APT_PACKAGE);
            messager.printMessage(Diagnostic.Kind.NOTE, moduleName);
            messager.printMessage(Diagnostic.Kind.NOTE, packageNameForAPT);
        }

        if (EmptyUtils.isEmpty(moduleName) || EmptyUtils.isEmpty(packageNameForAPT)) {
            throw new RuntimeException("注解处理器需要的moduleName或packageName为空，请在对应build.grade配置参数");
        }

    }

    /**
     * 相当于main函数，开始处理注解
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param set              使用了支持处理注解的节点集合（类 上面写了注解）
     * @param roundEnvironment 当前或是之前的运行环境,可以通过该对象查找找到的注解。
     * @return true 表示后续处理器不会再处理（已经处理完成）
     */

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!set.isEmpty()) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);
            if (!EmptyUtils.isEmpty(elements)) {
                try {
                    parseElements(elements);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
//
//        //获取项目中所有使用了ARouter注解的节点
//        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);
//        //遍历所有节点
//        for (Element element : elements) {
//            //类节点之上就是包节点
//            String packageName = elementsUtils.getPackageOf(element).getQualifiedName().toString();
//            //获取简单类名
//            String className = element.getSimpleName().toString();
//            messager.printMessage(Diagnostic.Kind.NOTE, "被注解的类有" + className);
//            //最终我们想要生成的类文件，如：MainActivity$$ARouter
//            String finalClassName = className + "$$ARouter";

//            try {
//                JavaFileObject sourceFile = filer.createSourceFile(packageName+"."+finalClassName);
//                Writer writer = sourceFile.openWriter();
//                // 设置包名
//                writer.write("package " + packageName + ";\n");
//
//                writer.write("public class " + finalClassName + " {\n");
//
//                writer.write("public static Class<?> findTargetClass(String path) {\n");
//
//                // 获取类之上@ARouter注解的path值
//                ARouter aRouter = element.getAnnotation(ARouter.class);
//
//                writer.write("if (path.equalsIgnoreCase(\"" + aRouter.path() + "\")) {\n");
//
//                writer.write("return " + className + ".class;\n}\n");
//
//                writer.write("return null;\n");
//
//                writer.write("}\n}");
//
//                // 最后结束别忘了
//                writer.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        //高级写法，javapoet构建工具
//            try {
//                //获取类之上@ARouter注解的path值
//                ARouter aRouter = element.getAnnotation(ARouter.class);
//                //构建方法体
//                MethodSpec method = MethodSpec.methodBuilder("finalTargetClass")
//                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                        .returns(Class.class)
//                        .addParameter(String.class, "path")
//                        .addStatement("return path.equals($S) ? $T.class : null",
//                                aRouter.path(), ClassName.get((TypeElement) element))
//                        .build();
//                //构建类
//                TypeSpec type = TypeSpec.classBuilder(finalClassName)
//                        .addModifiers(Modifier.PUBLIC)
//                        .addMethod(method) //添加方法体
//                        .build();
//                //在指定的包名下，生成java类文件
//                JavaFile javaFile = JavaFile.builder(packageName, type)
//                        .build();
//                javaFile.writeTo(filer);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
        return false;
    }

    private void parseElements(Set<? extends Element> elements) throws IOException {
        //通过Element工具类，获取Activity类型
        TypeElement activityType = elementsUtils.getTypeElement(Constants.ACTIVITY);
        TypeElement callType = elementsUtils.getTypeElement(Constants.CALL);
        //显示类信息
        TypeMirror activityMirror = activityType.asType();
        TypeMirror callMirror = callType.asType();

        for (Element element : elements) {
            //获取每个元素的类信息
            TypeMirror elementMirror = element.asType();
            messager.printMessage(Diagnostic.Kind.NOTE, "遍历的元素信息为：" + elementMirror.toString());
            //获取每个类上的@ARouter注解，对应得path值
            ARouter aRouter = element.getAnnotation(ARouter.class);

            //路径的详细信息，封装到实体类
            RouterBean bean = new RouterBean.Builder()
                    .setGroup(aRouter.group())
                    .setPath(aRouter.path())
                    .setElement(element)
                    .build();

            //高级判断，@ARouter注解仅仅只能用在类之上，并且是规定的Activity
            if (typesUtils.isSubtype(elementMirror, activityMirror)) {
                bean.setType(RouterBean.Type.ACTIVITY);
            }else if(typesUtils.isSubtype(elementMirror, callMirror)){
                bean.setType(RouterBean.Type.CALL);
            }else {
                throw new RuntimeException("@ARouter注解目前仅限于Activity之上");
            }

            //赋值临时map存储以上信息，用来遍历时生成代码
            valueOfPathMap(bean);
        }

        //ARouterLoadGroup喝ARouterLoadPath类型，用来生成类文件实现接口
        TypeElement groupLoadType = elementsUtils.getTypeElement(Constants.AROUTE_GROUP);
        TypeElement pathLoadType = elementsUtils.getTypeElement(Constants.AROUTE_Path);

        //1.生成路由的详细Path类文件，如：ARouter$$Path$$app
        createPathFile(pathLoadType);
        //2.生成路由组Group类文件（没有path类文件，取不到）如：ARouter$$Group$$app
        createGroupFile(groupLoadType, pathLoadType);
    }

    /**
     * 生成路由组Group文件，如：ARouter$$Group$$app
     *
     * @param groupLoadType ARouterLoadGroup接口信息
     * @param pathLoadType  ARouterLoadPath接口信息
     */
    private void createGroupFile(TypeElement groupLoadType, TypeElement pathLoadType) throws IOException {
        // 判断是否有需要生成的类文件
        if (EmptyUtils.isEmpty(tempGroupMap) || EmptyUtils.isEmpty(tempPathMap)) return;

        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class), // Map
                ClassName.get(String.class), // Map<String,
                // 第二个参数：Class<? extends ARouterLoadPath>
                // 某某Class是否属于ARouterLoad Path接口的实现类
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathLoadType)))
        );

        // 方法配置：public Map<String, Class<? extends ARouterLoadPath>> loadGroup() {
        MethodSpec.Builder methodBuidler = MethodSpec.methodBuilder(Constants.GROUP_METHOD_NAME) // 方法名
                .addAnnotation(Override.class) // 重写注解
                .addModifiers(Modifier.PUBLIC) // public修饰符
                .returns(methodReturns); // 方法返回值

        // 遍历之前：Map<String, Class<? extends ARouterLoadPath>> groupMap = new HashMap<>();
        methodBuidler.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathLoadType))),
                Constants.GROUP_PARAMETER_NAME,
                HashMap.class);

        // 方法内容配置
        for (Map.Entry<String, String> entry : tempGroupMap.entrySet()) {
            // 类似String.format("hello %s net163 %d", "net", 163)通配符
            // groupMap.put("main", ARouter$$Path$$app.class);
            methodBuidler.addStatement("$N.put($S, $T.class)",
                    Constants.GROUP_PARAMETER_NAME, // groupMap.put
                    entry.getKey(),
                    // 类文件在指定包名下
                    ClassName.get(packageNameForAPT, entry.getValue()));
        }

        // 遍历之后：return groupMap;
        methodBuidler.addStatement("return $N", Constants.GROUP_PARAMETER_NAME);

        // 最终生成的类文件名
        String finalClassName = Constants.GROUP_FILE_NAME + moduleName;
        messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由组Group类文件：" +
                packageNameForAPT + "." + finalClassName);

        // 生成类文件：ARouter$$Group$$app
        JavaFile.builder(packageNameForAPT, // 包名
                TypeSpec.classBuilder(finalClassName) // 类名
                        .addSuperinterface(ClassName.get(groupLoadType)) // 实现ARouterLoadGroup接口
                        .addModifiers(Modifier.PUBLIC) // public修饰符
                        .addMethod(methodBuidler.build()) // 方法的构建（方法参数 + 方法体）
                        .build()) // 类构建完成
                .build() // JavaFile构建完成
                .writeTo(filer); // 文件生成器开始生成类文件

    }

    /**
     * 生成路由组Group对应详细Path，如：ARouter$$Path$$app
     *
     * @param pathLoadType ARouterLoadPath接口信息
     */
    private void createPathFile(TypeElement pathLoadType) throws IOException {
        if (EmptyUtils.isEmpty(tempPathMap)) return;
        //方法的返回值Map<String,RouterBean>
        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(RouterBean.class));
        //遍历分组，每一个分组创建一个路径类文件夹，如：ARouter$$Group$$app
        for (Map.Entry<String, List<RouterBean>> entry : tempPathMap.entrySet()) {
            //方法体构造public Map<String,RouterBean> loadPath(){ }
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.PATH_METHOD_NAME) //方法名
                    .addAnnotation(Override.class) //重写注解
                    .addModifiers(Modifier.PUBLIC)  //public 修饰
                    .returns(methodReturns); //返回类型

            //不循环部分 Map<String,RouterBean> pathMap = new HashMap<>();
            methodBuilder.addStatement("$T<$T,$T> $N = new $T<>()",
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ClassName.get(RouterBean.class),
                    Constants.PATH_PARAMETER_NAME,
                    ClassName.get(HashMap.class));
            // app/MainActivity , app/FirstActivity
            List<RouterBean> pathList = entry.getValue();
            for (RouterBean bean : pathList) {
                //方法内容的循环部分
                // pathMap.put("/app/MainActivity", RouterBean.create(
                //        RouterBean.Type.ACTIVITY, MainActivity.class, "/app/MainActivity", "app"));
                methodBuilder.addStatement("$N.put($S,$T.create($T.$L,$T.class,$S,$S))",
                        Constants.PATH_PARAMETER_NAME,
                        bean.getPath(),
                        ClassName.get(RouterBean.class),
                        ClassName.get(RouterBean.Type.class),
                        bean.getType(),
                        ClassName.get((TypeElement) bean.getElement()),
                        bean.getPath(),
                        bean.getGroup());
            }
            // 遍历之后：return pathMap;
            methodBuilder.addStatement("return $N", Constants.PATH_PARAMETER_NAME);
            // 最终生成的类文件名
            String finalClassName = Constants.PATH_FILE_NAME + entry.getKey();

            // 生成类文件：ARouter$$Path$$app
            JavaFile.builder(packageNameForAPT, // 包名
                    TypeSpec.classBuilder(finalClassName) // 类名
                            .addSuperinterface(ClassName.get(pathLoadType)) // 实现ARouterLoadPath接口
                            .addModifiers(Modifier.PUBLIC) // public修饰符
                            .addMethod(methodBuilder.build()) // 方法的构建（方法参数 + 方法体）
                            .build()) // 类构建完成
                    .build() // JavaFile构建完成
                    .writeTo(filer); // 文件生成器开始生成类文件

            // 非常重要一步！！！！！路径文件生成出来了，才能赋值路由组tempGroupMap
             tempGroupMap.put(entry.getKey(), finalClassName);
        }

    }

    private void valueOfPathMap(RouterBean bean) {
        if (checkRouterPath(bean)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean>>>" + bean.toString());
            //开始赋值
            List<RouterBean> routerBeans = tempPathMap.get(bean.getGroup());
            //如果map找不到key
            if (EmptyUtils.isEmpty(routerBeans)) {
                routerBeans = new ArrayList<>();
                routerBeans.add(bean);
                tempPathMap.put(bean.getGroup(), routerBeans);
            } else {
                routerBeans.add(bean);
            }
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE, "ARouter注解未按规范，如：/app/MainActivity");
        }
    }

    /**
     * 校验@ARouter注解的值，如果group未填写就从必填项path中截取数据
     *
     * @param bean 路由详细信息，最终实体封装类
     */

    private boolean checkRouterPath(RouterBean bean) {
        String group = bean.getGroup();
        String path = bean.getPath();

        // @ARouter注解中的path值，必须要以 / 开头（模仿阿里Arouter规范）
        if (EmptyUtils.isEmpty(path) || !path.startsWith("/")) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的path值，必须要以 / 开头");
            return false;
        }

        // 比如开发者代码为：path = "/MainActivity"，最后一个 / 符号必然在字符串第1位
        if (path.lastIndexOf("/") == 0) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity");
            return false;
        }

        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app 作为group
        String finalGroup = path.substring(1, path.indexOf("/", 1));

        // @ARouter注解中的group有赋值情况
        if (!EmptyUtils.isEmpty(group) && !group.equals(moduleName)) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的group值必须和子模块名一致！");
            return false;
        } else {
            bean.setGroup(finalGroup);
        }
        return true;

    }
}
