package br.com.obpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"file:c:/obpc/external.properties"})
public class ObpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObpcApplication.class, args);
	}

}
