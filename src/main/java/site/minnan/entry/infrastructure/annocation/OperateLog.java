package site.minnan.entry.infrastructure.annocation;

import site.minnan.entry.infrastructure.enumerate.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    Operation operation();

    String module() default "";

    String content() default "";
}

