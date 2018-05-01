package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.RecipeAnswer;
import bot.service.answer.RepeatArgument;
import bot.service.answer.RepeatArgumentStop;
import bot.service.answer.SaveAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class SaveGenerator extends AnswerGeneratorImpl{
	private final static Logger LOGGER = LoggerFactory.getLogger(SaveGenerator.class);
	public SaveGenerator(Session session) {
		session.setSaveAnswer(new SaveAnswer());
		LOGGER.info("SaveAnswer instance");
		session.setRepeatArgument(new RepeatArgument());
		LOGGER.info("RepeatArgument instance");
		session.setRepeatArgumentStop(new RepeatArgumentStop());
		LOGGER.info("RepeatArgumentStop instance");
		session.setRecipeAnswer(new RecipeAnswer());
		LOGGER.info("RecipeAnswer instance");
	}

}
