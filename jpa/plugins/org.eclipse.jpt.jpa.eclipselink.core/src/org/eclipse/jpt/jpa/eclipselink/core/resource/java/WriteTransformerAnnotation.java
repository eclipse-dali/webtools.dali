/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;

/**
 * Corresponds to the Eclipselink annotation
 * <code>org.eclipse.persistence.annotations.WriteTransformer</code>
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
public interface WriteTransformerAnnotation
	extends EclipseLinkTransformerAnnotation
{
	String ANNOTATION_NAME = EclipseLink.WRITE_TRANSFORMER;

	/**
	 * Corresponds to the 'column' element of the WriteTransformer annotation.
	 * Return null if the element does not exist in Java.
	 */
	ColumnAnnotation getColumn();
		String COLUMN_PROPERTY = "column"; //$NON-NLS-1$

	/**
	 * Return a ColumnAnnotation, but do not return null if the 'column'
	 * element does not exist on the WriteTransformer annotation. Instead
	 * return a null object.
	 */
	ColumnAnnotation getNonNullColumn();

	/**
	 * Add the 'column' element to the WriteTransformer annotation.
	 */
	ColumnAnnotation addColumn();

	/**
	 * Remove the 'column' element from the WriteTransformer annotation.
	 */
	void removeColumn();

	/**
	 * Return the {@link TextRange} for the 'column' element. If the element 
	 * does not exist return the {@link TextRange} for the WriteTransformer annotation.
	 */
	TextRange getColumnTextRange();
}
