package com.magalu.desafiotecnico.api.dto.input;

import com.magalu.desafiotecnico.api.dto.ClienteDto;
import com.magalu.desafiotecnico.api.dto.ProdutoDto;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class WishlistDtoInput {

  @Valid
  @NotNull
  private ClienteDto cliente;

  @Valid
  @NotNull
  private List<ProdutoDto> produtos;

  public Wishlist toWishlist(ModelMapper modelMapper) {
    return modelMapper.map(this, Wishlist.class);
  }

}
