/**
 *<pre>
 * com.assemble.mova.dao
 * Class Name :MovieUserDetailDao
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
import com.assemble.mova.controller.LoginController;
import com.assemble.mova.controller.SearchListController;
import com.assemble.mova.vo.MovieDetailVO;
import com.assemble.mova.vo.MovieUserDetailVO;
import com.assemble.mova.vo.MovieVO;

public class MovieUserDetailDao implements WorkDiv {
	static Logger log = Logger.getLogger(MovieUserDetailDao.class);
	public List<MovieUserDetailVO> userDetailData = new ArrayList<MovieUserDetailVO>();
	public final String ADD_FILE = "src/file/movie_user_detail_info.csv";

	public MovieUserDetailDao() {

		userDetailData = readFile(ADD_FILE);// ADD_FILE경로에 있는 파일의 데이터를 읽어옴.
		display(userDetailData);
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 데이터 존재 유무 확인,Key는 Title
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

	public void display(List<MovieUserDetailVO> movieList) {
		for (MovieUserDetailVO vo : movieList) {
			log.debug("MovieUserDetailVO " + vo);
		}
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 데이터 존재 유무 확인,Key는 MovieCode,Userid
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : display
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param String
	 * @return MovieUserDetailVO
	 */

	public MovieUserDetailVO getSeachData1(String code, String id) {
		MovieUserDetailVO outVO = null;

		String mCode = code.trim();
		String mUserID = id.trim();

		outVO = new MovieUserDetailVO();
		outVO.setMovieCode(mCode);
		outVO.setUserid(mUserID);
		log.debug("MovieUserDetailVO 입력:" + outVO);
		return outVO;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  MovieCode,Userid 입력
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : display
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param String, String
	 * @return MovieUserDetailVO
	 */

	public MovieUserDetailVO getInputMoveCode(String movieCode, String userId) {
		MovieUserDetailVO outVO = null;

		String mCode = movieCode.trim();// 앞뒤 공간 삭제
		String mUserId = userId.trim();
		outVO = new MovieUserDetailVO();
		outVO.setMovieCode(mCode);
		outVO.setUserid(mUserId);
		log.debug("내가 찾을 데이터!!:" + outVO);
		return outVO;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : Userid 입력
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getInputMoveCode2
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param String
	 * @return MovieUserDetailVO
	 */
	
	public MovieUserDetailVO getInputMoveCode2( String userId) {
		MovieUserDetailVO outVO = null;

		
		String mUserId = userId.trim();
		outVO = new MovieUserDetailVO();
		outVO.setUserid(mUserId);
		log.debug("내가 찾을 데이터!!:" + outVO);
		return outVO;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 리뷰 y 값과 리뷰내용 입력 받기.
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getUdateReview
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieUserDetailVO, String
	 * @return MovieUserDetailVO
	 */

	// 유저 별 데이터 업데이트
	public MovieUserDetailVO getUdateReview(MovieUserDetailVO urdVO, String myReview) {

		urdVO.setReviewYN("y");
		urdVO.setMyReview(myReview);
		return urdVO;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 리뷰 y 값과 리뷰내용 입력 받기.
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getUdateLikeYN
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieUserDetailVO
	 * @return MovieUserDetailVO
	 */
	public MovieUserDetailVO getUdateLikeYN(MovieUserDetailVO urdVO) {
		urdVO.setLikeYN("y");
		return urdVO;
	}

	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 별점 y 값( 업데이트값)
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getUpdateScoreYN
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieUserDetailVO
	 * @return MovieUserDetailVO
	 */
	public MovieUserDetailVO getUpdateScoreYN(MovieUserDetailVO urdVO) {
		urdVO.setScoreYN("y");
		return urdVO;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 별점 y 값( 업데이트값)
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getUpdateChoiceStar
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieUserDetailVO,int
	 * @return MovieUserDetailVO
	 */

	public MovieUserDetailVO getUpdateChoiceStar(MovieUserDetailVO urdVO, int choiceStar) {
		urdVO.setChoiceScore(choiceStar);
		return urdVO;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 별점 y 값( 업데이트값)
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getMovieIDInputData
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieUserDetailVO
	 * @return MovieUserDetailVO
	 */
	public MovieUserDetailVO getMovieIDInputData(MovieUserDetailVO vo) {

		vo.setMovieCode(SearchListController.movieCode);
		vo.setUserid(LoginController.loginId);
		vo.setLikeYN("n");
		vo.setScoreYN("n");
		vo.setReviewYN("n");
		vo.setMyReview("리뷰를 작성하세요");

		log.debug("MovieUserDetailVO 기본값 입력:" + vo);
		return vo;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  데이터 저장
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : saveFile
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieUserDetailVO
	 * @return MovieUserDetailVO
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
			for (int i = 0; i < userDetailData.size(); i++) {
				MovieUserDetailVO vo = userDetailData.get(i);

				StringBuilder sb = new StringBuilder();
				sb.append(vo.getUserid() + ",");
				sb.append(vo.getMovieCode() + ",");
				sb.append(vo.getLikeYN() + ",");
				sb.append(vo.getReviewYN() + ",");
				sb.append(vo.getScoreYN() + ",");
				sb.append((vo.getMyReview() + ",").replaceAll("(\r\n|\r|\n|\n\r)", " "));
				sb.append(vo.getChoiceScore());

				// 마지막 라인에 "\n" 제거
				if ((i + 1) != userDetailData.size()) {
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
	 * 1. 설명 :  읽어온 데이터 출력
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : saveFile
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieUserDetailVO
	 * @return List
	 */
	public void disp(List<MovieUserDetailVO> userDetailData) {
		for (MovieUserDetailVO vo : userDetailData) {
			System.out.println(vo);
		}
	}

	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  csv 파일 읽기
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : readFile
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param String
	 * @return List
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
						dataArray[5], 
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
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  csv 파일로 저장
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_save
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param String
	 * @return int
	 */

	@Override
	public int do_save(DTO dto) {
		int flag = 0;
		MovieUserDetailVO vo = (MovieUserDetailVO) dto;

		userDetailData.add(vo);
		int saveCnt = saveFile(ADD_FILE);
		log.debug("총 "+saveCnt + "건 저장 되었습니다.");

		return flag;
	}
	
	
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  csv 파일 업데이트 
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_save
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param DTO
	 * @return int
	 */

	@Override
	public int do_update(DTO dto) {
		int flag = 0;
		MovieUserDetailVO modVO = (MovieUserDetailVO) dto;
		if (isMemberExists(modVO)) {
			flag = do_delete(modVO);
			if (flag > 0) {
				do_save(modVO);
			}

		}
		return flag;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  csv 파일 삭제
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_save
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param DTO
	 * @return int
	 */

	@Override
	public int do_delete(DTO dto) {
		int flag = 0;
		MovieUserDetailVO delVO = (MovieUserDetailVO) dto;
		// 반드시 역순으로 찾고 삭제 해야함.
		for (int i = userDetailData.size() - 1; i >= 0; i--) {
			MovieUserDetailVO vsVO = userDetailData.get(i);
			if (delVO.getMovieCode().equals(vsVO.getMovieCode()) && delVO.getUserid().equals(vsVO.getUserid())) {
				userDetailData.remove(i);
				flag++;
			}
		}

		// 파일에 저장
		if (flag != 0)
			saveFile(ADD_FILE);
		return flag;
	}
	
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  csv 파일 삭제
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_save
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param DTO
	 * @return int
	 */
	
	public boolean isMemberExists(MovieUserDetailVO vo) {
		boolean flag = false;

		for (int i = 0; i < userDetailData.size(); i++) {
			MovieUserDetailVO vsVO = userDetailData.get(i);

			if (vsVO.getMovieCode().equals(vo.getMovieCode()) && vsVO.getUserid().equals(vo.getUserid())) {
				flag = true;
				break;
			}

		}
		return flag;
	}
	
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  단건 조회
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_save
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param DTO
	 * @return DTO
	 */

	@Override
	public DTO do_selectOne(DTO dto) {
		MovieUserDetailVO vsVO = null;
		MovieUserDetailVO inVO = (MovieUserDetailVO) dto;
		String outMvId = null;
		MovieUserDetailVO resultVO = null;

		String inMvId = (inVO.getUserid() + inVO.getMovieCode()).trim();
		System.out.println(inMvId);

		// 읽어온데이터 담기!
		for (int i = 0; i < userDetailData.size(); i++) {
			vsVO = userDetailData.get(i);
			outMvId = (userDetailData.get(i).getUserid() + userDetailData.get(i).getMovieCode()).trim();
			log.debug("outMvId 읽어온 데이터" + i + " : " + outMvId);
			log.debug("확인결과: " + inMvId.matches(outMvId));
			if (inMvId.equals(outMvId)) {
				resultVO = vsVO;
				break;
			}
			log.debug("아웃풋:" + resultVO);
		}
		return resultVO;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  다건 조회
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_retrieve
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param DTO
	 * @return List
	 */

	@Override
	public List<?> do_retrieve(DTO dto) {
		List<MovieUserDetailVO> list = new ArrayList<MovieUserDetailVO>();

		// email검색
		MovieUserDetailVO inVO = (MovieUserDetailVO) dto;
		// Email검색 :검색 구분이=="1"

		try {
			for (int i = 0; i < userDetailData.size(); i++) {
				MovieUserDetailVO vsVO = userDetailData.get(i);

				if (vsVO.getMovieCode().matches(inVO.getMovieCode()) && vsVO.getUserid().matches(inVO.getUserid())) {
					list.add(vsVO);
					log.debug("userDetailData  검색 결과:" + vsVO);
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 :  다건 조회
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_retrieve2
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param DTO
	 * @return List
	 */
	public List<?> do_retrieve2(DTO dto) {
		List<MovieUserDetailVO> list = new ArrayList<MovieUserDetailVO>();

		// email검색
		MovieUserDetailVO inVO = (MovieUserDetailVO) dto;
		// Email검색 :검색 구분이=="1"

		try {
			for (int i = 0; i < userDetailData.size(); i++) {
				MovieUserDetailVO vsVO = userDetailData.get(i);

				if (vsVO.getUserid().equals(inVO.getUserid())) {
					
					if (vsVO.getLikeYN().equals("y")) {
						list.add(vsVO);
					}
					log.debug("userDetailData  검색 결과:" + vsVO);
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}