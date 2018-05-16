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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	private String photo;
	private String description;

	@Column(name = "has_sell")
	private Boolean hasSell;
	private Integer rating;

	// bi-directional many-to-one association to Category
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_category", referencedColumnName = "id")
	private Category category;
	// bi-directional many-to-one association to Userbot
	@ManyToOne
	@JoinColumn(name = "id_userbot", referencedColumnName = "id_t")
	@JsonIgnore
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

	public Boolean getHasSell() {
		return hasSell;
	}

	public void setHasSell(Boolean hasSell) {
		this.hasSell = hasSell;
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
		result = prime * result + ((hasSell == null) ? 0 : hasSell.hashCode());
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
		if (hasSell == null) {
			if (other.hasSell != null)
				return false;
		} else if (!hasSell.equals(other.hasSell))
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
				"Product [id=%s, name=%s, price=%s, photo=%s, description=%s, hasSell=%s, rating=%s, category=%s, userbot=%s]",
				id, name, price, photo, description, hasSell, rating, category, userbot);
	}

}