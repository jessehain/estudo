package com.magalu.desafiotecnico.api.constants;

public class Constants {

  private Constants() {
  }

  public static final String DESCRICAO_API = "API desafio tecnico LuizaLabs";
  public static final String LISTAR_WISHLIST_CLIENTE = "Listar a Wishlist de um cliente";
  public static final String LISTAR_PRODUTO_WISHLIST_CLIENTE = "Listar a Wishlist de um cliente";
  public static final String EXCLUIR_WISHLIST_CLIENTE = "Excluir a Wishlist de um cliente";
  public static final String EXCLUIR_PRODUTO_WISHLIST_CLIENTE = "Excluir um produto da Wishlist de um cliente";
  public static final String CRIAR_OU_ATUALIZAR_WISHLIST = "Criar ou atualizar Wishlist de um cliente";
  public static final String ADICIONAR_PRODUTO_WISHLIST = "Adicionar produto na Wishlist de um cliente";
  public static final String PARAMETROS_INVALIDOS = "Parametro(s) inválidos.";
  public static final String URI_WISHLIST = "/wishlist";
  public static final String URI_WISHLIST_CLIENTE = "/cliente/{idCliente}";
  public static final String URI_WISHLIST_CLIENTE_PRODUTO = "/cliente/{idCliente}/produto/{idProduto}";

  public static final int STATUS_NO_CONTENT = 204;
  public static final int STATUS_OK = 200;
  public static final int STATUS_CREATED = 201;
  public static final int STATUS_BAD_REQUEST = 400;
  public static final int STATUS_INTERNAL = 500;
  public static final String MESSAGE_NO_CONTENT = "Ok. Sem conteúdo";
  public static final String MESSAGE_OK = "Ok.";
  public static final String MESSAGE_CREATED = "Wishlist Atualizada";
  public static final String MESSAGE_BAD_REQUEST = "Erro na requisição";
  public static final String MESSAGE_INTERNAL = "Erro interno";

}
