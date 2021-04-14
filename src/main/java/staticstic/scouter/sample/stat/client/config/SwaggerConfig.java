package staticstic.scouter.sample.stat.client.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import staticstic.scouter.sample.stat.client.components.JWTCmd;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "test", havingValue = "true", matchIfMissing = false)
public class SwaggerConfig {

	@Autowired
    JWTCmd jwtCmd;
	
	@Autowired
    ResourcesManager mResourcesManager;
	
    @Bean
    public Docket api() {
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
    	Parameter p1 = aParameterBuilder.name("Authorization") //헤더 이름
                .modelRef(new ModelRef("String"))
                .parameterType("header") 
                .defaultValue("Bearer "+jwtCmd.getToken(mResourcesManager.getJwtTokenKey(), "agent1", 365 * 24 * 60 * 60 * 1000))
                .required(true)
                .build();

        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(p1);

    	Docket api = new Docket(DocumentationType.SWAGGER_2)
    			.ignoredParameterTypes(RedirectAttributes.class)
                .select()
                .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
                .paths(PathSelectors.ant("/api/**")) // 그중 /api/** 인 URL들만 필터링
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false) // default response description 사용하지 않음
                .globalOperationParameters(aParameters)
                ;
    	
    	return api;
    }
    
    private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API")
				.description("Portfolio API")
				.license("조성민")
				.version("v1")
				.build();
    }
}