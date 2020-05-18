/**
 *<pre>
 * com.assemble.mova.dao
 * Class Name :MovieDao.java
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
import org.apache.log4j.Logger;
import com.assemble.mova.cmn.DTO;
import com.assemble.mova.cmn.WorkDiv;
import com.assemble.mova.vo.MovieVO;

public class MovieDao implements WorkDiv {
	static Logger log = Logger.getLogger(MovieDao.class);

	private List<MovieVO> movieInfoData = new ArrayList<MovieVO>();
	private final String ADD_FILE = "src/file/movie_info.csv";

	public MovieDao() {
		movieInfoData = readFile(ADD_FILE);// ADD_FILE경로에 있는 파일의 데이터를 읽어옴.
		display(movieInfoData);
	}

	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 데이터 존재 유무 확인,Key는 Title
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : isMemberExists
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param movieList
	 * @return void
	 */
	
	public void display(List<MovieVO> movieList) {
		for (MovieVO vo : movieList) {
			log.debug("movieListVO:" + vo);
		}
	}

	/**
	 * 
	 * <pre>
	 * 1. 설명 : 데이터 존재 유무 확인,Key는 Title
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : isMemberExists
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param MovieVO
	 * @return boolean
	 */
	
	public boolean isMemberExists(MovieVO vo) {
		boolean flag = false;

		for (int i = 0; i < movieInfoData.size(); i++) {
			MovieVO vsVO = movieInfoData.get(i);
			// 이메일을 비교, 데이터가 있으면 loop종료

			if (vsVO.getTitle().equals(vo.getTitle())) {
				flag = true;
				break;
			}

		}
		return flag;
	}

	/**
	 * 
	 * <pre>
	 * 1. 설명 : 쉼표로 구분된 데이터를 입력 받아 MovieVO 돌려 준다.
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : getInputData
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param String
	 * @return MovieVO
	 */

	public MovieVO getInputMoveCode(String movieCode) {
		MovieVO outVO = null;
		String inputData = movieCode;
		inputData = inputData.trim();// 앞뒤 공간 삭제
		outVO = new MovieVO();
		outVO.setMovieCode(movieCode);
		log.debug("getInputMoveCode 입력:" + outVO);
		return outVO;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 쉼표로 구분된 데이터를 입력 받아 MovieVO 돌려 준다.
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : readMovieData
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param String
	 * @return List
	 */
	
	
	public List<MovieVO> readMovieData(String movieCode) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		
		for (int i = 0; i < movieInfoData.size(); i++) {
			MovieVO vsVO = movieInfoData.get(i);
			// %james%
			if (vsVO.getMovieCode().matches(".*" + movieCode + ".*")) {
				list.add(vsVO);

			}
		}

		return list;
	}

	
	/**
	 * 
	 * <pre>
	 * 1. 설명 : 저장하기
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : saveFile
	 * 
	 * @since : 2019. 12. 4.
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
			for (int i = 0; i < movieInfoData.size(); i++) {
				MovieVO vo = movieInfoData.get(i);

				StringBuilder sb = new StringBuilder();
				sb.append(vo.getMovieCode() + ",");
				sb.append(vo.getTitle() + ",");
				sb.append(vo.getCast() + ",");
				sb.append(vo.getGenre() + ",");
				sb.append(vo.getYear() + ",");
				sb.append((vo.getStory() + ",").replaceAll("(\r\n|\r|\n|\n\r)", " "));
				sb.append(vo.getImgPath());

				// 마지막 라인에 "\n" 제거
				if ((i + 1) != movieInfoData.size()) {
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
	 * 1. 설명 : List를 콘솔에 출력
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : saveFile
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param List
	 * @return void
	 */

	public void disp(List<MovieVO> movieInfoData) {
		for (MovieVO vo : movieInfoData) {
			log.debug("movieInfoData: " + vo);
		}
	}

	/**
	 * <pre>
	 * 1. 설명 : movieInfoData.csv파일에서 데이터를 읽어 List<MovieVO> 생성
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : readFie
	 * 
	 * @since : 2019. 12. 3.
	 * @author : 김주희
	 * @param filePath
	 * @return List
	 */
	public List<MovieVO> readFile(String filePath) {
		List<MovieVO> movieData = new ArrayList<MovieVO>();
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				// 홍길동,jamesol@paran.com,16321203,010-1234-5678
				String[] dataArray = line.split(",");
				MovieVO vo = new MovieVO(dataArray[0], dataArray[1], dataArray[2], dataArray[3], dataArray[4],
						dataArray[5].replaceAll("(\r\n|\r|\n|\n\r)", " "), dataArray[6]);

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
	 * 1. 개요 : 
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_save
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희

	 * @param dto
	 * @return int
	 */
	
	@Override
	public int do_save(DTO dto) {
		int flag = 0;
		MovieVO vo = (MovieVO) dto;

		movieInfoData.add(vo);
		int saveCnt = saveFile(ADD_FILE);
		log.debug("총" + saveCnt + "건 저장 되었습니다.");
		return flag;
	}

	/**
	 * <pre>
	 * 1. 개요 : 입력 받은 데이터로 업데이트
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
		MovieVO modVO = (MovieVO) dto;
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
	 * 1. 개요 : 입력받은 데이터 삭제
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
		MovieVO delVO = (MovieVO) dto;
		// 반드시 역순으로 찾고 삭제 해야함.
		for (int i = movieInfoData.size() - 1; i >= 0; i--) {
			MovieVO vsVO = movieInfoData.get(i);
			if (delVO.getTitle().equals(vsVO.getTitle())) {
				movieInfoData.remove(i);
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
	 * 1. 개요 : 
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_selectOne
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param dto
	 * @return MovieVO
	 */
	@Override
	public DTO do_selectOne(DTO dto) {
		MovieVO outVO = null;
		MovieVO inVO = (MovieVO) dto;

		for (int i = 0; i < movieInfoData.size(); i++) {
			MovieVO vsVO = movieInfoData.get(i);
			if (vsVO.getMovieCode().equals(inVO.getMovieCode())) {
				outVO = vsVO;
				break;
			}
		}

		return outVO;
	}

	/**
	 * <pre>
	 * 1. 개요 : 데이터 파일 읽어오기 다건.
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
		List<MovieVO> list = new ArrayList<MovieVO>();
		// email검색
		MovieVO inVO = (MovieVO) dto;
		// Email검색 :검색 구분이=="1"
		if (inVO.getSearchDiv().equals("1")) {

			for (int i = 0; i < movieInfoData.size(); i++) {
				MovieVO vsVO = movieInfoData.get(i);
				// %james%
				if (vsVO.getTitle().matches(".*" + inVO.getSearchWord() + ".*")) {
					list.add(vsVO);
				}
			}

		} else if (inVO.getSearchDiv().equals("2")) {// 이름
			for (int i = 0; i < movieInfoData.size(); i++) {
				MovieVO vsVO = movieInfoData.get(i);
				if (vsVO.getTitle().matches(".*" + inVO.getSearchWord() + ".*")) {
					list.add(vsVO);
				}
			}
		}
		return list;
	}
	
	
	
	/**
	 * <pre>
	 * 1. 개요 : 데이터 파일 읽어오기 다건.
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * Method Name : do_retrieve2
	 * 
	 * @since : 2019. 12. 4.
	 * @author : 김주희
	 * @param dto
	 * @return
	 */

	public List<?> do_retrieve2(DTO dto) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieVO inVO = (MovieVO) dto;
		

			for (int i = 0; i < movieInfoData.size(); i++) {
				MovieVO vsVO = movieInfoData.get(i);
				// %james%
				if (vsVO.getMovieCode().trim().equals(inVO.getMovieCode().trim())) {
					list.add(vsVO);
				}
			}

		
		return list;
	}

}