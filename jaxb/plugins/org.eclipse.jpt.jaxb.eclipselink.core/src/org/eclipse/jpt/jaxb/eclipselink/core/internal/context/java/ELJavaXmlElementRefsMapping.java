/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefs;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementRefs;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementRefsMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;


public class ELJavaXmlElementRefsMapping
		extends GenericJavaXmlElementRefsMapping {
	
	public ELJavaXmlElementRefsMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	
	@Override
	protected XmlElementRefs buildXmlElementRefs() {
		return new GenericJavaXmlElementRefs(this, new XmlElementRefsContext());
	}
	
	
	protected class XmlElementRefsContext
			extends GenericJavaXmlElementRefsMapping.XmlElementRefsContext {
		
		@Override
		public XmlElementRef buildXmlElementRef(JaxbContextNode parent, XmlElementRefAnnotation annotation) {
			return new ELJavaXmlElementRef(parent, new GenericJavaXmlElementRefsMapping.XmlElementRefContext(annotation));
		}
	}
}
