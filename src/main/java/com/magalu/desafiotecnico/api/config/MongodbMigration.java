package com.magalu.desafiotecnico.api.config;

import com.magalu.desafiotecnico.infrastructure.entity.WishlistDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
public class MongodbMigration {

  @Autowired
  MongoOperations mongoOperations;

  @Bean
  public void dbMigration() {
    createDatabase();
  }

  private void createDatabase() {

    if (!mongoOperations.collectionExists("wishlist")) {
      mongoOperations.createCollection("wishlist");
      mongoOperations.indexOps(WishlistDocument.class).
          ensureIndex(new Index().on("cliente.id", Direction.ASC));
      mongoOperations.indexOps(WishlistDocument.class).
          ensureIndex(new Index().on("produtos.id", Direction.ASC));
    }
  }
}
