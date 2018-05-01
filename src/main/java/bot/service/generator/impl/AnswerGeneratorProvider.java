package bot.service.generator.impl;

import bot.service.generator.AnswerGenerator;
import bot.service.generator.commands.EnGenerator;
import bot.service.generator.commands.RuGenerator;
import bot.session.Session;

public class AnswerGeneratorProvider {

	public AnswerGeneratorProvider() {
		super();
	}

	public static AnswerGenerator getAnswerGeneratorFor(Session session, String type) {
		switch (type.toLowerCase()) {
		case "en":
			return new EnGenerator(session);
		case "ru":
			return new RuGenerator(session);
		default:
			return null;
		}

	}

}
