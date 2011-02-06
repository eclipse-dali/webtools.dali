/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
// TODO bjv rename value to attribute name
	/**
	 * Return the specfied value, or in absence of that, the default value
	 */
	String getValue();
	
	/**
	 * String associated with changes to the specified value property of this object
	 */
	String SPECIFIED_VALUE_PROPERTY = "specifiedValue"; //$NON-NLS-1$
	
	/**
	 * Return the specified value (the id which has been specified in code)
	 * Will return null if no value is specified
	 */
	String getSpecifiedValue();
	
	/**
	 * Set the specified value (the id to specify in code)
	 */
	void setSpecifiedValue(String value);
	
	/**
	 * Return whether a default value is ever used (in some cases, there can be no default)
	 */
	boolean usesDefaultValue();
	
	/**
	 * String associated with changes to the default value property of this object
	 */
	String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$
	
	/**
	 * Return the default value (the id which would be used in the absence of a specified value)
	 */
	String getDefaultValue();
	
	/**
	 * Return a sorted iterator of possible value choices
	 */
	Iterable<String> getSortedValueChoices();
	
	/**
	 * Return a resolved attribute mapping, which may be a mapping on the entity, or a mapping
	 * within an embeddable mapping on the entity
	 */
	AttributeMapping getResolvedAttributeMappingValue();
}
