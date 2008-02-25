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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;


public class GenericOrmManyToOneMapping extends AbstractOrmSingleRelationshipMapping<XmlManyToOne>
	implements ManyToOneMapping
{

	protected GenericOrmManyToOneMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	@Override
	public int xmlSequence() {
		return 3;
	}

	public String getKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	//ManyToOne mapping is always the owning side
	public boolean isRelationshipOwner() {
		return true;
	}
	
	@Override
	protected void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		newMapping.initializeFromXmlManyToOneMapping(this);
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
	
	
	@Override
	public XmlManyToOne addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlManyToOne manyToOne = OrmFactory.eINSTANCE.createManyToOneImpl();
		persistentAttribute().initialize(manyToOne);
		typeMapping.getAttributes().getManyToOnes().add(manyToOne);
		return manyToOne;
	}
	
	@Override
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		typeMapping.getAttributes().getManyToOnes().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<GenericOrmJoinColumn> joinColumns() {
		return super.joinColumns();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<GenericOrmJoinColumn> specifiedJoinColumns() {
		// TODO Auto-generated method stub
		return super.specifiedJoinColumns();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<GenericOrmJoinColumn> defaultJoinColumns() {
		// TODO Auto-generated method stub
		return super.defaultJoinColumns();
	}
}
