package de.adrianwilke.music.features.templates;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.adrianwilke.music.exceptions.NotSpecifiedException;

/**
 * Default implementation for list of strings to represent multiple values.
 *
 * @author Adrian Wilke
 */
public abstract class StringListFeature implements Feature<List<String>> {

	protected String id;
	protected List<String> data;

	public StringListFeature(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public List<String> get() throws NotSpecifiedException {
		if (data == null) {
			throw new NotSpecifiedException();
		} else {
			return data;
		}
	}

	@Override
	public void set(List<String> value) {
		this.data = value;
	}

	@Override
	public void add(List<String> value) {
		if (value == null) {
			value = new LinkedList<String>();
		}
		this.data.addAll(value);
	}

	@Override
	public StringListFeature importString(String s) {
		if (!s.trim().isEmpty()) {
			this.data = Arrays.asList(s.substring(1, s.length() - 1).split(", "));
		}
		return this;
	}

	@Override
	public String exportString() {
		return data == null ? "" : data.toString();
	}

	@Override
	public String toString() {
		return data == null ? "?" : data.toString();
	}
}