/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.gen.internal2.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Collections of utility methods handling URLs.
 * 
 */
public class UrlUtil
{
	/**
	 * The <code>file</code> string indicating a url file protocol.
	 */
	public static final String FILE_PROTOCOL = "file";
	/**
	 * The <code>file</code> string indicating a url http protocol.
	 */
	public static final String HTTP_PROTOCOL = "http";
	/**
	 * The <code>file</code> string indicating a url http protocol.
	 */
	public static final String HTTPS_PROTOCOL = "https";
	/**
	 * The <code>file</code> string indicating a url file protocol.
	 */
	public static final String JAR_PROTOCOL = "jar";

	
	/**
	 * Returns true if the specified url is to a file, i.e its protocol is <code>file</code>.
	 */
	public static boolean isFileUrl(URL url) {
		return url != null && FILE_PROTOCOL.equals(url.getProtocol());
	}
	/**
	 * Returns true if the specified url is to a jar, i.e its protocol is <code>jar</code>.
	 * For example <code>jar:file:/C:/testapps/example/WEB-INF/lib/struts.jar!/META-INF/tlds/struts-bean.tld</code>.
	 */
	public static boolean isJarUrl(URL url) {
		return url != null && JAR_PROTOCOL.equals(url.getProtocol());
	}
	/**
	 * Returns true if the specified url protocol is http.
	 */
	public static boolean isHttpUrl(URL url) {
		String protocol =  url.getProtocol();
		return url != null && (HTTP_PROTOCOL.equals(protocol) || HTTPS_PROTOCOL.equals(protocol));
	}
	/**
	 * Returns the <code>File</code> corresponding to a url, or null if the url 
	 * protocol is not file.
	 */
	public static java.io.File getUrlFile(URL url) {
		if (isFileUrl(url) && !isJarUrl( url ) ){
			File ret = new java.io.File(url.getFile());
			return ret ;
		}
		return null;
 	}

	
	/**
	 * Returns the url to a jar file given a url to a file inside 
	 * the jar.
	 * For example if given 
	 * <code>jar:file:/C:/testapps/example/WEB-INF/lib/struts.jar!/META-INF/tlds/struts-bean.tld</code>
	 * this method returns <code>file:/C:/testapps/example/WEB-INF/lib/struts.jar</code>.
	 * 
	 * <p>Returns null if the given url is not recognized as a url to a file 
	 * inside a jar.
	 */
	public static URL getJarFileUrl(URL url) {
		if (!isJarUrl(url)) {
			return null;
		}
		String file = url.getFile(); //file:/C:/testapps/example/WEB-INF/lib/struts.jar!/META-INF/tlds/struts-bean.tld
		int index = file.indexOf('!');
		if (index < 0) {
			return null;
		}
		String jarFileUrlStr = file.substring(0, index);
		try {
			return new URL(jarFileUrlStr);
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public static boolean isRemote(String url){
		return url.startsWith("http:")||url.startsWith("https:")||url.startsWith("www.");
	}
	
	public static File getTemplateFolder(String plugId , String strPath ){
		Bundle bundle = Platform.getBundle( plugId );
		Path path = new Path( strPath );
		URL url = FileLocator.find(bundle, path, null);
		try {
			URL templUrl;
			templUrl = FileLocator.resolve(url);
			File templDir = UrlUtil.getUrlFile(templUrl);
			return templDir ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
