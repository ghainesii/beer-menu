package net.ghaines.beer.menu.Untappd;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class UntappdClient {

	private final RestTemplate rt;

	@Value("${untappd.url}")
	private String untappdUrl = "";

	public UntappdClient(RestTemplateBuilder rtBuilder) {
		this.rt = rtBuilder.build();
	}

	@CircuitBreaker(name = "untappd", fallbackMethod = "getFallback")
	public Untappd getUntappd() {
		return rt.getForObject(untappdUrl, Untappd.class);
	}

	public Untappd getFallback(Throwable e) {
		log.error("Breaker tripped!", e);
		return null;
	}

}
