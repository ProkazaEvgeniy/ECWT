package bot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(name = "product")
@Document(indexName = "product")
public class Product extends AbstractEntity<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PRODUCT_ID_GENERATOR", sequenceName = "seq_product", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_GENERATOR")
	private Long id;
	private String name;
	private String price;
	private String photo;
	private String description;

	@Column(name = "has_sell")
	private Boolean has_sell;
	private Integer rating;

	// bi-directional many-to-one association to Category
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_category", referencedColumnName = "id")
	private Category category;
	// bi-directional many-to-one association to Userbot
	@ManyToOne
	@JoinColumn(name = "id_userbot", referencedColumnName = "id_t")
	private Userbot userbot;

	public Product() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Userbot getUserbot() {
		return userbot;
	}

	public void setUserbot(Userbot userbot) {
		this.userbot = userbot;
	}

	public Boolean getHas_sell() {
		return has_sell;
	}

	public void setHas_sell(Boolean has_sell) {
		this.has_sell = has_sell;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((has_sell == null) ? 0 : has_sell.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((userbot == null) ? 0 : userbot.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (has_sell == null) {
			if (other.has_sell != null)
				return false;
		} else if (!has_sell.equals(other.has_sell))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (userbot == null) {
			if (other.userbot != null)
				return false;
		} else if (!userbot.equals(other.userbot))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Product [id=%s, name=%s, price=%s, photo=%s, description=%s, userbot=%s, has_sell=%s, rating=%s, category=%s]",
				id, name, price, photo, description, userbot, has_sell, rating, category);
	}

}