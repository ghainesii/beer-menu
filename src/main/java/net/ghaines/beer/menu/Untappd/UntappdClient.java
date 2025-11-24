package net.ghaines.beer.menu.Untappd;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UntappdClient {

	private static final Logger log = LoggerFactory.getLogger(UntappdClient.class);

	private final RestClient restClient;

	@Value("${untappd.url}")
	private String untappdUrl = "";

	public UntappdClient(RestClient.Builder builder) {
		this.restClient = builder.build();
	}

	@CircuitBreaker(name = "untappd", fallbackMethod = "getFallback")
	public Untappd getUntappd() {
		return restClient.get()
				.uri(untappdUrl)
				.retrieve()
				.body(Untappd.class);
	}

	public Untappd getFallback(Throwable e) {
		log.error("Breaker tripped!", e);
		return null;
	}

}
