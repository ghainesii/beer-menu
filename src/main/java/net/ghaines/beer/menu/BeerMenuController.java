package net.ghaines.beer.menu;

import net.ghaines.beer.menu.Untappd.UntappdClient;
import net.ghaines.beer.menu.ontap.OnTapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Base64;

@Controller
@RequestMapping(value = "/")
public class BeerMenuController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerMenuController.class);

	final OnTapRepository onTapRepository;

	final UntappdClient untappdClient;

	public BeerMenuController(UntappdClient untappdClient, OnTapRepository onTapRepository) {
		this.untappdClient = untappdClient;
		this.onTapRepository = onTapRepository;
	}

	@Value("${resident.state}")
	private String residentState;

	@GetMapping
	public String getMenu(Model model) {

		try {
			var onTap = onTapRepository.findAll();

			onTap.forEach(beer -> {
				// Brewery logo stored in DB for now.
				// Alternative solution would be to pull it from Untappd API.
				if (beer.getLogo() != null) {
					beer.setLogoBase64(Base64.getEncoder().encodeToString(beer.getLogo()));
				}
				var isNew = beer.getStartDa() != null && beer.getStartDa().isAfter(LocalDate.now().minusDays(7));
				beer.setNew(isNew);
			});
			model.addAttribute("onTap", onTap);
			model.addAttribute("residentState", residentState);

			var untappd = untappdClient.getUntappd();
			model.addAttribute("untappd", untappd);

		}
		catch (Exception e) {
			LOGGER.error("Unknown error in BeerMenuController", e);
		}

		return "beerMenu";
	}

}
