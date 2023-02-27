package net.ghaines.beer.menu.Untappd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UntappdClient {

	private final RestTemplate rt;

	@Value("${untappd.url}")
	private String untappdUrl = "";

	public UntappdClient(RestTemplateBuilder rtBuilder) {
		this.rt = rtBuilder.build();
	}

	public Untappd getUntappd() {
		return rt.getForObject(untappdUrl, Untappd.class);
	}

}
