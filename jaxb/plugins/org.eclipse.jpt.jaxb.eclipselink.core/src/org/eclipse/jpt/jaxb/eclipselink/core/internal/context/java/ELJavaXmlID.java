/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jaxb.core.context.XmlNamedNodeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlID;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class ELJavaXmlID
		extends GenericJavaXmlID {
	
	public ELJavaXmlID(XmlNamedNodeMapping parent, XmlIDAnnotation annotation) {
		super(parent, annotation);
	}
	
	
	@Override
	protected void validateAttributeType(List<IMessage> messages, CompilationUnit astRoot) {
		// no op - MOXy does not require String attribute types
	}
}
