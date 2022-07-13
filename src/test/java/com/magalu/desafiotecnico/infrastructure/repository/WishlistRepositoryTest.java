package com.magalu.desafiotecnico.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.magalu.desafiotecnico.common.constants.ConstantsTest;
import com.magalu.desafiotecnico.common.provider.ProdutoProvider;
import com.magalu.desafiotecnico.common.provider.WishlistDocumentProvider;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import com.magalu.desafiotecnico.infrastructure.annotation.InfraAnnotations;
import com.magalu.desafiotecnico.infrastructure.constants.Constants;
import com.magalu.desafiotecnico.infrastructure.exception.InfraException;
import com.magalu.desafiotecnico.infrastructure.factory.SequenceFactory;
import com.magalu.desafiotecnico.infrastructure.factory.WishlistDocumentFactory;
import com.magalu.desafiotecnico.infrastructure.repository.mongodb.WishlistMongoRepository;
import java.util.ArrayList;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
class WishlistRepositoryTest {

  @Spy
  ModelMapper modelMapper;

  @Mock
  SequenceFactory sequenceFactory;

  @Spy
  @InjectMocks
  InfraAnnotations infraAnnotations;

  @Spy
  @InjectMocks
  WishlistDocumentFactory wishlistDocumentFactory;

  @Mock
  WishlistMongoRepository wishlistMongoRepository;

  @InjectMocks
  WishlistRepository wishlistRepository;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @BeforeEach
  void setup() {

    MockitoAnnotations.openMocks(this);

    Mockito.doReturn(ConstantsTest.ID_VALIDO)
        .when(sequenceFactory).next(ArgumentMatchers.any());

    Mockito
        .doReturn(WishlistDocumentProvider.valido())
        .when(wishlistMongoRepository).save(WishlistDocumentProvider.valido());

    Mockito
        .doThrow(RuntimeException.class)
        .when(wishlistMongoRepository).save(WishlistDocumentProvider.valido());

    Mockito
        .doReturn(WishlistDocumentProvider.valido())
        .when(wishlistMongoRepository).findByIdCliente(ConstantsTest.ID_VALIDO);

    Mockito
        .doReturn(null)
        .when(wishlistMongoRepository).findByIdCliente(ConstantsTest.ID_INVALIDO);

    Mockito
        .doReturn(WishlistDocumentProvider.valido())
        .when(wishlistMongoRepository)
        .findByIdClienteAndIdProduto(ConstantsTest.ID_VALIDO, ConstantsTest.ID_VALIDO);

    Mockito
        .doReturn(null)
        .when(wishlistMongoRepository)
        .findByIdClienteAndIdProduto(ConstantsTest.ID_VALIDO, ConstantsTest.ID_INVALIDO);

  }

  @Test
  void dadoClienteValidoDeveRetornarWishlist() throws Exception {
    var retorno = wishlistRepository.buscarWishlistCliente(ConstantsTest.ID_VALIDO);
    var wishlist = modelMapper.map(WishlistDocumentProvider.valido(), Wishlist.class);
    assertThat(retorno).isEqualTo(wishlist);
  }


  @Test
  void dadoClienteInvalidoDeveFalhar() throws Exception {

    Exception exception = assertThrows(InfraException.class,
        () -> wishlistRepository.buscarWishlistCliente(ConstantsTest.ID_INVALIDO));

    assertTrue(exception.getMessage().contains(Constants.WISHLIST_NAO_EXISTE));

  }

  @Test
  void dadoDocumentoValidoDeveInserir() throws Exception {
    var wishlist = modelMapper.map(WishlistDocumentProvider.valido(), Wishlist.class);
    assertDoesNotThrow(
        () -> wishlistRepository.adicionarProdutoWishlist(wishlist, new ArrayList<>()));
  }

  @Test
  void dadoClienteEProdutoValidoDeveListar() throws Exception {
    var retorno =
        wishlistRepository.buscarProdutoWishlist(ConstantsTest.ID_VALIDO, ConstantsTest.ID_VALIDO);
    var produto = ProdutoProvider.buildProduto(ConstantsTest.ID_VALIDO);

    assertThat(retorno).isEqualTo(produto);

  }

  @Test
  void dadoProdutoInvalidoDeveFalhar() throws Exception {
    Exception exception =
        assertThrows(InfraException.class,
            () -> wishlistRepository
                .buscarProdutoWishlist(ConstantsTest.ID_VALIDO, ConstantsTest.ID_INVALIDO));

    assertTrue(exception.getMessage().contains(Constants.PRODUTO_NAO_EXISTE_NA_WISHLIST));

  }
}
