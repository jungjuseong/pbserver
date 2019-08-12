package com.clbee.pbcms.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.NoticeDao;
import com.clbee.pbcms.vo.NoticeList;
import com.clbee.pbcms.vo.NoticeSubVO;
import com.clbee.pbcms.vo.NoticeVO;
import com.clbee.pbcms.vo.NoticeappSubVO;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeDao noticeDao;

	@Override
	public int insertNoticeInfo(NoticeVO noticeVO) {
		// TODO Auto-generated method stub
		return noticeDao.insertNoticeInfo(noticeVO);
	}

	@Override
	public NoticeVO selectNoticeInfo(int noticeSeq) {
		// TODO Auto-generated method stub
		return noticeDao.selectNoticeInfo(noticeSeq);
	}

	@Override
	public NoticeList selectNoticeList( int currentPage, int companySeq, String searchType, String searchValue ) {
		// TODO Auto-generated method stub
		NoticeList list = null;
		// ��ü ������ 1,2,3,4,5,6,7,8,9,19�ؿ� �Ķ���� ���ڰ� ǥ�õ� ����
		int pageSize = 10;
		// ���������� �ִ� �����õ� Ƚ��
		int maxResult = 10; 
		int totalCount = 0;
		int startNo = 0;
			try{
				totalCount = noticeDao.selectNoticeListCount(companySeq, searchType, searchValue);
				System.out.println("totalCount = " + totalCount);
				
				list = new NoticeList(pageSize, totalCount, currentPage, maxResult);
			
				startNo = (currentPage-1) *maxResult;
	
				List<NoticeVO> vo = noticeDao.selectNoticeList(startNo, companySeq, searchType, searchValue);
				
				list.setList(vo);

			}catch(Exception e){
				System.out.println("����");
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public int updateNoticeInfo(NoticeVO updatedVO) {
		// TODO Auto-generated method stub
		return noticeDao.updateNoticeInfo(updatedVO);
	}

	@Override
	public int insertNoticeSubInfo(NoticeSubVO noticeSubVO) {
		// TODO Auto-generated method stub
		return noticeDao.insertNoticeSubInfo(noticeSubVO);
	}

	@Override
	public void deleteNoticeSubInfo(NoticeSubVO noticeSubVO) {
		// TODO Auto-generated method stub
		noticeDao.deleteNoticeSubInfo(noticeSubVO);
	}

	@Override
	public List<NoticeSubVO> selectNoticeSubList(int noticeSeq) {
		// TODO Auto-generated method stub
		return noticeDao.selectNoticeSubList(noticeSeq);
	}

	@Override
	public int insertNoticeappSubInfo(NoticeappSubVO noticeappSubVO) {
		// TODO Auto-generated method stub
		return noticeDao.insertNoticeappSubInfo(noticeappSubVO);
	}

	@Override
	public void deleteNoticeappSubInfo(NoticeappSubVO noticeappSubVO) {
		// TODO Auto-generated method stub
		noticeDao.deleteNoticeappSubInfo(noticeappSubVO);
	}

	@Override
	public List<NoticeappSubVO> selectNoticeappSubList(int noticeSeq) {
		// TODO Auto-generated method stub
		return noticeDao.selectNoticeappSubList(noticeSeq);
	}

	@Override
	public List<NoticeVO> getListAvailableNoticeByCompany(int companySeq,
			int userSeq, String storeBundleId) {
		// TODO Auto-generated method stub

		List<NoticeVO> list = null;
		List<NoticeVO> addList = new ArrayList<NoticeVO>();
		
		try {
			list =  noticeDao.getListNoticeByCompany(companySeq, userSeq, storeBundleId);

			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
			DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
			Date date = new Date();			
			Date formattedDate;
			formattedDate = inputDF.parse(format.format(date));

			Calendar cal = Calendar.getInstance();
			cal.setTime(formattedDate);
			
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int year = cal.get(Calendar.YEAR);

			
			
			
			if(list!= null) {
				for(int i =0; i < list.size() ; i++) {
					formattedDate = inputDF.parse(format.format(list.get(i).getNoticeStartDt()));
					
					cal.setTime(formattedDate);
					int stMonth = cal.get(Calendar.MONTH);
					int stDay = cal.get(Calendar.DAY_OF_MONTH);
					int stYear = cal.get(Calendar.YEAR);
					
					formattedDate = inputDF.parse(format.format(list.get(i).getNoticeEndDt()));
					
					cal.setTime(formattedDate);
					int endMonth = cal.get(Calendar.MONTH);
					int endDay = cal.get(Calendar.DAY_OF_MONTH);
					int endYear = cal.get(Calendar.YEAR);
					

					// 1. year(���� �⵵)�� stYear���� Ŭ��� month�� day�� �ƹ�����̾���
					// 2. year�� stYear�� ������� month�� ����
					// 3. �̶� month�� stMonth���� Ŭ��� day�� �ƹ������ ����
					// 4. month�� stMonth�� ������� stDay�� ����
					// endYear�� 1, 2, 3, 4 �� ����
					if(year > stYear || (year == stYear && (month > stMonth || (month == stMonth && day >= stDay)))){
						if(year < endYear || (year == endYear && (month < endMonth || (month == endMonth && day <= endDay)))){
							addList.add(list.get(i));
						}else {
						}
					}else {
					}
/*					
					if((year >= stYear) && (month >= stMonth) && (day >= stDay)){
						if((year <= endYear)  && (month <= endMonth) && (day <= endDay)){
							addList.add(list.get(i));
						}else {
						}
					}else {
					}*/
				}
			}

		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return addList;
	}

	@Override
	public void deleteNoticeSubAppSeqInfo(int appSeq) {
		// TODO Auto-generated method stub
		noticeDao.deleteNoticeappSubInfo(appSeq);
	}
}
