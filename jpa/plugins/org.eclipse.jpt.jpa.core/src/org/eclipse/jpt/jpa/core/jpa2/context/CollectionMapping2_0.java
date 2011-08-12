/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.Column;

/**
 * JPA 2.0 collection mapping (e.g. 1:m, m:m, element collection)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 2.3
 */
public interface CollectionMapping2_0
	extends CollectionMapping, AttributeMapping2_0, ConvertibleKeyMapping2_0
{
	// ********** map key class **********
	
	String getMapKeyClass();

	String getSpecifiedMapKeyClass();
	void setSpecifiedMapKeyClass(String value);
		String SPECIFIED_MAP_KEY_CLASS_PROPERTY = "specifiedMapKeyClass"; //$NON-NLS-1$

	String getDefaultMapKeyClass();
		String DEFAULT_MAP_KEY_CLASS_PROPERTY = "defaultMapKeyClass"; //$NON-NLS-1$

	/**
	 * Return the character to be used for browsing or creating the map key
	 * class {@link IType}.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getMapKeyClassEnclosingTypeSeparator();


	// ********** map key column **********

	/**
	 * Return the map key column for this collection mapping.
	 */
	Column getMapKeyColumn();

	AttributeOverrideContainer getMapKeyAttributeOverrideContainer();
}
