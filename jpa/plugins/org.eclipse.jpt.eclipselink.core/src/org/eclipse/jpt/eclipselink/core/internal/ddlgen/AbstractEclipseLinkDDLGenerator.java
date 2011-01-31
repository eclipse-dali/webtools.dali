/*******************************************************************************
* Copyright (c) 2008, 2011 Oracle. All rights reserved.
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
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jpt.common.core.internal.gen.AbstractJptGenerator;
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
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.wst.validation.ValidationFramework;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public abstract class AbstractEclipseLinkDDLGenerator extends AbstractJptGenerator
{
	public static final String LAUNCH_CONFIG_NAME = "Dali EclipseLink Table Generation";   //$NON-NLS-1$
	public static final String DDL_GEN_PACKAGE_NAME = "org.eclipse.jpt.eclipselink.core.ddlgen";   //$NON-NLS-1$
	public static final String ECLIPSELINK_DDL_GEN_CLASS = DDL_GEN_PACKAGE_NAME + ".Main";	  //$NON-NLS-1$
	public static final String ECLIPSELINK_DDL_GEN_JAR_PREFIX = DDL_GEN_PACKAGE_NAME + "_";	//$NON-NLS-1$
	public static final String BUNDLE_CLASSPATH = "Bundle-ClassPath";	  //$NON-NLS-1$
	public static final String PROPERTIES_FILE_NAME = "login.properties";	  //$NON-NLS-1$
	public static final String TRUE = "true";	  //$NON-NLS-1$
	public static final String FALSE = "false";	  //$NON-NLS-1$
	public static final String NONE = "NONE";	  //$NON-NLS-1$

	private String puName;
	private JpaProject jpaProject;

	// ********** constructors **********

	protected AbstractEclipseLinkDDLGenerator(String puName, JpaProject jpaProject) {
		super(JavaCore.create(jpaProject.getProject()));
		this.puName = puName;
		this.jpaProject = jpaProject;
	}

	// ********** overrides **********

	@Override
	protected String getLaunchConfigName() {
		return LAUNCH_CONFIG_NAME;
	}

	@Override
	protected String getMainType() {
		return ECLIPSELINK_DDL_GEN_CLASS;
	}

	@Override
	protected String getBootstrapJarPrefix() {
		return ECLIPSELINK_DDL_GEN_JAR_PREFIX;
	}
	
	@Override
	protected void preGenerate(IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 2);
		//disconnect since the runtime provider will need to connect to generate the tables
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp != null) {
			cp.disconnect();
			sm.worked(1);
		}
		sm.setWorkRemaining(1);
		this.saveLoginProperties();
		sm.worked(1);
	}
	
	@Override
	protected void postGenerate() {
		super.postGenerate();
		this.reconnect();
		this.validateProject();
	}

	// ********** Setting Launch Configuration **********
	
	@Override
	protected void specifyProgramArguments() {
		StringBuffer programArguments = new StringBuffer();
		this.appendPuNameArgument(programArguments);
		this.appendPropertiesFileArgument(programArguments);
		this.appendDebugArgument(programArguments);
		
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}

	@Override
	protected List<String> buildClasspath() throws CoreException {
		List<String> classpath = new ArrayList<String>();
		// DDL_GEN jar
		classpath.add(getBootstrapJarClasspathEntry().getMemento());
		// Default Project classpath
		classpath.add(this.getDefaultProjectClasspathEntry().getMemento());
		// Osgi Bundles
		classpath.addAll(this.getPersistenceOsgiBundlesMemento());
		// JDBC jar
		classpath.add(this.getJdbcJarClasspathEntry().getMemento());
		// System Library  
		classpath.add(this.getSystemLibraryClasspathEntry().getMemento());
		return classpath;
	}

	// ********** behavior **********

	//reconnect since we disconnected in preGenerate();
	//ensure the project is fully updated before validating - bug 277236
	protected void reconnect() {
		getJpaProject().updateAndWait();
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp != null) {
			cp.connect();
		}
	}

	protected void validateProject() {
		IProject project = this.getJpaProject().getProject();
		ValidateJob job = new ValidateJob(project);
		job.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(project));
		job.schedule();
	}

	// ********** ClasspathEntry **********

	private IRuntimeClasspathEntry getJdbcJarClasspathEntry() {
		return getArchiveClasspathEntry(this.buildJdbcJarPath());
	}
	
	private IRuntimeClasspathEntry getBundleClasspathEntry(String bundleId) {
		IPath persistClasspath = this.getBundleClasspath(bundleId);
		IRuntimeClasspathEntry bundleEntry = getArchiveClasspathEntry(persistClasspath);
		
		return bundleEntry;
	}


	// ********** EclipseLink properties **********
	
	protected void buildConnectionProperties(Properties properties) {
		this.putProperty(properties,  
			Connection.ECLIPSELINK_BIND_PARAMETERS,
			FALSE);
	}
	
	private void buildLoggingProperties(Properties properties) {
		this.putProperty(properties,
			Logging.ECLIPSELINK_LEVEL,
			LoggingLevel.FINE);
		this.putProperty(properties,
			Logging.ECLIPSELINK_TIMESTAMP,
			FALSE);
		this.putProperty(properties,
			Logging.ECLIPSELINK_THREAD,
			FALSE);
		this.putProperty(properties,
			Logging.ECLIPSELINK_SESSION,
			FALSE);
		this.putProperty(properties,
			Logging.ECLIPSELINK_EXCEPTIONS,
			TRUE);
	}
	
	protected void buildCustomizationProperties(Properties properties) {
		this.putProperty(properties,
			Customization.ECLIPSELINK_THROW_EXCEPTIONS,
			TRUE);
	}
	
	protected void putProperty(Properties properties, String key, String value) {
		properties.put(key, (value == null) ? "" : value);	  //$NON-NLS-1$
	}
	
	protected void buildAllProperties(Properties properties) {
		this.buildConnectionProperties(properties);
		this.buildConnectionPoolingProperties(properties);
		this.buildLoggingProperties(properties);
		this.buildCustomizationProperties(properties);
		this.buildDDLModeProperties(properties);
		this.buildProjectLocationProperty(properties);
	}

	// ********** private methods **********
	
	private void buildProjectLocationProperty(Properties properties) {
		this.putProperty(properties, 
			SchemaGeneration.ECLIPSELINK_APPLICATION_LOCATION,
			this.projectLocation);
	}
	
	private void buildDDLModeProperties(Properties properties) {
		this.putProperty(properties,  
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE,
			DdlGenerationType.DROP_AND_CREATE_TABLES);
		this.putProperty(properties,  
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE,
			OutputMode.DATABASE);
	}
	
	private void buildConnectionPoolingProperties(Properties properties) {
		this.putProperty(properties,
			Connection.ECLIPSELINK_READ_CONNECTIONS_SHARED,
			TRUE);
	}
	
	private void saveLoginProperties() {
		String propertiesFile  = this.projectLocation + "/" + PROPERTIES_FILE_NAME; 	  //$NON-NLS-1$

		Properties elProperties = new Properties();

		this.buildAllProperties(elProperties);
		FileOutputStream stream = null;
	    try {
	        File file = new File(propertiesFile);
			if (!file.exists() && ! file.createNewFile()) {
				throw new RuntimeException("createNewFile() failed: " + file); //$NON-NLS-1$
			}
	        stream = new FileOutputStream(file);
	        elProperties.store(stream, null);
	    }
	    catch (Exception e) {
	    	String message = "Error saving: " + propertiesFile; //$NON-NLS-1$
			throw new RuntimeException(message, e);
		}
	    finally {
	    	this.closeStream(stream);
	    }
	}
	
	private void closeStream(OutputStream stream) {
    	if (stream != null) {
    		try {
				stream.close();
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
    	}		
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
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp == null) ? "" : cp.getDriverJarList(); //$NON-NLS-1$
	}

	// ********** Main arguments **********

	private void appendPuNameArgument(StringBuffer sb) {
		sb.append(" -pu \""); //$NON-NLS-1$
		sb.append(this.puName);
		sb.append("\"");	  //$NON-NLS-1$
	}
	
	private void appendPropertiesFileArgument(StringBuffer sb) {
		sb.append(" -p \""); //$NON-NLS-1$
		sb.append(this.projectLocation).append("/").append(PROPERTIES_FILE_NAME).append("\""); //$NON-NLS-1$ //$NON-NLS-2$
	}


	// ********** Queries **********
	
	protected JpaPlatform getPlatform() {
		return this.getJpaProject().getJpaPlatform();
	}
	
	protected JpaProject getJpaProject() {
		return this.jpaProject;
	}
	
	
	protected ConnectionProfile getConnectionProfile() {
		return this.getJpaProject().getConnectionProfile();
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
			throw new RuntimeException(pluginID + " cannot be retrieved from the Platform"); //$NON-NLS-1$
		}
		return this.getClassPath(bundle);
	}

	private IPath getClassPath(Bundle bundle) {
		String path = bundle.getHeaders().get(BUNDLE_CLASSPATH);
		if (path == null) {
			path = ".";	  //$NON-NLS-1$
		}
		ManifestElement[] elements = null;
		try {
			elements = ManifestElement.parseHeader(BUNDLE_CLASSPATH, path);
		}
		catch (BundleException e) {
			throw new RuntimeException("Error parsing bundle header: " + bundle, e); //$NON-NLS-1$
		}
		if (elements != null) {
			for (int i = 0; i < elements.length; ++i) {
				ManifestElement element = elements[i];
				String value = element.getValue();
				if (".".equals(value)) {	  //$NON-NLS-1$
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
						throw new RuntimeException("Error locating bundle: " + bundle, e); //$NON-NLS-1$
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
