/*******************************************************************************
 *  Copyright (c) 2009, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;

/**
 * Joining strategy that uses join columns
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.1
 * 
 * @see {@link RelationshipMapping}
 * @see {@link JoinColumnEnabledRelationshipReference}
 */
public interface JoinColumnJoiningStrategy
	extends JoiningStrategy
{
	
	void initializeFrom(JoinColumnJoiningStrategy oldStrategy);
	
	/**
	 * Return the TypeMapping in which this join column is contained.
	 */
	TypeMapping getTypeMapping();
	//TODO getJoinColumnSourceTypeMapping();
	/**
	 * Return a list iterator of the join columns whether specified or default.
	 * This will not be null.
	 */
	<T extends JoinColumn> ListIterator<T> joinColumns();
	
	/**
	 * Return the number of join columns, both specified and default.
	 */
	int joinColumnsSize();
	
	/**
	 * Change notification identifier for "defaultJoinColumn" property
	 */
	String DEFAULT_JOIN_COLUMN_PROPERTY = "defaultJoinColumn"; //$NON-NLS-1$
	
	/**
	 * Return the default join column or null.  If there are specified join 
	 * columns, then there will be no default join column (though there are 
	 * times that there may be no default join column even if there are no
	 * specified join columns.)
	 */
	JoinColumn getDefaultJoinColumn();
	
	/**
	 * Change notification identifier for "specifiedJoinColumns" list
	 */
	String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumns"; //$NON-NLS-1$
	
	/**
	 * Return a list iterator of the specified join columns.
	 * This will not be null.
	 */
	<T extends JoinColumn> ListIterator<T> specifiedJoinColumns();
	
	/**
	 * Return the number of specified join columns.
	 */
	int specifiedJoinColumnsSize();
	
	/**
	 * Return whether this has any specified join columns.
	 * (Equivalent to {@link #specifiedJoinColumnsSize()} == 0)
	 */
	boolean hasSpecifiedJoinColumns();
	
	/**
	 * Add a specified join column to the join table return the object 
	 * representing it.
	 */
	JoinColumn addSpecifiedJoinColumn(int index);
	
	/**
	 * Remove the specified join column from the join table.
	 */
	void removeSpecifiedJoinColumn(int index);
	
	/**
	 * Remove the specified join column at the index from the join table.
	 */
	void removeSpecifiedJoinColumn(JoinColumn joinColumn);
	
	/**
	 * Move the specified join column from the source index to the target index.
	 */
	void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex);
}
