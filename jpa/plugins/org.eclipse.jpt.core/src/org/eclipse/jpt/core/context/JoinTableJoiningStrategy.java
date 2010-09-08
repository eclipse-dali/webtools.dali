/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;

/**
 * Joining strategy that uses a join table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.2
 * 
 * @see {@link RelationshipMapping}
 * @see {@link JoinTableEnabledRelationshipReference}
 */
public interface JoinTableJoiningStrategy 
	extends JoiningStrategy
{
		
	void initializeFrom(JoinTableJoiningStrategy oldStrategy);
	
	/**
	 * Change notification identifier for "joinTable" property
	 */
	String JOIN_TABLE_PROPERTY = "joinTable"; //$NON-NLS-1$
	
	/**
	 * Return the join table used in this reference or null.  This will be the 
	 * specified or default join table if one has been specified or a default 
	 * join table applies, otherwise null.
	 */
	JoinTable getJoinTable();
	
	/**
	 * Return the default name for the JoinTable in this context
	 */
	String getJoinTableDefaultName();

	boolean shouldValidateAgainstDatabase();

	JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver);

	JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver);

}
