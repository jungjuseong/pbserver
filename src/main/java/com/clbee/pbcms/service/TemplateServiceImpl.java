package com.clbee.pbcms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.TemplateDao;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.InappcategoryVO;
import com.clbee.pbcms.vo.MemberVO;
import com.clbee.pbcms.vo.TemplateList;
import com.clbee.pbcms.vo.TemplateSubVO;
import com.clbee.pbcms.vo.TemplateVO;

@Service
public class TemplateServiceImpl implements TemplateService {
	@Autowired TemplateDao dao;

	@Override
	public void insertTempInfo(TemplateVO templateVO) {
		dao.insertTempInfo(templateVO);
		
	}

	@Override
	public void updateTempInfo(TemplateVO updatedVO, int temp_id) {
		dao.updateTempInfo(updatedVO, temp_id);
		
	}

	@Override
	public TemplateVO selectByTempId(int temp_id) {
		// TODO Auto-generated method stub
		return dao.selectByTempId(temp_id);
	}

	@Override
	public List<TemplateVO> selectByAll() {
		// TODO Auto-generated method stub
		return dao.selectByAll();
	}

	@Override
	public TemplateList selectList(MemberVO memberVO, TemplateList templateList , String shField, String shKeyword, String status) {
		// TODO Auto-generated method stub
		//AppList list = null;
		int pageSize = 10;
		int maxResult = 10;
		int totalCount = 0;
		
		try{
			totalCount = dao.getListCount(templateList, memberVO , shField, shKeyword, status);
			System.out.println("totalCount = " + totalCount);
			System.out.println("totalCount = " + totalCount);
			System.out.println("totalCount = " + totalCount);
			System.out.println("totalCount = " + totalCount);
			System.out.println("totalCount = " + totalCount);
			System.out.println("totalCount = " + totalCount);
			
			templateList.calc(pageSize, totalCount, templateList.getCurrentPage(), maxResult);		

			List<TemplateVO> vo = dao.selectList(templateList, memberVO , shField, shKeyword, status);
			
			templateList.setList(vo);
			
			System.out.println("[ListService] - selectList method");
			System.out.println("selectList[] " + vo.size());
			System.out.println(vo.size());
			
		}catch(Exception e){
			System.out.println("����");
			e.printStackTrace();
		}
			
			return templateList;
	}

	@Override
	public int insertTemplate(TemplateVO tempVo) {
		return dao.insertTemplate(tempVo);
	}

	@Override
	public List<TemplateVO> selectInAppList(TemplateVO tempVo) {
		List<TemplateVO> Result = null;
		
		try{		
			Result = dao.selectInAppList(tempVo);
		}catch(Exception e){
			e.printStackTrace();
		}			
		return Result;
	}

	@Override
	public List selectUserList(MemberVO memVo, String useS) {
		List Result = null;
		
		try{		
			Result = dao.selectUserList(memVo, useS);
		}catch(Exception e){
			e.printStackTrace();
		}			
		return Result;
	}
	
	@Override
	public List selectUserList3(MemberVO memVo, String useS) {
		List Result = null;
		
		try{		
			Result = dao.selectUserList3(memVo, useS);
		}catch(Exception e){
			e.printStackTrace();
		}			
		return Result;
	}	

	@Override
	public Object[] selectUserList2(MemberVO memVo, String useS) {
		
		Object[] Result = null;
		
		try{		
			Result = dao.selectUserList2(memVo, useS);
		}catch(Exception e){
			e.printStackTrace();
		}			
		return Result;
	}

	@Override
	public TemplateVO selectView(int thisSeq) {
		return dao.selectView(thisSeq);
	}

	@Override
	public void updateTemplate(TemplateVO tempVo) {
		dao.updateTemplate(tempVo);		
	}

	@Override
	public void insertTemplateSub(TemplateSubVO tempSubVo) {
		dao.insertTemplateSub(tempSubVo);				
	}

	@Override
	public List<TemplateSubVO> selectUserList2(int thisSeq) {
		List<TemplateSubVO> Result = null;
		
		try{		
			Result = dao.selectUserList2(thisSeq);
		}catch(Exception e){
			e.printStackTrace();
		}			
		return Result;
	}

	@Override
	public void deleteTemplateSub(TemplateSubVO tempSubVo) {
		dao.deleteTemplateSub(tempSubVo);			
	}

	@Override
	public void updateTemplateFile(TemplateVO tempVo) {
		dao.updateTemplateFile(tempVo);					
	}

	@Override
	public TemplateList selectList(TemplateVO temVO, MemberVO memberVO, TemplateList templateList, String flagForAll) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		//AppList list = null;
		int pageSize = 10;
		int maxResult = 5;
		int totalCount = 0;

		try{

			totalCount = dao.getListCount(temVO, memberVO , templateList);

			System.out.println("[TemplateService] pageSize = " + pageSize);
			System.out.println("[TemplateService] totalCount = " + totalCount);
			System.out.println("[TemplateService] templateList.getCurrentPage() = " + templateList.getCurrentPage());
			System.out.println("[TemplateService] maxResult = " + maxResult);

			templateList.calc(pageSize, totalCount, templateList.getCurrentPage(), maxResult);		
			List<TemplateVO> vo = dao.selectList(temVO, memberVO, templateList, flagForAll);
			templateList.setList(vo);
		}catch(Exception e){
			System.out.println("����");
			e.printStackTrace();		}
			
			return templateList;
	}

	//20180219 : lsy - deleteTemplate
	@Override
	public void deleteTemplate(int thisSeq) {
		dao.deleteTemplate(thisSeq);			
	}
}
