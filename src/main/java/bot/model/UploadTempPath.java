package bot.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UploadTempPath extends AbstractModel{
	private final Path largeImagePath;
	public UploadTempPath() throws IOException {
		super();
		this.largeImagePath = Files.createTempFile("large", ".jpg");
	}
	public Path getLargeImagePath() {
		return largeImagePath;
	}
}
