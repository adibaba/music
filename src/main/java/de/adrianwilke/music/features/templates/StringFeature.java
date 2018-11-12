package de.adrianwilke.music.features.templates;

import de.adrianwilke.music.exceptions.NotSpecifiedException;
import de.adrianwilke.music.exceptions.NotSupportetException;

/**
 * Default implementation for string feature.
 *
 * @author Adrian Wilke
 */
public abstract class StringFeature implements Feature<String> {

	protected String id;
	protected String data;

	public StringFeature(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String get() throws NotSpecifiedException {
		if (data == null) {
			throw new NotSpecifiedException();
		} else {
			return data;
		}
	}

	@Override
	public void set(String data) {
		this.data = data;
	}

	@Override
	public void add(String data) throws NotSupportetException {
		throw new NotSupportetException();
	}

	@Override
	public StringFeature importString(String data) {
		set(data);
		return this;
	}

	@Override
	public String exportString() {
		return data == null ? "" : data;
	}

	@Override
	public String toString() {
		return data == null ? "?" : data;
	}

}