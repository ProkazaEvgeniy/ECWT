package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.SendAllUsersAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class SendAllUsersGenerator extends AnswerGeneratorImpl{
	private final static Logger LOGGER = LoggerFactory.getLogger(SendAllUsersGenerator.class);
	public SendAllUsersGenerator(Session session) {
		super();
		session.setSendAllUsersAnswer(new SendAllUsersAnswer());
		LOGGER.info("SendAllUsersAnswer instance");
	}

}
