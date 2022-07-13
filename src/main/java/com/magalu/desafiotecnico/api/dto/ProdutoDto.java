package com.magalu.desafiotecnico.api.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProdutoDto {

  @Min(1)
  private long id;

  @NotBlank
  private String descricao;
}
