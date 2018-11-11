package de.adrianwilke.music;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Date.
 *
 * @author Adrian Wilke
 */
public class Date implements Comparable<Date> {

	LocalDate localDate;

	public Date() {
		localDate = LocalDate.now();
	}

	public Date(LocalDate localDate) {
		this.localDate = localDate;
	}

	public Date(int year) {
		localDate = LocalDate.of(year, 1, 1);
	}

	public Date(int year, int month, int day) {
		localDate = LocalDate.of(year, month, day);
	}

	public Date nearDayOfWeek(DayOfWeek dayOfWeek, boolean next) {
		int weekday = this.localDate.getDayOfWeek().getValue();
		if (weekday != dayOfWeek.getValue()) {
			if (next) {
				return new Date(localDate.plusDays((dayOfWeek.getValue() + 7 - weekday) % 7));
			} else {
				return new Date(localDate.minusDays((weekday + 7 - dayOfWeek.getValue()) % 7));
			}
		} else {
			return new Date(this.localDate);
		}
	}

	public long toBerlinMillis() {
		return ZonedDateTime.of(localDate, LocalTime.of(0, 0), ZoneId.of("Europe/Berlin")).toEpochSecond() * 1000;
	}

	@Override
	public String toString() {
		return localDate.toString();
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	@Override
	public int compareTo(Date otherDate) {
		return this.localDate.compareTo(otherDate.getLocalDate());
	}
}