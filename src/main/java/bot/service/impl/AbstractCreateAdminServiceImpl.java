package bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import bot.entity.Category;
import bot.entity.Product;
import bot.entity.Userbot;
import bot.session.ProductForm;

public abstract class AbstractCreateAdminServiceImpl {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
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
	
	protected Product createNewProduct (String name, String price, String photo, String description, Category category, Userbot userbot) {
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setPhoto(photo);
		product.setDescription(description);
		product.setCategory(category);
		product.setUserbot(userbot);
		return product;
	}
	
	protected void setNullToProductForm (ProductForm productForm) {
		productForm.setName(null);
		productForm.setPrice(null);
		productForm.setPhoto(null);
		productForm.setDescription(null);
		productForm.setCategory(null);
		productForm.setUserbot(null);
	}
	
	protected void showUserbotCreatedLogInfoIfTransactionSuccess(final Userbot userbot) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("New userbot created: {}", userbot.getFirstName());
			}
		});
	}
	
	protected void showProductCreatedLogInfoIfTransactionSuccess(final Product product) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("New product created: {}", product.getName());
			}
		});
	}
}