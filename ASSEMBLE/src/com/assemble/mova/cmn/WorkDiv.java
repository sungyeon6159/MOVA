
package com.assemble.mova.cmn;

import java.util.List;

public interface WorkDiv {
   /**
    * 등록기능을 구현 하세요.
    * @param dto
    * @return 등록성공 : 1, 실패: 0.
    */
   public abstract int do_save(DTO dto);
   /**
    * 수정기능을 구현 하세요.
    * @param dto
    * @return 등록성공 : 1, 실패: 0.
    */
   public abstract int do_update(DTO dto);	
   /**
    * 삭제기능을 구현 하세요.
    * @param dto
    * @return 등록성공 : 1, 실패: 0.
    */   
   public abstract int do_delete(DTO dto);
   /**
    * 단건조회 기능을 구현 하세요.
    * @param dto
    * @return DTO
    */   
   public abstract DTO do_selectOne(DTO dto);
   /**
    * 목록조회 기능을 구현 하세요.
    * @param dto
    * @return List
    */   
   public abstract List<?> do_retrieve(DTO dto);
	
   
}








