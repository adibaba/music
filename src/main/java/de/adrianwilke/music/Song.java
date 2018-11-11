package de.adrianwilke.music;

/**
 * Song.
 *
 * @author Adrian Wilke
 */
public class Song {

	public static final String ID_TITLE = "title";
	public static final String ID_ARTIST = "artist";

	/**
	 * Title of the song.
	 */
	public String title;

	/**
	 * String can contain multiple artists.
	 */
	public String artist;

	@Override
	public String toString() {
		return (artist == null ? "?" : artist) + " - " + (title == null ? "?" : title);
	}

}