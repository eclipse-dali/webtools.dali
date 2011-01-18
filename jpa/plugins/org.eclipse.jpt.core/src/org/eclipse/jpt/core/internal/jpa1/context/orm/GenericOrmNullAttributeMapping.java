/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;

/**
 * <code>orm.xml</code> null attribute mapping
 */
public class GenericOrmNullAttributeMapping
	extends AbstractOrmAttributeMapping<XmlNullAttributeMapping>
{
	public GenericOrmNullAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping xmlMapping) {
		super(parent, xmlMapping);
	}

	public String getKey() {
		return null;
	}

	public int getXmlSequence() {
		return -1;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes resourceAttributes) {
		throw new UnsupportedOperationException();
	}

	public void removeXmlAttributeMappingFrom(Attributes resourceAttributes) {
		throw new UnsupportedOperationException();
	}

	// ********** metamodel **********

	@Override
	public MetamodelField getMetamodelField() {
		return null;
	}
}
