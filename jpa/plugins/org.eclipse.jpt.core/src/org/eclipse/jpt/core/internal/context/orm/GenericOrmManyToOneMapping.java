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
import org.eclipse.jpt.core.context.orm.OrmManyToOneRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;


public class GenericOrmManyToOneMapping<T extends XmlManyToOne>
	extends AbstractOrmSingleRelationshipMapping<T>
	implements OrmManyToOneMapping
{
	public GenericOrmManyToOneMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
	}
	
	
	@Override
	protected OrmRelationshipReference buildRelationshipReference() {
		return new GenericOrmManyToOneRelationshipReference(this, this.resourceAttributeMapping);
	}
	
	public int getXmlSequence() {
		return 40;
	}
	
	public String getKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmManyToOneMapping(this);
	}
	
	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getManyToOnes().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getManyToOnes().remove(this.resourceAttributeMapping);
	}
	
	@Override
	public OrmManyToOneRelationshipReference getRelationshipReference() {
		return (OrmManyToOneRelationshipReference) super.getRelationshipReference();
	}
	
	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
}
