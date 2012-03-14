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
public class JpaJpqlPreferencesManager extends JpaPreferencesManager
{
	// ********** public constants **********
	
	private static final String JPQL_IDENTIFIER_PREFIX = "jpqlIdentifier.";
	public static final String JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE = "lowercase"; //$NON-NLS-1$
	public static final String JPQL_IDENTIFIER_UPPERCASE_PREF_VALUE = "uppercase"; //$NON-NLS-1$
	
	public static final String CASE = "CASE"; //$NON-NLS-1$
	public static final String DEFAULT_CASE = JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE; //$NON-NLS-1$
	
	public static final String MATCH_FIRST_CHARACTER_CASE = "MATCH_FIRST_CHARACTER_CASE"; //$NON-NLS-1$
	public static final boolean DEFAULT_MATCH_FIRST_CHARACTER_CASE = true; //$NON-NLS-1$

	// ********** workspace preference **********
	
	public static String getIdentifiersCaseWorkspacePreference() {
		return getLegacyWorkspacePreference(appendPrefix(CASE), getDefaultIdentifiersCase());
	}
	
	public static void setIdentifiersCaseWorkspacePreference(String value) {
		if(StringTools.stringsAreEqual(value, getDefaultIdentifiersCase())) {
			removeLegacyWorkspacePreference(appendPrefix(CASE));
		}
		else {
			setLegacyWorkspacePreference(appendPrefix(CASE), value);
		}
	}
	
	public static String getDefaultIdentifiersCase() {
		return DEFAULT_CASE;
	}

	public static boolean getMatchFirstCharacterCaseWorkspacePreference() {
		return getLegacyWorkspacePreference(appendPrefix(MATCH_FIRST_CHARACTER_CASE), getDefaultMatchFirstCharacterCase());
	}
	
	public static void setMatchFirstCharacterCaseWorkspacePreference(boolean value) {
		if(value == getDefaultMatchFirstCharacterCase()) {
			removeLegacyWorkspacePreference(appendPrefix(MATCH_FIRST_CHARACTER_CASE));
		}
		else {
			setLegacyWorkspacePreference(appendPrefix(MATCH_FIRST_CHARACTER_CASE), value);
		}
	}
	
	public static boolean getDefaultMatchFirstCharacterCase() {
		return DEFAULT_MATCH_FIRST_CHARACTER_CASE;
	}

	private static String appendPrefix(String prefId) {
		return JPQL_IDENTIFIER_PREFIX + prefId;
	}

	// ********** constructor **********
	
	public JpaJpqlPreferencesManager(IProject project) {
		super(project);
	}

}
