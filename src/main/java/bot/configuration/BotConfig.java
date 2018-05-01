package bot.configuration;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import bot.main.BotMain;

@Configuration
public class BotConfig {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BotConfig.class);
	
	@Autowired
	private BotMain botMain;

	@PostConstruct
	private void init(){
		try {
			new TelegramBotsApi().registerBot(botMain);
			LOGGER.info("************ BotConfig @PostConstruct init created ************");
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}
	
}
