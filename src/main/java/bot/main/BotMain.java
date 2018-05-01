package bot.main;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import bot.entity.Userbot;
import bot.service.answer.CategoryProductAnswer;
import bot.service.answer.DescriptionProductAnswer;
import bot.service.answer.NameProductAnswer;
import bot.service.answer.PhotoProductAnswer;
import bot.service.answer.PriceProductAnswer;
import bot.service.answer.ProductAnswer;
import bot.service.generator.AnswerGenerator;
import bot.service.generator.impl.AnswerGeneratorProvider;
import bot.session.Session;
import bot.session.UserbotForm;

@Component
public class BotMain extends TelegramLongPollingBot implements ApplicationContextAware {

	/*
	 * Settings application
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(BotMain.class);

	@Value("${BotName}")
	private String botUsername;
	@Value("${BotToken}")
	private String botToken;
	@Value("${idAdmin}")
	private String idAdmin;
	@Value("${application.host}")
	private String applicationHost;
	@Value("${db.username}")
	private String dbUsername;
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public String getBotUsername() {
		return botUsername;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

	private LoadingCache<String, Session> sessions;

	static {
		ApiContextInitializer.init();
	}

	public BotMain() {
		this.sessions = CacheBuilder.newBuilder().expireAfterAccess(10L, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Session>() {
					@Override
					public Session load(String s) throws Exception {
						return applicationContext.getBean(Session.class);
					}
				});
		LOGGER.info("************ BotMain created ************");
	}

	/* impotent element */
	private Session getSession(Update update) {
		try {
			if (update.hasMessage()) {
				return sessions.get(String.format("%d:%d", update.getMessage().getFrom().getId(),
						update.getMessage().getFrom().getId()));
			} else if (update.hasCallbackQuery()) {
				return sessions.get(String.format("%d:%d", update.getCallbackQuery().getFrom().getId(),
						update.getCallbackQuery().getFrom().getId()));
			} else {
				return null;
			}
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
	/* impotent element */

	/*
	 * End Settings application
	 */

	/*
	 * Execute handler data
	 */

	@Override
	public void onUpdateReceived(Update update) {
		Session session = getSession(update);
		if (update.hasMessage()) {
			handleTextMessage(update.getMessage(), session);
		}
		if (update.hasCallbackQuery()) {
			handleCallbackQueryMessage(update.getCallbackQuery(), session);
		}
	}

	/*
	 * performance handleCallbackQueryMessage
	 */
	private void handleCallbackQueryMessage(CallbackQuery callbackQuery, Session session) {

		// Set variables
		String call_data = callbackQuery.getData();
		int message_id = callbackQuery.getMessage().getMessageId();
		long chat_id = callbackQuery.getMessage().getChatId();
		/*
		 * 
		 * */
		if (call_data.startsWith("/")) {
			answerGeneratorCallbackQuery(session, call_data, chat_id, message_id, callbackQuery);
		}
		/*
		 * hasAnswerGenerator
		 */
		else if (session.hasAnswerGenerator()) {
			/*
			 * hasEnAnswer
			 */
			if (session.hasEnAnswer()) {
				/*
				 * call_data is 'buy'
				 */
				if (call_data.equals("buy")) {

				}
				/*
				 * call_data is 'sell'
				 */
				if (call_data.equals("sell")) {
					answerBotEnAfterSell(session, chat_id, message_id);
					session.setProductAnswer(new ProductAnswer());
				}
				/*
				 * call_data is 'back_to_sell'
				 */
				if (call_data.equals("back_to_sell")) {
					answerBotEnAfterBackToSell(session, chat_id, message_id);
				}
				/*
				 * call_data is 'back_to_main_menu'
				 */
				if (call_data.equals("back_to_main_menu")) {
					answerBotEnAfterBackToMainMenu(session, chat_id, message_id);
				}
				/*
				 * call_data is 'create_product'
				 */
				if (call_data.equals("create_product")) {
					answerBotEnAfterCreateProduct(session, chat_id, message_id);
				}
				/*
				 * call_data is 'ok_created'
				 */
				if (call_data.equals("ok_created")) {
					answerBotEnAfterOKCreateProduct_To_CheckAdmin(session, chat_id, message_id);
					answerBotEnAfterOKCreateProduct(session, chat_id, message_id);
				}
				/*
				 * call_data is 'no_created'
				 */
				if (call_data.equals("no_created")) {
					answerBotEnAfterNOCreateProduct(session, chat_id, message_id);
				}
				/*
				 * call_data is 'name_product'
				 */
				if (call_data.equals("name_product")) {
					answerBotEnAfterNameProduct(session, chat_id, message_id);
					session.setNameProductAnswer(new NameProductAnswer());
				}
				/*
				 * call_data is 'price'
				 */
				if (call_data.equals("price")) {
					answerBotEnAfterPrice(session, chat_id, message_id);
					session.setPriceProductAnswer(new PriceProductAnswer());
				}
				/*
				 * call_data is 'photo'
				 */
				if (call_data.equals("photo")) {
					answerBotEnAfterPhoto(session, chat_id, message_id);
					session.setPhotoProductAnswer(new PhotoProductAnswer());
				}
				/*
				 * call_data is 'description'
				 */
				if (call_data.equals("description")) {
					answerBotEnAfterDescription(session, chat_id, message_id);
					session.setDescriptionProductAnswer(new DescriptionProductAnswer());
				}
				/*
				 * call_data is 'category'
				 */
				if (call_data.equals("category")) {
					answerBotEnAfterCategory(session, chat_id, message_id);
					//session.setCategoryProductAnswer(new CategoryProductAnswer());
				}
				/*
				 * call_data is 'add_category'
				 */
				if (call_data.equals("add_category")) {
					answerBotEnAfterAddCategory(session, chat_id, message_id);
					//session.setCategoryProductAnswer(new CategoryProductAnswer());
				}

			}
			/*
			 * hasRuAnswer
			 */
			if (session.hasRuAnswer()) {

			}
		}
		/*
		 * 
		 * */
		else {

		}

	}

	/*
	 * end handleCallbackQueryMessage
	 */

	/*
	 * handleTextMessage
	 */
	private void handleTextMessage(Message message, Session session) {
		String text = null;
		PhotoSize photo = null;
		if (message.hasPhoto()) {
			photo = message.getPhoto().get(1);
		}
		if (message.hasDocument()) {
			photo = message.getDocument().getThumb();
		}
		long chat_id = message.getChatId();
		proccesSaveUserbot(session, message);
		/*
		 * go to commands begin
		 */
		if (message.hasText()) {
			text = message.getText();
			if (text.startsWith("/")) {
				answerGenerator(session, message, text);
			}
		}
		/*
		 * hasAnswerGenerator
		 */
		if (session.hasAnswerGenerator()) {
			/*
			 * hasEnAnswer
			 */
			if (session.hasEnAnswer()) {
				/*
				 * hasProductAnswer
				 */
				if (session.hasProductAnswer()) {
					/*
					 * hasNameProductAnswer
					 */
					if (session.hasNameProductAnswer()) {
						proccesInstanceProductFormSetName(session, text);
						answerForBackToSellMenu(session, chat_id);
					}
					/*
					 * hasPriceProductAnswer
					 */
					if (session.hasPriceProductAnswer()) {
						proccesInstanceProductFormSetPrice(session, text);
						answerForBackToSellMenu(session, chat_id);
					}
					/*
					 * hasPhotoProductAnswer
					 */
					if (session.hasPhotoProductAnswer()) {
						proccesInstanceProductFormSetPhoto(session, photo);
						answerForBackToSellMenu(session, chat_id);
					}
					/*
					 * hasDescriptionProductAnswer
					 */
					if (session.hasDescriptionProductAnswer()) {
						proccesInstanceProductFormSetDescription(session, text);
						answerForBackToSellMenu(session, chat_id);
					}

				}
			}
		}
		/*
		 * end go to commands
		 */
	}

	/*
	 * end handleTextMessage
	 */

	/*
	 * answerGeneratorCallbackQuery
	 */
	private void answerGeneratorCallbackQuery(Session session, String text, long chat_id, int message_id,
			CallbackQuery callbackQuery) {
		AnswerGenerator answerGenerator = AnswerGeneratorProvider.getAnswerGeneratorFor(session, text.substring(1));
		if (answerGenerator != null) {
			session.setAnswerGenerator(answerGenerator);
			/*
			 * set English to userbot
			 */
			if (session.hasEnAnswer()) {
				updateUserbotChooseLanguage(session, callbackQuery, text);
				answerBotAfterChooseLanguageEnBuyOrSell(session, chat_id, message_id);
			}
			/*
			 * set Russian to userbot
			 */
			if (session.hasRuAnswer()) {
				updateUserbotChooseLanguage(session, callbackQuery, text);
			}
		} else {

		}
	}

	/*
	 * end answerGeneratorCallbackQuery
	 */

	/*
	 * answerGenerator
	 */
	private void answerGenerator(Session session, Message message, String text) {
		AnswerGenerator answerGenerator = AnswerGeneratorProvider.getAnswerGeneratorFor(session, text.substring(1));
		if (answerGenerator != null) {

		} else {
			answerStartCallbackQuery(session, message.getChatId());
		}
	}

	/*
	 * answer method bot
	 */
	private void answerStartCallbackQuery(Session session, long chat_id) {
		try {
			execute(session.answerForStart(chat_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void answerForBackToSellMenu(Session session, long chat_id) {
		try {
			execute(session.answerForBackToSellMenu(chat_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void answerBotAfterChooseLanguageEnBuyOrSell(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotAfterChooseLanguageEnBuyOrSell(chat_id, message_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void answerBotEnAfterBackToSell(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterBackToSell(chat_id, message_id, session.getProductForm()));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void answerBotEnAfterBackToMainMenu(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterBackToMainMenu(chat_id, message_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void answerBotEnAfterCreateProduct(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterCreateProduct(chat_id, message_id, session.getProductForm()));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void answerBotEnAfterOKCreateProduct_To_CheckAdmin(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterOKCreateProduct_To_CheckAdmin(chat_id, message_id, session.getProductForm()));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void answerBotEnAfterOKCreateProduct(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterOKCreateProduct(chat_id, message_id, session.getProductForm()));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void answerBotEnAfterNOCreateProduct(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterNOCreateProduct(chat_id, message_id, session.getProductForm()));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void answerBotEnAfterSell(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterSell(chat_id, message_id, session.getProductForm()));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void answerBotEnAfterNameProduct(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterNameProduct(chat_id, message_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void answerBotEnAfterPrice(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterPrice(chat_id, message_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void answerBotEnAfterPhoto(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterPhoto(chat_id, message_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void answerBotEnAfterDescription(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterDescription(chat_id, message_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void answerBotEnAfterCategory(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterCategory(chat_id, message_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void answerBotEnAfterAddCategory(Session session, long chat_id, int message_id) {
		try {
			execute(session.answerBotEnAfterAddCategory(chat_id, message_id));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	/*
	 * methods
	 */
	private void proccesSaveUserbot(Session session, Message message) {
		Userbot userbot = session.findByIDT(message.getFrom().getId());
		if (userbot == null) {
			UserbotForm userbotForm = new UserbotForm(message.getFrom().getId(), message.getFrom().getFirstName(),
					message.getFrom().getLastName(), message.getFrom().getUserName(), message.getFrom().getBot(),
					message.getFrom().getLanguageCode());
			session.saveUserbot(userbotForm);
		}
	}

	/*
	 * Set name product
	 */
	private void proccesInstanceProductFormSetName(Session session, String text) {
		session.getProductForm().setName(text);
		session.setNameProductAnswerNull();
	}

	/*
	 * Set price product
	 */
	private void proccesInstanceProductFormSetPrice(Session session, String text) {
		session.getProductForm().setPrice(text);
		session.setPriceProductAnswerNull();
	}

	/*
	 * Set photo product
	 */
	private void proccesInstanceProductFormSetPhoto(Session session, PhotoSize photo) {
		
		java.io.File tempImageFile = downloadPhotoByFilePath(getFilePath(photo));
		String imageLink = session.processNewProductPhoto(tempImageFile);
		session.getProductForm().setPhoto(imageLink);
		session.setPhotoProductAnswerNull();
	}
	
	public String getFilePath(PhotoSize photo) {
	    Objects.requireNonNull(photo);

	    if (photo.hasFilePath()) { // If the file_path is already present, we are done!
	        return photo.getFilePath();
	    } else { // If not, let find it
	        // We create a GetFile method and set the file_id from the photo
	        GetFile getFileMethod = new GetFile();
	        getFileMethod.setFileId(photo.getFileId());
	        try {
	            // We execute the method using AbsSender::execute method.
	            File file = execute(getFileMethod);
	            // We now have the file_path
	            return file.getFilePath();
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return null; // Just in case
	}
	
	public java.io.File downloadPhotoByFilePath(String filePath) {
	    try {
	        // Download the file calling AbsSender::downloadFile method
	        return downloadFile(filePath);
	    } catch (TelegramApiException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	/*
	 * Set description product
	 */
	private void proccesInstanceProductFormSetDescription(Session session, String text) {
		session.getProductForm().setDescription(text);
		session.setDescriptionProductAnswerNull();
	}

	/*
	 * 
	 * */
	private void updateUserbotChooseLanguage(Session session, CallbackQuery callbackQuery, String text) {
		session.updateUserbotChooseLanguage(callbackQuery.getFrom().getId(), text);
	}
}