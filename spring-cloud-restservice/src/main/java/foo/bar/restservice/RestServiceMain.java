package foo.bar.restservice;

import java.lang.management.ManagementFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RestServiceMain {
	
	public static void main(String[] args) {
		System.setProperty("myProcessId", ManagementFactory.getRuntimeMXBean().getName());
	    SpringApplication.run(RestServiceMain.class, args);

	}
}
