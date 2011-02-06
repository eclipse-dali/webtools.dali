/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Performs things common to install, version change
 */
public abstract class JpaFacetActionDelegate
	implements IDelegate, JpaFacetDataModelProperties
{
	public final void execute(
			IProject project, IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		this.execute_(project, fv, config, monitor);
	}
	
	protected void execute_(
			IProject project, IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		SubMonitor subMonitor = SubMonitor.convert(monitor, 7);
		
		IDataModel dataModel = (IDataModel) config;
		
		// project settings
		JpaPlatformDescription platform = (JpaPlatformDescription) dataModel.getProperty(PLATFORM);
		JptJpaCorePlugin.setJpaPlatformId(project, platform.getId());
		subMonitor.worked(1);
		
		// do NOT use IDataModel.getStringProperty(String) - or the connection profile name can
		// be set to an empty string - we want it to be null
		JptJpaCorePlugin.setConnectionProfileName(project, (String) dataModel.getProperty(CONNECTION));
		subMonitor.worked(1);
		
		if (dataModel.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)) {
			JptJpaCorePlugin.setUserOverrideDefaultCatalog(project, dataModel.getStringProperty(USER_OVERRIDE_DEFAULT_CATALOG));
		}
		subMonitor.worked(1);
		
		if (dataModel.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			JptJpaCorePlugin.setUserOverrideDefaultSchema(project, dataModel.getStringProperty(USER_OVERRIDE_DEFAULT_SCHEMA));
		}
		subMonitor.worked(1);
		
		JptJpaCorePlugin.setDiscoverAnnotatedClasses(project, dataModel.getBooleanProperty(DISCOVER_ANNOTATED_CLASSES));
		subMonitor.worked(1);
		
		// defaults settings
		JptJpaCorePlugin.setDefaultJpaPlatformId(fv.getVersionString(), platform.getId());
		subMonitor.worked(1);
		
		//Delegate to LibraryInstallDelegate to configure the project classpath
		((LibraryInstallDelegate) dataModel.getProperty(JpaFacetDataModelProperties.LIBRARY_PROVIDER_DELEGATE)).execute(subMonitor.newChild(1));
	}
}
