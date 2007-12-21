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

import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;


public abstract class XmlAttributeMapping<T extends AttributeMapping> extends JpaContextNode
	implements IAttributeMapping
{
	protected String name;
		public static final String NAME_PROPERTY = "nameProperty";
	
	protected T attributeMapping;

	protected XmlAttributeMapping(XmlPersistentAttribute parent) {
		super(parent);
	}
//
//	protected INamedColumn.Owner buildOwner() {
//		return new ColumnOwner();
//	}
//
//	@Override
//	protected void addInsignificantFeatureIdsTo(Set<Integer> insignificantFeatureIds) {
//		super.addInsignificantFeatureIdsTo(insignificantFeatureIds);
//		insignificantFeatureIds.add(OrmPackage.XML_ATTRIBUTE_MAPPING__PERSISTENT_ATTRIBUTE);
//	}
//
//	public XmlPersistentType getPersistentType() {
//		return (XmlPersistentType) eContainer();
//	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.attributeMapping.setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, oldName);
		persistentAttribute().nameChanged(oldName, oldName);
	}

	
	public XmlPersistentAttribute persistentAttribute() {
		return (XmlPersistentAttribute) parent();
	}

	public boolean isDefault() {
		return false;
	}


	/**
	 * IMPORTANT:  See XmlAttributeMapping class comment.
	 * Subclasses should override this method to call the
	 * appropriate initializeFrom___Mapping() method.
	 */
	protected abstract void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping);

	public void initializeFromXmlAttributeMapping(XmlAttributeMapping<? extends AttributeMapping> oldMapping) {}

	public void initializeFromXmlBasicMapping(XmlBasicMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlIdMapping(XmlIdMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlTransientMapping(XmlTransientMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlEmbeddedMapping(XmlEmbeddedMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlEmbeddedIdMapping(XmlEmbeddedIdMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlVersionMapping(XmlVersionMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlRelationshipMapping(XmlRelationshipMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlMulitRelationshipMapping(XmlMultiRelationshipMapping oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlSingleRelationshipMapping(XmlSingleRelationshipMapping oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlOneToManyMapping(XmlOneToManyMapping oldMapping) {
		initializeFromXmlMulitRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlManyToOneMapping(XmlManyToOneMapping oldMapping) {
		initializeFromXmlSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlOneToOneMapping(XmlOneToOneMapping oldMapping) {
		initializeFromXmlSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlManyToManyMapping(XmlManyToManyMapping oldMapping) {
		initializeFromXmlMulitRelationshipMapping(oldMapping);
	}

//	public IJpaContentNode getContentNode(int offset) {
//		return getPersistentAttribute();
//	}

	/**
	 * Attributes are a sequence in the orm schema. We must keep
	 * the list of attributes in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.  
	 * 
	 * Each concrete subclass of XmlAttributeMapping must implement this
	 * method and return an int that matches it's order in the schema
	 * @return
	 */
	public abstract int xmlSequence();

//	public void refreshDefaults(DefaultsContext defaultsContext) {
//	// do nothing
//	}

	public String primaryKeyColumnName() {
		return null;
	}

	public XmlTypeMapping<?> typeMapping() {
		return this.persistentAttribute().typeMapping();
	}
//
//	public boolean isVirtual() {
//		return getPersistentType().getVirtualAttributeMappings().contains(this);
//	}
//
//	public void setVirtual(boolean virtual) {
//		getPersistentType().setMappingVirtual(this, virtual);
//	}
//
//	@Override
//	public ITextRange validationTextRange() {
//		return (this.isVirtual()) ? this.getPersistentType().attributesTextRange() : super.validationTextRange();
//	}
//
//	public ITextRange nameTextRange() {
//		IDOMNode nameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.NAME);
//		return (nameNode != null) ? this.buildTextRange(nameNode) : this.validationTextRange();
//	}

	public boolean isOverridableAttributeMapping() {
		return false;
	}

	public boolean isOverridableAssociationMapping() {
		return false;
	}

	public boolean isIdMapping() {
		return false;
	}
	public abstract void removeFromResourceModel(org.eclipse.jpt.core.internal.resource.orm.TypeMapping typeMapping);
	
	public abstract T addToResourceModel(org.eclipse.jpt.core.internal.resource.orm.TypeMapping typeMapping);

	protected T attributeMapping() {
		return this.attributeMapping;
	}

	public void initialize(T attributeMapping) {
		this.attributeMapping = attributeMapping;
		this.name = attributeMapping.getName();
	}
	
	public void update(T attributeMapping) {
		this.attributeMapping = attributeMapping;
		this.setName(attributeMapping.getName());
	}
}
