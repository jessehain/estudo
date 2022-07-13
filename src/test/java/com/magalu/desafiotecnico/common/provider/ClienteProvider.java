package com.magalu.desafiotecnico.common.provider;

import static com.magalu.desafiotecnico.common.constants.ConstantsTest.ID_VALIDO;
import static com.magalu.desafiotecnico.common.constants.ConstantsTest.NOME_VALIDO;

import com.magalu.desafiotecnico.domain.model.Cliente;

public class ClienteProvider {

  public static Cliente valido(){
    Cliente cliente = new Cliente();
    cliente.setId(ID_VALIDO);
    cliente.setNome(NOME_VALIDO);
    return cliente;
  }

}
