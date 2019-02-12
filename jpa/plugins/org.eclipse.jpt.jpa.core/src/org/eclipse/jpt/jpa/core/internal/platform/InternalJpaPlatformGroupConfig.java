/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.platform;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;

class InternalJpaPlatformGroupConfig
	implements JpaPlatform.GroupConfig
{
	private final InternalJpaPlatformManager jpaPlatformManager;
	private final String id;
	private final String label;
	private /* final */ String pluginId;

	// not sure why we hold these...
	private ArrayList<InternalJpaPlatformConfig> platformConfigs = new ArrayList<InternalJpaPlatformConfig>();


	InternalJpaPlatformGroupConfig(InternalJpaPlatformManager jpaPlatformManager, String id, String label) {
		super();
		this.jpaPlatformManager = jpaPlatformManager;
		this.id = id;
		this.label = label;
	}

	public JpaPlatformManager getJpaPlatformManager() {
		return this.jpaPlatformManager;
	}

	public String getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public String getPluginId() {
		return this.pluginId;
	}

	void addPlatform(InternalJpaPlatformConfig platform) {
		this.platformConfigs.add(platform);
	}

	public Iterable<JpaPlatform.Config> getJpaPlatformConfigs() {
		return new SuperIterableWrapper<JpaPlatform.Config>(this.platformConfigs);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.label);
	}
}
