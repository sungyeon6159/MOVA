/**
 *<pre>
 * com.assemble.mova.vo
 * Class Name : MovieVO.java
 * Description : 
 * Modification Information
 * 
 *   수정일      수정자              수정내용
 *  ---------   ---------   -------------------------------
 *  2019-12-11           최초생성
 *
 * @author 개발프레임웍크 실행환경 ASSEMBLE
 * @since 2019-12-11 
 * @version 1.0
 * 
 *
 *  Copyright (C) by ASSEMBLE All right reserved.
 * </pre>
 */
package com.assemble.mova.vo;

import com.assemble.mova.cmn.DTO;
/**
 * <pre>
 * com.assemble.mova.vo
 *    MovieVO.java
 * 
 * </pre>
 * @date : 2019. 12. 11. 오후 4:23:19
 * @version : 
 * @author : 이재원
 */
public class MovieVO extends DTO {

	private String movieCode;
	private String title;
	private String cast;
	private String genre;
	private String year;
	private String story;
	private String imgPath;
	
	public MovieVO() {
	}

	public MovieVO(String movieCode, String title, String cast, String genre, String year, String story,
			String imgPath) {
		
		this.movieCode = movieCode;
		this.title = title;
		this.cast = cast;
		this.genre = genre;
		this.year = year;
		this.story = story;
		this.imgPath = imgPath;
	}
	/**
	 * getMovieCode
	 * @return String
	 */
	public String getMovieCode() {
		return movieCode;
	}

	/**
	 * setMovieCode
	 * @param movieCode
	 */
	public void setMovieCode(String movieCode) {
		this.movieCode = movieCode;
	}
	/**
	 * getTitle
	 * 제목이 리턴된다. 
	 * @return String 
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * setTitle
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * getCast
	 * 출연진이 리턴된다.
	 * @return String
	 */
	public String getCast() {
		return cast;
	}
	/**
	 * setCast
	 * @param cast
	 */
	public void setCast(String cast) {
		this.cast = cast;
	}
	/**
	 * 장르가 리턴된다.
	 * @return String
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * setGenre
	 * @param genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * 개봉일이 리턴된다.
	 * @return String
	 */
	public String getYear() {
		return year;
	}
	/**
	 * setYear
	 * @param year
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * getStory
	 * 줄거리가 리턴된다.
	 * @return String
	 */
	
	public String getStory() {
		return story;
	}
	/**
	 * setStory
	 * @param story
	 */
	public void setStory(String story) {
		this.story = story;
	}
	/**
	 * getImgPath
	 * 사진경로가 리턴된다.
	 * @return String
	 */
	public String getImgPath() {
		return imgPath;
	}
	/**
	 * setImgPath
	 * @param imgPath
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "MovieDao [movieCode=" + movieCode + ", title=" + title + ", cast=" + cast + ", genre=" + genre
				+ ", year=" + year + ", story=" + story + ", imgPath=" + imgPath + "]";
	}

}
