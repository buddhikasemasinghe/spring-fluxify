package com.buddhika.springfluxify;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringFluxifyApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringFluxifyApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ProductRepository productRepository){
		return args -> {
				Flux<Product> cars = Flux.just(
						new Product(1L, "Toyota","2001", "Rav4", new BigDecimal(900000)),
						new Product(2L, "Toyota","2015", "Camry", new BigDecimal(448282)),
						new Product(3L, "Honda","1999", "HRV", new BigDecimal(4858272)),
						new Product(4L, "Honda","2017", "CRV", new BigDecimal(3848283)),
						new Product(5L, "Nissan","2005", "Patrol", new BigDecimal(4828283)),
						new Product(6L, "Nissan","2018", "XTrail", new BigDecimal(48282938)),
						new Product(7L, "Mazda","1983", "CX5", new BigDecimal(3883828)),
						new Product(8L, "Mazda","1016", "Mazda3", new BigDecimal(5909999))
				);
				productRepository.deleteAll()
						.thenMany(productRepository.saveAll(cars))
						.blockLast();
		};
	}
}
