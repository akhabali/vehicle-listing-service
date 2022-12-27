package io.github.akhabali;

import io.github.akhabali.dto.UpdateListingDto;
import io.github.akhabali.dto.GetListingDto;
import io.github.akhabali.model.Dealer;
import io.github.akhabali.model.Listing;
import io.github.akhabali.service.DealerService;
import io.github.akhabali.service.ListingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static io.github.akhabali.model.ListingState.draft;
import static io.github.akhabali.model.ListingState.published;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateListingControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ListingService listingService;
    @Autowired
    private DealerService dealerService;


    @Test
    public void updateListingValidTest() {
        //Given
        // A dealer
        Dealer dealer = new Dealer();
        dealer.setName("Dealer1");
        dealer.setTierLimit(2L);
        dealer = dealerService.newDealer(dealer);

        // And a  dealer's listing
        Listing listing = new Listing();
        listing.setDealer(dealer);
        listing.setPrice(32000D);
        listing.setVehicle("V1");
        listing.setCreatedAt(System.currentTimeMillis());
        listing = listingService.createListing(listing);


        // When
        // dealer's listing is updated
        UpdateListingDto listingDto = new UpdateListingDto(listing.getId(), listing.getVehicle(), listing.getPrice());
        listingDto.setPrice(30000D);
        listingDto.setVehicle("V2");

        GetListingDto response = this.restTemplate.patchForObject("http://localhost:" + port + "/dealers/" + dealer.getId() + "/listing", listingDto, GetListingDto.class);


        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getDealerId()).isEqualTo(dealer.getId());
        assertThat(response.getState()).isEqualTo(draft);
        assertThat(response.getPrice()).isEqualTo(listingDto.getPrice());
        assertThat(response.getVehicle()).isEqualTo(listingDto.getVehicle());
    }


}
