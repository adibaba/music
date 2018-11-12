package de.adrianwilke.music;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.adrianwilke.music.io.Exporter;
import de.adrianwilke.music.io.Importer;
import de.adrianwilke.music.io.UrlCache;
import de.adrianwilke.music.ocde.OcdeParser;
import de.adrianwilke.music.ocde.OcdeSong;
import de.adrianwilke.music.youtube.YoutubeSearch;

/**
 * Music.
 *
 * @author Adrian Wilke
 */
public class Music {

	protected static final Logger LOGGER = LogManager.getLogger();

	protected static final int DEV = -1;
	protected static final int MODE_EXPORT_IMPORT_CSV = 1;
	protected static final int MODE_PRINT_SINGLEYEARCHARTS_SONG = 2;
	protected static final int MODE_YOUTUBE_SEARCH = 3;
	protected static final int MODE_PRINT_SONG = 4;

	/**
	 * Main entry point.
	 * 
	 * @param args[0] Google API key
	 * @param args[1] OCDE base URL
	 * @param args[2] UrlCache directory (optional)
	 */
	public static void main(String[] args) throws IOException {

		int mode = 1;

		if (args.length < 2) {
			System.err.println("Please provide parameters.");
			System.exit(1);
		}
		String googleApiKey = args[0];
		String ocdeBaseUrl = args[1];

		UrlCache urlCache;
		if (args.length == 3) {
			urlCache = new UrlCache(args[2]);
		} else {
			urlCache = new UrlCache();
		}

		new Music(urlCache, googleApiKey, mode, ocdeBaseUrl);
	}

	public Music(UrlCache urlCache, String googleApiKey, int mode, String ocdeBaseUrl) throws IOException {

		if (mode == MODE_EXPORT_IMPORT_CSV) {
			exportImport(ocdeBaseUrl, urlCache, new File(System.getProperty("java.io.tmpdir"), "singleyearA.csv"),
					new File(System.getProperty("java.io.tmpdir"), "singleyearB.csv"));

		} else if (mode == MODE_PRINT_SINGLEYEARCHARTS_SONG) {
			printSingleYearChartsSong(ocdeBaseUrl, urlCache, 1994, 68);

		} else if (mode == MODE_YOUTUBE_SEARCH) {
			youtube(googleApiKey, "Beck");

		} else if (mode == MODE_PRINT_SONG) {
			Date date = new Date(2018, 11, 1);
			List<OcdeSong> charts = new OcdeParser(ocdeBaseUrl).parseSingleSongs(date, urlCache);
			printOcdeSong(ocdeBaseUrl, charts.get(12));
		}

	}

	public void youtube(String apiKey, String query) throws UnsupportedEncodingException, IOException {
		System.out.println(new YoutubeSearch(apiKey).get(URLEncoder.encode(query, "UTF-8")));
	}

	public void printOcdeSong(String ocdeBaseUrl, OcdeSong ocdeSong) throws IOException {
		System.out.println(ocdeSong);
		System.out.println(ocdeSong.getDetailsUrl(ocdeBaseUrl));
		System.out.println(ocdeSong.getCoverUrl(ocdeBaseUrl));
		System.out.println(ocdeSong.getYoutube());
	}

	public void printSingleYearChartsSong(String ocdeBaseUrl, UrlCache urlCache, int year, int rank)
			throws IOException {
		Map<Integer, List<OcdeSong>> ocdeSongs = getSingleYearCharts(ocdeBaseUrl, urlCache);
		System.out.println("Year " + year + ", rank " + rank + ":");
		System.out.println(ocdeSongs.get(year).get(rank - 1));
		System.out.println(ocdeSongs.get(year).get(rank - 1).getDetailsUrl(ocdeBaseUrl));
		System.out.println(ocdeSongs.get(year).get(rank - 1).getCoverUrl(ocdeBaseUrl));
		System.out.println(ocdeSongs.get(year).get(rank - 1).getYoutube());
	}

	public void exportImport(String ocdeBaseUrl, UrlCache urlCache, File csvOutFileA, File csvOutFileB)
			throws IOException {

		// TODO: Years and ranks not included

		Map<Integer, List<OcdeSong>> ocdeSongs = getSingleYearCharts(ocdeBaseUrl, urlCache);
		List<Song> allSongs = new LinkedList<Song>();
		for (List<OcdeSong> songsList : ocdeSongs.values()) {
			allSongs.addAll(songsList);
		}
		new Exporter().export(allSongs, csvOutFileA);

		new Importer().importFile(csvOutFileA);

		new Exporter().export(allSongs, csvOutFileB);
	}

	/**
	 * Gets years (keys) and charts of the respective year (values).
	 */
	public Map<Integer, List<OcdeSong>> getSingleYearCharts(String ocdeBaseUrl, UrlCache urlCache) throws IOException {
		OcdeParser ocde = new OcdeParser(ocdeBaseUrl);
		Map<Integer, URL> singleYearUrls = ocde.parseSingleYearUrls(urlCache);

		int songCounter = 0;
		Map<Integer, List<OcdeSong>> ocdeSongs = new TreeMap<Integer, List<OcdeSong>>();
		for (Entry<Integer, URL> entry : singleYearUrls.entrySet()) {
			List<OcdeSong> ocdeSongList = ocde.parseSingleYearSongs(entry.getValue(), urlCache);
			ocdeSongs.put(entry.getKey(), ocdeSongList);
			songCounter += ocdeSongList.size();
		}

		LOGGER.info("Parsed " + singleYearUrls.size() + " years and " + songCounter + " song entries.");
		return ocdeSongs;
	}

}