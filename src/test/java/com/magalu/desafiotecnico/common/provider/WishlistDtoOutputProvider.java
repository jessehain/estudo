package com.magalu.desafiotecnico.common.provider;

import static com.magalu.desafiotecnico.common.constants.ConstantsTest.DATA_ATUALIZACAO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_VALIDO;

import com.magalu.desafiotecnico.api.dto.ClienteDto;
import com.magalu.desafiotecnico.api.dto.ProdutoDto;
import com.magalu.desafiotecnico.api.dto.output.WishlistDtoOutput;
import java.util.ArrayList;
import java.util.List;

public class WishlistDtoOutputProvider {

  public static WishlistDtoOutput build() {

    ClienteDto cliente = WishlistDtoInputProvider.buildClienteDto();
    ProdutoDto produto = WishlistDtoInputProvider.buildProdutoDto();

    List<ProdutoDto> produtos = new ArrayList<>();
    produtos.add(produto);

    WishlistDtoOutput wishlistDtoOutput = new WishlistDtoOutput();
    wishlistDtoOutput.setId(ID_VALIDO);
    wishlistDtoOutput.setCliente(cliente);
    wishlistDtoOutput.setProdutos(produtos);
    wishlistDtoOutput.setDataAtualizacao(DATA_ATUALIZACAO);

    return wishlistDtoOutput;

  }

}
