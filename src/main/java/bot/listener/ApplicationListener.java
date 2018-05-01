package bot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ApplicationListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LOGGER.info("***************** Aplication bot telegram started *****************");
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LOGGER.info("***************** Aplication bot telegram stoped *****************");
		
	}

}
