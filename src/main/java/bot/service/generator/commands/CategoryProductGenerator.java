package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class CategoryProductGenerator extends AnswerGeneratorImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(CategoryProductGenerator.class);

	public CategoryProductGenerator(Session session) {
	
		LOGGER.info("************* CategoryProductGenerator instance");
	}

}
