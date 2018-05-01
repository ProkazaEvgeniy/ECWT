package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class AddCategoryProductGenerator extends AnswerGeneratorImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(AddCategoryProductGenerator.class);

	public AddCategoryProductGenerator(Session session) {
	
		LOGGER.info("************* CategoryProductGenerator instance");
	}

}
