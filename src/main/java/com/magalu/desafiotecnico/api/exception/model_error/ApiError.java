package com.magalu.desafiotecnico.api.exception.model_error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError {

  private String tipo;
  private String mensagem;

  public ApiError(String tipo, String mensagem) {
    super();
    this.tipo = tipo;
    this.mensagem = mensagem;
  }

  public ApiError() {
    super();
  }

}