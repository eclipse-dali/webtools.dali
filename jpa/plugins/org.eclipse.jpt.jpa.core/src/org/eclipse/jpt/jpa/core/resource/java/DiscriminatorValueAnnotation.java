/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotation
 * <code>javax.persistence.DiscriminatorValue</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface DiscriminatorValueAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = JPA.DISCRIMINATOR_VALUE;

	/**
	 * Corresponds to the 'value' element of the DiscriminatorValue annotation.
	 * Return null if the element does not exist in the annotation.
	 */
	String getValue();
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the DiscriminatorValue annotation.
	 */
	void setValue(String value);

	/**
	 * Return the {@link TextRange} for the 'value' element. If the element
	 * does not exist return the {@link TextRange} for the DiscriminatorValue annotation.
	 */
	TextRange getValueTextRange();
}
