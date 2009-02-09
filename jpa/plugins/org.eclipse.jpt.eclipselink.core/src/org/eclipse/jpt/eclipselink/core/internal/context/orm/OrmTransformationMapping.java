/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.TransformationMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation;

public class OrmTransformationMapping extends AbstractOrmAttributeMapping<XmlTransformation> implements TransformationMapping
{
	
	public OrmTransformationMapping(OrmPersistentAttribute parent, XmlTransformation resourceMapping) {
		super(parent, resourceMapping);
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void addToResourceModel(org.eclipse.jpt.core.resource.orm.Attributes resourceAttributes) {
		((Attributes) resourceAttributes).getTransformations().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(org.eclipse.jpt.core.resource.orm.Attributes resourceAttributes) {
		((Attributes) resourceAttributes).getTransformations().remove(this.resourceAttributeMapping);
	}

	
	public int getXmlSequence() {
		return 85;
	}
}
