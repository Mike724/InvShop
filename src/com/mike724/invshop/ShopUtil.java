package com.mike724.invshop;

import java.io.File;

public class ShopUtil {
	
	public static int getHighestID() {
		File[] files = InvShopGlobal.shopsFolder.listFiles(new ShopFilter(null,null,null));
		int high = 0;
		for(int i=0;i<files.length;i++) {
			String[] data = files[i].getName().split(".");
			if(data.length!=2) {
				continue;
			}
			try {
				int n = Integer.parseInt(data[0]);
				if(n>high) {
					high = n;
				}
			} catch(NumberFormatException e) {
				continue;
			}
		}
		return high;
	}

}
