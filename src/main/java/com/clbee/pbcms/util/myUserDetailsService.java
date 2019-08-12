package com.clbee.pbcms.util;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.service.MemberService;
import com.clbee.pbcms.vo.MemberVO;


@Service
public class myUserDetailsService implements UserDetailsService {

	@Autowired
	MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
//		System.out.println("Hello [loadUserByUserName");
//		System.out.println(username);
		
		MemberVO memberVO = memberService.findByUserName(username);
		
//		System.out.println("password" + memberVO.getUserPw());
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

//		System.out.println("userGb Number // = " + memberVO.getUserGb());
		switch(Integer.parseInt(memberVO.getUserGb())) {
		
/*		20180518 : lsy - 권한 체계 변경
 * 
 * 		기존 : ROLE_ADMIN_SERVICE -> ROLE_INDIVIDUAL_MEMBER / ROLE_COMPANY_MEMBER -> ROLE_COMPANY_MIDDLEADMIN / ROLE_COMPANY_DISTRIBUTOR / ROLE_COMPANY_CREATOR / ROLE_COMPANY_USER
 * 		변경 : ROLE_ADMIN_SERVICE -> ROLE_INDIVIDUAL_MEMBER / ROLE_COMPANY_MEMBER -> ROLE_USER
 * 		변경 내용
 * 			1. ROLE_COMPANY_MEMBER(기업회원) 하위에 -> 중간관리자/배포자/제작자/사용자 4가지로 권한 구분(권한별로 페이지 접근권한/사용권한이 정해져있었음)
 * 				=> 사용자 1가지로 권한 압축 및 그룹 개념 도입 : 기업회원 or 그룹관리 권한을 가진 기업회원 하위 사용자가 만든 기업 사용자용 그룹을 
 * 					--> 기업회원 or 사용자 관리 권한을 가진 기업회원 하위 사용자가 사용자 등록,수정 시 그룹을 선택하는 개념(그룹에 따라서 페이지 접근권한/사용권한이 변경)
 * 			2. ROLE_INDIVIDUAL_MEMBER / ROLE_COMPANY_MEMBER -> 그룹관리 개념 적용 
 * 				=> ROLE_ADMIN_SERVICE 권한의 계정(service)으로 회원용 그룹을 생성/수정 가능 : 회원가입 시, 회원용 그룹을 선택하는 개념
 * 					--> 그룹 생성 시, 기업용인지 개인용인지 선택 필수(사용자 그룹과 다른점)
 * 
			case 1 :
				 authorities.add(new GrantedAuthorityImpl("ROLE_COMPANY_USER"));
				break;
			case 5 :
				 authorities.add(new GrantedAuthorityImpl("ROLE_COMPANY_CREATOR"));
				break;
			case 21 :
				authorities.add(new GrantedAuthorityImpl("ROLE_COMPANY_DISTRIBUTOR"));
				break;
			case 29 :
				authorities.add(new GrantedAuthorityImpl("ROLE_COMPANY_MIDDLEADMIN"));
				break;
*/
			case 63 :	//	새 권한 체계 : 사용자
				authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
				break;
			case 127 :	//	회원(1:기업,2:개인)
				if("1".equals(memberVO.getCompanyGb())) {
					 authorities.add(new GrantedAuthorityImpl("ROLE_COMPANY_MEMBER"));
				}else{
					 authorities.add(new GrantedAuthorityImpl("ROLE_INDIVIDUAL_MEMBER"));
				}
				break;
			case 255 :	//	service
				authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN_SERVICE"));
				break;
		}
        
        
        if(ConditionCompile.bookFeature) {
			if("5".equals(memberVO.getUserStatus())){
				System.out.println("getUserStatus : 5");
				return new myUserDetails(username, memberVO.getUserPw(),authorities, memberVO, false, true ); 
			}else if("4".equals(memberVO.getUserStatus())){
				System.out.println("getUserStats : 4 ");
				return new myUserDetails(username, memberVO.getUserPw(),authorities, memberVO, true, true );
			}
        }else {
        	if("5".equals(memberVO.getUserStatus())){
				System.out.println("getUserStatus : 5");
				return new myUserDetails(username, memberVO.getUserPw(),authorities, memberVO, false, false ); 
			}else if("4".equals(memberVO.getUserStatus())){
				System.out.println("getUserStats : 4 ");
				return new myUserDetails(username, memberVO.getUserPw(),authorities, memberVO, true, false );
			}
        }

		return null;

	}	
}
