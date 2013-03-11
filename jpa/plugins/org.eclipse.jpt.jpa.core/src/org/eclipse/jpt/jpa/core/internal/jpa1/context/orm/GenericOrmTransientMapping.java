/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTransient;

/**
 * <code>orm.xml</code> transient mapping
 */
public class GenericOrmTransientMapping
	extends AbstractOrmAttributeMapping<XmlTransient>
	implements OrmTransientMapping
{
	public GenericOrmTransientMapping(OrmSpecifiedPersistentAttribute parent, XmlTransient xmlMapping) {
		super(parent, xmlMapping);
	}

	public String getKey() {
		return MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 90;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmTransientMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes resourceAttributes) {
		resourceAttributes.getTransients().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes resourceAttributes) {
		resourceAttributes.getTransients().remove(this.xmlAttributeMapping);
	}

	// ********** metamodel **********

	@Override
	public MetamodelField2_0 getMetamodelField() {
		return null;
	}
}
