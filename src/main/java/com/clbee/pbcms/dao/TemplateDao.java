package com.clbee.pbcms.dao;


import java.util.List;

import com.clbee.pbcms.vo.MemberVO;
import com.clbee.pbcms.vo.TemplateList;
import com.clbee.pbcms.vo.TemplateSubVO;
import com.clbee.pbcms.vo.TemplateVO;

public interface TemplateDao {
	void insertTempInfo( TemplateVO templateVO );
	void updateTempInfo( TemplateVO updatedVO, int temp_id );
	TemplateVO selectByTempId( int temp_id );
	List<TemplateVO> selectByAll();
	public int getListCount(TemplateList templateList, MemberVO memberVO , String shField, String shKeyword, String status);
	public List<TemplateVO> selectList(TemplateList templateList, MemberVO memberVO , String shField, String shKeyword, String status);
	public int insertTemplate(TemplateVO tempVo);
	public List<TemplateVO> selectInAppList(TemplateVO tempVo);
	public List selectUserList(MemberVO memVo, String useS);
	public List selectUserList3(MemberVO memVo, String useS);
	public Object[] selectUserList2(MemberVO memVo, String useS);
	public TemplateVO selectView(int thisSeq);
	public void updateTemplate(TemplateVO tempVo);
	public void insertTemplateSub(TemplateSubVO tempSubVo);
	public List<TemplateSubVO> selectUserList2(int thisSeq);
	public void deleteTemplateSub(TemplateSubVO tempSubVo);
	public void updateTemplateFile(TemplateVO tempVo);
	int getListCount(TemplateVO temVO, MemberVO memberVO,TemplateList templateList);
	List selectList(TemplateVO temVO, MemberVO memberVO, TemplateList templateList, String flagForAll);
	
	//20180219 : lsy - templateDelete
	void deleteTemplate(int thisSeq);
} 