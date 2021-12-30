package foo.bar.edge;

import java.lang.management.ManagementFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EdgeMain {

	public static void main(String[] args) {
		System.setProperty("myProcessId", ManagementFactory.getRuntimeMXBean().getName());
	    SpringApplication.run(EdgeMain.class, args);

	}
}
