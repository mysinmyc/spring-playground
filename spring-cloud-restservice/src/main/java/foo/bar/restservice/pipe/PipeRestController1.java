package foo.bar.restservice.pipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PipeRestController1 {

	
	@Autowired
	DiscoveryClient discoveryClient;
	
	static String getFirstUrl(DiscoveryClient discoveryClient, String service) {
		
		List<ServiceInstance> serviceInstances =discoveryClient.getInstances(service);
		
		if (serviceInstances.size()==0) {
			throw new RuntimeException("Service "+service+" not found");
		}
		return serviceInstances.get(0).getUri().toString();
	}
	
	
	@GetMapping("/pipe1/{service}/{endpoint}")
	public String pipe(RestTemplate restTemplate, @PathVariable("service")String service, @PathVariable("endpoint") String endpoint) {
		
		Map<String, String> variables = new HashMap<>();
		variables.put("endpoint", endpoint);
		return restTemplate.getForEntity(getFirstUrl(discoveryClient, service)+"/{endpoint}", Object.class,variables).getBody().toString();
	}
}
