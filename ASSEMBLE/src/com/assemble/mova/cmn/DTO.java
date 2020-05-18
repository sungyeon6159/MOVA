package com.assemble.mova.cmn;


public class DTO {

	private String searchDiv;
	private String searchWord;
	
	public DTO() {
		// TODO Auto-generated constructor stub
	}

	public DTO(String searchDiv, String searchWord) {

		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
	}

	public String getSearchDiv() {
		return searchDiv;
	}

	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	@Override
	public String toString() {
		return "DTO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", toString()=" + super.toString() + "]";
	}

}
