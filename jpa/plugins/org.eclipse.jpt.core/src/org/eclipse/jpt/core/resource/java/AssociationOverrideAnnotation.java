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
 * javax.persistence.AssociationOverride
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
public interface AssociationOverrideAnnotation
	extends OverrideAnnotation
{
	String ANNOTATION_NAME = JPA.ASSOCIATION_OVERRIDE;


	/**
	 * Corresponds to the 'joinColumns' element of the AssociationOverride annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterator<JoinColumnAnnotation> joinColumns();
		String JOIN_COLUMNS_LIST = "joinColumns"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'joinColumns' element of the AssociationOverride annotation.
	 */
	int joinColumnsSize();

	/**
	 * Corresponds to the 'joinColumns' element of the AssociationOverride annotation.
	 */
	JoinColumnAnnotation joinColumnAt(int index);
	
	/**
	 * Corresponds to the 'joinColumns' element of the AssociationOverride annotation.
	 */
	int indexOfJoinColumn(JoinColumnAnnotation joinColumn);
	
	/**
	 * Corresponds to the 'joinColumns' element of the AssociationOverride annotation.
	 */
	JoinColumnAnnotation addJoinColumn(int index);
	
	/**
	 * Corresponds to the 'joinColumns' element of the AssociationOverride annotation.
	 */
	void moveJoinColumn(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'joinColumns' element of the AssociationOverride annotation.
	 */
	void removeJoinColumn(int index);
	
}
