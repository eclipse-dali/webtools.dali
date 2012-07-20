/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;

/**
 * Class used to initialize default (non-persistent) preference values.
 * <p>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.preferences</code>.
 */
public class JpaPreferenceInitializer
	extends AbstractPreferenceInitializer 
{
	@Override
	public void initializeDefaultPreferences() {
		JpaPreferences.initializeDefaultPreferences();
		this.getJpaWorkspace().initializeDefaultPreferences();
	}

	private InternalJpaWorkspace getJpaWorkspace() {
		return JptJpaCorePlugin.instance().getJpaWorkspace(ResourcesPlugin.getWorkspace());
	}
}
