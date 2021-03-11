package site.minnan.entry.infrastructure.config;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public Docket commonDocket() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("用户接口")
                .description("用户接口相关描述")
                .contact(new Contact("Minnan", "", "minnanr475@outlook.com"))
                .version("1.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("site.minnan.entry.userinterface.fascade"))
                .paths(PathSelectors.any())
                .build();
    }
}
