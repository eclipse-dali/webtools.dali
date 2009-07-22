/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmBaseEmbeddedMapping;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;


public class GenericOrmEmbeddedIdMapping
	extends AbstractOrmBaseEmbeddedMapping<XmlEmbeddedId> 
	implements OrmEmbeddedIdMapping
{

	public GenericOrmEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		super(parent, resourceMapping);
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmEmbeddedIdMapping(this);
	}
	
	public int getXmlSequence() {
		return 10;
	}
	
	//*************** AttributeMapping implementation *********************
	
	public String getKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public boolean isIdMapping() {
		return true;
	}

	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getEmbeddedIds().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getEmbeddedIds().remove(this.resourceAttributeMapping);
	}
}
