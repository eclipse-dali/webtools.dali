/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.platform;

import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;

class JaxbPlatformUiConfig {
	private final InternalJaxbPlatformUiManager manager;
	private final String id;
	private final String className;
	private final String jaxbPlatformID;
	private /* final */ String pluginID;

	// lazily initialized
	private JaxbPlatformUi jaxbPlatformUI;


	JaxbPlatformUiConfig(InternalJaxbPlatformUiManager manager, String id, String className, String jaxbPlatformID) {
		super();
		this.manager = manager;
		this.id = id;
		this.className = className;
		this.jaxbPlatformID = jaxbPlatformID;
	}

	InternalJaxbPlatformUiManager getManager() {
		return this.manager;
	}

	String getID() {
		return this.id;
	}

	String getClassName() {
		return this.className;
	}

	String getJaxbPlatformID() {
		return this.jaxbPlatformID;
	}

	String getPluginID() {
		return this.pluginID;
	}

	void setPluginID(String pluginID) {
		this.pluginID = pluginID;
	}

	synchronized JaxbPlatformUi getJaxbPlatformUi() {
		if (this.jaxbPlatformUI == null) {
			this.jaxbPlatformUI = this.buildJaxbPlatformUi();
		}
		return this.jaxbPlatformUI;
	}

	private JaxbPlatformUi buildJaxbPlatformUi() {
		return PlatformTools.instantiate(this.pluginID, this.manager.getExtensionPointName(), this.className, JaxbPlatformUi.class);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.jaxbPlatformID);
	}
}
