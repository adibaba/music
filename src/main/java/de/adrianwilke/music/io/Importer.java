package de.adrianwilke.music.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.adrianwilke.music.Features;
import de.adrianwilke.music.Song;
import de.adrianwilke.music.features.templates.Feature;

/**
 * Imports data in CSV files.
 *
 * @author Adrian Wilke
 */
public class Importer {

	protected static final Logger LOGGER = LogManager.getLogger();

	public List<Song> importFile(File file) throws IOException {
		List<Song> songs = new LinkedList<Song>();

		CSVParser csvParser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT.withFirstRecordAsHeader());

		List<String> featureIdsCsv = new LinkedList<String>();
		for (Entry<String, Integer> header : csvParser.getHeaderMap().entrySet()) {
			featureIdsCsv.add(header.getValue(), header.getKey());
			if (!Features.isFeatureDefined(header.getKey())) {
				// TODO Import unknown columns?				
				LOGGER.warn("Unknown feature. Will skip importing: " + header.getKey());
			}
		}

		for (CSVRecord csvRecord : csvParser.getRecords()) {
			Song song = new Song();

			importFeaturesLoop: for (String featureId : featureIdsCsv) {
				Feature<?> feature = Features.getFeatureforId(featureId);
				if (feature == null) {
					continue importFeaturesLoop;
				}
				song.addFeature(featureId, feature.importString(csvRecord.get(featureId)));
			}

			songs.add(song);
		}

		csvParser.close();

		LOGGER.info("Imported file: " + file.getAbsolutePath());

		return songs;
	}

}