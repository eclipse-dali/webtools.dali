/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.utility;

import junit.framework.TestCase;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.BackingStoreException;

@SuppressWarnings("nls")
public class ProjectPreferencesTests
	extends TestCase
{
	public ProjectPreferencesTests(String name) {
		super(name);
	}

	public void testProjectRename() throws Exception {
		String key = "pref";
		String value = "XXXX";

		IProject project1 = this.buildProject("foo");
		this.setPreference(project1, key, value);
		assertEquals(value, this.getPreference(project1, key));

		IPath dest = new Path("/bar");
		project1.move(dest, IResource.SHALLOW, null);
		IProject project2 = ResourcesPlugin.getWorkspace().getRoot().getProject("bar");
		assertEquals(value, this.getPreference(project2, key));

		// this will trigger the creation of "phantom" pref nodes for the now-missing 'foo' project
		String pref = this.getPreference(project1, key);
		assertNull(pref);

		dest = new Path("/foo");
		project2.move(dest, IResource.SHALLOW, null);
		// 414795 - this will fail if the 'foo' project's "phantom" pref nodes are still there
		assertEquals(value, this.getPreference(project1, key));
		project2.delete(true, null);
	}

	private IProject buildProject(String projectName) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.create(null);
		project.open(null);
		return project;
	}

	private void setPreference(IProject project, String key, String value) throws BackingStoreException {
		IEclipsePreferences prefs = this.getPreferences(project);
		prefs.put(key, value);
		prefs.flush();
	}

	private String getPreference(IProject project, String key) {
		return this.getPreferences(project).get(key, null);
	}

	private IEclipsePreferences getPreferences(IProject project) {
		return this.getPreferences(new ProjectScope(project));
	}

	private IEclipsePreferences getPreferences(IScopeContext context) {
		return context.getNode("test");
	}
}
