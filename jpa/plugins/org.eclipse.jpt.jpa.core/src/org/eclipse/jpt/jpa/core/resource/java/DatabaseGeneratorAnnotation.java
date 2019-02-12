/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
 * Common protocol among:<code><ul>
 * <li>javax.persistence.SequenceGenerator
 * <li>javax.persistence.TableGenerator
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface DatabaseGeneratorAnnotation
	extends GeneratorAnnotation
{

	/**
	 * Corresponds to the 'initialValue' element of the *Generator annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getInitialValue();
		String INITIAL_VALUE_PROPERTY = "initialValue"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'initialValue' element of the *Generator annotation.
	 * Set to null to remove the element.
	 */
	void setInitialValue(Integer initialValue);

	/**
	 * Return the {@link TextRange} for the 'initialValue' element. If the element 
	 * does not exist return the {@link TextRange} for the *Generator annotation.
	 */
	TextRange getInitialValueTextRange();


	/**
	 * Corresponds to the 'allocationSize' element of the *Generator annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getAllocationSize();
		String ALLOCATION_SIZE_PROPERTY = "allocationSize"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'allocationSize' element of the *Generator annotation.
	 * Set to null to remove the element.
	 */
	void setAllocationSize(Integer allocationSize);

	/**
	 * Return the {@link TextRange} for the 'allocationSize' element. If the element 
	 * does not exist return the {@link TextRange} for the *Generator annotation.
	 */
	TextRange getAllocationSizeTextRange();
}
