/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotations:<ul>
 * <li><code>javax.persistence.Column<code>
 * <li><code>javax.persistence.MapKeyColumn<code>
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface CompleteColumnAnnotation
	extends BaseColumnAnnotation
{
	// ********** length **********

	/**
	 * Corresponds to the 'length' element of the Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getLength();
		String LENGTH_PROPERTY = "length"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'length' element of the Column annotation.
	 * Set to null to remove the element.
	 */
	void setLength(Integer length);

	/**
	 * Return the {@link TextRange} for the 'length' element. If element
	 * does not exist return the {@link TextRange} for the Column annotation.
	 */
	TextRange getLengthTextRange(CompilationUnit astRoot);


	// ********** precision **********

	/**
	 * Corresponds to the 'precision' element of the Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getPrecision();
		String PRECISION_PROPERTY = "precision"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'precision' element of the Column annotation.
	 * Set to null to remove the element.
	 */
	void setPrecision(Integer precision);

	/**
	 * Return the {@link TextRange} for the 'precision' element. If element
	 * does not exist return the {@link TextRange} for the Column annotation.
	 */
	TextRange getPrecisionTextRange(CompilationUnit astRoot);


	// ********** scale **********

	/**
	 * Corresponds to the 'scale' element of the Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getScale();
		String SCALE_PROPERTY = "scale"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'scale' element of the Column annotation.
	 * Set to null to remove the element.
	 */
	void setScale(Integer scale);

	/**
	 * Return the {@link TextRange} for the 'scale' element. If element
	 * does not exist return the {@link TextRange} for the Column annotation.
	 */
	TextRange getScaleTextRange(CompilationUnit astRoot);

}
