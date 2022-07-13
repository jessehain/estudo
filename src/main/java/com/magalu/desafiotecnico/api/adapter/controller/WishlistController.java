package com.magalu.desafiotecnico.api.adapter.controller;

import static com.magalu.desafiotecnico.api.constants.Constants.MESSAGE_BAD_REQUEST;
import static com.magalu.desafiotecnico.api.constants.Constants.MESSAGE_CREATED;
import static com.magalu.desafiotecnico.api.constants.Constants.MESSAGE_INTERNAL;
import static com.magalu.desafiotecnico.api.constants.Constants.MESSAGE_NO_CONTENT;
import static com.magalu.desafiotecnico.api.constants.Constants.MESSAGE_OK;
import static com.magalu.desafiotecnico.api.constants.Constants.STATUS_BAD_REQUEST;
import static com.magalu.desafiotecnico.api.constants.Constants.STATUS_CREATED;
import static com.magalu.desafiotecnico.api.constants.Constants.STATUS_INTERNAL;
import static com.magalu.desafiotecnico.api.constants.Constants.STATUS_NO_CONTENT;
import static com.magalu.desafiotecnico.api.constants.Constants.STATUS_OK;
import static com.magalu.desafiotecnico.api.constants.Constants.URI_WISHLIST;

import com.magalu.desafiotecnico.api.constants.Constants;
import com.magalu.desafiotecnico.api.dto.ProdutoDto;
import com.magalu.desafiotecnico.api.dto.input.WishlistDtoInput;
import com.magalu.desafiotecnico.api.dto.output.WishlistDtoOutput;
import com.magalu.desafiotecnico.api.exception.ApiException;
import com.magalu.desafiotecnico.domain.model.Produto;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import com.magalu.desafiotecnico.domain.ports.interfaces.WishlistServicePort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = Constants.DESCRICAO_API)
@RestController
@RequestMapping(value = URI_WISHLIST)
@AllArgsConstructor
public class WishlistController {

  private WishlistServicePort wishlistService;
  private ModelMapper modelMapper;

  @ApiOperation(Constants.CRIAR_OU_ATUALIZAR_WISHLIST)
  @ApiResponses(value = {
      @ApiResponse(code = STATUS_CREATED, message = MESSAGE_CREATED),
      @ApiResponse(code = STATUS_BAD_REQUEST, message = MESSAGE_BAD_REQUEST),
      @ApiResponse(code = STATUS_INTERNAL, message = MESSAGE_INTERNAL)}
  )
  @PutMapping
  public ResponseEntity<Wishlist> criarOuAlterarWishlist(
      @Valid @RequestBody WishlistDtoInput input) {
    Wishlist wishlist = input.toWishlist(modelMapper);
    wishlistService.criarOuAtualizarWishlist(wishlist);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @ApiOperation(Constants.ADICIONAR_PRODUTO_WISHLIST)
  @ApiResponses(value = {
      @ApiResponse(code = STATUS_CREATED, message = MESSAGE_CREATED),
      @ApiResponse(code = STATUS_BAD_REQUEST, message = MESSAGE_BAD_REQUEST),
      @ApiResponse(code = STATUS_INTERNAL, message = MESSAGE_INTERNAL)}
  )
  @PutMapping(Constants.URI_WISHLIST_CLIENTE)
  public ResponseEntity<Wishlist> inserirProdutoNaWishlist(@PathVariable long idCliente,
      @Valid @RequestBody ProdutoDto input) {
    if (idCliente == 0) {
      throw new ApiException(Constants.PARAMETROS_INVALIDOS);
    }
    Produto produto = modelMapper.map(input, Produto.class);
    wishlistService.inserirItemLista(idCliente, produto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @ApiOperation(Constants.LISTAR_WISHLIST_CLIENTE)
  @ApiResponses(value = {
      @ApiResponse(code = STATUS_OK, message = MESSAGE_OK),
      @ApiResponse(code = STATUS_BAD_REQUEST, message = MESSAGE_BAD_REQUEST),
      @ApiResponse(code = STATUS_INTERNAL, message = MESSAGE_INTERNAL)}
  )
  @GetMapping(Constants.URI_WISHLIST_CLIENTE)
  public ResponseEntity<WishlistDtoOutput> listarWishlist(@PathVariable long idCliente) {
    if (idCliente == 0) {
      throw new ApiException(Constants.PARAMETROS_INVALIDOS);
    }
    var wishlist = wishlistService.buscarListaCompletaCliente(idCliente);
    var wislistOutput = WishlistDtoOutput.fromWishlist(wishlist, modelMapper);
    return ResponseEntity.ok().body(wislistOutput);
  }

  @ApiOperation(Constants.LISTAR_PRODUTO_WISHLIST_CLIENTE)
  @ApiResponses(value = {
      @ApiResponse(code = STATUS_OK, message = MESSAGE_OK),
      @ApiResponse(code = STATUS_BAD_REQUEST, message = MESSAGE_BAD_REQUEST),
      @ApiResponse(code = STATUS_INTERNAL, message = MESSAGE_INTERNAL)}
  )
  @GetMapping(Constants.URI_WISHLIST_CLIENTE_PRODUTO)
  public ResponseEntity<ProdutoDto> listarProdutoWishlistCliente(@PathVariable long idCliente,
      @PathVariable long idProduto) {
    if (idCliente == 0 || idProduto == 0) {
      throw new ApiException(Constants.PARAMETROS_INVALIDOS);
    }
    var produto = wishlistService.buscarProdutoLista(idCliente, idProduto);
    var produtoOutput = modelMapper.map(produto, ProdutoDto.class);
    return ResponseEntity.ok().body(produtoOutput);
  }

  @ApiOperation(Constants.EXCLUIR_WISHLIST_CLIENTE)
  @ApiResponses(value = {
      @ApiResponse(code = STATUS_NO_CONTENT, message = MESSAGE_NO_CONTENT),
      @ApiResponse(code = STATUS_BAD_REQUEST, message = MESSAGE_BAD_REQUEST),
      @ApiResponse(code = STATUS_INTERNAL, message = MESSAGE_INTERNAL)}
  )
  @DeleteMapping(Constants.URI_WISHLIST_CLIENTE)
  public ResponseEntity<Wishlist> excluirWishlist(@PathVariable long idCliente) {
    if (idCliente == 0) {
      throw new ApiException(Constants.PARAMETROS_INVALIDOS);
    }
    wishlistService.excluirLista(idCliente);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation(Constants.EXCLUIR_PRODUTO_WISHLIST_CLIENTE)
  @ApiResponses(value = {
      @ApiResponse(code = STATUS_NO_CONTENT, message = MESSAGE_NO_CONTENT),
      @ApiResponse(code = STATUS_BAD_REQUEST, message = MESSAGE_BAD_REQUEST),
      @ApiResponse(code = STATUS_INTERNAL, message = MESSAGE_INTERNAL)}
  )
  @DeleteMapping(Constants.URI_WISHLIST_CLIENTE_PRODUTO)
  public ResponseEntity<Wishlist> excluirProdutoWishlist(@PathVariable long idCliente,
      @PathVariable long idProduto) {
    if (idCliente == 0) {
      throw new ApiException(Constants.PARAMETROS_INVALIDOS);
    }
    wishlistService.excluirItemLista(idCliente, idProduto);
    return ResponseEntity.noContent().build();
  }


}
