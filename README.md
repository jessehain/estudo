Olá,

Bem vindo ao meu repositório.

Seguem algumas explicações e justificativas:

- Para rodar a aplicação, está incluido um docker compose do mongoDb, que deve ser executado antes de subir a aplicação;
- Existe uma classe MongodbMigration anotada com @Config que faz a criação de database e alguns índices;
- Utilizado Java 11, Maven, Spring Boot 2.6.7, MongoDb;
- Tem um swagger configurado. 
- A idéia foi manter a camada de domínio completamente desacoplada (inclusive do Spring), com acessos realizados através de portas e adaptadores;
- Os testes unitários tbm seguiram a idéia de desacoplar, mockando as outras classes;
- O controle da API ficou somente na wishlist e itens. Não há validações de cliente e produto;
- Como se tratou de um teste, alguns cenários não tiverem testes unitários;
- 87% das classes e 66% das linhas estão cobertas por testes;
- Pensei em implementar cache, mas não tive tempo para isso;
- Implementei uma sequence para geração automática dos Ids no mongoDb, apenas para ter um Id com um número mais significativo que o padrão do MongoDb;
- Muita coisa deveria ser melhorada se fosse uma API para produção, porém isso demandaria um tempo que infelizmente não tenho disponível.

Bom, fico a disposição para conversar sobre a aplicação e esclarecer dúvidas.



 