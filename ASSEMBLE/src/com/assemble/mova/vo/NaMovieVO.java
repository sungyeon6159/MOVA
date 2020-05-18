package com.assemble.mova.vo;

public class NaMovieVO {

	private String subtitle;
	private String link;
	private String image;
	private String pubDate;
	private String director;
	private String actor;
	private String userRating;
	
	public NaMovieVO() {
	
	}
	
	public NaMovieVO(String subtitle, String link, String image, String pubDate, String director, String actor,
			String userRating) {
	
		this.subtitle = subtitle;
		this.link = link;
		this.image = image;
		this.pubDate = pubDate;
		this.director = director;
		this.actor = actor;
		this.userRating = userRating;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getUserRating() {
		return userRating;
	}

	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}

	@Override
	public String toString() {
		return "NaMovieVO [subtitle=" + subtitle + ", link=" + link + ", image=" + image + ", pubDate=" + pubDate
				+ ", director=" + director + ", actor=" + actor + ", userRating=" + userRating + "]";
	}
	
	
	
	
	
}