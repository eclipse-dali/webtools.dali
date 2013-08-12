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
import java.util.Properties;
import junit.framework.TestCase;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

/**
 * Provide a little infrastructure for testing the backward-compatibility of
 * preferences
 */
@SuppressWarnings("nls")
public abstract class PreferencesTests
	extends TestCase
{
	private JavaProjectTestHarness javaProjectTestHarness;
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
		this.javaProjectTestHarness = this.buildJavaProjectTestHarness(false); // false = no auto-build
		this.workspacePrefsFilePath = this.getWorkspaceRoot().getLocation().toString() + WORKSPACE_PREFS_DIR_NAME + this.getWorkspacePrefsFileName();
		this.projectPrefsFilePath = this.getProject().getLocation().toString() + PROJECT_PREFS_DIR_NAME + this.getProjectPrefsFileName();
	}

	protected abstract String getWorkspacePrefsFileName();

	protected abstract String getProjectPrefsFileName();

	private JavaProjectTestHarness buildJavaProjectTestHarness(boolean autoBuild) throws Exception {
		return this.buildJavaProjectTestHarness(this.getName(), autoBuild);
	}

	private JavaProjectTestHarness buildJavaProjectTestHarness(String projectName, boolean autoBuild) throws Exception {
		return new JavaProjectTestHarness(projectName, autoBuild);
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
		return this.javaProjectTestHarness.getProject();
	}

	protected Properties readProjectPrefs() throws Exception {
		return this.readProperties(this.projectPrefsFilePath);
	}

	protected Properties readWorkspacePrefs() throws Exception {
		return this.readProperties(this.workspacePrefsFilePath);
	}

	/**
	 * read preferences directly from file
	 */
	protected Properties readProperties(String path) throws Exception {
		Properties properties = new Properties();
		FileInputStream stream = new FileInputStream(path);
		try {
			properties.load(stream);
		} finally {
			stream.close();
		}
		return properties;
	}

	protected void flushWorkspacePrefs() throws Exception {
		JptPlugin plugin = this.getPlugin();
		IEclipsePreferences prefs = (IEclipsePreferences) ObjectTools.execute(plugin, "getWorkspacePreferences");
		prefs.flush();
	}

	protected void flushProjectPrefs() throws Exception {
		JptPlugin plugin = this.getPlugin();
		IEclipsePreferences prefs = (IEclipsePreferences) ObjectTools.execute(plugin, "getProjectPreferences", IProject.class, this.getProject());
		prefs.flush();
	}

	protected JptPlugin getPlugin() throws Exception {
		return (JptPlugin) ClassTools.execute(this.getPreferencesClass(), "getPlugin");
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
