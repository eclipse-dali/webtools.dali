/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal;

import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetDataModelProperties;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetInstallDataModelProvider;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.tests.internal.projects.TestJaxbProject;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public class JaxbTestCase
		extends AnnotationTestCase {
	
	protected static final String BASE_PROJECT_NAME = "JaxbTestProject";
	
	protected JaxbTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return buildJaxbProject(BASE_PROJECT_NAME, autoBuild, buildJaxbFacetInstallConfig());
	}
	
	protected TestJaxbProject buildJaxbProject(
			String projectName, boolean autoBuild, IDataModel jaxbConfig) 
			throws Exception {
		return TestJaxbProject.buildJaxbProject(projectName, autoBuild, jaxbConfig);
	}
	
	protected IDataModel buildJaxbFacetInstallConfig() {
		IDataModel config = DataModelFactory.createDataModel(new JaxbFacetInstallDataModelProvider());		
		config.setProperty(IFacetDataModelProperties.FACET_VERSION, getProjectFacetVersion());
		config.setProperty(JaxbFacetDataModelProperties.PLATFORM, getPlatform());
		return config;
	}
	
	protected JaxbPlatformDescription getPlatform() {
		return JptJaxbCorePlugin.getDefaultPlatform(getProjectFacetVersion());
	}
	
	protected IProjectFacetVersion getProjectFacetVersion() {
		return JaxbFacet.VERSION_2_1;
	}
	
	@Override
	protected TestJaxbProject getJavaProject() {
		return (TestJaxbProject) super.getJavaProject();
	}
	
	protected JaxbProject getJaxbProject() {
		return this.getJavaProject().getJaxbProject();
	}
}
