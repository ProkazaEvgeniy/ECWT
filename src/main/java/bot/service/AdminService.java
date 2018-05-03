package bot.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;

import bot.entity.Category;
import bot.entity.Product;
import bot.entity.Userbot;
import bot.session.CategoryForm;
import bot.session.ProductForm;
import bot.session.UserbotForm;

public interface AdminService {
	/*
	 * commands
	 * */
	@Nonnull SendMessage answerBotStart(@Nonnull long chat_id);
	@Nonnull SendMessage answerForBackToSellMenu(@Nonnull long chat_id);
	@Nonnull SendMessage answerBotEnAfterAddCategory(@Nonnull long chat_id);
	@Nonnull EditMessageText answerBotAfterChooseLanguageEnBuyOrSell(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterSell(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotEnAfterBackToSell(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotEnAfterBackToMainMenu(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterCreateProduct(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull SendMessage answerBotEnAfterOKCreateProduct_To_CheckAdmin(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotEnAfterOKCreateProduct(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotEnAfterNOCreateProduct(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotEnAfterNameProduct(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterPrice(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterPhoto(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterDescription(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterCategory(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterAddCategory(@Nonnull long chat_id, int message_id);
	
	/*
	 * userbot
	 * */
	void saveUserbot(UserbotForm userbotForm);
	int updateUserbotChooseLanguage(Integer id, String text);
	@Nonnull Userbot findByIDT(Integer idT);
	
	/*
	 * product
	 * */
	void saveProduct(ProductForm productForm);
	@Nonnull Product findByPhoto(String photo);
	
	/*
	 * category
	 * */
	@Nonnull List<Category> findAllCategory();
	void saveCategory(CategoryForm categoryForm);
}
