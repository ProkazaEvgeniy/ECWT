package bot.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserbotForm {

	private Integer idT;
	private String lastName;
	private String userName;
	private String firstName;
	private Boolean hasBot;
	private String languageCode;
	
	public UserbotForm(Integer idT, String firstName, String lastName, String userName, Boolean hasBot, String languageCode) {
		super();
		this.idT = idT;
		this.lastName = lastName;
		this.userName = userName;
		this.firstName = firstName;
		this.hasBot = hasBot;
		this.languageCode = languageCode;
	}

	public UserbotForm() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdT() {
		return idT;
	}

	public void setIdT(Integer idT) {
		this.idT = idT;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean getHasBot() {
		return hasBot;
	}

	public void setHasBot(Boolean hasBot) {
		this.hasBot = hasBot;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

}
