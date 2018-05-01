package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.FindUserbotByIDAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class FindUserbotByIDGenerator extends AnswerGeneratorImpl {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(FindUserbotByIDGenerator.class);

	public FindUserbotByIDGenerator(Session session) {
		session.setFindUserbotByIDAnswer(new FindUserbotByIDAnswer());
		LOGGER.info("setFindUserbotByIDAnswer instance");
	}

}
