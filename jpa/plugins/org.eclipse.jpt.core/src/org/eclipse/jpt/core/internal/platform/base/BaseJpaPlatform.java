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
import java.util.ListIterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.internal.IJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.context.java.IDefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEntityProvider;
import org.eclipse.jpt.core.internal.context.java.JavaIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullAttributeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseJpaPlatform implements IJpaPlatform
{
	private String id;
	
	protected IJpaFactory jpaFactory;
	
	protected IJpaAnnotationProvider annotationProvider;
	
	protected Collection<IJavaTypeMappingProvider> javaTypeMappingProviders;
	
	protected Collection<IJavaAttributeMappingProvider> javaAttributeMappingProviders;
	
	protected List<IDefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders;
	
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
		if (this.jpaFactory == null) {
			this.jpaFactory = buildJpaFactory();
		}
		return this.jpaFactory;
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
		if (this.annotationProvider == null) {
			this.annotationProvider = buildAnnotationProvider();
		}
		return this.annotationProvider;
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
	
	public IJavaAttributeMapping createJavaAttributeMappingFromMappingKey(String attributeMappingKey, IJavaPersistentAttribute parent) {
		return javaAttributeMappingProviderFromMappingKey(attributeMappingKey).buildMapping(parent, (IJpaBaseContextFactory) jpaFactory());
	}
	
	public IJavaAttributeMapping createJavaAttributeMappingFromAnnotation(String mappingAnnotationName, IJavaPersistentAttribute parent) {
		return javaAttributeMappingProviderFromAnnotation(mappingAnnotationName).buildMapping(parent, (IJpaBaseContextFactory) jpaFactory());
	}

	public IJavaAttributeMapping createDefaultJavaAttributeMapping(IJavaPersistentAttribute parent) {
		return defaultJavaAttributeMappingProvider(parent).buildMapping(parent, (IJpaBaseContextFactory) jpaFactory());
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
		providers.add(JavaEmbeddableProvider.instance());
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
	
	protected Iterator<IJavaAttributeMappingProvider> javaAttributeMappingProviders() {
		if (this.javaAttributeMappingProviders == null) {
			this.javaAttributeMappingProviders = new ArrayList<IJavaAttributeMappingProvider>();
			this.addJavaAttributeMappingProvidersTo(this.javaAttributeMappingProviders);
		}
		return new CloneIterator<IJavaAttributeMappingProvider>(this.javaAttributeMappingProviders);
	}

	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
	 */
	protected void addJavaAttributeMappingProvidersTo(Collection<IJavaAttributeMappingProvider> providers) {
		providers.add(JavaBasicMappingProvider.instance());
		providers.add(JavaEmbeddedMappingProvider.instance());
		providers.add(JavaIdMappingProvider.instance());
		providers.add(JavaTransientMappingProvider.instance());
		providers.add(JavaVersionMappingProvider.instance());
	}

	protected IJavaAttributeMappingProvider javaAttributeMappingProviderFromMappingKey(String attributeMappingKey) {
		for (Iterator<IJavaAttributeMappingProvider> i = this.javaAttributeMappingProviders(); i.hasNext(); ) {
			IJavaAttributeMappingProvider provider = i.next();
			if (provider.key() == attributeMappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + attributeMappingKey);
	}
	
	protected IJavaAttributeMappingProvider javaAttributeMappingProviderFromAnnotation(String annotationName) {
		for (Iterator<IJavaAttributeMappingProvider> i = this.javaAttributeMappingProviders(); i.hasNext(); ) {
			IJavaAttributeMappingProvider provider = i.next();
			if (provider.annotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName);
	}
	
	protected ListIterator<IDefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders() {
		if (this.defaultJavaAttributeMappingProviders == null) {
			this.defaultJavaAttributeMappingProviders = new ArrayList<IDefaultJavaAttributeMappingProvider>();
			this.addDefaultJavaAttributeMappingProvidersTo(this.defaultJavaAttributeMappingProviders);
		}
		return new CloneListIterator<IDefaultJavaAttributeMappingProvider>(this.defaultJavaAttributeMappingProviders);
	}
	
	/**
	 * Override this to specify more or different default attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Embedded and Basic.
	 */
	protected void addDefaultJavaAttributeMappingProvidersTo(List<IDefaultJavaAttributeMappingProvider> providers) {
		providers.add(JavaEmbeddedMappingProvider.instance()); //bug 190344 need to test default embedded before basic
		providers.add(JavaBasicMappingProvider.instance());
	}

	protected IJavaAttributeMappingProvider defaultJavaAttributeMappingProvider(IJavaPersistentAttribute persistentAttribute) {
		for (Iterator<IDefaultJavaAttributeMappingProvider> i = this.defaultJavaAttributeMappingProviders(); i.hasNext(); ) {
			IDefaultJavaAttributeMappingProvider provider = i.next();
			if (provider.defaultApplies(persistentAttribute)) {
				return provider;
			}
		}
		
		return nullAttributeMappingProvider();
	}

	public String defaultJavaAttributeMappingKey(IJavaPersistentAttribute persistentAttribute) {
		return defaultJavaAttributeMappingProvider(persistentAttribute).key();
	}
	
	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	protected IJavaAttributeMappingProvider nullAttributeMappingProvider() {
		return JavaNullAttributeMappingProvider.instance();
	}

	// **************** Validation *********************************************
	
	public void addToMessages(List<IMessage> messages) {
		// TODO Auto-generated method stub
		
	}
}
