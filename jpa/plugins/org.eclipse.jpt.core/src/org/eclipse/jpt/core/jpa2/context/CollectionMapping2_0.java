/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;

/**
 * 1:m, m:m, element collection are all collection mappings.
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
public interface CollectionMapping2_0
	extends CollectionMapping
{


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

	/**
	 * If the attribute type is not a map or if the map key class is a basic type this will return null.
	 */
	Embeddable getResolvedMapKeyEmbeddable();
		String RESOLVED_MAP_KEY_EMBEDDABLE_PROPERTY = "resolvedMapKeyEmbeddable"; //$NON-NLS-1$

	/**
	 * If the attribute type is not a map or if the map key class is a basic type this will return null.
	 */
	Entity getResolvedMapKeyEntity();
		String RESOLVED_MAP_KEY_ENTITY_PROPERTY = "resolvedMapKeyEntity"; //$NON-NLS-1$


	// **************** key column **************************************

	/**
	 * Return the map key column for this collection mapping.
	 */
	Column getMapKeyColumn();

}
