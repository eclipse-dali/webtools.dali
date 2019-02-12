/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ContentTypeTools;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaEntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaResourceDefinition;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatform
	implements JpaPlatform
{
	private final JpaPlatform.Config config;

	private final Version jpaVersion;

	private final JpaFactory jpaFactory;

	private final AnnotationProvider annotationProvider;

	private final JpaPlatformProvider platformProvider;

	private final JpaPlatformVariation jpaVariation;

	private final JPQLGrammar jpqlGrammar;


	public GenericJpaPlatform(
			JpaPlatform.Config config,
			Version jpaVersion,
			JpaFactory jpaFactory,
			AnnotationProvider annotationProvider,
			JpaPlatformProvider platformProvider,
			JpaPlatformVariation jpaVariation,
			JPQLGrammar jpqlGrammar
	) {
		super();
		this.config = config;
		this.jpaVersion = jpaVersion;
		this.jpaFactory = jpaFactory;
		this.annotationProvider = annotationProvider;
		this.jpaVariation = jpaVariation;
		this.platformProvider = platformProvider;
		this.jpqlGrammar = jpqlGrammar;
	}


	// ********** meta stuff **********

	public String getId() {
		return this.config.getId();
	}

	public JpaPlatform.Config getConfig() {
		return this.config;
	}

	public Version getJpaVersion() {
		return this.jpaVersion;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.getId());
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
		IContentType contentType = getContentType(file);
		return (contentType == null) ? null : this.buildJpaFile(jpaProject, file, contentType);
	}

	/**
	 * Performance hook: {@link ContentTypeTools#contentType(IFile)} gets the
	 * file contents <em>every</em> time. Many of our files are Java files and
	 * it is possible to determine a Java file's content type from the file
	 * name; so do that here, before using {@link PlatformTools}.
	 */
	public IContentType getContentType(IFile file) {
		IContentType contentType = Platform.getContentTypeManager().findContentTypeFor(file.getName());
		if (contentType != null) {
			if (contentType.equals(JavaResourceCompilationUnit.CONTENT_TYPE)) {
				return contentType;
			}
		}
		return ContentTypeTools.contentType(file);
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
	 * Return <code>null</code> if we don't have a provider for the specified content type
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

	public AnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}

	public AnnotationEditFormatter getAnnotationEditFormatter() {
		return DefaultAnnotationEditFormatter.instance();
	}


	// ********** Java managed types **********

	public Iterable<JavaManagedTypeDefinition> getJavaManagedTypeDefinitions() {
		return this.platformProvider.getJavaManagedTypeDefinitions();
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
		return this.getResourceDefinition_(resourceType) != null;
	}

	public JpaResourceDefinition getResourceDefinition(JptResourceType resourceType) {
		JpaResourceDefinition definition = this.getResourceDefinition_(resourceType);
		if (definition == null) {
			throw new IllegalArgumentException("Illegal resource type: " + resourceType); //$NON-NLS-1$
		}
		return definition;
	}

	protected JpaResourceDefinition getResourceDefinition_(JptResourceType resourceType) {
		for (JpaResourceDefinition resourceDefinition : this.platformProvider.getResourceDefinitions()) {
			if (resourceDefinition.getResourceType().equals(resourceType)) {
				return resourceDefinition;
			}
		}
		return null;
	}

	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		for (JptResourceType resourceType : this.platformProvider.getMostRecentSupportedResourceTypes()) {
			if (resourceType.getContentType().equals(contentType)) {
				return resourceType;
			}
		}
		throw new IllegalArgumentException(contentType.toString());
	}


	// ********** database **********

	public ConnectionProfileFactory getConnectionProfileFactory() {
		return this.config.getJpaPlatformManager().getJpaWorkspace().getConnectionProfileFactory();
	}

	public JpaEntityGeneratorDatabaseAnnotationNameBuilder getEntityGeneratorDatabaseAnnotationNameBuilder() {
		return GenericEntityGeneratorDatabaseAnnotationNameBuilder.instance();
	}


	// ********** validation **********

	public JpaPlatformVariation getJpaVariation() {
		return this.jpaVariation;
	}


	// ********** adapter **********

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(Class adapter) {
		return PlatformTools.getAdapter(this, adapter);
	}


	// ********** Hermes integration **********

	public JPQLGrammar getJpqlGrammar() {
		return this.jpqlGrammar;
	}
}
