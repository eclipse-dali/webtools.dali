/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jpt.common.core.internal.gen.AbstractJptGenerator;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.utility.internal.io.FileTools;
import org.eclipse.jpt.jaxb.core.JptJaxbCoreMessages;

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
	private String mainType;
	private boolean useMoxy;
	private ArrayList<String> generatedNames;


	// ********** constructors **********
	
	public SchemaGenerator(
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
		if(this.useMoxy) {
			if( ! this.jaxbPropertiesFileIsPresent()) {
				this.generateJaxbPropertiesFile(monitor);
			}
			else if( ! this.jaxbContextUsesMoxy()) {
				//properties file actually specifies a different implementation
				//override wizard setting and fall back to generic generation
				this.useMoxy = false;
				this.mainType = JAXB_SCHEMA_GEN_CLASS;
			}
		}
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.generatedNames = new ArrayList<String>();
	}

	// ********** misc **********

	/**
	 * Returns generated names without extension.
	 */
	public Iterable<String> getGeneratedNames() {
		return this.generatedNames;
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

		this.getLaunchConfig().setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, programArguments.toString());
	}

	@Override
	protected ILaunch saveAndLaunchConfig(IProgressMonitor monitor) {
		ILaunch launch = super.saveAndLaunchConfig(monitor);

		this.getConsoleOutputStream(launch).addListener(this.buildConsoleOutputStreamListener());
		return launch;
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
		return getJaxbPropertiesFileFromPackageRoots(JDTTools.getJavaSourceFolders(this.getJavaProject()));
	}
	
	private IFile getJaxbPropertiesFileFromPackageRoots(Iterable<IPackageFragmentRoot> packageFragmentRoots){
		IJavaElement[] javaElements;
		IFile jaxbPropertiesFile = null;
		try {
			for (IPackageFragmentRoot pfr : packageFragmentRoots) {
				jaxbPropertiesFile = this.findJaxbPropertiesFile(pfr.getNonJavaResources());
				if(jaxbPropertiesFile != null) {
					return jaxbPropertiesFile;
				}
				javaElements = pfr.getChildren();
				for(IJavaElement je : javaElements) {
					jaxbPropertiesFile = this.findJaxbPropertiesFile(((IPackageFragment)je).getNonJavaResources());
					if(jaxbPropertiesFile != null) {
						return jaxbPropertiesFile;
					}
				}
			}
		} catch (JavaModelException jme) {
			throw new RuntimeException(jme);
		}
		return null;
	}
	
	private IFile findJaxbPropertiesFile(Object[] objects) throws JavaModelException {
		for(Object object : objects) {
			IResource resource = (IResource) object;
			if(resource.getName().equals(JAXB_PROPERTIES_FILE_NAME)) {
				// jaxb.properties has been found
				return (IFile)resource;
			}
		}
		return null;
	}	

	private boolean jaxbPropertiesFileIsPresent() {
		return this.getJaxbPropertiesFile() != null;
	}

	private boolean jaxbContextUsesMoxy() {	

		InputStream in = null;
		try {
			in = this.getJaxbPropertiesFile().getContents();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			//jaxb.properties will only contain one property entry, the JAXBContextFactory
			String propertyValue = line.substring(line.indexOf("=") + 1); //$NON-NLS-1$
			if (propertyValue.equals(ECLIPSELINK_JAXB_CONTEXT_FACTORY)) {
				return true;
			}
		} catch (CoreException ce) {
			throw new RuntimeException(ce);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		} finally {
		    if (in != null) {
		    	try {
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
		sm.subTask(JptJaxbCoreMessages.SCHEMA_GENERATOR_CREATING_JAXB_PROPERTIES_FILE_TASK);

		IPackageFragment packageFragment = this.findPackageFragementForSourceClassName(this.sourceClassNames[0]);

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

		if(this.classIsInDefaultPackage(sourceClassName)) {
			return this.emptyPackageFragment();
		}
		String packageName = sourceClassName.substring(0, sourceClassName.lastIndexOf('.'));
		
		//Find the existing package fragment where we want to generate
		for (IPackageFragmentRoot pfr : JDTTools.getJavaSourceFolders(this.getJavaProject())) {
			//use the package of the first source class as the package for generation
			IPackageFragment packageFragment = pfr.getPackageFragment(packageName);
			if (packageFragment.exists()){
				return packageFragment;
			}
		}
		//the existing package fragment was not found
		throw new IllegalStateException("Java package must exist for source class"); //$NON-NLS-1$
	}
	
	private IPackageFragment emptyPackageFragment() {
		return this.getFirstJavaSourceFolder().getPackageFragment(""); //$NON-NLS-1$
	}
	
	private IPackageFragmentRoot getFirstJavaSourceFolder() {
		Iterator<IPackageFragmentRoot> i = JDTTools.getJavaSourceFolders(this.getJavaProject()).iterator();
		return i.hasNext() ? i.next() : null;
	}
	
	private boolean classIsInDefaultPackage(String sourceClassName) {
		return ! sourceClassName.contains("."); //$NON-NLS-1$
	}

	private IStreamMonitor getConsoleOutputStream(ILaunch launch) {
		return (launch.getProcesses()[0]).getStreamsProxy().getOutputStreamMonitor();
	}
	
	private IStreamListener buildConsoleOutputStreamListener() {
		return new IStreamListener() {
            public void streamAppended(String text, IStreamMonitor monitor) {
            	if(text.indexOf(JptJaxbCoreMessages.SCHEMA_GENERATED) > -1) {
            		String[] texts = text.split(" ");  //$NON-NLS-1$
            		// stripExtension to remove unwanted ending char
                	SchemaGenerator.this.generatedNames.add(FileTools.stripExtension(texts[texts.length - 1]));
                }
            }
        };
	}
}
