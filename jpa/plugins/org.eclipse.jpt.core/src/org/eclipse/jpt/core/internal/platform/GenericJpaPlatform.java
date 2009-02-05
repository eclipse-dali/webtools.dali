/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ListIterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.ExtendedOrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.ExtendedOrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.context.java.JavaNullAttributeMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmNullAttributeMappingProvider;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatform
	implements JpaPlatform
{

	private final String id;

	private final JpaFactory jpaFactory;

	private final JpaAnnotationProvider annotationProvider;

	private final JpaPlatformProvider[] platformProviders;

	public GenericJpaPlatform(String id, JpaFactory jpaFactory, JpaAnnotationProvider jpaAnnotationProvider, JpaPlatformProvider... platformProviders) {
		super();
		this.id = id;
		this.jpaFactory = jpaFactory;
		this.annotationProvider = jpaAnnotationProvider;
		this.platformProviders = platformProviders;
	}

	public String getId() {
		return this.id;
	}


	// ********** factory **********

	public synchronized JpaFactory getJpaFactory() {
		return this.jpaFactory;
	}
	
	
	// ********** platform providers **********
	
	protected synchronized ListIterator<JpaPlatformProvider> platformProviders() {
		return new ArrayListIterator<JpaPlatformProvider>(this.platformProviders);
	}

	// ********** JPA file/resource models **********

	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file) {
		IContentType contentType = PlatformTools.getContentType(file);
		return (contentType == null) ? null : this.buildJpaFile(jpaProject, file, contentType);
	}

	protected JpaFile buildJpaFile(JpaProject jpaProject, IFile file, IContentType contentType) {
		JpaResourceModel resourceModel = this.buildResourceModel(jpaProject, file, contentType);
		return (resourceModel == null) ? null : this.jpaFactory.buildJpaFile(jpaProject, file, contentType, resourceModel);
	}

	protected JpaResourceModel buildResourceModel(JpaProject jpaProject, IFile file, IContentType contentType) {
		JpaResourceModelProvider provider = this.getResourceModelProvider(contentType);
		return (provider == null) ? null : provider.buildResourceModel(jpaProject, file);
	}

	/**
	 * Return null if we don't have a provider for the specified content type
	 * (since we don't have control over the possible content types).
	 * NB: We use "is kind of" to match content types; so the search may be
	 * order-sensitive.
	 * @see IContentType#isKindOf(IContentType)
	 */
	protected JpaResourceModelProvider getResourceModelProvider(IContentType contentType) {
		for (JpaResourceModelProvider provider : CollectionTools.iterable(resourceModelProviders())) {
			if (contentType.isKindOf(provider.getContentType())) {
				return provider;
			}
		}
		return null;
	}

	protected ListIterator<JpaResourceModelProvider> resourceModelProviders() {
		return new CompositeListIterator<JpaResourceModelProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<JpaResourceModelProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<JpaResourceModelProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.resourceModelProviders();
				}
			}
		);
	}

	// ********** Java annotations **********

	public synchronized JpaAnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}

	public AnnotationEditFormatter getAnnotationEditFormatter() {
		return DefaultAnnotationEditFormatter.instance();
	}


	// ********** Java type mappings **********

	public JavaTypeMapping buildJavaTypeMappingFromMappingKey(String key, JavaPersistentType type) {
		return this.getJavaTypeMappingProviderForMappingKey(key).buildMapping(type, this.jpaFactory);
	}
	
	protected JavaTypeMappingProvider getJavaTypeMappingProviderForMappingKey(String key) {
		for (JavaTypeMappingProvider provider : CollectionTools.iterable(javaTypeMappingProviders())) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + key); //$NON-NLS-1$
	}

	protected ListIterator<JavaTypeMappingProvider> javaTypeMappingProviders() {
		return new CompositeListIterator<JavaTypeMappingProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<JavaTypeMappingProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<JavaTypeMappingProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.javaTypeMappingProviders();
				}
			}
		);
	}

	public JavaTypeMapping buildJavaTypeMappingFromAnnotation(String annotationName, JavaPersistentType type) {
		return this.getJavaTypeMappingProviderForAnnotation(annotationName).buildMapping(type, this.jpaFactory);
	}

	protected JavaTypeMappingProvider getJavaTypeMappingProviderForAnnotation(String annotationName) {
		for (JavaTypeMappingProvider provider : CollectionTools.iterable(javaTypeMappingProviders())) {
			if (provider.getAnnotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName); //$NON-NLS-1$
	}

	
	// ********** Java attribute mappings **********

	public JavaAttributeMapping buildJavaAttributeMappingFromMappingKey(String key, JavaPersistentAttribute attribute) {
		return this.getJavaAttributeMappingProviderForMappingKey(key).buildMapping(attribute, this.jpaFactory);
	}

	protected JavaAttributeMappingProvider getJavaAttributeMappingProviderForMappingKey(String key) {
		for (JavaAttributeMappingProvider provider : CollectionTools.iterable(javaAttributeMappingProviders())) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + key); //$NON-NLS-1$
	}

	protected ListIterator<JavaAttributeMappingProvider> javaAttributeMappingProviders() {
		return new CompositeListIterator<JavaAttributeMappingProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<JavaAttributeMappingProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<JavaAttributeMappingProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.javaAttributeMappingProviders();
				}
			}
		);
	}

	public JavaAttributeMapping buildJavaAttributeMappingFromAnnotation(String annotationName, JavaPersistentAttribute attribute) {
		return this.getJavaAttributeMappingProviderForAnnotation(annotationName).buildMapping(attribute, this.jpaFactory);
	}

	protected JavaAttributeMappingProvider getJavaAttributeMappingProviderForAnnotation(String annotationName) {
		for (JavaAttributeMappingProvider provider : CollectionTools.iterable(javaAttributeMappingProviders())) {
			if (provider.getAnnotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName); //$NON-NLS-1$
	}

	// ********** default Java attribute mappings **********

	public JavaAttributeMapping buildDefaultJavaAttributeMapping(JavaPersistentAttribute attribute) {
		return this.getDefaultJavaAttributeMappingProvider(attribute).buildMapping(attribute, this.jpaFactory);
	}

	public String getDefaultJavaAttributeMappingKey(JavaPersistentAttribute attribute) {
		return this.getDefaultJavaAttributeMappingProvider(attribute).getKey();
	}
	
	protected ListIterator<DefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders() {
		return new CompositeListIterator<DefaultJavaAttributeMappingProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<DefaultJavaAttributeMappingProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<DefaultJavaAttributeMappingProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.defaultJavaAttributeMappingProviders();
				}
			}
		);
	}

	protected JavaAttributeMappingProvider getDefaultJavaAttributeMappingProvider(JavaPersistentAttribute attribute) {
		for (DefaultJavaAttributeMappingProvider provider : CollectionTools.iterable(this.defaultJavaAttributeMappingProviders())) {
			if (provider.defaultApplies(attribute)) {
				return provider;
			}
		}
		return this.getNullAttributeMappingProvider();
	}

	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	protected JavaAttributeMappingProvider getNullAttributeMappingProvider() {
		return JavaNullAttributeMappingProvider.instance();
	}


	// ********** Mapping File **********

	public MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.getMappingFileProviderForResourceType(resource.getContentType()).buildMappingFile(parent, resource, this.jpaFactory);
	}

	protected MappingFileProvider getMappingFileProviderForResourceType(IContentType contentType) {
		for (MappingFileProvider provider : CollectionTools.iterable(this.mappingFileProviders())) {
			if (provider.getContentType().equals(contentType)) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal mapping file content type: " + contentType); //$NON-NLS-1$
	}
	
	protected ListIterator<MappingFileProvider> mappingFileProviders() {
		return new CompositeListIterator<MappingFileProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<MappingFileProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<MappingFileProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.mappingFileProviders();
				}
			}
		);
	}


	// ********** extended ORM type mappings **********

	public OrmTypeMapping buildOrmTypeMappingFromMappingKey(String key, OrmPersistentType type) {
		return this.getOrmTypeMappingProviderForMappingKey(type.getContentType(), key).buildMapping(type, this.jpaFactory);
	}

	protected OrmTypeMappingProvider getOrmTypeMappingProviderForMappingKey(IContentType contentType, String key) {
		for (ExtendedOrmTypeMappingProvider provider : CollectionTools.iterable(this.extendedOrmTypeMappingProviders())) {
			if ((provider.getContentType().equals(contentType)) && (provider.getKey() == key)) {
				return provider;
			}
		}
		// if we don't have an ORM-specific provider, look for a "default" provider
		return this.getOrmTypeMappingProviderForMappingKey(key);
	}

	protected ListIterator<ExtendedOrmTypeMappingProvider> extendedOrmTypeMappingProviders() {
		return new CompositeListIterator<ExtendedOrmTypeMappingProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<ExtendedOrmTypeMappingProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<ExtendedOrmTypeMappingProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.extendedOrmTypeMappingProviders();
				}
			}
		);
	}

	// ********** "default" ORM type mappings **********

	protected OrmTypeMappingProvider getOrmTypeMappingProviderForMappingKey(String key) {
		for (OrmTypeMappingProvider provider : CollectionTools.iterable(this.ormTypeMappingProviders())) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + key); //$NON-NLS-1$
	}

	protected ListIterator<OrmTypeMappingProvider> ormTypeMappingProviders() {
		return new CompositeListIterator<OrmTypeMappingProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<OrmTypeMappingProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<OrmTypeMappingProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.ormTypeMappingProviders();
				}
			}
		);
	}
	

	// ********** extended ORM attribute mappings **********

	public OrmAttributeMapping buildOrmAttributeMappingFromMappingKey(String key, OrmPersistentAttribute attribute) {
		return this.getOrmAttributeMappingProviderForMappingKey(attribute.getContentType(), key).buildMapping(attribute, this.jpaFactory);
	}

	public XmlAttributeMapping buildVirtualOrmResourceMappingFromMappingKey(String key, OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return this.getOrmAttributeMappingProviderForMappingKey(ormTypeMapping.getContentType(), key).buildVirtualResourceMapping(ormTypeMapping, javaAttributeMapping, this.jpaFactory);
	}

	protected OrmAttributeMappingProvider getOrmAttributeMappingProviderForMappingKey(IContentType contentType, String key) {
		for (ExtendedOrmAttributeMappingProvider provider : CollectionTools.iterable(this.extendedOrmAttributeMappingProviders())) {
			if ((provider.getContentType().equals(contentType)) && (provider.getKey() == key)) {
				return provider;
			}
		}
		// if we don't have an ORM-specific provider, look for a "default" provider
		return this.getOrmAttributeMappingProviderForMappingKey(key);
	}

	protected ListIterator<ExtendedOrmAttributeMappingProvider> extendedOrmAttributeMappingProviders() {
		return new CompositeListIterator<ExtendedOrmAttributeMappingProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<ExtendedOrmAttributeMappingProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<ExtendedOrmAttributeMappingProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.extendedOrmAttributeMappingProviders();
				}
			}
		);
	}

	// ********** "default" ORM attribute mappings **********

	protected OrmAttributeMappingProvider getOrmAttributeMappingProviderForMappingKey(String key) {
		for (OrmAttributeMappingProvider provider : CollectionTools.iterable(this.ormAttributeMappingProviders())) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		return OrmNullAttributeMappingProvider.instance();
	}

	protected ListIterator<OrmAttributeMappingProvider> ormAttributeMappingProviders() {
		return new CompositeListIterator<OrmAttributeMappingProvider> ( 
			new TransformationListIterator<JpaPlatformProvider, ListIterator<OrmAttributeMappingProvider>>(this.platformProviders()) {
				@Override
				protected ListIterator<OrmAttributeMappingProvider> transform(JpaPlatformProvider platformProvider) {
					return platformProvider.ormAttributeMappingProviders();
				}
			}
		);
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
