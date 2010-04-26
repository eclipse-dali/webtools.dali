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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.Bundle;

/**
 *  SchemaGenerator
 */
public class SchemaGenerator
{
	static public String LAUNCH_CONFIG_NAME = "JAXB Schema Gen Run Config";   //$NON-NLS-1$
	static public String JAXB_SCHEMA_GEN_PACKAGE_NAME = "org.eclipse.jpt.jaxb.core.schemagen";   //$NON-NLS-1$
	static public String JAXB_SCHEMA_GEN_CLASS = JAXB_SCHEMA_GEN_PACKAGE_NAME + ".Main";	  //$NON-NLS-1$
	static public String ECLIPSELINK_JAXB_SCHEMA_GEN_PACKAGE_NAME = "org.eclipse.jpt.eclipselink.jaxb.core.schemagen";   //$NON-NLS-1$
	static public String ECLIPSELINK_JAXB_SCHEMA_GEN_CLASS = ECLIPSELINK_JAXB_SCHEMA_GEN_PACKAGE_NAME + ".Main";	  //$NON-NLS-1$
	
	static public String JAXB_SCHEMA_GEN_JAR = JAXB_SCHEMA_GEN_PACKAGE_NAME + "_";	//$NON-NLS-1$
	static public String ECLIPSELINK_JAXB_SCHEMA_GEN_JAR = ECLIPSELINK_JAXB_SCHEMA_GEN_PACKAGE_NAME + "_";	//$NON-NLS-1$
	static public String JAXB_GENERIC_SCHEMA_GEN_CLASS = "javax.xml.bind.JAXBContext";	//$NON-NLS-1$
	static public String PLUGINS_DIR = "plugins/";	  //$NON-NLS-1$
	
	private IVMInstall jre;
	private ILaunchConfigurationWorkingCopy launchConfig;
	private ILaunch launch;
	
	private final IProject project;
	private final String targetSchemaName;
	private final String[] sourceClassNames;
	private final String mainType;
	private final boolean useMoxy;
	private final boolean isDebug = false;

	// ********** static methods **********
	
	public static void generate(
			IProject project, 
			String targetSchemaName, 
			String[] sourceClassNames,
			boolean useMoxy,
			IProgressMonitor monitor) {
		if (project == null) {
			throw new NullPointerException();
		}
		new SchemaGenerator(project, 
			targetSchemaName, 
			sourceClassNames,
			useMoxy,
			monitor).generate();
	}

	// ********** constructors **********
	
	protected SchemaGenerator(
			IProject project, 
			String targetSchemaName, 
			String[] sourceClassNames,
			boolean useMoxy,
			@SuppressWarnings("unused") IProgressMonitor monitor) {
		super();
		this.project = project;
		this.targetSchemaName = targetSchemaName;
		this.sourceClassNames = sourceClassNames;
		this.useMoxy = useMoxy;
		this.mainType = (this.useMoxy) ? 
				ECLIPSELINK_JAXB_SCHEMA_GEN_CLASS :
				JAXB_SCHEMA_GEN_CLASS;
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
		String projectLocation = this.getProject().getLocation().toString();
		
		this.initializeLaunchConfiguration(projectLocation);

		this.addLaunchListener();
		this.launch = this.saveAndLaunchConfig();
	}
	
	private void initializeLaunchConfiguration(String projectLocation) {

		this.specifyJRE(this.jre.getName(), this.jre.getVMInstallType().getId());

		this.specifyProject(this.getProject().getName()); 
		this.specifyMainType(this.mainType);

		this.specifyProgramArguments(
			this.targetSchemaName, 
			this.sourceClassNames);  // -d -c
		this.specifyWorkingDir(projectLocation); 

		String jarName = (this.useMoxy) ? 
					ECLIPSELINK_JAXB_SCHEMA_GEN_JAR :
					JAXB_SCHEMA_GEN_JAR;
		this.specifyClasspathProperties(this.getJavaProject(), this.buildBootstrapJarPath(jarName));
	}

	protected void postGenerate() {
		try {
			if ( ! this.isDebug) {
				this.removeLaunchConfiguration(LAUNCH_CONFIG_NAME);
			}
		}
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	// ********** Launch Configuration Setup **********

	private void specifyClasspathProperties(IJavaProject javaProject, IPath bootstrapJar)  {
		List<String> classpath = new ArrayList<String>();
		try {
			// Schema_Gen jar
			classpath.add(this.getArchiveClasspathEntry(bootstrapJar).getMemento());
			// Default Project classpath
			classpath.add(this.getDefaultProjectClasspathEntry(javaProject).getMemento());
			// System Library  
			classpath.add(this.getSystemLibraryClasspathEntry().getMemento());
		}
		catch (CoreException e) {
			throw new RuntimeException("An error occurs generating a memento", e);	  //$NON-NLS-1$
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
		String targetSchemaName, 
		String[] sourceClassNames) {

		StringBuffer programArguments = new StringBuffer();
		// sourceClassNames
		StringBuffer sourceClassNamesArguments = this.buildClassNamesArguments(sourceClassNames);
		programArguments.append(sourceClassNamesArguments);
		
		// schema
		programArguments.append(" -s ");	  //$NON-NLS-1$
		programArguments.append(targetSchemaName);
		
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}
	
	private StringBuffer buildClassNamesArguments(String[] sourceClassNames) {

		StringBuffer classNamesArguments = new StringBuffer();
		for (String className: sourceClassNames) {
			classNamesArguments.append(" -c ");	  //$NON-NLS-1$
			classNamesArguments.append(className);
		}
		return classNamesArguments;
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
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);

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
					if (launch.equals(SchemaGenerator.this.getLaunch())) {

						SchemaGenerator.this.postGenerate();
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
	
	private IRuntimeClasspathEntry getArchiveClasspathEntry(IPath archivePath) {

		IRuntimeClasspathEntry archiveEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(archivePath);
		archiveEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		
		return archiveEntry;
	}
	
	private IRuntimeClasspathEntry getDefaultProjectClasspathEntry(IJavaProject project) {

		IRuntimeClasspathEntry projectEntry = JavaRuntime.newDefaultProjectClasspathEntry(project);
		projectEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		
		return projectEntry;
	}
	
	protected IProject getProject() {
		return this.project;
	}

	public IJavaProject getJavaProject() {
		return JavaCore.create(this.project);
	}
	
	private IVMInstall getProjectJRE() throws CoreException {
		return JavaRuntime.getVMInstall(this.getJavaProject());
	}
	
	protected ILaunch getLaunch() {
		return this.launch;
	}
	
	protected ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}
	
	// ********** private method **********

	private IPath buildBootstrapJarPath(String jarName) {
		try {
			File jarInstallDir = this.getBundleParentDir(JptJaxbUiPlugin.PLUGIN_ID);

			List<File> result = new ArrayList<File>();
			this.findFile(jarName, jarInstallDir, result);
			if (result.isEmpty()) {
				throw new RuntimeException("Could not find: " + jarName + "#.#.#v###.jar in: " + jarInstallDir);   //$NON-NLS-1$ //$NON-NLS-2$
			}
			File ddlGenJarFile = result.get(0);
			String ddlGenJarPath = ddlGenJarFile.getCanonicalPath();
			return new Path(ddlGenJarPath);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void findFile(String fileName, File directory, List<? super File> list) {
		if(directory.listFiles() == null) {
			throw new RuntimeException("Could not find directory: " + directory);   //$NON-NLS-1$
		}
		for (File file : directory.listFiles()) {
			if (file.getName().startsWith(fileName)) {
				list.add(file);
			}
			if (file.isDirectory()) {
				this.findFile(fileName, file, list);
			}
		}
	} 
	
	private File getBundleParentDir(String bundleName) throws IOException {
	
		if (Platform.inDevelopmentMode()) {
			Location eclipseHomeLoc = Platform.getInstallLocation();
			String eclipseHome = eclipseHomeLoc.getURL().getPath();
			if ( ! eclipseHome.endsWith(PLUGINS_DIR)) {
				eclipseHome += PLUGINS_DIR;
			}
			return new File(eclipseHome);
		}
		Bundle bundle = Platform.getBundle(bundleName);
		return FileLocator.getBundleFile(bundle).getParentFile();
	}

}
