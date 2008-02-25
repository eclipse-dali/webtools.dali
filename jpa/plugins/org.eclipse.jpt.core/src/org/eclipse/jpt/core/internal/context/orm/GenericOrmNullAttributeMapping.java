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

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;


public class GenericOrmNullAttributeMapping extends AbstractOrmAttributeMapping<XmlAttributeMapping>
{

	protected GenericOrmNullAttributeMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	@Override
	public int xmlSequence() {
		return -1;
	}

	@Override
	protected void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		newMapping.initializeFromXmlAttributeMapping(this);
	}

	public String getKey() {
		return null;
	}
	
	@Override
	//TODO throwing an exception correct here?
	public XmlAttributeMapping addToResourceModel(AbstractTypeMapping typeMapping) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		throw new UnsupportedOperationException();
	}
}