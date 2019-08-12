package com.clbee.pbcms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.ContentList;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.ContentsappSubVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MemberVO;

public interface ContentsDao {
	int insertContentInfo( ContentVO contentVO);
	int insertContentsappSubInfo ( ContentsappSubVO contentappSubVO);
	int updateNullableContentInfo(ContentVO updatedVO);
	int updateContentInfo( ContentVO updatedVO, int contentID);
	int updateContentsappSubInfo(ContentsappSubVO updatedVO, int contentsappSubSeq);
	ContentVO selectByContentId( int contentID );
	List<ContentVO> getListContents( int startNo, int MaxResult, String[] sort, String searchSeq, Integer valueSeq, String searchType, String searchValue, boolean isMember);
	int getListContentsCount( String searchSeq, Integer valueSeq, String[] sort, String searchType, String searchValue, boolean isMember);
	/*List<ContentVO> getListContentOfCheckBox(int startNo, int MaxResult, String[] sort, String searchSeq, Integer valueSeq, String searchType, String searchValue);*/
	/*int getListContentsCountOfCheckBox( String searchSeq, Integer valueSeq, String[] sort, String searchType, String searchValue);*/
	ContentVO selectByUltimateCondition(ContentVO cvo);
	List<ContentVO> getListByCustomInfo(String DBName, String value);
	List<ContentVO> getListByCustomInfo(String DBName, int value);
	void deleteContentsInfo(int contentsSeq);
	
	//20180327 - lsy : develop version managemenet
	int verifyIfExistsContents( String DBName, ArrayList<Map<String, String>> param);
	int verifyIfExistsHistory( String DBName, ArrayList<Map<String, String>> param);
	String getSameContentsSeq( String DBName, String contentsName );
	void insertContentsHistory( String DBName, String contents_seq );
	void deleteContentsBySeq( String DBName, String contents_seq );

	List<ContentVO> selectForHistory( String DBName, String contents_name );
	//20180327 - lsy : develop version managemenet - end
	
	//20180607 : lsy - app make request -> contents select
	List<ContentVO> getListContentsForAppMake(String DBName, Entity param);
	//20180614 : lsy - app make request -> contents relatedAppinfo update
	ContentVO selectRelatedAppinfo( String DBName, String relatedAppName );
	String selectRelatedAppSeqInfo( String DBName, int contentSeq );
	
	ContentVO selectRelatedAppInfoNew( String DBName, int contentSeq );
	
	int updateRelatedAppinfo(String DBName, Entity param);
	int updateRelatedAppSubInfo(String DBName, Entity param);
	
	//20180618 : lsy - app make request(Library type)
	int inappContentsProcessGb(String DBName, Entity param);
	int insertContentsFromInapp(String DBName, ContentVO contentVO);
	void insertContentsSubFromInapp(String DBName, ContentsappSubVO contentsAppSubVO);

	int selectRelatedContentsSeq(String DBName, Entity param);
	ContentVO selectBeforeContentInfo( String DBName, int contents_seq );
	void updateContentsFromInapp(String DBName, ContentVO contentVO);
	//20180618 : lsy - app make request(Library type) - end
	
	String selectContentsFileName( String DBName, int contentSeq );
	void deleteContentsSubBySeq( String DBName, int contentsSeq);
}