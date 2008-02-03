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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.OneToOne;
import org.eclipse.jpt.core.internal.resource.orm.OneToOneImpl;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;


public class XmlOneToOneMapping extends XmlSingleRelationshipMapping<OneToOne>
	implements IOneToOneMapping
{
	
	protected String mappedBy;


	protected XmlOneToOneMapping(XmlPersistentAttribute parent) {
		super(parent);
	}

	@Override
	protected void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping) {
		newMapping.initializeFromXmlOneToOneMapping(this);
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
	
	public boolean mappedByIsValid(IAttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	public ITextRange mappedByTextRange(CompilationUnit astRoot) {
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
		return IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
	
	@Override
	public OneToOne addToResourceModel(TypeMapping typeMapping) {
		OneToOneImpl oneToOne = OrmFactory.eINSTANCE.createOneToOneImpl();
		typeMapping.getAttributes().getOneToOnes().add(oneToOne);
		return oneToOne;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getOneToOnes().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	@Override
	public Iterator<String> candidateMappedByAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void initialize(OneToOne oneToOne) {
		super.initialize(oneToOne);
		this.mappedBy = oneToOne.getMappedBy();
	}
	
	@Override
	public void update(OneToOne oneToOne) {
		super.update(oneToOne);
		this.setMappedBy_(oneToOne.getMappedBy());
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
