/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
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
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.internal.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbPlatformProvider;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

public final class JaxbPlatformImpl
		implements JaxbPlatform {
	
	private final JaxbFactory jaxbFactory;
	
	private final JpaAnnotationProvider annotationProvider;

	private final JaxbPlatformProvider platformProvider;

	public JaxbPlatformImpl(JaxbPlatformDefinition jaxbPlatformDefinition) {
		super();
		this.jaxbFactory = jaxbPlatformDefinition.buildFactory();
		this.annotationProvider = new GenericJpaAnnotationProvider(jaxbPlatformDefinition.getAnnotationDefinitionProviders());
		this.platformProvider = jaxbPlatformDefinition.buildPlatformProvider();
	}


	// ********** factory **********

	public JaxbFactory getFactory() {
		return this.jaxbFactory;
	}


	// ********** JAXB file/resource models **********

	public JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file) {
		IContentType contentType = PlatformTools.getContentType(file);
		return (contentType == null) ? null : this.buildJaxbFile(jaxbProject, file, contentType);
	}

	protected JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file, IContentType contentType) {
		JpaResourceModel resourceModel = this.buildResourceModel(jaxbProject, file, contentType);
		return (resourceModel == null) ? null : this.jaxbFactory.buildJaxbFile(jaxbProject, file, contentType, resourceModel);
	}

	protected JpaResourceModel buildResourceModel(JaxbProject jaxbProject, IFile file, IContentType contentType) {
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
		return this.platformProvider.getResourceModelProviders();
	}


	// ********** Java annotations **********

	public JpaAnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}

	public AnnotationEditFormatter getAnnotationEditFormatter() {
		return DefaultAnnotationEditFormatter.instance();
	}

}
