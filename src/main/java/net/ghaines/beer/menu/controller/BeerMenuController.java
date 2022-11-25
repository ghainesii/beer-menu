package net.ghaines.beer.menu.controller;

import net.ghaines.beer.menu.entity.OnTap;
import net.ghaines.beer.menu.model.Untappd;
import net.ghaines.beer.menu.repository.OnTapRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Controller
@RequestMapping(value = "/")
public class BeerMenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeerMenuController.class);

    final RestTemplate restTemplate;

    final OnTapRepository onTapRepository;

    public BeerMenuController(RestTemplate restTemplate, OnTapRepository onTapRepository) {
        this.restTemplate = restTemplate;
        this.onTapRepository = onTapRepository;
    }

    @Value("${resident.state}")
    private String residentState;

    @Value("${untappd.url}")
    private String untappdUrl = "";

    @GetMapping
    public String getMenu(Model model) {

        try {
            Iterable<OnTap> onTap = onTapRepository.findAll();

            onTap.forEach(beer -> {
                // Brewery logo stored in DB for now.
                // Alternative solution would be to pull it from Untappd API.
                if (beer.getLogo() != null) {
                    byte[] encodeBase64 = Base64.encodeBase64(beer.getLogo());
                    String base64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
                    beer.setLogoBase64(base64Encoded);
                }
                boolean isNew = beer.getStartDa() != null && beer.getStartDa().isAfter(LocalDate.now().minusDays(7));
                beer.setNew(isNew);
            });
            model.addAttribute("onTap", onTap);
            model.addAttribute("residentState", residentState);

            Untappd untappd = restTemplate.getForObject(untappdUrl, Untappd.class);
            model.addAttribute("untappd", untappd);

        } catch (Exception e) {
            LOGGER.error("Unknown error in BeerMenuController", e);
        }

        return "beerMenu";
    }
}
