/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmStructureNode;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmPersistentAttribute extends AbstractXmlContextNode
	implements OrmStructureNode, OrmPersistentAttribute
{
	protected final Owner owner;

	protected OrmAttributeMapping attributeMapping;
	
	protected JavaPersistentAttribute javaPersistentAttribute;	
	
	public GenericOrmPersistentAttribute(OrmPersistentType parent, Owner owner, XmlAttributeMapping resourceMapping) {
		super(parent);
		this.owner = owner;
		this.attributeMapping = buildAttributeMapping(resourceMapping);
		this.javaPersistentAttribute = findJavaPersistentAttribute();
	}
	
	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}
	
	protected JavaResourcePersistentAttribute getJavaResourcePersistentAttribute() {
		return this.javaPersistentAttribute.getResourcePersistentAttribute();
	}
	
	protected void setJavaPersistentAttribute(JavaPersistentAttribute javaPersistentAttribute) {
		JavaPersistentAttribute old = this.javaPersistentAttribute;
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.firePropertyChanged(JAVA_PERSISTENT_ATTRIBUTE_PROPERTY, old, javaPersistentAttribute);
	}

	protected OrmAttributeMapping buildAttributeMapping(XmlAttributeMapping resourceMapping) {
		return getJpaPlatform().buildOrmAttributeMappingFromMappingKey(this, resourceMapping);
	}
	
	public String getId() {
		return PERSISTENT_ATTRIBUTE_ID;
	}

	public String getName() {
		return this.attributeMapping.getName();
	}

	public void nameChanged(String oldName, String newName) {
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public OrmAttributeMapping getSpecifiedMapping() {
		return this.attributeMapping;
	}
	
	public OrmAttributeMapping getMapping() {
		return this.attributeMapping;
	}

	public String getMappingKey() {
		return this.attributeMapping.getKey();
	}

	public String getDefaultMappingKey() {
		return null;
	}

	public void setSpecifiedMappingKey(String newMappingKey) {
		if (this.getMappingKey() == newMappingKey) {
			return;
		}
		OrmAttributeMapping oldMapping = this.attributeMapping;
		XmlAttributeMapping newResourceAttributeMapping = getJpaPlatform().buildOrmResourceAttributeMapping(newMappingKey, getContentType());
		this.attributeMapping = buildAttributeMapping(newResourceAttributeMapping);
		
		getPersistentType().changeMapping(this, oldMapping, this.attributeMapping);
		firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, this.attributeMapping);
	}

	public OrmPersistentType getPersistentType() {
		return (OrmPersistentType) getParent();
	}

	public OrmTypeMapping getTypeMapping() {
		return getPersistentType().getMapping();
	}

	public boolean isVirtual() {
		return getPersistentType().containsVirtualPersistentAttribute(this);
	}

	public void makeVirtual() {
		if (isVirtual()) {
			throw new IllegalStateException("Attribute is already virtual"); //$NON-NLS-1$
		}
		getPersistentType().makePersistentAttributeVirtual(this);
	}
	
	public void makeSpecified() {
		if (!isVirtual()) {
			throw new IllegalStateException("Attribute is already specified"); //$NON-NLS-1$
		}
		if (getMappingKey() == MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY) {
			throw new IllegalStateException("Use makeSpecified(String) instead and specify a mapping type"); //$NON-NLS-1$
		}
		getPersistentType().makePersistentAttributeSpecified(this);
	}
	
	public void makeSpecified(String mappingKey) {
		if (!isVirtual()) {
			throw new IllegalStateException("Attribute is already specified"); //$NON-NLS-1$
		}
		getPersistentType().makePersistentAttributeSpecified(this, mappingKey);
	}
	
	public String getPrimaryKeyColumnName() {
		return this.attributeMapping.getPrimaryKeyColumnName();
	}

	public boolean isOverridableAttribute() {
		return this.attributeMapping.isOverridableAttributeMapping();
	}

	public boolean isOverridableAssociation() {
		return this.attributeMapping.isOverridableAssociationMapping();
	}

	public boolean isIdAttribute() {
		return this.attributeMapping.isIdMapping();
	}
	
	public void update() {
		this.attributeMapping.update();
		this.setJavaPersistentAttribute(findJavaPersistentAttribute());
		this.owner.updateJavaPersistentAttribute();
	}
	
	protected JavaPersistentAttribute findJavaPersistentAttribute() {
		return this.owner.findJavaPersistentAttribute(this);
	}
	
	public JpaStructureNode getStructureNode(@SuppressWarnings("unused") int offset) {
		return this;
	}

	public IContentType getContentType() {
		return this.getPersistentType().getContentType();
	}

	public boolean contains(int textOffset) {
		if (isVirtual()) {
			return false;
		}
		return this.attributeMapping.contains(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		if (isVirtual()) {
			return null;
		}
		return this.attributeMapping.getSelectionTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.validateAttribute(messages);
		this.validateModifiers(messages);
		this.attributeMapping.validate(messages);
	}
	
	protected void validateAttribute(List<IMessage> messages) {
		if (this.javaPersistentAttribute == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME,
					new String[] {this.getName(), this.getPersistentType().getMapping().getClass_()},
					this.attributeMapping, 
					this.attributeMapping.getNameTextRange()
				)
			);
		}
	}
	
	protected void validateModifiers(List<IMessage> messages) {
		if (this.getMappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			return;
		}
		if (this.javaPersistentAttribute == null) {
			return;
		}
		JavaResourcePersistentAttribute jrpa = this.getJavaResourcePersistentAttribute();

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
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			msgID,
			new String[] {this.getName()},
			this, 
			getValidationTextRange()
		);
	}

	public TextRange getValidationTextRange() {
		if (isVirtual()) {
			return getPersistentType().getMapping().getAttributesTextRange();
		}
		return this.attributeMapping.getValidationTextRange();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
	
	public void dispose() {
		//nothing to dispose
	}

}
