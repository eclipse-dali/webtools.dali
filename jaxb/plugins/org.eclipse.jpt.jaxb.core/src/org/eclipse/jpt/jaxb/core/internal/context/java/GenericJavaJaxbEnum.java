/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;


public class GenericJavaJaxbEnum
		extends AbstractJavaType
		implements JavaEnum {
	
	public GenericJavaJaxbEnum(JaxbContextNode parent, JavaResourceEnum resourceType) {
		super(parent, resourceType);
	}
	
	
	@Override
	public JavaResourceEnum getJavaResourceType() {
		return (JavaResourceEnum) super.getJavaResourceType();
	}
	
	public TypeKind getKind() {
		return TypeKind.ENUM;
	}
	
	
	// ***** mapping *****
	
	@Override
	public JavaEnumMapping getMapping() {
		return (JavaEnumMapping) super.getMapping();
	}
	
	@Override
	protected JavaEnumMapping buildMapping() {
		return getFactory().buildJavaEnumMapping(this);
	}
	
	@Override
	protected boolean isSpecifiedMapped() {
		return super.isSpecifiedMapped() 
				|| getXmlEnumAnnotation() != null;
	}
	
	protected XmlEnumAnnotation getXmlEnumAnnotation() {
		return (XmlEnumAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_ENUM);
	}
}
