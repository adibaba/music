package de.adrianwilke.music;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * HTML parser.
 *
 * @author Adrian Wilke
 */
public abstract class HtmlParser {

	protected static final String UTF_8 = "UTF-8";

	public static Document parse(String url, UrlCache urlCache) throws IOException {
		return Jsoup.parse(urlCache.get(url), UTF_8);
	}

	public static Document parse(URL url, UrlCache urlCache) throws IOException {
		return Jsoup.parse(urlCache.get(url), UTF_8);
	}

}