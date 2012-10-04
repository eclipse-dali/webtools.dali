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


public interface XmlAdaptable
		extends JaxbContextNode {
	
	// ***** XmlJavaTypeAdapter *****
	
	String XML_JAVA_TYPE_ADAPTER_PROPERTY = "xmlJavaTypeAdapter"; //$NON-NLS-1$
	
	XmlJavaTypeAdapter getXmlJavaTypeAdapter();
	
	XmlJavaTypeAdapter addXmlJavaTypeAdapter();
	
	void removeXmlJavaTypeAdapter();
}
