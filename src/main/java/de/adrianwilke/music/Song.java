package de.adrianwilke.music;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.adrianwilke.music.exceptions.NotSpecifiedException;
import de.adrianwilke.music.features.Youtube;
import de.adrianwilke.music.features.templates.Feature;

/**
 * Song.
 * 
 * @author Adrian Wilke
 */
public class Song {

	protected Map<String, Feature<?>> features = new LinkedHashMap<String, Feature<?>>();

	public void addFeature(String featureId, Feature<?> feature) {
		features.put(featureId, feature);
	}

	public Feature<?> getFeature(String featureId) {
		return features.get(featureId);
	}

	public void set(String featureId, String data) {
		features.get(featureId).importString(data);
	}

	/**
	 * Returns a {@link LinkedHashMap#keySet()}, "which is normally the order in
	 * which keys were inserted".
	 */
	public Set<String> getFeatureIds() {
		return features.keySet();
	}

	public List<String> getFeaturesExportStrings() {
		List<String> data = new LinkedList<String>();
		for (Feature<?> feature : features.values()) {
			data.add(feature.exportString());
		}
		return data;
	}

	public String getTitle() {
		return features.containsKey(Ids.TITLE) ? features.get(Ids.TITLE).toString() : "?";
	}

	public String getArtist() {
		return features.containsKey(Ids.ARTIST) ? features.get(Ids.ARTIST).toString() : "?";
	}

	public List<String> getYoutube() {
		List<String> list = new LinkedList<String>();
		try {
			if (features.containsKey(Ids.YOUTUBE)) {
				Youtube youtube = (Youtube) (features.get(Ids.YOUTUBE));
				return youtube.get();
			} else {
				throw new NotSpecifiedException();
			}
		} catch (NotSpecifiedException e) {
			return list;
		}
	}

	@Override
	public String toString() {
		return getArtist() + " - " + getTitle();
	}

}