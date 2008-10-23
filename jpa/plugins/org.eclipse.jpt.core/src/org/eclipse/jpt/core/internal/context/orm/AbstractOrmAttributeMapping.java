/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
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
import org.eclipse.jpt.core.internal.context.persistence.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class AbstractOrmAttributeMapping<T extends XmlAttributeMapping>
	extends AbstractXmlContextNode
	implements OrmAttributeMapping
{
	protected String name;
	
	protected T resourceAttributeMapping;

	protected JavaPersistentAttribute javaPersistentAttribute;
	

	protected AbstractOrmAttributeMapping(OrmPersistentAttribute parent) {
		super(parent);
	}	
	
	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}
	
	protected void setJavaPersistentAttribute(JavaPersistentAttribute javaPersistentAttribute) {
		JavaPersistentAttribute old = this.javaPersistentAttribute;
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.firePropertyChanged(JAVA_PERSISTENT_ATTRIBUTE_PROPERTY, old, javaPersistentAttribute);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.resourceAttributeMapping.setName(name);
		this.firePropertyChanged(NAME_PROPERTY, old, name);
		this.getPersistentAttribute().nameChanged(old, name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
		this.getPersistentAttribute().nameChanged(old, name);
	}
	
	public OrmPersistentAttribute getPersistentAttribute() {
		return (OrmPersistentAttribute) getParent();
	}

	public String getAttributeName() {
		return this.getPersistentAttribute().getName();
	}

	public boolean isDefault() {
		return false;
	}
	
	public void initializeFromOrmAttributeMapping(OrmAttributeMapping oldMapping) {
		setName(oldMapping.getName());
	}

	public void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		initializeFromOrmAttributeMapping((OrmAttributeMapping) oldMapping);
	}

	public void initializeFromOrmNonOwningMapping(NonOwningMapping oldMapping) {
		initializeFromOrmAttributeMapping((OrmAttributeMapping) oldMapping);
	}

	public void initializeFromOrmBasicMapping(OrmBasicMapping oldMapping) {
		initializeFromOrmColumnMapping(oldMapping);
	}

	public void initializeFromOrmIdMapping(OrmIdMapping oldMapping) {
		initializeFromOrmColumnMapping(oldMapping);
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
		initializeFromOrmColumnMapping(oldMapping);
	}

	public void initializeFromOrmRelationshipMapping(OrmRelationshipMapping oldMapping) {
		initializeFromOrmAttributeMapping(oldMapping);
	}

	public void initializeFromOrmMulitRelationshipMapping(OrmMultiRelationshipMapping oldMapping) {
		initializeFromOrmRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		initializeFromOrmRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmOneToManyMapping(OrmOneToManyMapping oldMapping) {
		initializeFromOrmNonOwningMapping(oldMapping);
		initializeFromOrmMulitRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmManyToOneMapping(OrmManyToOneMapping oldMapping) {
		initializeFromOrmSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmOneToOneMapping(OrmOneToOneMapping oldMapping) {
		initializeFromOrmNonOwningMapping(oldMapping);
		initializeFromOrmSingleRelationshipMapping(oldMapping);
	}

	public void initializeFromOrmManyToManyMapping(OrmManyToManyMapping oldMapping) {
		initializeFromOrmNonOwningMapping(oldMapping);
		initializeFromOrmMulitRelationshipMapping(oldMapping);
	}

	public String getPrimaryKeyColumnName() {
		return null;
	}

	public OrmTypeMapping getTypeMapping() {
		return this.getPersistentAttribute().getTypeMapping();
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

	public T getResourceAttributeMapping() {
		return this.resourceAttributeMapping;
	}

	public void initialize(XmlAttributeMapping resourceAttributeMapping) {
		this.resourceAttributeMapping = (T) resourceAttributeMapping;
		this.name = resourceAttributeMapping.getName();
		this.javaPersistentAttribute = findJavaPersistentAttribute();
	}
	
	public void update() {
		this.setName_(this.resourceAttributeMapping.getName());
		this.setJavaPersistentAttribute(findJavaPersistentAttribute());
	}
	
	protected JavaPersistentAttribute findJavaPersistentAttribute() {
		JavaPersistentType javaPersistentType = getPersistentAttribute().getPersistentType().getJavaPersistentType();
		if (javaPersistentType != null && getName() != null) {
			return javaPersistentType.getAttributeNamed(getName());
		}
		return null;
	}
	
	
	protected boolean ownerIsEntity() {
		return getTypeMapping().getKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean contains(int textOffset) {
		return this.resourceAttributeMapping.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		return this.resourceAttributeMapping.getSelectionTextRange();
	}	
	
	public TextRange getValidationTextRange() {
		return (this.getPersistentAttribute().isVirtual()) ? this.getTypeMapping().getAttributesTextRange() : this.resourceAttributeMapping.getValidationTextRange();
	}
	
	public TextRange getNameTextRange() {
		return this.resourceAttributeMapping.getNameTextRange();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.validateAttribute(messages);
		this.validateModifiers(messages);
		this.validateMapping(messages);
	}
	
	protected void validateAttribute(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(this.name)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME,
					this, 
					this.getValidationTextRange()
				)
			);
			return;
		}

		if (this.findJavaPersistentAttribute() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME,
					new String[] {this.name, this.getPersistentAttribute().getPersistentType().getMapping().getClass_()},
					this, 
					this.getNameTextRange()
				)
			);
		}
	}
	
	protected void validateModifiers(List<IMessage> messages) {
		if (this.getKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			return;
		}
		if (this.javaPersistentAttribute == null) {
			return;
		}
		JavaResourcePersistentAttribute jrpa = this.javaPersistentAttribute.getResourcePersistentAttribute();

		if (jrpa.isForField()) {
			if (jrpa.isFinal()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD));
			}
			if (jrpa.isPublic()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD));
			}
		} else {
			//TODO validation : need to have a validation message for final methods as well.
			//From the JPA spec : No methods or persistent instance variables of the entity class may be final.
		}
	}

	protected IMessage buildAttributeMessage(String msgID) {
		OrmPersistentAttribute pa = this.getPersistentAttribute();
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			msgID,
			new String[] {this.name},
			pa, 
			pa.getValidationTextRange()
		);
	}

	//TODO validation message - i think more info is needed in this message.  include type mapping type?
	protected void validateMapping(List<IMessage> messages) {
		if ( ! this.getTypeMapping().attributeMappingKeyAllowed(this.getKey())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,
					new String[] {this.name},
					this, 
					this.getValidationTextRange()
				)
			);
		}
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getAttributeName());
	}

}
