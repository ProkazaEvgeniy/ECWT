package bot.component.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import bot.component.ImageResizer;
import net.coobird.thumbnailator.Thumbnails;

@Component
public class ThumbnailsImageResizer implements ImageResizer {

	@Override
	public void resizeImage(Path sourceImageFile, Path destImageFile, int width, int height) throws IOException {
		Thumbnails.of(sourceImageFile.toFile()).size(width, height).toFile(destImageFile.toFile());
	}
}
