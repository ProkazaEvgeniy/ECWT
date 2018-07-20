package bot.session;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import bot.entity.Category;
import bot.entity.Userbot;

@Component
@Scope("prototype")
public class ProductForm {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProductForm.class);
	
	private String name;
	private String price;
	private String photoImageLink;
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_category", nullable = false)
	private Category category;
	@ManyToOne
	@JoinColumn(name = "id_userbot", referencedColumnName = "id_t")
	private Userbot userbot;
	public ProductForm() {
		super();
		LOGGER.info("********************* ProductForm created");
	}
	public ProductForm(String name, String price, String photoImageLink, String description) {
		super();
		this.name = name;
		this.price = price;
		this.photoImageLink = photoImageLink;
		this.description = description;
		LOGGER.info("********************* ProductForm created whith fields");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPhotoImageLink() {
		return photoImageLink;
	}
	public void setPhotoImageLink(String photo) {
		this.photoImageLink = photo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Userbot getUserbot() {
		return userbot;
	}
	public void setUserbot(Userbot userbot) {
		this.userbot = userbot;
	}
	@Override
	public String toString() {
		return String.format("%s"+"\n"+"%s"+"\n"+"%s", name.toUpperCase(), price, category.getName());
	}
	
	public String toString_admin() {
		return String.format("name=%s"+"\n"+"price=%s"+"\n"+"description=%s"+"\n"+"category=%s"+"\n"+"userbot=%s_%s_%s", name,
				price, description, category.getName(), userbot.getFirstName(), userbot.getLastName(), userbot.getUserName());
	}
	
}
