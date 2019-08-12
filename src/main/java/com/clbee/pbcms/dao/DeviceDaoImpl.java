package com.clbee.pbcms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;









import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.DeviceVO;
import com.clbee.pbcms.vo.MemberVO;

@Repository
public class DeviceDaoImpl implements DeviceDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int insertDeviceInfo(DeviceVO deviceVO) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			
			session.save(deviceVO);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return deviceVO.getDeviceSeq();
	}

	@Override
	public int updateDeviceInfo(DeviceVO updatedVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			DeviceVO deviceVO = (DeviceVO)session.get(DeviceVO.class, updatedVO.getDeviceSeq());
			
			if(updatedVO.getDeviceInfo() != null && !"".equals(updatedVO.getDeviceInfo()))
				deviceVO.setDeviceInfo(updatedVO.getDeviceInfo());
			if(updatedVO.getDeviceType() != null && !"".equals(updatedVO.getDeviceType()))
				deviceVO.setDeviceType(updatedVO.getDeviceType());
			if(updatedVO.getDeviceUuid() != null && !"".equals(updatedVO.getDeviceUuid()))
				deviceVO.setDeviceUuid(updatedVO.getDeviceUuid());
			if(updatedVO.getUseGb() != null && !"".equals(updatedVO.getUseGb()))
				deviceVO.setUseGb(updatedVO.getUseGb());
			if(updatedVO.getRegUserSeq() != null)
				deviceVO.setRegUserSeq(updatedVO.getRegUserSeq());

			session.update(deviceVO);
			tx.commit();
			return 1;
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
			return -1;
		}finally {
			session.close();
		}
	}

	@Override
	public List<DeviceVO> selectDeviceList(int startNo, int companySeq, String searchType, String searchValue) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(DeviceVO.class);
			cr.add(
				Restrictions.eq("companySeq", companySeq)
			)
			.setFirstResult(startNo)
			.setMaxResults(10)
			.addOrder(Order.desc("regDt"));
	
			/*
			 * 1. 정보 검색
			 * 2. 등록자 검색
			 * 3. 그룹1 검색
			 * 4. 그룹2 검색
			*/
			if(searchType != null && !"".equals(searchType)) {
				switch(Integer.parseInt(searchType)) {
					case 1 :
						cr.add(Restrictions.like("deviceInfo", "%"+searchValue+"%"));
						break;
					case 2 :
						cr.createCriteria("memberVO").add(Restrictions.like("userId", "%"+searchValue+"%"));
						break;
					case 3 :
						cr.createCriteria("memberVO").add(Restrictions.like("onedepartmentName", "%"+searchValue+"%"));
						break;
					case 4 :
						cr.createCriteria("memberVO").add(Restrictions.like("twodepartmentName", "%"+searchValue+"%"));
						break;
				}
			}
			list = cr.list();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return list;
	}



	@Override
	public int selectDeviceListCount( int companySeq, String searchType, String searchValue) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(DeviceVO.class);
			cr.add(
				Restrictions.eq("companySeq", companySeq)
			);
	
			/*
			 * 1. 정보 검색
			 * 2. 등록자 검색
			 * 3. 그룹1 검색
			 * 4. 그룹2 검색
			*/
			if(searchType != null && !"".equals(searchType)) {
				switch(Integer.parseInt(searchType)) {
					case 1 :
						cr.add(Restrictions.like("deviceInfo", "%"+searchValue+"%"));
						break;
					case 2 :
						cr.createCriteria("memberVO").add(Restrictions.like("userId", "%"+searchValue+"%"));
						break;
					case 3 :
						cr.createCriteria("memberVO").add(Restrictions.like("onedepartmentName", "%"+searchValue+"%"));
						break;
					case 4 :
						cr.createCriteria("memberVO").add(Restrictions.like("twodepartmentName", "%"+searchValue+"%"));
						break;
				}
			}
			
			list = cr.list();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return list.size();
	}

	@Override
	public DeviceVO selectDeviceInfo(int deviceSeq) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		DeviceVO deviceVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(DeviceVO.class);
			cr.add(
				Restrictions.eq("deviceSeq", deviceSeq)
			);
			
			deviceVO = (DeviceVO)cr.uniqueResult();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return deviceVO;
	}

	@Override
	public int checkIfExistUUID(String deviceUuid, int companySeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<DeviceVO> list = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(DeviceVO.class);
			cr.add(
				Restrictions.eq("deviceUuid", deviceUuid)
			);
			list = cr.list();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		/*
		 * 1 : 해당하는 UUID가 존재하지 않음 ( UUID를 등록해야됨 )
		 * 2 : 해당하는 UUID가 1개 이상 존재함. ( Duplicated )
		 * 3 : 해당하는 UUID가 1개 존재하는데 사용할 수 없는 UUID ( Rejected ) 
		 * 4 : 해당하는 UUID가 1개 존재하고 사용할수 있는 UUID임 ( Success !! )
		 * 5 : 해당하는 UUID가 1개 존재하고 사용할 수 있는 UUID인데, companySeq가 다름
		 */

		
		
		if(list.size() == 0) {
			return 1;
		}else if(list.size() == 1 ) {
			if(companySeq != list.get(0).getCompanySeq() ){
				return 5;
			}else{
				if("2".equals(list.get(0).getUseGb()))return 3;
				else return 4;
			}
			
		}else {//if(list.size() > 1 ) 한개 이상일경우
			int deviceCnt = 0;
			int [] useGbCnt ;
			useGbCnt = new int[ list .size () ];

			for(int i=0; i< list.size() ; i++){
				
				if(companySeq == list.get(i).getCompanySeq()){
					useGbCnt[deviceCnt] = Integer.parseInt(list.get(i).getUseGb());
					System.out.println();
					deviceCnt++;
				}
			}
			
			for(int i=0; i< useGbCnt.length;i++){
				System.out.println("useGbCnt = ["+i+"] = "+useGbCnt[i]);
			}
			
			System.out.println("deviceCnt = " + deviceCnt);
			switch(deviceCnt){
				case 0 : return 1;
					
				case 1 : 
					if( 2  == useGbCnt[0]) return 3;
					else return 4;
					
				default :return 2;
			}
			/*if (deviceCnt > 1 ) return 2;
			if ( devi)*/
		}
	}

	@Override
	public int countDeviceIsAvailable(int companySeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<DeviceVO> list = null;
		
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(DeviceVO.class);
	
			cr.add(
				Restrictions.and(
					Restrictions.eq("companySeq", companySeq),
					Restrictions.eq("useGb", "1")
				)
			);
			list = cr.list();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	
		return list.size();
	}
}
