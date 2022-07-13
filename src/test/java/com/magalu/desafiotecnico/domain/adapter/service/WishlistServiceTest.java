package com.magalu.desafiotecnico.domain.adapter.service;

import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_INVALIDO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_VALIDO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.magalu.desafiotecnico.common.provider.ProdutoProvider;
import com.magalu.desafiotecnico.common.provider.WishlistProvider;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import com.magalu.desafiotecnico.domain.ports.repository.WishlistRepositoryPort;
import com.magalu.desafiotecnico.infrastructure.exception.InfraException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
class WishlistServiceTest {

  @Mock
  private WishlistRepositoryPort wishlistRepositoryPort;

  @InjectMocks
  private WishlistService wishlistService;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    Mockito
        .doReturn(WishlistProvider.build())
        .when(wishlistRepositoryPort).buscarWishlistCliente(ID_VALIDO);

    Mockito
        .doThrow(new InfraException(null))
        .when(wishlistRepositoryPort).buscarWishlistCliente(ID_INVALIDO);

    Mockito
        .doReturn(ProdutoProvider.buildProduto(ID_VALIDO))
        .when(wishlistRepositoryPort).buscarProdutoWishlist(ID_VALIDO, ID_VALIDO);

    Mockito
        .doThrow(new InfraException(null))
        .when(wishlistRepositoryPort).buscarProdutoWishlist(ID_VALIDO, ID_INVALIDO);
  }

  @Test
  void dadoIdClienteValidoDeveListar() throws Exception {
    var retorno = wishlistService.buscarListaCompletaCliente(ID_VALIDO);
    assertThat(retorno).isEqualTo(WishlistProvider.build());
  }

  @Test
  void dadoIdClienteInvalidoDeveFalhar() {
    assertThrows(InfraException.class,
        () -> wishlistService.buscarListaCompletaCliente(ID_INVALIDO));
  }

  @Test
  void dadoIdClienteEIdProdutoValidoDeveListar() throws Exception {
    var retorno = wishlistService.buscarProdutoLista(ID_VALIDO, ID_VALIDO);
    assertThat(retorno).isEqualTo(ProdutoProvider.buildProduto(ID_VALIDO));
  }

  @Test
  void dadoIdProdutoInvalidoDeveFalhar() throws Exception {
    assertThrows(InfraException.class,
        () -> wishlistService.buscarProdutoLista(ID_VALIDO, ID_INVALIDO));
  }

  @Test
  void dadoWishlistValidaDeveCriarOuAtualizar() throws Exception {
    Wishlist wishlist = WishlistProvider.build();
    assertDoesNotThrow(() -> wishlistService.criarOuAtualizarWishlist(wishlist));
  }

  @Test
  void dadoIdClienteValidoDeveBuscarListaCompleta() throws Exception {
    var wishlist = wishlistService.buscarListaCompletaCliente(ID_VALIDO);
    assertThat(wishlist).isEqualTo(WishlistProvider.build());
  }

  @Test
  void dadoWishlistEListaDeProdutosValidaDeveInserirListaItens() throws Exception {
    assertDoesNotThrow(() ->
        wishlistService
            .inserirListaItens(
                WishlistProvider.build(),
                ProdutoProvider.buildListProduto(1, ID_VALIDO + 1)));
  }


}
