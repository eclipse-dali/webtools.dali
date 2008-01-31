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

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.ManyToOne;
import org.eclipse.jpt.core.internal.resource.orm.ManyToOneImpl;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;


public class XmlManyToOneMapping extends XmlSingleRelationshipMapping<ManyToOne>
	implements IManyToOneMapping
{

	protected XmlManyToOneMapping(XmlPersistentAttribute parent) {
		super(parent);
	}

	@Override
	public int xmlSequence() {
		return 3;
	}

	public String getKey() {
		return IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping) {
		newMapping.initializeFromXmlManyToOneMapping(this);
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
	
	
	@Override
	public ManyToOne addToResourceModel(TypeMapping typeMapping) {
		ManyToOneImpl manyToOne = OrmFactory.eINSTANCE.createManyToOneImpl();
		typeMapping.getAttributes().getManyToOnes().add(manyToOne);
		return manyToOne;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getManyToOnes().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<XmlJoinColumn> joinColumns() {
		return super.joinColumns();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<XmlJoinColumn> specifiedJoinColumns() {
		// TODO Auto-generated method stub
		return super.specifiedJoinColumns();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<XmlJoinColumn> defaultJoinColumns() {
		// TODO Auto-generated method stub
		return super.defaultJoinColumns();
	}
}
