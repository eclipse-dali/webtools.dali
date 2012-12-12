/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA 2.1 annotation
 * <code>javax.persistence.Converter</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface Converter2_1Annotation
	extends Annotation
{
	String ANNOTATION_NAME = JPA2_1.CONVERTER;
	

	// ********** autoApply **********

	/**
	 * Corresponds to the 'autoApply' element of the Converter annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getAutoApply();
		String AUTO_APPLY_PROPERTY = "autoApply"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'nullable' element of the Converter annotation.
	 * Set to null to remove the element.
	 */
	void setAutoApply(Boolean autoApply);

	/**
	 * Return the {@link TextRange} for the 'autoApply' element. If the element
	 * does not exist return the {@link TextRange} for the Converter annotation.
	 */
	TextRange getAutoApplyTextRange();
}
