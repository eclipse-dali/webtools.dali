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
package org.eclipse.jpt.jaxb.eclipselink.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the Oxm annotation
 * org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.2
 */
public interface XmlDiscriminatorValueAnnotation
		extends Annotation {
	
	/**
	 * String associated with change events to the 'value' property
	 */
	String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the XmlDiscriminatorValue annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getValue();
	
	/**
	 * Corresponds to the 'value' element of the XmlDiscriminatorValue annotation.
	 * Set to null to remove the element.
	 */
	void setValue(String value);
	
	/**
	 * Return the text range associated with the 'value' element.
	 * Return null if the element is absent.
	 */
	TextRange getValueTextRange();
	
	/**
	 * Return the text range associated with the 'value' element.
	 * Return the text range of this annotation if the element is absent.
	 */
	TextRange getValueValidationTextRange();	
}
