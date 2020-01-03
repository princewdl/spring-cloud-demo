package gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/customer/**")
                        .filters(f -> f.filter(new RequestTimeFilter())
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("http://httpbin.org:80/get")
                        .order(0)
                        .id("customer_filter_router")
                )
                .build();

    }




    @Bean
    public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }

        @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }

   /* 本篇文章讲述了Spring Cloud Gateway中的过滤器，包括GatewayFilter和GlobalFilter。
    从官方文档的内置过滤器讲起，然后讲解自定义GatewayFilter、GatewayFilterFactory以及自定义的GlobalFilter。
    有很多内置的过滤器并没有讲述到，比如限流过滤器，这个我觉得是比较重要和大家关注的过滤器，将在之后的文章讲述。*/

}