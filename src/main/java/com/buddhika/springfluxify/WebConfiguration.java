package com.buddhika.springfluxify;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.BodyInserters.fromServerSentEvents;

@Configuration
public class WebConfiguration {
    private final ProductRepository productRepository;

    public WebConfiguration(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Bean
    public RouterFunction<?> routes() {
        return RouterFunctions.route()
                .GET("/product/{id}", request -> {
                    Long id = Long.parseLong(request.pathVariable("id"));
                    return ServerResponse.ok().body(this.productRepository.findById(id), Product.class);
                })
                .GET("/products", request -> ServerResponse.ok().body(this.productRepository.findAll(),
                        Product.class))
                .GET("/products/events", request -> {
                    Flux<ServerSentEvent<Mono<Product>>> stream =
                            Flux.interval(Duration.ofSeconds(2))
                                    .map(i -> this.productRepository.findById((i % 10) + 1))
                                    .map(data -> ServerSentEvent.builder(data).build());
                    return ServerResponse.ok().body(fromServerSentEvents(stream));
                })
                .build();
    }
}
