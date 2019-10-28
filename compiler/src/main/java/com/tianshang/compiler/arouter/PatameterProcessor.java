package com.tianshang.compiler.arouter;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.tianshang.annotation.arouter.Parameter;
import com.tianshang.compiler.arouter.factory.ParameterFactory;
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
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
// 允许/支持的注解类型，让注解处理器处理
@SupportedAnnotationTypes(Constants.PARAMETER_ANNOTATION_TYPES)
// 指定JDK编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PatameterProcessor extends AbstractProcessor {

    //操作Element工具类
    private Elements elementsUtils;
    //type(信息类)工具类
    private Types typesUtils;
    //用来输出警告和错误日志
    private Messager messager;
    //文件生成器 类/资源，Filter用来创建新的源文件，class文件以及辅助文件
    private Filer filer;
    // 临时map存储，用来存放被@Parameter注解的属性集合，生成类文件时遍历
    // key:类节点，value：被@Parameter注解的属性集合
    private Map<TypeElement, List<Element>> tempParameterMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementsUtils = processingEnvironment.getElementUtils();
        typesUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //一旦属性之上有@Paraneter注解
        if (!EmptyUtils.isEmpty(set)) {
            //获取所有被注接的属性
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Parameter.class);
            if (!EmptyUtils.isEmpty(elements)) {
                try {
                    //用临时map存储，用来遍历生成代码
                    valueOfPathMap(elements);
                    //生成类文件
                    createParameterFile();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return false;
    }

    private void createParameterFile() throws IOException {
        // 判断是否有需要生成的类文件
        if (EmptyUtils.isEmpty(tempParameterMap)) return;
        // 通过Element工具类，获取Parameter类型
        TypeElement activityType = elementsUtils.getTypeElement(Constants.ACTIVITY);
        TypeElement parameterType = elementsUtils.getTypeElement(Constants.PARAMETER_LOAD);

        // 参数体配置(Object target)
        ParameterSpec parameterSpec = ParameterSpec.builder(TypeName.OBJECT, Constants.PARAMETER_NAMR).build();
        for (Map.Entry<TypeElement, List<Element>> entry : tempParameterMap.entrySet()) {
            // Map集合中的key是类名，如：MainActivity
            TypeElement typeElement = entry.getKey();
            // 如果类名的类型和Activity类型不匹配
            if (!typesUtils.isSubtype(typeElement.asType(), activityType.asType())) {
                throw new RuntimeException("@Parameter注解目前仅限用于Activity类之上");
            }

            // 获取类名
            ClassName className = ClassName.get(typeElement);
            // 方法体内容构建
            ParameterFactory factory = new ParameterFactory.Builder(parameterSpec)
                    .setMessager(messager)
                    .setElementsUtils(elementsUtils)
                    .setTypesUtils(typesUtils)
                    .setClassName(className)
                    .build();

            // 添加方法体内容的第一行
            factory.addFirstStatement();

            // 遍历类里面所有属性
            for (Element fieldElement : entry.getValue()) {
                factory.buildStatement(fieldElement);
            }

            // 最终生成的类文件名（类名$$Parameter）
            String finalClassName = typeElement.getSimpleName() + Constants.PARAMETER_FILE_NAME;
            messager.printMessage(Diagnostic.Kind.NOTE, "APT生成获取参数类文件：" +
                    className.packageName() + "." + finalClassName);

            // MainActivity$$Parameter
            JavaFile.builder(className.packageName(), // 包名
                    TypeSpec.classBuilder(finalClassName) // 类名
                            .addSuperinterface(ClassName.get(parameterType)) // 实现ParameterLoad接口
                            .addModifiers(Modifier.PUBLIC) // public修饰符
                            .addMethod(factory.build()) // 方法的构建（方法参数 + 方法体）
                            .build()) // 类构建完成
                    .build() // JavaFile构建完成
                    .writeTo(filer); // 文件生成器开始生成类文件
        }

    }

    private void valueOfPathMap(Set<? extends Element> elements) {
        for (Element element : elements) {
            //注解的属性，父节点是类节点
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            //如果map集合有这个类节点
            if (tempParameterMap.containsKey(typeElement)) {
                tempParameterMap.get(typeElement).add(element);
            } else {
                List<Element> filds = new ArrayList<>();
                filds.add(element);
                tempParameterMap.put(typeElement, filds);
            }
            messager.printMessage(Diagnostic.Kind.NOTE, "遍历的元素信息为：" + element.toString());
        }
    }
}
