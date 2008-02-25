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
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;

public class GenericOrmManyToManyMapping extends AbstractOrmMultiRelationshipMapping<XmlManyToMany>
	implements ManyToManyMapping
{

	protected GenericOrmManyToManyMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		newMapping.initializeFromXmlManyToManyMapping(this);
	}

	@Override
	public void initializeFromXmlNonOwningMapping(NonOwningMapping oldMapping) {
		super.initializeFromXmlNonOwningMapping(oldMapping);
		setMappedBy(oldMapping.getMappedBy());
	}

	@Override
	public int xmlSequence() {
		return 6;
	}

	// ********** INonOwningMapping implementation **********
	@Override
	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
	}
	
	@Override
	public XmlManyToMany addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlManyToMany manyToMany = OrmFactory.eINSTANCE.createManyToManyImpl();
		persistentAttribute().initialize(manyToMany);
		typeMapping.getAttributes().getManyToManys().add(manyToMany);
		return manyToMany;
	}
	
	@Override
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		typeMapping.getAttributes().getManyToManys().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
}
