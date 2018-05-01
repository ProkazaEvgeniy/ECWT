package bot.service.generator.impl;

import bot.service.generator.AnswerGenerator;
import bot.service.generator.commands.BackupDBGenerator;
import bot.service.generator.commands.CancelGenerator;
import bot.service.generator.commands.DeleteGenerator;
import bot.service.generator.commands.EditGenerator;
import bot.service.generator.commands.FindByCategoryGenerator;
import bot.service.generator.commands.FindGenerator;
import bot.service.generator.commands.FindUserbotByIDGenerator;
import bot.service.generator.commands.HelpGenerator;
import bot.service.generator.commands.SaveCategoryGenerator;
import bot.service.generator.commands.SaveCocktailGenerator;
import bot.service.generator.commands.SaveGenerator;
import bot.service.generator.commands.SendAllUsersGenerator;
import bot.session.Session;

public class AnswerGeneratorProvider {

	public AnswerGeneratorProvider() {
		super();
	}

	public static AnswerGenerator getAnswerGeneratorFor(Session session, String type) {
		switch (type.toLowerCase()) {
		case "help":
			return new HelpGenerator(session);
		case "find":
			return new FindGenerator(session);
		case "findbycategory":
			return new FindByCategoryGenerator(session);
		case "finduserbotbyid":
			return new FindUserbotByIDGenerator(session);
		case "savecocktail":
			return new SaveCocktailGenerator(session);
		case "save":
			return new SaveGenerator(session);
		case "savecategory":
			return new SaveCategoryGenerator(session);
		case "sendallusers":
			return new SendAllUsersGenerator(session);
		case "bdb":
			return new BackupDBGenerator(session);
		case "edit":
			return new EditGenerator(session);
		case "delete":
			return new DeleteGenerator(session);
		case "cancel":
			return new CancelGenerator(session);
		default:
			return null;
		}

	}

}
