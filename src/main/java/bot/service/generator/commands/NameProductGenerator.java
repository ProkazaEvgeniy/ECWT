package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.NameProductAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class NameProductGenerator extends AnswerGeneratorImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(NameProductGenerator.class);

	public NameProductGenerator(Session session) {
		session.setNameProductAnswer(new NameProductAnswer());
		LOGGER.info("************* NameProductAnswer instance");
	}

}
