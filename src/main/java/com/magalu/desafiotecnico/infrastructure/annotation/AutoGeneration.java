package com.magalu.desafiotecnico.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoGeneration {

  @AliasFor("sequence")
  String value() default "";

  @AliasFor("value")
  String sequence() default "";
}
