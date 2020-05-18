/**
 *<pre>
 * com.assemble.mova.dao
 * Class Name :MovieMoreReviewDao
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

package com.assemble.mova.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.assemble.mova.cmn.DTO;
import com.assemble.mova.cmn.WorkDiv;
import com.assemble.mova.controller.SearchListController;
import com.assemble.mova.vo.MovieDetailVO;
import com.assemble.mova.vo.MovieUserDetailVO;
import com.assemble.mova.vo.MovieVO;

public class MovieMoreReviewDao implements WorkDiv {

	static Logger log = Logger.getLogger(MovieMoreReviewDao.class);
	private List<MovieUserDetailVO> movieDetailData = new ArrayList<MovieUserDetailVO>();
	private final String ADD_FILE = "src/file/movie_user_detail_info.csv";

	public MovieMoreReviewDao() {
		movieDetailData = readFile(ADD_FILE);// ADD_FILE경로에 있는 파일의 데이터를 읽어옴.
		display(movieDetailData);
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 읽어온 데이터 출력
	 * 2. 처리내용 :
	 * </pre>
	 * Method Name : display
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param List
	 * @return void
	 */

	public void display(List<MovieUserDetailVO> movieList) {
		for (MovieUserDetailVO vo : movieList) {
			System.out.println(vo);
		}
	}

	@Override
	public int do_save(DTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int do_update(DTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int do_delete(DTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DTO do_selectOne(DTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> do_retrieve(DTO dto) {

		return null;
	}
	
	
	
	/**
	 * <pre>
	 * 1. 개요 : ReviewYN 값이 y 인 사용자를 입력 받는다.
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_retrieve2
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @return : List
	 */

	public List<?> do_retrieve2() {
		List<MovieUserDetailVO> list = new ArrayList<MovieUserDetailVO>(); // email검색

		try {
			for (int i = 0; i < movieDetailData.size(); i++) {
				MovieUserDetailVO vsVO = movieDetailData.get(i);

				// %james%
				if (vsVO.getReviewYN().equals("y") && vsVO.getMovieCode().equals(SearchListController.movieCode)) {
					log.debug("리뷰 더보기" + i + ":" + vsVO);
					list.add(vsVO);
				} else {
					log.debug("리뷰 더보기 데이터가 없습니다.");
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();

			System.out.println(e.getMessage());
		}

		return list;
	}

	
	
	/**
	 * <pre>
	 * 1. 개요 : csv파일 읽어오기
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : readFile
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param : String
	 * @return : List
	 */
	
	public List<MovieUserDetailVO> readFile(String filePath) {
		List<MovieUserDetailVO> userDetailData = new ArrayList<MovieUserDetailVO>();
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = "";

			while ((line = br.readLine()) != null) {
				// 홍길동,jamesol@paran.com,16321203,010-1234-5678
				String[] dataArray = line.split(",");
				MovieUserDetailVO vo = new MovieUserDetailVO(
						dataArray[0], 
						dataArray[1], 
						dataArray[2], 
						dataArray[3],
						dataArray[4], 
						dataArray[5].replaceAll("(\r\n|\r|\n|\n\r)", " "),
						Integer.parseInt(dataArray[6]));
				userDetailData.add(vo);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return userDetailData;
	}

}
