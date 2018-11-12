package de.adrianwilke.music.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.adrianwilke.music.Song;

/**
 * Exporter.
 *
 * @author Adrian Wilke
 */
public class Exporter {

	protected static final Logger LOGGER = LogManager.getLogger();
	
	public void export(List<Song> songs, File file) throws IOException {

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(file), CSVFormat.DEFAULT);

		csvPrinter.printRecord(songs.get(0).getFeatureIds());

		for (Song song : songs) {
			csvPrinter.printRecord(song.getFeaturesExportStrings());
		}

		csvPrinter.close();

		LOGGER.info("Exported file: " + file.getAbsolutePath());
	}

}