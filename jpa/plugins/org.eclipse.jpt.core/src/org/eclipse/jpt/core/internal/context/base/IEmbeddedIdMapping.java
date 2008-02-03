/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;


public interface IEmbeddedIdMapping extends IAttributeMapping, IOverride.Owner
{
	<T extends IAttributeOverride> ListIterator<T> attributeOverrides();
	<T extends IAttributeOverride> ListIterator<T> specifiedAttributeOverrides();
	<T extends IAttributeOverride> ListIterator<T> defaultAttributeOverrides();
	int specifiedAttributeOverridesSize();
	IAttributeOverride addSpecifiedAttributeOverride(int index);
	void removeSpecifiedAttributeOverride(int index);
	void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex);
		String SPECIFIED_ATTRIBUTE_OVERRIDES_LIST = "specifiedAttributeOverridesList";
		String DEFAULT_ATTRIBUTE_OVERRIDES_LIST = "defaultAttributeOverridesList";	
}
