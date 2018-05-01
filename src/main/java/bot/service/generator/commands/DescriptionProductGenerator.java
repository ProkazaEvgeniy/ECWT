package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.DescriptionProductAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class DescriptionProductGenerator extends AnswerGeneratorImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(DescriptionProductGenerator.class);

	public DescriptionProductGenerator(Session session) {
		session.setDescriptionProductAnswer(new DescriptionProductAnswer());
		LOGGER.info("************* DescriptionProductAnswer instance");
	}

}
