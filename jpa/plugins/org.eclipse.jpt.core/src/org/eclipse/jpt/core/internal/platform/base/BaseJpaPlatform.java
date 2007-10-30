/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.internal.IJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEntityProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMappingProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseJpaPlatform implements IJpaPlatform
{
	private String id;
	
	protected IJpaFactory jpaFactory;
	
	protected IJpaAnnotationProvider annotationProvider;
	
	private Collection<IJavaTypeMappingProvider> javaTypeMappingProviders;
	
	protected BaseJpaPlatform() {
		super();
	}
	
	
	public String getId() {
		return this.id;
	}
	
	/**
	 * *************
	 * * IMPORTANT *   For INTERNAL use only !!
	 * *************
	 * 
	 * @see IJpaPlatform#setId(String)
	 */
	public void setId(String theId) {
		this.id = theId;
	}
	
	
	// **************** Model construction / updating **************************
	
	public IJpaFactory jpaFactory() {
		if (jpaFactory == null) {
			jpaFactory = buildJpaFactory();
		}
		return jpaFactory;
	}
	
	protected abstract IJpaFactory buildJpaFactory();
	
	public IJpaFile buildJpaFile(IJpaProject jpaProject, IFile file) {
		if (jpaFactory().hasRelevantContent(file)) {
			IResourceModel resourceModel = jpaFactory().buildResourceModel(jpaProject, file);
			return jpaFactory().createJpaFile(jpaProject, file, resourceModel);
		}
		
		return null;
	}
	
	
	// **************** java annotation support ********************************
	
	public IJpaAnnotationProvider annotationProvider() {
		if (annotationProvider == null) {
			annotationProvider = buildAnnotationProvider();
		}
		return annotationProvider;
	}
	
	protected abstract IJpaAnnotationProvider buildAnnotationProvider();
	
	
	// **************** type mapping support ********************************
	
	public IJavaTypeMapping createJavaTypeMappingFromMappingKey(String typeMappingKey, IJavaPersistentType parent) {
		//TODO I don't like that i am casting to IJpaBaseContextFactory here, not sure what to do about it
		return javaTypeMappingProviderFromMappingKey(typeMappingKey).buildMapping(parent, (IJpaBaseContextFactory) jpaFactory());
	}
	
	public IJavaTypeMapping createJavaTypeMappingFromAnnotation(String mappingAnnotationName, IJavaPersistentType parent) {
		//TODO I don't like that i am casting to IJpaBaseContextFactory here, not sure what to do about it
		return javaTypeMappingProviderFromAnnotation(mappingAnnotationName).buildMapping(parent, (IJpaBaseContextFactory) jpaFactory());
	}
	
	protected Iterator<IJavaTypeMappingProvider> javaTypeMappingProviders() {
		if (this.javaTypeMappingProviders == null) {
			this.javaTypeMappingProviders = new ArrayList<IJavaTypeMappingProvider>();
			this.addJavaTypeMappingProvidersTo(this.javaTypeMappingProviders);
		}
		return new CloneIterator<IJavaTypeMappingProvider>(this.javaTypeMappingProviders);
	}

	/**
	 * Override this to specify more or different type mapping providers.
	 * The default includes the JPA spec-defined type mappings of 
	 * Entity, MappedSuperclass, and Embeddable
	 */
	protected void addJavaTypeMappingProvidersTo(Collection<IJavaTypeMappingProvider> providers) {
		providers.add(JavaEntityProvider.instance());
		//providers.add(JavaMappedSuperclassProvider.instance());
		//providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaNullTypeMappingProvider.instance());
	}

	protected IJavaTypeMappingProvider javaTypeMappingProviderFromMappingKey(String typeMappingKey) {
		for (Iterator<IJavaTypeMappingProvider> i = this.javaTypeMappingProviders(); i.hasNext(); ) {
			IJavaTypeMappingProvider provider = i.next();
			if (provider.key() == typeMappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + typeMappingKey);
	}
	
	protected IJavaTypeMappingProvider javaTypeMappingProviderFromAnnotation(String annotationName) {
		for (Iterator<IJavaTypeMappingProvider> i = this.javaTypeMappingProviders(); i.hasNext(); ) {
			IJavaTypeMappingProvider provider = i.next();
			if (provider.annotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName);
	}
	
	// **************** Validation *********************************************
	
	public void addToMessages(List<IMessage> messages) {
		// TODO Auto-generated method stub
		
	}
}
