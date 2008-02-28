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

import java.util.List;
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
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public abstract class AbstractOrmAttributeMapping<T extends XmlAttributeMapping> extends AbstractOrmJpaContextNode
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
	
	
	public TextRange validationTextRange() {
		return this.attributeMapping.validationTextRange();
	}
	
	public TextRange nameTextRange() {
		return this.attributeMapping.nameTextRange();
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addUnspecifiedAttributeMessage(messages);
		addUnresolvedAttributeMessage(messages);
		addInvalidMappingMessage(messages);
	}
	
	protected void addUnspecifiedAttributeMessage(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(getName())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME,
					this, 
					validationTextRange())
			);
		}
	}
	
	protected void addUnresolvedAttributeMessage(List<IMessage> messages) {
		if (! StringTools.stringIsEmpty(getName())
				&& javaPersistentAttribute() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME,
					new String[] {getName(), persistentAttribute().persistentType().getMapping().getClass_()},
					this, nameTextRange())
			);
		}
	}
	//TODO validation message - i think more info is needed in this message.  include type mapping type?
	protected void addInvalidMappingMessage(List<IMessage> messages) {
		if (! typeMapping().attributeMappingKeyAllowed(getKey())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,
					new String[] {getName()},
					this, 
					validationTextRange())
			);
		}
	}
	
}
