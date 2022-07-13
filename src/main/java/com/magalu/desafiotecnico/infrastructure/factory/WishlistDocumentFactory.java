package com.magalu.desafiotecnico.infrastructure.factory;

import com.magalu.desafiotecnico.domain.model.Wishlist;
import com.magalu.desafiotecnico.infrastructure.annotation.InfraAnnotations;
import com.magalu.desafiotecnico.infrastructure.entity.WishlistDocument;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WishlistDocumentFactory {

  ModelMapper modelMapper;
  InfraAnnotations infraAnnotations;

  public WishlistDocument fromDto(Wishlist wishlist, long id) {
    WishlistDocument document = WishlistDocument
        .builder()
        .id(id)
        .cliente(wishlist.getCliente())
        .produtos(wishlist.getProdutos())
        .build();
    return infraAnnotations.preencherCamposAnotados(document);
  }

  public WishlistDocument formataParaPersistir(WishlistDocument document) {
    return infraAnnotations.preencherCamposAnotados(document);
  }
}
