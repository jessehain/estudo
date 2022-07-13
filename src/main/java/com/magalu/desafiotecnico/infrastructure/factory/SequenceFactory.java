package com.magalu.desafiotecnico.infrastructure.factory;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class SequenceFactory {

  @Autowired
  MongoOperations mongoOperations;

  @Document(collection = "sequences")
  private static class Sequence {

    @Id
    private String id;
    private long seq;
  }

  public long next(String seqName) {
    Sequence counter = mongoOperations
        .findAndModify(
            Query.query(where("_id").is(seqName)),
            new Update().inc("seq", 1),
            options().returnNew(true).upsert(true),
            Sequence.class);

    return !Objects.isNull(counter) ? counter.seq : 1;
  }

}

