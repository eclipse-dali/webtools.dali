/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;

/**
 * This will be used in cases where the java support attribute mapping types
 * that are not supported by the particular version of the orm.xml file.  For example,
 * EclipseLink supports variable 1-1 mappings, but the generic orm.xml file does not.
 */
public class UnsupportedOrmAttributeMapping 
	extends AbstractOrmAttributeMapping<XmlNullAttributeMapping>
{

	public UnsupportedOrmAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		super(parent, resourceMapping);
	}

	public int getXmlSequence() {
		return -1;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}

	public String getKey() {
		//this ends up returning the java attribute mapping key
		return this.resourceAttributeMapping.getMappingKey();
	}
	
	public void addToResourceModel(Attributes resourceAttributes) {
		throw new UnsupportedOperationException();
	}

	public void removeFromResourceModel(Attributes resourceAttributes) {
		throw new UnsupportedOperationException();
	}
}
