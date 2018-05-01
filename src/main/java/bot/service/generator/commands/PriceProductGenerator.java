package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.ProductAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class PriceProductGenerator extends AnswerGeneratorImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(PriceProductGenerator.class);

	public PriceProductGenerator(Session session) {
		session.setProductAnswer(new ProductAnswer());
		LOGGER.info("************* ProductAnswer instance");
	}

}
