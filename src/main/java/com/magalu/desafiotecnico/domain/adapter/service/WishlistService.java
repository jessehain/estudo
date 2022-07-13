package com.magalu.desafiotecnico.domain.adapter.service;

import com.magalu.desafiotecnico.domain.constants.Constants;
import com.magalu.desafiotecnico.domain.exception.DomainException;
import com.magalu.desafiotecnico.domain.model.Produto;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import com.magalu.desafiotecnico.domain.ports.interfaces.WishlistServicePort;
import com.magalu.desafiotecnico.domain.ports.repository.WishlistRepositoryPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WishlistService implements WishlistServicePort {

  private WishlistRepositoryPort wishlistRepositoryPort;

  @Override
  public Produto buscarProdutoLista(long idCliente, long idProduto) {
    return wishlistRepositoryPort.buscarProdutoWishlist(idCliente, idProduto);
  }

  @Override
  public Wishlist buscarListaCompletaCliente(long idCliente) {
    return wishlistRepositoryPort.buscarWishlistCliente(idCliente);
  }

  @Override
  public void criarOuAtualizarWishlist(Wishlist wishlist) {
    inserirListaItens(wishlist, wishlist.getProdutos());
  }

  @Override
  public void inserirItemLista(long idCliente, Produto produto) {

    var wishlist = wishlistRepositoryPort.buscarWishlistCliente(idCliente);

    if (Objects.isNull(wishlist)) {
      throw new DomainException(Constants.CLIENTE_NAO_POSSUI_WISHLIST);
    }

    if (wishlist.getProdutos().stream().anyMatch(prod -> produto.getId() == prod.getId())) {
      throw new DomainException(Constants.PRODUTO_JA_ESTA_NA_WISHLIST);
    }

    var produtos = new ArrayList<Produto>();
    produtos.add(produto);

    inserirListaItens(wishlist, produtos);
  }

  @Override
  public void inserirListaItens(Wishlist wishlist, List<Produto> produtos) {

    if (wishlist.getProdutos().size() + produtos.size() > Constants.MAXIMO_ITENS_WISHLIST) {
      throw new DomainException(Constants.EXCEDIDO_NUMERO_DE_ITENS_DA_LISTA);
    }
    wishlistRepositoryPort.adicionarProdutoWishlist(wishlist, produtos);
  }

  @Override
  public void excluirItemLista(long idCliente, long idProduto) {
    wishlistRepositoryPort.excluirProdutoLista(idCliente, idProduto);
  }

  @Override
  public void excluirLista(long idCliente) {
    wishlistRepositoryPort.excluirListaCliente(idCliente);
  }

}
