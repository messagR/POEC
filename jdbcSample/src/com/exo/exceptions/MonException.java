/**
 * test
 */
package com.exo.exceptions;

import java.io.File;

/**
 * @author PC
 *
 */
public class MonException {

	public static void main(String[] args) {
		File f = new File("c:/Temp");
		if (f.exists() && f.canRead()) {
			File[] lesFichiers = f.listFiles();
			for (int i = 0; i < lesFichiers.length; i++) {
				File file = lesFichiers[i];
				if (file.isDirectory()) {
					System.out.print("[D] - ");
				} else {
					System.out.print("[F] - ");
				}
				System.out.println(file.getName());
			}
		}
	}

}
