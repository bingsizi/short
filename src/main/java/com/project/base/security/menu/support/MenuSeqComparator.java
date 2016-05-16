package com.project.base.security.menu.support;

import java.util.Comparator;

import com.project.base.security.menu.entity.Menu;

/**
 * 根据menu中的seq升序排序,ASC
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月10日 上午10:30:54
 */
public class MenuSeqComparator implements Comparator<Menu> {

	@Override
	public int compare(Menu menu1, Menu menu2) {
		if (menu1.getSeq() > menu2.getSeq()) {
			return 1;
		} else {
			return -1;
		}
	}
}
