package de.adrianwilke.music.odde;

import java.net.MalformedURLException;
import java.net.URL;

import de.adrianwilke.music.Song;

/**
 * ocde song.
 * 
 * Online data structures of 2018-11-10.
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
