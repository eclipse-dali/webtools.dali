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
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaFileProvider;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.JavaJpaFileProvider;
import org.eclipse.jpt.core.internal.OrmJpaFileProvider;
import org.eclipse.jpt.core.internal.PersistenceJpaFileProvider;
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

/**
 * 
 */
public class GenericJpaPlatform
	implements JpaPlatform
{
	public static final String ID = "generic"; //$NON-NLS-1$

	private String id;

	private JpaFactory jpaFactory;

	private JpaAnnotationProvider annotationProvider;

	private JpaFileProvider[] jpaFileProviders;

	private JavaTypeMappingProvider[] javaTypeMappingProviders;

	private JavaAttributeMappingProvider[] javaAttributeMappingProviders;

	private DefaultJavaAttributeMappingProvider[] defaultJavaAttributeMappingProviders;


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
	 * For INTERNAL use only!!
	 */
	public void setId(String id) {
		this.id = id;
	}


	// ********** factory **********

	public synchronized JpaFactory getJpaFactory() {
		if (this.jpaFactory == null) {
			this.jpaFactory = this.buildJpaFactory();
		}
		return this.jpaFactory;
	}

	protected JpaFactory buildJpaFactory() {
		return new GenericJpaFactory();
	}


	// ********** JPA files **********

	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file) {
		String contentTypeId = this.getContentTypeId(file);
		return (contentTypeId == null) ? null : this.buildJpaFileForContentTypeId(jpaProject, file, contentTypeId);
	}

	protected JpaFile buildJpaFileForContentTypeId(JpaProject jpaProject, IFile file, String contentTypeID) {
		JpaFileProvider provider = this.getJpaFileProviderForContentTypeId(contentTypeID);
		return (provider == null) ? null : provider.buildJpaFile(jpaProject, file, this.getJpaFactory());
	}

	/**
	 * Return null if we don't have a provider for the specified content
	 * (since we don't have control over the possible content IDs).
	 * Also, use #equals(Object) for the same reason.
	 */
	protected JpaFileProvider getJpaFileProviderForContentTypeId(String contentTypeID) {
		for (JpaFileProvider provider : this.getJpaFileProviders()) {
			if (provider.getContentId().equals(contentTypeID)) {
				return provider;
			}
		}
		return null;
	}

	protected synchronized JpaFileProvider[] getJpaFileProviders() {
		if (this.jpaFileProviders == null) {
			this.jpaFileProviders = this.buildJpaFileProviders();
		}
		return this.jpaFileProviders;
	}

	protected JpaFileProvider[] buildJpaFileProviders() {
		ArrayList<JpaFileProvider> providers = new ArrayList<JpaFileProvider>();
		this.addJpaFileProvidersTo(providers);
		return providers.toArray(new JpaFileProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different JPA file providers.
	 * The default includes support for Java, persistence.xml, and orm.xml
	 * files
	 */
	protected void addJpaFileProvidersTo(List<JpaFileProvider> providers) {
		providers.add(JavaJpaFileProvider.instance());
		providers.add(PersistenceJpaFileProvider.instance());
		providers.add(OrmJpaFileProvider.instance());
	}

	protected String getContentTypeId(IFile file) {
		IContentType contentType = this.getContentType(file);
		return (contentType == null) ? null : contentType.getId();
	}

	protected IContentType getContentType(IFile file) {
		InputStream inputStream = null;
		try {
			inputStream = file.getContents();
		} catch (CoreException ex) {
			JptCorePlugin.log(ex);
			return null;  // cannot find the file
		}

		IContentType contentType = null;
		try {
			contentType = Platform.getContentTypeManager().findContentTypeFor(inputStream, file.getName());
		} catch (IOException ex) {
			JptCorePlugin.log(ex);
		} finally {
			try {
				inputStream.close();
			} catch (IOException ex) {
				JptCorePlugin.log(ex);
			}
		}
		return contentType;
	}


	// ********** Java annotations **********

	public synchronized JpaAnnotationProvider getAnnotationProvider() {
		if (this.annotationProvider == null) {
			this.annotationProvider = this.buildAnnotationProvider();
		}
		return this.annotationProvider;
	}

	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider();
	}


	// ********** Java type mappings **********

	public JavaTypeMapping buildJavaTypeMappingFromMappingKey(String key, JavaPersistentType type) {
		return this.getJavaTypeMappingProviderForMappingKey(key).buildMapping(type, this.getJpaFactory());
	}

	public JavaTypeMapping buildJavaTypeMappingFromAnnotation(String annotationName, JavaPersistentType type) {
		return this.getJavaTypeMappingProviderForAnnotation(annotationName).buildMapping(type, this.getJpaFactory());
	}

	protected synchronized JavaTypeMappingProvider[] getJavaTypeMappingProviders() {
		if (this.javaTypeMappingProviders == null) {
			this.javaTypeMappingProviders = this.buildJavaTypeMappingProviders();
		}
		return this.javaTypeMappingProviders;
	}

	protected JavaTypeMappingProvider[] buildJavaTypeMappingProviders() {
		ArrayList<JavaTypeMappingProvider> providers = new ArrayList<JavaTypeMappingProvider>();
		this.addJavaTypeMappingProvidersTo(providers);
		return providers.toArray(new JavaTypeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different type mapping providers.
	 * The default includes the JPA spec-defined type mappings of 
	 * Entity, MappedSuperclass, and Embeddable.
	 */
	protected void addJavaTypeMappingProvidersTo(List<JavaTypeMappingProvider> providers) {
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaNullTypeMappingProvider.instance());
	}

	protected JavaTypeMappingProvider getJavaTypeMappingProviderForMappingKey(String key) {
		for (JavaTypeMappingProvider provider : this.getJavaTypeMappingProviders()) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + key); //$NON-NLS-1$
	}

	protected JavaTypeMappingProvider getJavaTypeMappingProviderForAnnotation(String annotationName) {
		for (JavaTypeMappingProvider provider : this.getJavaTypeMappingProviders()) {
			if (provider.getAnnotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName); //$NON-NLS-1$
	}


	// ********** Java attribute mappings **********

	public JavaAttributeMapping buildJavaAttributeMappingFromMappingKey(String key, JavaPersistentAttribute attribute) {
		return this.getJavaAttributeMappingProviderForMappingKey(key).buildMapping(attribute, this.getJpaFactory());
	}

	public JavaAttributeMapping buildJavaAttributeMappingFromAnnotation(String annotationName, JavaPersistentAttribute attribute) {
		return this.getJavaAttributeMappingProviderForAnnotation(annotationName).buildMapping(attribute, this.getJpaFactory());
	}

	public JavaAttributeMapping buildDefaultJavaAttributeMapping(JavaPersistentAttribute attribute) {
		return this.getDefaultJavaAttributeMappingProvider(attribute).buildMapping(attribute, this.getJpaFactory());
	}

	protected synchronized JavaAttributeMappingProvider[] getJavaAttributeMappingProviders() {
		if (this.javaAttributeMappingProviders == null) {
			this.javaAttributeMappingProviders = this.buildJavaAttributeMappingProviders();
		}
		return this.javaAttributeMappingProviders;
	}

	protected JavaAttributeMappingProvider[] buildJavaAttributeMappingProviders() {
		ArrayList<JavaAttributeMappingProvider> providers = new ArrayList<JavaAttributeMappingProvider>();
		this.addJavaAttributeMappingProvidersTo(providers);
		return providers.toArray(new JavaAttributeMappingProvider[providers.size()]);
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

	protected JavaAttributeMappingProvider getJavaAttributeMappingProviderForMappingKey(String key) {
		for (JavaAttributeMappingProvider provider : this.getJavaAttributeMappingProviders()) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + key); //$NON-NLS-1$
	}

	protected JavaAttributeMappingProvider getJavaAttributeMappingProviderForAnnotation(String annotationName) {
		for (JavaAttributeMappingProvider provider : this.getJavaAttributeMappingProviders()) {
			if (provider.getAnnotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName); //$NON-NLS-1$
	}

	protected synchronized DefaultJavaAttributeMappingProvider[] getDefaultJavaAttributeMappingProviders() {
		if (this.defaultJavaAttributeMappingProviders == null) {
			this.defaultJavaAttributeMappingProviders = this.buildDefaultJavaAttributeMappingProviders();
		}
		return this.defaultJavaAttributeMappingProviders;
	}

	protected DefaultJavaAttributeMappingProvider[] buildDefaultJavaAttributeMappingProviders() {
		ArrayList<DefaultJavaAttributeMappingProvider> providers = new ArrayList<DefaultJavaAttributeMappingProvider>();
		this.addDefaultJavaAttributeMappingProvidersTo(providers);
		return providers.toArray(new DefaultJavaAttributeMappingProvider[providers.size()]);
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

	protected JavaAttributeMappingProvider getDefaultJavaAttributeMappingProvider(JavaPersistentAttribute attribute) {
		for (DefaultJavaAttributeMappingProvider provider : this.getDefaultJavaAttributeMappingProviders()) {
			if (provider.defaultApplies(attribute)) {
				return provider;
			}
		}
		return this.getNullAttributeMappingProvider();
	}

	public String getDefaultJavaAttributeMappingKey(JavaPersistentAttribute attribute) {
		return this.getDefaultJavaAttributeMappingProvider(attribute).getKey();
	}

	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	protected JavaAttributeMappingProvider getNullAttributeMappingProvider() {
		return JavaNullAttributeMappingProvider.instance();
	}


	// ********** database **********

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
