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

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;


public class GenericOrmNullAttributeMapping extends AbstractOrmAttributeMapping<XmlNullAttributeMapping>
{

	public GenericOrmNullAttributeMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	public int getXmlSequence() {
		return -1;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}

	public String getKey() {
		return null;
	}
	
	public XmlAttributeMapping addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		throw new UnsupportedOperationException();
	}

	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		throw new UnsupportedOperationException();
	}
}