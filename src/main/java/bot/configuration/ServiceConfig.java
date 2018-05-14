package bot.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan({ 	"bot.listener", 
					"bot.session", 
					"bot.main", 
					"bot.service.impl", 
					"bot.component.impl"})
@EnableAspectJAutoProxy
public class ServiceConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() throws IOException {
		PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
		conf.setLocations(
				new ClassPathResource("properties/application.properties"),
				new ClassPathResource("properties/elasticsearch.properties"),
				new ClassPathResource("properties/jpa.properties"));
		return conf;
	}
}
