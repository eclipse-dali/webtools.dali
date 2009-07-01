/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;

public abstract class AbstractOrmManyToManyMapping<T extends XmlManyToMany> 
	extends AbstractOrmMultiRelationshipMapping<T>
	implements OrmManyToManyMapping
{
	protected AbstractOrmManyToManyMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
	}
	
	
	@Override
	protected OrmRelationshipReference buildRelationshipReference() {
		return new GenericOrmManyToManyRelationshipReference(this, this.resourceAttributeMapping);
	}
	
	public int getXmlSequence() {
		return 70;
	}
	
	public String getKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmManyToManyMapping(this);
	}
	
	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getManyToManys().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getManyToManys().remove(this.resourceAttributeMapping);
	}
	
	@Override
	public OrmManyToManyRelationshipReference getRelationshipReference() {
		return (OrmManyToManyRelationshipReference) super.getRelationshipReference();
	}
}
