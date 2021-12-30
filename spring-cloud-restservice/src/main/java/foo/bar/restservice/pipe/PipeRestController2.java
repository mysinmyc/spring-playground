package foo.bar.restservice.pipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class PipeRestController2 {
	
	@Autowired
	WebClient.Builder webClientBuilder;
	
	@GetMapping("/pipe2/{service}/{endpoint}")
	public String pipe( @PathVariable("service") String service,
			@PathVariable("endpoint") String endpoint) {

		return webClientBuilder.build().get().uri("http://" + service + "/" + endpoint).retrieve().bodyToMono(String.class).block();
	}
}
