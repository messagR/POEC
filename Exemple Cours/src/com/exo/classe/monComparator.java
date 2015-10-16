/**
 * test
 */
package com.exo.classe;

import java.util.Comparator;

/**
 * @author PC
 *
 */
public class monComparator implements Comparator<String> {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(String o1, String o2) {
		// if (o1.equals(o2)) {
		// return 0;
		// }
		// if (o1.compareTo(o2) < 0) {
		// return -1;
		// }
		// return 1;

		// int resu = o1.compareTo(o2);
		// if (resu > 0) {
		// return -1;
		// }
		// if (resu < 0) {
		// return 1;
		// }
		// return 0;

		return -o1.compareTo(o2);
	}

}
