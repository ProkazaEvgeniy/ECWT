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
public class ProductFormToCheckAdmin {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProductFormToCheckAdmin.class);
	
	private String name;
	private String price;
	private String photo;
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_category", nullable = false)
	private Category category;
	@ManyToOne
	@JoinColumn(name = "id_userbot", referencedColumnName = "id_t")
	private Userbot userbot;
	public ProductFormToCheckAdmin() {
		super();
		LOGGER.info("********************* ProductFormToCheckAdmin created");
	}
	public ProductFormToCheckAdmin(String name, String price, String photo, String description) {
		super();
		this.name = name;
		this.price = price;
		this.photo = photo;
		this.description = description;
		LOGGER.info("********************* ProductFormToCheckAdmin created whith fields");
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
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
		return String.format("ProductFormToCheckAdmin [name=%s, price=%s, photo=%s, description=%s, category=%s, userbot=%s]", name,
				price, photo, description, category, userbot);
	}
	
}
