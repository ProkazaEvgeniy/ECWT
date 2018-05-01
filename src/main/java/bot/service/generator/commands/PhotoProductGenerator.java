package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.PhotoProductAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class PhotoProductGenerator extends AnswerGeneratorImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(PhotoProductGenerator.class);

	public PhotoProductGenerator(Session session) {
		session.setPhotoProductAnswer(new PhotoProductAnswer());
		LOGGER.info("************* PhotoProductAnswer instance");
	}

}
