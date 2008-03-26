/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;


public class GenericOrmManyToOneMapping extends AbstractOrmSingleRelationshipMapping<XmlManyToOne>
	implements OrmManyToOneMapping
{

	public GenericOrmManyToOneMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	public int getXmlSequence() {
		return 3;
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
		
	public XmlManyToOne addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlManyToOne manyToOne = OrmFactory.eINSTANCE.createXmlManyToOneImpl();
		getPersistentAttribute().initialize(manyToOne);
		typeMapping.getAttributes().getManyToOnes().add(manyToOne);
		return manyToOne;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getManyToOnes().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
}
