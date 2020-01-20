package com.insect.jskin.processor;

import com.google.auto.service.AutoService;
import com.insect.jskin.annotation.SupportAttr;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
public class SkinProcessor extends AbstractProcessor{
    private Filer filer;
    private Messager messager;
    private Elements elements;

    private  HashMap<Integer,ClassName> supMap=new HashMap<>();


    private final ClassName classSparseArray=ClassName.get("android.util","SparseArray");
    private final ClassName classClass=ClassName.get("java.lang","Class");
    private final ClassName classAttrBase=ClassName.get("com.insect.jskin.library.support.base","SkinAttr");

    private final TypeName support =ParameterizedTypeName.get(classSparseArray,
                            ParameterizedTypeName.get(classClass,WildcardTypeName.subtypeOf(classAttrBase)));


    private static final  String generatedPackage="com.insect.jskin.factory";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        this.filer = processingEnvironment.getFiler();
        this.messager = processingEnvironment.getMessager();
        this.elements = processingEnvironment.getElementUtils();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        for(Element element : roundEnvironment.getElementsAnnotatedWith(SupportAttr.class)){
            if(element.getKind() != ElementKind.CLASS){
                messager.printMessage(Diagnostic.Kind.ERROR, "@SupportAttr should be on top of classes");
                return false;
            }
            SupportAttr supportAttr=element.getAnnotation(SupportAttr.class);
            for(int i=0;i<supportAttr.value().length;i++) {
                supMap.put(supportAttr.value()[i], ClassName.get(elements.getPackageOf(element).getQualifiedName().toString(),
                        element.getSimpleName().toString()));
            }
        }



        FieldSpec sparseArraySpec= FieldSpec.builder(support
                , "supportAttrs", Modifier.PRIVATE,Modifier.STATIC,Modifier.FINAL).initializer("new SparseArray<>()").build();


        TypeSpec.Builder generatedClass = TypeSpec
            .classBuilder("SupportAttrFactory")
            .addStaticBlock(method(supMap))
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addField(sparseArraySpec)
            .addMethod(MethodSpec
                    .methodBuilder("getSupportAttrs")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(support)
                    .addStatement("return supportAttrs")
                    .build());


        try {
           JavaFile javaFile= JavaFile.builder(generatedPackage, generatedClass.build()).build();
           javaFile.writeTo(filer);
            System.out.println(javaFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(SupportAttr.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private CodeBlock method(HashMap<Integer,ClassName> supMap){

        CodeBlock.Builder builder= CodeBlock.builder();
               for(Integer key:supMap.keySet()) {
                   builder.addStatement("$N.put($L,$T.class)","supportAttrs",key,supMap.get(key));
               }
               return builder.build();
    }
}
