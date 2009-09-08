/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.XmlContextNode;

public interface OrmAttributeOverrideContainer extends AttributeOverrideContainer, XmlContextNode
{
	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> attributeOverrides();

	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> specifiedAttributeOverrides();

	@SuppressWarnings("unchecked")
	ListIterator<OrmAttributeOverride> virtualAttributeOverrides();
		
	OrmAttributeOverride getAttributeOverrideNamed(String name);
	
	void update();
}