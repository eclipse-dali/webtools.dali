/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.Entity;

/**
 * JPA 2.0 element collection mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.2
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
	 * If the target class is specified, this will return it fully qualified. If not
	 * specified, it returns the default target class, which is always fully qualified
	 */
	String getFullyQualifiedTargetClass();
		String FULLY_QUALIFIED_TARGET_CLASS_PROPERTY = "fullyQualifiedTargetClass"; //$NON-NLS-1$

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
	SpecifiedColumn getValueColumn();


	// ********** override containers **********

	AttributeOverrideContainer getValueAttributeOverrideContainer();

	AssociationOverrideContainer getValueAssociationOverrideContainer();
}
