/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;


public class GenericOrmOneToManyMapping extends AbstractOrmMultiRelationshipMapping<XmlOneToMany>
	implements OrmOneToManyMapping
{

	public GenericOrmOneToManyMapping(OrmPersistentAttribute parent) {
		super(parent);
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
	
	public XmlOneToMany addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlOneToMany oneToMany = OrmFactory.eINSTANCE.createXmlOneToManyImpl();
		getPersistentAttribute().initialize(oneToMany);
		typeMapping.getAttributes().getOneToManys().add(oneToMany);
		return oneToMany;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getOneToManys().remove(this.resourceAttributeMapping);
	}
}
