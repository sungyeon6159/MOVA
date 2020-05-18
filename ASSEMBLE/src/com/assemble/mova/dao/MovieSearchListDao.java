/**
 *<pre>
 * com.assemble.mova.dao
 * Class Name : MovieSearchListDao.java
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
import com.assemble.mova.vo.MovieVO;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * <pre>
 * com.assemble.mova.dao
 *    |_ MovieSearchListDao.java
 * 
 * </pre>
 * 
 * @date : 2019. 12. 11. 오후 4:23:19
 * @version :
 * @author : 박성연
 */
public class MovieSearchListDao implements WorkDiv {

	static Logger log = Logger.getLogger(MovieSearchListDao.class);
	private List<MovieVO> movieInfoData = new ArrayList<MovieVO>();
	private final String ADD_FILE = "src/file/movie_info.csv";

	public MovieSearchListDao() {
		movieInfoData = readFile(ADD_FILE);
		display(movieInfoData);
	}

	/**
	 * display 
	 * movieList를 콘솔에 출력
	 * @author 박성연
	 * @param movieList
	 * @return void
	 */
	public void display(List<MovieVO> movieList) {
		for (MovieVO vo : movieList) {
			log.debug(movieList);
		}
	}

	/**
	 * isMemberExists 
	 * 입력한 검색어와 데이터 비교해서 선택적 read.
	 * @author 박성연
	 * @param vo
	 * @return boolean
	 */
	public boolean isMemberExists(MovieVO vo) {
		boolean flag = false;

		for (int i = 0; i < movieInfoData.size(); i++) {
			MovieVO vsVO = movieInfoData.get(i);

			if (vsVO.getTitle().equals(vo.getTitle())) {
				flag = true;
				break;
			}

		}
		return flag;
	}

	/**
	 * getSeachData 
	 * 검색 데이터를 입력 받아 MovieVO 돌려 준다.
	 * @author 박성연
	 * @param title
	 * @return MovieVO
	 */
	public MovieVO getSeachData(String title) {
		MovieVO outVO = null;

		String inputData = title;
		inputData = inputData.trim();

		System.out.println("검색 데이터 입력(ex 어벤져스) >>" + inputData);

		String[] dataArray = inputData.split(",");
		outVO = new MovieVO();
		outVO.setTitle((dataArray[0]));

		return outVO;

	}

	/**
	 * saveFile 
	 * List에 있는 Data 파일에 기록.
	 * @author 박성연
	 * @param path
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
	 * disp 
	 * List를 콘솔에 출력
	 * @author 박성연
	 * @param movieInfoData
	 * @return void
	 */
	public void disp(List<MovieVO> movieInfoData) {
		for (MovieVO vo : movieInfoData) {
			log.debug("movieInfoData" + vo);
		}
	}
	/**
	 * readFile 
	 * 파일 읽어서 List배열에 담기.
	 * @author 박성연
	 * @param filePath
	 * @return List<MovieVO>
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
	 * do_save 
	 * 저장하기
	 * @author 박성연
	 * @param dto
	 * @return int
	 */
	@Override
	public int do_save(DTO dto) {
		int flag = 0;
		MovieVO vo = (MovieVO) dto;

		movieInfoData.add(vo);
		int saveCnt = saveFile(ADD_FILE);
		log.debug("총 " + saveCnt + "건 저장 되었습니다.");

		return flag;
	}
	/**
	 * do_update 
	 * 이미 존재하는 데이터 삭제하고 저장.(수정)
	 * @author 박성연
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
	 * 데이터 삭제하고 파일 저장.
	 * @author 박성연
	 * @param dto
	 * @return int
	 */
	@Override
	public int do_delete(DTO dto) {
		int flag = 0;
		MovieVO delVO = (MovieVO) dto;
		
		for (int i = movieInfoData.size() - 1; i >= 0; i--) {
			MovieVO vsVO = movieInfoData.get(i);
			if (delVO.getTitle().equals(vsVO.getTitle())) {
				movieInfoData.remove(i);
				flag++;
			}
		}

	
		if (flag != 0)
			saveFile(ADD_FILE);

		return flag;
	}
	/**
	 * do_selectOne 
	 * 단건검색.
	 * @author 박성연
	 * @param dto
	 * @return DTO
	 */
	@Override
	public DTO do_selectOne(DTO dto) {
		/*
		 * MovieVO outVO = null; MovieVO inVO = (MovieVO) dto;
		 * 
		 * System.out.println("★:"+inVO);
		 * 
		 * for (int i = 0; i < movieInfoData.size(); i++) { MovieVO vsVO =
		 * movieInfoData.get(i);
		 * 
		 * System.out.println("☆"+vsVO);
		 * 
		 * if (vsVO.getTitle().equals(inVO.getTitle())) { outVO = vsVO;
		 * 
		 * System.out.println("＠ outVO:"+outVO); break; } }
		 */

		return null;
	}
	/**
	 * do_retrieve 
	 * 검색하기.
	 * @author 박성연
	 * @param dto
	 * @return List<?>
	 */
	@Override
	public List<?> do_retrieve(DTO dto) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		
		MovieVO inVO = (MovieVO) dto;
		
		try {
			for (int i = 0; i < movieInfoData.size(); i++) {
				MovieVO vsVO = movieInfoData.get(i);
				// %james%
				if (vsVO.getTitle().matches(".*" + inVO.getTitle() + ".*")) {
					list.add(vsVO);
					log.debug("MovieVO 검색 결과:" + vsVO);
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
