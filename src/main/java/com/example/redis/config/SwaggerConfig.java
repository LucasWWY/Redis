//package com.example.redis.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.ArrayList;
//
//@Configuration
//@EnableOpenApi
//public class SwaggerConfig {
//
//    /**
//     * 读取yml中swagger属性是否开启
//     */
//    //@Value("${swagger.enabled}")
//    Boolean swaggerEnabled;
//
//    //@Value("${spring.profiles.active:NA}")
//    private String active;
//
//    @Bean
//    public Docket createRestApi() {
//    return new Docket(DocumentationType.SWAGGER_2)
//            .apiInfo(apiInfo())
//            .enable(swaggerEnabled) // 是否开启swagger
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.elec5619.rateyourunits.controller"))
//            .paths(PathSelectors.any()) // 不过滤任何路径
//            .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "RateYourProject API",
//                "Redis demo",
//                "1.0",
//                "",
//                new Contact("", "", ""),
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0",
//                new ArrayList<>()
//        );
//    }
//}
