/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.LoggingLevel;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.DdlGenerationType;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.OutputMode;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.wst.validation.ValidationFramework;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 *  EclipseLinkDLLGenerator launches the EclipseLink DDL generator in a separate VM.
 *  For this it will create a Java Configuration named "EclipseLink" 
 *  in the target workspace (note: the configuration will be overridden at each run).
 *  It will than launch the configuration created with the login information from 
 *  the current Dali project, and passes it to EclipseLink.
 *  
 *  Pre-req in PDE environment:
 *  	org.eclipse.jpt.eclipselink.core.ddlgen.jar in ECLIPSE_HOME/plugins
 */
public class EclipseLinkDDLGenerator
{
	static public String ECLIPSELINK_DDL_GEN_JAR_VERSION = "1.0.100";	//$NON-NLS-1$
	static public String LAUNCH_CONFIG_NAME = "EclipseLink";   //$NON-NLS-1$
	static public String DDL_GEN_PACKAGE_NAME = "org.eclipse.jpt.eclipselink.core.ddlgen";   //$NON-NLS-1$
	static public String ECLIPSELINK_DDL_GEN_CLASS = DDL_GEN_PACKAGE_NAME + ".Main";	  //$NON-NLS-1$
	static public String ECLIPSELINK_DDL_GEN_JAR = DDL_GEN_PACKAGE_NAME + "_";	//$NON-NLS-1$
	static public String BUNDLE_CLASSPATH = "Bundle-ClassPath";	  //$NON-NLS-1$
	static public String PROPERTIES_FILE_NAME = "login.properties";	  //$NON-NLS-1$
	static public String PLUGINS_DIR = "plugins/";	  //$NON-NLS-1$
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

	private EclipseLinkDDLGenerator(String puName, JpaProject jpaProject, String projectLocation, @SuppressWarnings("unused") IProgressMonitor monitor) {
		super();
		this.puName = puName;
		this.jpaProject = jpaProject;
		this.projectLocation = projectLocation;
		this.initialize();
	}

	// ********** behavior **********
	
	protected void initialize() {
		try {
			this.jre = this.getProjectJRE();
			if (this.jre == null) {
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
		
		this.initializeLaunchConfiguration(this.projectLocation, propertiesFile);

		this.saveLoginProperties(this.projectLocation, propertiesFile);

		this.addLaunchListener();
		this.launch = this.saveAndLaunchConfig();
	}
	
	private void initializeLaunchConfiguration(String projectLocation, String propertiesFile) {

		this.specifyJRE(this.jre.getName(), this.jre.getVMInstallType().getId());
		
		this.specifyProject(this.jpaProject.getProject().getName()); 
		this.specifyMainType(ECLIPSELINK_DDL_GEN_CLASS);	
		
		this.specifyProgramArguments(this.puName, propertiesFile);  // -pu & -p
		this.specifyWorkingDir(projectLocation); 

		this.specifyClasspathProperties(this.jpaProject, this.buildJdbcJarPath(), this.buildBootstrapJarPath());
	}
	
	private void addLaunchListener() {

		this.getLaunchManager().addLaunchListener(this.buildLaunchListener());
	}
	
	protected void preGenerate() {
		//disconnect since the runtime provider will need to connect to generate the tables
		ConnectionProfile cp = this.jpaProject.getConnectionProfile();
		if (cp != null) {
			cp.disconnect();
		}
	}
	
	protected void postGenerate() {
		try {
			if ( ! this.isDebug) {
				this.removeLaunchConfiguration(LAUNCH_CONFIG_NAME);
			}
		}
		catch ( CoreException e) {
			throw new RuntimeException(e);
		}
		//reconnect since we disconnected in preGenerate(), 
		ConnectionProfile cp = this.jpaProject.getConnectionProfile();
		if (cp != null) {
			cp.connect();
		}
		
		this.validateProject();
	}

	protected void validateProject() {
		IProject project = this.jpaProject.getProject();
		ValidateJob job = new ValidateJob(project);
		job.setRule(project);
		job.schedule();
	}

	/**
	 * Performs validation after tables have been generated
	 */
	private class ValidateJob extends Job 
	{	
		private IProject project;
		
		
		public ValidateJob(IProject project) {
			super(JptCoreMessages.VALIDATE_JOB);
			this.project = project;
		}
		
		
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IStatus status = Status.OK_STATUS;
			try {
				ValidationFramework.getDefault().validate(
					new IProject[] {this.project}, true, false, monitor);
			}
			catch (CoreException ce) {
				status = Status.CANCEL_STATUS;
			}
			return status;
		}
	}
	
	private IPath buildJdbcJarPath() {
		return new Path(this.getJpaProjectConnectionDriverJarList());
	}
	
	private String getJpaProjectConnectionDriverJarList() {
		ConnectionProfile cp = this.jpaProject.getConnectionProfile();
		return (cp == null) ? "" : cp.getDriverJarList(); //$NON-NLS-1$
	}
	
	private IPath buildBootstrapJarPath() {
		try {
			File jarInstallDir = this.getBundleParentDir(JptEclipseLinkCorePlugin.PLUGIN_ID);

			List<File> result = new ArrayList<File>();
			this.findFile(ECLIPSELINK_DDL_GEN_JAR, jarInstallDir, result);
			if (result.isEmpty()) {
				throw new RuntimeException("Could not find: " + DDL_GEN_PACKAGE_NAME + ".jar in: " + jarInstallDir);
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
			throw new RuntimeException("Could not find directory: " + directory);
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

	// ********** Setting Launch Configuration **********
	
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
		programArguments.append(this.buildPropertiesFileArgument(propertiesPath));
		programArguments.append(this.buildDebugArgument());
		
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}

	private void specifyClasspathProperties(JpaProject project, IPath jdbcJar, IPath bootstrapJar)  {
		List<String> classpath = new ArrayList<String>();
		try {
			// DDL_GEN jar
			classpath.add(this.getArchiveClasspathEntry(bootstrapJar).getMemento());
			// Default Project classpath
			classpath.add(this.getDefaultProjectClasspathEntry(project.getJavaProject()).getMemento());
			// Osgi Bundles
			classpath.addAll(this.getPersistenceOsgiBundlesMemento());
			// JDBC jar
			classpath.add(this.getArchiveClasspathEntry(jdbcJar).getMemento());
			// System Library  
			classpath.add(this.getSystemLibraryClasspathEntry().getMemento());
		}
		catch (CoreException e) {
			throw new RuntimeException("An error occurs generating a memento", e);
		}
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpath);
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
	}
	
	private void specifyWorkingDir(String projectLocation) {
		
		File workingDir = new Path(projectLocation).toFile();
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, workingDir.getAbsolutePath());
	}

	// ********** ClasspathEntry **********
	
	private IRuntimeClasspathEntry getSystemLibraryClasspathEntry() throws CoreException {

		IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
		return JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath, IRuntimeClasspathEntry.STANDARD_CLASSES);
	}
	
	private IRuntimeClasspathEntry getDefaultProjectClasspathEntry(IJavaProject project) {

		IRuntimeClasspathEntry projectEntry = JavaRuntime.newDefaultProjectClasspathEntry(project);
		projectEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		
		return projectEntry;
	}
	
	private IRuntimeClasspathEntry getArchiveClasspathEntry(IPath archivePath) {

		IRuntimeClasspathEntry archiveEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(archivePath);
		archiveEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		
		return archiveEntry;
	}
	
	private IRuntimeClasspathEntry getBundleClasspathEntry(String bundleId) {

		IPath persistClasspath = this.getBundleClasspath(bundleId);
		IRuntimeClasspathEntry bundleEntry = this.getArchiveClasspathEntry(persistClasspath);
		
		return bundleEntry;
	}

	// ********** LaunchConfig **********
	
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
			if (!file.exists() && ! file.createNewFile()) {
				throw new RuntimeException("createNewFile() failed: " + file); //$NON-NLS-1$
			}
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
	
	private String buildPropertiesFileArgument(String propertiesFile) {
		return " -p \"" + propertiesFile + "\"";
	}
	
	private String buildDebugArgument() {
		return (this.isDebug) ? " -debug" : "";
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

	// ********** Bundles *********

	private Collection<String> getPersistenceOsgiBundlesMemento() throws CoreException {

		Collection<String> result = new HashSet<String>();
		if (javaxPersistenceBundleExists()) {
			result.add(this.getBundleClasspathEntry(JAVAX_PERSISTENCE_BUNDLE).getMemento());
			result.add(this.getBundleClasspathEntry(ORG_ECLIPSE_PERSISTENCE_CORE_BUNDLE).getMemento());
			result.add(this.getBundleClasspathEntry(ORG_ECLIPSE_PERSISTENCE_ASM_BUNDLE).getMemento());
			result.add(this.getBundleClasspathEntry(ORG_ECLIPSE_PERSISTENCE_ANTLR_BUNDLE).getMemento());
			result.add(this.getBundleClasspathEntry(ORG_ECLIPSE_PERSISTENCE_JPA_BUNDLE).getMemento());
		}
		return result;
	}
	
	private IPath getBundleClasspath(String pluginID) {
		Bundle bundle = Platform.getBundle(pluginID);
		if (bundle == null) {
			throw new RuntimeException(pluginID + " cannot be retrieved from the Platform");
		}
		return this.getClassPath(bundle);
	}

	private IPath getClassPath(Bundle bundle) {
		String path = (String) bundle.getHeaders().get(BUNDLE_CLASSPATH);
		if (path == null) {
			path = ".";
		}
		ManifestElement[] elements = null;
		try {
			elements = ManifestElement.parseHeader(BUNDLE_CLASSPATH, path);
		}
		catch (BundleException e) {
			throw new RuntimeException("Error parsing bundle header: " + bundle, e);
		}
		if (elements != null) {
			for (int i = 0; i < elements.length; ++i) {
				ManifestElement element = elements[i];
				String value = element.getValue();
				if (".".equals(value)) {
					value = "/";		//$NON-NLS-1$
				}
				URL url = bundle.getEntry(value);
				if (url != null) {
					try {
						URL resolvedURL = FileLocator.resolve(url);
						String filestring = FileLocator.toFileURL(resolvedURL).getFile();
		                if (filestring.startsWith("file:")) {	//$NON-NLS-1$
		                	filestring = filestring.substring(filestring.indexOf(':') + 1);
		                }
		                if (filestring.endsWith("!/")) {		//$NON-NLS-1$
		                	filestring = filestring.substring(0, filestring.lastIndexOf('!'));
		                }
						File file = new File(filestring);
						String filePath = file.getCanonicalPath();
						return new Path(filePath);
					}
					catch (IOException e) {
						throw new RuntimeException("Error locating bundle: " + bundle, e);
					}
				}
			}
		}
		return null;
	}
	
	private boolean javaxPersistenceBundleExists() {
		return Platform.getBundle(JAVAX_PERSISTENCE_BUNDLE) != null;
	}

	// ********** constants **********

	private static final String JAVAX_PERSISTENCE_BUNDLE = "javax.persistence";					//$NON-NLS-1$
	private static final String ORG_ECLIPSE_PERSISTENCE_CORE_BUNDLE = "org.eclipse.persistence.core";	//$NON-NLS-1$
	private static final String ORG_ECLIPSE_PERSISTENCE_ASM_BUNDLE = "org.eclipse.persistence.asm";	//$NON-NLS-1$
	private static final String ORG_ECLIPSE_PERSISTENCE_ANTLR_BUNDLE = "org.eclipse.persistence.antlr";	//$NON-NLS-1$
	private static final String ORG_ECLIPSE_PERSISTENCE_JPA_BUNDLE = "org.eclipse.persistence.jpa";	//$NON-NLS-1$

}
