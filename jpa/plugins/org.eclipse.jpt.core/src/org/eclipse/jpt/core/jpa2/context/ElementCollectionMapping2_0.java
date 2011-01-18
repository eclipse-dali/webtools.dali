/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.ConvertibleMapping;
import org.eclipse.jpt.core.context.Entity;

/**
 * JPA 2.0 element collection mapping
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
public interface ElementCollectionMapping2_0
	extends CollectionMapping2_0, ConvertibleMapping
{
	/**
	 * Return the entity that owns the mapping. This is
	 * just a convenience method that calls {@link #getTypeMapping()} and returns
	 * <code>null</code> if it is not an {@link Entity}.
	 */
	Entity getEntity();


	// ********** target class **********

	String getTargetClass();

	String getSpecifiedTargetClass();
	void setSpecifiedTargetClass(String value);
		String SPECIFIED_TARGET_CLASS_PROPERTY = "specifiedTargetClass"; //$NON-NLS-1$

	String getDefaultTargetClass();
		String DEFAULT_TARGET_CLASS_PROPERTY = "defaultTargetClass"; //$NON-NLS-1$

	/**
	 * Return the character to be used for browsing or creating the target
	 * class {@link IType}.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getTargetClassEnclosingTypeSeparator();


	// ********** collection table **********

	/**
	 * Return the mapping's collection table.
	 * This will not be null.
	 */
	CollectionTable2_0 getCollectionTable();


	// ********** value column **********

	/**
	 * Return the mapping's value column.
	 */
	Column getValueColumn();


	// ********** override containers **********

	AttributeOverrideContainer getValueAttributeOverrideContainer();

	AssociationOverrideContainer getValueAssociationOverrideContainer();
}
