/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEntityProvider;
import org.eclipse.jpt.core.internal.context.java.JavaIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullAttributeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaOneToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaOneToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericJpaPlatform implements JpaPlatform
{
	public static String ID = "generic";
	
	private String id;
	
	protected JpaFactory jpaFactory;
	
	protected JpaAnnotationProvider annotationProvider;
	
	protected Collection<JavaTypeMappingProvider> javaTypeMappingProviders;
	
	protected Collection<JavaAttributeMappingProvider> javaAttributeMappingProviders;
	
	protected List<DefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders;
	
	public GenericJpaPlatform() {
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
	 * @see JpaPlatform#setId(String)
	 */
	public void setId(String theId) {
		this.id = theId;
	}
	
	
	// **************** Model construction / updating **************************
	
	public JpaFactory getJpaFactory() {
		if (this.jpaFactory == null) {
			this.jpaFactory = buildJpaFactory();
		}
		return this.jpaFactory;
	}
	
	protected JpaFactory buildJpaFactory() {
		return new GenericJpaFactory();
	}
	
	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file) {
		if (getJpaFactory().hasRelevantContent(file)) {
			ResourceModel resourceModel = getJpaFactory().buildResourceModel(jpaProject, file);
			return getJpaFactory().buildJpaFile(jpaProject, file, resourceModel);
		}
		
		return null;
	}
	
	
	// **************** java annotation support ********************************
	
	public JpaAnnotationProvider getAnnotationProvider() {
		if (this.annotationProvider == null) {
			this.annotationProvider = buildAnnotationProvider();
		}
		return this.annotationProvider;
	}
	
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider();
	}
	
	
	// **************** type mapping support ********************************
	
	public JavaTypeMapping buildJavaTypeMappingFromMappingKey(String typeMappingKey, JavaPersistentType parent) {
		return javaTypeMappingProviderFromMappingKey(typeMappingKey).buildMapping(parent, getJpaFactory());
	}
	
	public JavaTypeMapping buildJavaTypeMappingFromAnnotation(String mappingAnnotationName, JavaPersistentType parent) {
		return javaTypeMappingProviderFromAnnotation(mappingAnnotationName).buildMapping(parent, getJpaFactory());
	}
	
	public JavaAttributeMapping buildJavaAttributeMappingFromMappingKey(String attributeMappingKey, JavaPersistentAttribute parent) {
		return javaAttributeMappingProviderFromMappingKey(attributeMappingKey).buildMapping(parent, getJpaFactory());
	}
	
	public JavaAttributeMapping buildJavaAttributeMappingFromAnnotation(String mappingAnnotationName, JavaPersistentAttribute parent) {
		return javaAttributeMappingProviderFromAnnotation(mappingAnnotationName).buildMapping(parent, getJpaFactory());
	}

	public JavaAttributeMapping buildDefaultJavaAttributeMapping(JavaPersistentAttribute parent) {
		return defaultJavaAttributeMappingProvider(parent).buildMapping(parent, getJpaFactory());
	}
	
	protected Iterator<JavaTypeMappingProvider> javaTypeMappingProviders() {
		if (this.javaTypeMappingProviders == null) {
			this.javaTypeMappingProviders = new ArrayList<JavaTypeMappingProvider>();
			this.addJavaTypeMappingProvidersTo(this.javaTypeMappingProviders);
		}
		return new CloneIterator<JavaTypeMappingProvider>(this.javaTypeMappingProviders);
	}

	/**
	 * Override this to specify more or different type mapping providers.
	 * The default includes the JPA spec-defined type mappings of 
	 * Entity, MappedSuperclass, and Embeddable
	 */
	protected void addJavaTypeMappingProvidersTo(Collection<JavaTypeMappingProvider> providers) {
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaNullTypeMappingProvider.instance());
	}

	protected JavaTypeMappingProvider javaTypeMappingProviderFromMappingKey(String typeMappingKey) {
		for (Iterator<JavaTypeMappingProvider> i = this.javaTypeMappingProviders(); i.hasNext(); ) {
			JavaTypeMappingProvider provider = i.next();
			if (provider.key() == typeMappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + typeMappingKey);
	}
	
	protected JavaTypeMappingProvider javaTypeMappingProviderFromAnnotation(String annotationName) {
		for (Iterator<JavaTypeMappingProvider> i = this.javaTypeMappingProviders(); i.hasNext(); ) {
			JavaTypeMappingProvider provider = i.next();
			if (provider.annotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName);
	}
	
	protected Iterator<JavaAttributeMappingProvider> javaAttributeMappingProviders() {
		if (this.javaAttributeMappingProviders == null) {
			this.javaAttributeMappingProviders = new ArrayList<JavaAttributeMappingProvider>();
			this.addJavaAttributeMappingProvidersTo(this.javaAttributeMappingProviders);
		}
		return new CloneIterator<JavaAttributeMappingProvider>(this.javaAttributeMappingProviders);
	}

	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
	 */
	protected void addJavaAttributeMappingProvidersTo(Collection<JavaAttributeMappingProvider> providers) {
		providers.add(JavaBasicMappingProvider.instance());
		providers.add(JavaEmbeddedMappingProvider.instance());
		providers.add(JavaEmbeddedIdMappingProvider.instance());
		providers.add(JavaIdMappingProvider.instance());
		providers.add(JavaManyToManyMappingProvider.instance());
		providers.add(JavaManyToOneMappingProvider.instance());
		providers.add(JavaOneToManyMappingProvider.instance());
		providers.add(JavaOneToOneMappingProvider.instance());
		providers.add(JavaTransientMappingProvider.instance());
		providers.add(JavaVersionMappingProvider.instance());
	}

	protected JavaAttributeMappingProvider javaAttributeMappingProviderFromMappingKey(String attributeMappingKey) {
		for (Iterator<JavaAttributeMappingProvider> i = this.javaAttributeMappingProviders(); i.hasNext(); ) {
			JavaAttributeMappingProvider provider = i.next();
			if (provider.key() == attributeMappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + attributeMappingKey);
	}
	
	protected JavaAttributeMappingProvider javaAttributeMappingProviderFromAnnotation(String annotationName) {
		for (Iterator<JavaAttributeMappingProvider> i = this.javaAttributeMappingProviders(); i.hasNext(); ) {
			JavaAttributeMappingProvider provider = i.next();
			if (provider.annotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName);
	}
	
	protected ListIterator<DefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders() {
		if (this.defaultJavaAttributeMappingProviders == null) {
			this.defaultJavaAttributeMappingProviders = new ArrayList<DefaultJavaAttributeMappingProvider>();
			this.addDefaultJavaAttributeMappingProvidersTo(this.defaultJavaAttributeMappingProviders);
		}
		return new CloneListIterator<DefaultJavaAttributeMappingProvider>(this.defaultJavaAttributeMappingProviders);
	}
	
	/**
	 * Override this to specify more or different default attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Embedded and Basic.
	 */
	protected void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers) {
		providers.add(JavaEmbeddedMappingProvider.instance()); //bug 190344 need to test default embedded before basic
		providers.add(JavaBasicMappingProvider.instance());
	}

	protected JavaAttributeMappingProvider defaultJavaAttributeMappingProvider(JavaPersistentAttribute persistentAttribute) {
		for (Iterator<DefaultJavaAttributeMappingProvider> i = this.defaultJavaAttributeMappingProviders(); i.hasNext(); ) {
			DefaultJavaAttributeMappingProvider provider = i.next();
			if (provider.defaultApplies(persistentAttribute)) {
				return provider;
			}
		}
		
		return nullAttributeMappingProvider();
	}

	public String defaultJavaAttributeMappingKey(JavaPersistentAttribute persistentAttribute) {
		return defaultJavaAttributeMappingProvider(persistentAttribute).key();
	}
	
	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	protected JavaAttributeMappingProvider nullAttributeMappingProvider() {
		return JavaNullAttributeMappingProvider.instance();
	}

	// **************** Validation *********************************************
	
	public void addToMessages(JpaProject project, List<IMessage> messages) {
		project.addToMessages(messages);
	}
}
