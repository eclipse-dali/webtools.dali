/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the EclipseLink annotation
 * <code>org.eclipse.persistence.annotations.ExistenceChecking</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface ExistenceCheckingAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = EclipseLink.EXISTENCE_CHECKING;

	/**
	 * Corresponds to the 'value' element of the ExistenceChecking annotation.
	 * Return null if the element does not exist in Java.
	 */
	ExistenceType getValue();
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'value' element of the ExistenceChecking annotation.
	 * Set to null to remove the element.
	 */
	void setValue(ExistenceType value);

	/**
	 * Return the {@link TextRange} for the 'value' element. If the element 
	 * does not exist return the {@link TextRange} for the ExistenceChecking annotation.
	 */
	TextRange getValueTextRange();
}
