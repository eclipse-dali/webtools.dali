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

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;


public class GenericOrmOneToOneMapping extends AbstractOrmSingleRelationshipMapping<XmlOneToOne>
	implements OneToOneMapping
{
	
	protected String mappedBy;


	protected GenericOrmOneToOneMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	@Override
	protected void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		newMapping.initializeFromXmlOneToOneMapping(this);
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

	public TextRange mappedByTextRange(CompilationUnit astRoot) {
		return null;
//		if (node == null) {
//			return typeMapping().validationTextRange();
//		}
//		IDOMNode mappedByNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.MAPPED_BY);
//		return (mappedByNode == null) ? validationTextRange() : buildTextRange(mappedByNode);
	}

	@Override
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
	
	@Override
	public XmlOneToOne addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlOneToOne oneToOne = OrmFactory.eINSTANCE.createOneToOneImpl();
		persistentAttribute().initialize(oneToOne);
		typeMapping.getAttributes().getOneToOnes().add(oneToOne);
		return oneToOne;
	}
	
	@Override
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<OrmJoinColumn> joinColumns() {
		return super.joinColumns();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		// TODO Auto-generated method stub
		return super.specifiedJoinColumns();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<OrmJoinColumn> defaultJoinColumns() {
		// TODO Auto-generated method stub
		return super.defaultJoinColumns();
	}
}
