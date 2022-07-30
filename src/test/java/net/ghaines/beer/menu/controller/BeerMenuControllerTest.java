package net.ghaines.beer.menu.controller;

import net.ghaines.beer.menu.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class BeerMenuControllerTest {

    @InjectMocks
    BeerMenuController beerMenuController;

    @Mock
    RestTemplate restTemplate;

    private MockMvc mockMvc;

    private UntappdBrewery brewery = new UntappdBrewery("Triptych");
    private UntappdBeer beer = new UntappdBeer("Dank Meme", "Pale Ale");
    private UntappdUser user = new UntappdUser("jsmith", "John", "Smith", "test");
    private UntappdItem item = new UntappdItem(new Date(), user, beer, brewery);
    private UntappdCheckins checkins = new UntappdCheckins(List.of(item));
    private UntappdResponse response = new UntappdResponse(checkins);
    private Untappd untappd = new Untappd(response);

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(beerMenuController).build();
    }

    @Test
    void testGetMenu() throws Exception {
        when(restTemplate.getForObject(anyString(), eq(Untappd.class))).thenReturn(untappd);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("untappd"))
                .andExpect(forwardedUrl("beerMenu"));
    }

    @Test
    void testException() throws Exception {
        when(restTemplate.getForObject(anyString(), eq(Untappd.class))).thenThrow(RestClientException.class);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(status().isOk())
                .andExpect(model(). attributeDoesNotExist("untappd"))
                .andExpect(forwardedUrl("beerMenu"));
    }
    
}