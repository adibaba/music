package de.adrianwilke.music;

/**
 * Song.
 *
 * @author Adrian Wilke
 */
public class Song {

	public String artist;
	public String title;

	public Song setArtist(String artist) {
		this.artist = artist;
		return this;
	}

	public Song setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String toString() {
		return (artist == null ? "?" : artist) + " - " + (title == null ? "?" : title);
	}

}