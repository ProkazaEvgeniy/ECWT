package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.RuAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class RuGenerator extends AnswerGeneratorImpl {

	private final static Logger LOGGER = LoggerFactory.getLogger(RuGenerator.class);
	
	public RuGenerator(Session session) {
		session.setRuAnswer(new RuAnswer());
		LOGGER.info("************* RuAnswer instance");
	}

}
