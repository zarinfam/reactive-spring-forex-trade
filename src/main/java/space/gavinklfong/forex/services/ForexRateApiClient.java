package space.gavinklfong.forex.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import space.gavinklfong.forex.dto.ForexRateApiResp;

@Component
public class ForexRateApiClient {
	
	private static Logger logger = LoggerFactory.getLogger(ForexRateApiClient.class);

	private WebClient webClient;

	private String forexApiUrl;
	
	@Autowired
	public ForexRateApiClient(@Value("${app.forex-rate-api-url}") String forexApiUrl) {
		this.forexApiUrl = forexApiUrl;
		this.webClient = WebClient.builder().baseUrl(forexApiUrl)
//        		.filter(WebClientFilter.logResponse())
        		.build();
	}
	
	public Mono<ForexRateApiResp> fetchLatestRates(String baseCurrency) {
		
		logger.debug("fetchLatestRates() - baseUrl = " + forexApiUrl);
		
		return webClient.get().uri("/latest?base={base}", baseCurrency)
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.bodyToMono(ForexRateApiResp.class);
	}
	
	public Mono<ForexRateApiResp> fetchLatestRates(String baseCurrency, String counterCurrency) {
		
		logger.debug("fetchLatestRates() - baseUrl = " + forexApiUrl);

		
		return webClient.get().uri("/latest?base={base}&symbols={counter}", baseCurrency, counterCurrency)
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.bodyToMono(ForexRateApiResp.class);
	}
	
}
