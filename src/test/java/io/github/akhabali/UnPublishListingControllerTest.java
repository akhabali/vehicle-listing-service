package io.github.akhabali;

import io.github.akhabali.dto.ErrorDto;
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

import java.util.ArrayList;
import java.util.List;

import static io.github.akhabali.model.ListingState.draft;
import static io.github.akhabali.model.ListingState.published;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnPublishListingControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ListingService listingService;
    @Autowired
    private DealerService dealerService;


    @Test
    public void unpublishListingTest() {
        //Given
        // A dealer
        Dealer dealer = new Dealer();
        dealer.setName("Dealer1");
        dealer.setTierLimit(2L);
        dealer = dealerService.newDealer(dealer);

        // And a  dealer's listing
        long MAX_LISTING = dealer.getTierLimit() + 3;
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < MAX_LISTING; i++) {
            Listing listing = new Listing();
            listing.setDealer(dealer);
            listing.setPrice((double) i);
            listing.setVehicle("V" + i);
            listing.setCreatedAt(System.currentTimeMillis());
            listing = listingService.createListing(listing);
            listingService.publishListing(listing.getId(), true);
            ids.add(listing.getId());
        }


        // When
        // unpublish all
        for (Long id : ids) {
            this.restTemplate.delete("http://localhost:" + port + "/listing/" + id);
        }

        // Then
        long draftCount = listingService.findListingByDealerId(dealer.getId(), draft).size();
        assertThat(draftCount).isEqualTo(MAX_LISTING);
    }


}
