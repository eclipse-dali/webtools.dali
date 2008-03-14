/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface BaseEmbeddedMapping extends AttributeMapping, AttributeOverride.Owner
{

	/**
	 * Return a list iterator of the attribute overrides whether specified or default.
	 * This will not be null.
	 */
	<T extends AttributeOverride> ListIterator<T> attributeOverrides();
	
	/**
	 * Return the number of attribute overrides, both specified and default.
	 */
	int attributeOverridesSize();

	/**
	 * Return a list iterator of the specified attribute overrides.
	 * This will not be null.
	 */
	<T extends AttributeOverride> ListIterator<T> specifiedAttributeOverrides();
		String SPECIFIED_ATTRIBUTE_OVERRIDES_LIST = "specifiedAttributeOverridesList";
	
	/**
	 * Return the number of specified attribute overrides.
	 */
	int specifiedAttributeOverridesSize();

	/**
	 * Return a list iterator of the default attribute overrides.
	 * This will not be null.
	 */
	<T extends AttributeOverride> ListIterator<T> defaultAttributeOverrides();
		String DEFAULT_ATTRIBUTE_OVERRIDES_LIST = "defaultAttributeOverridesList";

	/**
	 * Return the number of default attribute overrides.
	 */
	int defaultAttributeOverridesSize();

	/**
	 * Add a specified attribute override to the entity return the object 
	 * representing it.
	 */
	AttributeOverride addSpecifiedAttributeOverride(int index);
	
	/**
	 * Remove the specified attribute override from the entity.
	 */
	void removeSpecifiedAttributeOverride(int index);
	
	/**
	 * Remove the specified attribute override at the index from the entity.
	 */
	void removeSpecifiedAttributeOverride(AttributeOverride attributeOverride);
	
	/**
	 * Move the specified attribute override from the source index to the target index.
	 */
	void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex);

}
