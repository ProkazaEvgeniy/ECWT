package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.CancelAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class CancelGenerator extends AnswerGeneratorImpl {

	private final static Logger LOGGER = LoggerFactory.getLogger(CancelGenerator.class);
	
	public CancelGenerator(Session session) {
		session.setCancelAnswer(new CancelAnswer());
		LOGGER.info("CancelAnswer instance");
	}

}
