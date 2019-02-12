/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;

/**
 * Class used to initialize default (non-persistent) preference values.
 * <p>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.core.runtime.preferences</code>.
 */
public class JaxbPreferenceInitializer
	extends AbstractPreferenceInitializer 
{
	@Override
	public void initializeDefaultPreferences() {
		this.getJaxbWorkspace().initializeDefaultPreferences();
	}

	private InternalJaxbWorkspace getJaxbWorkspace() {
		return JptJaxbCorePlugin.instance().getJaxbWorkspace(ResourcesPlugin.getWorkspace());
	}
}
