/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.platform;

import org.eclipse.jpt.common.core.internal.utility.ExtensionPointTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinitionFactory;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

class InternalJaxbPlatformConfig
	implements JaxbPlatformConfig
{
	private final InternalJaxbPlatformManager jaxbPlatformManager;
	private final String id;
	private final String label;
	private final String factoryClassName;
	private /* final */ IProjectFacetVersion jaxbFacetVersion;
	private /* final */ boolean default_ = false;
	private /* final */ InternalJaxbPlatformGroupConfig group;
	private /* final */ String pluginId;

	// lazily initialized
	private JaxbPlatformDefinition jaxbPlatformDefinition;


	InternalJaxbPlatformConfig(InternalJaxbPlatformManager jaxbPlatformManager, String id, String label, String factoryClassName) {
		super();
		this.jaxbPlatformManager = jaxbPlatformManager;
		this.id = id;
		this.label = label;
		this.factoryClassName = factoryClassName;
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

	public String getFactoryClassName() {
		return this.factoryClassName;
	}

	void setJaxbFacetVersion(IProjectFacetVersion jaxbFacetVersion) {
		this.jaxbFacetVersion = jaxbFacetVersion;
	}

	public boolean supportsJaxbFacetVersion(IProjectFacetVersion version) {
		if ( ! version.getProjectFacet().equals(JaxbProject.FACET)) {
			throw new IllegalArgumentException(version.toString());
		}
		return (this.jaxbFacetVersion == null) || this.jaxbFacetVersion.equals(version);
	}

	public boolean isDefault() {
		return this.default_;
	}

	void setDefault(boolean default_) {
		this.default_ = default_;
	}

	public JaxbPlatformGroupConfig getGroupConfig() {
		return this.group;
	}

	void setGroup(InternalJaxbPlatformGroupConfig group) {
		this.group = group;
	}

	public String getPluginId() {
		return this.pluginId;
	}

	void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	synchronized JaxbPlatformDefinition getJaxbPlatformDefinition() {
		if (this.jaxbPlatformDefinition == null) {
			this.jaxbPlatformDefinition = this.buildJaxbPlatformDefinition();
		}
		return this.jaxbPlatformDefinition;
	}

	private JaxbPlatformDefinition buildJaxbPlatformDefinition() {
		JaxbPlatformDefinitionFactory factory = this.buildJaxbPlatformDefinitionFactory();
		return (factory == null) ? null : factory.buildJaxbPlatformDefinition();
	}

	private JaxbPlatformDefinitionFactory buildJaxbPlatformDefinitionFactory() {
		return ExtensionPointTools.instantiate(this.pluginId, this.jaxbPlatformManager.getExtensionPointName(), this.factoryClassName, JaxbPlatformDefinitionFactory.class);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.label);
	}
}
