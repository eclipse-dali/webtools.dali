/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

import org.eclipse.jpt.core.context.JpaContextNode;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JoinFetchable extends JpaContextNode
{
	
	/**
	 * Return true if the join-fetch model object exists.  
	 * Have to have a separate flag for this since the default join fetch type
	 * is different depending on whether hasJoinFetch() returns true or false.
	 */
	boolean hasJoinFetch();
	void setJoinFetch(boolean joinFetch);
		String JOIN_FETCH_PROPERTY = "joinFetchProperty"; //$NON-NLS-1$

	JoinFetchType getJoinFetch();

	JoinFetchType getDefaultJoinFetch();
		String DEFAULT_JOIN_FETCH_PROPERTY = "defaultJoinFetchProperty"; //$NON-NLS-1$
		//default if hasJoinFetch returns false
		JoinFetchType DEFAULT_JOIN_FETCH_TYPE = JoinFetchType.INNER;
		
	JoinFetchType getSpecifiedJoinFetch();
	void setSpecifiedJoinFetch(JoinFetchType newSpecifiedJoinFetch);
		String SPECIFIED_JOIN_FETCH_PROPERTY = "specifiedJoinFetchProperty"; //$NON-NLS-1$
	
}
