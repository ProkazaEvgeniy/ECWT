package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.FindByCategoryAnswer;
import bot.service.answer.FindFirstTime;
import bot.service.answer.RepeatArgument;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class FindByCategoryGenerator extends AnswerGeneratorImpl{
	private final static Logger LOGGER = LoggerFactory.getLogger(FindByCategoryGenerator.class);

	public FindByCategoryGenerator(Session session) {
		session.setFindByCategoryAnswer(new FindByCategoryAnswer());
		LOGGER.info("FindByCategoryAnswer instance");
		session.setRepeatArgument(new RepeatArgument());
		LOGGER.info("RepeatArgument instance");
		session.setFindFirstTime(new FindFirstTime());
		LOGGER.info("FindFirstTime instance");
	}

}
