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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jpt.core.internal.gen.AbstractJptGenerator;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 *  ClassesGenerator
 */
public class ClassesGenerator extends AbstractJptGenerator
{
	public static final String LAUNCH_CONFIG_NAME = "JAXB Run Config";   //$NON-NLS-1$
	public static final String JAXB_GENERIC_GEN_CLASS = "com.sun.tools.xjc.XJCFacade";   //$NON-NLS-1$
	public static final String JAXB_ECLIPSELINK_GEN_CLASS = "org.eclipse.persistence.jaxb.xjc.MOXyXJC";   //$NON-NLS-1$
	
	private final String xmlSchemaName;
	private final String outputDir;
	private final String targetPackage;
	private final String catalog;
	private final String[] bindingsFileNames;
	private final String mainType;

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
			bindingsFileNames).generate(monitor);
	}

	// ********** constructors **********
	
	protected ClassesGenerator(
			IJavaProject javaProject, 
			String xmlSchemaName, 
			String outputDir, 
			String targetPackage, 
			String catalog, 
			boolean useMoxyGenerator, 
			String[] bindingsFileNames) {
		super(javaProject);
		this.xmlSchemaName = xmlSchemaName;
		this.outputDir = outputDir;
		this.targetPackage = targetPackage;
		this.catalog = catalog;
		this.bindingsFileNames = bindingsFileNames;
		this.mainType = (useMoxyGenerator) ? JAXB_ECLIPSELINK_GEN_CLASS : JAXB_GENERIC_GEN_CLASS;
	}

	@Override
	protected String getMainType() {
		return this.mainType;
	}

	@Override
	protected String getLaunchConfigName() {
		return LAUNCH_CONFIG_NAME;
	}


	// ********** behavior **********

	@Override
	protected void preGenerate(IProgressMonitor monitor) {
		//nothing to do yet...
	}

	@Override
	protected void postGenerate() {
		super.postGenerate();
		try {
			this.javaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	// ********** Launch Configuration Setup **********

	@Override
	protected List<String> buildClasspath() throws CoreException {
		List<String> classpath = new ArrayList<String>();
		// Default Project classpath
		classpath.add(this.getDefaultProjectClasspathEntry().getMemento());
		// System Library  
		classpath.add(this.getSystemLibraryClasspathEntry().getMemento());
		// Containers classpath
		for(IRuntimeClasspathEntry containerClasspathEntry: this.getContainersClasspathEntries()) {
			classpath.add(containerClasspathEntry.getMemento());
		}
		return classpath;
	}

	@Override
	protected void specifyProgramArguments() {
		StringBuffer programArguments = new StringBuffer();
		// options
		programArguments.append("-d ");	  //$NON-NLS-1$
		programArguments.append(this.outputDir);
		if( ! StringTools.stringIsEmpty(this.targetPackage)) {
			programArguments.append(" -p ");	  //$NON-NLS-1$
			programArguments.append(this.targetPackage);
		}
		if( ! StringTools.stringIsEmpty(this.catalog)) {
			programArguments.append(" -catalog ");	  //$NON-NLS-1$
			programArguments.append(this.catalog);
		}
		// schema
		programArguments.append(' ');
		programArguments.append(this.xmlSchemaName);
		
		// bindings
		if (this.bindingsFileNames.length > 0) {
			for (String bindingsFileName : this.bindingsFileNames) {
				programArguments.append(" -b ");	  //$NON-NLS-1$
				programArguments.append(bindingsFileName);
			}
		}
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}


	// ********** Queries **********

	private List<IRuntimeClasspathEntry> getContainersClasspathEntries() throws CoreException {
		ArrayList<IRuntimeClasspathEntry> classpathEntries = new ArrayList<IRuntimeClasspathEntry>();
		for(IClasspathEntry classpathEntry: this.javaProject.getRawClasspath()) {
			if(classpathEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				IClasspathContainer container = JavaCore.getClasspathContainer(classpathEntry.getPath(), this.javaProject);
				if(container != null && container.getKind() == IClasspathContainer.K_SYSTEM) {
					classpathEntries.add( 
						JavaRuntime.newRuntimeContainerClasspathEntry(
							container.getPath(), 
							IRuntimeClasspathEntry.BOOTSTRAP_CLASSES, 
							this.javaProject));
				}
			}
		}
		return classpathEntries;
	}
}
