/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
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
