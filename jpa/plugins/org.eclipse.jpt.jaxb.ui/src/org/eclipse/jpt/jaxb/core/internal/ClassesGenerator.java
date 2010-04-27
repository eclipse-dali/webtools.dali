/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 *  ClassesGenerator
 */
public class ClassesGenerator
{
	static public String LAUNCH_CONFIG_NAME = "JAXB Run Config";   //$NON-NLS-1$
	static public String JAXB_GENERIC_GEN_CLASS = "com.sun.tools.xjc.XJCFacade";   //$NON-NLS-1$
	static public String JAXB_ECLIPSELINK_GEN_CLASS = "org.eclipse.persistence.jaxb.xjc.MOXyXJC";   //$NON-NLS-1$
	
	private IVMInstall jre;
	private ILaunchConfigurationWorkingCopy launchConfig;
	private ILaunch launch;
	
	private final IJavaProject javaProject;
	private final String xmlSchemaName;
	private final String outputDir;
	private final String targetPackage;
	private final String catalog;
	private final String[] bindingsFileNames;
	private final String mainType;
	private final boolean isDebug = false;

	// ********** static methods **********
	
	public static void generate(
			IJavaProject javaProject, 
			String xmlSchemaName, 
			String outputDir, 
			String targetPackage, 
			String catalog, 
			boolean useMoxyGenerator,
			String[] bindingsFileNames,
			IProgressMonitor monitor) {
		if (javaProject == null) {
			throw new NullPointerException();
		}
		new ClassesGenerator(javaProject, 
			xmlSchemaName, 
			outputDir, 
			targetPackage, 
			catalog, 
			useMoxyGenerator, 
			bindingsFileNames, 
			monitor).generate();
	}

	// ********** constructors **********
	
	protected ClassesGenerator(
			IJavaProject javaProject, 
			String xmlSchemaName, 
			String outputDir, 
			String targetPackage, 
			String catalog, 
			boolean useMoxyGenerator, 
			String[] bindingsFileNames,
			@SuppressWarnings("unused") IProgressMonitor monitor) {
		super();
		this.javaProject = javaProject;
		this.xmlSchemaName = xmlSchemaName;
		this.outputDir = outputDir;
		this.targetPackage = targetPackage;
		this.catalog = catalog;
		this.bindingsFileNames = bindingsFileNames;
		this.mainType = (useMoxyGenerator) ? JAXB_ECLIPSELINK_GEN_CLASS : JAXB_GENERIC_GEN_CLASS;

		this.initialize();
	}

	// ********** behavior **********
	
	protected void initialize() {
		try {
			this.jre = this.getProjectJRE();
			if (this.jre == null) {
				String message = "Could not identify the VM.";   //$NON-NLS-1$
				throw new RuntimeException(message);
			}
			this.launchConfig = this.buildLaunchConfiguration();
		} 
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	protected void generate() {
		String projectLocation = this.javaProject.getProject().getLocation().toString();
		
		this.initializeLaunchConfiguration(projectLocation);

		this.addLaunchListener();
		this.launch = this.saveAndLaunchConfig();
	}
	
	private void initializeLaunchConfiguration(String projectLocation) {

		this.specifyJRE(this.jre.getName(), this.jre.getVMInstallType().getId());

		this.specifyProject(this.javaProject.getProject().getName()); 
		this.specifyMainType(this.mainType);

		this.specifyProgramArguments(
			this.xmlSchemaName, 
			this.outputDir, 
			this.targetPackage, 
			this.catalog, 
			this.bindingsFileNames);  // -d -p
		this.specifyWorkingDir(projectLocation); 

		this.specifyClasspathProperties(this.javaProject);
	}

	protected void postGenerate() {
		try {
			if ( ! this.isDebug) {
				this.removeLaunchConfiguration(LAUNCH_CONFIG_NAME);
			}
			this.javaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	// ********** Launch Configuration Setup **********

	private void specifyClasspathProperties(IJavaProject project)  {
		List<String> classpath = new ArrayList<String>();
		try {
			// Default Project classpath
			classpath.add(this.getDefaultProjectClasspathEntry(project).getMemento());
			// System Library  
			classpath.add(this.getSystemLibraryClasspathEntry().getMemento());
		}
		catch (CoreException e) {
			throw new RuntimeException("An error occurs generating a memento", e);
		}
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpath);
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
	}
	
	private void specifyJRE(String jreName, String vmId) {

		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, jreName);
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, vmId);
	}
	
	private void specifyProject(String projectName) {

		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
	}
	
	private void specifyMainType(String mainType) {

		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, mainType);
	}
	
	private void specifyProgramArguments(
		String xmlSchemaName, 
		String outputDir,
		String targetPackage, 
		String catalog,
		String[] bindingsFileNames) {

		StringBuffer programArguments = new StringBuffer();
		// options
		programArguments.append("-d ");	  //$NON-NLS-1$
		programArguments.append(outputDir);
		if( ! StringTools.stringIsEmpty(targetPackage)) {
			programArguments.append(" -p ");	  //$NON-NLS-1$
			programArguments.append(targetPackage);
		}
		if( ! StringTools.stringIsEmpty(catalog)) {
			programArguments.append(" -catalog ");	  //$NON-NLS-1$
			programArguments.append(catalog);
		}
		// schema
		programArguments.append(" ");	  //$NON-NLS-1$
		programArguments.append(xmlSchemaName);
		
		// bindings
		if( bindingsFileNames.length > 0) {
			for(String bindingsFileName: bindingsFileNames) {
				programArguments.append(" -b ");	  //$NON-NLS-1$
				programArguments.append(bindingsFileName);
			}
		}
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}
	
	private void specifyWorkingDir(String projectLocation) {
		
		File workingDir = new Path(projectLocation).toFile();
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, workingDir.getAbsolutePath());
	}
	
	// ********** LaunchConfig **********

	private ILaunchConfigurationWorkingCopy buildLaunchConfiguration() throws CoreException {
		ILaunchConfigurationWorkingCopy launchConfig = null;
		this.removeLaunchConfiguration(LAUNCH_CONFIG_NAME);

		ILaunchManager manager = getLaunchManager();
		ILaunchConfigurationType type = getLaunchManager().getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);

		launchConfig = type.newInstance(null, LAUNCH_CONFIG_NAME);
		return launchConfig;
	}

	private void removeLaunchConfiguration(String launchConfigurationName) throws CoreException {

		ILaunchManager manager = getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
	
		ILaunchConfiguration[] configurations = manager.getLaunchConfigurations(type);
				for (int i = 0; i < configurations.length; i++) {
			ILaunchConfiguration configuration = configurations[i];
			if (configuration.getName().equals(launchConfigurationName)) {
				configuration.delete();
				break;
			}
		}
	}
	
	private ILaunch saveAndLaunchConfig() {
		ILaunchConfiguration configuration = null;
		ILaunch result = null;
		try {
			configuration = this.launchConfig.doSave();
		}
		catch (CoreException saveException) {
			throw new RuntimeException("Could not save LaunchConfig", saveException);
		}
		 try {
			result = configuration.launch(ILaunchManager.RUN_MODE, new NullProgressMonitor());
		}
		catch (CoreException lauchException) {
			throw new RuntimeException("An error occured during launch", lauchException);
		}
		return result;
	}
	
	private void addLaunchListener() {

		this.getLaunchManager().addLaunchListener(this.buildLaunchListener());
	}

	private ILaunchesListener2 buildLaunchListener() {
		return new ILaunchesListener2() {
			
			public void launchesTerminated(ILaunch[] launches) {
				for (int i = 0; i < launches.length; i++) {
					ILaunch launch = launches[i];
					if (launch.equals(ClassesGenerator.this.getLaunch())) {

						ClassesGenerator.this.postGenerate();
						return;
					}
				}
			}

			public void launchesAdded(ILaunch[] launches) {
				// not interested to this event
			}

			public void launchesChanged(ILaunch[] launches) {
				// not interested to this event
			}

			public void launchesRemoved(ILaunch[] launches) {
				// not interested to this event
			}
		};
	}
	
	// ********** Queries **********

	private IRuntimeClasspathEntry getSystemLibraryClasspathEntry() throws CoreException {

		IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
		return JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath, IRuntimeClasspathEntry.STANDARD_CLASSES);
	}
	
	private IRuntimeClasspathEntry getDefaultProjectClasspathEntry(IJavaProject project) {

		IRuntimeClasspathEntry projectEntry = JavaRuntime.newDefaultProjectClasspathEntry(project);
		projectEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		
		return projectEntry;
	}
	
	private IVMInstall getProjectJRE() throws CoreException {
		return JavaRuntime.getVMInstall(this.javaProject);
	}
	
	protected ILaunch getLaunch() {
		return this.launch;
	}
	
	protected ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}
}
