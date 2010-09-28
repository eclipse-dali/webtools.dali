/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformFactory;
import org.eclipse.jpt.core.internal.XPointUtil;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.core.platform.JpaPlatformGroupDescription;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaPlatformDescriptionImpl
		implements JpaPlatformDescription {
	
	private String id;
	private String pluginId;
	private String label;
	private String factoryClassName;
	private IProjectFacetVersion jpaFacetVersion;
	private boolean default_ = false;
	private JpaPlatformGroupDescriptionImpl group;
	
	
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
	
	public IProjectFacetVersion getJpaFacetVersion() {
		return this.jpaFacetVersion;
	}
	
	void setJpaFacetVersion(IProjectFacetVersion jpaFacetVersion) {
		if (! jpaFacetVersion.getProjectFacet().equals(JpaFacet.FACET)) {
			throw new IllegalArgumentException(jpaFacetVersion.toString());
		}
		this.jpaFacetVersion = jpaFacetVersion;
	}
	
	public boolean supportsJpaFacetVersion(IProjectFacetVersion jpaFacetVersion) {
		if (! jpaFacetVersion.getProjectFacet().equals(JpaFacet.FACET)) {
			throw new IllegalArgumentException(jpaFacetVersion.toString());
		}
		return (this.jpaFacetVersion != null) ? this.jpaFacetVersion.equals(jpaFacetVersion) : true;
	}
	
	public boolean isDefault() {
		return this.default_;
	}
	
	void setDefault(boolean default_) {
		this.default_ = default_;
	}
	
	public JpaPlatformGroupDescription getGroup() {
		return this.group;
	}
	
	void setGroup(JpaPlatformGroupDescriptionImpl group) {
		this.group = group;
	}
	
	public JpaPlatform buildJpaPlatform() {
		JpaPlatformFactory factory = (JpaPlatformFactory) XPointUtil.instantiate(
				this.pluginId, JpaPlatformManagerImpl.QUALIFIED_EXTENSION_POINT_ID, 
				this.factoryClassName, JpaPlatformFactory.class);
		return factory.buildJpaPlatform(getId());
	}
	
	@Override
	public String toString() {
		return this.label;
	}
}
