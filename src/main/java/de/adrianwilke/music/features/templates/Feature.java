package de.adrianwilke.music.features.templates;

import de.adrianwilke.music.Ids;
import de.adrianwilke.music.exceptions.NotSpecifiedException;
import de.adrianwilke.music.exceptions.NotSupportetException;

/**
 * Methods of features to implement import and export.
 *
 * @author Adrian Wilke
 */
public interface Feature<Type> {

	/**
	 * Like specified in {@link Ids}. Used for identification in import and export.
	 */
	public String getId();

	/**
	 * Returns value.
	 * 
	 * @throws NotSpecifiedException if data is null.
	 */
	public Type get() throws NotSpecifiedException;

	/**
	 * Overwrites value.
	 */
	public void set(Type data);

	/**
	 * Add to collections.
	 * 
	 * @throws NotSupportetException if no collection or appropriate underlying data
	 *                               structure available.
	 */
	public void add(Type data) throws NotSupportetException;

	/**
	 * Imports data in string format.
	 */
	public Feature<Type> importString(String data);

	/**
	 * Exports data in string format.
	 */
	public String exportString();

}