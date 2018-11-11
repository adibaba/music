package de.adrianwilke.music;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	protected static final int MODE_CREATE_SINGLE_CSV = 1;
	protected static final int MODE_PRINT_SONG = 2;
	protected static final int MODE_YOUTUBE_SEARCH = 3;

	/**
	 * Main entry point.
	 * 
	 * @param args[0] Google API key
	 * @param args[1] OCDE base URL
	 * @param args[2] UrlCache directory (optional)
	 */
	public static void main(String[] args) throws IOException {

		int mode = 0;

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

		if (mode == MODE_CREATE_SINGLE_CSV) {
			writeSingleYearChartsToCsvFile(ocdeBaseUrl, urlCache,
					new File(System.getProperty("java.io.tmpdir"), "singleyear.csv"));

		} else if (mode == MODE_PRINT_SONG) {
			printSingleYearChartsSong(ocdeBaseUrl, urlCache, 1994, 68);

		} else if (mode == MODE_YOUTUBE_SEARCH) {
			youtube(googleApiKey, "Beck");
		}
	}

	public void youtube(String apiKey, String query) throws UnsupportedEncodingException, IOException {
		System.out.println(new YoutubeSearch(apiKey).get(URLEncoder.encode(query, "UTF-8")));
	}

	public void printSingleYearChartsSong(String ocdeBaseUrl, UrlCache urlCache, int year, int rank)
			throws IOException {
		Map<Integer, List<OcdeSong>> ocdeSongs = getSingleYearCharts(ocdeBaseUrl, urlCache);
		System.out.println("Year " + year + ", rank " + rank + ":");
		System.out.println(ocdeSongs.get(year).get(rank - 1));
		System.out.println(ocdeSongs.get(year).get(rank - 1).getDetailsUrl(ocdeBaseUrl));
		System.out.println(ocdeSongs.get(year).get(rank - 1).getCoverUrl(ocdeBaseUrl));
	}

	public void writeSingleYearChartsToCsvFile(String ocdeBaseUrl, UrlCache urlCache, File csvOutFile)
			throws IOException {

		Map<Integer, List<OcdeSong>> ocdeSongs = getSingleYearCharts(ocdeBaseUrl, urlCache);

		CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvOutFile), CSVFormat.DEFAULT);
		csvPrinter.printRecord(new Object[] { "year", "rank", Song.ID_ARTIST, Song.ID_TITLE, OcdeSong.ID_OCDE });

		for (Entry<Integer, List<OcdeSong>> ocdeYearSongs : ocdeSongs.entrySet()) {
			Integer year = ocdeYearSongs.getKey();
			Integer rank = 0;
			for (OcdeSong ocdeSong : ocdeYearSongs.getValue()) {
				List<String> list = new LinkedList<String>();
				list.add(year.toString());
				list.add((++rank).toString());
				list.add(ocdeSong.artist);
				list.add(ocdeSong.title);
				list.add(ocdeSong.ocde.toString());
				csvPrinter.printRecord(list);
			}
		}
		csvPrinter.close();
		LOGGER.info("Wrote file: " + csvOutFile.getAbsolutePath());
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