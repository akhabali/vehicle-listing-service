package io.github.akhabali;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.HashMap;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DealerControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void allTest() {
        CollectionModel<?> response = this.restTemplate.getForObject("http://localhost:" + port + "/dealers", CollectionModel.class);
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getContent().size()).isEqualTo(3);
        assertThat(response.getContent().stream().map(e -> (String) ((HashMap<?, ?>) e).get("name")).collect(Collectors.toSet())).contains("BMW", "AUDI", "VOLKSWAGEN");
    }

    @Test
    public void oneByValidIdTest() {
        EntityModel<?> response = this.restTemplate.getForObject("http://localhost:" + port + "/dealers/1", EntityModel.class);
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isNotNull();
        assertThat(((HashMap<?, ?>) response.getContent()).get("name")).isEqualTo("BMW");
    }

    @Test
    public void oneByNonValidIdTest() {
        EntityModel<?> response = this.restTemplate.getForObject("http://localhost:" + port + "/dealers/99", EntityModel.class);
        assertThat(response).isNotNull();
        assertThat(((HashMap<?, ?>) response.getContent()).get("error")).isEqualTo("No dealer was found with id=99");
    }
}