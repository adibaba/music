package de.adrianwilke.music.ocde;

import de.adrianwilke.music.Song;

/**
 * OCDE song.
 *
 * @author Adrian Wilke
 */
public class OcdeSong extends Song {

	public static final String ID_OCDE = "ocde";

	/**
	 * OCDE ID
	 */
	public Integer ocde;

	public String getDetailsUrl(String ocdeBaseUrl) {
		return ocdeBaseUrl + "/titel-details-" + ocde;
	}

	public String getCoverUrl(String ocdeBaseUrl) {
		return ocdeBaseUrl + "/templates/gfktemplate/images/cover/" + ocde + "_s.jpg";
	}
}
