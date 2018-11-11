package de.adrianwilke.music.ocde;

import java.net.MalformedURLException;
import java.net.URL;

import de.adrianwilke.music.Song;

/**
 * OCDE song.
 *
 * @author Adrian Wilke
 */
public class OcdeSong extends Song {

	public URL ocdeCover;
	public URL ocdeDetails;

	public OcdeSong setOcdeCoverUrl(String ocdeCover) throws MalformedURLException {
		this.ocdeCover = new URL(ocdeCover);
		return this;
	}

	public OcdeSong setOcdeDetailsUrl(String ocdeDetails) throws MalformedURLException {
		this.ocdeDetails = new URL(ocdeDetails);
		return this;
	}
}
