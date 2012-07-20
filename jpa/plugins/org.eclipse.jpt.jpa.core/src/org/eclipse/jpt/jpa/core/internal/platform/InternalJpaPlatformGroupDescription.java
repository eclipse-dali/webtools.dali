/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.platform;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.SuperIterableWrapper;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformGroupDescription;

class InternalJpaPlatformGroupDescription
	implements JpaPlatformGroupDescription
{
	private final InternalJpaPlatformManager jpaPlatformManager;
	private final String id;
	private final String label;
	private String pluginId;

	// not sure why we hold these...
	private ArrayList<InternalJpaPlatformDescription> platformDescriptions = new ArrayList<InternalJpaPlatformDescription>();


	InternalJpaPlatformGroupDescription(InternalJpaPlatformManager jpaPlatformManager, String id, String label) {
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

	void addPlatform(InternalJpaPlatformDescription platform) {
		this.platformDescriptions.add(platform);
	}

	public Iterable<JpaPlatformDescription> getJpaPlatformDescriptions() {
		return new SuperIterableWrapper<JpaPlatformDescription>(this.platformDescriptions);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.label);
	}
}
