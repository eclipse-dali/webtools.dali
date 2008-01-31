/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;


public class XmlNullAttributeMapping extends XmlAttributeMapping<AttributeMapping>
{

	protected XmlNullAttributeMapping(XmlPersistentAttribute parent) {
		super(parent);
	}

	@Override
	public int xmlSequence() {
		return -1;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping) {
		newMapping.initializeFromXmlAttributeMapping(this);
	}

	public String getKey() {
		return null;
	}
	
	@Override
	//TODO throwing an exception correct here?
	public AttributeMapping addToResourceModel(TypeMapping typeMapping) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		throw new UnsupportedOperationException();
	}
}