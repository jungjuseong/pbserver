package com.clbee.pbcms.util;

public class VersionCheckUtil {
	
	// 버전비교 메서드(새버전, 기존버전)
	public static int versionCompare(String str1, String str2) {
	    String[] vals1 = str1.split("\\.");
	    String[] vals2 = str2.split("\\.");
	    int i = 0;
	    // set index to first non-equal ordinal or length of shortest version string
	    while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
	      i++;
	    }
	    // compare first non-equal ordinal number
	    if (i < vals1.length && i < vals2.length) {
	        int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
	        return Integer.signum(diff);
	    }
	    // the strings are equal or one string is a substring of the other
	    // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
	    return Integer.signum(vals1.length - vals2.length);
	}
	
	//기존버전 토대로 새 버전 만들어서 미리 세팅하는 메서드(기존버전)
	public static String newVerMake(String nowVer) {
		String viewVer = "";
		String[] newVerMake = nowVer.split("\\.");
		String[] newVer = new String[newVerMake.length];
		
		checkVersionNum(newVerMake, newVer, newVerMake.length, true);
		
		for(int j=0;j<newVer.length;j++) {
			if(j == 0) {
				viewVer += newVer[j];
			}else {
				viewVer += ("."+newVer[j]);
			}
		}
		
		return viewVer;
	}
	
	//새 버전 미리 세팅하려고 만들 때, 버전 각 자리의 숫자들 비교해서 만들어놓는 메서드
	public static void checkVersionNum(String[] newVerMake, String[] newVer, int loopLength, boolean numUp) {
		for(int i=0;i<loopLength;i++) {
			if(i == (loopLength-1) ) {
				int verUp = Integer.parseInt(newVerMake[i]);

				if(numUp) {
					verUp += 1;
					
					if(verUp == 10) {
						newVer[i] = "0";

						if((loopLength-1) != 0) {
							checkVersionNum(newVerMake, newVer, loopLength-1, true);
						}
					}else {
						newVer[i] = String.valueOf(verUp);
						if((loopLength-1) != 0) {
							checkVersionNum(newVerMake, newVer, loopLength-1, false);
						}
					}
				}else {
					newVer[i] = newVerMake[i];
					if((loopLength-1) != 0) {
						checkVersionNum(newVerMake, newVer, loopLength-1, false);
					}
				}
			}
		}
	}
	
	public static int digitCheck(String nowVer) {
		String[] nowVerCheck = nowVer.split("\\.");
		if(nowVerCheck.length == 3) {
			return 0;
		}else {
			return 1;
		}
	}
	
}