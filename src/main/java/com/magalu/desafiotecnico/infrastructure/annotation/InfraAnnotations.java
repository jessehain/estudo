package com.magalu.desafiotecnico.infrastructure.annotation;

import com.magalu.desafiotecnico.infrastructure.exception.InfraException;
import com.magalu.desafiotecnico.infrastructure.factory.SequenceFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InfraAnnotations {

  SequenceFactory sequenceFactory;

  public <D> D preencherCamposAnotados(D document) {

    Class<?> classeDoc = document.getClass();

    Arrays.stream(classeDoc.getDeclaredFields())
        .forEach(field -> {
          try {
            this.processarAnotacoes(document, field);
          } catch (Exception e) {
            throw new InfraException("Erro no preenchimento dos campos anotados.");
          }
        });

    return (document);
  }

  public void processarAnotacoes(Object object, Field campo)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    if (campo.isAnnotationPresent(AutoGeneration.class) &&
        BeanUtils.getProperty(object, campo.getName()).equals("0")) {
      String sequenceName = campo.getAnnotation(AutoGeneration.class).sequence();
      var sequenceId = sequenceFactory.next(sequenceName);
      BeanUtils.setProperty(object, campo.getName(), sequenceId);

    }
    if (campo.isAnnotationPresent(DateTimeNow.class)) {
      BeanUtils.setProperty(object, campo.getName(), LocalDateTime.now());
    }
  }

}
