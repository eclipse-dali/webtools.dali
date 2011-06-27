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

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

public interface XmlAdaptable
	extends JavaContextNode
{
	/********** XmlJavaTypeAdapter **********/
	XmlJavaTypeAdapter getXmlJavaTypeAdapter();
	XmlJavaTypeAdapter addXmlJavaTypeAdapter();
	void removeXmlJavaTypeAdapter();
		String XML_JAVA_TYPE_ADAPTER_PROPERTY = "xmlJavaTypeAdapter"; //$NON-NLS-1$

	interface Owner {
		JavaResourceAnnotatedElement getResource();
		XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation);
		void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter);
	}
}
