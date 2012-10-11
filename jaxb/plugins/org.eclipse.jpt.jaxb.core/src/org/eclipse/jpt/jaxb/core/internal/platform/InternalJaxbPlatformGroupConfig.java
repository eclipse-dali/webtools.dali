/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.platform;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;

class InternalJaxbPlatformGroupConfig
	implements JaxbPlatformGroupConfig
{
	private final InternalJaxbPlatformManager jaxbPlatformManager;
	private final String id;
	private final String label;
	private /* final */ String pluginId;

	// not sure why we hold these...
	private ArrayList<InternalJaxbPlatformConfig> platformConfigs = new ArrayList<InternalJaxbPlatformConfig>();


	InternalJaxbPlatformGroupConfig(InternalJaxbPlatformManager jaxbPlatformManager, String id, String label) {
		super();
		this.jaxbPlatformManager = jaxbPlatformManager;
		this.id = id;
		this.label = label;
	}

	public JaxbPlatformManager getJaxbPlatformManager() {
		return this.jaxbPlatformManager;
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

	void addPlatform(InternalJaxbPlatformConfig platform) {
		this.platformConfigs.add(platform);
	}

	public Iterable<JaxbPlatformConfig> getJaxbPlatformConfigs() {
		return new SuperIterableWrapper<JaxbPlatformConfig>(this.platformConfigs);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.label);
	}
}
