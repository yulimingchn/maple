package com.dawn.eureka;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

//@Component
//@Primary
//@EnableAutoConfiguration
public class GatewaySwaggerResourceProvider implements SwaggerResourcesProvider {

    @Autowired
    private SwaggerServicesConfig swaggerServiceList;

    public GatewaySwaggerResourceProvider() {

    }

    @Override
    public List<SwaggerResource> get() {
        return swaggerServiceList.getServices().stream()
                .map(s -> swaggerResource(s.getName(), s.getUrl(), s.getVersion())).collect(Collectors.toList());
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}