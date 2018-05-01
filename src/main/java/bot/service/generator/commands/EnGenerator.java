package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.EnAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class EnGenerator extends AnswerGeneratorImpl {

	private final static Logger LOGGER = LoggerFactory.getLogger(EnGenerator.class);
	
	public EnGenerator(Session session) {
		session.setEnAnswer(new EnAnswer());
		LOGGER.info("************* EnAnswer instance");
	}

}
