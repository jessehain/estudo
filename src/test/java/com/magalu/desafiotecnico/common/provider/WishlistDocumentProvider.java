package com.magalu.desafiotecnico.common.provider;

import static com.magalu.desafiotecnico.common.constants.ConstantsTest.DATA_ATUALIZACAO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_VALIDO;

import com.magalu.desafiotecnico.infrastructure.entity.WishlistDocument;

public class WishlistDocumentProvider {

  public static WishlistDocument valido() {
    WishlistDocument document = WishlistDocument
        .builder()
        .id(ID_VALIDO)
        .cliente(ClienteProvider.valido())
        .produtos(ProdutoProvider.buildListProduto(1, ID_VALIDO))
        .dataAtualizacao(DATA_ATUALIZACAO)
        .build();
    return document;
  }

}
