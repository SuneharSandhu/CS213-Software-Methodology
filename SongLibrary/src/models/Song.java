/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package models;

public class Song implements Comparable<Song> {

	private String name, artist, album, year;

	public Song(String name, String artist, String album, String year) {
		this.setName(name);
		this.setArtist(artist);
		this.setAlbum(album);
		this.setYear(year);
	}

	public String getName() {
		return this.name;
	}

	public String getArtist() {
		return this.artist;
	}

	public String getAlbum() {
		return this.album;
	}

	public String getYear() {
		return this.year;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int compareTo(Song comparable) {
		if (this.name.equals(comparable.name))
			return this.artist.toLowerCase().compareTo(comparable.artist.toLowerCase());

		return this.name.toLowerCase().compareTo(comparable.name.toLowerCase());
	}

	@Override
	public String toString() {
		return this.name + " - " + this.artist;
	}
}
