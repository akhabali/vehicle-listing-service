package io.github.akhabali;

import io.github.akhabali.dto.ErrorDto;
import io.github.akhabali.dto.ListingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static io.github.akhabali.model.ListingState.draft;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListingControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void createListingValidTest() {
        //Given
        Long dealerId = 1L;
        ListingDto newListing = new ListingDto(dealerId, "serie 1", 32000D);

        //When
        ListingDto response = this.restTemplate.postForObject("http://localhost:" + port + "/dealers/" + dealerId + "/listing", newListing, ListingDto.class);

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
        ListingDto newListing = new ListingDto(dealerId, "serie 1", 32000D);

        //When
        ErrorDto response = this.restTemplate.postForObject("http://localhost:" + port + "/dealers/" + dealerId + "/listing", newListing, ErrorDto.class);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotEmpty();
        assertThat(response.getMessage()).contains("No dealer was found with id="+dealerId);
    }

    @Test
    public void createListingWithIdTest() {
        //Given
        Long dealerId = 1L;
        ListingDto newListing = new ListingDto(dealerId, "serie 1", 32000D);
        newListing.setId(1L);

        //When
        ErrorDto response = this.restTemplate.postForObject("http://localhost:" + port + "/dealers/" + dealerId + "/listing", newListing, ErrorDto.class);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotEmpty();
        assertThat(response.getMessage()).contains("Dealer's listing already exists with id="+dealerId);
    }
}
