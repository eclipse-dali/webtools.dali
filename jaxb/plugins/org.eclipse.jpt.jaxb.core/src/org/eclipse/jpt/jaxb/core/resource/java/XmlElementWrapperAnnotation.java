/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlElementWrapper
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface XmlElementWrapperAnnotation
		extends QNameAnnotation {
	
	/**
	 * Corresponds to the 'nillable' element of the XmlElementWrapper annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getNillable();
	String NILLABLE_PROPERTY = "nillable"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'nillable' element of the XmlElementWrapper annotation.
	 * Set to null to remove the element.
	 */
	void setNillable(Boolean nillable);
	
	/**
	 * Return the {@link TextRange} for the 'nillable' element. If the element
	 * does not exist return the {@link TextRange} for the XmlElementWrapper annotation.
	 */
	TextRange getNillableTextRange();
	
	/**
	 * Corresponds to the 'required' element of the XmlElementWrapper annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getRequired();
	String REQUIRED_PROPERTY = "required"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'required' element of the XmlElementWrapper annotation.
	 * Set to null to remove the element.
	 */
	void setRequired(Boolean required);
	
	/**
	 * Return the {@link TextRange} for the 'required' element. If the element
	 * does not exist return the {@link TextRange} for the XmlElementWrapper annotation.
	 */
	TextRange getRequiredTextRange();
}
