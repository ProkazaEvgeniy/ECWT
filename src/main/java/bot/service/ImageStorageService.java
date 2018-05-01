package bot.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import bot.constant.Constants.UIImageType;

public interface ImageStorageService {
	
	@Nonnull String saveAndReturnImageLink (@Nonnull String imageName, @Nonnull UIImageType imageType, @Nonnull java.io.File tempImageFile);

	void remove (@Nullable String ... imageLinks);
}
