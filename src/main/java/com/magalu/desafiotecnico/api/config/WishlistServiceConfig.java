package com.magalu.desafiotecnico.api.config;

import com.magalu.desafiotecnico.domain.adapter.service.WishlistService;
import com.magalu.desafiotecnico.domain.ports.interfaces.WishlistServicePort;
import com.magalu.desafiotecnico.infrastructure.repository.WishlistRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WishlistServiceConfig {

  @Bean
  WishlistServicePort wishlistServicePort(WishlistRepository wishlistRepository) {
    return new WishlistService(wishlistRepository);
  }
}
