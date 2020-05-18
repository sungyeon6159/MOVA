package com.assemble.mova.vo;

import com.assemble.mova.cmn.DTO;

public class MovieDetailVO extends DTO {

	String movieCode;
	int totalLike;
	int scoreUserCount;
	int totalScore;
	String avgScore;
	int oneScore;
	int twoScore;
	int threeScore;
	int fourScore;
	int fiveScore;

	public MovieDetailVO() {

	}

	public MovieDetailVO(String movieCode, int totalLike, int scoreUserCount, int totalScore, String avgScore,
			int oneScore, int twoScore, int threeScore, int fourScore, int fiveScore) {
		this.movieCode = movieCode;
		this.totalLike = totalLike;
		this.scoreUserCount = scoreUserCount;
		this.totalScore = totalScore;
		this.avgScore = avgScore;
		this.oneScore = oneScore;
		this.twoScore = twoScore;
		this.threeScore = threeScore;
		this.fourScore = fourScore;
		this.fiveScore = fiveScore;
	}

	public String getMovieCode() {
		return movieCode;
	}

	public void setMovieCode(String movieCode) {
		this.movieCode = movieCode;
	}

	public int getTotalLike() {
		return totalLike;
	}

	public void setTotalLike(int totalLike) {
		this.totalLike = totalLike;
	}

	public int getScoreUserCount() {
		return scoreUserCount;
	}

	public void setScoreUserCount(int scoreUserCount) {
		this.scoreUserCount = scoreUserCount;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public String getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}

	public int getOneScore() {
		return oneScore;
	}

	public void setOneScore(int oneScore) {
		this.oneScore = oneScore;
	}

	public int getTwoScore() {
		return twoScore;
	}

	public void setTwoScore(int twoScore) {
		this.twoScore = twoScore;
	}

	public int getThreeScore() {
		return threeScore;
	}

	public void setThreeScore(int threeScore) {
		this.threeScore = threeScore;
	}

	public int getFourScore() {
		return fourScore;
	}

	public void setFourScore(int fourScore) {
		this.fourScore = fourScore;
	}

	public int getFiveScore() {
		return fiveScore;
	}

	public void setFiveScore(int fiveScore) {
		this.fiveScore = fiveScore;
	}

	@Override
	public String toString() {
		return "MovieDetailVO [movieCode=" + movieCode + ", totalLike=" + totalLike + ", scoreUserCount="
				+ scoreUserCount + ", totalScore=" + totalScore + ", avgScore=" + avgScore + ", oneScore=" + oneScore
				+ ", twoScore=" + twoScore + ", threeScore=" + threeScore + ", fourScore=" + fourScore + ", fiveScore="
				+ fiveScore + ", toString()=" + super.toString() + "]";
	}

}