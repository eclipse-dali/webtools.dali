/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.prefs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 *  JpaValidationPreferencesManager
 */
public class JpaValidationPreferencesManager extends JpaPreferencesManager
{
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

	public static final int NO_SEVERITY_PREFERENCE = -1;

	public static final String WORKSPACE_PREFERENCES_OVERRIDEN = "workspace_preferences_overriden"; //$NON-NLS-1$

	// ********** public static methods **********

	/**
	 * Returns only the severity level of a given problem preference.  This does not
	 * include information on whether the problem is ignored.  See isProblemIgnored.
	 * @return an IMessage severity level
	 */
	public static int getProblemSeverityPreference(IResource targetObject, String messageId) {
		String problemPreference = (new JpaValidationPreferencesManager(targetObject.getProject())).
															getPreference(appendProblemPrefix(messageId));

		if(problemPreference == null) {
			return NO_SEVERITY_PREFERENCE;
		} 
		else if (problemPreference.equals(ERROR)) {
			return IMessage.HIGH_SEVERITY;
		} 
		else if (problemPreference.equals(WARNING)) {
			return IMessage.NORMAL_SEVERITY;
		} 
		else if (problemPreference.equals(INFO)) {
			return IMessage.LOW_SEVERITY;
		}
		return NO_SEVERITY_PREFERENCE;
	}

	// ********** workspace preference **********

	/**
	 * Returns the String value of the problem preference from the workspace preferences
	 */
	public static String getWorkspaceLevelProblemPreference(String messageId) {
		return getWorkspacePreference(appendProblemPrefix(messageId));
	}
	
	public static void setWorkspaceLevelProblemPreference(String messageId, String problemPreference) {
		setWorkspacePreference(appendProblemPrefix(messageId), problemPreference);
	}
	
	protected static String appendProblemPrefix(String messageId) {
		return PROBLEM_PREFIX + messageId;
	}

	// ********** implementation **************************************************

	public JpaValidationPreferencesManager(IProject project) {
		
		super(project);
	}

	// ********** behavior **********

	/**
	 * Return whether the specified problem should <em>not</em> be ignored based
	 * on project or workspace preferences.
	 */
	public boolean problemIsNotIgnored(String messageId) {
		return ! problemIsIgnored(messageId);
	}

	/**
	 * Return whether the specified problem should be ignored based on project or
	 * workspace preferences.
	 */
	public boolean problemIsIgnored(String messageId) {
		return Tools.valuesAreEqual(this.getPreference(appendProblemPrefix(messageId)), IGNORE);
	}

	public boolean projectHasSpecificOptions() {
		return this.getProjectPreference(WORKSPACE_PREFERENCES_OVERRIDEN, false);
	}

	public void setProjectHasSpecificOptions(boolean booleanValue) {
		this.setProjectPreference(WORKSPACE_PREFERENCES_OVERRIDEN, booleanValue);
	}
	
	// ********** project preference **********

	/**
	 * Returns the String value of the problem preference from the project preferences
	 */
	public String getProjectLevelProblemPreference(String messageId) {
		return this.getProjectPreference(appendProblemPrefix(messageId));
	}
	
	public void setProjectLevelProblemPreference(String messageId, String problemPreference) {
		this.setProjectPreference(appendProblemPrefix(messageId), problemPreference);
	}
	
}