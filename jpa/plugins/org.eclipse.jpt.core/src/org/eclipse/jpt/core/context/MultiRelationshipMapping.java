/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
 * Multi-valued (1:m, m:m) relationship mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MultiRelationshipMapping
	extends RelationshipMapping
{
	FetchType DEFAULT_FETCH_TYPE = FetchType.LAZY;

	String getOrderBy();

	String getSpecifiedOrderBy();
	void setSpecifiedOrderBy(String orderBy);
		String SPECIFIED_ORDER_BY_PROPERTY = "specifiedOrderBy"; //$NON-NLS-1$
	
	boolean isNoOrdering();
	void setNoOrdering(boolean noOrdering);
		String NO_ORDERING_PROPERTY = "noOrdering"; //$NON-NLS-1$
	
	boolean isPkOrdering();
	void setPkOrdering(boolean pkOrdering);
		String PK_ORDERING_PROPERTY = "pkOrdering"; //$NON-NLS-1$
	
	boolean isCustomOrdering();
	void setCustomOrdering(boolean customOrdering);
		String CUSTOM_ORDERING_PROPERTY = "customOrdering"; //$NON-NLS-1$


	String getMapKey();

	String getSpecifiedMapKey();
	void setSpecifiedMapKey(String mapKey);
		String SPECIFIED_MAP_KEY_PROPERTY = "specifiedMapKey"; //$NON-NLS-1$

	boolean isNoMapKey();
	void setNoMapKey(boolean noMapKey);
		String NO_MAP_KEY_PROPERTY = "noMapKey"; //$NON-NLS-1$
	
	boolean isPkMapKey();
	void setPkMapKey(boolean pkMapKey);
		String PK_MAP_KEY_PROPERTY = "pkMapKey"; //$NON-NLS-1$
	
	boolean isCustomMapKey();
	void setCustomMapKey(boolean customMapKey);
		String CUSTOM_MAP_KEY_PROPERTY = "customMapKey"; //$NON-NLS-1$

	Iterator<String> candidateMapKeyNames();

	String getMetamodelFieldMapKeyTypeName();
}
