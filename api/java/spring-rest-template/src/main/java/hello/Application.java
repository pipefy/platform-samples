package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			HttpHeaders headers = new HttpHeaders();
			String authorizationToken = "YOUR TOKEN HERE";
			headers.set("Authorization", "Bearer " + authorizationToken);
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			String url = "https://app.pipefy.com/queries";
			String graphqlQuery = "{ organizations { name } }";
			String requestJson = "{\"query\":\""+ graphqlQuery + "\"}";
			
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			String answer = restTemplate.postForObject(url, entity, String.class);
			log.info(answer);
		};
	}
}