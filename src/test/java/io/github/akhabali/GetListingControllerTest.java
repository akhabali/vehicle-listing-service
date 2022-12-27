package io.github.akhabali;

import io.github.akhabali.dto.DealerListingDto;
import io.github.akhabali.model.Dealer;
import io.github.akhabali.model.Listing;
import io.github.akhabali.service.DealerService;
import io.github.akhabali.service.ListingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetListingControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ListingService listingService;
    @Autowired
    private DealerService dealerService;


    @Test
    public void GetDraftListingByDealerTest() {
        //Given
        // A dealer
        Dealer dealer = new Dealer();
        dealer.setName("Dealer1");
        dealer.setTierLimit(2L);
        dealer = dealerService.newDealer(dealer);

        // And a  dealer's listing
        int MAX_LISTING = 5;
        for (int i = 0; i < MAX_LISTING; i++) {
            Listing listing = new Listing();
            listing.setDealer(dealer);
            listing.setPrice(32000D);
            listing.setVehicle("V1");
            listing.setCreatedAt(System.currentTimeMillis());
            listingService.createListing(listing);
        }

        // When
        // dealer's listing is updated
        DealerListingDto response = this.restTemplate.getForObject("http://localhost:" + port + "/dealers/" + dealer.getId() + "/listing?state=draft", DealerListingDto.class);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getListings()).isNotEmpty();
        assertThat(response.getListings().size()).isEqualTo(MAX_LISTING);
    }

    @Test
    public void GetDraftEmptyListingByDealerTest() {
        //Given
        // A dealer
        Dealer dealer = new Dealer();
        dealer.setName("Dealer1");
        dealer.setTierLimit(2L);
        dealer = dealerService.newDealer(dealer);

        // And a  dealer's empty listing


        // When
        // dealer's listing is updated
        DealerListingDto response = this.restTemplate.getForObject("http://localhost:" + port + "/dealers/" + dealer.getId() + "/listing?state=draft", DealerListingDto.class);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getListings()).isEmpty();

    }


}
