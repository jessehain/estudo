package com.magalu.desafiotecnico.infrastructure.entity;

import com.magalu.desafiotecnico.domain.model.Cliente;
import com.magalu.desafiotecnico.domain.model.Produto;
import com.magalu.desafiotecnico.infrastructure.annotation.AutoGeneration;
import com.magalu.desafiotecnico.infrastructure.annotation.DateTimeNow;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "wishlist")
public class WishlistDocument {

  @Id
  @AutoGeneration(sequence = "wishlist_id")
  private long id;
  private Cliente cliente;
  private List<Produto> produtos;
  @DateTimeNow
  private LocalDateTime dataAtualizacao;

  public void addProdutos(List<Produto> produtos) {
    this.produtos.addAll(produtos);
  }

}
