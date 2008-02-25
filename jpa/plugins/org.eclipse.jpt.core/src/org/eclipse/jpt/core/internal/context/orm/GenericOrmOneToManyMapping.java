/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;


public class GenericOrmOneToManyMapping extends AbstractOrmMultiRelationshipMapping<XmlOneToMany>
	implements OneToManyMapping
{

	protected GenericOrmOneToManyMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		newMapping.initializeFromXmlOneToManyMapping(this);
	}

	@Override
	public void initializeFromXmlNonOwningMapping(NonOwningMapping oldMapping) {
		super.initializeFromXmlNonOwningMapping(oldMapping);
		setMappedBy(oldMapping.getMappedBy());
	}

	@Override
	public int xmlSequence() {
		return 4;
	}

	// ********** INonOwningMapping implementation **********
	@Override
	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}
	
	@Override
	public XmlOneToMany addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlOneToMany oneToMany = OrmFactory.eINSTANCE.createOneToManyImpl();
		persistentAttribute().initialize(oneToMany);
		typeMapping.getAttributes().getOneToManys().add(oneToMany);
		return oneToMany;
	}
	
	@Override
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		typeMapping.getAttributes().getOneToManys().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
}
