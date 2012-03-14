/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.prefs;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.internal.prefs.JpaPreferencesManager;
/**
 *  JpaEntityGenPreferencesManager
 */
public class JpaEntityGenPreferencesManager extends JpaPreferencesManager
{
	// ********** public constants **********
	
	private static final String ENTITY_GEN_PREFIX = "entitygen."; //$NON-NLS-1$

	public static final String DEFAULT_PACKAGE = "DEFAULT_PACKAGE"; //$NON-NLS-1$
	
	public static final String DEFAULT_PACKAGE_NAME = "model"; //$NON-NLS-1$

	// ********** static methods **************************************************

	public static String getDefaultPackageWorkspacePreference() {
		return getLegacyWorkspacePreference(appendPrefix(DEFAULT_PACKAGE), getDefaultDefaultPackage());
	}
	
	public static void setDefaultPackageWorkspacePreference(String value) {
		if(StringTools.stringsAreEqual(value, getDefaultDefaultPackage())) {
			removeLegacyWorkspacePreference(appendPrefix(DEFAULT_PACKAGE));
		}
		else {
			setLegacyWorkspacePreference(appendPrefix(DEFAULT_PACKAGE), value);
		}
	}
	
	public static String getDefaultDefaultPackage() {
		return DEFAULT_PACKAGE_NAME;
	}

	private static String appendPrefix(String prefId) {
		return ENTITY_GEN_PREFIX + prefId;
	}
	
	// ********** implementation **************************************************
	
	public JpaEntityGenPreferencesManager(IProject project) {
		super(project);
	}

	// ********** project preference **********

	public String getDefaultPackagePreference() {
		return this.getLegacyPreference(appendPrefix(DEFAULT_PACKAGE), getDefaultDefaultPackage());
	}

	public void setDefaultPackagePreference(String preferenceValue) {
		if(StringTools.stringsAreEqual(preferenceValue, getDefaultPackageWorkspacePreference())) {
			this.removeDefaultPackagePreference();
		}
		else {
			this.setLegacyProjectPreference(appendPrefix(DEFAULT_PACKAGE), preferenceValue);
		}
	}

	public void removeDefaultPackagePreference() {
		this.removeLegacyProjectPreference(appendPrefix(DEFAULT_PACKAGE));
	}

}
