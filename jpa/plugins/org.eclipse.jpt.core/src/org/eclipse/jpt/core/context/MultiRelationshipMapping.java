/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;

public interface MultiRelationshipMapping extends NonOwningMapping
{
	FetchType DEFAULT_FETCH_TYPE = FetchType.LAZY;

	String getOrderBy();
	void setOrderBy(String value);
		String ORDER_BY_PROPERTY = "orderByProperty";


	boolean isNoOrdering();
	void setNoOrdering(boolean newNoOrdering);
		String NO_ORDERING_PROPERTY = "noOrderingProperty";

	boolean isPkOrdering();
	void setPkOrdering(boolean newPkOrdering);
	String PK_ORDERING_PROPERTY = "pkOrderingProperty";

	boolean isCustomOrdering();
	void setCustomOrdering(boolean newCustomOrdering);
	String CUSTOM_ORDERING_PROPERTY = "customOrderingProperty";

	
	JoinTable getJoinTable();
	
	boolean isJoinTableSpecified();


	String getMapKey();
	void setMapKey(String value);
		String MAP_KEY_PROPERTY = "mapKeyProperty";

	Iterator<String> candidateMapKeyNames();
}
