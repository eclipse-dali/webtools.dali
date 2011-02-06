/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne;

public class OrmEclipseLinkVariableOneToOneMapping
	extends AbstractOrmAttributeMapping<XmlVariableOneToOne> 
	implements EclipseLinkVariableOneToOneMapping
{
	public OrmEclipseLinkVariableOneToOneMapping(OrmPersistentAttribute parent, XmlVariableOneToOne xmlMapping) {
		super(parent, xmlMapping);
	}

	public String getKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 65;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}

	public void addXmlAttributeMappingTo(org.eclipse.jpt.jpa.core.resource.orm.Attributes xmlAttributes) {
		((Attributes) xmlAttributes).getVariableOneToOnes().add(this.xmlAttributeMapping);
	}
	
	public void removeXmlAttributeMappingFrom(org.eclipse.jpt.jpa.core.resource.orm.Attributes xmlAttributes) {
		((Attributes) xmlAttributes).getVariableOneToOnes().remove(this.xmlAttributeMapping);
	}
}
