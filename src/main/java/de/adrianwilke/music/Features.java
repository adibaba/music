package de.adrianwilke.music;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import de.adrianwilke.music.features.Artist;
import de.adrianwilke.music.features.Ocde;
import de.adrianwilke.music.features.Title;
import de.adrianwilke.music.features.Youtube;
import de.adrianwilke.music.features.templates.Feature;

/**
 * All features.
 *
 * @author Adrian Wilke
 */
public abstract class Features {

	protected static final Map<String, Feature<?>> features = new LinkedHashMap<String, Feature<?>>();

	static {
		features.put(Ids.TITLE, new Title(Ids.TITLE));
		features.put(Ids.ARTIST, new Artist(Ids.ARTIST));

		features.put(Ids.YOUTUBE, new Youtube(Ids.YOUTUBE));
		features.put(Ids.OCDE, new Ocde(Ids.OCDE));
	}

	public static final Collection<Feature<?>> getAllFeatures() {
		return features.values();
	}

	/**
	 * Returns a {@link LinkedHashMap#keySet()}, "which is normally the order in
	 * which keys were inserted".
	 */
	public static final Set<String> getAllFeatureIds() {
		return features.keySet();
	}

	/**
	 * @return the feature or null, if the feature is not known.
	 */
	public static final Feature<?> getFeatureforId(String id) {
		return features.get(id);
	}

	public static final boolean isFeatureDefined(String id) {
		return features.containsKey(id);
	}
}