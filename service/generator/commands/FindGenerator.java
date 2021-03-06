package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.FindAnswer;
import bot.service.answer.FindFirstTime;
import bot.service.answer.RepeatArgument;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class FindGenerator extends AnswerGeneratorImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(FindGenerator.class);

	public FindGenerator(Session session) {
		session.setFindAnswer(new FindAnswer());
		LOGGER.info("FindAnswer instance");
		session.setRepeatArgument(new RepeatArgument());
		LOGGER.info("RepeatArgument instance");
		session.setFindFirstTime(new FindFirstTime());
		LOGGER.info("FindFirstTime instance");
	}
	
	
}
