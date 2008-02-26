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
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;


public abstract class AbstractOrmAttributeMapping<T extends XmlAttributeMapping> extends AbstractJpaContextNode
	implements OrmAttributeMapping
{
	protected String name;
	
	protected T attributeMapping;

	protected JavaPersistentAttribute javaPersistentAttribute;
	
	protected AbstractOrmAttributeMapping(OrmPersistentAttribute parent) {
		super(parent);
	}	
	
	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}
	
	protected void setJavaPersistentAttribute(JavaPersistentAttribute newJavaPersistentAttribute) {
		JavaPersistentAttribute oldJavaPersistentAttribute = this.javaPersistentAttribute;
		this.javaPersistentAttribute = newJavaPersistentAttribute;
		firePropertyChanged(OrmAttributeMapping.JAVA_PERSISTENT_ATTRIBUTE_PROPERTY, oldJavaPersistentAttribute, newJavaPersistentAttribute);
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.attributeMapping.setName(newName);
		firePropertyChanged(OrmAttributeMapping.NAME_PROPERTY, oldName, newName);
		persistentAttribute().nameChanged(oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(OrmAttributeMapping.NAME_PROPERTY, oldName, newName);
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
	
	public void initializeFromOrmAttributeMapping(OrmAttributeMapping oldMapping) {
		setName(oldMapping.getName());
	}

	public void initializeFromXmlColumnMapping(OrmColumnMapping oldMapping) {
		initializeFromOrmAttributeMapping((OrmAttributeMapping) oldMapping);
	}

	public void initializeFromXmlNonOwningMapping(NonOwningMapping oldMapping) {
		initializeFromOrmAttributeMapping((OrmAttributeMapping) oldMapping);
	}

	public void initializeFromOrmBasicMapping(OrmBasicMapping oldMapping) {
		initializeFromXmlColumnMapping(oldMapping);
	}

	public void initializeFromOrmIdMapping(OrmIdMapping oldMapping) {
		initializeFromXmlColumnMapping(oldMapping);
	}

	public void initializeFromOrmTransientMapping(OrmTransientMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmEmbeddedMapping(OrmEmbeddedMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmEmbeddedIdMapping(OrmEmbeddedIdMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmVersionMapping(OrmVersionMapping oldMapping) {
		initializeFromXmlColumnMapping(oldMapping);
	}

	public void initializeFromXmlRelationshipMapping(OrmRelationshipMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromXmlMulitRelationshipMapping(OrmMultiRelationshipMapping oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromXmlSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		initializeFromXmlRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmOneToManyMapping(OrmOneToManyMapping oldMapping) {
		initializeFromXmlNonOwningMapping(oldMapping);
		initializeFromXmlMulitRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmManyToOneMapping(OrmManyToOneMapping oldMapping) {
		initializeFromXmlSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmOneToOneMapping(OrmOneToOneMapping oldMapping) {
		initializeFromXmlNonOwningMapping(oldMapping);
		initializeFromXmlSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmManyToManyMapping(OrmManyToManyMapping oldMapping) {
		initializeFromXmlNonOwningMapping(oldMapping);
		initializeFromXmlMulitRelationshipMapping(oldMapping);
	}

	public String primaryKeyColumnName() {
		return null;
	}

	public OrmTypeMapping typeMapping() {
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
	
	public boolean contains(int textOffset) {
		return this.attributeMapping.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		return this.attributeMapping.selectionTextRange();
	}
}
