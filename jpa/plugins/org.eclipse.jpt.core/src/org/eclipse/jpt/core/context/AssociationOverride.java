/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;

public interface AssociationOverride extends BaseOverride
{
	<T extends JoinColumn> ListIterator<T> joinColumns();
	<T extends JoinColumn> ListIterator<T> specifiedJoinColumns();
	<T extends JoinColumn> ListIterator<T> defaultJoinColumns();
	int joinColumnsSize();
	int specifiedJoinColumnsSize();
	int defaultJoinColumnsSize();
	JoinColumn addSpecifiedJoinColumn(int index);
	void removeSpecifiedJoinColumn(int index);
	void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex);
		String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumnsList";
		String DEFAULT_JOIN_COLUMNS_LIST = "defaultJoinColumnsList";
		
	boolean containsSpecifiedJoinColumns();

	AssociationOverride.Owner owner();
	
	interface Owner extends BaseOverride.Owner
	{
		/**
		 * Return the relationship mapping with the given attribute name.
		 * Return null if it does not exist.  This relationship mapping
		 * will be found in the mapped superclass, not in the owning entity
		 */
		RelationshipMapping relationshipMapping(String attributeName);
	}
}