/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MultiRelationshipMapping extends NonOwningMapping
{
	FetchType DEFAULT_FETCH_TYPE = FetchType.LAZY;

	String getOrderBy();
	void setOrderBy(String value);
		String ORDER_BY_PROPERTY = "orderBy"; //$NON-NLS-1$


	boolean isNoOrdering();
	void setNoOrdering(boolean newNoOrdering);
		String NO_ORDERING_PROPERTY = "noOrdering"; //$NON-NLS-1$

	boolean isPkOrdering();
	void setPkOrdering(boolean newPkOrdering);
	String PK_ORDERING_PROPERTY = "pkOrdering"; //$NON-NLS-1$

	boolean isCustomOrdering();
	void setCustomOrdering(boolean newCustomOrdering);
	String CUSTOM_ORDERING_PROPERTY = "customOrdering"; //$NON-NLS-1$

	
	JoinTable getJoinTable();
	
	boolean joinTableIsSpecified();


	String getMapKey();
	void setMapKey(String value);
		String MAP_KEY_PROPERTY = "mapKey"; //$NON-NLS-1$

	Iterator<String> candidateMapKeyNames();
}
