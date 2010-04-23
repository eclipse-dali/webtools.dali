/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jpt.core.IResourcePart;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.osgi.service.prefs.BackingStoreException;

//TODO:  Probably want to merge the behavior in this class into JptCorePlugin
public class JpaValidationPreferences {

	/*
	 * prefix for all preference strings.  This is only used internally.
	 * Clients of get*LevelProblemPrefernce() and set*LevelProblemPreference 
	 * should not include the prefix.
	 */
	private static final String PROBLEM_PREFIX = "problem."; //$NON-NLS-1$

	public static final String ERROR = "error"; //$NON-NLS-1$
	public static final String WARNING = "warning"; //$NON-NLS-1$
	public static final String INFO = "info"; //$NON-NLS-1$
	public static final String IGNORE = "ignore"; //$NON-NLS-1$

	static final int NO_SEVERITY_PREFERENCE = -1;

	public static final String WORKSPACE_PREFERENCES_OVERRIDEN = "workspace_preferences_overriden"; //$NON-NLS-1$

	/**
	 * Returns only the severity level of a given problem preference.  This does not
	 * include information on whether the problem is ignored.  See isProblemIgnored.
	 * @return an IMessage severity level
	 */
	public static int getProblemSeverityPreference(Object targetObject, String messageId) {
		IProject project = getProject(targetObject);
		String problemPreference = getPreference(project, messageId);
		
		if (problemPreference == null){
			return NO_SEVERITY_PREFERENCE;
		} else if (problemPreference.equals(ERROR)){
			return IMessage.HIGH_SEVERITY;
		} else if (problemPreference.equals(WARNING)){
			return IMessage.NORMAL_SEVERITY;
		} else if (problemPreference.equals(INFO)){
			return IMessage.LOW_SEVERITY;
		}
		return NO_SEVERITY_PREFERENCE;
	}

	private static IProject getProject(Object targetObject) {
		IAdaptable target = (IAdaptable)targetObject;
		IResource resource = ((IResourcePart) target.getAdapter(IResourcePart.class)).getResource();
		IProject project = resource.getProject();
		return project;
	}

	/**
	 * Returns whether or not this problem should be ignored based on project or
	 * workspace preferences
	 */
	public static boolean isProblemIgnored(IProject project, String messageId){
		String problemPreference = getPreference(project, messageId);
		if (problemPreference != null && problemPreference.equals(IGNORE)){
			return true;
		}
		return false;
	}

	private static String getPreference(IProject project, String messageId) {
		String problemPreference = null;
		problemPreference = getProjectLevelProblemPreference(project, messageId);
		//if severity is still null, check the workspace preferences
		if (problemPreference == null) {
			problemPreference = getWorkspaceLevelProblemPreference(messageId);
		}
		return problemPreference;
	}

	/**
	 * Returns the String value of the problem preference from the project preferences
	 */
	public static String getProjectLevelProblemPreference(IProject project, String messageId){
		return getPreference(JptCorePlugin.getProjectPreferences(project), messageId);
	}

	public static void setProjectLevelProblemPreference(IProject project, String messageId, String problemPreference) {
		IEclipsePreferences projectPreferences = JptCorePlugin.getProjectPreferences(project);
		setPreference(projectPreferences, messageId, problemPreference);
		flush(projectPreferences);
	}

	/**
	 * Returns the String value of the problem preference from the workspace preferences
	 */
	public static String getWorkspaceLevelProblemPreference(String messageId){
		return getPreference(JptCorePlugin.getWorkspacePreferences(), messageId);
	}

	public static void setWorkspaceLevelProblemPreference(String messageId, String problemPreference) {
		IEclipsePreferences workspacePreferences = JptCorePlugin.getWorkspacePreferences();
		setPreference(workspacePreferences, messageId, problemPreference);
		flush(workspacePreferences);
	}

	private static String getPreference(IEclipsePreferences preferences, String messageId) {
		return preferences.get(appendProblemPrefix(messageId), null);
	}

	private static void setPreference(IEclipsePreferences preferences, String messageId, String problemPreference) {
		if (problemPreference == null){
			preferences.remove(appendProblemPrefix(messageId));
		}
		else {
			preferences.put(appendProblemPrefix(messageId), problemPreference);
		}
	}

	private static String appendProblemPrefix(String messageId) {
		return PROBLEM_PREFIX + messageId;
	}

	private static void flush(IEclipsePreferences prefs) {
		try {
			prefs.flush();
		} catch(BackingStoreException ex) {
			JptCorePlugin.log(ex);
		}
	}
}
