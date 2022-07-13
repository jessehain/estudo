package com.magalu.desafiotecnico.api.adapter.controller;

import static com.magalu.desafiotecnico.api.constants.Constants.URI_WISHLIST;
import static com.magalu.desafiotecnico.api.constants.Constants.URI_WISHLIST_CLIENTE;
import static com.magalu.desafiotecnico.api.constants.Constants.URI_WISHLIST_CLIENTE_PRODUTO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.DESCRICAO_PRODUTO_VALIDO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_INVALIDO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_VALIDO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.NUMBER_ZERO;
import static com.magalu.desafiotecnico.domain.constants.Constants.CLIENTE_NAO_POSSUI_WISHLIST;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.magalu.desafiotecnico.api.dto.ProdutoDto;
import com.magalu.desafiotecnico.api.exception.handler.ApiExceptionHandler;
import com.magalu.desafiotecnico.common.JsonConverter;
import com.magalu.desafiotecnico.common.provider.ProdutoProvider;
import com.magalu.desafiotecnico.common.provider.WishlistDtoInputProvider;
import com.magalu.desafiotecnico.common.provider.WishlistDtoOutputProvider;
import com.magalu.desafiotecnico.common.provider.WishlistProvider;
import com.magalu.desafiotecnico.domain.exception.DomainException;
import com.magalu.desafiotecnico.domain.ports.interfaces.WishlistServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
class WishlistControllerTest {

  private MockMvc mockMvc;

  @Autowired
  ModelMapper modelMapper;

  @Mock
  private WishlistServicePort wishlistServicePort;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    WishlistController wishlistController = new WishlistController(this.wishlistServicePort,
        this.modelMapper);

    this.mockMvc = MockMvcBuilders
        .standaloneSetup(wishlistController)
        .setControllerAdvice(new ApiExceptionHandler())
        .build();

    Mockito
        .doReturn(WishlistProvider.build())
        .when(wishlistServicePort).buscarListaCompletaCliente(ID_VALIDO);

    Mockito
        .doReturn(ProdutoProvider.buildProduto(ID_VALIDO))
        .when(wishlistServicePort).buscarProdutoLista(ID_VALIDO, ID_VALIDO);

    Mockito
        .doThrow(new DomainException(CLIENTE_NAO_POSSUI_WISHLIST))
        .when(wishlistServicePort).buscarListaCompletaCliente(ID_INVALIDO);

    Mockito
        .doThrow(new DomainException(CLIENTE_NAO_POSSUI_WISHLIST))
        .when(wishlistServicePort).excluirLista(ID_INVALIDO);

  }

  @Test
  void dadoWishlistDadosValidosPutDeveCriar() throws Exception {

    var validRequest = WishlistDtoInputProvider.buildRequisicaoValida();
    String request = JsonConverter.toJson(validRequest);

    mockMvc.perform(put(URI_WISHLIST)
        .contentType(APPLICATION_JSON_VALUE)
        .content(request))
        .andExpect(status().isCreated());
  }

  @Test
  void dadoWishlistComClienteInvalidoPutDeveFalhar() throws Exception {

    var invalidRequest = WishlistDtoInputProvider.buildRequisicaoClienteInvalido();
    String request = JsonConverter.toJson(invalidRequest);

    mockMvc.perform(put(URI_WISHLIST)
        .contentType(APPLICATION_JSON_VALUE)
        .content(request))
        .andExpect(status().isBadRequest());
  }

  @Test
  void dadoWishlistComProdutoInvalidoPutDeveFalhar() throws Exception {

    var validRequest = WishlistDtoInputProvider.buildRequisicaoProdutoInvalido();
    String request = JsonConverter.toJson(validRequest);

    mockMvc.perform(put(URI_WISHLIST)
        .contentType(APPLICATION_JSON_VALUE)
        .content(request))
        .andExpect(status().isBadRequest());
  }

  @Test
  void dadoClienteEProdutoValidosPutDeveInserirProduto() throws Exception {

    ProdutoDto produtoDto = new ProdutoDto();
    produtoDto.setId(ID_VALIDO);
    produtoDto.setDescricao(DESCRICAO_PRODUTO_VALIDO);
    String request = JsonConverter.toJson(produtoDto);

    mockMvc.perform(put(URI_WISHLIST + URI_WISHLIST_CLIENTE, ID_VALIDO)
        .contentType(APPLICATION_JSON_VALUE)
        .content(request))
        .andExpect(status().isCreated());
  }

  @Test
  void dadoClienteEProdutoZeroPutDeveFalhar() throws Exception {

    ProdutoDto produtoDto = new ProdutoDto();
    produtoDto.setId(0);
    produtoDto.setDescricao(DESCRICAO_PRODUTO_VALIDO);
    String request = JsonConverter.toJson(produtoDto);

    mockMvc.perform(put(URI_WISHLIST + URI_WISHLIST_CLIENTE, 0)
        .contentType(APPLICATION_JSON_VALUE)
        .content(request))
        .andExpect(status().isBadRequest());
  }

  @Test
  void dadoClienteEProdutoValidosGetDeveListarProduto() throws Exception {

    ProdutoDto produtoDto = new ProdutoDto();
    produtoDto.setId(ProdutoProvider.buildProduto(ID_VALIDO).getId());
    produtoDto.setDescricao(ProdutoProvider.buildProduto(ID_VALIDO).getDescricao());

    mockMvc.perform(get(URI_WISHLIST + URI_WISHLIST_CLIENTE_PRODUTO, ID_VALIDO, ID_VALIDO))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is((int) produtoDto.getId())))
        .andExpect(jsonPath("$.descricao", is(produtoDto.getDescricao())));
  }

  @Test
  void dadoClienteEProdutoInvalidosGetDeveFalhar() throws Exception {

    mockMvc.perform(get(URI_WISHLIST + URI_WISHLIST_CLIENTE_PRODUTO, 0, 0))
        .andExpect(status().isBadRequest());
  }

  @Test
  void dadoClienteComWishlistDeveListar() throws Exception {
    var output = WishlistDtoOutputProvider.build();

    mockMvc.perform(get(URI_WISHLIST + URI_WISHLIST_CLIENTE, ID_VALIDO))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is((int) output.getId())))
        .andExpect(jsonPath("$.cliente.id", is((int) output.getCliente().getId())))
        .andExpect(jsonPath("$.cliente.nome", is(output.getCliente().getNome())))
        .andExpect(jsonPath("$.produtos.[0].id", is((int) output.getProdutos().get(0).getId())))
        .andExpect(
            jsonPath("$.produtos.[0].descricao", is(output.getProdutos().get(0).getDescricao())));
  }

  @Test
  void dadoClienteInexistenteGetDeveFalhar() throws Exception {
    mockMvc.perform(get(URI_WISHLIST + URI_WISHLIST_CLIENTE, ID_INVALIDO))
        .andExpect(status().isBadRequest());
  }

  @Test
  void dadoClienteIdZeroGetDeveFalhar() throws Exception {

    mockMvc.perform(get(URI_WISHLIST + URI_WISHLIST_CLIENTE, NUMBER_ZERO))
        .andExpect(status().isBadRequest());
  }

  @Test
  void dadoClienteValidoDeleteDeveExcluir() throws Exception {

    mockMvc.perform(delete(URI_WISHLIST + URI_WISHLIST_CLIENTE, ID_VALIDO))
        .andExpect(status().isNoContent());
  }

  @Test
  void dadoClienteInexistenteDeleteDeveFalhar() throws Exception {

    mockMvc.perform(delete(URI_WISHLIST + URI_WISHLIST_CLIENTE, ID_INVALIDO))
        .andExpect(status().isBadRequest());
  }

  @Test
  void dadoClienteComIdZeroDeleteDeveFalhar() throws Exception {

    mockMvc.perform(delete(URI_WISHLIST + URI_WISHLIST_CLIENTE, NUMBER_ZERO))
        .andExpect(status().isBadRequest());
  }
}
