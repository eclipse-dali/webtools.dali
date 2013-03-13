/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementRefMapping;


public class ELJavaXmlElementRefMapping
		extends GenericJavaXmlElementRefMapping {
	
	public ELJavaXmlElementRefMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	
	@Override
	protected XmlElementRef buildXmlElementRef() {
		return new ELJavaXmlElementRef(this, new GenericJavaXmlElementRefMapping.XmlElementRefContext());
	}
}
