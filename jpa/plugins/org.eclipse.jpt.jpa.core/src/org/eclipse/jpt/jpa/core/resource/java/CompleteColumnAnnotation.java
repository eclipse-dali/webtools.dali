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

import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotations:<code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.MapKeyColumn
 * </ul></code>
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
	TextRange getLengthTextRange();


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
	TextRange getPrecisionTextRange();


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
	TextRange getScaleTextRange();
}
