/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTransformationMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation;

public class OrmEclipseLinkTransformationMapping
	extends AbstractOrmAttributeMapping<XmlTransformation> 
	implements EclipseLinkTransformationMapping
{
	public OrmEclipseLinkTransformationMapping(OrmPersistentAttribute parent, XmlTransformation xmlMapping) {
		super(parent, xmlMapping);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 85;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}
	
	public void addXmlAttributeMappingTo(org.eclipse.jpt.jpa.core.resource.orm.Attributes xmlAttributes) {
		((Attributes) xmlAttributes).getTransformations().add(this.xmlAttributeMapping);
	}
	
	public void removeXmlAttributeMappingFrom(org.eclipse.jpt.jpa.core.resource.orm.Attributes xmlAttributes) {
		((Attributes) xmlAttributes).getTransformations().remove(this.xmlAttributeMapping);
	}
}
