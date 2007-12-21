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

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.ManyToMany;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;

public class XmlManyToManyMapping extends XmlMultiRelationshipMapping<ManyToMany>
	implements IManyToManyMapping
{

	protected XmlManyToManyMapping(XmlPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping) {
		newMapping.initializeFromXmlManyToManyMapping(this);
	}

	@Override
	public int xmlSequence() {
		return 6;
	}

	// ********** INonOwningMapping implementation **********
	@Override
	public boolean mappedByIsValid(IAttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
	}
	
	@Override
	public ManyToMany addToResourceModel(TypeMapping typeMapping) {
		ManyToMany manyToMany = OrmFactory.eINSTANCE.createManyToMany();
		typeMapping.getAttributes().getManyToManys().add(manyToMany);
		return manyToMany;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getManyToManys().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
}
