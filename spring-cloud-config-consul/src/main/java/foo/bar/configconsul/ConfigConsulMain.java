package foo.bar.configconsul;

import java.lang.management.ManagementFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConfigConsulMain {
	
	public static void main(String[] args) {
		System.setProperty("myProcessId", ManagementFactory.getRuntimeMXBean().getName());
	    SpringApplication.run(ConfigConsulMain.class, args);

	}
}
