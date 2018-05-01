package bot.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bot.constant.Constants.UIImageType;
import bot.exception.CantCompleteClientRequestException;
import bot.service.ImageStorageService;

@Service
public class FileImageStorageService implements ImageStorageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileImageStorageService.class);
	
	@Value("${media.storage.root.path}")
	protected String root;

	@Override
	public String saveAndReturnImageLink(String imageName, UIImageType imageType, java.io.File tempImageFile) {
		try {
			String imageLink = getImageLink(imageType.getFolderName(), imageName);
			saveImageFile(getSrcImageFile(tempImageFile), getDestinationImageFile(imageLink));
			return imageLink;
		} catch (IOException e) {
			throw new CantCompleteClientRequestException("Can't save image: " + e.getMessage(), e);
		}
	}

	protected String getImageLink(String folderName, String imageName) {
		return "/media/" + folderName + "/" + imageName;
	}

	protected Path getDestinationImageFile(String imageLink) {
		return Paths.get(root + imageLink);
	}
	
	protected Path getSrcImageFile(java.io.File tempImageFile) {
		return Paths.get(tempImageFile.getAbsolutePath());
	}

	protected void saveImageFile(Path srcImageFile, Path destinationImageFile) throws IOException {
		Files.move(srcImageFile, destinationImageFile);
	}

	@Override
	public void remove(String... imageLinks) {
		for (String imageLink : imageLinks) {
			if (StringUtils.isNotBlank(imageLink)) {
				removeImageFile(getDestinationImageFile(imageLink));
			}
		}
	}

	protected void removeImageFile(Path path) {
		try {
			if (Files.exists(path)) {
				Files.delete(path);
				LOGGER.debug("Image file {} removed successful", path);
			}
		} catch (IOException e) {
			LOGGER.error("Can't remove file: " + path, e);
		}
	}
}
