/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Common protocol among:<code><ul>
 * <li>javax.persistence.NamedQuery
 * <li>javax.persistence.NamedNativeQuery
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 2.2
 */
public interface QueryAnnotation
	extends NestableAnnotation
{
	// ********** name **********

	/**
	 * Corresponds to the 'name' element of the *Query annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'name' element of the *Query annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If element
	 * does not exist return the {@link TextRange} for the *Query annotation.
	 */
	TextRange getNameTextRange();


	// ********** hints **********

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<QueryHintAnnotation> getHints();
		String HINTS_LIST = "hints"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	int getHintsSize();

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	QueryHintAnnotation hintAt(int index);

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	QueryHintAnnotation addHint(int index);

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	void moveHint(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	void removeHint(int index);

}
