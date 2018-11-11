package de.adrianwilke.music.youtube;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * YouTube search.
 * 
 * @author Adrian Wilke
 */
public class YoutubeSearch {

	public static final String URL_BASE = "https://www.googleapis.com/youtube/v3/search";

	protected String apiKey;

	public YoutubeSearch(String apiKey) {
		this.apiKey = apiKey;
	}

	public String get(String id) throws IOException {
		return IOUtils.toString(new URL(URL_BASE + "?part=snippet&key=" + apiKey + "&q=" + id), "UTF-8");
	}

}