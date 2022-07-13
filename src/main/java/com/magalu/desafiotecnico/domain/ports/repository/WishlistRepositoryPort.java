package com.magalu.desafiotecnico.domain.ports.repository;

import com.magalu.desafiotecnico.domain.model.Produto;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import java.util.List;

public interface WishlistRepositoryPort {

  Wishlist buscarWishlistCliente(long idCliente);

  Produto buscarProdutoWishlist(long idCliente, long idProduto);

  void adicionarProdutoWishlist(Wishlist wishlist, List<Produto> produtos);

  void excluirProdutoLista(long idCliente, long idProduto);

  void excluirListaCliente(long idCliente);

}
