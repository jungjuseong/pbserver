package com.clbee.pbcms.util;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

public class CustomPrimitiveFormat extends CustomNumberEditor{

	public CustomPrimitiveFormat(Class numberClass, boolean allowEmpty)
	    throws IllegalArgumentException {
	    super(numberClass, allowEmpty);
	    // TODO Auto-generated constructor stub
	}

	public void setValue(Object value){
	    System.out.println("Entered CustomPrimitiveFormat setValue");
	    if (value == null) {
	        super.setValue(null);
	        return;
	    }
	    if (value.getClass().equals(String.class)){
	        if (StringUtils.isEmpty((String)value)){
	            super.setValue(null);
	        }
	    }
	}
	
	public void setAsText(Object value){
	    System.out.println("Entered CustomPrimitiveFormat setAsText");
	    if (value == null) {
	        super.setValue(null);
	        return;
	    }
	    if (value.getClass().equals(String.class)){
	        if (StringUtils.isEmpty((String)value)){
	            super.setValue(null);
	        }
	    }
	}
}
