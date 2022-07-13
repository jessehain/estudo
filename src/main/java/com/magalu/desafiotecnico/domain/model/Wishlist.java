package com.magalu.desafiotecnico.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class Wishlist {

  private long id;
  private Cliente cliente;
  private List<Produto> produtos;
  private LocalDateTime dataAtualizacao;
}
