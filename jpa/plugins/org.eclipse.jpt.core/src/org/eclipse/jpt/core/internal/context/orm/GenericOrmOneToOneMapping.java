/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.utility.TextRange;


public class GenericOrmOneToOneMapping extends AbstractOrmSingleRelationshipMapping<XmlOneToOne>
	implements OrmOneToOneMapping
{
	
	protected String mappedBy;


	public GenericOrmOneToOneMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmOneToOneMapping(this);
	}

	@Override
	public void initializeFromXmlNonOwningMapping(NonOwningMapping oldMapping) {
		super.initializeFromXmlNonOwningMapping(oldMapping);
		setMappedBy(oldMapping.getMappedBy());
	}
	
	public boolean isRelationshipOwner() {
		return getMappedBy() == null;
	}

	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		attributeMapping().setMappedBy(newMappedBy);
		firePropertyChanged(MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}
	
	protected void setMappedBy_(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		firePropertyChanged(MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}	
	
	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	public TextRange mappedByTextRange() {
		return null;
//		if (node == null) {
//			return typeMapping().validationTextRange();
//		}
//		IDOMNode mappedByNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.MAPPED_BY);
//		return (mappedByNode == null) ? validationTextRange() : buildTextRange(mappedByNode);
	}

	public int xmlSequence() {
		return 5;
	}

	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
	
	public XmlOneToOne addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlOneToOne oneToOne = OrmFactory.eINSTANCE.createXmlOneToOneImpl();
		persistentAttribute().initialize(oneToOne);
		typeMapping.getAttributes().getOneToOnes().add(oneToOne);
		return oneToOne;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getOneToOnes().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	@Override
	public void initialize(XmlOneToOne oneToOne) {
		super.initialize(oneToOne);
		this.mappedBy = oneToOne.getMappedBy();
	}
	
	@Override
	public void update(XmlOneToOne oneToOne) {
		super.update(oneToOne);
		this.setMappedBy_(oneToOne.getMappedBy());
	}
}
