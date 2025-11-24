package net.ghaines.beer.menu;

import net.ghaines.beer.menu.Untappd.*;
import net.ghaines.beer.menu.ontap.OnTap;
import net.ghaines.beer.menu.ontap.OnTapRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestClientException;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerMenuController.class)
class BeerMenuControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	UntappdClient untappdClient;

	@MockitoBean
	OnTapRepository onTapRepository;

	private final UntappdBrewery brewery = new UntappdBrewery("Triptych");

	private final UntappdBeer beer = new UntappdBeer("Dank Meme", "Pale Ale");

	private final UntappdUser user = new UntappdUser("jsmith", "John", "Smith", "test");

	private final UntappdItem item = new UntappdItem(new Date(), user, beer, brewery);

	private final UntappdCheckins checkins = new UntappdCheckins(List.of(item));

	private final UntappdResponse response = new UntappdResponse(checkins);

	private final Untappd untappd = new Untappd(response);

	@Test
	void testGetMenu() throws Exception {
		when(untappdClient.getUntappd()).thenReturn(untappd);
		when(onTapRepository.findAll()).thenReturn(List.of(new OnTap()));
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("untappd"));
	}

	@Test
	void testException() throws Exception {
		when(untappdClient.getUntappd()).thenThrow(RestClientException.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().isOk())
				.andExpect(model().attributeDoesNotExist("untappd"));
	}

}
