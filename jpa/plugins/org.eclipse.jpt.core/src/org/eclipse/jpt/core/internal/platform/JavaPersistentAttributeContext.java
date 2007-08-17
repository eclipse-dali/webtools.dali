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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JavaPersistentAttributeContext extends BaseContext
{
	private JavaPersistentAttribute javaPersistentAttribute;
	
	private JavaAttributeContext javaAttributeMappingContext;
	
	private JavaAttributeContext defaultJavaAttributeMappingContext;
	
	public JavaPersistentAttributeContext(IContext parentContext, JavaPersistentAttribute javaPersistentAttribute) {
		super(parentContext);
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.javaAttributeMappingContext = buildJavaAttributeMappingContext();
	}
	
	protected JavaTypeContext javaTypeContext() {
		return (JavaTypeContext) getParentContext();
	}
	
	protected JavaAttributeContext buildJavaAttributeMappingContext() {
		IJavaAttributeMapping javaAttributeMapping = this.javaPersistentAttribute.getSpecifiedMapping();
		if (javaAttributeMapping != null) {
			return (JavaAttributeContext) getPlatform().buildJavaAttributeContext(this,javaAttributeMapping );
		}
		return null;
	}

	@Override
	protected void initialize() {}

	protected void populateGeneratorRepository(GeneratorRepository generatorRepository) {
		if (this.javaAttributeMappingContext != null) {
			this.javaAttributeMappingContext.populateGeneratorRepository(generatorRepository);
		}
	}

	protected boolean embeddableOwned() {
		return getPersistentAttribute().typeMapping().getKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	protected boolean entityOwned() {
		return getPersistentAttribute().typeMapping().getKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	public final void refreshDefaults(DefaultsContext defaultsContext) {
		defaultsContext = wrapDefaultsContext(defaultsContext);
		refreshDefaultsInternal(defaultsContext);
	}
	
	protected void refreshDefaultsInternal(DefaultsContext defaultsContext) {
		this.javaPersistentAttribute.refreshDefaults(defaultsContext);
		
		if (this.javaAttributeMappingContext != null) {
			this.javaAttributeMappingContext.refreshDefaults(defaultsContext);
			this.defaultJavaAttributeMappingContext = null;
		}
		else {
			IJavaAttributeMapping javaAttributeMapping = this.javaPersistentAttribute.getDefaultMapping();
			if (javaAttributeMapping != null) {
				this.defaultJavaAttributeMappingContext = (JavaAttributeContext) getPlatform().buildJavaAttributeContext(this, javaAttributeMapping);
				this.defaultJavaAttributeMappingContext.refreshDefaults(defaultsContext);
			}
		}
	}
	
	protected JavaPersistentAttribute getPersistentAttribute() {
		return this.javaPersistentAttribute;
	}
	
	public final DefaultsContext wrapDefaultsContext(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			public Object getDefault(String key) {
				return JavaPersistentAttributeContext.this.getDefault(key, getWrappedDefaultsContext());
			}
		};
	}
	
	protected Object getDefault(String key, DefaultsContext defaultsContext) {
		if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY)) {
			return getPersistentAttribute().getName();			
		}
		return defaultsContext.getDefault(key);
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		if (this.javaAttributeMappingContext != null) {
			this.javaAttributeMappingContext.addToMessages(messages);
		}
		else if (this.defaultJavaAttributeMappingContext != null) {
			this.defaultJavaAttributeMappingContext.addToMessages(messages);
		}
	}
}
