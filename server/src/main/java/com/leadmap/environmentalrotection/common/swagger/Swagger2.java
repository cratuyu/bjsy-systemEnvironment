package com.leadmap.environmentalrotection.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/17 10:00
 */

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.leadmap.environmentalrotection.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("顺义环保服务端api文档")
                .description("顺义环保服务端api文档")
                .termsOfServiceUrl("http://183.129.204.238:6600/swagger-ui.html#/air-controller")
                .version("1.0")
                .build();
    }
}

//@Configuration
//@EnableSwagger2
//public class Swagger2 extends WebMvcConfigurerAdapter {
//    /**
//     * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
//     *  <!--swagger资源配置-->
//     *  <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
//     *  <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
//     *  不知道为什么，这也是spring boot的一个缺点（菜鸟觉得的）
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars*")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//    //可以注入多个doket，也就是多个版本的api，可以在看到有三个版本groupName不能是重复的，v1和v2是ant风格匹配，配置文件
//    @Bean
//    public Docket api() {
//        //可以添加多个header或参数
//        ParameterBuilder aParameterBuilder = new ParameterBuilder();
//        aParameterBuilder
//                .parameterType("header") //参数类型支持header, cookie, body, query etc
//                .name("token") //参数名
//                .defaultValue("token") //默认值
//                .description("header中token字段测试")
//                .modelRef(new ModelRef("string"))//指定参数值的类型
//                .required(false).build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
//        List<Parameter> aParameters = new ArrayList<Parameter>();
//        aParameters.add(aParameterBuilder.build());
//        return new Docket(DocumentationType.SWAGGER_2).groupName("v1").select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/api/v1/**")).build().apiInfo(apiInfo1()).globalOperationParameters(aParameters);
//    }
//
//
//    private ApiInfo apiInfo1() {
//        return new ApiInfoBuilder()
//                .title("exampleApi 0.01")
//                .termsOfServiceUrl("www.example.com")
//                .contact(new Contact("liumei","http://blog.csdn.net/pc_gad","hilin2333@gmail.com"))
//                .version("v0.01")
//                .build();
//    }
//}
