package com.clbee.pbcms.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.clbee.pbcms.vo.MemberVO;

public class myUserDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7306690067344994732L;



	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private MemberVO memberVO;
	private boolean isEnabled;
	private boolean isBook;

	public myUserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities,
			MemberVO memberVO, boolean isEnabled, boolean isBook) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.memberVO = memberVO;
		this.isEnabled = isEnabled;
		this.isBook = isBook;
	}


	public MemberVO getMemberVO() {
		return memberVO;
	}


	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}


	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return isEnabled;
	}


	public boolean getIsBook() {
		return isBook;
	}


	public void setIsBook(boolean isBook) {
		this.isBook = isBook;
	}



}
