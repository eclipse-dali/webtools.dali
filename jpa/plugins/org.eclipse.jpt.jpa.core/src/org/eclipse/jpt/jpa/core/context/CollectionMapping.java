/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.Iterator;

/**
 * JPA collection mapping (e.g. 1:m, m:m, element collection)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface CollectionMapping
	extends FetchableMapping
{
	FetchType DEFAULT_FETCH_TYPE = FetchType.LAZY;

	Orderable getOrderable();
	
	PersistentType getResolvedTargetType();


	// ********** types **********

	Type getValueType();
		String VALUE_TYPE_PROPERTY = "valueType"; //$NON-NLS-1$

	Type getKeyType();
		String KEY_TYPE_PROPERTY = "keyType"; //$NON-NLS-1$

	public enum Type {
		BASIC_TYPE,
		EMBEDDABLE_TYPE,
		ENTITY_TYPE,
		NO_TYPE
	}


	// ********** map key **********

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
