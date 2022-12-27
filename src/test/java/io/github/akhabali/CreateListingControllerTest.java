package io.github.akhabali;

import io.github.akhabali.dto.CreateListingDto;
import io.github.akhabali.dto.UpdateListingDto;
import io.github.akhabali.dto.ErrorDto;
import io.github.akhabali.dto.GetListingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static io.github.akhabali.model.ListingState.draft;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateListingControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void createListingValidTest() {
        //Given
        Long dealerId = 1L;
        CreateListingDto newListing = new CreateListingDto("serie 1", 32000D);

        //When
        GetListingDto response = this.restTemplate.postForObject("http://localhost:" + port + "/dealers/" + dealerId + "/listing", newListing, GetListingDto.class);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getDealerId()).isEqualTo(dealerId);
        assertThat(response.getState()).isEqualTo(draft);
    }

    @Test
    public void createListingWithWrongDealerTest() {
        //Given
        Long dealerId = 99L;
        CreateListingDto newListing = new CreateListingDto("serie 1", 32000D);

        //When
        ErrorDto response = this.restTemplate.postForObject("http://localhost:" + port + "/dealers/" + dealerId + "/listing", newListing, ErrorDto.class);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotEmpty();
        assertThat(response.getMessage()).contains("No dealer was found with id=" + dealerId);
    }

}
