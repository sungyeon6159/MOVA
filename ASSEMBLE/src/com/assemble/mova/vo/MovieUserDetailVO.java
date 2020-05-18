package com.assemble.mova.vo;

import com.assemble.mova.cmn.DTO;

public class MovieUserDetailVO extends DTO {
	private String userid;
	private String movieCode;
	private String likeYN;
	private String reviewYN;
	private String scoreYN;
	private String myReview;
	private int choiceScore;

	public MovieUserDetailVO() {

	}

	public MovieUserDetailVO(String userid, String movieCode, String likeYN, String reviewYN, String scoreYN,
			String myReview, int choiceScore) {
	
		this.userid = userid;
		this.movieCode = movieCode;
		this.likeYN = likeYN;
		this.reviewYN = reviewYN;
		this.scoreYN = scoreYN;
		this.myReview = myReview;
		this.choiceScore = choiceScore;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMovieCode() {
		return movieCode;
	}

	public void setMovieCode(String movieCode) {
		this.movieCode = movieCode;
	}

	public String getLikeYN() {
		return likeYN;
	}

	public void setLikeYN(String likeYN) {
		this.likeYN = likeYN;
	}

	public String getReviewYN() {
		return reviewYN;
	}

	public void setReviewYN(String reviewYN) {
		this.reviewYN = reviewYN;
	}

	public String getScoreYN() {
		return scoreYN;
	}

	public void setScoreYN(String scoreYN) {
		this.scoreYN = scoreYN;
	}

	public String getMyReview() {
		return myReview;
	}

	public void setMyReview(String myReview) {
		this.myReview = myReview;
	}

	public int getChoiceScore() {
		return choiceScore;
	}

	public void setChoiceScore(int choiceScore) {
		this.choiceScore = choiceScore;
	}

	@Override
	public String toString() {
		return "MovieUserDetailVO [userid=" + userid + ", movieCode=" + movieCode + ", likeYN=" + likeYN + ", reviewYN="
				+ reviewYN + ", scoreYN=" + scoreYN + ", myReview=" + myReview + ", choiceScore=" + choiceScore
				+ ", toString()=" + super.toString() + "]";
	}


	

}
