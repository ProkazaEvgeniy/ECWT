package bot.service.generator.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.answer.BackupDBAnswer;
import bot.service.generator.impl.AnswerGeneratorImpl;
import bot.session.Session;

public class BackupDBGenerator extends AnswerGeneratorImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(BackupDBGenerator.class);
	public BackupDBGenerator(Session session) {
		session.setBackupDBAnswer(new BackupDBAnswer());
		LOGGER.info("BackupDBAnswer instance");
	}

}
