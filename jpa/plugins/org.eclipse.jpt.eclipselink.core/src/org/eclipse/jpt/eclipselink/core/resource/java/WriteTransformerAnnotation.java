/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Java resource model interface that corresponds to the Eclipselink
 * annotation org.eclipse.persistence.annotations.WriteTransformer
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface WriteTransformerAnnotation extends TransformerAnnotation
{
	
	String ANNOTATION_NAME = EclipseLinkJPA.WRITE_TRANSFORMER;
	
	/**
	 * Corresponds to the column element of the WriteTransformer annotation.
	 * Returns null if the column element does not exist in java.
	 */
	ColumnAnnotation getColumn();
	
	/**
	 * Add the column element to the WriteTransformer annotation.
	 */
	ColumnAnnotation addColumn();
	
	/**
	 * Remove the column element from the WriteTransformer annotation.
	 */
	void removeColumn();
	
	/**
	 * Return a ColumnAnnotation, but do not return null if the column
	 * element does not exist on the WriteTransformer annotation.  Instead
	 * return a null object.
	 */
	ColumnAnnotation getNonNullColumn();
	
	String COLUMN_PROPERTY = "columnProperty"; //$NON-NLS-1$
	
	/**
	 * Return the {@link TextRange} for the column element.  If the column element 
	 * does not exist return the {@link TextRange} for the WriteTransformer annotation.
	 */
	TextRange getColumnTextRange(CompilationUnit astRoot);

}
