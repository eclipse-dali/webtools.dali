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

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Corresponds to the JPA annotation
 * <code>javax.persistence.SecondaryTable</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface SecondaryTableAnnotation
	extends BaseTableAnnotation, NestableAnnotation
{
	String ANNOTATION_NAME = JPA.SECONDARY_TABLE;


	// ********** primary key join columns **********

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<PrimaryKeyJoinColumnAnnotation> getPkJoinColumns();
		String PK_JOIN_COLUMNS_LIST = "pkJoinColumns"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 */
	int getPkJoinColumnsSize();

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 */
	PrimaryKeyJoinColumnAnnotation pkJoinColumnAt(int index);

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 */
	PrimaryKeyJoinColumnAnnotation addPkJoinColumn(int index);

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 */
	void removePkJoinColumn(int index);

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 */
	void movePkJoinColumn(int targetIndex, int sourceIndex);

}
