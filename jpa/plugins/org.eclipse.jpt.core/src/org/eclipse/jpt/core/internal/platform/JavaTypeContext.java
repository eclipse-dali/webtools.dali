/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class JavaTypeContext extends BaseContext
	implements TypeContext
{
	private IJavaTypeMapping typeMapping;
	
	private Collection<JavaPersistentAttributeContext> javaPersistentAttributeContexts;
	
	private boolean refreshed;
	
	public JavaTypeContext(IContext parentContext, IJavaTypeMapping typeMapping) {
		super(parentContext);
		this.typeMapping = typeMapping;
		this.javaPersistentAttributeContexts = buildJavaPersistentAttributeContexts();
	}

	protected Collection<JavaPersistentAttributeContext> buildJavaPersistentAttributeContexts() {
		Collection<JavaPersistentAttributeContext> persistentAttributes = new ArrayList<JavaPersistentAttributeContext>();
		for (Iterator<JavaPersistentAttribute> i = getPersistentType().getAttributes().iterator(); i.hasNext(); ) {
			persistentAttributes.add(new JavaPersistentAttributeContext(this, i.next()));
		}
		
		return persistentAttributes;
	}
	
	
	@Override
	protected void initialize() {}
	
	protected void populateGeneratorRepository(GeneratorRepository generatorRepository) {
		for (JavaPersistentAttributeContext context : getJavaPersistentAttributeContexts()) {
			context.populateGeneratorRepository(generatorRepository);
		}
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		this.refreshed = true;
		this.getPersistentType().refreshDefaults(defaultsContext);
		for (JavaPersistentAttributeContext context : this.javaPersistentAttributeContexts) {
			context.refreshDefaults(defaultsContext);
		}
	}
	
	public JavaPersistentType getPersistentType() {
		return (JavaPersistentType) typeMapping.getPersistentType();
	}
	
	protected IJavaTypeMapping getTypeMapping() {
		return typeMapping;
	}
	
	protected Collection<JavaPersistentAttributeContext> getJavaPersistentAttributeContexts() {
		return this.javaPersistentAttributeContexts;
	}

	public boolean isRefreshed() {
		return this.refreshed;
	}
	
	public boolean contains(IPersistentType persistentType) {
		return persistentType == getPersistentType();
	}
	
	public void addToMessages(List<IMessage> messages) {
		addAttributeMessages(messages);
	}
	
	protected void addAttributeMessages(List<IMessage> messages) {
		for (JavaPersistentAttributeContext attributeContext : javaPersistentAttributeContexts) {
			attributeContext.addToMessages(messages);
		}
	}
}
