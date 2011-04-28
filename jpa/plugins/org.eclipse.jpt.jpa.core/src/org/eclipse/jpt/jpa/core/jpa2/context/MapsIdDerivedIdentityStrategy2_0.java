/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;

/**
 * Maps ID derived identity strategy
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
public interface MapsIdDerivedIdentityStrategy2_0
	extends DerivedIdentityStrategy2_0
{
// TODO bjv rename value to attributeName
	/**
	 * Return the specified attribute name, or in its absence, the default
	 * attribute name. This the name of the ID attribute the relationship
	 * mapping also maps (e.g. a Child's parent mapping also maps part of
	 * the Child's primary key, the parent ID).
	 */
	String getValue();
	
	/**
	 * String associated with changes to the specified value property of this object
	 */
	String SPECIFIED_VALUE_PROPERTY = "specifiedValue"; //$NON-NLS-1$
	
	/**
	 * Return the specified attribute name.
	 * Return <code>null</code> if no name is specified.
	 */
	String getSpecifiedValue();
	
	/**
	 * Set the specified attribute name.
	 */
	void setSpecifiedValue(String value);
	
	/**
	 * Return whether a default value is ever used.
	 * (In some cases, there can be no default.)
	 */
	boolean usesDefaultValue();
	
	/**
	 * String associated with changes to the default value property of this object
	 */
	String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$
	
	/**
	 * Return the default attribute name.
	 */
	String getDefaultValue();
	
	/**
	 * Return a sorted list of possible attribute names.
	 */
	Iterable<String> getSortedValueChoices();
	
	/**
	 * Return attribute mapping referenced by the attribute name,
	 * which may be a mapping on the entity
	 * or a mapping within the embeddable mapping referenced by the entity's
	 * embedded ID mapping.
	 */
	AttributeMapping getResolvedAttributeMappingValue();
}
