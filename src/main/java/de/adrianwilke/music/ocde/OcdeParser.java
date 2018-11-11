package de.adrianwilke.music.ocde;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import de.adrianwilke.music.HtmlParser;
import de.adrianwilke.music.UrlCache;

/**
 * OCDE parser.
 *
 * @author Adrian Wilke
 */
public class OcdeParser {

	public SortedMap<Integer, URL> getSingleYearUrls(UrlCache urlCache) throws IOException {
		SortedMap<Integer, URL> map = new TreeMap<Integer, URL>();

		Document doc = HtmlParser.parse(OcdeUrls.URL_SINGLE_YEAR, urlCache);
		for (Element element : doc.getElementsByTag("option")) {
			map.put(Integer.parseInt(element.val()), new URL(OcdeUrls.URL_BASE + element.attr("data-link")));
		}

		return map;
	}

	public List<OcdeSong> getSingleYearSongs(URL url, UrlCache urlCache) throws IOException {
		List<OcdeSong> songs = new LinkedList<OcdeSong>();

		Document doc = HtmlParser.parse(url, urlCache);
		for (Element element : doc.getElementsByClass("drill-down-link")) {
			OcdeSong song = new OcdeSong();

			song.setArtist(element.getElementsByClass("info-artist").get(0).text());
			song.setTitle(element.getElementsByClass("info-title").get(0).text());
			song.setOcdeDetailsUrl(OcdeUrls.URL_BASE + element.getElementsByClass("drill-down").get(0).attr("href"));
			song.setOcdeCoverUrl(OcdeUrls.URL_BASE + element.getElementsByClass("cover-img").attr("style")
					.replaceFirst(".*\\('", "").replaceFirst("'\\).*", ""));
			songs.add(song);

		}

		return songs;
	}

}