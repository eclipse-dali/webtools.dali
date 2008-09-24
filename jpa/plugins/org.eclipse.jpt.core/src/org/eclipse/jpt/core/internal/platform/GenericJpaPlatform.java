/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
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
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class GenericJpaPlatform
	implements JpaPlatform
{
	public static final String ID = "generic"; //$NON-NLS-1$

	private String id;

	protected JpaFactory jpaFactory;

	protected JpaAnnotationProvider annotationProvider;

	protected List<JavaTypeMappingProvider> javaTypeMappingProviders;

	protected List<JavaAttributeMappingProvider> javaAttributeMappingProviders;

	protected List<DefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders;


	/**
	 * zero-argument constructor
	 */
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


	// **************** Model construction/updating **************************

	public synchronized JpaFactory getJpaFactory() {
		if (this.jpaFactory == null) {
			this.jpaFactory = this.buildJpaFactory();
		}
		return this.jpaFactory;
	}

	protected JpaFactory buildJpaFactory() {
		return new GenericJpaFactory();
	}

	//TODO Something still needs to change here with this API.  buildJpaFile is having to do too much
	//and it is odd to return null from it.  We should probably have API that returns the 
	//contentTypeId if it is relevant to the platform, then we would only ever call buildJpaFile
	//if that was non-null.  Or the buildJpaFile api should just be removed completely
	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file) {
		if (! JavaCore.create(file.getProject()).isOnClasspath(file)) {
			return null;
		}
		IContentType contentType = this.contentType(file);
		if (contentType == null) {
			return null;
		}
		String contentTypeId = contentType.getId();
		if (supportsContentType(contentTypeId)) {
			ResourceModel resourceModel = getJpaFactory().buildResourceModel(jpaProject, file, contentTypeId);
			return getJpaFactory().buildJpaFile(jpaProject, file, resourceModel);
		}
		
		return null;
	}
	
	protected boolean supportsContentType(String contentTypeId) {
		return contentTypeId.equals(JavaCore.JAVA_SOURCE_CONTENT_TYPE)
				|| contentTypeId.equals(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE)
				|| contentTypeId.equals(JptCorePlugin.ORM_XML_CONTENT_TYPE);
	}
	
	// attempting to get the contentType based on the file contents.
	// have to check the file contents instead of just the file name
	// because for xml we base it on the rootElement name
	private IContentType contentType(IFile file) {
		InputStream inputStream = null;
		try {
			inputStream = file.getContents();
			return Platform.getContentTypeManager().findContentTypeFor(inputStream, file.getName());
		}
		catch (IOException ex) {
			JptCorePlugin.log(ex);
		}
		catch (CoreException ex) {
			JptCorePlugin.log(ex);
		}
		finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
				JptCorePlugin.log(ex);
			}
		}
		return null;
	}

	// **************** Java annotation support ********************************

	public synchronized JpaAnnotationProvider getAnnotationProvider() {
		if (this.annotationProvider == null) {
			this.annotationProvider = this.buildAnnotationProvider();
		}
		return this.annotationProvider;
	}

	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider();
	}


	// **************** Type mapping providers ********************************

	public JavaTypeMapping buildJavaTypeMappingFromMappingKey(String typeMappingKey, JavaPersistentType parent) {
		return this.javaTypeMappingProviderFromMappingKey(typeMappingKey).buildMapping(parent, this.getJpaFactory());
	}

	public JavaTypeMapping buildJavaTypeMappingFromAnnotation(String mappingAnnotationName, JavaPersistentType parent) {
		return this.javaTypeMappingProviderFromAnnotation(mappingAnnotationName).buildMapping(parent, this.getJpaFactory());
	}

	protected synchronized Iterator<JavaTypeMappingProvider> javaTypeMappingProviders() {
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
	protected void addJavaTypeMappingProvidersTo(List<JavaTypeMappingProvider> providers) {
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaNullTypeMappingProvider.instance());
	}

	protected JavaTypeMappingProvider javaTypeMappingProviderFromMappingKey(String typeMappingKey) {
		for (Iterator<JavaTypeMappingProvider> stream = this.javaTypeMappingProviders(); stream.hasNext(); ) {
			JavaTypeMappingProvider provider = stream.next();
			if (provider.getKey() == typeMappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + typeMappingKey); //$NON-NLS-1$
	}

	protected JavaTypeMappingProvider javaTypeMappingProviderFromAnnotation(String annotationName) {
		for (Iterator<JavaTypeMappingProvider> stream = this.javaTypeMappingProviders(); stream.hasNext(); ) {
			JavaTypeMappingProvider provider = stream.next();
			if (provider.getAnnotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName); //$NON-NLS-1$
	}


	// **************** Attribute mapping providers ********************************

	public JavaAttributeMapping buildJavaAttributeMappingFromMappingKey(String attributeMappingKey, JavaPersistentAttribute parent) {
		return this.javaAttributeMappingProviderFromMappingKey(attributeMappingKey).buildMapping(parent, this.getJpaFactory());
	}

	public JavaAttributeMapping buildJavaAttributeMappingFromAnnotation(String mappingAnnotationName, JavaPersistentAttribute parent) {
		return this.javaAttributeMappingProviderFromAnnotation(mappingAnnotationName).buildMapping(parent, this.getJpaFactory());
	}

	public JavaAttributeMapping buildDefaultJavaAttributeMapping(JavaPersistentAttribute parent) {
		return this.defaultJavaAttributeMappingProvider(parent).buildMapping(parent, this.getJpaFactory());
	}

	protected synchronized Iterator<JavaAttributeMappingProvider> javaAttributeMappingProviders() {
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
	protected void addJavaAttributeMappingProvidersTo(List<JavaAttributeMappingProvider> providers) {
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
		for (Iterator<JavaAttributeMappingProvider> stream = this.javaAttributeMappingProviders(); stream.hasNext(); ) {
			JavaAttributeMappingProvider provider = stream.next();
			if (provider.getKey() == attributeMappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + attributeMappingKey); //$NON-NLS-1$
	}

	protected JavaAttributeMappingProvider javaAttributeMappingProviderFromAnnotation(String annotationName) {
		for (Iterator<JavaAttributeMappingProvider> stream = this.javaAttributeMappingProviders(); stream.hasNext(); ) {
			JavaAttributeMappingProvider provider = stream.next();
			if (provider.getAnnotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName); //$NON-NLS-1$
	}

	protected synchronized ListIterator<DefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders() {
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
		providers.add(JavaEmbeddedMappingProvider.instance());  // bug 190344 need to test default embedded before basic
		providers.add(JavaBasicMappingProvider.instance());
	}

	protected JavaAttributeMappingProvider defaultJavaAttributeMappingProvider(JavaPersistentAttribute persistentAttribute) {
		for (Iterator<DefaultJavaAttributeMappingProvider> stream = this.defaultJavaAttributeMappingProviders(); stream.hasNext(); ) {
			DefaultJavaAttributeMappingProvider provider = stream.next();
			if (provider.defaultApplies(persistentAttribute)) {
				return provider;
			}
		}
		return this.getNullAttributeMappingProvider();
	}

	public String getDefaultJavaAttributeMappingKey(JavaPersistentAttribute persistentAttribute) {
		return this.defaultJavaAttributeMappingProvider(persistentAttribute).getKey();
	}

	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	protected JavaAttributeMappingProvider getNullAttributeMappingProvider() {
		return JavaNullAttributeMappingProvider.instance();
	}


	// **************** Database *********************************************

	public ConnectionProfileFactory getConnectionProfileFactory() {
		return JptDbPlugin.instance().getConnectionProfileFactory();
	}

	public EntityGeneratorDatabaseAnnotationNameBuilder getEntityGeneratorDatabaseAnnotationNameBuilder() {
		return GenericEntityGeneratorDatabaseAnnotationNameBuilder.instance();
	}

	public DatabaseFinder getDatabaseFinder() {
		return DatabaseFinder.Default.instance();
	}

}
