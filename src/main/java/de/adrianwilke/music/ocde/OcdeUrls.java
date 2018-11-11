package de.adrianwilke.music.ocde;

/**
 * OCDE URLs.
 *
 * @author Adrian Wilke
 */
public abstract class OcdeUrls {

	public static final String URL_SINGLE_YEAR_OVERVIEW = "/charts/single-jahr";

	/**
	 * Has to be extended by 'YYYY'
	 */
	public static final String URL_SINGLE_YEAR = URL_SINGLE_YEAR_OVERVIEW + "/for-date-";

}