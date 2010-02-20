/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ListIterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaPlatformVariation;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.ResourceDefinition;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatform
	implements JpaPlatform
{
	private final String id;
	
	private final Version jpaVersion;
	
	private final JpaFactory jpaFactory;
	
	private final JpaAnnotationProvider annotationProvider;
	
	private final JpaPlatformProvider platformProvider;
	
	private final JpaPlatformVariation jpaVariation;
	
	
	public GenericJpaPlatform(String id, Version jpaVersion, JpaFactory jpaFactory, JpaAnnotationProvider jpaAnnotationProvider, JpaPlatformProvider platformProvider, JpaPlatformVariation jpaVariation) {
		super();
		this.id = id;
		this.jpaVersion = jpaVersion;
		this.jpaFactory = jpaFactory;
		this.annotationProvider = jpaAnnotationProvider;
		this.jpaVariation = jpaVariation;
		this.platformProvider = platformProvider;
	}
	
	
	public String getId() {
		return this.id;
	}
	
	public Version getJpaVersion() {
		return this.jpaVersion;
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
	
	public JavaTypeMappingDefinition getJavaTypeMappingDefinition(JavaPersistentType type) {
		for (JavaTypeMappingDefinition definition : 
				CollectionTools.iterable(javaTypeMappingDefinitions())) {
			if (definition.test(type)) {
				return definition;
			}
		}
		throw new IllegalStateException("There must be a mapping definition for all types"); //$NON-NLS-1$
	}
	
	public JavaTypeMappingDefinition getJavaTypeMappingDefinition(String mappingKey) {
		for (JavaTypeMappingDefinition definition : 
				CollectionTools.iterable(javaTypeMappingDefinitions())) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}
	
	protected ListIterator<JavaTypeMappingDefinition> javaTypeMappingDefinitions() {
		return this.platformProvider.javaTypeMappingDefinitions();
	}
	
	
	// ********** Java attribute mappings **********
	
	public JavaAttributeMappingDefinition getDefaultJavaAttributeMappingDefinition(
			JavaPersistentAttribute attribute) {
		for (JavaAttributeMappingDefinition definition : 
				CollectionTools.iterable(defaultJavaAttributeMappingDefinitions())) {
			if (definition.testDefault(attribute)) {
				return definition;
			}
		}
		throw new IllegalStateException("There must be a mapping definition for all attributes"); //$NON-NLS-1$
	}
	
	protected ListIterator<JavaAttributeMappingDefinition> defaultJavaAttributeMappingDefinitions() {
		return this.platformProvider.defaultJavaAttributeMappingDefinitions();
	}
	
	public JavaAttributeMappingDefinition getSpecifiedJavaAttributeMappingDefinition(
			JavaPersistentAttribute attribute) {
		for (JavaAttributeMappingDefinition definition : 
				CollectionTools.iterable(specifiedJavaAttributeMappingDefinitions())) {
			if (definition.testSpecified(attribute)) {
				return definition;
			}
		}
		throw new IllegalStateException("There must be a mapping definition for all attributes"); //$NON-NLS-1$
	}
	
	protected ListIterator<JavaAttributeMappingDefinition> specifiedJavaAttributeMappingDefinitions() {
		return this.platformProvider.specifiedJavaAttributeMappingDefinitions();
	}
	
	public JavaAttributeMappingDefinition getSpecifiedJavaAttributeMappingDefinition(String mappingKey) {
		for (JavaAttributeMappingDefinition definition : 
				CollectionTools.iterable(specifiedJavaAttributeMappingDefinitions())) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + mappingKey); //$NON-NLS-1$
	}
	
	
	// ********** resource types and definitions **********
	
	public boolean supportsResourceType(JpaResourceType resourceType) {
		for (ResourceDefinition resourceDefinition : CollectionTools.iterable(resourceDefinitions())) {
			if (resourceDefinition.getResourceType().equals(resourceType)) {
				return true;
			}
		}
		return false;
	}
	
	public ResourceDefinition getResourceDefinition(JpaResourceType resourceType) {
		for (ResourceDefinition resourceDefinition : CollectionTools.iterable(resourceDefinitions())) {
			if (resourceDefinition.getResourceType().equals(resourceType)) {
				return resourceDefinition;
			}
		}
		throw new IllegalArgumentException("Illegal resource type: " + resourceType); //$NON-NLS-1$
	}
	
	protected ListIterator<ResourceDefinition> resourceDefinitions() {
		return this.platformProvider.resourceDefinitions();
	}
	
	public JpaResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		return this.platformProvider.getMostRecentSupportedResourceType(contentType);
	}
	
	
	// ********** database **********

	public ConnectionProfileFactory getConnectionProfileFactory() {
		return JptDbPlugin.instance().getConnectionProfileFactory();
	}

	public EntityGeneratorDatabaseAnnotationNameBuilder getEntityGeneratorDatabaseAnnotationNameBuilder() {
		return GenericEntityGeneratorDatabaseAnnotationNameBuilder.instance();
	}

	
	// ********** validation **********
	
	public JpaPlatformVariation getJpaVariation() {
		return this.jpaVariation;
	}
}
