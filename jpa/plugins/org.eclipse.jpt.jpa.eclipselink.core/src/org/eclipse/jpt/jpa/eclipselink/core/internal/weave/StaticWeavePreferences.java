/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.weave;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.osgi.service.prefs.BackingStoreException;

/**
 *  StaticWeavePreferences
 *  	Project level preference
 */
public class StaticWeavePreferences
{
	private static final String STATIC_WEAVE_PREFIX = "staticweave."; //$NON-NLS-1$

	public static final String SOURCE = "SOURCE"; //$NON-NLS-1$
	public static final String TARGET = "TARGET"; //$NON-NLS-1$
	public static final String LOG_LEVEL = "LOG_LEVEL"; //$NON-NLS-1$
	public static final String PERSISTENCE_INFO = "PERSISTENCE_INFO"; //$NON-NLS-1$
	
	public static String getPreference(IProject project, String messageId) {
		return getPreference(JptJpaCorePlugin.getProjectPreferences(project), messageId);
	}

	public static void setPreference(IProject project, String messageId, String staticWeavePreference) {
		IEclipsePreferences projectPreferences = JptJpaCorePlugin.getProjectPreferences(project);
		setPreference_(projectPreferences, messageId, staticWeavePreference);
		flush(projectPreferences);
	}

	private static void setPreference_(IEclipsePreferences preferences, String messageId, String staticWeavePreference) {
		if(StringTools.stringIsEmpty(staticWeavePreference)) {
			preferences.remove(appendStaticWeavePrefix(messageId));
		}
		else {
			preferences.put(appendStaticWeavePrefix(messageId), staticWeavePreference);
		}
	}

	private static String getPreference(IEclipsePreferences preferences, String messageId) {
		return preferences.get(appendStaticWeavePrefix(messageId), null);
	}

	private static String appendStaticWeavePrefix(String messageId) {
		return STATIC_WEAVE_PREFIX + messageId;
	}

	public static void flush(IEclipsePreferences prefs) {
		try {
			prefs.flush();
		} catch(BackingStoreException ex) {
			JptJpaCorePlugin.log(ex);
		}
	}
}
