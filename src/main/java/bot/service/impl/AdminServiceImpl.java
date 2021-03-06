package bot.service.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import bot.entity.Category;
import bot.entity.Product;
import bot.entity.Userbot;
import bot.repository.search.ProductSearchRepository;
import bot.repository.storage.CategoryRepository;
import bot.repository.storage.ProductRepository;
import bot.repository.storage.UserbotRepository;
import bot.service.AdminService;
import bot.service.ImageStorageService;
import bot.session.CategoryForm;
import bot.session.ProductForm;
import bot.session.ProductFormToCheckAdmin;
import bot.session.UserbotForm;

@Service
public class AdminServiceImpl extends AbstractCreateAdminServiceImpl implements AdminService {

	private final static Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private UserbotRepository userbotRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductSearchRepository productSearchRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ImageStorageService imageStorageService;

	/*
	 * commands
	 */
	@Override
	public SendMessage answerBotStart(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText("Choose language");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
		//
		rowInline1.add(new InlineKeyboardButton().setText("English").setCallbackData("/en"));
		// rowInline2.add(new
		// InlineKeyboardButton().setText("Russian").setCallbackData("/ru"));
		// Set the keyboard to the markup
		rowsInline.add(rowInline1);
		rowsInline.add(rowInline2);
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		mg.setReplyMarkup(markupInline);
		LOGGER.info("---------------> SendMessage answerBotStart " + mg.toString());
		return mg;
	}

	@Override
	public SendMessage answerForBackToSellMenu(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText("Ok!");
		mg.setReplyMarkup(backToSell());
		LOGGER.info("---------------> SendMessage answerForBackToSellMenu " + mg.toString());
		return mg;
	}

	@Override
	public SendMessage answerBotEnAfterAddCategory(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText("Ok! Category create");
		mg.setReplyMarkup(allCategoryToInline());
		LOGGER.info("---------------> SendMessage answerBotEnAfterAddCategory " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotAfterChooseLanguageEnBuyOrSell(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok! What do you want to do?");
		mg.setReplyMarkup(BUY_SELL());
		LOGGER.info("---------------> SendMessage answerBotAfterChooseLanguageEnBuyOrSell " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerAnythingTextToCallbackQuery(long chat_id, int message_id_previous) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id_previous)
				.setText("Message delete");
		return mg;
	}

	private InlineKeyboardMarkup BUY_SELL() {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
		//
		// rowInline1.add(new
		// InlineKeyboardButton().setText("Buy").setCallbackData("buy"));
		rowInline2.add(new InlineKeyboardButton().setText("Sell").setCallbackData("sell"));
		// Set the keyboard to the markup
		rowsInline.add(rowInline1);
		rowsInline.add(rowInline2);
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		return markupInline;
	}

	@Override
	public EditMessageText answerBotEnAfterBuy(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok, you choose buy. Please select how me find your product?");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
		//
		rowInline1.add(new InlineKeyboardButton().setText("Find by name or description")
				.setCallbackData("find_by_name_or_description"));
		rowInline2.add(new InlineKeyboardButton().setText("Find by category").setCallbackData("find_by_category"));
		rowInline3.add(new InlineKeyboardButton().setText("<- Back menu").setCallbackData("back_to_main_menu"));
		// Set the keyboard to the markup
		rowsInline.add(rowInline1);
		rowsInline.add(rowInline2);
		rowsInline.add(rowInline3);
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		mg.setReplyMarkup(markupInline);
		LOGGER.info("---------------> SendMessage answerBotEnAfterBuy " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterFindByCategory(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok, you choose 'Find by category'. Please select category for search your product.");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		allCategory(rowsInline);
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		mg.setReplyMarkup(markupInline);
		LOGGER.info("---------------> SendMessage answerBotEnAfterFindByCategory " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterSell(long chat_id, int message_id, ProductForm productForm) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok, you choose sell. Please input data product");
		
		mg.setReplyMarkup(dataToSELL(productForm));
		LOGGER.info("---------------> SendMessage answerBotEnAfterSell " + mg.toString());
		return mg;
	}
	
	@Override
	public SendMessage answerBotEnAfterSell(long chat_id, ProductForm productForm) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText("Ok, you choose sell. Please input data product");
		mg.setReplyMarkup(dataToSELL(productForm));
		LOGGER.info("---------------> SendMessage answerBotEnAfterSell " + mg.toString());
		return mg;
	}
	
	private InlineKeyboardMarkup dataToSELL(ProductForm productForm) {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline4 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline5 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline6 = new ArrayList<>();
		//
		rowInline1.add(new InlineKeyboardButton().setText("Name product" + getName(productForm))
				.setCallbackData("name_product"));
		rowInline2.add(new InlineKeyboardButton().setText("Price" + getPrice(productForm)).setCallbackData("price"));
		rowInline3.add(new InlineKeyboardButton().setText("Photo" + getPhoto(productForm)).setCallbackData("photo"));
		rowInline4.add(new InlineKeyboardButton().setText("Description" + getDescription(productForm))
				.setCallbackData("description"));
		rowInline5.add(new InlineKeyboardButton().setText("Category" + getCategoryName(productForm))
				.setCallbackData("category"));
		rowInline6.add(new InlineKeyboardButton().setText("<- Back menu").setCallbackData("back_to_main_menu"));
		rowInline6.add(new InlineKeyboardButton().setText("Create product").setCallbackData("create_product"));
		// Set the keyboard to the markup
		rowsInline.add(rowInline1);
		rowsInline.add(rowInline2);
		rowsInline.add(rowInline3);
		rowsInline.add(rowInline4);
		rowsInline.add(rowInline5);
		rowsInline.add(rowInline6);
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		return markupInline;
	}

	private String getName(ProductForm productForm) {
		String text = productForm.getName();
		return text != null ? " - " + text : "";
	}

	private String getPrice(ProductForm productForm) {
		String text = productForm.getPrice();
		return text != null ? " - " + text : "";
	}

	private String getPhoto(ProductForm productForm) {
		String text = productForm.getPhotoImageLink();
		return text != null ? " - " + text : "";
	}

	private String getDescription(ProductForm productForm) {
		String text = productForm.getDescription();
		return text != null ? " - " + text : "";
	}

	private String getCategoryName(ProductForm productForm) {
		return productForm.getCategory() != null ? " - " + productForm.getCategory().getName() : "";
	}

	private InlineKeyboardMarkup backToSell() {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		//
		rowInline1.add(new InlineKeyboardButton().setText("<- Back").setCallbackData("back_to_sell"));
		// Set the keyboard to the markup
		rowsInline.add(rowInline1);
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		return markupInline;
	}

	@Override
	public EditMessageText answerForNoCompleteProductForm(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Sorry, your no complete form product. Please finish it and set CREATE");
		mg.setReplyMarkup(backToSell());
		LOGGER.info("---------------> SendMessage answerForNoCompleteProductForm " + mg.toString());
		return mg;
	}

	@Override
	public SendMessage answerNoToCreateProductFromAdmin(long chat_id, int message_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText("Sorry. But Your product not created.");
		mg.setReplyMarkup(backToSell());
		LOGGER.info("---------------> SendMessage answerOkToCreateProductFromAdmin " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterBackToSell(long chat_id, int message_id, ProductForm productForm) {
		return answerBotEnAfterSell(chat_id, message_id, productForm);
	}

	@Override
	public EditMessageText answerBotEnAfterBackToMainMenu(long chat_id, int message_id) {
		return answerBotAfterChooseLanguageEnBuyOrSell(chat_id, message_id);
	}

	@Override
	public EditMessageText answerBotEnAfterCreateProduct(long chat_id, int message_id, ProductForm productForm) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText(productForm.toString());
		mg.setReplyMarkup(OK_NO_created_InlineKeyboardMarkup());
		LOGGER.info("---------------> SendMessage answerBotEnAfterCreateProduct " + mg.toString());
		return mg;
	}

	private InlineKeyboardMarkup OK_NO_created_InlineKeyboardMarkup() {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		//
		rowInline1.add(new InlineKeyboardButton().setText("Ok").setCallbackData("ok_created"));
		rowInline1.add(new InlineKeyboardButton().setText("No").setCallbackData("no_created"));
		// Set the keyboard to the markup
		rowsInline.add(rowInline1);
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		return markupInline;
	}

	@Override
	public SendMessage answerBotEnAfterOKCreateProduct_To_CheckAdmin(long chat_id, ProductForm productForm) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText(productForm.toString_admin());
		mg.setReplyMarkup(OK_NO_created_SaveToDB_InlineKeyboardMarkup());
		LOGGER.info("---------------> SendMessage answerBotEnAfterOKCreateProduct_To_CheckAdmin " + mg.toString());
		return mg;
	}

	private InlineKeyboardMarkup OK_NO_created_SaveToDB_InlineKeyboardMarkup() {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		//
		rowInline1.add(new InlineKeyboardButton().setText("Ok to BD").setCallbackData("ok_created_db"));
		rowInline1.add(new InlineKeyboardButton().setText("No to BD").setCallbackData("no_created_db"));
		// Set the keyboard to the markup
		rowsInline.add(rowInline1);
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		return markupInline;
	}

	@Override
	public EditMessageText answerBotEnAfterOKCreateProduct(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Your product sent Admin check for to validate");
		LOGGER.info("---------------> SendMessage answerBotEnAfterOKCreateProduct " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterNOCreateProduct(long chat_id, int message_id, ProductForm productForm) {
		setNullToProductForm(productForm);
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Your product is not created");
		mg.setReplyMarkup(BUY_SELL());
		LOGGER.info("---------------> SendMessage answerBotEnAfterNOCreateProduct " + mg.toString());
		return mg;
	}

	@Override
	public SendMessage answerOkToCreateProductFromAdmin(long chat_id, String name) {
		SendMessage mg = new SendMessage().setChatId(chat_id)
				.setText("Success! Your product " + "'" + name.toUpperCase() + "'" + " has created. Find him via 'BUY'");
		mg.setReplyMarkup(BUY_SELL());
		LOGGER.info("---------------> SendMessage answerOkToCreateProductFromAdmin " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterNameProduct(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok! Please input a name product");
		LOGGER.info("---------------> SendMessage answerBotEnAfterNameProduct " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterPrice(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok! Please input a price, for example '100 euro' or '100 dollar'");
		LOGGER.info("---------------> SendMessage answerBotEnAfterPrice " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterPhoto(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok! Please send a photo");
		LOGGER.info("---------------> SendMessage answerBotEnAfterPhoto " + mg.toString());
		return mg;
	}

	@Override
	public SendMessage answerBotEnAfterPhotoErorr(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText("Why do I need it? I need a photo. Please.");
		LOGGER.info("---------------> SendMessage answerBotEnAfterPhotoErorr " + mg.toString());
		return mg;
	}

	@Override
	public SendMessage answerBotEnAfterTextNameErorr(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id)
				.setText("Why do I need it? I need a text. Please write me a name product.");
		LOGGER.info("---------------> SendMessage answerBotEnAfterTextNameErorr " + mg.toString());
		return mg;
	}

	@Override
	public SendMessage answerBotEnAfterTextPriceErorr(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText(
				"Why do I need it? I need a text. Please write me a price product. For example '100 euro' or '100 dollar'");
		LOGGER.info("---------------> SendMessage answerBotEnAfterTextPriceErorr " + mg.toString());
		return mg;
	}

	@Override
	public SendMessage answerBotEnAfterTextDescriptionErorr(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id)
				.setText("Why do I need it? I need a text. Please write me a description product.");
		LOGGER.info("---------------> SendMessage answerBotEnAfterTextDescriptionErorr " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterDescription(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok! Please input Description");
		LOGGER.info("---------------> SendMessage answerBotEnAfterDescription " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterCategory(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok! Please choose category to product");
		mg.setReplyMarkup(allCategoryToInline());
		LOGGER.info("---------------> SendMessage answerBotEnAfterCategory " + mg.toString());
		return mg;
	}
	
	@Override
	public SendMessage answerBotEnAfterCategory(long chat_id) {
		SendMessage mg = new SendMessage().setChatId(chat_id).setText("Ok! Please choose category to product");
		mg.setReplyMarkup(allCategoryToInline());
		LOGGER.info("---------------> SendMessage answerBotEnAfterCategory " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnAfterChooseCategory(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok! Category complite");
		mg.setReplyMarkup(backToSell());
		LOGGER.info("---------------> SendMessage answerBotEnAfterChooseCategory " + mg.toString());
		return mg;
	}

	private InlineKeyboardMarkup allCategoryToInline() {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		//
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		//
		// Set the keyboard to the markup
		allCategory(rowsInline);
		//
		rowInline1.add(new InlineKeyboardButton().setText("<- Back").setCallbackData("back_to_sell"));
		rowInline1.add(new InlineKeyboardButton().setText("+ Add category").setCallbackData("add_category"));
		rowsInline.add(rowInline1);
		markupInline.setKeyboard(rowsInline);
		return markupInline;
	}

	private void allCategory(List<List<InlineKeyboardButton>> rowsInline) {
		for (Category category : findAllCategory()) {
			List<InlineKeyboardButton> rowInline = new ArrayList<>();
			rowInline.add(new InlineKeyboardButton().setText(category.getName().toUpperCase())
					.setCallbackData(category.getUrl()));
			rowsInline.add(rowInline);
		}
	}

	@Override
	public EditMessageText answerBotEnAfterAddCategory(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Ok! Write category name");
		LOGGER.info("---------------> SendMessage answerBotEnAfterAddCategory " + mg.toString());
		return mg;
	}

	@Override
	public EditMessageText answerBotEnDeleteMassage(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Message delete!");
		LOGGER.info("---------------> SendMessage answerBotEnDeleteMassage " + mg.toString());
		return mg;
	}
	
	
	public EditMessageText answerBotEnDeletePhoto(long chat_id, int message_id) {
		EditMessageText mg = new EditMessageText().setChatId(chat_id).setMessageId(message_id)
				.setText("Message delete!");
		LOGGER.info("---------------> SendMessage answerBotEnDeleteMassage " + mg.toString());
		return mg;
	}

	@Override
	public SendPhoto answerBotEnSendPhoto(long chat_id, ProductForm productForm) throws FileNotFoundException {
		java.io.File file = getFileImage(productForm.getPhotoImageLink());
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(Long.valueOf(chat_id));
		sendPhoto.setNewPhoto(file);
		sendPhoto.setCaption(productForm.toString());
		sendPhoto.setReplyMarkup(OK_NO_created_InlineKeyboardMarkup());
		return sendPhoto;
	}

	public java.io.File getFileImage(String imageLink) throws FileNotFoundException {
		return imageStorageService.getFileImage(imageLink);
	}

	/*
	 * userbots
	 */
	@Override
	@Transactional
	public Userbot saveUserbot(UserbotForm userbotForm) {
		Userbot userbot = createNewUserbot(userbotForm.getIdT(), userbotForm.getFirstName(), userbotForm.getLastName(),
				userbotForm.getUserName(), userbotForm.getHasBot(), userbotForm.getLanguageCode());
		showUserbotCreatedLogInfoIfTransactionSuccess(userbot);
		return userbotRepository.save(userbot);
	}

	@Override
	public Userbot findByIDT(Integer idT) {
		return userbotRepository.findByIdT(idT);
	}

	@Override
	@Transactional
	public int updateUserbotChooseLanguage(Integer id, String text) {
		Userbot loaderUserbot = userbotRepository.findByIdT(id);
		executeUpdateUserbotData(id, text, loaderUserbot);
		return 0;
	}

	protected void executeUpdateUserbotData(Integer id, String text, Userbot loaderUserbot) {
		synchronized (this) {
			loaderUserbot.setChooseLanguage(text);
			userbotRepository.save(loaderUserbot);
			userbotRepository.flush();
			LOGGER.info("---------------> executeUpdateUserbotData " + loaderUserbot.toString());
		}
	}

	/*
	 * product
	 */
	@Override
	@Transactional
	public void deleteProduct(Long idProduct) {
		Product product = productRepository.findOne(idProduct);
		productRepository.delete(product);
		removeCocktailIndexIfTransactionSuccess(product);
	}

	@Override
	public Product findByPhoto(String photo) {
		return productRepository.findByPhoto(photo);
	}

	@Override
	@Transactional
	public long saveProduct(ProductFormToCheckAdmin productFormToCheckAdmin) {
		Product product = createNewProduct(productFormToCheckAdmin.getName(), productFormToCheckAdmin.getPrice(),
				productFormToCheckAdmin.getPhoto(), productFormToCheckAdmin.getDescription(),
				productFormToCheckAdmin.getCategory(), productFormToCheckAdmin.getUserbot());
		showProductCreatedLogInfoIfTransactionSuccess(product);
		productRepository.save(product);
		long chat_id_answer = productFormToCheckAdmin.getUserbot().getIdT();
		return chat_id_answer;
	}

	@Override
	public void setNullToProductForm(ProductForm productForm) {
		setNullToProductFormSuper(productForm);
	}

	@Override
	public void setNullToProductFormToCheckAdmin(ProductFormToCheckAdmin productFormToCheckAdmin) {
		setNullToProductFormToCheckAdminSuper(productFormToCheckAdmin);
	}

	/*
	 * category
	 */
	@Override
	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	@Transactional
	public void saveCategory(CategoryForm categoryForm) {
		Category category = createNewCategory(categoryForm.getName(), categoryForm.getUrl());
		showCategoryCreatedLogInfoIfTransactionSuccess(category);
		categoryRepository.save(category);
	}

	@Override
	public Category findByUrl(String text) {
		return categoryRepository.findByUrl(text);
	}

	/*
	 * 
	 * */
	@Override
	@Transactional
	public Iterable<Product> findAllForIndexing() {
		Iterable<Product> all = productRepository.findAll();
		return all;
	}

	protected void updateIndexProductData(Long idProduct, Product loaderProduct) {
		Product p = productRepository.findOne(idProduct);
		if (p == null) {
			createNewProductIndex(loaderProduct);
		} else {
			productSearchRepository.save(p);
			LOGGER.info("Product index updated");
		}
	}

	protected void createNewProductIndex(Product loaderProduct) {
		productSearchRepository.save(loaderProduct);
		LOGGER.info("New Product index created: {}", loaderProduct.getName());
	}

	private void removeCocktailIndexIfTransactionSuccess(Product product) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("Product by id=" + product.getId() + " removed from storage");
				productSearchRepository.delete(product.getId());
			}
		});

	}
}
