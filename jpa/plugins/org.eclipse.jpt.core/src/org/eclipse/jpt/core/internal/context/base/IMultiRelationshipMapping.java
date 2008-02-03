/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.Iterator;

public interface IMultiRelationshipMapping extends INonOwningMapping
{
	FetchType DEFAULT_FETCH_TYPE = FetchType.LAZY;

	String getOrderBy();
	void setOrderBy(String value);
		String ORDER_BY_PROPERTY = "orderByProperty";


	boolean isNoOrdering();

	void setNoOrdering();

	boolean isOrderByPk();

	void setOrderByPk();

	boolean isCustomOrdering();

	
	IJoinTable getJoinTable();
	
	boolean isJoinTableSpecified();


	String getMapKey();
	void setMapKey(String value);
		String MAP_KEY_PROPERTY = "mapKeyProperty";

	Iterator<String> candidateMapKeyNames();
}
