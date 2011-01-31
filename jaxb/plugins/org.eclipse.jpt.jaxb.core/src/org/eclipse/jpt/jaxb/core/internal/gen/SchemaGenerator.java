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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jpt.common.core.internal.gen.AbstractJptGenerator;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;

/**
 *  SchemaGenerator
 */
public class SchemaGenerator extends AbstractJptGenerator
{
	static public String LAUNCH_CONFIG_NAME = "JAXB Schema Gen Run Config";   //$NON-NLS-1$
	static public String JAXB_SCHEMA_GEN_PACKAGE_NAME = "org.eclipse.jpt.jaxb.core.schemagen";   //$NON-NLS-1$
	static public String JAXB_SCHEMA_GEN_CLASS = JAXB_SCHEMA_GEN_PACKAGE_NAME + ".Main";	  //$NON-NLS-1$
	static public String ECLIPSELINK_JAXB_SCHEMA_GEN_PACKAGE_NAME = "org.eclipse.jpt.jaxb.eclipselink.core.schemagen";   //$NON-NLS-1$
	static public String ECLIPSELINK_JAXB_SCHEMA_GEN_CLASS = ECLIPSELINK_JAXB_SCHEMA_GEN_PACKAGE_NAME + ".Main";	  //$NON-NLS-1$
	
	static public String ECLIPSELINK_JAXB_CONTEXT_FACTORY = "org.eclipse.persistence.jaxb.JAXBContextFactory";  //$NON-NLS-1$
	static public String ECLIPSELINK_JAXB_PROPERTIES_FILE_CONTENTS = "javax.xml.bind.context.factory=" + ECLIPSELINK_JAXB_CONTEXT_FACTORY;  //$NON-NLS-1$
	static public String JAXB_PROPERTIES_FILE_NAME = "jaxb.properties";  //$NON-NLS-1$
	
	static public String JAXB_SCHEMA_GEN_JAR_PREFIX = JAXB_SCHEMA_GEN_PACKAGE_NAME + "_";	//$NON-NLS-1$
	static public String ECLIPSELINK_JAXB_SCHEMA_GEN_JAR_PREFIX = ECLIPSELINK_JAXB_SCHEMA_GEN_PACKAGE_NAME + "_";	//$NON-NLS-1$

	static public String JAXB_GENERIC_SCHEMA_GEN_CLASS = "javax.xml.bind.JAXBContext";	//$NON-NLS-1$
	static public String JAXB_ECLIPSELINK_SCHEMA_GEN_CLASS = "org.eclipse.persistence.jaxb.JAXBContext";	//$NON-NLS-1$
	
	private final String targetSchemaName;
	private final String[] sourceClassNames;
	private  String mainType;
	private  boolean useMoxy;

	// ********** static methods **********
	
	public static void generate(
			IJavaProject javaProject, 
			String targetSchemaName, 
			String[] sourceClassNames,
			boolean useMoxy,
			IProgressMonitor monitor) {
		if (javaProject == null) {
			throw new NullPointerException();
		}
		new SchemaGenerator(javaProject, 
			targetSchemaName, 
			sourceClassNames,
			useMoxy).generate(monitor);
	}

	// ********** constructors **********
	
	protected SchemaGenerator(
			IJavaProject javaProject, 
			String targetSchemaName, 
			String[] sourceClassNames,
			boolean useMoxy) {
		super(javaProject);
		this.targetSchemaName = targetSchemaName;
		this.sourceClassNames = sourceClassNames;
		this.useMoxy = useMoxy;
		this.mainType = (this.useMoxy) ? 
				ECLIPSELINK_JAXB_SCHEMA_GEN_CLASS :
				JAXB_SCHEMA_GEN_CLASS;
		this.initialize();
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
	protected String getBootstrapJarPrefix() {
		return (this.useMoxy) ? 
						ECLIPSELINK_JAXB_SCHEMA_GEN_JAR_PREFIX :
						JAXB_SCHEMA_GEN_JAR_PREFIX;
	}

	@Override
	protected void preGenerate(IProgressMonitor monitor) {
		// generate jaxb.properties file if necessary
		if (this.useMoxy){
			if (!isJaxbPropertiesFilePresent()){
				this.generateJaxbPropertiesFile(monitor);
			}
			else if (!isJaxbContextMoxy()){
				//properties file actually specifies a different implementation
				//override wizard setting and fall back to generic generation
				this.useMoxy = false;
				this.mainType = JAXB_SCHEMA_GEN_CLASS;
			}
		}
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
		// Schema_Gen jar
		classpath.add(this.getBootstrapJarClasspathEntry().getMemento());
		// Default Project classpath
		classpath.add(this.getDefaultProjectClasspathEntry().getMemento());
		// System Library  
		classpath.add(this.getSystemLibraryClasspathEntry().getMemento());
		return classpath;
	}
	
	@Override
	protected void specifyProgramArguments() {

		StringBuffer programArguments = new StringBuffer();
		// sourceClassNames
		this.appendClassNameArguments(programArguments);

		// schema
		programArguments.append(" -s \"");	  //$NON-NLS-1$
		programArguments.append(this.targetSchemaName);
		programArguments.append('"');

		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}

	// ********** private methods **********
	
	private void appendClassNameArguments(StringBuffer sb) {
		for (String className : this.sourceClassNames) {
			sb.append(" -c ");	  //$NON-NLS-1$
			sb.append(className);
		}
	}
	
	/**
	 * Returns the first "jaxb.properties" file that is found in a valid source
	 * folder in the project.
	 * 
	 * Returns null if no "jaxb.properties" file is found.
	 */
	private IFile getJaxbPropertiesFile() {
		return getJaxbPropertiesFileFromPackageRoots(JDTTools.getJavaSourceFolders(this.javaProject));
	}
	
	private IFile getJaxbPropertiesFileFromPackageRoots(Iterable<IPackageFragmentRoot> packageFragmentRoots){
		Object[] objects = null;
		IJavaElement[] javaElements;
		try {
			for (IPackageFragmentRoot pfr : packageFragmentRoots) {
				javaElements = pfr.getChildren();
				for (IJavaElement javaElement : javaElements) {
					objects = ((IPackageFragment) javaElement).getNonJavaResources();
					for (Object object : objects) {
						IResource resource = (IResource) object;
						if (resource.getName().equals(JAXB_PROPERTIES_FILE_NAME)) {
							// jaxb.properties has been found
							return (IFile)resource;
						}
					}
				}
			}
		} catch (JavaModelException jme) {
			throw new RuntimeException(jme);
		}
		return null;
	}		

	private boolean isJaxbPropertiesFilePresent(){
		return getJaxbPropertiesFile()!= null;
	}

	private boolean isJaxbContextMoxy(){	

		InputStream in = null;
		try {
			in = getJaxbPropertiesFile().getContents();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			//jaxb.properties will only contain one property entry, the JAXBContextFactory
			String propertyValue = line.substring(line.indexOf("=") + 1); //$NON-NLS-1$
			if (propertyValue.equals(ECLIPSELINK_JAXB_CONTEXT_FACTORY)){
				return true;
			}
		} catch (CoreException ce){
			throw new RuntimeException(ce);
		} catch (IOException ioe){
			throw new RuntimeException(ioe);
		} finally {
		    if (in != null){
		    	try{
		    		in.close();
		    	} catch (IOException ioe) {
		    		throw new RuntimeException(ioe);
				}
		    }
		}
		return false;
	}
	
	private void generateJaxbPropertiesFile(IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 1);
		sm.subTask(JptJaxbCoreMessages.SchemaGenerator_creatingJAXBPropertiesFileTask);
		
		IPackageFragment packageFragment = findPackageFragementForSourceClassName(this.sourceClassNames[0]);

		IFolder folder = (IFolder)packageFragment.getResource();
		IFile file = folder.getFile(JAXB_PROPERTIES_FILE_NAME);
			
		byte[] bytes;
		try {
			bytes = ECLIPSELINK_JAXB_PROPERTIES_FILE_CONTENTS.getBytes("UTF-8"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}    
		
		InputStream contents = new ByteArrayInputStream(bytes);
		
		try {
			//the input stream will be closed as a result of calling create
		    file.create(contents, IResource.NONE, sm.newChild(1));		
		} catch (CoreException ce) {
			throw new RuntimeException(ce);
		}
	}

	private IPackageFragment findPackageFragementForSourceClassName(String sourceClassName) {
		String packageName = sourceClassName.substring(0, sourceClassName.lastIndexOf('.'));
		
		//Find the existing package fragment where we want to generate
		for (IPackageFragmentRoot pfr : JDTTools.getJavaSourceFolders(this.javaProject)) {
			//use the package of the first source class as the package for generation
			IPackageFragment packageFragment = pfr.getPackageFragment(packageName);
			if (packageFragment.exists()){
				return packageFragment;
			}
		}
		//the existing package fragment was not found
		throw new IllegalStateException("Java package must exist for source class"); //$NON-NLS-1$
	}

}
