package gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
  /*  @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
       return builder.routes()
        .route(p -> p
            .path("/get")
            .filters(f -> f.addRequestHeader("Hello", "World"))
            .uri("http://httpbin.org:80"))
        .build();
    }*/
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        RouteLocator v = builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                .route(p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("mycmd22")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
        return  v;

    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
    }