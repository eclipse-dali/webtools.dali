/*******************************************************************************
 *  Copyright (c) 2005, 2007 Oracle. All rights reserved.  This program and 
 *  the accompanying materials are made available under the terms of the 
 *  Eclipse Public License v1.0 which accompanies this distribution, and is 
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jpt.core.internal.IJpaCoreConstants;
import org.eclipse.jpt.core.internal.JpaPlatformRegistry;
import org.eclipse.jpt.core.internal.JpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.platform.generic.GenericPlatform;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.osgi.service.prefs.BackingStoreException;

public class JpaFacetUtils
{
	private static QualifiedName CONNECTION_KEY = 
			new QualifiedName(JptCorePlugin.PLUGIN_ID, IJpaCoreConstants.DATA_SOURCE_CONNECTION_NAME);
	
	
	/**
	 * Private constructor - static class only
	 */
	private JpaFacetUtils() {}
	
	
	public static String getPlatform(final IProject project) {
		final IScopeContext context = new ProjectScope(project);
		final IEclipsePreferences prefs = context.getNode(JptCorePlugin.PLUGIN_ID);
		
		String platformId = prefs.get(IJpaCoreConstants.JPA_PLATFORM, null);
		
		if (platformId == null) {
			try {
				setPlatform(project, GenericPlatform.ID);
			}
			catch (CoreException ce) {
				// do nothing.  not sure what can be done here.
			}
			return GenericPlatform.ID;
		}
		
		return platformId;
	}
	
	public static void setPlatform(final IProject project, String jpaPlatformId) 
		throws CoreException
	{
		final IScopeContext context = new ProjectScope(project);
		final IEclipsePreferences prefs = context.getNode(JptCorePlugin.PLUGIN_ID);
		
		JpaProject jpaProject = (JpaProject) JptCorePlugin.getJpaProject(project);
		
		if (jpaProject == null) {
			throw new IllegalArgumentException(project.getName());
		}
		
		if (JpaPlatformRegistry.instance().jpaPlatform(jpaPlatformId) == null) {
			throw new IllegalArgumentException(jpaPlatformId);
		}
		
		jpaProject.setPlatform(jpaPlatformId);
		prefs.put(IJpaCoreConstants.JPA_PLATFORM, jpaPlatformId);
		
		try {
			prefs.flush();
		}
		catch( BackingStoreException e ) {
			JptCorePlugin.log(e);
		}
	}
	
	public static String getConnectionName(final IProject project) {
		try {
			return project.getPersistentProperty(CONNECTION_KEY);
		}
		catch (CoreException ce) {
			return null;
		}
	}
	
	public static void setConnectionName(final IProject project, String connectionName)
		throws CoreException
	{
		JpaProject jpaProject = (JpaProject) JptCorePlugin.getJpaProject(project);
		
		if (jpaProject == null) {
			throw new IllegalArgumentException(project.getName());
		}
		
		jpaProject.setDataSource(connectionName);
		project.setPersistentProperty(CONNECTION_KEY, connectionName);
	}
	
	public static boolean getDiscoverAnnotatedClasses(final IProject project) {
		final IScopeContext context = new ProjectScope(project);
		final IEclipsePreferences prefs = context.getNode(JptCorePlugin.PLUGIN_ID);
		
		return prefs.getBoolean(IJpaCoreConstants.DISCOVER_ANNOTATED_CLASSES, false);
	}
	
	public static void setDiscoverAnnotatedClasses(final IProject project, boolean discoverAnnotatedClasses) 
		throws CoreException
	{
		final IScopeContext context = new ProjectScope(project);
		final IEclipsePreferences prefs = context.getNode(JptCorePlugin.PLUGIN_ID);
		
		JpaProject jpaProject = (JpaProject) JptCorePlugin.getJpaProject(project);
		
		if (jpaProject == null) {
			throw new IllegalArgumentException(project.getName());
		}
		
		jpaProject.setDiscoverAnnotatedClasses(discoverAnnotatedClasses);
		prefs.putBoolean(IJpaCoreConstants.DISCOVER_ANNOTATED_CLASSES, discoverAnnotatedClasses);
		
		try {
			prefs.flush();
		}
		catch(BackingStoreException e) {
			JptCorePlugin.log(e);
		}
	}
}
