package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.MemberVO;
import com.clbee.pbcms.vo.TemplateList;
import com.clbee.pbcms.vo.TemplateSubVO;
import com.clbee.pbcms.vo.TemplateVO;

public interface TemplateService {
	void insertTempInfo( TemplateVO templateVO );
	void updateTempInfo( TemplateVO updatedVO, int temp_id );
	TemplateVO selectByTempId( int temp_id );
	List<TemplateVO> selectByAll();
	TemplateList selectList(MemberVO memberVO, TemplateList templateList , String shField, String shKeyword, String status);
	int insertTemplate(TemplateVO tempVo);
	List<TemplateVO> selectInAppList(TemplateVO tempVo);
	List selectUserList(MemberVO memVo, String useS);
	List selectUserList3(MemberVO memVo, String useS);
	Object[] selectUserList2(MemberVO memVo, String useS);
	TemplateVO selectView(int thisSeq);
	void updateTemplate(TemplateVO tempVo);
	void insertTemplateSub(TemplateSubVO tempSubVo);
	List<TemplateSubVO> selectUserList2(int thisSeq);
	void deleteTemplateSub(TemplateSubVO tempSubVo);
	void updateTemplateFile(TemplateVO tempVo);
	TemplateList selectList(TemplateVO temVO, MemberVO memberVO, TemplateList templateList, String flagForAll);
	/*List<TemplateVO> selectListForAvailable( String availableUserId );*/
	
	//20180219 : lsy - templateDelete
	void deleteTemplate(int thisSeq);
}
