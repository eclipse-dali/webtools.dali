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
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the EclipseLink annotation
 * org.eclipse.persistence.oxm.annotations.XmlTransformation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface XmlTransformationAnnotation
		extends Annotation {
	
	/**
	 * String associated with change events to the 'optional' property
	 */
	String OPTIONAL_PROPERTY = "optional"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'optional' element of the XmlTransformation annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getOptional();
	
	/**
	 * Corresponds to the 'optional' element of the XmlTransformation annotation.
	 * Set to null to remove the element.
	 */
	void setOptional(Boolean optional);
	
	/**
	 * Return the text range associated with the 'optional' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getOptionalTextRange(CompilationUnit astRoot);
}
