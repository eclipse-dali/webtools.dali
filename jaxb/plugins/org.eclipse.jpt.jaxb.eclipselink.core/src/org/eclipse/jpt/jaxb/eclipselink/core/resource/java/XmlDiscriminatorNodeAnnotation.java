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

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the Oxm annotation
 * org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode
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
public interface XmlDiscriminatorNodeAnnotation
		extends Annotation {
	
	/**
	 * String associated with change events to the 'value' property
	 */
	String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the XmlDiscriminatorNode annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getValue();
	
	/**
	 * Corresponds to the 'value' element of the XmlDiscriminatorNode annotation.
	 * Set to null to remove the element.
	 */
	void setValue(String value);
	
	/**
	 * Return the text range associated with the 'value' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getValueTextRange();
	
	/**
	 * Return whether the specified text position is within the 'value' element.
	 */
	boolean valueTouches(int pos);
}
