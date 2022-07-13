package com.magalu.desafiotecnico.common.provider;

import com.magalu.desafiotecnico.domain.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoProvider {

  public static Produto buildProduto(long idProduto) {
    Produto produto = new Produto();
    produto.setId(idProduto);
    produto.setDescricao("PRODUTO " + produto.getId());
    return produto;
  }

  public static List<Produto> buildListProduto(int numItens, long idInicial) {

    List<Produto> produtos = new ArrayList<>();
    for (int i = 0; i < numItens; i++) {
      Produto produto = buildProduto(idInicial + i);
      produtos.add(produto);
    }
    return produtos;

  }

}