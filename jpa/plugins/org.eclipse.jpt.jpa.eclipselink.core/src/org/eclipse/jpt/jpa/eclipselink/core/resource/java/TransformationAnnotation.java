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
import org.eclipse.jpt.jpa.core.resource.java.FetchType;

/**
 * Corresponds to the EclipseLink annotation
 * <code>org.eclipse.persistence.annotations.Transformation</code>
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
public interface TransformationAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = EclipseLink.TRANSFORMATION;

	/**
	 * Corresponds to the 'fetch' element of the Transformation annotation.
	 * Return null if the element does not exist in Java.
	 */
	FetchType getFetch();
		String FETCH_PROPERTY = "fetch"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'fetch' element of the Transformation annotation.
	 * Set to null to remove the element.
	 */
	void setFetch(FetchType fetch);

	/**
	 * Return the {@link TextRange} for the 'fetch' element. If the element 
	 * does not exist return the {@link TextRange} for the Transformation annotation.
	 */
	TextRange getFetchTextRange();


	/**
	 * Corresponds to the 'optional' element of the Transformation annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getOptional();
		String OPTIONAL_PROPERTY = "optional"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'optional' element of the Transformation annotation.
	 * Set to null to remove the element.
	 */
	void setOptional(Boolean optional);

	/**
	 * Return the {@link TextRange} for the 'optional' element. If the element 
	 * does not exist return the {@link TextRange} for the Transformation annotation.
	 */
	TextRange getOptionalTextRange();
}
