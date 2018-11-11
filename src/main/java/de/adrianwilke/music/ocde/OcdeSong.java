package de.adrianwilke.music.ocde;

import de.adrianwilke.music.Song;

/**
 * OCDE song.
 *
 * @author Adrian Wilke
 */
public class OcdeSong extends Song {

	public static final String ID_OCDE = "ocde";
	public static final String ID_YOUTUBE = "youtube";

	/**
	 * OCDE ID
	 */
	public Integer ocde;

	/**
	 * YouTube video ID
	 */
	public String youtube;

	public String getDetailsUrl(String ocdeBaseUrl) {
		return ocdeBaseUrl + "/titel-details-" + ocde;
	}

	public String getCoverUrl(String ocdeBaseUrl) {
		return ocdeBaseUrl + "/templates/gfktemplate/images/cover/" + ocde + "_s.jpg";
	}

	public String getYoutubeUrl() {
		return (youtube == null) ? "" : "https://www.youtube.com/watch?v=" + youtube;
	}
}
