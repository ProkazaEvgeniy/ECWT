package bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import bot.entity.Category;
import bot.entity.Cocktail;
import bot.entity.Userbot;

public abstract class AbstractCreateAdminServiceImpl {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	protected Cocktail createNewCocktail(Integer idtUserbot, String name,  String recipe, String description, Category category, Integer popular){
		Cocktail cocktail = new Cocktail();
		cocktail.setIdtUserbot(idtUserbot);
		cocktail.setName(name.toUpperCase());
		cocktail.setRecipe(recipe);
		cocktail.setDescription(description);
		cocktail.setCategory(category);
		cocktail.setPopular(popular);
		return cocktail;
	}
	
	protected Userbot createNewUserbot(Integer idT, String firstName, String lastName, String userName, Boolean hasBot, String languageCode) {
		Userbot userbot = new Userbot();
		userbot.setIdT(idT);
		userbot.setFirstName(firstName);
		userbot.setLastName(lastName);
		userbot.setUserName(userName);
		userbot.setHasBot(hasBot);
		userbot.setLanguageCode(languageCode);
		return userbot;
	}
	
	protected Category createdNewCategory(String name, String url){
		Category category = new Category();
		category.setName(name);
		category.setUrl(url);
		return category;
	}
	
	protected void showCocktailCreatedLogInfoIfTransactionSuccess(final Cocktail cocktail) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("New cocktail created: {}", cocktail.getName());
			}
		});
	}
	
	protected void showUserbotCreatedLogInfoIfTransactionSuccess(final Userbot userbot) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("New userbot created: {}", userbot.getFirstName());
			}
		});
	}
	
	protected void showCategoryCreatedLogInfoIfTransactionSuccess(final Category category) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("New category created: {}", category.getName());
			}
		});
	}
}
