package bot.service;

import java.io.FileNotFoundException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import bot.constant.Constants.UIImageType;

public interface ImageStorageService {
	
	@Nonnull String saveAndReturnImageLink (@Nonnull String imageName, @Nonnull UIImageType imageType, @Nonnull java.io.File tempImageFile);

	void remove (@Nullable String ... imageLinks);
	
	@Nonnull java.io.File getFileImage(String imageLink) throws FileNotFoundException;
}
