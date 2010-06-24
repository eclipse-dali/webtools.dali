/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.Attributes;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection;

public abstract class AbstractOrmEclipseLinkBasicCollectionMapping
	extends AbstractOrmAttributeMapping<XmlBasicCollection> 
	implements EclipseLinkBasicCollectionMapping
{
	protected AbstractOrmEclipseLinkBasicCollectionMapping(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		super(parent, resourceMapping);
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void addToResourceModel(org.eclipse.jpt.core.resource.orm.Attributes resourceAttributes) {
		((Attributes) resourceAttributes).getBasicCollections().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(org.eclipse.jpt.core.resource.orm.Attributes resourceAttributes) {
		((Attributes) resourceAttributes).getBasicCollections().remove(this.resourceAttributeMapping);
	}

	public int getXmlSequence() {
		return 23;
	}


	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((PersistentAttribute2_0) getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		String targetTypeName = null;
		JavaPersistentAttribute javaPersistentAttribute = getJavaPersistentAttribute();
		if (javaPersistentAttribute != null) {
			targetTypeName = javaPersistentAttribute.getMultiReferenceTargetTypeName();
		}
		return (targetTypeName != null) ? targetTypeName : MetamodelField.DEFAULT_TYPE_NAME;
	}
}
