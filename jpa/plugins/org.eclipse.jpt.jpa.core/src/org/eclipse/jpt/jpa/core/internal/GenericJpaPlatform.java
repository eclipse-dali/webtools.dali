/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.jpa.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.jpa.core.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.ResourceDefinition;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.jpt.jpa.db.JptJpaDbPlugin;

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


	// ********** meta stuff **********

	public String getId() {
		return this.id;
	}

	public JpaPlatformDescription getDescription() {
		return JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform(this.getId());
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
		JptResourceModel resourceModel = this.buildResourceModel(jpaProject, file, contentType);
		return (resourceModel == null) ? null : this.jpaFactory.buildJpaFile(jpaProject, file, contentType, resourceModel);
	}

	protected JptResourceModel buildResourceModel(JpaProject jpaProject, IFile file, IContentType contentType) {
		JpaResourceModelProvider provider = this.getResourceModelProvider(contentType);
		return (provider == null) ? null : provider.buildResourceModel(jpaProject, file);
	}

	/**
	 * Return null if we don't have a provider for the specified content type
	 * (since we don't have control over the possible content types).
	 */
	protected JpaResourceModelProvider getResourceModelProvider(IContentType contentType) {
		for (JpaResourceModelProvider provider : this.platformProvider.getResourceModelProviders()) {
			if (contentType.equals(provider.getContentType())) {
				return provider;
			}
		}
		return null;
	}


	// ********** Java annotations **********

	public JpaAnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}

	public AnnotationEditFormatter getAnnotationEditFormatter() {
		return DefaultAnnotationEditFormatter.instance();
	}


	// ********** Java type mappings **********

	public Iterable<JavaTypeMappingDefinition> getJavaTypeMappingDefinitions() {
		return this.platformProvider.getJavaTypeMappingDefinitions();
	}


	// ********** Java attribute mappings **********

	public Iterable<JavaAttributeMappingDefinition> getSpecifiedJavaAttributeMappingDefinitions() {
		return this.platformProvider.getSpecifiedJavaAttributeMappingDefinitions();
	}

	public Iterable<DefaultJavaAttributeMappingDefinition> getDefaultJavaAttributeMappingDefinitions() {
		return this.platformProvider.getDefaultJavaAttributeMappingDefinitions();
	}


	// ********** resource types and definitions **********

	public boolean supportsResourceType(JptResourceType resourceType) {
		for (ResourceDefinition resourceDefinition : this.platformProvider.getResourceDefinitions()) {
			if (resourceDefinition.getResourceType().equals(resourceType)) {
				return true;
			}
		}
		return false;
	}

	public ResourceDefinition getResourceDefinition(JptResourceType resourceType) {
		for (ResourceDefinition resourceDefinition : this.platformProvider.getResourceDefinitions()) {
			if (resourceDefinition.getResourceType().equals(resourceType)) {
				return resourceDefinition;
			}
		}
		throw new IllegalArgumentException("Illegal resource type: " + resourceType); //$NON-NLS-1$
	}

	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		return this.platformProvider.getMostRecentSupportedResourceType(contentType);
	}


	// ********** database **********

	public ConnectionProfileFactory getConnectionProfileFactory() {
		return JptJpaDbPlugin.getConnectionProfileFactory();
	}

	public EntityGeneratorDatabaseAnnotationNameBuilder getEntityGeneratorDatabaseAnnotationNameBuilder() {
		return GenericEntityGeneratorDatabaseAnnotationNameBuilder.instance();
	}


	// ********** validation **********

	public JpaPlatformVariation getJpaVariation() {
		return this.jpaVariation;
	}
}
