/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.dbws.eclipselink.core.internal.gen.DbwsGenerator;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen.DbwsGeneratorWizard;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.persistence.oxm.XMLContext;
import org.eclipse.persistence.oxm.XMLUnmarshaller;
import org.eclipse.persistence.tools.dbws.DBWSBuilderModel;
import org.eclipse.persistence.tools.dbws.DBWSBuilderModelProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 *  DbwsGeneratorUi
 */
public class DbwsGeneratorUi
{
	private final IJavaProject javaProject;
	private final String builderXmlFile;

	private static final String WEB_FACET_ID = "jst.web";		//$NON-NLS-1$
	private static final String DRIVER_PROPERTY = "driver";		//$NON-NLS-1$
	private static final String DBWS_BUILDER_CLASS_NAME = "org.eclipse.persistence.tools.dbws.DBWSBuilder";		//$NON-NLS-1$

	// ********** static methods **********

	public static void generate(IFile xmlFile) {
		IProject project = xmlFile.getProject();
		
		if( ! projectIsWebDynamic(project)) {
			throw new RuntimeException(JptDbwsUiMessages.DbwsGeneratorUi_notWebDynamicProject);
		}
		IPath xmlPath = xmlFile.getProjectRelativePath();
		
		new DbwsGeneratorUi(project, xmlPath.toOSString()).generate();
	}

	public static boolean projectIsWebDynamic(IProject project) {

		 IProjectFacet projectFacet = ProjectFacetsManager.getProjectFacet(WEB_FACET_ID);
         IFacetedProject facetedProject = null;
         try {
             facetedProject = ProjectFacetsManager.create(project);
         } 
         catch (CoreException e) {
             return false;
         }
         return (facetedProject == null) ? false : facetedProject.hasProjectFacet(projectFacet);
    }
    
	public static IPath getWebContentPath(IProject project){
		IVirtualComponent component = ComponentCore.createComponent(project);
		IPath modulePath = component.getRootFolder().getProjectRelativePath();
		return modulePath;
	}

	public static boolean displayDBWSNotOnClasspathWarning(Shell shell) {

		String msg = JptDbwsUiMessages.DbwsGeneratorUi_dbwsNotOnClasspathMessage;

		return MessageDialog.openQuestion(
			shell,
			JptDbwsUiMessages.DbwsGeneratorUi_runningDbwsWarningTitle,
			msg);
	}

	public static boolean displayOverridingWebContentWarning(Shell shell) {

		String msg = JptDbwsUiMessages.DbwsGeneratorUi_runningDbwsWarningMessage;

		return MessageDialog.openQuestion(
			shell,
			JptDbwsUiMessages.DbwsGeneratorUi_runningDbwsWarningTitle,
			msg);
	}
    
	// ********** constructors **********
    
	private DbwsGeneratorUi(IProject webProject, String builderXmlFile) {
		super();
		if(webProject == null || StringTools.stringIsEmpty(builderXmlFile)) {
			throw new NullPointerException();
		}
		this.javaProject = this.getJavaProjectFrom(webProject);
		if(this.javaProject == null) {
			throw new RuntimeException(JptDbwsUiMessages.DbwsGeneratorUi_notJavaProject);
		}
		this.builderXmlFile = builderXmlFile;
	}

	// ********** generate **********
	/**
	 * prompt the user with a wizard
	 */
	protected void generate() {

		if( ! this.dbwsIsOnClasspath()) {
			if( ! displayDBWSNotOnClasspathWarning(this.getCurrentShell())) {
				return;
			}
		}
		if(this.classIsOnProjectsClasspath(
							this.getDriverNameFrom(this.builderXmlFile))) {

			if(displayOverridingWebContentWarning(this.getCurrentShell())) {
				this.scheduleGenerateDbwsJob();
			}
			return;
		}
		this.openWizardDialog();
		return;
	}

	// ********** internal methods **********

	private void openWizardDialog() {

		DbwsGeneratorWizard wizard = new DbwsGeneratorWizard(this.javaProject, this.builderXmlFile);
		wizard.setWindowTitle(JptDbwsUiMessages.DbwsGeneratorWizard_title);
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if(returnCode != Window.OK) {
			return;
		}
	}
	
	private String getDriverNameFrom(String builderXmlFile) {
		try {
			DBWSBuilderModel model = this.buildBuilderModel(builderXmlFile);
			Map<String, String> properties = model.getProperties();
			return properties.get(DRIVER_PROPERTY);
		}
		catch(Exception e) {
			return null;
		}
	}

	private DBWSBuilderModel buildBuilderModel(String builderFileName) {
		String projectDirName = this.javaProject.getProject().getLocation().toOSString();
    	File builderFile = this.getBuilderFile(projectDirName + File.separator + builderFileName);
		return this.unmarshal(builderFile);
	}

	private DBWSBuilderModel unmarshal(File builderFile) {
        if((builderFile == null) || ( ! builderFile.exists())) {
        	return null;
        }
		XMLContext context = new XMLContext(new DBWSBuilderModelProject());
        XMLUnmarshaller unmarshaller = context.createUnmarshaller();
        
        DBWSBuilderModel model = (DBWSBuilderModel)unmarshaller.unmarshal(builderFile);
        if(model == null || model.properties.size() == 0) {
            return null;
        }
        return model;
	}

	private File getBuilderFile(String builderFileName) {

        File builderFile = new File(builderFileName);
        if( ! (builderFile.exists() && builderFile.isFile())) {
        	return null;
        }
        return builderFile;
	}

	/**
	 * Test if the given class is on the project's classpath.
	 */
	private boolean classIsOnProjectsClasspath(String driverName) {
		try {
			if(StringTools.stringIsEmpty(driverName)) {
				return false;
			}
			IType genClass = this.javaProject.findType(driverName);
			return (genClass != null);
		} 
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Test if the DBWS builder is on the project's classpath.
	 */
	private boolean dbwsIsOnClasspath() {
		try {
			IType genClass = this.javaProject.findType(DBWS_BUILDER_CLASS_NAME);
			return (genClass != null);
		} 
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
		
	private IJavaProject getJavaProjectFrom(IProject project) {
		return (IJavaProject)((IJavaElement)((IAdaptable)project).getAdapter(IJavaElement.class));
	}

	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}

	private void scheduleGenerateDbwsJob() {

		String stageDirName = this.javaProject.getProject().getLocation().toOSString();
		
		WorkspaceJob generateJob = new GenerateDbwsJob(
			this.javaProject,
			this.builderXmlFile,
			stageDirName,
			null		// driverJarList
		);
		generateJob.schedule();	
	}

	// ********** generate DBWS job **********

	public static class GenerateDbwsJob extends WorkspaceJob {
		private final IJavaProject project;
		final String builderFileName;
		final String stageDirName;
		final String driverJarList;

		public GenerateDbwsJob(IJavaProject project, String builderFileName, String stageDirName, String driverJarList) {
			super(JptDbwsUiMessages.DbwsGeneratorWizard_generatingDbws);

			this.project = project ;
			this.builderFileName = builderFileName;
			this.stageDirName = stageDirName;
			this.driverJarList = driverJarList;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			DbwsGenerator.generate(this.project, this.builderFileName, this.stageDirName, this.driverJarList, monitor);
			return Status.OK_STATUS;
		}
		
	}
}
