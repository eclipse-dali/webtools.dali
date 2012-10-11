/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform;

import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiFactory;

class JpaPlatformUiConfig {
	private final InternalJpaPlatformUiManager manager;
	private final String id;
	private final String factoryClassName;
	private final String jpaPlatformID;
	private /* final */ String pluginID;

	// lazily initialized
	private JpaPlatformUi jpaPlatformUI;


	JpaPlatformUiConfig(InternalJpaPlatformUiManager manager, String id, String factoryClassName, String jpaPlatformID) {
		super();
		this.manager = manager;
		this.id = id;
		this.factoryClassName = factoryClassName;
		this.jpaPlatformID = jpaPlatformID;
	}

	InternalJpaPlatformUiManager getManager() {
		return this.manager;
	}

	String getID() {
		return this.id;
	}

	String getFactoryClassName() {
		return this.factoryClassName;
	}

	String getJpaPlatformID() {
		return this.jpaPlatformID;
	}

	String getPluginID() {
		return this.pluginID;
	}

	void setPluginID(String pluginID) {
		this.pluginID = pluginID;
	}

	synchronized JpaPlatformUi getJpaPlatformUi() {
		if (this.jpaPlatformUI == null) {
			this.jpaPlatformUI = this.buildJpaPlatformUi();
		}
		return this.jpaPlatformUI;
	}

	private JpaPlatformUi buildJpaPlatformUi() {
		JpaPlatformUiFactory factory = this.buildJpaPlatformUiFactory();
		return (factory == null) ? null : factory.buildJpaPlatformUi();
	}

	private JpaPlatformUiFactory buildJpaPlatformUiFactory() {
		return PlatformTools.instantiate(this.pluginID, this.manager.getExtensionPointName(), this.factoryClassName, JpaPlatformUiFactory.class);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.jpaPlatformID);
	}
}
