/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.ddlgen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.validation.JpaHelper;
import org.eclipse.jpt.core.internal.validation.JpaValidator;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.LoggingLevel;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.DdlGenerationType;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.OutputMode;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.wst.validation.internal.operations.ValidatorJob;

/**
 *  EclipseLinkDLLGenerator launches the EclipseLink DDL generator in a separate VM.
 *  For this it will create a Java Configuration named "EclipseLink" 
 *  in the target workspace (note: the configuration will be overridden at each run).
 *  It will than launch the configuration created with the login information from 
 *  the current Dali project, and passes it to EclipseLink.
 *  
 *  Pre-req:
 *  	org.eclipse.jpt.eclipselink.core.ddlgen.jar in ECLIPSE_HOME/plugins
 */
public class EclipseLinkDDLGenerator
{
	static public String ECLIPSELINK_DDL_GEN_JAR_VERSION = "1.0.100";	//$NON-NLS-1$
	static public String LAUNCH_CONFIG_NAME = "EclipseLink";   //$NON-NLS-1$
	static public String DDL_GEN_PACKAGE_NAME = "org.eclipse.jpt.eclipselink.core.ddlgen";   //$NON-NLS-1$
	static public String ECLIPSELINK_DDL_GEN_CLASS = DDL_GEN_PACKAGE_NAME + ".Main";	  //$NON-NLS-1$
	static public String ECLIPSELINK_DDL_GEN_JAR = DDL_GEN_PACKAGE_NAME + "_";	//$NON-NLS-1$
	static public String PROPERTIES_FILE_NAME = "login.properties";	  //$NON-NLS-1$
	static public String ECLIPSE_HOME = "ECLIPSE_HOME";	  //$NON-NLS-1$
	static public String PLUGINS_DIR = "plugins";	  //$NON-NLS-1$
	private IVMInstall jre;
	private ILaunchConfigurationWorkingCopy launchConfig;
	private ILaunch launch;
	
	private String puName;
	private JpaProject jpaProject;
	private String projectLocation;
	private boolean isDebug = false;
	
	// ********** constructors **********
	
	public static void generate(String puName, JpaProject project, String projectLocation, IProgressMonitor monitor) {
		if (puName == null || puName.length() == 0 || project == null) {
			throw new NullPointerException();
		}
		new EclipseLinkDDLGenerator(puName, project, projectLocation, monitor).generate();
	}

	private EclipseLinkDDLGenerator(String puName, JpaProject jpaProject, String projectLocation, IProgressMonitor monitor) {
		super();
		this.puName = puName;
		this.jpaProject = jpaProject;
		this.projectLocation = projectLocation;
		this.initialize();
	}
	
	// ********** Queries **********
	
	protected JpaPlatform getPlatform() {
		return this.jpaProject.getJpaPlatform();
	}
	
	protected ILaunch getLaunch() {
		return this.launch;
	}
	
	protected ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}
	
	private IVMInstall getProjectJRE() throws CoreException {
		
		return JavaRuntime.getVMInstall(this.jpaProject.getJavaProject());
	}

	// ********** behavior **********
	
	protected void initialize() {
		try {
			this.jre = this.getProjectJRE();
			if(this.jre == null) {
				String message = "Could not identify the VM.";
				throw new RuntimeException(message);
			}
			this.launchConfig = this.buildLaunchConfiguration();
		} 
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	protected void generate() {
		this.preGenerate();
		String propertiesFile  = this.projectLocation + "/" + PROPERTIES_FILE_NAME;
		try {
			this.initializeLaunchConfiguration(this.projectLocation, propertiesFile);
	
			this.saveLoginProperties(this.projectLocation, propertiesFile);
	
			this.launch = this.saveAndLaunchConfig();
			this.addLaunchListener();
		} 
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void initializeLaunchConfiguration(String projectLocation, String propertiesFile) throws CoreException {

		this.specifyJRE(this.jre.getName(), this.jre.getVMInstallType().getId());
		
		this.specifyProject(this.jpaProject.getProject().getName()); 
		this.specifyMainType(ECLIPSELINK_DDL_GEN_CLASS);	
		
		this.specifyProgramArguments(this.puName, propertiesFile); 
		this.specifyWorkingDir(projectLocation); 

		this.specifyClasspathProperties(this.jpaProject, this.buildJdbcJarPath(), this.buildBootstrapJarPath());
	}
	
	private void addLaunchListener() {

		this.getLaunchManager().addLaunchListener(this.buildLaunchListener());
	}
	
	protected void preGenerate() {
		ConnectionProfile cp = this.jpaProject.getConnectionProfile();
		if (cp != null) {
			cp.disconnect();
		}
	}
	
	protected void postGenerate() {
		try {
			if(! this.isDebug) {
				this.removeLaunchConfiguration(LAUNCH_CONFIG_NAME);
			}
		}
		catch ( CoreException e) {
			throw new RuntimeException(e);
		}
		ConnectionProfile cp = this.jpaProject.getConnectionProfile();
		if (cp != null) {
			cp.connect();
		}
		
		this.validateProject();
	}

	protected void validateProject() {
		 JpaValidator validator = new JpaValidator();
		 IProject project = this.jpaProject.getProject();
		 JpaHelper helper = new JpaHelper();
		 helper.setProject(project);

		 ValidatorJob validatorJob = new ValidatorJob(validator, "JPA Validator", "", project, helper);
		 validatorJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
		 validatorJob.setUser(true);
		 validatorJob.schedule();
	}
	
	private IPath buildJdbcJarPath() {
		return new Path(this.getJpaProjectConnectionDriverJarList());
	}
	
	private String getJpaProjectConnectionDriverJarList() {
		ConnectionProfile cp = this.jpaProject.getConnectionProfile();
		return (cp == null) ? "" : cp.getDriverJarList(); //$NON-NLS-1$
	}
	
	private IPath buildBootstrapJarPath() {
		
		IPath path = JavaCore.getClasspathVariable(ECLIPSE_HOME).append(PLUGINS_DIR);
		File eclipseHome = path.toFile();
		
		List<File> result = new ArrayList<File>();
		this.findFile(ECLIPSELINK_DDL_GEN_JAR, eclipseHome, result);
		
		File ddlGenJarFile = result.get(0);
		try {
			String ddlGenJarPath = ddlGenJarFile.getCanonicalPath();
			return new Path(ddlGenJarPath);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void findFile(String fileName, File directory, List<? super File> list) {
		for (File file : directory.listFiles()) {
			if (file.getName().startsWith(fileName)) {
				list.add(file);
			}
			if (file.isDirectory()) {
				findFile(fileName, file, list);
			}
		}
	} 

	private ILaunchesListener2 buildLaunchListener() {
		return new ILaunchesListener2() {
			
			public void launchesTerminated(ILaunch[] launches) {
				for (int i = 0; i < launches.length; i++) {
					ILaunch launch = launches[i];
					if (launch.equals(EclipseLinkDDLGenerator.this.getLaunch())) {

						EclipseLinkDDLGenerator.this.postGenerate();
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

	// ********** launch configuration **********
	
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
	
	private void specifyProgramArguments(String puName, String propertiesPath) {

		StringBuffer programArguments = new StringBuffer();
		programArguments.append(this.buildPuNameArgument(puName));
		programArguments.append(this.buildPropertiesPathArgument(propertiesPath));
		programArguments.append(this.buildDebugArgument());
		
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}

	private void specifyClasspathProperties(JpaProject project, IPath jdbcJar, IPath bootstrapJar) throws CoreException {
		// DDL_GEN jar
		IRuntimeClasspathEntry bootstrapEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(bootstrapJar);
		bootstrapEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		// Default Project classpath
		IRuntimeClasspathEntry defaultEntry = JavaRuntime.newDefaultProjectClasspathEntry(project.getJavaProject());
		defaultEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		// JDBC jar
		IRuntimeClasspathEntry jdbcEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(jdbcJar);
		jdbcEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		// System Library  
		IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
		IRuntimeClasspathEntry systemLibsEntry = JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath, IRuntimeClasspathEntry.STANDARD_CLASSES);

		List<String> classpath = new ArrayList<String>();
		classpath.add(bootstrapEntry.getMemento());
		classpath.add(defaultEntry.getMemento());
		classpath.add(jdbcEntry.getMemento());
		classpath.add(systemLibsEntry.getMemento());
		
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpath);
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
	}
	
	private void specifyWorkingDir(String projectLocation) {
		
		File workingDir = new Path(projectLocation).toFile();
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, workingDir.getAbsolutePath());
	}
	
	private ILaunch saveAndLaunchConfig() throws CoreException {
		ILaunchConfiguration configuration = this.launchConfig.doSave();
		
		return configuration.launch(ILaunchManager.RUN_MODE, new NullProgressMonitor());
	}

	private ILaunchConfigurationWorkingCopy buildLaunchConfiguration() throws CoreException {
		ILaunchConfigurationWorkingCopy launchConfig = null;
		this.removeLaunchConfiguration( LAUNCH_CONFIG_NAME);

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);

		launchConfig = type.newInstance(null, LAUNCH_CONFIG_NAME);
		return launchConfig;
	}

	private void removeLaunchConfiguration(String launchConfigurationName) throws CoreException {

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
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

	// ********** EclipseLink properties **********
	
	private void buildProjectLocationProperty(Properties properties, String location) {
		this.putProperty(properties, 
			SchemaGeneration.ECLIPSELINK_APPLICATION_LOCATION,
			location);
	}
	
	private void buildDDLModeProperties(Properties properties) {
		this.putProperty(properties,  
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE,
			DdlGenerationType.DROP_AND_CREATE_TABLES);
		this.putProperty(properties,  
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE,
			OutputMode.DATABASE);
	}
	
	private void buildConnectionProperties(Properties properties) {
		ConnectionProfile cp = this.jpaProject.getConnectionProfile();

		this.putProperty(properties,  
			Connection.ECLIPSELINK_BIND_PARAMETERS,
			"false");
		this.putProperty(properties, 
			Connection.ECLIPSELINK_DRIVER,
			(cp == null) ? "" : cp.getDriverClassName());
		this.putProperty(properties,
			Connection.ECLIPSELINK_URL,
			(cp == null) ? "" : cp.getURL());
		this.putProperty(properties,
			Connection.ECLIPSELINK_USER,
			(cp == null) ? "" : cp.getUserName());
		this.putProperty(properties,
			Connection.ECLIPSELINK_PASSWORD,
			(cp == null) ? "" : cp.getUserPassword());
	}
	
	private void buildConnectionPoolingProperties(Properties properties) {
		this.putProperty(properties,
			Connection.ECLIPSELINK_READ_CONNECTIONS_SHARED,
			"true");
	}
	
	private void buildLoggingProperties(Properties properties) {
		this.putProperty(properties,
			Logging.ECLIPSELINK_LEVEL,
			LoggingLevel.FINE);
		this.putProperty(properties,
			Logging.ECLIPSELINK_TIMESTAMP,
			"false");
		this.putProperty(properties,
			Logging.ECLIPSELINK_THREAD,
			"false");
		this.putProperty(properties,
			Logging.ECLIPSELINK_SESSION,
			"false");
		this.putProperty(properties,
			Logging.ECLIPSELINK_EXCEPTIONS,
			"true");
	}
	
	private void buildCustomizationProperties(Properties properties) {
		this.putProperty(properties,
			Customization.ECLIPSELINK_THROW_EXCEPTIONS,
			"true");
	}
	
	private void putProperty(Properties properties, String key, String value) {
		properties.put(key, (value == null) ? "" : value);
	}
	
	private void saveLoginProperties(String projectLocation, String propertiesFile) {
		Properties elProperties = new Properties();
		
		this.buildConnectionProperties(elProperties);
		
		this.buildConnectionPoolingProperties(elProperties);
		
		this.buildLoggingProperties(elProperties);
		
		this.buildCustomizationProperties(elProperties);
		
		this.buildDDLModeProperties(elProperties);

		this.buildProjectLocationProperty(elProperties, projectLocation);
	    try {
	        File file = new File(propertiesFile);
	        file.createNewFile();
	        FileOutputStream stream = new FileOutputStream(file);
	        elProperties.store(stream, null);
		    stream.close();
	    }
	    catch (Exception e) {
	    	String message = "Error saving: " + propertiesFile;
			throw new RuntimeException(message, e);
		}
	}

	// ********** Main arguments **********

	private String buildPuNameArgument(String puName) {
		return " -pu \"" + puName + "\"";
	}
	
	private String buildPropertiesPathArgument(String propertiesFile) {
		return " -p \"" + propertiesFile + "\"";
	}
	
	private String buildDebugArgument() {
		return (this.isDebug) ? " -debug" : "";
	}
}
