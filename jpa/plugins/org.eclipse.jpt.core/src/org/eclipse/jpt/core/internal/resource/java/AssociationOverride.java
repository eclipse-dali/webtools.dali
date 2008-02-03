/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;

/**
 * Corresponds to the javax.persistence.AssociationOverride annotation
 */
public interface AssociationOverride extends OverrideResource
{
	String ANNOTATION_NAME = JPA.ASSOCIATION_OVERRIDE;

	
	/**
	 * Corresponds to the joinColumns element of the AssociationOverride annotation.
	 * Returns an empty iterator if the joinColumns element does not exist in java.
	 */
	ListIterator<JoinColumn> joinColumns();
	
	JoinColumn joinColumnAt(int index);
	
	int indexOfJoinColumn(JoinColumn joinColumn);
	
	int joinColumnsSize();

	JoinColumn addJoinColumn(int index);
	
	void removeJoinColumn(int index);
	
	void moveJoinColumn(int targetIndex, int sourceIndex);
	
		String JOIN_COLUMNS_LIST = "joinColumnsList";
}
