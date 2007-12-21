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
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.OneToMany;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;


public class XmlOneToManyMapping extends XmlMultiRelationshipMapping<OneToMany>
	implements IOneToManyMapping
{

	protected XmlOneToManyMapping(XmlPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping) {
		newMapping.initializeFromXmlOneToManyMapping(this);
	}

	@Override
	public int xmlSequence() {
		return 4;
	}

	// ********** INonOwningMapping implementation **********
	@Override
	public boolean mappedByIsValid(IAttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}
	
	@Override
	public OneToMany addToResourceModel(TypeMapping typeMapping) {
		OneToMany oneToMany = OrmFactory.eINSTANCE.createOneToMany();
		typeMapping.getAttributes().getOneToManys().add(oneToMany);
		return oneToMany;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getOneToManys().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
}
