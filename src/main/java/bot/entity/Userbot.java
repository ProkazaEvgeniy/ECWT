package bot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "userbot")
public class Userbot extends AbstractEntity<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "USERBOT_ID_GENERATOR", sequenceName = "seq_userbot", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERBOT_ID_GENERATOR")
	private Integer id;

	@Column(name = "id_t")
	private Integer idT;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "has_bot")
	private Boolean hasBot;

	@Column(name = "language_code")
	private String languageCode;

	@Column(name = "choose_language")
	private String chooseLanguage;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "userbot", fetch = FetchType.EAGER)
	private List<Product> products;

	public Userbot() {
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean getHasBot() {
		return this.hasBot;
	}

	public void setHasBot(Boolean hasBot) {
		this.hasBot = hasBot;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdT() {
		return this.idT;
	}

	public void setIdT(Integer idT) {
		this.idT = idT;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getChooseLanguage() {
		return chooseLanguage;
	}

	public void setChooseLanguage(String chooseLanguage) {
		this.chooseLanguage = chooseLanguage;
	}
	
	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setUserbot(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setUserbot(null);

		return product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((chooseLanguage == null) ? 0 : chooseLanguage.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((hasBot == null) ? 0 : hasBot.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idT == null) ? 0 : idT.hashCode());
		result = prime * result + ((languageCode == null) ? 0 : languageCode.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Userbot other = (Userbot) obj;
		if (chooseLanguage == null) {
			if (other.chooseLanguage != null)
				return false;
		} else if (!chooseLanguage.equals(other.chooseLanguage))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (hasBot == null) {
			if (other.hasBot != null)
				return false;
		} else if (!hasBot.equals(other.hasBot))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idT == null) {
			if (other.idT != null)
				return false;
		} else if (!idT.equals(other.idT))
			return false;
		if (languageCode == null) {
			if (other.languageCode != null)
				return false;
		} else if (!languageCode.equals(other.languageCode))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Userbot [id=%s, idT=%s, lastName=%s, userName=%s, firstName=%s, hasBot=%s, languageCode=%s, chooseLanguage=%s]",
				id, idT, lastName, userName, firstName, hasBot, languageCode, chooseLanguage);
	}
}