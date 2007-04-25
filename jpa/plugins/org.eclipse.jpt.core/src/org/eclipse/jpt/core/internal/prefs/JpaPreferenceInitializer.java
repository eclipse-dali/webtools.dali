/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jpt.core.internal.JptCorePlugin;

/**
 * Class used to initialize default preference values.
 */
public class JpaPreferenceInitializer extends AbstractPreferenceInitializer 
{
	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = new DefaultScope().getNode(JptCorePlugin.getPlugin().getBundle().getSymbolicName());
		
		// default JPA library
		node.put(JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB, "");
	}
}
