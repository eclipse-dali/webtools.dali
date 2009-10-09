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
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.core.JptCorePlugin;
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
		
		monitor = this.nonNullMonitor(monitor);
		try {
			this.execute_(project, fv, config, monitor);
		} finally {
			monitor.done();
		}
	}
	
	protected void execute_(
			IProject project, @SuppressWarnings("unused") IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		monitor.beginTask("", 1); //$NON-NLS-1$
		
		// NB: WTP Natures (including the JavaEMFNature)
		// should already be added, as this facet should 
		// always coexist with a module facet.
		
		IDataModel dataModel = (IDataModel) config;
		
		// project settings
		JptCorePlugin.setJpaPlatformId(project, dataModel.getStringProperty(PLATFORM_ID));
		
		// do NOT use IDataModel.getStringProperty(String) - or the connection profile name can
		// be set to an empty string - we want it to be null
		JptCorePlugin.setConnectionProfileName(project, (String) dataModel.getProperty(CONNECTION));
		
		if (dataModel.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)) {
			JptCorePlugin.setUserOverrideDefaultCatalog(project, dataModel.getStringProperty(USER_OVERRIDE_DEFAULT_CATALOG));
		}
		if (dataModel.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			JptCorePlugin.setUserOverrideDefaultSchema(project, dataModel.getStringProperty(USER_OVERRIDE_DEFAULT_SCHEMA));
		}
		
		JptCorePlugin.setDiscoverAnnotatedClasses(project, dataModel.getBooleanProperty(DISCOVER_ANNOTATED_CLASSES));
		
		// defaults settings
		JptCorePlugin.setDefaultJpaPlatformId(dataModel.getStringProperty(PLATFORM_ID));
		
		//Delegate to LibraryInstallDelegate to configure the project classpath
		((LibraryInstallDelegate) dataModel.getProperty(JpaFacetInstallDataModelProperties.LIBRARY_PROVIDER_DELEGATE)).execute(new NullProgressMonitor());
		
		monitor.worked(1);
	}
	
	private IProgressMonitor nonNullMonitor(IProgressMonitor monitor) {
		return (monitor != null) ? monitor : new NullProgressMonitor();
	}
}
