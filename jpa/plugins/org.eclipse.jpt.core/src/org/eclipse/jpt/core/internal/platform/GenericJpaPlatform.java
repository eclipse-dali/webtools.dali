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
import org.eclipse.jpt.core.JpaPlatformVariation;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;

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
	
	private final JpaPlatformProvider platformProvider;
	
	private final JpaPlatformVariation jpaVariation;
	
	
	public GenericJpaPlatform(String id, JpaFactory jpaFactory, JpaAnnotationProvider jpaAnnotationProvider, JpaPlatformProvider platformProvider, JpaPlatformVariation jpaVariation) {
		super();
		this.id = id;
		this.jpaFactory = jpaFactory;
		this.annotationProvider = jpaAnnotationProvider;
		this.jpaVariation = jpaVariation;
		this.platformProvider = platformProvider;
	}
	
	
	public String getId() {
		return this.id;
	}


	// ********** factory **********

	public JpaFactory getJpaFactory() {
		return this.jpaFactory;
	}
	
	
	// ********** platform providers **********
	
	protected JpaPlatformProvider getPlatformProvider() {
		return this.platformProvider;
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
	 */
	protected JpaResourceModelProvider getResourceModelProvider(IContentType contentType) {
		for (JpaResourceModelProvider provider : CollectionTools.iterable(resourceModelProviders())) {
			if (contentType.equals(provider.getContentType())) {
				return provider;
			}
		}
		return null;
	}

	protected ListIterator<JpaResourceModelProvider> resourceModelProviders() {
		return this.platformProvider.resourceModelProviders();
	}
	
	
	// ********** Java annotations **********

	public JpaAnnotationProvider getAnnotationProvider() {
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
		return this.platformProvider.javaTypeMappingProviders();
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
	
	public JavaAttributeMappingProvider getDefaultJavaAttributeMappingProvider(
			JavaPersistentAttribute attribute) {
		for (JavaAttributeMappingProvider provider : 
				CollectionTools.iterable(defaultJavaAttributeMappingProviders())) {
			if (provider.defaultApplies(attribute)) {
				return provider;
			}
		}
		throw new IllegalStateException("There must be a mapping provider for all attributes"); //$NON-NLS-1$
	}
	
	protected ListIterator<JavaAttributeMappingProvider> defaultJavaAttributeMappingProviders() {
		return this.platformProvider.defaultJavaAttributeMappingProviders();
	}
	
	public JavaAttributeMappingProvider getSpecifiedJavaAttributeMappingProvider(
			JavaPersistentAttribute attribute) {
		for (JavaAttributeMappingProvider provider : 
				CollectionTools.iterable(specifiedJavaAttributeMappingProviders())) {
			if (provider.specifiedApplies(attribute)) {
				return provider;
			}
		}
		throw new IllegalStateException("There must be a mapping provider for all attributes"); //$NON-NLS-1$
	}
	
	protected ListIterator<JavaAttributeMappingProvider> specifiedJavaAttributeMappingProviders() {
		return this.platformProvider.specifiedJavaAttributeMappingProviders();
	}
	
	public JavaAttributeMappingProvider getSpecifiedJavaAttributeMappingProvider(String mappingKey) {
		for (JavaAttributeMappingProvider provider : CollectionTools.iterable(specifiedJavaAttributeMappingProviders())) {
			if (provider.getKey() == mappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + mappingKey); //$NON-NLS-1$
	}
	

	// ********** Mapping Files **********
	
	public MappingFileDefinition getMappingFileDefinition(IContentType contentType) {
		for (MappingFileDefinition mappingFileDef : CollectionTools.iterable(mappingFileDefinitions())) {
			if (mappingFileDef.getContentType().isKindOf(contentType)) {
				return mappingFileDef;
			}
		}
		throw new IllegalArgumentException("Illegal mapping file content type: " + contentType); //$NON-NLS-1$
	}
	
	protected ListIterator<MappingFileDefinition> mappingFileDefinitions() {
		return this.platformProvider.mappingFileDefinitions();
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
	
	
	// ********** validation **********
	
	public JpaPlatformVariation getJpaVariation() {
		return this.jpaVariation;
	}
}
