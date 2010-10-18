/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.core.resource.java.ContainerAnnotation;

public interface XmlSchemaTypesAnnotation
		extends ContainerAnnotation<XmlSchemaTypeAnnotation> {
	
	String ANNOTATION_NAME = JAXB.XML_SCHEMA_TYPES;
}
