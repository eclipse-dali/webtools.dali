/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.TransformationMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation;

public class OrmTransformationMapping extends AbstractOrmAttributeMapping<XmlTransformation> implements TransformationMapping
{
	
	public OrmTransformationMapping(OrmPersistentAttribute parent) {
		super(parent);
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlTransformation addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlTransformation transformation = EclipseLinkOrmFactory.eINSTANCE.createXmlTransformationImpl();
		getPersistentAttribute().initialize(transformation);
		((Attributes) typeMapping.getAttributes()).getTransformations().add(transformation);
		return transformation;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		((Attributes) typeMapping.getAttributes()).getTransformations().remove(this.resourceAttributeMapping);
	}

	
	public int getXmlSequence() {
		return 85;
	}
}
