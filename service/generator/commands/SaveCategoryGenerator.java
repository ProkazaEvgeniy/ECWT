package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.RepeatArgument;
import bot.service.answer.SaveCategoryAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class SaveCategoryGenerator extends AnswerGeneratorImpl {

	private final static Logger LOGGER = LoggerFactory.getLogger(SaveCategoryGenerator.class);

	public SaveCategoryGenerator(Session session) {
		super();
		session.setSaveCategoryAnswer(new SaveCategoryAnswer());
		LOGGER.info("SaveCategoryAnswer instance");
		session.setRepeatArgument(new RepeatArgument());
		LOGGER.info("RepeatArgument instance");
	}
	
}
