package com.magalu.desafiotecnico.infrastructure.repository.mongodb;

import com.magalu.desafiotecnico.infrastructure.entity.WishlistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistMongoRepository extends MongoRepository<WishlistDocument, Long> {

  @Query("{'cliente._id': ?0, 'produtos._id': ?1 }")
  WishlistDocument findByIdClienteAndIdProduto(long idCliente, long idProduto);

  @Query("{'cliente._id': ?0 }")
  WishlistDocument findByIdCliente(long idCliente);
}
