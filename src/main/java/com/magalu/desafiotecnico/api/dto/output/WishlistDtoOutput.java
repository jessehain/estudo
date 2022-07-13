package com.magalu.desafiotecnico.api.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.magalu.desafiotecnico.api.dto.ClienteDto;
import com.magalu.desafiotecnico.api.dto.ProdutoDto;
import com.magalu.desafiotecnico.domain.model.Wishlist;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class WishlistDtoOutput {

  private long id;
  private ClienteDto cliente;
  private List<ProdutoDto> produtos;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dataAtualizacao;

  public static WishlistDtoOutput fromWishlist(Wishlist wishlist, ModelMapper modelMapper) {
    return modelMapper.map(wishlist, WishlistDtoOutput.class);
  }

}
