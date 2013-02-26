/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.ModifiablePersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection;

public abstract class AbstractOrmEclipseLinkBasicCollectionMapping
	extends AbstractOrmAttributeMapping<XmlBasicCollection> 
	implements EclipseLinkBasicCollectionMapping
{
	protected AbstractOrmEclipseLinkBasicCollectionMapping(OrmModifiablePersistentAttribute parent, XmlBasicCollection xmlMapping) {
		super(parent, xmlMapping);
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void addXmlAttributeMappingTo(org.eclipse.jpt.jpa.core.resource.orm.Attributes resourceAttributes) {
		((Attributes) resourceAttributes).getBasicCollections().add(this.xmlAttributeMapping);
	}
	
	public void removeXmlAttributeMappingFrom(org.eclipse.jpt.jpa.core.resource.orm.Attributes resourceAttributes) {
		((Attributes) resourceAttributes).getBasicCollections().remove(this.xmlAttributeMapping);
	}

	public int getXmlSequence() {
		return 23;
	}


	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((ModifiablePersistentAttribute2_0) getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		String targetTypeName = null;
		JavaModifiablePersistentAttribute javaPersistentAttribute = getJavaPersistentAttribute();
		if (javaPersistentAttribute != null) {
			if(((PersistentType2_0)javaPersistentAttribute).getMetamodelType() == null) { // dynamic type
				return null;
			}
			targetTypeName = javaPersistentAttribute.getMultiReferenceTargetTypeName();
		}
		return (targetTypeName != null) ? targetTypeName : MetamodelField.DEFAULT_TYPE_NAME;
	}
}
