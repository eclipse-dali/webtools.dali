/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jpt.common.core.internal.gen.AbstractJptGenerator;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 *  ClassesGenerator
 */
public class ClassesGenerator extends AbstractJptGenerator
{
	public static final String LAUNCH_CONFIG_NAME = "JAXB Run Config";   //$NON-NLS-1$
	public static final String JAXB_GENERIC_GEN_CLASS = "com.sun.tools.xjc.XJCFacade";   //$NON-NLS-1$
	public static final String JAXB_GENERIC_GEN_JDK_CLASS = "com.sun.tools.internal.xjc.XJCFacade";   //$NON-NLS-1$
	public static final String JAXB_ECLIPSELINK_GEN_CLASS = "org.eclipse.persistence.jaxb.xjc.MOXyXJC";   //$NON-NLS-1$
	
	private final String schemaPathOrUri;
	private final String outputDir;
	private final String targetPackage;
	private final String catalog;
	private final String[] bindingsFileNames;
	private final ClassesGeneratorOptions generatorOptions;
	private final ClassesGeneratorExtensionOptions generatorExtensionOptions;
	private final String mainType;
	private String toolsJarPath;

	// ********** static methods **********
	
	public static void generate(
			IJavaProject javaProject, 
			String schemaPathOrUri, 
			String outputDir, 
			String targetPackage, 
			String catalog, 
			boolean usesMoxyGenerator,
			String[] bindingsFileNames,
			ClassesGeneratorOptions generatorOptions,
			ClassesGeneratorExtensionOptions generatorExtensionOptions,
			IProgressMonitor monitor) {
		
		if (javaProject == null) {
			throw new NullPointerException();
		}
		new ClassesGenerator(javaProject, 
			schemaPathOrUri, 
			outputDir, 
			targetPackage, 
			catalog, 
			usesMoxyGenerator, 
			bindingsFileNames,
			generatorOptions, 
			generatorExtensionOptions).generate(monitor);
	}

	/**
	 * Test if the JDK Jaxb compiler is on the classpath.
	 */
	public static boolean genericJaxbJdkIsOnClasspath(IJavaProject javaProject) {
		try {
			IType genClass = javaProject.findType(JAXB_GENERIC_GEN_JDK_CLASS);
			return (genClass != null);
		}
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Test if the non-JDK Jaxb compiler is on the classpath.
	 */
	public static boolean genericJaxbNonJdkIsOnClasspath(IJavaProject javaProject) {
		try {
			IType genClass = javaProject.findType(JAXB_GENERIC_GEN_CLASS);
			return (genClass != null);
		}
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static IVMInstall getVMInstall(IJavaProject javaProject) throws CoreException {
		return JavaRuntime.getVMInstall(javaProject);
	}
	
	public static String getVMInstallLocation(IVMInstall vm) {
			return vm.getInstallLocation().getAbsolutePath();
	}
	
	public static String getVMInstallToolsJarAbsolutePath(IVMInstall vm) {
		String vmInstallLocation = getVMInstallLocation(vm);
		return vmInstallLocation + File.separator + "lib" + File.separator + "tools.jar";   //$NON-NLS-1$
	}

	public static String buildToolsJarPath(IJavaProject javaProject) {
		try {
			IVMInstall vm = getVMInstall(javaProject);
			return getVMInstallToolsJarAbsolutePath(vm);
		}
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	public static String findToolsJarPath(IJavaProject javaProject) {
			String toolsAbsolutePath = buildToolsJarPath(javaProject);
			return ((new File(toolsAbsolutePath)).exists()) ? toolsAbsolutePath : null;
	}
	
	public static boolean toolsJarExists(IJavaProject javaProject) {
		return (findToolsJarPath(javaProject) != null);
	}

	// ********** constructors **********
	
	protected ClassesGenerator(
			IJavaProject javaProject, 
			String schemaPathOrUri, 
			String outputDir, 
			String targetPackage, 
			String catalog, 
			boolean usesMoxyGenerator, 
			String[] bindingsFileNames,
			ClassesGeneratorOptions generatorOptions,
			ClassesGeneratorExtensionOptions generatorExtensionOptions) {
		super(javaProject);
		this.schemaPathOrUri = schemaPathOrUri;
		this.outputDir = outputDir;
		this.targetPackage = targetPackage;
		this.catalog = catalog;
		this.bindingsFileNames = bindingsFileNames;
		this.generatorOptions = generatorOptions;
		this.generatorExtensionOptions = generatorExtensionOptions;
		
		this.mainType = this.buildMainType(javaProject, usesMoxyGenerator);
	}

	// ********** overrides **********
	
	@Override
	protected String getMainType() {
		return this.mainType;
	}

	@Override
	protected String getLaunchConfigName() {
		return LAUNCH_CONFIG_NAME;
	}

	@Override
	protected void specifyJRE() {
		// do nothing
	}

	// ********** behavior **********

	@Override
	protected void preGenerate(IProgressMonitor monitor) {
		// nothing to do yet...
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

	// ********** private methods **********
	
	protected String buildMainType(IJavaProject javaProject, boolean usesMoxyGenerator) {
		if(usesMoxyGenerator) {
			return JAXB_ECLIPSELINK_GEN_CLASS;
		}
		else if(genericJaxbNonJdkIsOnClasspath(javaProject)) {
			return JAXB_GENERIC_GEN_CLASS;
		}
		else if(genericJaxbJdkIsOnClasspath(javaProject)) {
			return JAXB_GENERIC_GEN_JDK_CLASS;
		}
		this.toolsJarPath = findToolsJarPath(javaProject);
		return JAXB_GENERIC_GEN_JDK_CLASS;
	}

	private IRuntimeClasspathEntry getToolsClasspathEntry() {
		return (StringTools.stringIsEmpty(this.toolsJarPath)) ?
			null :
			getArchiveClasspathEntry(new Path(this.toolsJarPath));
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
		// Tools classpath
		IRuntimeClasspathEntry toolsClasspathEntry = this.getToolsClasspathEntry();
		if(toolsClasspathEntry != null) {
			classpath.add(toolsClasspathEntry.getMemento());
		}
		return classpath;
	}

	@Override
	protected void specifyProgramArguments() {
		StringBuffer programArguments = new StringBuffer();
		
		programArguments.append("-d ");	  //$NON-NLS-1$
		if(StringTools.stringIsEmpty(this.outputDir)) {
			throw new RuntimeException("Output directory cannot be empty");	  //$NON-NLS-1$
		}
		programArguments.append(StringTools.quote(this.outputDir));
		if( ! StringTools.stringIsEmpty(this.targetPackage)) {
			programArguments.append(" -p ");	  //$NON-NLS-1$
			programArguments.append(this.targetPackage);
		}
		if( ! StringTools.stringIsEmpty(this.catalog)) {
			programArguments.append(" -catalog ");	  //$NON-NLS-1$
			programArguments.append(StringTools.quote(this.catalog));
		}

		// Options
		if( ! StringTools.stringIsEmpty(this.generatorOptions.getProxy())) {
			programArguments.append(" -httpproxy ");	  //$NON-NLS-1$
			programArguments.append(this.generatorOptions.getProxy());
		}
		if( ! StringTools.stringIsEmpty(this.generatorOptions.getProxyFile())) {
			programArguments.append(" -httpproxyfile ");	  //$NON-NLS-1$
			programArguments.append(StringTools.quote(this.generatorOptions.getProxyFile()));
		}
		
		if( ! this.generatorOptions.usesStrictValidation()) {
			programArguments.append(" -nv");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.makesReadOnly()) {
			programArguments.append(" -readOnly");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.suppressesPackageInfoGen()) {
			programArguments.append(" -npa");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.suppressesHeaderGen()) {
			programArguments.append(" -no-header");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.isVerbose()) {
			programArguments.append(" -verbose");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.isQuiet()) {
			programArguments.append(" -quiet");	  //$NON-NLS-1$
		}

		if(this.generatorOptions.treatsAsXmlSchema()) {
			programArguments.append(" -xmlschema");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.treatsAsRelaxNg()) {
			programArguments.append(" -relaxng");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.treatsAsRelaxNgCompact()) {
			programArguments.append(" -relaxng-compact");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.treatsAsDtd()) {
			programArguments.append(" -dtd");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.treatsAsWsdl()) {
			programArguments.append(" -wsdl");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.showsVersion()) {
			programArguments.append(" -version");	  //$NON-NLS-1$
		}
		if(this.generatorOptions.showsHelp()) {
			programArguments.append(" -help");	  //$NON-NLS-1$
		}

		// Extension Options
		if(this.generatorExtensionOptions.allowsExtensions()) {
			programArguments.append(" -extension");	  //$NON-NLS-1$
		}
		if( ! StringTools.stringIsEmpty(this.generatorExtensionOptions.getClasspath())) {
			programArguments.append(" -classpath ");	  //$NON-NLS-1$
			programArguments.append(StringTools.quote(this.generatorExtensionOptions.getClasspath()));
		}
		if( ! StringTools.stringIsEmpty(this.generatorExtensionOptions.getAdditionalArgs())) {
			programArguments.append(' ');
			programArguments.append(this.generatorExtensionOptions.getAdditionalArgs());
		}

		// schema
		programArguments.append(' ');
		if(StringTools.stringIsEmpty(this.schemaPathOrUri)) {
			throw new RuntimeException("Schema cannot be empty");	  //$NON-NLS-1$
		}
		programArguments.append(StringTools.quote(this.schemaPathOrUri));
		
		// bindings
		if (this.bindingsFileNames.length > 0) {
			for (String bindingsFileName : this.bindingsFileNames) {
				programArguments.append(" -b ");	  //$NON-NLS-1$
				programArguments.append(StringTools.quote(bindingsFileName));
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
