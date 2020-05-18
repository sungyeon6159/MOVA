/**
 *<pre>
 * com.assemble.mova.dao
 * Class Name :MovieDetailDao
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.assemble.mova.cmn.DTO;
import com.assemble.mova.cmn.WorkDiv;
import com.assemble.mova.controller.SearchListController;
import com.assemble.mova.vo.MovieDetailVO;

public class MovieDetailDao implements WorkDiv {

	static Logger log = Logger.getLogger(MovieDetailDao.class);
	private List<MovieDetailVO> movieDetailData = new ArrayList<MovieDetailVO>();
	private final String ADD_FILE = "src/file/movie_detail_Info.csv";

	public MovieDetailDao() {
		movieDetailData = readFile(ADD_FILE);// ADD_FILE경로에 있는 파일의 데이터를 읽어옴.
		display(movieDetailData);
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 읽어온 데이터 출력
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : display
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param List
	 * @return void
	 */
	public void display(List<MovieDetailVO> movieList) {
		for (MovieDetailVO vo : movieList) {
			log.debug("movieList" + vo);
		}
	}

	/**
	 * 
	 * <pre>
	 * 1. 설명 : 데이터 존재 유무 확인,Key는 movieCoe
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : isMemberExists
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieDetailVO
	 * @return boolean
	 */

	public boolean isMemberExists(MovieDetailVO vo) {
		boolean flag = false;

		for (int i = 0; i < movieDetailData.size(); i++) {
			MovieDetailVO vsVO = movieDetailData.get(i);
			// 이메일을 비교, 데이터가 있으면 loop종료
			if (vsVO.getMovieCode().equals(vo.getMovieCode())) {
				flag = true;
				break;
			}

		}
		return flag;
	}

	

	/**
	 * 
	 * <pre>
	 * 1. 설명 : MovieCode ,setAvgScore 입력 받는다.
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getMovieInputData
	 * 
	 * @since : 2019. 12. 14.
	 * @author : 김주희
	 * @param MovieDetailVO
	 * @return MovieDetailVO
	 */
	
	public MovieDetailVO getMovieInputData(MovieDetailVO vo) {

		vo.setMovieCode(SearchListController.movieCode);
		vo.setAvgScore("0");

		log.debug("MovieDetailVO 입력:" + vo);
		return vo;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : My리뷰데이터를 입력 받아 VO 담는다
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getMovieInputData
	 * 
	 * @since : 2019. 12. 14.
	 * @author : 김주희
	 * @param MovieDetailVO,String
	 * @return MovieDetailVO
	 */

	public MovieDetailVO getInputData(MovieDetailVO vo, String update) {
		System.out.println("기존 디테일정보를 입력 받은 뒤 변경되는 My리뷰데이터를 업데이트한다.");
		String inputData = update.trim();// 앞뒤 공간 삭제

		// vo.setMyReview(update);
		log.debug("MovieDetailVO Update:" + vo);
		return vo;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :List에 있는 Data를 파일에 기록
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getMovieInputData
	 * 
	 * @since : 2019. 12. 14.
	 * @author : 김주희
	 * @param String
	 * @return int
	 */

	public int saveFile(String path) {
		int cnt = 0;
		FileWriter writer = null;
		BufferedWriter bw = null;
		File file = new File(path);

		try {
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);

			// --------------------------------------
			for (int i = 0; i < movieDetailData.size(); i++) {
				MovieDetailVO vo = movieDetailData.get(i);

				StringBuilder sb = new StringBuilder();

				sb.append(vo.getMovieCode() + ",");
				sb.append(vo.getTotalLike() + ",");
				sb.append(vo.getScoreUserCount() + ",");
				sb.append(vo.getTotalScore() + ",");
				sb.append(vo.getAvgScore() + ",");
				sb.append(vo.getOneScore() + ",");
				sb.append(vo.getTwoScore() + ",");
				sb.append(vo.getThreeScore() + ",");
				sb.append(vo.getFourScore() + ",");
				sb.append(vo.getFiveScore());

				// 마지막 라인에 "\n" 제거
				if ((i + 1) != movieDetailData.size()) {
					sb.append("\n");
				}

				cnt++;
				bw.write(sb.toString());

			}
			// --------------------------------------
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (null != bw) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :List를 콘솔에 출력
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getMovieInputData
	 * 
	 * @since : 2019. 12. 14.
	 * @author : 김주희
	 * @param movieDetailData
	 * @return void
	 */
	
	
	public void disp(List<MovieDetailVO> movieDetailData) {
		for (MovieDetailVO vo : movieDetailData) {
			log.debug("MovieDetailVO movieDetailData" + vo);
		}
	}

	/**
	 * <pre>
	 * 1. 설명 : movieDetailData.csv파일에서 데이터를 읽어 List<MovieDetailVO> 생성
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : readFie
	 * 
	 * @since : 2019. 12. 3.
	 * @author : 김주희
	 * 
	 * @param filePath
	 * @return List
	 */

	public List<MovieDetailVO> readFile(String filePath) {
		List<MovieDetailVO> movieData = new ArrayList<MovieDetailVO>();
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				// 홍길동,jamesol@paran.com,16321203,010-1234-5678
				String[] dataArray = line.split(",");
				MovieDetailVO vo = new MovieDetailVO(dataArray[0], Integer.parseInt(dataArray[1]),
						Integer.parseInt(dataArray[2]), Integer.parseInt(dataArray[3]), dataArray[4],
						Integer.parseInt(dataArray[5]), Integer.parseInt(dataArray[6]), Integer.parseInt(dataArray[7]),
						Integer.parseInt(dataArray[8]), Integer.parseInt(dataArray[9]));

				movieData.add(vo);
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

		return movieData;
	}

	/**
	 * <pre>
	 * 1. 개요 : 입력 받은 데이터 csv에 저장
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_save
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param dto
	 * @return
	 */
	@Override
	public int do_save(DTO dto) {
		int flag = 0;
		MovieDetailVO vo = (MovieDetailVO) dto;

		movieDetailData.add(vo);
		int saveCnt = saveFile(ADD_FILE);
		log.debug("총 " + saveCnt + "건 저장 되었습니다.");

		return flag;
	}

	/**
	 * <pre>
	 * 1. 개요 : 입력 받은 데이터로 업데이트. 
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_update
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param dto
	 * @return int
	 */
	@Override
	public int do_update(DTO dto) {
		int flag = 0;
		MovieDetailVO modVO = (MovieDetailVO) dto;
		if (isMemberExists(modVO)) {
			flag = do_delete(modVO);
			if (flag > 0) {
				do_save(modVO);
			}

		}
		return flag;
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_delete
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param dto
	 * @return int
	 */
	@Override
	public int do_delete(DTO dto) {
		int flag = 0;
		MovieDetailVO delVO = (MovieDetailVO) dto;
		// 반드시 역순으로 찾고 삭제 해야함.
		for (int i = movieDetailData.size() - 1; i >= 0; i--) {
			MovieDetailVO vsVO = movieDetailData.get(i);
			if (delVO.getMovieCode().equals(vsVO.getMovieCode())) {
				movieDetailData.remove(i);
				flag++;
			}
		}

		// 파일에 저장
		if (flag != 0)
			saveFile(ADD_FILE);

		return flag;
	}

	/**
	 * <pre>
	 * 1. 개요 : movieCode로 단건 조회. 
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_selectOne
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param dto
	 * @return MovieDetailVO
	 */
	@Override
	public DTO do_selectOne(DTO dto) {
		MovieDetailVO outVO = null;
		MovieDetailVO inVO = (MovieDetailVO) dto;

		for (int i = 0; i < movieDetailData.size(); i++) {
			MovieDetailVO vsVO = movieDetailData.get(i);
			if (vsVO.getMovieCode().equals(inVO.getMovieCode())) {
				outVO = vsVO;
				break;
			}
		}

		return outVO;
	}

	
	/**
	 * <pre>
	 * 1. 개요 : 좋아요 총갯수 업데이트. 
	 * 2. 처리내용 :
	 * </pre>
	 *
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieDetailVO , int
	 * @return MovieDetailVO
	 */
	
	public MovieDetailVO getUpdateLikeTotal(MovieDetailVO urdVO, int likeTotal) {
		urdVO.setTotalLike(likeTotal);
		log.debug("getUpdateLikeTotal 업데이트 된 값: " + urdVO);
		return urdVO;
	}
	
	
	/**
	 * <pre>
	 * 1. 개요 : one Score 업데이트
	 * 2. 처리내용 :
	 * </pre>
	 *
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieDetailVO, int, int, String, int
	 * @return MovieDetailVO
	 */

	public MovieDetailVO getUpdateScore1(MovieDetailVO urdVO, int userCount, int totalScore, String avg, int score) {
		urdVO.setScoreUserCount(userCount);
		urdVO.setTotalScore(totalScore);
		urdVO.setAvgScore(avg);
		urdVO.setOneScore(score);
		log.debug("업데이트 된 값1:" + urdVO);
		return urdVO;
	}
	
	
	/**
	 * <pre>
	 * 1. 개요 : two Score 업데이트
	 * 2. 처리내용 :
	 * </pre>
	 *
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieDetailVO, int, int, String, int
	 * @return MovieDetailVO
	 */

	public MovieDetailVO getUpdateScore2(MovieDetailVO urdVO, int userCount, int totalScore, String avg, int score) {
		urdVO.setScoreUserCount(userCount);
		urdVO.setTotalScore(totalScore);
		urdVO.setAvgScore(avg);
		urdVO.setTwoScore(score);
		log.debug("업데이트 된 값2:" + urdVO);
		return urdVO;
	}
	
	
	/**
	 * <pre>
	 * 1. 개요 : three Score 업데이트
	 * 2. 처리내용 :
	 * </pre>
	 *
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieDetailVO, int, int, String, int
	 * @return MovieDetailVO
	 */

	public MovieDetailVO getUpdateScore3(MovieDetailVO urdVO, int userCount, int totalScore, String avg, int score) {
		urdVO.setScoreUserCount(userCount);
		urdVO.setTotalScore(totalScore);
		urdVO.setAvgScore(avg);
		urdVO.setThreeScore(score);
		log.debug("업데이트 된 값3:" + urdVO);
		return urdVO;
	}

	
	
	/**
	 * <pre>
	 * 1. 개요 : four Score 업데이트
	 * 2. 처리내용 :
	 * </pre>
	 *
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieDetailVO, int, int, String, int
	 * @return MovieDetailVO
	 */

	public MovieDetailVO getUpdateScore4(MovieDetailVO urdVO, int userCount, int totalScore, String avg, int score) {
		urdVO.setScoreUserCount(userCount);
		urdVO.setTotalScore(totalScore);
		urdVO.setAvgScore(avg);
		urdVO.setFourScore(score);
		log.debug("업데이트 된 값4:" + urdVO);
		return urdVO;
	}

	
	/**
	 * <pre>
	 * 1. 개요 : five Score 업데이트
	 * 2. 처리내용 :
	 * </pre>
	 *
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieDetailVO, int, int, String, int
	 * @return MovieDetailVO
	 */
	
	public MovieDetailVO getUpdateScore5(MovieDetailVO urdVO, int userCount, int totalScore, String avg, int score) {
		urdVO.setScoreUserCount(userCount);
		urdVO.setTotalScore(totalScore);
		urdVO.setAvgScore(avg);
		urdVO.setFiveScore(score);
		log.debug("업데이트 된 값5:" + urdVO);
		return urdVO;
	}
	
	
	/**
	 * <pre>
	 * 1. 개요 : movieCode 입력 받아 MovieDetailVO에 넣어 리턴.
	 * 2. 처리내용 :
	 * </pre>
	 *
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param String
	 * @return MovieDetailVO
	 */
	public MovieDetailVO getInputMoveCode(String movieCode) {
		MovieDetailVO outVO = null;
		String inputData = movieCode;
		inputData = inputData.trim();// 앞뒤 공간 삭제
		outVO = new MovieDetailVO();
		outVO.setMovieCode(movieCode);
		log.debug("MovieDetailVO 업데이트 된 값:" + outVO);
		return outVO;
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_retrieve
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param dto
	 * @return
	 */

	@Override
	public List<?> do_retrieve(DTO dto) {
		/*
		 * List<MovieDetailVO> list = new ArrayList<MovieDetailVO>(); // email검색
		 * MovieDetailVO inVO = (MovieDetailVO) dto; // Email검색 :검색 구분이=="1" if
		 * (inVO.getSearchDiv().equals("1")) {
		 * 
		 * for (int i = 0; i < movieDetailData.size(); i++) { MovieDetailVO vsVO =
		 * movieDetailData.get(i); // %james% if (vsVO.getMovieCode().matches(".*" +
		 * inVO.getSearchWord() + ".*")) { list.add(vsVO); } }
		 * 
		 * } else if (inVO.getSearchDiv().equals("2")) {// 이름 for (int i = 0; i <
		 * movieDetailData.size(); i++) { MovieDetailVO vsVO = movieDetailData.get(i);
		 * if (vsVO.getMovieCode().matches(".*" + inVO.getSearchWord() + ".*")) {
		 * list.add(vsVO); } } }
		 */
		return null;
	}

}