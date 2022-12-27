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
public class PublishListingControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ListingService listingService;
    @Autowired
    private DealerService dealerService;


    @Test
    public void publishListingValidTest() {
        //Given
        // A dealer
        Dealer dealer = new Dealer();
        dealer.setName("Dealer1");
        dealer.setTierLimit(2L);
        dealer = dealerService.newDealer(dealer);

        // And a  dealer's listing
        long MAX_LISTING = dealer.getTierLimit();
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < MAX_LISTING; i++) {
            Listing listing = new Listing();
            listing.setDealer(dealer);
            listing.setPrice((double) i);
            listing.setVehicle("V" + i);
            listing.setCreatedAt(System.currentTimeMillis());
            listing = listingService.createListing(listing);
            ids.add(listing.getId());
        }


        // When
        for (Long id : ids) {
            GetListingDto response = this.restTemplate.patchForObject("http://localhost:" + port + "/listing/" + id, null, GetListingDto.class);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getId()).isNotNull();
            assertThat(response.getDealerId()).isEqualTo(dealer.getId());
            assertThat(response.getState()).isEqualTo(published);
        }
    }

    @Test
    public void publishListingAndExceedLimitTest() {
        //Given
        // A dealer
        Dealer dealer = new Dealer();
        dealer.setName("Dealer1");
        dealer.setTierLimit(2L);
        dealer = dealerService.newDealer(dealer);

        // And a  dealer's listing
        long MAX_LISTING = dealer.getTierLimit() + 1;
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < MAX_LISTING; i++) {
            Listing listing = new Listing();
            listing.setDealer(dealer);
            listing.setPrice((double) i);
            listing.setVehicle("V" + i);
            listing.setCreatedAt(System.currentTimeMillis());
            listing = listingService.createListing(listing);
            ids.add(listing.getId());
        }


        // When
        // publishing in the limit range
        for (int i = 0; i < dealer.getTierLimit(); i++) {
            GetListingDto response = this.restTemplate.patchForObject("http://localhost:" + port + "/listing/" + ids.get(i), null, GetListingDto.class);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getId()).isNotNull();
            assertThat(response.getDealerId()).isEqualTo(dealer.getId());
            assertThat(response.getState()).isEqualTo(published);
        }

        //And When publishing outside the limit range
        for (int i = Math.toIntExact(dealer.getTierLimit()); i < MAX_LISTING; i++) {
            ErrorDto response = this.restTemplate.patchForObject("http://localhost:" + port + "/listing/" + ids.get(i), null, ErrorDto.class);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isNotEmpty();
            assertThat(response.getMessage()).contains("Tier limit exceeded for dealer id");
        }
    }

    @Test
    public void forcePublishListingWhenExceedLimitTest() {
        //Given
        // A dealer
        Dealer dealer = new Dealer();
        dealer.setName("Dealer1");
        dealer.setTierLimit(2L);
        dealer = dealerService.newDealer(dealer);

        // And a  dealer's listing
        long MAX_LISTING = dealer.getTierLimit() + 10;
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < MAX_LISTING; i++) {
            Listing listing = new Listing();
            listing.setDealer(dealer);
            listing.setPrice((double) i);
            listing.setVehicle("V" + i);
            listing.setCreatedAt(System.currentTimeMillis());
            listing = listingService.createListing(listing);
            ids.add(listing.getId());
        }


        // When
        // publishing in the limit range
        for (int i = 0; i < MAX_LISTING; i++) {
            GetListingDto response = this.restTemplate.patchForObject("http://localhost:" + port + "/listing/" + ids.get(i) + "?force=true", null, GetListingDto.class);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getId()).isNotNull();
            assertThat(response.getDealerId()).isEqualTo(dealer.getId());
            assertThat(response.getState()).isEqualTo(published);
        }

        // ensure the dealer only have the authorized count at the end
        long publishedCount = listingService.findListingByDealerId(dealer.getId(), published).size();
        assertThat(publishedCount).isEqualTo(dealer.getTierLimit());

        long draftCount = listingService.findListingByDealerId(dealer.getId(), draft).size();
        assertThat(draftCount + publishedCount).isEqualTo(MAX_LISTING);


    }


}
