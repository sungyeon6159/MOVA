/**
 *<pre>
 * com.assemble.mova.dao
 * Class Name : AdminDao.java
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
import com.assemble.mova.controller.AdminController;
import com.assemble.mova.controller.SearchListController;
import com.assemble.mova.vo.MovieDetailVO;
import com.assemble.mova.vo.MovieVO;

/**
 * <pre>
 * com.assemble.mova.dao
 *    AdminDao.java
 * </pre>
 * @date : 2019. 12. 11. 오후 4:23:19
 * @version : 
 * @author : 이재원
 */
public class AdminDao implements WorkDiv {
	static Logger log = Logger.getLogger(AdminDao.class);
	private List<MovieVO> movieInfoData = new ArrayList<MovieVO>();
	private final String ADD_FILE = "src/file/movie_info.csv";

	public AdminDao() {
		movieInfoData = readFile(ADD_FILE);// ADD_FILE경로에 있는 파일의 데이터를 읽어옴.
		display(movieInfoData);
	}

	public void display(List<MovieVO> movieList) {
		for (MovieVO vo : movieList) {
			log.debug("movieInfoDataVO:" + vo);
		}
	}

	public MovieVO getMovieInputData(MovieVO vo) {
		log.debug("getMovieInputData 입력:" + vo);
		return vo;
	}
	/**
	 * isMemberExists
	 * 저장된 코드 다음으로 부터+1씩 자동 입력
	 * @author 이재원
	 * @param vo
	 * @return boolean
	 */
	public boolean isMemberExists(MovieVO vo) {
		boolean flag = false;

		for (int i = 0; i < movieInfoData.size(); i++) {
			MovieVO vsVO = movieInfoData.get(i);

			if (vsVO.getMovieCode().equals(vo.getMovieCode())) {
				flag = true;
				break;
			}

		}
		return flag;
	}
	/**
	 * getInputMoveCode
	 * movie code를 받아서 outVO에 넣어준다.
	 * @author 이재원
	 * @param movieCode
	 * @return MovieVO 
	 */
	public MovieVO getInputMoveCode(String movieCode) {
		MovieVO outVO = null;
		String inputData = movieCode;
		inputData = inputData.trim();// 앞뒤 공간 삭제
		outVO = new MovieVO();
		outVO.setMovieCode(movieCode);
		log.debug("입력:" + outVO);
		return outVO;
	}
	/**
	 * saveFile
	 * List에 있는 Data를 파일에 기록
	 * @author 이재원
	 * @param path
	 * @return int
	 */
	// List에 있는 Data를 파일에 기록
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
	 * disp
	 * List를 콘솔에 출력
	 * @author 이재원
	 * @param movieInfoData
	 * @return void
	 */
	public void disp(List<MovieVO> movieInfoData) {
		for (MovieVO vo : movieInfoData) {
			System.out.println(vo);
		}
	}

	/**
	 * readFile
	 * 파일 읽어오기
	 * @author 이재원
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
	 * do_save
	 * 파일 저장하기
	 * @author 이재원
	 * @param dto
	 * @return int
	 */
	@Override
	public int do_save(DTO dto) {
		int flag = 0;
		MovieVO vo = (MovieVO) dto;

		movieInfoData.add(vo);
		int saveCnt = saveFile(ADD_FILE);
		System.out.println(saveCnt + "건 저장 되었습니다.");

		return flag;
	}

	/**
	 * do_update
	 * 파일 업로드
	 * @author 이재원
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
	 * do_delete
	 * 파일 지우기
	 * @author 이재원
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
	 * do_selectOne
	 * 단건 조회
	 * @author 이재원
	 * @param dto
	 * @return DTO
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
	 * do_retrieve
	 * 검색
	 * @author 이재원
	 * @param dto
	 * @return List
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

}