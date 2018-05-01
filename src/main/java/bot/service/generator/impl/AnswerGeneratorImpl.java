package bot.service.generator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.generator.AnswerGenerator;

public class AnswerGeneratorImpl implements AnswerGenerator{
	private final static Logger LOGGER = LoggerFactory.getLogger(AnswerGeneratorImpl.class);

	public AnswerGeneratorImpl() {
		super();
		LOGGER.info("************ AnswerGeneratorImpl created ************");
	}
}
