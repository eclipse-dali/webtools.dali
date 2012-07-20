/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.jpa2.Generic2_0JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class Generic2_0ContextModelTestCase extends ContextModelTestCase
{
	protected Generic2_0ContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, JpaProject2_0.FACET_VERSION_STRING);
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM, this.getJpaPlatformDescription());
		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}

	@Override
	protected JpaPlatformDescription getJpaPlatformDescription() {
		return this.getJpaPlatformManager().getJpaPlatformDescription(Generic2_0JpaPlatformFactory.ID);
	}

	protected JpaPlatformManager getJpaPlatformManager() {
		return this.getJpaWorkspace().getJpaPlatformManager();
	}

	protected JpaWorkspace getJpaWorkspace() {
		return (JpaWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JpaWorkspace.class);
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_0.FACET_VERSION_STRING;
	}
}
