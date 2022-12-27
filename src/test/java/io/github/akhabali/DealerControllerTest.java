package io.github.akhabali;

import io.github.akhabali.dto.DealerDto;
import io.github.akhabali.dto.DealerListDto;
import io.github.akhabali.dto.ErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DealerControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findAllTest() {
        // Given
        // A client that wants a list of dealers

        // When
        DealerListDto response = this.restTemplate.getForObject("http://localhost:" + port + "/dealers", DealerListDto.class);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getDealers()).isNotEmpty();
        assertThat(response.getDealers().stream().map(DealerDto::getName).collect(Collectors.toSet())).contains("BMW", "AUDI", "VOLKSWAGEN");
    }

    @Test
    public void findByValidIdTest() {
        // Given
        long id = 1L;

        // When
        DealerDto response = this.restTemplate.getForObject("http://localhost:" + port + "/dealers/1", DealerDto.class);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("BMW");
    }

    @Test
    public void findByNonValidIdTest() {
        // Given
        long id = 99L;

        // When
        ErrorDto response = this.restTemplate.getForObject("http://localhost:" + port + "/dealers/" + id, ErrorDto.class);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("No dealer was found with id=99");
    }

    @Test
    public void createNewDealerTest() {
        // Given
        DealerDto renault = new DealerDto("Renault", 5L);

        // When
        DealerDto response = this.restTemplate.postForObject("http://localhost:" + port + "/dealers", renault, DealerDto.class);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo(renault.getName());
        assertThat(response.getTierLimit()).isEqualTo(renault.getTierLimit());
    }
}