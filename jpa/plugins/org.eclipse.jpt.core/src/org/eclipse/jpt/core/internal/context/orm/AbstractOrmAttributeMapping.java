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

import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping;


public abstract class AbstractOrmAttributeMapping<T extends XmlAttributeMapping> extends AbstractJpaContextNode
	implements AttributeMapping
{
	protected String name;
		public static final String NAME_PROPERTY = "nameProperty";
	
	protected T attributeMapping;

	protected JavaPersistentAttribute javaPersistentAttribute;
		public static final String JAVA_PERSISTENT_ATTRIBUTE_PROPERTY = "javaPersistentAttributeProperty";
	
	protected AbstractOrmAttributeMapping(OrmPersistentAttribute parent) {
		super(parent);
	}
	
	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}
	
	protected void setJavaPersistentAttribute(JavaPersistentAttribute newJavaPersistentAttribute) {
		JavaPersistentAttribute oldJavaPersistentAttribute = this.javaPersistentAttribute;
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
	
	public OrmPersistentAttribute persistentAttribute() {
		return (OrmPersistentAttribute) parent();
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
	protected abstract void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping);

	public void initializeFromXmlAttributeMapping(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> oldMapping) {
		setName(oldMapping.getName());
	}

	public void initializeFromXmlColumnMapping(OrmColumnMapping oldMapping) {
		initializeFromXmlAttributeMapping((AbstractOrmAttributeMapping) oldMapping);
	}

	public void initializeFromXmlNonOwningMapping(NonOwningMapping oldMapping) {
		initializeFromXmlAttributeMapping((AbstractOrmAttributeMapping) oldMapping);
	}

	public void initializeFromXmlBasicMapping(GenericOrmBasicMapping oldMapping) {
		initializeFromXmlColumnMapping(oldMapping);
	}

	public void initializeFromXmlIdMapping(GenericOrmIdMapping oldMapping) {
		initializeFromXmlColumnMapping(oldMapping);
	}

	public void initializeFromXmlTransientMapping(GenericOrmTransientMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlEmbeddedMapping(GenericOrmEmbeddedMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlEmbeddedIdMapping(GenericOrmEmbeddedIdMapping oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlVersionMapping(GenericOrmVersionMapping oldMapping) {
		initializeFromXmlColumnMapping(oldMapping);
	}

	public void initializeFromXmlRelationshipMapping(AbstractOrmRelationshipMapping<? extends XmlRelationshipMapping> oldMapping) {
		initializeFromXmlAttributeMapping(oldMapping);
	}

	public void initializeFromXmlMulitRelationshipMapping(AbstractOrmMultiRelationshipMapping<? extends XmlMultiRelationshipMapping> oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlSingleRelationshipMapping(AbstractOrmSingleRelationshipMapping<? extends XmlSingleRelationshipMapping> oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlOneToManyMapping(GenericOrmOneToManyMapping oldMapping) {
		initializeFromXmlNonOwningMapping(oldMapping);
		initializeFromXmlMulitRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlManyToOneMapping(GenericOrmManyToOneMapping oldMapping) {
		initializeFromXmlSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlOneToOneMapping(GenericOrmOneToOneMapping oldMapping) {
		initializeFromXmlNonOwningMapping(oldMapping);
		initializeFromXmlSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlManyToManyMapping(GenericOrmManyToManyMapping oldMapping) {
		initializeFromXmlNonOwningMapping(oldMapping);
		initializeFromXmlMulitRelationshipMapping(oldMapping);
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

	public OrmTypeMapping<?> typeMapping() {
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
	public abstract void removeFromResourceModel(org.eclipse.jpt.core.resource.orm.AbstractTypeMapping typeMapping);
	
	public abstract T addToResourceModel(org.eclipse.jpt.core.resource.orm.AbstractTypeMapping typeMapping);

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
	
	protected JavaPersistentAttribute javaPersistentAttribute() {
		JavaPersistentType javaPersistentType = persistentAttribute().persistentType().javaPersistentType();
		if (javaPersistentType != null && getName() != null) {
			return javaPersistentType.attributeNamed(getName());
		}
		return null;
	}
	
	public boolean containsOffset(int textOffset) {
		return attributeMapping.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		return attributeMapping.selectionTextRange();
	}
}
