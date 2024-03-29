/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.ArrayList;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedPersistentAttribute2_0;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap;

public abstract class EclipseLinkAbstractOrmBasicMapMapping
	extends AbstractOrmAttributeMapping<XmlBasicMap> 
	implements EclipseLinkBasicMapMapping
{
	protected EclipseLinkAbstractOrmBasicMapMapping(OrmSpecifiedPersistentAttribute parent, XmlBasicMap xmlMapping) {
		super(parent, xmlMapping);
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void addXmlAttributeMappingTo(org.eclipse.jpt.jpa.core.resource.orm.Attributes resourceAttributes) {
		((Attributes) resourceAttributes).getBasicMaps().add(this.xmlAttributeMapping);
	}
	
	public void removeXmlAttributeMappingFrom(org.eclipse.jpt.jpa.core.resource.orm.Attributes resourceAttributes) {
		((Attributes) resourceAttributes).getBasicMaps().remove(this.xmlAttributeMapping);
	}

	
	public int getXmlSequence() {
		return 27;
	}


	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((SpecifiedPersistentAttribute2_0) getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		String targetTypeName = null;
		JavaSpecifiedPersistentAttribute javaPersistentAttribute = this.getJavaPersistentAttribute();
		if (javaPersistentAttribute != null) {
			targetTypeName = javaPersistentAttribute.getMultiReferenceTargetTypeName();
		}
		return (targetTypeName != null) ? targetTypeName : MetamodelField2_0.DEFAULT_TYPE_NAME;
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String mapKeyTypeName = null;
		JavaSpecifiedPersistentAttribute javaPersistentAttribute = getJavaPersistentAttribute();
		if (javaPersistentAttribute != null) {
			mapKeyTypeName = javaPersistentAttribute.getMultiReferenceMapKeyTypeName();
		}
		if (mapKeyTypeName == null) {
			mapKeyTypeName = MetamodelField2_0.DEFAULT_TYPE_NAME;
		}
		typeArgumentNames.add(mapKeyTypeName);
	}
}
