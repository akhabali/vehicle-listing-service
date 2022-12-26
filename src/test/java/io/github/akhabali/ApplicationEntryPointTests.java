package io.github.akhabali;

import io.github.akhabali.controller.DealerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationEntryPointTests {

    @Autowired
    DealerController dealerController;

    @Test
    void contextLoads() {
        assertThat(dealerController).isNotNull();
    }

}
