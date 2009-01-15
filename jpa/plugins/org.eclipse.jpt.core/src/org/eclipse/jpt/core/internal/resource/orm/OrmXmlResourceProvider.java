/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JpaConstants;
import org.eclipse.jpt.core.internal.resource.JpaXmlResourceProviderManager;
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.OrmXmlResource;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;

public class OrmXmlResourceProvider
	extends AbstractXmlResourceProvider<OrmXmlResource>
{
	/**
	 * (Convenience method) Returns an ORM resource model provider for 
	 * the given file.
	 */
	public static OrmXmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath().toString());
	}
	
	/**
	 * (Convenience method) Returns an ORM resource model provider for
	 * the given project in the specified deploy location
	 */
	public static OrmXmlResourceProvider getXmlResourceProvider(IProject project, String deployLocation) {
		return getXmlResourceProvider_(project, JptCorePlugin.getDeploymentURI(project, deployLocation));
	}
	
	/**
	 * (Convenience method) Returns an ORM resource model provider for 
	 * the given project in the default deploy location
	 */
	public static OrmXmlResourceProvider getDefaultXmlResourceProvider(IProject project) {
		return getXmlResourceProvider(project, JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
	}
	
	private static OrmXmlResourceProvider getXmlResourceProvider_(IProject project, String location) {
		return (OrmXmlResourceProvider) JpaXmlResourceProviderManager.instance().getXmlResourceProvider(
			project, 
			new Path(location),
			JptCorePlugin.ORM_XML_CONTENT_TYPE);
	}
	
	
	public OrmXmlResourceProvider(IProject project) {
		this(project, new Path(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH));
	}
	
	public OrmXmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath);
	}
	
	
	@Override
	protected IContentType getContentType() {
		return JptCorePlugin.ORM_XML_CONTENT_TYPE;
	}
	
	@Override
	protected void populateRoot(JpaXmlResource resource) {
		XmlEntityMappings entityMappings = OrmFactory.eINSTANCE.createXmlEntityMappings();
		entityMappings.setVersion(JpaConstants.VERSION_1_0_TEXT);
		getResourceContents(resource).add(entityMappings);
	}
	
	@Override
	protected OrmXmlResource ensureCorrectType(Resource resource) throws ClassCastException {
		return (OrmXmlResource) resource;
	}
}
