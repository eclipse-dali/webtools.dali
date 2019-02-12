/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNullAttributeMapping;

/**
 * <code>orm.xml</code> null attribute mapping
 */
public class GenericOrmNullAttributeMapping
	extends AbstractOrmAttributeMapping<XmlNullAttributeMapping>
{
	public GenericOrmNullAttributeMapping(OrmSpecifiedPersistentAttribute parent, XmlNullAttributeMapping xmlMapping) {
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
	public MetamodelField2_0 getMetamodelField() {
		return null;
	}
}
