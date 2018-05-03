package bot.session;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CategoryForm {
	
	@SafeHtml
	private String name;

	@SafeHtml
	private String url;

	public CategoryForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = "URL_"+ url.toUpperCase();
	}
}
