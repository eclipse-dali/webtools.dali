/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import junit.framework.TestCase;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

/**
 * Provide a little infrastructure for testing the backward-compatibility of
 * preferences
 */
@SuppressWarnings("nls")
public abstract class PreferencesTests
	extends TestCase
{
	private TestJavaProject javaProjectHarness;
	private String workspacePrefsFilePath;
	private String projectPrefsFilePath;

	private static final String WORKSPACE_PREFS_DIR_NAME = "/.metadata/.plugins/org.eclipse.core.runtime/.settings/";
	private static final String PROJECT_PREFS_DIR_NAME = "/.settings/";


	public PreferencesTests(String name) {
		super(name);
	}

	// ********** set up/tear down **********

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.javaProjectHarness = this.buildJavaProjectHarness(false); // false = no auto-build
		this.workspacePrefsFilePath = this.getWorkspaceRoot().getLocation().toString() + WORKSPACE_PREFS_DIR_NAME + this.getWorkspacePrefsFileName();
		this.projectPrefsFilePath = this.getProject().getLocation().toString() + PROJECT_PREFS_DIR_NAME + this.getProjectPrefsFileName();
	}

	protected abstract String getWorkspacePrefsFileName();

	protected abstract String getProjectPrefsFileName();

	private TestJavaProject buildJavaProjectHarness(boolean autoBuild) throws Exception {
		return this.buildJavaProjectHarness(this.getName(), autoBuild);
	}

	private TestJavaProject buildJavaProjectHarness(String projectName, boolean autoBuild) throws Exception {
		return new TestJavaProject(projectName, autoBuild);
	}

	@Override
	protected void tearDown() throws Exception {
		this.getPlugin().removePreferences();
		this.flushWorkspacePrefs();
		// no need to remove project prefs, as we simply delete the entire project here
		CoreTestTools.delete(this.getProject());
		TestTools.clear(this);
		super.tearDown();
	}


	// ********** convenience methods **********

	protected IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	protected IProject getProject() {
		return this.javaProjectHarness.getProject();
	}

	protected Properties readProjectPrefs() {
		return this.readProperties(this.projectPrefsFilePath);
	}

	protected Properties readWorkspacePrefs() {
		return this.readProperties(this.workspacePrefsFilePath);
	}

	/**
	 * read preferences directly from file
	 */
	protected Properties readProperties(String path) {
		Properties properties = new Properties();
		FileInputStream stream;
		try {
			stream = new FileInputStream(path);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		try {
			properties.load(stream);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				stream.close();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		return properties;
	}

	protected void flushWorkspacePrefs() throws Exception {
		JptPlugin plugin = this.getPlugin();
		IEclipsePreferences prefs = (IEclipsePreferences) ReflectionTools.executeMethod(plugin, "getWorkspacePreferences");
		prefs.flush();
	}

	protected void flushProjectPrefs() throws Exception {
		JptPlugin plugin = this.getPlugin();
		IEclipsePreferences prefs = (IEclipsePreferences) ReflectionTools.executeMethod(plugin, "getProjectPreferences", IProject.class, this.getProject());
		prefs.flush();
	}

	protected JptPlugin getPlugin() throws Exception {
		return (JptPlugin) ReflectionTools.executeStaticMethod(this.getPreferencesClass(), "getPlugin");
	}

	/**
	 * Return the public facade class used by clients to read and write
	 * the plug-in's preferences (e.g. <code>JpaPreferences.class</code>).
	 * The expectation is this class implements the (private) static method
	 * <code>getPlugin()</code> and the returned plug-in is an instance of
	 * a subclass of {@link JptPlugin}.
	 */
	protected abstract Class<?> getPreferencesClass();
}
