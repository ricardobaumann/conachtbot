package conachtbot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

@Configuration
public class BotConfig {

    @Bean(name = "frankRestTemplate")
    RestTemplate frankRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("biggus", "dickus"));
        final DefaultUriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
        uriTemplateHandler.setBaseUrl("https://frank-ecs-production.up.welt.de");
        restTemplate.setUriTemplateHandler(uriTemplateHandler);
        return restTemplate;
    }

}
