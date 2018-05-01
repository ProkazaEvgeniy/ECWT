package bot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.google.common.collect.Lists;
import com.vdurmont.emoji.EmojiParser;

import bot.entity.Category;
import bot.entity.Cocktail;
import bot.entity.Userbot;
import bot.model.CategoryForm;
import bot.model.CocktailForm;
import bot.model.UserbotForm;
import bot.repository.search.CocktailSearchRepository;
import bot.repository.storage.CategoryRepository;
import bot.repository.storage.CocktailRepository;
import bot.repository.storage.UserbotRepository;
import bot.service.AdminService;
import bot.session.Session;

@Service
public class AdminServiceImpl extends AbstractCreateAdminServiceImpl implements AdminService {

	private final static Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private CocktailSearchRepository cocktailSearchRepository;

	@Autowired
	private CocktailRepository cocktailRepository;

	@Autowired
	private UserbotRepository userbotRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public AdminServiceImpl() {
		super();
		LOGGER.info("************ AdminServiceImpl created ************");
	}

	@Override
	public SendMessage answerBot(Message message, String text) {
		LOGGER.info("---> SendMessage answerBot " + text);
		return new SendMessage().setChatId(message.getChatId()).setText(text);
	}
	
	@Override
	public SendMessage answer_callback(long chat_id, String text) {
		LOGGER.info("---> SendMessage answerBot " + text);
		return new SendMessage().setChatId(chat_id).setText(text);
	}
	
	@Override
	public EditMessageText answerFindByCategoryCocktail_Callback(Session session, long chat_id, int message_id) {
		EditMessageText new_message = new EditMessageText().setChatId(chat_id).setMessageId(message_id).setText(EmojiParser.parseToUnicode(":blue_book:") + " OK, выберите категорию коктейля (OK, select a cocktail category)");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		for (Category category : session.listCategory()) {
			List<InlineKeyboardButton> rowInline = new ArrayList<>();
			rowInline.add(new InlineKeyboardButton().setText(category.getName().toUpperCase()).setCallbackData(category.getUrl()));
			rowsInline.add(rowInline);
			markupInline.setKeyboard(rowsInline);
		}
		new_message.setReplyMarkup(markupInline);
        LOGGER.info("---> SendMessage answerBotStart " + new_message.toString());
		return new_message;
	}
	
	@Override
	public SendMessage answerBotStart(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText("Choose what to do.");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        //List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        rowInline1.add(new InlineKeyboardButton().setText("Find " + EmojiParser.parseToUnicode(":mag_right:")).setCallbackData("/find"));
        rowInline2.add(new InlineKeyboardButton().setText("Find on category " + EmojiParser.parseToUnicode(":open_file_folder:")).setCallbackData("/findbycategory"));
        //rowInline3.add(new InlineKeyboardButton().setText("Save " + EmojiParser.parseToUnicode(":floppy_disk:")).setCallbackData("/save"));
        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        //rowsInline.add(rowInline3);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        mg.setReplyMarkup(markupInline);
        LOGGER.info("---> SendMessage answerBotStart " + mg.toString());
        return mg;
	}
	
	@Override
	public EditMessageText answerBotStart(long chat_id, int message_id){
		EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText("Choose what to do.");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        //List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        rowInline1.add(new InlineKeyboardButton().setText("Find " + EmojiParser.parseToUnicode(":mag_right:")).setCallbackData("/find"));
        rowInline2.add(new InlineKeyboardButton().setText("Find on category " + EmojiParser.parseToUnicode(":open_file_folder:")).setCallbackData("/findbycategory"));
        //rowInline3.add(new InlineKeyboardButton().setText("Save " + EmojiParser.parseToUnicode(":floppy_disk:")).setCallbackData("/save"));
        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        //rowsInline.add(rowInline3);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        new_message.setReplyMarkup(markupInline);
        LOGGER.info("---> SendMessage answerBotStart " + new_message.toString());
		return new_message;
	}
	
	@Override
	public EditMessageText answerCallback(long chat_id, int message_id, String text) {
		LOGGER.info("---> SendMessage answerBot " + text);
		EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(text);
		
		return new_message;
	}
	
	@Override
	public EditMessageText answerResult_CallbackQuery_ForFind(long chat_id, int message_id, String text) {
		EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(text);
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline1.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":arrow_backward:") + "Back ").setCallbackData("/back"));
        rowInline1.add(new InlineKeyboardButton().setText("Forward " + EmojiParser.parseToUnicode(":arrow_forward:")).setCallbackData("/forward"));
        rowInline2.add(new InlineKeyboardButton().setText("Stop " + EmojiParser.parseToUnicode(":no_entry_sign:")).setCallbackData("/_stop"));
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        markupInline.setKeyboard(rowsInline);
        new_message.setReplyMarkup(markupInline);
        LOGGER.info("---> SendMessage answerBotStart " + new_message.toString());
		return new_message;
	}
	
	@Override
	public SendMessage answerForCount(long chat_id, int size) {
		String text = "I`m find " + size + " recipe";
		SendMessage mg = new SendMessage()
                .setChatId(chat_id)
                .setText(text);
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline1.add(new InlineKeyboardButton().setText("Go " + EmojiParser.parseToUnicode(":bicyclist:")).setCallbackData("/forward"));
        rowInline2.add(new InlineKeyboardButton().setText("Stop " + EmojiParser.parseToUnicode(":no_entry_sign:")).setCallbackData("/_stop"));
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        markupInline.setKeyboard(rowsInline);
        mg.setReplyMarkup(markupInline);
        LOGGER.info("---> SendMessage answerBotStart " + mg.toString());
		return mg;
	}
	
	@Override
	public EditMessageText answerForCountEditTextMessage(long chat_id, int message_id, int size) {
		String text = "I`m find " + size + " recipe";
		EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(text);
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline1.add(new InlineKeyboardButton().setText("Go " + EmojiParser.parseToUnicode(":bicyclist:")).setCallbackData("/forward"));
        rowInline2.add(new InlineKeyboardButton().setText("Stop " + EmojiParser.parseToUnicode(":no_entry_sign:")).setCallbackData("/_stop"));
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        markupInline.setKeyboard(rowsInline);
        new_message.setReplyMarkup(markupInline);
        LOGGER.info("---> SendMessage answerBotStart " + new_message.toString());
		return new_message;
	}
	
	@Override
	public SendMessage answerBotForID(String text, Integer id) {
		LOGGER.info("---> SendMessage answerBot " + text);
		return new SendMessage().setChatId(Long.valueOf(id.longValue())).setText(text);
	}
	
	@Override
	public SendMessage answerBotSendUsers(Long chatId, String text) {
		LOGGER.info("---> SendMessage answerBotSendUsers " + text);
		return new SendMessage().setChatId(chatId).setText(text);
	}
	

	// Userbot

	@Override
	public List<Integer> findIDAllUsers() {
		List<Userbot> userbots = userbotRepository.findAll();
		List<Integer> idUserbots = new ArrayList<>();
		for (Userbot userbot : userbots) {
			idUserbots.add(userbot.getIdT());
		}
		return idUserbots;
	}
	
	@Override
	public Userbot findByIDT(Integer idT) {
		return userbotRepository.findByIdT(idT);
	}

	@Override
	@Transactional
	public void saveUserbot(UserbotForm userbotForm) {
		Userbot userbot = createNewUserbot(userbotForm.getIdT(), userbotForm.getFirstName(), userbotForm.getLastName(),
				userbotForm.getUserName(), userbotForm.getHasBot(), userbotForm.getLanguageCode());
		showUserbotCreatedLogInfoIfTransactionSuccess(userbot);
		userbotRepository.save(userbot);
	}
	//

	// Cocktail

	@Override
	public List<Cocktail> findByCategory(String text) {
		Category category = categoryRepository.findByUrl(text);
		return cocktailRepository.findByCategory(category);
	}	
	
	@Override
	public List<Cocktail> findByRecipeAndName(String text) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(text).
						field("recipe").
						field("name")
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fuzziness(Fuzziness.AUTO)
						.operator(MatchQueryBuilder.Operator.AND))
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC)).build();
		Page<Cocktail> page = cocktailSearchRepository.search(searchQuery);
		
		return setTotalContentFromPage(page);
	}
	
	private List<Cocktail> setTotalContentFromPage(Page<Cocktail> page){
		return Lists.newArrayList(page.getContent());
	}

	@Override
	@Transactional
	public Iterable<Cocktail> findAllForIndexing() {
		Iterable<Cocktail> all = cocktailRepository.findAll();
		return all;
	}

	@Override
	@Transactional
	public List<Cocktail> findBySearchQuery(String query) {
		List<Cocktail> cocktails = cocktailSearchRepository.findByRecipeOrName(query, query);
		return cocktails;
	}

	@Override
	@Transactional
	public Cocktail createNewCocktail(CocktailForm cocktailForm) {
		Cocktail cocktail = createNewCocktail(
				cocktailForm.getIdtUserbot(), 
				cocktailForm.getName(),
				cocktailForm.getRecipe(), 
				cocktailForm.getDescription(), 
				cocktailForm.getCategory(), 
				cocktailForm.getPopular());
		showCocktailCreatedLogInfoIfTransactionSuccess(cocktail);
		cocktailRepository.save(cocktail);
		Cocktail loaderCocktail = cocktailRepository.findOne(cocktail.getId());
		updateIndexCocktailDataIfTransactionSuccess(loaderCocktail.getId(), loaderCocktail);
		return cocktail;
	}

	/*
	 * edit command
	 */
	@Override
	@Transactional
	public String editCocktail(Integer id_cocktail, List<String> arguments) {
		Cocktail loaderCocktail = cocktailRepository.findOne(id_cocktail);
		executeUpdateCocktailData(id_cocktail, loaderCocktail, arguments);
		return "Update";
	}

	protected void executeUpdateCocktailData(Integer id_cocktail, Cocktail loaderCocktail, List<String> arguments) {
		synchronized (this) {
			loaderCocktail.setName(arguments.get(0));
			loaderCocktail.setRecipe(arguments.get(1));
			loaderCocktail.setDescription(arguments.get(2));
			cocktailRepository.save(loaderCocktail);
			cocktailRepository.flush();
		}
		updateIndexCocktailDataIfTransactionSuccess(id_cocktail, loaderCocktail);

	}

	protected void updateIndexCocktailDataIfTransactionSuccess(final Integer id_cocktail,
			final Cocktail loaderCocktail) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("Cocktail updated");
				updateIndexCocktailData(id_cocktail, loaderCocktail);
			}
		});

	}

	protected void updateIndexCocktailData(Integer id_cocktail, Cocktail loaderCocktail) {
		Cocktail c = cocktailRepository.findOne(id_cocktail);
		if (c == null) {
			createNewCocktailIndex(loaderCocktail);
		} else {
			cocktailSearchRepository.save(c);
			LOGGER.info("Cocktail index updated");
		}
	}

	protected void createNewCocktailIndex(Cocktail loaderCocktail) {
		cocktailSearchRepository.save(loaderCocktail);
		LOGGER.info("New Cocktail index created: {}", loaderCocktail.getName());
	}

	@Override
	@Transactional
	public void deleteCocktail(Integer id_cocktail) {
		Cocktail cocktail = cocktailRepository.findOne(id_cocktail);
		cocktailRepository.delete(cocktail);
		removeCocktailIndexIfTransactionSuccess(cocktail);
	}
	
	private void removeCocktailIndexIfTransactionSuccess(Cocktail cocktail) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("Cocktail by id=" + cocktail.getId() + " removed from storage");
				cocktailSearchRepository.delete(cocktail.getId());
			}
		});
		
	}

	// Category
	@Override
	@Transactional
	public Category createNewCategory(CategoryForm categoryForm) {
		Category category = createdNewCategory(categoryForm.getName(), categoryForm.getUrl());
		showCategoryCreatedLogInfoIfTransactionSuccess(category);
		categoryRepository.save(category);
		return category;
	}
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	@Override
	public Category findByUrl(String text) {
		return categoryRepository.findByUrl(text);
	}
	
	//
}
