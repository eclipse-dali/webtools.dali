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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetDataModelProperties;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetInstallDataModelProvider;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
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
		config.setProperty(JaxbFacetDataModelProperties.PLATFORM, getPlatformConfig());
		return config;
	}
	
	protected JaxbPlatformConfig getPlatformConfig() {
		return this.getJaxbPlatformManager().getDefaultJaxbPlatformConfig(getProjectFacetVersion());
	}

	protected JaxbPlatformManager getJaxbPlatformManager() {
		return this.getJaxbWorkspace().getJaxbPlatformManager();
	}

	protected JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}

	protected IProjectFacetVersion getProjectFacetVersion() {
		return JaxbFacet.VERSION_2_2;
	}
	
	@Override
	protected TestJaxbProject getJavaProject() {
		return (TestJaxbProject) super.getJavaProject();
	}
	
	protected JaxbProject getJaxbProject() {
		return this.getJavaProject().getJaxbProject();
	}
	
	protected JavaResourceAttribute getFieldNamed(JavaResourceType resourceType, String attributeName) {
		for (JavaResourceAttribute attribute : resourceType.getFields()) {
			if (attribute.getName().equals(attributeName)) {
				return attribute;
			}
		}
		return null;
	}
}
