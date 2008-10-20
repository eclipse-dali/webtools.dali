/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.JpaResourceModelProviderManager;
import org.eclipse.jpt.core.resource.AbstractResourceModelProvider;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkConstants;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResource;

public class EclipseLinkOrmResourceModelProvider extends AbstractResourceModelProvider
{
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for 
	 * the given project in the default location
	 */
	public static EclipseLinkOrmResourceModelProvider getDefaultModelProvider(IProject project) {
		return getModelProvider(project, JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
	}
	
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for 
	 * the given file.
	 */
	public static EclipseLinkOrmResourceModelProvider getModelProvider(IFile file) {
		return getModelProvider(file.getProject(), file.getProjectRelativePath().toString());
	}
	
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for
	 * the given project in the specified location
	 */
	public static EclipseLinkOrmResourceModelProvider getModelProvider(IProject project, String location) {
		return (EclipseLinkOrmResourceModelProvider) JpaResourceModelProviderManager.instance().getModelProvider(
			project, 
			new Path(JptCorePlugin.getDeploymentURI(project, location)),
			JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE);
	}
	
	
	public EclipseLinkOrmResourceModelProvider(IProject project) {
		this(project, new Path(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH));
	}
	
	public EclipseLinkOrmResourceModelProvider(IProject project, IPath filePath) {
		super(project, filePath);
	}
	
	
	@Override
	protected String getContentTypeDescriber() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}
	
	@Override
	protected void populateRoot(JpaXmlResource resource) {
		XmlEntityMappings entityMappings = OrmFactory.eINSTANCE.createXmlEntityMappings();
		entityMappings.setVersion(EclipseLinkConstants.VERSION_1_0_TEXT);
		getResourceContents(resource).add(entityMappings);
	}
	
	@Override
	public EclipseLinkOrmResource getResource() {
		return (EclipseLinkOrmResource) super.getResource();
	}
}
