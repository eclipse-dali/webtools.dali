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
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmBaseEmbeddedMapping;
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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
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
		return (OrmPersistentAttribute) getParent();
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

	public void initializeFromOrmBaseEmbeddedMapping(OrmBaseEmbeddedMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmEmbeddedMapping(OrmEmbeddedMapping oldMapping) {
		initializeFromOrmBaseEmbeddedMapping(oldMapping);
	}

	public void initializeFromOrmEmbeddedIdMapping(OrmEmbeddedIdMapping oldMapping) {
		initializeFromOrmBaseEmbeddedMapping(oldMapping);
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

	public OrmTypeMapping getTypeMapping() {
		return this.persistentAttribute().typeMapping();
	}


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
	
	
	protected boolean entityOwned() {
		return getTypeMapping().getKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean contains(int textOffset) {
		return this.attributeMapping.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		return this.attributeMapping.selectionTextRange();
	}	
	
	public TextRange validationTextRange() {
		return (this.persistentAttribute().isVirtual()) ? this.getTypeMapping().attributesTextRange() : this.attributeMapping.validationTextRange();
	}
	
	public TextRange nameTextRange() {
		return this.attributeMapping.nameTextRange();
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addAttributeMessages(messages);
		addInvalidMappingMessage(messages);
	}
	
	protected void addAttributeMessages(List<IMessage> messages) {
		addUnspecifiedAttributeMessage(messages);
		addUnresolvedAttributeMessage(messages);
		addModifierMessages(messages);
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
					this, 
					nameTextRange())
			);
		}
	}
	
	protected void addModifierMessages(List<IMessage> messages) {
		if (getKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			return;
		}
		
		if (getJavaPersistentAttribute() == null) {
			return;
		}
		JavaResourcePersistentAttribute resourcePersistentAttribute = getJavaPersistentAttribute().getResourcePersistentAttribute();
		if (resourcePersistentAttribute== null) {
			return;
		}

		if (resourcePersistentAttribute.isForField()) {
			int flags;
			try {
				flags = resourcePersistentAttribute.getMember().jdtMember().getFlags();
			} catch (JavaModelException jme) { 
				/* no error to log, in that case */ 
				return;
			}
			//TODO validation : need to have a validation message for final methods as well.
			//From the JPA spec : No methods or persistent instance variables of the entity class may be final.
			if (Flags.isFinal(flags)) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD,
						new String[] {getName()},
						persistentAttribute(),
						persistentAttribute().validationTextRange())
				);
			}
			
			if (Flags.isPublic(flags)) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD,
						new String[] {getName()},
						persistentAttribute(), 
						persistentAttribute().validationTextRange())
				);
				
			}
		}
	}

	//TODO validation message - i think more info is needed in this message.  include type mapping type?
	protected void addInvalidMappingMessage(List<IMessage> messages) {
		if (! getTypeMapping().attributeMappingKeyAllowed(getKey())) {
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
