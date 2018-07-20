package bot.service;

import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Nonnull;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;

import bot.entity.Category;
import bot.entity.Product;
import bot.entity.Userbot;
import bot.session.CategoryForm;
import bot.session.ProductForm;
import bot.session.ProductFormToCheckAdmin;
import bot.session.UserbotForm;

public interface AdminService {
	/*
	 * commands
	 * */
	@Nonnull SendMessage answerBotStart(@Nonnull long chat_id);
	@Nonnull SendMessage answerForBackToSellMenu(@Nonnull long chat_id);
	@Nonnull SendMessage answerBotEnAfterAddCategory(@Nonnull long chat_id);
	@Nonnull SendMessage answerBotEnAfterPhotoErorr(@Nonnull long chat_id);
	@Nonnull SendMessage answerBotEnAfterTextNameErorr(@Nonnull long chat_id);
	@Nonnull SendMessage answerBotEnAfterTextPriceErorr(@Nonnull long chat_id);
	@Nonnull SendMessage answerBotEnAfterTextDescriptionErorr(@Nonnull long chat_id);
	@Nonnull EditMessageText answerAnythingTextToCallbackQuery(@Nonnull long chat_id, int message_id_previous);
	@Nonnull SendMessage answerNoToCreateProductFromAdmin(@Nonnull long chat_id, int message_id);
	@Nonnull SendMessage answerOkToCreateProductFromAdmin(@Nonnull long chat_id, String name);
	@Nonnull SendMessage answerBotEnAfterOKCreateProduct_To_CheckAdmin(@Nonnull long chat_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotAfterChooseLanguageEnBuyOrSell(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterSell(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull SendMessage answerBotEnAfterSell(@Nonnull long chat_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotEnAfterBuy(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterFindByCategory(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterBackToSell(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotEnAfterBackToMainMenu(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterCreateProduct(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull EditMessageText answerForNoCompleteProductForm(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterOKCreateProduct(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterNOCreateProduct(@Nonnull long chat_id, int message_id, ProductForm productForm);
	@Nonnull EditMessageText answerBotEnAfterNameProduct(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterPrice(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterPhoto(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterDescription(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterCategory(@Nonnull long chat_id, int message_id);
	@Nonnull SendMessage answerBotEnAfterCategory(@Nonnull long chat_id);
	@Nonnull EditMessageText answerBotEnAfterChooseCategory(@Nonnull long chat_id, int message_id);
	@Nonnull EditMessageText answerBotEnAfterAddCategory(@Nonnull long chat_id, int message_id);
	@Nonnull SendPhoto answerBotEnSendPhoto(@Nonnull long chat_id, ProductForm productForm) throws FileNotFoundException;
	@Nonnull EditMessageText answerBotEnDeleteMassage(@Nonnull long chat_id, int message_id);
	
	/*
	 * userbot
	 * */
	Userbot saveUserbot(UserbotForm userbotForm);
	int updateUserbotChooseLanguage(Integer id, String text);
	@Nonnull Userbot findByIDT(Integer idT);
	
	/*
	 * product
	 * */
	void deleteProduct(@Nonnull Long idProduct);
	long saveProduct(ProductFormToCheckAdmin productFormToCheckAdmin);
	@Nonnull Product findByPhoto(String photo);
	void setNullToProductForm(ProductForm productForm);
	void setNullToProductFormToCheckAdmin(ProductFormToCheckAdmin productFormToCheckAdmin);
	
	/*
	 * category
	 * */
	@Nonnull List<Category> findAllCategory();
	void saveCategory(CategoryForm categoryForm);
	Category findByUrl(String text);
	
	/*
	 * 
	 * */
	@Nonnull Iterable<Product> findAllForIndexing();
}
