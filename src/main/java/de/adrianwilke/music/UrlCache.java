package de.adrianwilke.music;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cache for URLs.
 *
 * @author Adrian Wilke
 */
public class UrlCache {

	protected static final Logger LOGGER = LogManager.getLogger();
	protected static final String DEFAULT_TMP_DIRECTORY = "music-cache";

	protected File cacheDirectory;

	public UrlCache() throws IOException {
		this(new File(System.getProperty("java.io.tmpdir"), DEFAULT_TMP_DIRECTORY));
	}

	public UrlCache(String directory) throws IOException {
		this(new File(directory));
	}

	public UrlCache(File directory) throws IOException {
		if (directory.exists()) {
			if (!directory.isDirectory()) {
				throw new IOException("Cache directory is not a directory: " + directory);
			}
			if (!directory.canRead()) {
				throw new IOException("Cache directory is not readdable: " + directory);
			}
			if (!directory.canWrite()) {
				throw new IOException("Cache directory is not writable: " + directory);
			}
		} else {
			if (!directory.mkdirs()) {
				throw new IOException("Cache directory could not be created: " + directory);
			}
		}
		this.cacheDirectory = directory;
		LOGGER.info("Cache directory: " + directory);
	}

	public File get(String url) throws IOException {
		return get(new URL(url));
	}

	public File get(URL url) throws IOException {
		File file = new File(cacheDirectory, String.valueOf(url.hashCode()));
		if (!file.exists()) {
			LOGGER.debug("Cache miss: " + url.toString());
			FileUtils.copyURLToFile(url, file);
		} else {
			LOGGER.debug("Cache hit: " + url.toString());
		}
		return file;
	}

}