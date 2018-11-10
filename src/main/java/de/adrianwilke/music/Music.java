package de.adrianwilke.music;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.adrianwilke.music.odde.OcdeParser;
import de.adrianwilke.music.odde.OcdeSong;

/**
 * Music.
 *
 * @author Adrian Wilke
 */
public class Music {

	protected static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) throws IOException {

		UrlCache urlCache = new UrlCache();

		OcdeParser ocde = new OcdeParser();
		Map<Integer, List<OcdeSong>> ocdeSongs = new TreeMap<Integer, List<OcdeSong>>();
		Map<Integer, URL> singleYearUrls = ocde.getSingleYearUrls(urlCache);
		for (Entry<Integer, URL> entry : singleYearUrls.entrySet()) {
			ocdeSongs.put(entry.getKey(), ocde.getSingleYearSongs(entry.getValue(), urlCache));
		}

		if ("write".equals("")) {
			File file = new File("singleyear.csv");
			CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(file), CSVFormat.DEFAULT);
			csvPrinter.printRecord(new Object[] { "Year", "Rank", "Artist", "Title", });

			for (Entry<Integer, List<OcdeSong>> ocdeYearSongs : ocdeSongs.entrySet()) {
				Integer year = ocdeYearSongs.getKey();
				Integer rank = 0;
				for (OcdeSong ocdeSong : ocdeYearSongs.getValue()) {
					List<String> list = new LinkedList<String>();
					list.add(year.toString());
					list.add((++rank).toString());
					list.add(ocdeSong.artist);
					list.add(ocdeSong.title);
					csvPrinter.printRecord(list);
				}
			}
			csvPrinter.close();
			LOGGER.info(file.getAbsolutePath());
		}

		int year = 1994;
		int rank = 68;
		if ("print".equals("")) {
			System.out.println(ocdeSongs.get(year).get(rank - 1));
			System.out.println(ocdeSongs.get(year).get(rank - 1).ocdeCover);
			System.out.println(ocdeSongs.get(year).get(rank - 1).ocdeDetails);
		}

	}

}