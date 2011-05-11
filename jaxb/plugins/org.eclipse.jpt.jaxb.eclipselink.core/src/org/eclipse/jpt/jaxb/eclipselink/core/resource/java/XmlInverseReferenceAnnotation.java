/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;


public interface XmlInverseReferenceAnnotation
	extends Annotation {
	
	String ANNOTATION_NAME = ELJaxb.XML_INVERSE_REFERENCE;
	
	/**
	 * String associated with change events to the 'mappedBy' property
	 */
	String MAPPED_BY_PROPERTY = "mappedBy"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'mappedBy' element of the XmlInverseReference annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getMappedBy();
	
	/**
	 * Corresponds to the 'mappedBy' element of the XmlInverseReference annotation.
	 * Set to null to remove the element.
	 */
	void setMappedBy(String mappedBy);
	
	/**
	 * Return the text range associated with the 'mappedBy' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getMappedByTextRange(CompilationUnit astRoot);
}
