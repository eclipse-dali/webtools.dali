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
 * javax.persistence.JoinTable
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface JoinTableAnnotation
	extends ReferenceTableAnnotation
{
	String ANNOTATION_NAME = JPA.JOIN_TABLE;

	/**
	 * Corresponds to the 'inverseJoinColumns' element of the JoinTable annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterator<JoinColumnAnnotation> inverseJoinColumns();
		String INVERSE_JOIN_COLUMNS_LIST = "inverseJoinColumns"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'inverseJoinColumns' element of the JoinTable annotation.
	 */
	int inverseJoinColumnsSize();

	/**
	 * Corresponds to the 'inverseJoinColumns' element of the JoinTable annotation.
	 */
	JoinColumnAnnotation inverseJoinColumnAt(int index);

	/**
	 * Corresponds to the 'inverseJoinColumns' element of the JoinTable annotation.
	 */
	int indexOfInverseJoinColumn(JoinColumnAnnotation joinColumn);

	/**
	 * Corresponds to the 'inverseJoinColumns' element of the JoinTable annotation.
	 */
	JoinColumnAnnotation addInverseJoinColumn(int index);

	/**
	 * Corresponds to the 'inverseJoinColumns' element of the JoinTable annotation.
	 */
	void moveInverseJoinColumn(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'inverseJoinColumns' element of the JoinTable annotation.
	 */
	void removeInverseJoinColumn(int index);

}
