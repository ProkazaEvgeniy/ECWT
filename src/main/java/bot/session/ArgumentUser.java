package bot.session;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ArgumentUser {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ArgumentUser.class);

	private List<String> arguments = new ArrayList<>();

	public ArgumentUser() {
		super();
		LOGGER.info("************ ArgumentUser instance ************");
	}

	public List<String> getArguments() {
		return arguments;
	}

	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}
	
	public void add(String text) {
		arguments.add(text);
	}

	@Override
	public String toString() {
		return String.format("ArgumentUser [arguments=%s]", arguments);
	}		
}
