package de.adrianwilke.music.ocde;

import de.adrianwilke.music.Ids;
import de.adrianwilke.music.Song;
import de.adrianwilke.music.features.Artist;
import de.adrianwilke.music.features.Ocde;
import de.adrianwilke.music.features.Title;
import de.adrianwilke.music.features.Youtube;

/**
 * OCDE song.
 *
 * @author Adrian Wilke
 */
public class OcdeSong extends Song {

	public OcdeSong() {
		this.addFeature(Ids.TITLE, new Title(Ids.TITLE));
		this.addFeature(Ids.ARTIST, new Artist(Ids.ARTIST));
		this.addFeature(Ids.YOUTUBE, new Youtube(Ids.YOUTUBE));
		this.addFeature(Ids.OCDE, new Ocde(Ids.OCDE));
	}

	public String getOcde() {
		return features.containsKey(Ids.OCDE) ? features.get(Ids.OCDE).toString() : "?";
	}

	public String getDetailsUrl(String ocdeBaseUrl) {
		return ocdeBaseUrl + "/titel-details-" + getOcde();
	}

	public String getCoverUrl(String ocdeBaseUrl) {
		return ocdeBaseUrl + "/templates/gfktemplate/images/cover/" + getOcde() + "_s.jpg";
	}

}