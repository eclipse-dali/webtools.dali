/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.platform;

import org.eclipse.jpt.common.core.internal.utility.XPointTools;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinitionFactory;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupDescription;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JaxbPlatformDescriptionImpl
		implements JaxbPlatformDescription {
	
	private String id;
	private String pluginId;
	private String label;
	private String factoryClassName;
	private IProjectFacetVersion jaxbFacetVersion;
	private boolean default_ = false;
	private JaxbPlatformGroupDescriptionImpl group;
	
	
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
	
	public String getFactoryClassName() {
		return this.factoryClassName;
	}
	
	void setFactoryClassName(String className) {
		this.factoryClassName = className;
	}
	
	public IProjectFacetVersion getJaxbFacetVersion() {
		return this.jaxbFacetVersion;
	}
	
	void setJaxbFacetVersion(IProjectFacetVersion jaxbFacetVersion) {
		if (! jaxbFacetVersion.getProjectFacet().equals(JaxbFacet.FACET)) {
			throw new IllegalArgumentException(jaxbFacetVersion.toString());
		}
		this.jaxbFacetVersion = jaxbFacetVersion;
	}
	
	public boolean supportsJaxbFacetVersion(IProjectFacetVersion jaxbFacetVersion) {
		if (! jaxbFacetVersion.getProjectFacet().equals(JaxbFacet.FACET)) {
			throw new IllegalArgumentException(jaxbFacetVersion.toString());
		}
		return (this.jaxbFacetVersion != null) ? this.jaxbFacetVersion.equals(jaxbFacetVersion) : true;
	}
	
	public boolean isDefault() {
		return this.default_;
	}
	
	void setDefault(boolean default_) {
		this.default_ = default_;
	}
	
	public JaxbPlatformGroupDescription getGroup() {
		return this.group;
	}
	
	void setGroup(JaxbPlatformGroupDescriptionImpl group) {
		this.group = group;
	}
	
	public JaxbPlatformDefinition buildJaxbPlatformDefinition() {
		JaxbPlatformDefinitionFactory factory = XPointTools.instantiate(
				this.pluginId, JaxbPlatformManagerImpl.QUALIFIED_EXTENSION_POINT_ID, 
				this.factoryClassName, JaxbPlatformDefinitionFactory.class);
		return factory.buildJaxbPlatformDefinition();
	}
	
	@Override
	public String toString() {
		return this.label;
	}
}
