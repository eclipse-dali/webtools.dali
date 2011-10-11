/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

/**
 * Represents an attribute mapping that may also have an XmlJavaTypeAdapter
 */
public interface XmlAdaptableMapping extends XmlAdaptable {
	
//	/**
//	 * Return either the {@link XmlAdapter} defined here or any XmlAdapter defined at a higher level
//	 * that also applies here.
//	 */
//	XmlAdapter getXmlAdapter();
}
