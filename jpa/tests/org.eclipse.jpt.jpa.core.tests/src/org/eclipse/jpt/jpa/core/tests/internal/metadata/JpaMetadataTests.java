/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.metadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;

/**
 *  JpaMetadataTests
 */
public abstract class JpaMetadataTests extends AnnotationTestCase {
	
	private static final String SETTINGS_DIR = ".settings/";
	private static final String ECLIPSE_CORE_SETTINGS_DIR = ".metadata/.plugins/org.eclipse.core.runtime/" + SETTINGS_DIR;
	public static final String PREFS_FILE_NAME = JptJpaCorePlugin.LEGACY_PLUGIN_ID + ".prefs";
	
	protected IEclipsePreferences workspacePreferences;
	private String eclipseCoreSettingsDir;
	private String projectSettingsDir;

	// ********** constructor **********

	protected JpaMetadataTests(String name) {
		super(name);
	}

	// ********** overrides **********

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		IPath workspacePath = this.getWorkspaceRoot().getLocation();
		this.eclipseCoreSettingsDir = workspacePath + "/" + ECLIPSE_CORE_SETTINGS_DIR;

		IPath projectPath = this.javaProject.getProject().getLocation();
		this.projectSettingsDir = projectPath + "/" + SETTINGS_DIR;
	}

	// ********** preferences **********
	
	protected Map<String, String> getWorkspacePrefs() {
		return this.getSettings(this.eclipseCoreSettingsDir + PREFS_FILE_NAME); 
	}
	
	protected Map<String, String> getProjectPrefs() {
		return this.getSettings(this.projectSettingsDir + PREFS_FILE_NAME); 
	}

	// ********** internal methods **********
	
	private IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	private Map<String, String> getSettings(String settingsPath) {
		Set<Entry<Object, Object>> settingsSet = null;
		try {
			settingsSet = this.loadMetadata(settingsPath);
		}
		catch (IOException e) {
			throw new RuntimeException("Missing: " + settingsPath, e);
		}

		Map<String, String> settings = new HashMap<String, String>();
		for(Entry<Object, Object> property : settingsSet) {
			settings.put((String)property.getKey(), (String)property.getValue());
		}
		return settings;
	}

    private Set<Entry<Object, Object>> loadMetadata(String metadataPath) throws IOException {
        FileInputStream stream = new FileInputStream(metadataPath);
        
        Properties properties = new Properties();
        properties.load(stream);
        stream.close();
        
        return properties.entrySet();
	}
	
}
