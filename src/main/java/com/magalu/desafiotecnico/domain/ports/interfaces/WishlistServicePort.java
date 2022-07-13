package com.magalu.desafiotecnico.domain.ports.interfaces;

import com.magalu.desafiotecnico.domain.model.Produto;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import java.util.List;

public interface WishlistServicePort {

  Produto buscarProdutoLista(long idCliente, long idProduto);

  Wishlist buscarListaCompletaCliente(long idCliente);

  void criarOuAtualizarWishlist(Wishlist wishlist);

  void inserirItemLista(long idCliente, Produto produto);

  void inserirListaItens(Wishlist wishlist, List<Produto> produtos);

  void excluirItemLista(long idCliente, long idProduto);

  void excluirLista(long idCliente);

}
