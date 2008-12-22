/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ColumnAnnotation extends BaseColumnAnnotation
{
	String ANNOTATION_NAME = JPA.COLUMN;

	/**
	 * Corresponds to the length element of the javax.persistence.Column annotation.
	 * Returns null if the length valuePair does not exist in the annotation
	 */
	Integer getLength();
	
	/**
	 * Corresponds to the length element of the javax.persistence.Column annotation.
	 * Set to null to remove the length valuePair from the annotation
	 */
	void setLength(Integer length);
		String LENGTH_PROPERTY = "length"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the precision element of the javax.persistence.Column annotation.
	 * Returns null if the precision valuePair does not exist in the annotation
	 */
	Integer getPrecision();
	
	/**
	 * Corresponds to the precision element of the javax.persistence.Column annotation.
	 * Set to null to remove the precision valuePair from the annotation
	 */
	void setPrecision(Integer precision);
		String PRECISION_PROPERTY = "precision"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the scale element of the javax.persistence.Column annotation.
	 * Returns null if the scale valuePair does not exist in the annotation
	 */
	Integer getScale();
	
	/**
	 * Corresponds to the scale element of the javax.persistence.Column annotation.
	 * Set to null to remove the scale valuePair from the annotation
	 */
	void setScale(Integer scale);
		String SCALE_PROPERTY = "scale"; //$NON-NLS-1$

	/**
	 * Return the {@link TextRange} for the length element. If length element
	 * does not exist return the {@link TextRange} for the Column annotation.
	 */
	TextRange getLengthTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the precision element. If precision element
	 * does not exist return the {@link TextRange} for the Column annotation.
	 */
	TextRange getPrecisionTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the scale element. If scale element
	 * does not exist return the {@link TextRange} for the Column annotation.
	 */
	TextRange getScaleTextRange(CompilationUnit astRoot);

}
