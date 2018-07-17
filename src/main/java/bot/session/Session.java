package bot.session;

import static bot.constant.Constants.UIImageType.PRODUCT;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;

import bot.entity.Category;
import bot.entity.Userbot;
import bot.service.AdminService;
import bot.service.ImageStorageService;
import bot.service.answer.AddCategoryProductAnswer;
import bot.service.answer.CategoryProductAnswer;
import bot.service.answer.DescriptionProductAnswer;
import bot.service.answer.EnAnswer;
import bot.service.answer.NameProductAnswer;
import bot.service.answer.PhotoProductAnswer;
import bot.service.answer.PriceProductAnswer;
import bot.service.answer.ProductAnswer;
import bot.service.answer.RuAnswer;
import bot.service.generator.AnswerGenerator;

@Component
@Scope("prototype")
public class Session {

	private final static Logger LOGGER = LoggerFactory.getLogger(Session.class);

	/*
	 * field
	 */
	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductForm productForm;
	@Autowired
	private CategoryForm categoryForm;
	@Autowired
	private ImageStorageService imageStorageService;
	private AnswerGenerator answerGenerator;
	private EnAnswer enAnswer;
	private RuAnswer ruAnswer;
	private ProductAnswer productAnswer;
	private NameProductAnswer nameProductAnswer;
	private PriceProductAnswer priceProductAnswer;
	private PhotoProductAnswer photoProductAnswer;
	private DescriptionProductAnswer descriptionProductAnswer;
	private CategoryProductAnswer categoryProductAnswer;
	private AddCategoryProductAnswer addCategoryProductAnswer;

	/*
	 * 
	 * */
	public Session() {
		super();
		createdProductForm();
		LOGGER.info("************ Session created ************");
	}

	/*
	 * service answer
	 */
	public SendMessage answerForStart(long chat_id) {
		return adminService.answerBotStart(chat_id);
	}
	
	public EditMessageText answerAnythingTextToCallbackQuery(long chat_id, int message_id_previous) {
		return adminService.answerAnythingTextToCallbackQuery(chat_id, message_id_previous);
	}
	
	public SendMessage answerOkToCreateProductFromAdmin(long chat_id, int message_id) {
		return adminService.answerOkToCreateProductFromAdmin(chat_id, message_id);
	}
	
	public SendMessage answerNoToCreateProductFromAdmin(long chat_id, int message_id) {
		return adminService.answerNoToCreateProductFromAdmin(chat_id, message_id);
	}

	public SendMessage answerForBackToSellMenu(long chat_id) {
		return adminService.answerForBackToSellMenu(chat_id);
	}
	
	public EditMessageText answerForNoCompleteProductForm(long chat_id, int message_id) {
		return adminService.answerForNoCompleteProductForm(chat_id, message_id);
	}

	public SendMessage answerBotEnAfterAddCategory(long chat_id) {
		return adminService.answerBotEnAfterAddCategory(chat_id);
	}
	
	public SendMessage answerBotEnAfterPhotoErorr(long chat_id) {
		return adminService.answerBotEnAfterPhotoErorr(chat_id);
	}

	public EditMessageText answerBotAfterChooseLanguageEnBuyOrSell(long chat_id, int message_id) {
		return adminService.answerBotAfterChooseLanguageEnBuyOrSell(chat_id, message_id);
	}
	
	public EditMessageText answerBotEnAfterBuy(long chat_id, int message_id) {
		return adminService.answerBotEnAfterBuy(chat_id, message_id);
	}
	
	public EditMessageText answerBotEnAfterFindByCategory(long chat_id, int message_id) {
		return adminService.answerBotEnAfterFindByCategory(chat_id, message_id);
	}
	
	public EditMessageText answerBotEnAfterSell(long chat_id, int message_id, ProductForm productForm) {
		return adminService.answerBotEnAfterSell(chat_id, message_id, productForm);
	}

	public EditMessageText answerBotEnAfterBackToSell(long chat_id, int message_id, ProductForm productForm) {
		return adminService.answerBotEnAfterBackToSell(chat_id, message_id, productForm);
	}

	public EditMessageText answerBotEnAfterBackToMainMenu(long chat_id, int message_id) {
		return adminService.answerBotEnAfterBackToMainMenu(chat_id, message_id);
	}

	public EditMessageText answerBotEnAfterCreateProduct(long chat_id, int message_id, ProductForm productForm) {
		return adminService.answerBotEnAfterCreateProduct(chat_id, message_id, productForm);
	}

	public SendMessage answerBotEnAfterOKCreateProduct_To_CheckAdmin(long chat_id, ProductForm productForm) {
		return adminService.answerBotEnAfterOKCreateProduct_To_CheckAdmin(chat_id, productForm);
	}
	
	public EditMessageText answerBotEnAfterOKCreateProduct_To_CheckAdmin(long chat_id, int message_id, ProductForm productForm) {
		return adminService.answerBotEnAfterOKCreateProduct_To_CheckAdmin(chat_id, message_id, productForm);
	}

	public EditMessageText answerBotEnAfterOKCreateProduct(long chat_id, int message_id, ProductForm productForm) {
		return adminService.answerBotEnAfterOKCreateProduct(chat_id, message_id, productForm);
	}

	public EditMessageText answerBotEnAfterNOCreateProduct(long chat_id, int message_id, ProductForm productForm) {
		return adminService.answerBotEnAfterNOCreateProduct(chat_id, message_id, productForm);
	}

	public EditMessageText answerBotEnAfterNameProduct(long chat_id, int message_id) {
		return adminService.answerBotEnAfterNameProduct(chat_id, message_id);
	}

	public EditMessageText answerBotEnAfterPrice(long chat_id, int message_id) {
		return adminService.answerBotEnAfterPrice(chat_id, message_id);
	}

	public EditMessageText answerBotEnAfterPhoto(long chat_id, int message_id) {
		return adminService.answerBotEnAfterPhoto(chat_id, message_id);
	}

	public EditMessageText answerBotEnAfterDescription(long chat_id, int message_id) {
		return adminService.answerBotEnAfterDescription(chat_id, message_id);
	}

	public EditMessageText answerBotEnAfterCategory(long chat_id, int message_id) {
		return adminService.answerBotEnAfterCategory(chat_id, message_id);
	}

	public EditMessageText answerBotEnAfterChooseCategory(long chat_id, int message_id) {
		return adminService.answerBotEnAfterChooseCategory(chat_id, message_id);
	}

	public EditMessageText answerBotEnAfterAddCategory(long chat_id, int message_id) {
		return adminService.answerBotEnAfterAddCategory(chat_id, message_id);
	}

	/*
	 * service userbot
	 */
	public Userbot findByIDT(Integer id) {
		return adminService.findByIDT(id);
	}

	public Userbot saveUserbot(UserbotForm userbotForm) {
		return adminService.saveUserbot(userbotForm);
	}

	public void updateUserbotChooseLanguage(Integer id, String text) {
		adminService.updateUserbotChooseLanguage(id, text);
	}
	
	/*
	 * service product
	 * */
	public long saveProduct(ProductFormToCheckAdmin productFormToCheckAdmin) {
		return adminService.saveProduct(productFormToCheckAdmin);
	}

	/*
	 * service category
	 */
	public void saveCategory(CategoryForm categoryForm) {
		adminService.saveCategory(categoryForm);
	}

	public Category findByUrl(String text) {
		return adminService.findByUrl(text);
	}

	/*
	 * service download file
	 */
	public String processNewProductPhoto(java.io.File tempImageFile) {
		String imageName = generateNewFileName();
		String imageLink = imageStorageService.saveAndReturnImageLink(imageName, PRODUCT, tempImageFile);
		return imageLink;
	}

	protected String generateNewFileName() {
		return UUID.randomUUID().toString() + ".jpg";
	}

	/*
	 * has...
	 */
	public boolean hasAnswerGenerator() {
		return answerGenerator != null;
	}

	public boolean hasEnAnswer() {
		return enAnswer != null;
	}

	public boolean hasRuAnswer() {
		return ruAnswer != null;
	}

	public boolean hasProductAnswer() {
		return productAnswer != null;
	}

	public boolean hasNameProductAnswer() {
		return nameProductAnswer != null;
	}

	public boolean hasPriceProductAnswer() {
		return priceProductAnswer != null;
	}

	public boolean hasPhotoProductAnswer() {
		return photoProductAnswer != null;
	}

	public boolean hasDescriptionProductAnswer() {
		return descriptionProductAnswer != null;
	}

	public boolean hasCategoryProductAnswer() {
		return categoryProductAnswer != null;
	}

	public boolean hasAddCategoryProductAnswer() {
		return addCategoryProductAnswer != null;
	}

	/*
	 * set generator or answer instance
	 */
	public void setAnswerGenerator(AnswerGenerator answerGenerator) {
		this.answerGenerator = answerGenerator;
		LOGGER.info("****************** setAnswerGenerator");
	}

	public void setEnAnswer(EnAnswer enAnswer) {
		this.enAnswer = enAnswer;
		LOGGER.info("****************** setEnAnswer");
	}

	public void setRuAnswer(RuAnswer ruAnswer) {
		this.ruAnswer = ruAnswer;
		LOGGER.info("****************** setRuAnswer");
	}

	public void setProductAnswer(ProductAnswer productAnswer) {
		this.productAnswer = productAnswer;
		LOGGER.info("****************** setProductAnswer");
	}

	public void setNameProductAnswer(NameProductAnswer nameProductAnswer) {
		this.nameProductAnswer = nameProductAnswer;
		LOGGER.info("****************** setNameProductAnswer");
	}

	public void setPriceProductAnswer(PriceProductAnswer priceProductAnswer) {
		this.priceProductAnswer = priceProductAnswer;
		LOGGER.info("****************** setPriceProductAnswer");
	}

	public void setPhotoProductAnswer(PhotoProductAnswer photoProductAnswer) {
		this.photoProductAnswer = photoProductAnswer;
		LOGGER.info("****************** setPhotoProductAnswer");
	}

	public void setDescriptionProductAnswer(DescriptionProductAnswer descriptionProductAnswer) {
		this.descriptionProductAnswer = descriptionProductAnswer;
		LOGGER.info("****************** setDescriptionProductAnswer");
	}

	public void setCategoryProductAnswer(CategoryProductAnswer categoryProductAnswer) {
		this.categoryProductAnswer = categoryProductAnswer;
		LOGGER.info("****************** setCategoryProductAnswer");
	}

	public void setAddCategoryProductAnswer(AddCategoryProductAnswer addCategoryProductAnswer) {
		this.addCategoryProductAnswer = addCategoryProductAnswer;
		LOGGER.info("****************** setAddCategoryProductAnswer");
	}

	/*
	 * set null
	 */
	public void setAnswerGeneratorNull() {
		answerGenerator = null;
		LOGGER.info("****************** setAnswerGeneratorNull");
	}

	public void setEnAnswerNull() {
		enAnswer = null;
		LOGGER.info("****************** setEnAnswerNull");
	}

	public void setRuAnswerNull() {
		ruAnswer = null;
		LOGGER.info("****************** setRuAnswerNull");
	}

	public void setProductAnswerNull() {
		productAnswer = null;
		LOGGER.info("****************** setProductAnswerNull");
	}

	public void setNameProductAnswerNull() {
		nameProductAnswer = null;
		LOGGER.info("****************** setNameProductAnswerNull");
	}

	public void setPriceProductAnswerNull() {
		priceProductAnswer = null;
		LOGGER.info("****************** setPriceProductAnswerNull");
	}

	public void setPhotoProductAnswerNull() {
		photoProductAnswer = null;
		LOGGER.info("****************** setPhotoProductAnswerNull");
	}

	public void setDescriptionProductAnswerNull() {
		descriptionProductAnswer = null;
		LOGGER.info("****************** setDescriptionProductAnswerNull");
	}

	public void setCategoryProductAnswerNull() {
		categoryProductAnswer = null;
		LOGGER.info("****************** setCategoryProductAnswerNull");
	}

	public void setAddCategoryProductAnswerNull() {
		addCategoryProductAnswer = null;
		LOGGER.info("****************** setAddCategoryProductAnswerNull");
	}

	/*
	 * created model ProductForm
	 */
	public ProductForm getProductForm() {
		return productForm;
	}

	public void setProductForm(ProductForm productForm) {
		this.productForm = productForm;
	}

	public void createdProductForm() {
		setProductForm(new ProductForm());
		LOGGER.info("****************** createdProductForm");
	}

	public boolean hasCompleteProductForm() {
		return getProductForm().getName() != null && getProductForm().getPrice() != null
				&& getProductForm().getPhoto() != null && getProductForm().getDescription() != null
				&& getProductForm().getCategory() != null;
	}
	
	/*
	 * created model CategoryForm
	 */
	public CategoryForm getCategoryForm() {
		return categoryForm;
	}

	public void setCategoryForm(CategoryForm categoryForm) {
		this.categoryForm = categoryForm;
	}

	public void createdCategoryForm() {
		setCategoryForm(new CategoryForm());
		LOGGER.info("****************** createdCategoryForm");
	}

	/*
	 * get set adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
}