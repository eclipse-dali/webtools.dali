/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.operations;

import java.util.Set;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.operations.AbstractJptFileCreationDataModelProvider;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JaxbPreferences;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.jpt.jaxb.eclipselink.core.JptJaxbEclipseLinkCoreMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.plugin.JptJaxbEclipseLinkCorePlugin;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;

public class OxmFileCreationDataModelProvider
		extends AbstractJptFileCreationDataModelProvider
		implements OxmFileCreationDataModelProperties {
	
	public OxmFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public IDataModelOperation getDefaultOperation() {
		return new OxmFileCreationOperation(getDataModel());
	}
	
	@Override
	public Set<String> getPropertyNames() {
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(VERSION);
		propertyNames.add(PACKAGE_NAME);
		return propertyNames;
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(VERSION)) {
			return getDefaultVersion();
		}
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	protected String getDefaultFileName() {
		return "oxm.xml";
	}
	
	protected final String getDefaultVersion() {
		IProject project = this.getProject();
		if (project == null) {
			return null;
		}
		JaxbProject jaxbProject = getJaxbProject_(project);
		JaxbPlatformDefinition jaxbPlatformDef = (jaxbProject != null) ? jaxbProject.getPlatform().getDefinition() : getJaxbPlatformDefinition();
		return jaxbPlatformDef.getMostRecentSupportedResourceType(getContentType()).getVersion();
	}
	
	protected String getPackageName() {
		return getStringProperty(PACKAGE_NAME);
	}
	
	protected IContentType getContentType() {
		return EXmlBindings.CONTENT_TYPE;
	}
	
	protected JaxbPlatformDefinition getJaxbPlatformDefinition() {
		IProject project = getProject();
		return (project == null) ? null : getJaxbPlatformDefinition(project);
	}
	
	protected JaxbPlatformDefinition getJaxbPlatformDefinition(IProject project) {
		String jaxbPlatformID = JaxbPreferences.getJaxbPlatformID(project);
		JaxbPlatformManager jaxbPlatformManager = getJaxbPlatformManager();
		return (jaxbPlatformManager == null) ? null : jaxbPlatformManager.getJaxbPlatformDefinition(jaxbPlatformID);
	}
	
	protected JaxbProject getJaxbProject() {
		return getJaxbProject(getProject());
	}
	
	protected JaxbProject getJaxbProject(IProject project) {
		return (project == null) ? null : getJaxbProject_(project);
	}
	
	protected JaxbProject getJaxbProject_(IProject project) {
		JaxbProjectManager jaxbProjectManager = getJaxbProjectManager();
		return (jaxbProjectManager == null) ? null : jaxbProjectManager.getJaxbProject(project);
	}
	
	protected JaxbPlatformManager getJaxbPlatformManager() {
		JaxbWorkspace jaxbWorkspace = getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbPlatformManager();
	}
	
	protected JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbProjectManager();
	}
	
	protected JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}
	
	
	// ***** validation *****
	
	@Override
	public IStatus validate(String propertyName) {
		IStatus status = super.validate(propertyName);
		if (! status.isOK()) {
			return status;
		}
		
		if (propertyName.equals(PACKAGE_NAME)) {
			status = validatePackageName();
		}
		if (! status.isOK()) {
			return status;
		}
		
		return Status.OK_STATUS;
	}
	
	@Override
	protected IStatus validateContainerPathAndFileName() {
		IStatus status = super.validateContainerPathAndFileName();
		if (! status.isOK()) {
			return status;
		}
		IContainer container = getContainer();
		IProject project = container.getProject();
		if ( ! ProjectTools.hasFacet(project, JaxbProject.FACET)) {
			return JptJaxbEclipseLinkCorePlugin.instance().buildErrorStatus(JptJaxbEclipseLinkCoreMessages.VALIDATE__NEW_OXM_FILE__PROJECT_NOT_JAXB);
		}
		if (! isEclipseLink(project)) {
			return JptJaxbEclipseLinkCorePlugin.instance().buildErrorStatus(JptJaxbEclipseLinkCoreMessages.VALIDATE__NEW_OXM_FILE__PROJECT_NOT_ECLIPSELINK);
		}
		ProjectResourceLocator resourceLocator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
		if ( ! resourceLocator.locationIsValid(container)) {
			return JptJaxbEclipseLinkCorePlugin.instance().buildWarningStatus(JptJaxbEclipseLinkCoreMessages.VALIDATE__NEW_OXM_FILE__CONTAINER_QUESTIONABLE);
		}
		return Status.OK_STATUS;
	}
	
	protected IStatus validatePackageName() {
		String packageName = getPackageName();
		if (StringTools.isBlank(packageName)) {
			return JptJaxbEclipseLinkCorePlugin.instance().buildWarningStatus(JptJaxbEclipseLinkCoreMessages.VALIDATE__NEW_OXM_FILE__PACKAGE_UNSPECIFIED);
		}
		
		IStatus status = JavaConventions.validatePackageName(packageName, JavaCore.VERSION_1_5, JavaCore.VERSION_1_5);
		if (! status.isOK()) {
			return status;
		}
		
		IJavaProject javaProject = getJavaProject();
		if (javaProject != null && IterableTools.isEmpty(JavaProjectTools.getPackageFragments(javaProject, packageName))) {
			return JptJaxbEclipseLinkCorePlugin.instance().buildWarningStatus(JptJaxbEclipseLinkCoreMessages.VALIDATE__NEW_OXM_FILE__PACKAGE_DOESNT_EXIST);
		}
		
		return Status.OK_STATUS;
	}
	
	protected boolean isEclipseLink(IProject project) {
		JaxbPlatformDefinition def = getJaxbPlatformDefinition(project);
		return def != null 
				&& ObjectTools.equals(
						def.getConfig().getGroupConfig(), 
						getJaxbPlatformManager().getJaxbPlatformGroupConfig("eclipselink"));
	}
}
