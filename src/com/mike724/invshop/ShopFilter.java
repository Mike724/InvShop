package com.mike724.invshop;

import java.io.File;
import java.io.FileFilter;

public class ShopFilter implements FileFilter {
	
	private String startsWith = null;
	private String endsWith = null;
	private String contains = null;
	
	public ShopFilter(String s, String e, String c) {
		startsWith = s;
		endsWith = e;
		contains = c;
	}

	@Override
	public boolean accept(File file) {
		boolean denied = false;
		if(startsWith!=null) {
			if(!file.getName().startsWith(startsWith)) {
				denied = true;
			}
		}
		if(endsWith!=null) {
			if(!file.getName().endsWith(endsWith)) {
				denied = true;
			}
		}
		if(contains!=null) {
			if(!file.getName().contains(contains)) {
				denied = true;
			}
		}
		return !denied;
	}

}
