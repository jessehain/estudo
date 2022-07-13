package com.magalu.desafiotecnico.common.provider;

import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_VALIDO;

import com.magalu.desafiotecnico.api.dto.ClienteDto;
import com.magalu.desafiotecnico.api.dto.ProdutoDto;
import com.magalu.desafiotecnico.api.dto.input.WishlistDtoInput;
import java.util.ArrayList;
import java.util.List;

public class WishlistDtoInputProvider {

  public static WishlistDtoInput buildRequisicaoValida() {

    ClienteDto clienteDto = buildClienteDto();
    ProdutoDto produtoDto = buildProdutoDto();

    List<ProdutoDto> produtos = new ArrayList<>();
    produtos.add(produtoDto);

    WishlistDtoInput wishlistDtoInput = new WishlistDtoInput();
    wishlistDtoInput.setCliente(clienteDto);
    wishlistDtoInput.setProdutos(produtos);

    return wishlistDtoInput;
  }

  public static ClienteDto buildClienteDto() {
    ClienteDto clienteDto = new ClienteDto();
    clienteDto.setId(ClienteProvider.valido().getId());
    clienteDto.setNome(ClienteProvider.valido().getNome());
    return clienteDto;
  }

  public static ProdutoDto buildProdutoDto() {
    ProdutoDto produtoDto = new ProdutoDto();
    produtoDto.setId(ProdutoProvider.buildProduto(ID_VALIDO).getId());
    produtoDto.setDescricao(ProdutoProvider.buildProduto(ID_VALIDO).getDescricao());
    return produtoDto;
  }

  public static WishlistDtoInput buildRequisicaoClienteInvalido() {

    var requisicao = buildRequisicaoValida();
    requisicao.setCliente(null);
    return requisicao;
  }

  public static WishlistDtoInput buildRequisicaoProdutoInvalido() {

    var requisicao = buildRequisicaoValida();
    requisicao.setProdutos(null);
    return requisicao;
  }

}
