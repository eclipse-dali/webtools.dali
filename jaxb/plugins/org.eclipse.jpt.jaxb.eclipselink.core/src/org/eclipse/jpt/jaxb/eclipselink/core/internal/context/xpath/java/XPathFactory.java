/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java;

import java.util.Map;
import java.util.WeakHashMap;


public class XPathFactory {
	
	private static XPathFactory INSTANCE;
	
	private Map<String, XPath> xpaths;
	
	
	private XPathFactory() {
		this.xpaths = new WeakHashMap<String, XPath>();
	}
	
	
	public static XPathFactory instance() {
		if (INSTANCE == null) {
			INSTANCE = new XPathFactory();
		}
		return INSTANCE;
	}
	
	
	public XPath getXpath(String xpathString) {
		XPath xpath = this.xpaths.get(xpathString);
		if (xpath == null) {
			xpath = buildXpath(xpathString);
			this.xpaths.put(xpathString, xpath);
		}
		return xpath;
	}
	
	private XPath buildXpath(String xpathString) {
		return new XPath(xpathString);
	}
}
