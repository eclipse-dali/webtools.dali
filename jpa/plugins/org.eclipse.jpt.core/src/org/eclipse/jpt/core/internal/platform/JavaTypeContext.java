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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class JavaTypeContext extends BaseContext
	implements TypeContext
{
	private IJavaTypeMapping typeMapping;
	
	private Collection<JavaPersistentAttributeContext> javaPersistentAttributeContexts;
	
	private boolean refreshed;
	
	private CompilationUnit astRoot;
	
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

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaults(defaultsContext, monitor);
		this.refreshed = true;
		DefaultsContext wrappedDefaultsContext = wrapDefaultsContext(defaultsContext);
		this.getPersistentType().refreshDefaults(wrappedDefaultsContext);
		for (JavaPersistentAttributeContext context : this.javaPersistentAttributeContexts) {
			checkCanceled(monitor);
			context.refreshDefaults(wrappedDefaultsContext, monitor);
		}
	}
	
	private void checkCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}		
	}
	
	private DefaultsContext wrapDefaultsContext(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			@Override
			public CompilationUnit astRoot() {
				return JavaTypeContext.this.getAstRoot();
			}
		};
	}
	
	protected CompilationUnit getAstRoot() {
		if (this.astRoot == null) {
			this.astRoot = getPersistentType().getType().astRoot();
		}
		return this.astRoot;
		
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
