/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;


public class GenericOrmManyToOneMapping extends AbstractOrmSingleRelationshipMapping<XmlManyToOne>
	implements OrmManyToOneMapping
{

	public GenericOrmManyToOneMapping(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		super(parent, resourceMapping);
	}

	public int getXmlSequence() {
		return 40;
	}

	public String getKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	//ManyToOne mapping is always the owning side
	public boolean isRelationshipOwner() {
		return true;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmManyToOneMapping(this);
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
		
	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getManyToOnes().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getManyToOnes().remove(this.resourceAttributeMapping);
	}
}
