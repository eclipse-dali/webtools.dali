/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.platform;

import org.eclipse.jpt.common.core.internal.XPointUtil;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;

public class JaxbPlatformUiConfig {
	
	private String id;
	private String pluginId;
	private JaxbPlatformDescription jaxbPlatform;
	private String className;
	private JaxbPlatformUi platformUi;
	
	
	JaxbPlatformUiConfig() {
		super();
	}
	
	
	public String getId() {
		return this.id;
	}
	
	void setId(String id) {
		this.id = id;
	}
	
	public String getPluginId() {
		return this.pluginId;
	}
	
	void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}
	
	public JaxbPlatformDescription getJaxbPlatform() {
		return this.jaxbPlatform;
	}
	
	void setJaxbPlatform(JaxbPlatformDescription jaxbPlatform) {
		this.jaxbPlatform = jaxbPlatform;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	void setClassName(String className) {
		this.className = className;
	}
	
	public JaxbPlatformUi getPlatformUi() {
		if (this.platformUi == null) {
			this.platformUi = XPointUtil.instantiate(
					this.pluginId, JaxbPlatformUiManagerImpl.QUALIFIED_EXTENSION_POINT_ID,
					this.className, JaxbPlatformUi.class);
		}
		return this.platformUi;
	}
}
