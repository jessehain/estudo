package com.magalu.desafiotecnico.infrastructure.repository;

import com.magalu.desafiotecnico.domain.model.Produto;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import com.magalu.desafiotecnico.domain.ports.repository.WishlistRepositoryPort;
import com.magalu.desafiotecnico.infrastructure.constants.Constants;
import com.magalu.desafiotecnico.infrastructure.entity.WishlistDocument;
import com.magalu.desafiotecnico.infrastructure.exception.InfraException;
import com.magalu.desafiotecnico.infrastructure.factory.WishlistDocumentFactory;
import com.magalu.desafiotecnico.infrastructure.repository.mongodb.WishlistMongoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WishlistRepository implements WishlistRepositoryPort {

  WishlistMongoRepository wishlistMongoRepository;
  WishlistDocumentFactory wishlistDocumentFactory;
  ModelMapper modelMapper;

  @Override
  public Wishlist buscarWishlistCliente(long idCliente) {
    var wishlistDocument = wishlistMongoRepository.findByIdCliente(idCliente);

    if (Objects.isNull(wishlistDocument)) {
      throw new InfraException(Constants.WISHLIST_NAO_EXISTE);
    }

    return modelMapper.map(wishlistDocument, Wishlist.class);
  }

  @Override
  public Produto buscarProdutoWishlist(long idCliente, long idProduto) {
    var wishlistDocument = wishlistMongoRepository
        .findByIdClienteAndIdProduto(idCliente, idProduto);

    if (Objects.isNull(wishlistDocument)) {
      throw new InfraException(Constants.PRODUTO_NAO_EXISTE_NA_WISHLIST);
    }

    return wishlistDocument.getProdutos()
        .stream()
        .filter(produto -> produto.getId() == idProduto)
        .collect(Collectors.toList()).get(0);
  }

  @Override
  public void adicionarProdutoWishlist(Wishlist wishlist, List<Produto> produtos) {

    var cliente = wishlist.getCliente();
    List<Produto> produtosAdd = new ArrayList<>();
    var wishlistDocument = wishlistMongoRepository.findByIdCliente(cliente.getId());

    if (Objects.isNull(wishlistDocument)) {
      wishlistDocument = WishlistDocument.builder()
          .id(0)
          .cliente(cliente)
          .produtos(wishlist.getProdutos())
          .build();
    }

    for (var produto : produtos) {
      if (wishlistDocument.getProdutos().stream()
          .anyMatch(prod -> prod.getId() == produto.getId())) {
        continue;
      }
      produtosAdd.add(produto);
    }

    wishlistDocument.addProdutos(produtosAdd);

    wishlistDocument =
        wishlistDocumentFactory
            .formataParaPersistir(wishlistDocument);

    wishlistMongoRepository.save(wishlistDocument);
  }

  @Override
  public void excluirProdutoLista(long idCliente, long idProduto) {
    var wishlistDocument = wishlistMongoRepository
        .findByIdClienteAndIdProduto(idCliente, idProduto);

    if (Objects.isNull(wishlistDocument)) {
      throw new InfraException(Constants.PRODUTO_NAO_EXISTE_NA_WISHLIST);
    }

    if (wishlistDocument.getProdutos().size() == 1) {
      excluirListaCliente(idCliente);
    }
    wishlistDocument.getProdutos().removeIf(produto -> produto.getId() == idProduto);
    wishlistMongoRepository.save(wishlistDocument);
  }

  @Override
  public void excluirListaCliente(long idCliente) {
    var wishlistDocument = wishlistMongoRepository.findByIdCliente(idCliente);

    if (Objects.isNull(wishlistDocument)) {
      throw new InfraException(Constants.WISHLIST_NAO_EXISTE);
    }

    wishlistMongoRepository.delete(wishlistDocument);
  }


}
