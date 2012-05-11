/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.ListIterator;

/**
 * Corresponds to the JPA annotation
 * javax.persistence.SecondaryTable
 * 
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
	extends BaseTableAnnotation
{
	String ANNOTATION_NAME = JPA.SECONDARY_TABLE;

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns();
		String PK_JOIN_COLUMNS_LIST = "pkJoinColumns"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 */
	int pkJoinColumnsSize();

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 */
	PrimaryKeyJoinColumnAnnotation pkJoinColumnAt(int index);

	/**
	 * Corresponds to the 'pkJoinColumns' element of the SecondaryTable annotation.
	 */
	int indexOfPkJoinColumn(PrimaryKeyJoinColumnAnnotation pkJoinColumn);

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
