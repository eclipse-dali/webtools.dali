/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
 * 1:m, m:m, element collection are all collection mappings.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface CollectionMapping
	extends AttributeMapping, Fetchable
{
	FetchType DEFAULT_FETCH_TYPE = FetchType.LAZY;

	Orderable getOrderable();
	
	PersistentType getResolvedTargetType();


	//**************** map key *****************

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


	// **************** map key class **************************************
	
	String getMapKeyClass();

	String getSpecifiedMapKeyClass();
	void setSpecifiedMapKeyClass(String value);
		String SPECIFIED_MAP_KEY_CLASS_PROPERTY = "specifiedMapKeyClass"; //$NON-NLS-1$

	String getDefaultMapKeyClass();
		String DEFAULT_MAP_KEY_CLASS_PROPERTY = "defaultMapKeyClass"; //$NON-NLS-1$

	/**
	 * Return the char to be used for browsing or creating the map key class IType.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getMapKeyClassEnclosingTypeSeparator();

}
