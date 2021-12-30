package foo.bar.restservice.pipe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PipeRestController3 {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/pipe3/{service}/{endpoint}")
	public String pipe(@PathVariable("service") String service, @PathVariable("endpoint") String endpoint) {

		Map<String, String> variables = new HashMap<>();
		variables.put("service", service);
		variables.put("endpoint", endpoint);
		return restTemplate.getForEntity("http://{service}/{endpoint}", Object.class, variables).getBody().toString();
	}
}
