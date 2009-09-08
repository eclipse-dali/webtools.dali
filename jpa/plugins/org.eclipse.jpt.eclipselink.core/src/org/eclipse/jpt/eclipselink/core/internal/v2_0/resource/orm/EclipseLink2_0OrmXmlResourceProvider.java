/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0;
import org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmFactory;

public class EclipseLink2_0OrmXmlResourceProvider
	extends AbstractXmlResourceProvider
{
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for 
	 * the given file.
	 */
	public static EclipseLink2_0OrmXmlResourceProvider getXmlResourceProvider(IFile file) {
		return getXmlResourceProvider_(file.getProject(), file.getFullPath().toString());
	}
	
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for
	 * the given project in the specified deploy location
	 */
	public static EclipseLink2_0OrmXmlResourceProvider getXmlResourceProvider(
			IProject project, String deployLocation) {
		return getXmlResourceProvider_(
			project, JptCorePlugin.getDeploymentURI(project, deployLocation));
	}
	
	/**
	 * (Convenience method) Returns an EclipseLink ORM resource model provider for 
	 * the given project in the default deploy location
	 */
	public static EclipseLink2_0OrmXmlResourceProvider getDefaultXmlResourceProvider(IProject project) {
		return getXmlResourceProvider(
			project, JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
	}
	
	private static EclipseLink2_0OrmXmlResourceProvider getXmlResourceProvider_(
			IProject project, String location) {
		return new EclipseLink2_0OrmXmlResourceProvider(project, new Path(location));
	}
	
	
	public EclipseLink2_0OrmXmlResourceProvider(IProject project) {
		this(project, new Path(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH));
	}
	
	public EclipseLink2_0OrmXmlResourceProvider(IProject project, IPath filePath) {
		super(project, filePath, JptEclipseLinkCorePlugin.ECLIPSELINK2_0_ORM_XML_CONTENT_TYPE);
	}
	
	
	@Override
	protected void populateRoot() {
		XmlEntityMappings entityMappings = 
			EclipseLink2_0OrmFactory.eINSTANCE.createXmlEntityMappings();
		entityMappings.setVersion(EclipseLink2_0.SCHEMA_VERSION);
		getResourceContents().add(entityMappings);
	}
}
