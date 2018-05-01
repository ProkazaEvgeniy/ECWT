package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.DeleteAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class DeleteGenerator extends AnswerGeneratorImpl{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DeleteGenerator.class);
	
	public DeleteGenerator(Session session){
		super();
		session.setDeleteAnswer(new DeleteAnswer());
		LOGGER.info("DeleteAnswer instance");
	}
}
