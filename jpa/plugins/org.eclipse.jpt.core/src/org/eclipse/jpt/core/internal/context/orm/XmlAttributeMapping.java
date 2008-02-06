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
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.MultiRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping;
import org.eclipse.jpt.core.internal.resource.orm.SingleRelationshipMapping;


public abstract class XmlAttributeMapping<T extends AttributeMapping> extends JpaContextNode
	implements IAttributeMapping
{
	protected String name;
		public static final String NAME_PROPERTY = "nameProperty";
	
	protected T attributeMapping;

	protected IJavaPersistentAttribute javaPersistentAttribute;
		public static final String JAVA_PERSISTENT_ATTRIBUTE_PROPERTY = "javaPersistentAttributeProperty";
	
	protected XmlAttributeMapping(XmlPersistentAttribute parent) {
		super(parent);
	}
	
	public IJavaPersistentAttribute getJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}
	
	protected void setJavaPersistentAttribute(IJavaPersistentAttribute newJavaPersistentAttribute) {
		IJavaPersistentAttribute oldJavaPersistentAttribute = this.javaPersistentAttribute;
		this.javaPersistentAttribute = newJavaPersistentAttribute;
		firePropertyChanged(JAVA_PERSISTENT_ATTRIBUTE_PROPERTY, oldJavaPersistentAttribute, newJavaPersistentAttribute);
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.attributeMapping.setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
		persistentAttribute().nameChanged(oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
		persistentAttribute().nameChanged(oldName, newName);
	}
	
	public XmlPersistentAttribute persistentAttribute() {
		return (XmlPersistentAttribute) parent();
	}

	public String attributeName() {
		return this.persistentAttribute().getName();
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

	public void initializeFromXmlRelationshipMapping(XmlRelationshipMapping<? extends RelationshipMapping> oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlMulitRelationshipMapping(XmlMultiRelationshipMapping<? extends MultiRelationshipMapping> oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlSingleRelationshipMapping(XmlSingleRelationshipMapping<? extends SingleRelationshipMapping> oldMapping) {
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

	public IJpaContextNode contextNode(int offset) {
		if (this.attributeMapping.contains(offset)) {
			return persistentAttribute();
		}
		return null;
	}

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
		this.javaPersistentAttribute = javaPersistentAttribute();
	}
	
	public void update(T attributeMapping) {
		this.attributeMapping = attributeMapping;
		this.setName_(attributeMapping.getName());
		this.setJavaPersistentAttribute(javaPersistentAttribute());
	}
	
	protected IJavaPersistentAttribute javaPersistentAttribute() {
		IJavaPersistentType javaPersistentType = persistentAttribute().persistentType().javaPersistentType();
		if (javaPersistentType != null && getName() != null) {
			return javaPersistentType.attributeNamed(getName());
		}
		return null;
	}
}
