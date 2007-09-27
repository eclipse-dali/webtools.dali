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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class JavaAttributeContext extends BaseContext
{
	protected IJavaAttributeMapping attributeMapping;
	
	public JavaAttributeContext(IContext parentContext, IJavaAttributeMapping javaAttributeMapping) {
		super(parentContext);
		this.attributeMapping = javaAttributeMapping;
	}
	
	@Override
	protected void initialize() {}

	protected void populateGeneratorRepository(GeneratorRepository generatorRepository) {
		//do nothing, override as necessary
	}

	protected PersistenceUnitContext persistenceUnitContext() {
		return (PersistenceUnitContext) persistentAttributeContext().javaTypeContext().getParentContext();
	}
	
	protected JavaPersistentAttributeContext persistentAttributeContext() {
		return (JavaPersistentAttributeContext) getParentContext();
	}

	protected boolean embeddableOwned() {
		return getMapping().typeMapping().getKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	protected boolean entityOwned() {
		return getMapping().typeMapping().getKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	@Override
	public final void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaults(defaultsContext, monitor);
		defaultsContext = wrapDefaultsContext(defaultsContext);
		refreshDefaultsInternal(defaultsContext, monitor);
	}
	
	protected void refreshDefaultsInternal(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		this.attributeMapping.refreshDefaults(defaultsContext);
	}
	
	protected IAttributeMapping getMapping() {
		return this.attributeMapping;
	}
	
	public final DefaultsContext wrapDefaultsContext(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			public Object getDefault(String key) {
				return JavaAttributeContext.this.getDefault(key, getWrappedDefaultsContext());
			}
		};
	}
	
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY)) {
			return getMapping().getPersistentAttribute().getName();			
		}
		return defaultsContext.getDefault(key);
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		addModifierMessages(messages);
		
		addInvalidMappingMessage(messages);
	}
	
	protected void addModifierMessages(List<IMessage> messages) {
		JavaPersistentAttribute attribute = 
				(JavaPersistentAttribute) attributeMapping.getPersistentAttribute();
		if (attribute.getMapping().getKey() != IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY
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
		IAttributeMapping attributeMapping = getMapping();
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
