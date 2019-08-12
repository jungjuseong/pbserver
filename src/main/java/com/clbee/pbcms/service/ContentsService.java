package com.clbee.pbcms.service;

import java.util.HashMap;
import java.util.List;

import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.ContentList;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.ContentsappSubVO;
import com.clbee.pbcms.vo.InappVO;

public interface ContentsService {
	int insertContentInfo( ContentVO content );
	int insertContentsappSubInfo( ContentsappSubVO contentsappSubVO );
	int updateNullableContentInfo(ContentVO updatedVO);
	int updateContentInfo( ContentVO content, int contentID );
	int updateContentsappSubInfo ( ContentsappSubVO contentappSubVO, int contentsappSubSeq );
	
	ContentList getListContents( int currentPage, int maxResult, String[] sort,  String searchSeq, Integer valueSeq, String searchType, String searchValue, boolean isMember );
	ContentVO selectByContentId( int contentID );
	/*ContentList getListContentOfCheckBox( int currentPage, int maxResult, String[] sort, String searchSeq, Integer valueSeq, String searchType, String searchValue );*/
	ContentVO selectByUltimateCondition(ContentVO cvo);
	List<ContentVO> getListByCustomInfo(String DBName, String value);
	List<ContentVO> getListByCustomInfo(String DBName, int value);
	void deleteContentsInfo( int contentsSeq );
	
	//20180327 - lsy : develop version managemenet, date management
	int verifyIfExists( String contentsName, String ver_num);
	String getSameContentsSeq( String contentsName );
	void insertContentsHistory( String contents_seq );
	void deleteContentsBySeq( String contents_seq );
	
	List<ContentVO> selectForHistory( String contents_name );
	
	int infoUpdateCheck( ContentVO contentFormVO, int contents_seq);
	//20180327 - lsy : develop version managemenet, date management - end
	
	//20180607 : lsy - app make request -> contents select
	List<ContentVO> getListContentsForAppMake(Entity param);
	//20180614 : lsy - app make request -> contents relatedAppinfo update
	ContentVO selectRelatedAppinfo( String relatedAppName );
	String selectRelatedAppSeqInfo( int contentSeq );
	
	ContentVO selectRelatedAppInfoNew( int contentSeq );
	
	int updateRelatedAppinfo(Entity param);
	int updateRelatedAppSubInfo(Entity param);

	//20180618 : lsy - app make request(Library type)
	int inappContentsProcessGb(Entity param);
	
	int insertContentsFromInapp(ContentVO contentVO);
	void insertContentsSubFromInapp(ContentsappSubVO contentsAppSubVO);

	int selectRelatedContentsSeq(Entity param);
	ContentVO selectBeforeContentInfo( int contents_seq );
	void updateContentsFromInapp(ContentVO contentVO);
	//20180618 : lsy - app make request(Library type) - end
	
	String selectContentsFileName( int contentSeq );
	void deleteContentsSubBySeq( int contentsSeq);
}
