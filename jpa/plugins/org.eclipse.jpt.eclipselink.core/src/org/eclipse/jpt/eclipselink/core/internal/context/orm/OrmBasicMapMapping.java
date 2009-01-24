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
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.BasicMapMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap;

public class OrmBasicMapMapping extends AbstractOrmAttributeMapping<XmlBasicMap> implements BasicMapMapping
{
	
	public OrmBasicMapMapping(OrmPersistentAttribute parent) {
		super(parent);
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlBasicMap addToResourceModel(XmlTypeMapping typeMapping) {
		XmlBasicMap basicMap = EclipseLinkOrmFactory.eINSTANCE.createXmlBasicMapImpl();
		getPersistentAttribute().initialize(basicMap);
		((Attributes) typeMapping.getAttributes()).getBasicMaps().add(basicMap);
		return basicMap;
	}
	
	public void removeFromResourceModel(XmlTypeMapping typeMapping) {
		((Attributes) typeMapping.getAttributes()).getBasicMaps().remove(this.resourceAttributeMapping);
	}

	
	public int getXmlSequence() {
		return 27;
	}
}
