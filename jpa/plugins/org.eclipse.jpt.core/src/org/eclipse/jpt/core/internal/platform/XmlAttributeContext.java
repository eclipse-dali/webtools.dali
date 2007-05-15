/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class XmlAttributeContext extends BaseContext
{
	private XmlAttributeMapping xmlAttributeMapping;
	
	private JavaAttributeContext javaAttributeContext;
	
	protected XmlAttributeContext(IContext parentContext, XmlAttributeMapping xmlAttributeMapping) {
		super(parentContext);
		this.xmlAttributeMapping = xmlAttributeMapping;
	}
	
	@Override
	protected void initialize() {
		
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		JavaPersistentType javaPersistentType = this.xmlAttributeMapping.getPersistentType().findJavaPersistentType();
		String name = this.xmlAttributeMapping.getPersistentAttribute().getName();
		if (name != null && javaPersistentType != null) {
			JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.attributeNamed(name);
			if (javaPersistentAttribute != null) {
				this.javaAttributeContext = 
					(JavaAttributeContext) getPlatform().buildJavaAttributeContext(this, javaPersistentAttribute.getMapping());
			}
		}
		this.xmlAttributeMapping.refreshDefaults(defaultsContext);
	}
	
	protected XmlAttributeMapping attributeMapping() {
		return this.xmlAttributeMapping;
	}
	
	protected IAttributeMapping javaAttributeMapping() {
		if (this.javaAttributeContext != null) {
			return this.javaAttributeContext.getMapping();
		}
		return null;
	}
	
	protected boolean embeddableOwned() {
		return attributeMapping().typeMapping().getKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	protected boolean entityOwned() {
		return attributeMapping().typeMapping().getKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	public final DefaultsContext wrapDefaultsContext(final DefaultsContext defaultsContext) {
		return new DefaultsContext() {
			public Object getDefault(String key) {
				return XmlAttributeContext.this.getDefault(key, defaultsContext);
			}
		
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				return defaultsContext.persistentType(fullyQualifiedTypeName);
			}
		};
	}
	
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		return defaultsContext.getDefault(key);
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
		XmlPersistentAttribute persistentAttribute = xmlAttributeMapping.getPersistentAttribute();
		if (StringTools.stringIsEmpty(persistentAttribute.getName())) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME,
					persistentAttribute, persistentAttribute.nameTextRange())
			);
		}
	}
	
	protected void addUnresolvedAttributeMessage(List<IMessage> messages) {
		XmlPersistentAttribute persistentAttribute = xmlAttributeMapping.getPersistentAttribute();
		if (! StringTools.stringIsEmpty(persistentAttribute.getName())
				&& persistentAttribute.persistentType().findJdtType() != null
				&& persistentAttribute.getAttribute() == null) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME,
					new String[] {persistentAttribute.getName(), persistentAttribute.persistentType().getClass_()},
					persistentAttribute, persistentAttribute.nameTextRange())
			);
		}
	}
	
	protected void addModifierMessages(List<IMessage> messages) {
		XmlPersistentAttribute attribute = xmlAttributeMapping.getPersistentAttribute();
		
		if (attribute.getMapping().getKey() != IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY
				&& attribute.getAttribute() != null 
				&& attribute.getAttribute().isField()) {
			int flags;
			try {
				flags = attribute.getAttribute().getJdtMember().getFlags();
			} catch (JavaModelException jme) { 
				/* no error to log, in that case */ 
				return;
			}
			
			if (Flags.isFinal(flags)) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD,
						new String[] {attribute.getName()},
						attribute, attribute.validationTextRange())
				);
			}
			
			if (Flags.isPublic(flags)) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD,
						new String[] {attribute.getName()},
						attribute, attribute.validationTextRange())
				);
				
			}
		}
	}
	
	protected void addInvalidMappingMessage(List<IMessage> messages) {
		IAttributeMapping attributeMapping = attributeMapping();
		ITypeMapping typeMapping = attributeMapping.typeMapping();
		if (! typeMapping.attributeMappingKeyAllowed(attributeMapping.getKey())) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,
					new String[] {attributeMapping.getPersistentAttribute().getName()},
					attributeMapping, attributeMapping.validationTextRange())
			);
		}
	}
}