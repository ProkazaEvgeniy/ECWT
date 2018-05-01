package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.EditAnswer;
import bot.service.answer.RepeatArgument;
import bot.service.answer.RepeatArgumentStop;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class EditGenerator extends AnswerGeneratorImpl {

	private final static Logger LOGGER = LoggerFactory.getLogger(EditGenerator.class);

	public EditGenerator(Session session) {
		super();
		session.setEditAnswer(new EditAnswer());
		LOGGER.info("EditAnswer instance");
		session.setRepeatArgument(new RepeatArgument());
		LOGGER.info("RepeatArgument instance");
		session.setRepeatArgumentStop(new RepeatArgumentStop());
		LOGGER.info("RepeatArgumentStop");
	}
	
	
}
