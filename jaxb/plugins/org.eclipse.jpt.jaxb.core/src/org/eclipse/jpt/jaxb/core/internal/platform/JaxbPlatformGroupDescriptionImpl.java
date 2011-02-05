/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.platform;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.SuperIterableWrapper;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupDescription;


public class JaxbPlatformGroupDescriptionImpl
		implements JaxbPlatformGroupDescription {
	
	private String id;
	private String pluginId;
	private String label;
	private Map<String, JaxbPlatformDescriptionImpl> platforms;
	
	
	JaxbPlatformGroupDescriptionImpl() {
		this.platforms = new HashMap<String, JaxbPlatformDescriptionImpl>();
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
	
	public String getLabel() {
		return this.label;
	}
	
	void setLabel(String label) {
		this.label = label;
	}
	
	void addPlatform(JaxbPlatformDescriptionImpl platform) {
		this.platforms.put(platform.getId(), platform);
	}
	
	public Iterable<JaxbPlatformDescription> getPlatforms() {
		return new SuperIterableWrapper<JaxbPlatformDescription>(CollectionTools.collection(this.platforms.values()));
	}
	
	@Override
	public String toString() {
		return this.label;
	}
}
