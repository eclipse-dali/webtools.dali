/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.gen.internal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.JdkLogChute;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.gen.internal.util.CompilationUnitModifier;
import org.eclipse.jpt.gen.internal.util.FileUtil;
import org.eclipse.jpt.gen.internal.util.UrlUtil;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

/**
 * This generator will generate a package of entities for a set of tables.
 */
public class PackageGenerator2 { 

	private static final String LOGGER_NAME = "org.eclipse.jpt.entities.gen.log"; //$NON-NLS-1$
	private IJavaProject javaProject;
	private ORMGenCustomizer customizer ; 
	private static OverwriteConfirmer overwriteConfirmer = null;

	static public void setOverwriteConfirmer(OverwriteConfirmer confirmer){
		overwriteConfirmer = confirmer;
	}
	/**
	 * @param customizer
	 * @param synchronizePersistenceXml
	 * @param copyJdbcDrive
	 * @throws Exception 
	 */
	static public void generate(IJavaProject jpaProject, ORMGenCustomizer customizer, IProgressMonitor monitor ) throws CoreException {
		PackageGenerator2 generator = new PackageGenerator2();
		generator.setProject(jpaProject);
		generator.setCustomizer(customizer);
		
		try {
			generator.doGenerate(  monitor);
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, JptGenPlugin.PLUGIN_ID, JptGenMessages.Error_Generating_Entities, e));
		}
	}
	
	private Object getCustomizer() {
		return this.customizer;
	}

	private void setCustomizer(ORMGenCustomizer customizer2) {
		this.customizer = customizer2;
	}
	
	private void setProject(IJavaProject proj ){
		this.javaProject = proj;
	}
	
	protected void doGenerate(  IProgressMonitor monitor ) throws Exception {
		generateInternal( monitor );
	}

	protected void generateInternal( IProgressMonitor progress) throws Exception {
		File templDir = prepareTemplatesFolder();

		List<String> genClasses = new java.util.ArrayList<String>();
		List<String> tableNames = customizer.getGenTableNames();

		/* .java per table, persistence.xml, refresh package folder */
		String taskName = NLS.bind(JptGenMessages.EntityGenerator_taskName, "Total "+ tableNames.size() + 2);//$NON-NLS-1$
		progress.beginTask(taskName, tableNames.size() + 2);


		for (Iterator<String> iter = tableNames.iterator(); iter.hasNext();) {
			String tableName = iter.next();
			ORMGenTable table = customizer.getTable(tableName);

			String className = table.getQualifiedClassName();

			generateClass(table, templDir.getAbsolutePath(), progress);
			progress.worked(1);

			genClasses.add( className );
			/*
			 * add the composite key class to persistence.xml because some 
			 * JPA provider(e.g. Kodo) requires it. Hibernate doesn't seem to care). 
			 */
			if (table.isCompositeKey()) {
				genClasses.add(table.getQualifiedCompositeKeyClassName());
			}
		}
		progress.done();
	}

	private File prepareTemplatesFolder() throws IOException, Exception,
			CoreException {
		//Prepare the Velocity template folder:
		//If the plug-in is packaged as a JAR, we need extract the template 
		//folder into the plug-in state location. This is required by Velocity
		//since we use included templates.
		Bundle bundle = Platform.getBundle(JptGenPlugin.PLUGIN_ID);
		String templatesPath = "templates/entities/";  //$NON-NLS-1$
		Path path = new Path( templatesPath);
		URL url = FileLocator.find(bundle, path, null);
		if ( url ==null  ) {
			throw new CoreException(new Status(IStatus.ERROR, JptGenPlugin.PLUGIN_ID,  JptGenMessages.Templates_notFound + " "+  JptGenPlugin.PLUGIN_ID + "/" + templatesPath) );//$NON-NLS-1$
		}		
		URL templUrl = FileLocator.resolve(url);
		
		//Have this check so that the code would work in both PDE and JARed plug-in at runtime
		File templDir = null;
		if( UrlUtil.isJarUrl(templUrl) ){
			templDir = FileUtil.extractFilesFromBundle( templUrl, bundle, templatesPath );
		}else{
			templDir = UrlUtil.getUrlFile(templUrl);
		}
		

		if (templDir==null || !templDir.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, JptGenPlugin.PLUGIN_ID,  JptGenMessages.Templates_notFound + " "+  JptGenPlugin.PLUGIN_ID ) );//$NON-NLS-1$
		}
		return templDir;
	}
	
	/**
	 * Saves/Creates the .java file corresponding to a database table 
	 * with the given content.
	 * 
	 * @param templDir The velocity template file directory. It is assumed 
	 * that this directory contains the 2 files <em>main.java.vm</em> 
	 * and <em>pk.java.vm</em> 
	 * @param progress 
	 */
	protected void generateClass(ORMGenTable table, String templateDirPath, IProgressMonitor monitor) throws Exception {

		String subTaskName = NLS.bind(JptGenMessages.EntityGenerator_taskName, table.getName());
		SubMonitor sm = SubMonitor.convert(monitor, subTaskName, 100);

		try{
			IFolder javaPackageFolder = getJavaPackageFolder(table, monitor);
			IFile javaFile = javaPackageFolder.getFile( table.getClassName() + ".java"); //$NON-NLS-1$
			
			if( javaFile.exists() ){
				if( overwriteConfirmer!=null && !overwriteConfirmer.overwrite(javaFile.getName()) )
					return;
			}
			//JdkLogChute in this version of Velocity not allow to set log level
			//Workaround by preset the log level before Velocity is initialized
			Logger logger = Logger.getLogger( LOGGER_NAME );
			logger.setLevel( Level.SEVERE );
			
			Properties vep = new Properties();
			vep.setProperty("file.resource.loader.path", templateDirPath); //$NON-NLS-1$
			vep.setProperty( JdkLogChute.RUNTIME_LOG_JDK_LOGGER, LOGGER_NAME );
			VelocityEngine ve = new VelocityEngine();
		    ve.init(vep);
		    sm.worked(20);
		    
		    generateJavaFile(table, javaFile, ve, "main.java.vm", true/*isDomainClass*/, monitor); //$NON-NLS-1$
		    sm.worked(80);
		    
		    if (table.isCompositeKey()) {
		    	IFile compositeKeyFile = javaPackageFolder.getFile( table.getCompositeKeyClassName()+".java"); //$NON-NLS-1$
		    	generateJavaFile(table, compositeKeyFile, ve, "pk.java.vm", false/*isDomainClass*/, monitor ); //$NON-NLS-1$
		    }
		    
			javaFile.refreshLocal(1, new NullProgressMonitor());
			
		} catch(Throwable e){
			CoreException ce = new CoreException(new Status(IStatus.ERROR, JptGenPlugin.PLUGIN_ID, JptGenMessages.Templates_notFound + "" + JptGenPlugin.PLUGIN_ID , e) );//$NON-NLS-1$
			JptGenPlugin.logException( ce );
		}
		sm.setWorkRemaining(0);
	}
	
	private void generateJavaFile(ORMGenTable table, IFile javaFile, VelocityEngine ve
			, String templateName, boolean isDomainClass, IProgressMonitor monitor) throws Exception {
		VelocityContext context = new VelocityContext();
        context.put("table", table); //$NON-NLS-1$
        context.put("customizer", getCustomizer()); //$NON-NLS-1$
        
		StringWriter w = new StringWriter();
		ve.mergeTemplate(templateName, context, w);
		
		String fileContent = w.toString();
		if (javaFile.exists()) {
			if (isDomainClass) {
				updateExistingDomainClass(table.getQualifiedClassName(), javaFile, fileContent);
			} else {
				javaFile.setContents(new ByteArrayInputStream(fileContent.getBytes()), true, true, monitor );
			}
		} else {
			createFile(javaFile, new ByteArrayInputStream(fileContent.getBytes()));
		}		
	}
	
	
	/**
	 * Updates the (existing) Java file corresponding to the given class.
	 * 
	 * @param className The qualified class name.
	 * 
	 * @param javaFile The existing Java file of the class to update.
	 * 
	 * @param fileContent The new file content.
	 */
	protected void updateExistingDomainClass(String className, IFile javaFile, String fileContent) throws Exception {
		/*use CompilationUnitModifier instead of calling WideEnv.getEnv().setFileContent 
		 * so that if the unit is up to date if it is used before file change 
		 * notifications are delivered (see EJB3ImportSchemaWizard.updateExistingDomainClass for example)*/
		IJavaProject project = javaProject.getJavaProject();
		CompilationUnitModifier modifier = new CompilationUnitModifier(project, className);
		modifier.setJavaSource(fileContent);
		modifier.save();
	}
	
	public void createFile(IFile file, java.io.InputStream contents) throws CoreException {		
		file.create(contents, false, null/*monitor*/);
	}
	
	public IFolder getJavaPackageFolder(ORMGenTable table, IProgressMonitor monitor) throws CoreException {
		IPackageFragmentRoot root = getDefaultJavaSrouceLocation ( javaProject , table.getSourceFolder()) ;
		String packageName = table.getPackage();
		if( packageName==null ) packageName ="";
		IPackageFragment packageFragment = root.getPackageFragment(packageName);
		if( !packageFragment.exists()){
			root.createPackageFragment(packageName, true, monitor);
		}		
		return (IFolder)packageFragment.getResource();
	}
	
	private IPackageFragmentRoot getDefaultJavaSrouceLocation(IJavaProject jproject, String sourceFolder){
		IPackageFragmentRoot defaultSrcPath = null;
		if (jproject != null) {
			try {
				if (jproject.exists()) {
					IPackageFragmentRoot[] roots = jproject.getPackageFragmentRoots();
					for (int i= 0; i < roots.length; i++) {
						if (roots[i].getKind() == IPackageFragmentRoot.K_SOURCE ){
							if( i == 0 ) 
								defaultSrcPath = roots[i];
							String path = roots[i].getPath().toString(); 
							if( path.equals( "/"+sourceFolder )) {
								return roots[i] ; 
							}
						}
					}							
				}
			} catch (JavaModelException e) {
				JptGenPlugin.logException(e);
			}
		}
		return defaultSrcPath;
	}

	
	// ********** annotation name builder **********

	
}
