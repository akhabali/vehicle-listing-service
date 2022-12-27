package io.github.akhabali;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class TestBeansConfiguration {

    @Bean
    public TestRestTemplate testRestTemplate() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        // This is needed to perform HTTP PATCH requests
        // as (default) HttpURLConnection doesn't support it
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return testRestTemplate;
    }
}
