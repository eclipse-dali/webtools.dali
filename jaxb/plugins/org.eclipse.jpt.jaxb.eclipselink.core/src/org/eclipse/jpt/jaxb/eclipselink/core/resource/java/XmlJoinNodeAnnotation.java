/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the EclipseLink annotation
 * org.eclipse.persistence.oxm.annotations.XmlJoinNode
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface XmlJoinNodeAnnotation
		extends NestableAnnotation {
	
	/**
	 * String associated with change events to the 'xmlPath' property
	 */
	String XML_PATH_PROPERTY = "xmlPath"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'xmlPath' element of the XmlJoinNode annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getXmlPath();
	
	/**
	 * Corresponds to the 'xmlPath' element of the XmlJoinNode annotation.
	 * Set to null to remove the element.
	 */
	void setXmlPath(String xmlPath);
	
	/**
	 * Return the text range associated with the 'xmlPath' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getXmlPathTextRange(CompilationUnit astRoot);
	
	/**
	 * Return whether the specified text position is within the 'xmlPath' element.
	 */
	boolean xmlPathTouches(int pos, CompilationUnit astRoot);
	
	
	/**
	 * String associated with change events to the 'referencedXmlPath' property
	 */
	String REFERENCED_XML_PATH_PROPERTY = "referencedXmlPath"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'referencedXmlPath' element of the XmlJoinNode annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getReferencedXmlPath();
	
	/**
	 * Corresponds to the 'referencedXmlPath' element of the XmlJoinNode annotation.
	 * Set to null to remove the element.
	 */
	void setReferencedXmlPath(String referencedXmlPath);
	
	/**
	 * Return the text range associated with the 'referencedXmlPath' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getReferencedXmlPathTextRange(CompilationUnit astRoot);
	
	/**
	 * Return whether the specified text position is within the 'referencedXmlPath' element.
	 */
	boolean referencedXmlPathTouches(int pos, CompilationUnit astRoot);
}
