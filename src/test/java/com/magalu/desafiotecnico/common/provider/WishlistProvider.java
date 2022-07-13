package com.magalu.desafiotecnico.common.provider;

import static com.magalu.desafiotecnico.common.constants.ConstantsTest.DATA_ATUALIZACAO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_VALIDO;

import com.magalu.desafiotecnico.domain.model.Wishlist;

public class WishlistProvider {

  public static Wishlist build() {

    Wishlist wishlist = new Wishlist();
    wishlist.setId(ID_VALIDO);
    wishlist.setDataAtualizacao(DATA_ATUALIZACAO);
    wishlist.setCliente(ClienteProvider.valido());
    wishlist.setProdutos(ProdutoProvider.buildListProduto(1, ID_VALIDO));
    ;

    return wishlist;

  }
}
