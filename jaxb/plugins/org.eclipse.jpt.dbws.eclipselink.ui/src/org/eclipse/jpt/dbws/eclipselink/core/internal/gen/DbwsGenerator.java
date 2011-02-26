/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.core.internal.gen;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jpt.common.core.internal.gen.AbstractJptGenerator;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 *  DbwsGenerator
 */
public class DbwsGenerator extends AbstractJptGenerator
{
	static public String LAUNCH_CONFIG_NAME = "DBWS Gen Run Config";   //$NON-NLS-1$
	static public String DBWS_GEN_PACKAGE_NAME = "org.eclipse.jpt.dbws.eclipselink.core.gen";   //$NON-NLS-1$
	static public String DBWS_GEN_CLASS = DBWS_GEN_PACKAGE_NAME + ".Main";	  //$NON-NLS-1$

	static public String DBWS_GEN_JAR_PREFIX = DBWS_GEN_PACKAGE_NAME + "_";	//$NON-NLS-1$
	
	private final String builderFileName;
	private final String stageDirName;
	private final String driverJarList;

	// ********** static methods **********
	
	public static void generate(
			IJavaProject javaProject, 
			String builderFileName, 
			String stageDirName, 
			String driverJarList,
			IProgressMonitor monitor) {
		if (javaProject == null) {
			throw new NullPointerException();
		}
		new DbwsGenerator(javaProject, 
			builderFileName, 
			stageDirName, 
			driverJarList).generate(monitor);
	}

	// ********** constructors **********

	private DbwsGenerator(
			IJavaProject javaProject, 
			String builderFileName, 
			String stageDirName, 
			String driverJarList) {
		super(javaProject);
		this.builderFileName = builderFileName;
		this.stageDirName = stageDirName;
		this.driverJarList = driverJarList;
	}

	// ********** overrides **********
	
	@Override
	protected String getMainType() {
		return DBWS_GEN_CLASS;
	}

	@Override
	protected String getLaunchConfigName() {
		return LAUNCH_CONFIG_NAME;
	}

	@Override
	protected String getBootstrapJarPrefix() {
		return DBWS_GEN_JAR_PREFIX;
	}

	@Override
	protected void preGenerate(IProgressMonitor monitor) {
		// do nothing
		
	}

	@Override
	protected void postGenerate() {
		super.postGenerate();
		try {
			this.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	// ********** Launch Configuration Setup **********

	@Override
	protected List<String> buildClasspath() throws CoreException {
		List<String> classpath = new ArrayList<String>();
		// DBWS_Gen jar
		classpath.add(this.getBootstrapJarClasspathEntry().getMemento());
		// Default Project classpath
		classpath.add(this.getDefaultProjectClasspathEntry().getMemento());
		// JDBC jar
		if( ! StringTools.stringIsEmpty(this.driverJarList)) {
			classpath.add(this.getJdbcJarClasspathEntry().getMemento());
		}
		// System Library  
		classpath.add(this.getSystemLibraryClasspathEntry().getMemento());
		return classpath;
	}
	
	@Override
	protected void specifyProgramArguments() {

		StringBuffer programArguments = new StringBuffer();

		// builderFile
		programArguments.append(" -builderFile \"");	  //$NON-NLS-1$
		programArguments.append(this.builderFileName);
		programArguments.append('"');

		// stageDir
		programArguments.append(" -stageDir \"");	  //$NON-NLS-1$
		programArguments.append(this.stageDirName);
		programArguments.append('"');

		// packageAs
		programArguments.append(" -packageAs eclipse");	  //$NON-NLS-1$

		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}

	// ********** private methods **********

	private IRuntimeClasspathEntry getJdbcJarClasspathEntry() {
		return getArchiveClasspathEntry(this.buildJdbcJarPath());
	}
	
	private IPath buildJdbcJarPath() {
		return new Path(this.driverJarList);
	}
	
	
}
