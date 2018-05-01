package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.HelpAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class HelpGenerator extends AnswerGeneratorImpl {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HelpGenerator.class);

	public HelpGenerator(Session session) {
		session.setHelpAnswer(new HelpAnswer());
		LOGGER.info("HelpGenerator instance");
	}
	
}
