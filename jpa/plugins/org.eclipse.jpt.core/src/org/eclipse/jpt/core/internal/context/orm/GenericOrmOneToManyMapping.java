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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;


public class GenericOrmOneToManyMapping extends AbstractOrmMultiRelationshipMapping<XmlOneToMany>
	implements OrmOneToManyMapping
{

	public GenericOrmOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		super(parent, resourceMapping);
	}

	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmOneToManyMapping(this);
	}

	@Override
	public void initializeFromOrmNonOwningMapping(NonOwningMapping oldMapping) {
		super.initializeFromOrmNonOwningMapping(oldMapping);
		setMappedBy(oldMapping.getMappedBy());
	}

	public int getXmlSequence() {
		return 50;
	}

	// ********** NonOwningMapping implementation **********
	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}
	
	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getOneToManys().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getOneToManys().remove(this.resourceAttributeMapping);
	}
}
