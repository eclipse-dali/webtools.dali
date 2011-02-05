/*******************************************************************************
 *  Copyright (c) 2010, 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.platform;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.AnnotationProvider;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.internal.GenericAnnotationProvider;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;

public final class JaxbPlatformImpl
		implements JaxbPlatform {
	
	private JaxbPlatformDefinition platformDefinition;
	
	private AnnotationProvider annotationProvider;
	
	
	public JaxbPlatformImpl(JaxbPlatformDefinition jaxbPlatformDefinition) {
		super();
		this.platformDefinition = jaxbPlatformDefinition;
		this.annotationProvider = new GenericAnnotationProvider(this.platformDefinition.getAnnotationDefinitions(), this.platformDefinition.getNestableAnnotationDefinitions());
	}
	
	
	public JaxbPlatformDescription getDescription() {
		return this.platformDefinition.getDescription();
	}
	
	// ********** factory **********

	public JaxbFactory getFactory() {
		return this.platformDefinition.getFactory();
	}
	
	
	// ********** JAXB file/resource models **********

	public JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file) {
		IContentType contentType = PlatformTools.getContentType(file);
		return (contentType == null) ? null : this.buildJaxbFile(jaxbProject, file, contentType);
	}

	protected JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file, IContentType contentType) {
		JptResourceModel resourceModel = this.buildResourceModel(jaxbProject, file, contentType);
		return (resourceModel == null) ? null : getFactory().buildJaxbFile(jaxbProject, file, contentType, resourceModel);
	}

	protected JptResourceModel buildResourceModel(JaxbProject jaxbProject, IFile file, IContentType contentType) {
		JaxbResourceModelProvider provider = this.getResourceModelProvider(contentType);
		return (provider == null) ? null : provider.buildResourceModel(jaxbProject, file);
	}

	/**
	 * Return null if we don't have a provider for the specified content type
	 * (since we don't have control over the possible content types).
	 */
	protected JaxbResourceModelProvider getResourceModelProvider(IContentType contentType) {
		for (JaxbResourceModelProvider provider : getResourceModelProviders()) {
			if (contentType.equals(provider.getContentType())) {
				return provider;
			}
		}
		return null;
	}

	protected ListIterable<JaxbResourceModelProvider> getResourceModelProviders() {
		return this.platformDefinition.getResourceModelProviders();
	}
	
	
	// ********** Java annotations **********
	
	public AnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}

	public AnnotationEditFormatter getAnnotationEditFormatter() {
		return DefaultAnnotationEditFormatter.instance();
	}


	// ********** Java attribute mappings **********

	public JavaAttributeMappingDefinition getSpecifiedJavaAttributeMappingDefinition(
			JaxbPersistentAttribute attribute) {
		for (JavaAttributeMappingDefinition definition : getSpecifiedJavaAttributeMappingDefinitions()) {
			if (definition.isSpecified(attribute)) {
				return definition;
			}
		}
		throw new IllegalStateException("There must be a mapping definition for all attributes"); //$NON-NLS-1$
	}

	public Iterable<JavaAttributeMappingDefinition> getSpecifiedJavaAttributeMappingDefinitions() {
		return this.platformDefinition.getSpecifiedJavaAttributeMappingDefinitions();
	}

	public JavaAttributeMappingDefinition getSpecifiedJavaAttributeMappingDefinition(String mappingKey) {
		for (JavaAttributeMappingDefinition definition : getSpecifiedJavaAttributeMappingDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), mappingKey)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + mappingKey); //$NON-NLS-1$
	}

	public Iterable<DefaultJavaAttributeMappingDefinition> getDefaultJavaAttributeMappingDefinitions() {
		return this.platformDefinition.getDefaultJavaAttributeMappingDefinitions();
	}
}
