/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.ResourceDefinition;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar;

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

	private final AnnotationProvider annotationProvider;

	private final JpaPlatformProvider platformProvider;

	private final JpaPlatformVariation jpaVariation;

	private final JPQLGrammar jpqlGrammar;


	public GenericJpaPlatform(String id, Version jpaVersion, JpaFactory jpaFactory, AnnotationProvider annotationProvider, JpaPlatformProvider platformProvider, JpaPlatformVariation jpaVariation, JPQLGrammar jpqlGrammar) {
		super();
		this.id = id;
		this.jpaVersion = jpaVersion;
		this.jpaFactory = jpaFactory;
		this.annotationProvider = annotationProvider;
		this.jpaVariation = jpaVariation;
		this.platformProvider = platformProvider;
		this.jpqlGrammar = jpqlGrammar;
	}


	// ********** meta stuff **********

	public String getId() {
		return this.id;
	}

	public JpaPlatformDescription getDescription() {
		return this.getJpaPlatformManager().getJpaPlatformDescription(this.getId());
	}

	private JpaPlatformManager getJpaPlatformManager() {
		return this.getJpaWorkspace().getJpaPlatformManager();
	}

	private JpaWorkspace getJpaWorkspace() {
		return (JpaWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JpaWorkspace.class);
	}

	public Version getJpaVersion() {
		return this.jpaVersion;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.id);
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

	//TODO make this a non-static method on JpaPlatform
	//I have done this because our code PlatformTools.getContentType(IFile) opens an InputStream
	//on the IFile in order to find the content type for our xml mapping files. This is expensive
	//when called for every file in the project and is only needed for xml mapping files. For now
	//I am attempting to find the content type just based on the file name first and short-circuiting
	//if it is a .java or .class file. If we made this api on the JpaPlatform we could potentially
	//check if it is XML content type and then only do the more expensive InputStream look-up
	//in that case. Because we are extensible we can't be 100% certain that a "mapping file" 
	//has xml content type so we would allow this to be overridable.
	public static IContentType getContentType(IFile file) {
		IContentType contentType = Platform.getContentTypeManager().findContentTypeFor(file.getName());
		if (contentType != null) {
			if (contentType.equals(JavaResourceCompilationUnit.CONTENT_TYPE)) {
				return contentType;
			}
			if (contentType.equals(JAVA_CLASS_CONTENT_TYPE)) {
				return contentType;
			}
		}
		return PlatformTools.getContentType(file);
	}
	//TODO JptCommonCorePlugin.JAVA_CLASS_CONTENT_TYPE after API freeze
	private static final IContentType JAVA_CLASS_CONTENT_TYPE = getContentType(JavaCore.PLUGIN_ID + ".javaClass");//$NON-NLS-1$
	
	private static IContentType getContentType(String contentType) {
		return Platform.getContentTypeManager().getContentType(contentType);
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

	public ResourceDefinition getResourceDefinition(JptResourceType resourceType) {
		ResourceDefinition definition = this.getResourceDefinition_(resourceType);
		if (definition == null) {
			throw new IllegalArgumentException("Illegal resource type: " + resourceType); //$NON-NLS-1$
		}
		return definition;
	}

	protected ResourceDefinition getResourceDefinition_(JptResourceType resourceType) {
		for (ResourceDefinition resourceDefinition : this.platformProvider.getResourceDefinitions()) {
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
		return (ConnectionProfileFactory) ResourcesPlugin.getWorkspace().getAdapter(ConnectionProfileFactory.class);
	}

	public EntityGeneratorDatabaseAnnotationNameBuilder getEntityGeneratorDatabaseAnnotationNameBuilder() {
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
		return jpqlGrammar;
	}
}