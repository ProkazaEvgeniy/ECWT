package bot.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;

import bot.entity.Category;
import bot.entity.Cocktail;
import bot.entity.Userbot;
import bot.model.CategoryForm;
import bot.model.CocktailForm;
import bot.model.UserbotForm;
import bot.session.Session;

public interface AdminService {

	@Nonnull SendMessage answerBot(@Nonnull Message message, @Nonnull String text);
	
	@Nonnull SendMessage answerBotStart(@Nonnull long chat_id);
	
	@Nonnull SendMessage answer_callback(@Nonnull long chat_id, @Nonnull String text);
	
	@Nonnull EditMessageText answerForCountEditTextMessage(@Nonnull long chat_id, @Nonnull int message_id, @Nonnull int size);
	
	@Nonnull EditMessageText answerFindByCategoryCocktail_Callback(@Nonnull Session session, @Nonnull long chat_id, int message_id);
	
	@Nonnull EditMessageText answerBotStart(@Nonnull long chat_id, @Nonnull int message_id);
	
	@Nonnull EditMessageText answerCallback(@Nonnull long chat_id, @Nonnull int message_id, @Nonnull String text);
	
	@Nonnull EditMessageText answerResult_CallbackQuery_ForFind(@Nonnull long chat_id, @Nonnull int message_id, @Nonnull String text);
	
	@Nonnull SendMessage answerForCount(@Nonnull long chat_id, @Nonnull int size);
	
	@Nonnull SendMessage answerBotSendUsers(@Nonnull Long chatId, @Nonnull String text);
	
	@Nonnull SendMessage answerBotForID(@Nonnull String text, @Nonnull Integer id);
	
	// Cocktail
	@Nonnull List<Cocktail> findByRecipeAndName(@Nonnull String text);
	
	@Nonnull List<Cocktail> findByCategory(@Nonnull String text);
	
	@Nonnull Iterable<Cocktail> findAllForIndexing();
	
	@Nonnull List<Cocktail> findBySearchQuery(@Nonnull String query);
	
	@Nonnull Cocktail createNewCocktail(@Nonnull CocktailForm cocktailForm);
	
	@Nonnull String editCocktail(@Nonnull Integer id_cocktail, @Nonnull List<String> arguments);
	
	void deleteCocktail(@Nonnull Integer id_cocktail);
	
	// Userbot
	@Nonnull Userbot findByIDT(Integer idT);
	
	void saveUserbot(UserbotForm userbotForm);
	
	@Nonnull List<Integer> findIDAllUsers();
	
	// Category
	
	@Nonnull Category createNewCategory(@Nonnull CategoryForm categoryForm);
	
	@Nonnull List<Category> findAll();
	
	@Nonnull Category findByUrl(String text);
}
