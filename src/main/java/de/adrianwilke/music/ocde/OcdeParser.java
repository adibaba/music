package de.adrianwilke.music.ocde;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.adrianwilke.music.Date;
import de.adrianwilke.music.HtmlParser;
import de.adrianwilke.music.UrlCache;

/**
 * OCDE parser.
 *
 * @author Adrian Wilke
 */
public class OcdeParser {

	protected String ocdeBaseUrl;

	public OcdeParser(String ocdeBaseUrl) {
		this.ocdeBaseUrl = ocdeBaseUrl;
	}

	/**
	 * Gets years (keys) and URLs (values) of single year charts.
	 */
	public SortedMap<Integer, URL> parseSingleYearUrls(UrlCache urlCache) throws IOException {
		SortedMap<Integer, URL> map = new TreeMap<Integer, URL>();

		Document doc = HtmlParser.parse(ocdeBaseUrl + OcdeUrls.URL_SINGLE_YEAR_OVERVIEW, urlCache);
		for (Element element : doc.getElementsByTag("option")) {
			map.put(Integer.parseInt(element.val()), new URL(ocdeBaseUrl + element.attr("data-link")));
		}
		return map;
	}

	public List<OcdeSong> parseSingleYearSongs(int year, UrlCache urlCache) throws IOException {
		return parseSingleYearSongs(new URL(ocdeBaseUrl + OcdeUrls.URL_SINGLE_YEAR + year), urlCache);
	}

	public List<OcdeSong> parseSingleYearSongs(URL singleYearUrl, UrlCache urlCache) throws IOException {
		List<OcdeSong> songs = new LinkedList<OcdeSong>();

		Document doc = HtmlParser.parse(singleYearUrl, urlCache);
		for (Element element : doc.getElementsByClass("drill-down-link")) {
			OcdeSong song = new OcdeSong();
			song.artist = element.getElementsByClass("info-artist").get(0).text().trim();
			song.title = element.getElementsByClass("info-title").get(0).text().trim();
			song.ocde = Integer.parseInt(element.getElementsByClass("drill-down").get(0).attr("href")
					.replaceFirst("/titel-details-", "").trim());
			songs.add(song);
		}

		return songs;
	}

	public List<OcdeSong> parseSingleSongs(Date date, UrlCache urlCache) throws IOException {
		List<OcdeSong> songs = new LinkedList<OcdeSong>();

		URL url = new URL(ocdeBaseUrl + OcdeUrls.URL_SINGLE + date.toBerlinMillis());
		Document doc = HtmlParser.parse(url, urlCache);

		for (Element element : doc.getElementsByClass("drill-down-link")) {
			OcdeSong song = new OcdeSong();
			song.artist = element.getElementsByClass("info-artist").get(0).text().trim();
			song.title = element.getElementsByClass("info-title").get(0).text().trim();
			song.ocde = Integer.parseInt(element.getElementsByClass("drill-down").get(0).attr("href")
					.replaceFirst("/titel-details-", "").trim());
			Elements elements = element.getElementsByClass("play-video");
			if (!elements.isEmpty()) {
				song.youtube = elements.get(0).attr("data-target").replaceFirst("https://www.youtube.com/embed/", "")
						.trim();
			}
			songs.add(song);
		}

		return songs;
	}

}