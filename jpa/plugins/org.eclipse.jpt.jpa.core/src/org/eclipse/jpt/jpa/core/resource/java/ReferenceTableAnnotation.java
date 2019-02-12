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

import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Corresponds to the JPA annotations:<code><ul>
 * <li>javax.persistence.JoinTable
 * <li>javax.persistence.CollectionTable
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface ReferenceTableAnnotation
	extends BaseTableAnnotation
{
	// ********** join columns **********

	/**
	 * Corresponds to the 'joinColumns' element of the JoinTable annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<JoinColumnAnnotation> getJoinColumns();
		String JOIN_COLUMNS_LIST = "joinColumns"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'joinColumns' element of the JoinTable annotation.
	 */
	int getJoinColumnsSize();

	/**
	 * Corresponds to the 'joinColumns' element of the JoinTable annotation.
	 */
	JoinColumnAnnotation joinColumnAt(int index);

	/**
	 * Corresponds to the 'joinColumns' element of the JoinTable annotation.
	 */
	JoinColumnAnnotation addJoinColumn(int index);

	/**
	 * Corresponds to the 'joinColumns' element of the JoinTable annotation.
	 */
	void moveJoinColumn(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'joinColumns' element of the JoinTable annotation.
	 */
	void removeJoinColumn(int index);
}
