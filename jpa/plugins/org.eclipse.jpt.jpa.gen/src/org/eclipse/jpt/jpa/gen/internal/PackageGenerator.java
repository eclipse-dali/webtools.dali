/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.gen.internal;

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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.gen.internal.util.CompilationUnitModifier;
import org.eclipse.jpt.jpa.gen.internal.util.FileUtil;
import org.eclipse.jpt.jpa.gen.internal.util.UrlUtil;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

/**
 * This generator will generate a package of entities for a set of tables.
 */
public class PackageGenerator { 

	private static final String LOGGER_NAME = "org.eclipse.jpt.entities.gen.log"; //$NON-NLS-1$
	private final JpaProject jpaProject;
	private final ORMGenCustomizer customizer;
	private final OverwriteConfirmer overwriteConfirmer;

	public static void generate(JpaProject jpaProject, ORMGenCustomizer customizer, OverwriteConfirmer overwriteConfirmer, IProgressMonitor monitor) throws CoreException {
		SubMonitor sm = SubMonitor.convert(monitor, 20);
		PackageGenerator generator = new PackageGenerator(jpaProject, customizer, overwriteConfirmer);
		sm.worked(1);
		try {
			generator.doGenerate(sm.newChild(19));
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, JptJpaGenPlugin.PLUGIN_ID, JptGenMessages.Error_Generating_Entities, e));
		}
	}
	
	private PackageGenerator(JpaProject jpaProject, ORMGenCustomizer customizer, OverwriteConfirmer confirmer) {
		super();
		this.jpaProject = jpaProject;
		this.customizer = customizer;
		this.overwriteConfirmer = confirmer;
	}

	private Object getCustomizer() {
		return this.customizer;
	}

	private IJavaProject getJavaProject(){
		return this.jpaProject.getJavaProject();
	}

	
	protected void doGenerate(IProgressMonitor monitor) throws Exception {
		generateInternal(monitor);
	}

	protected void generateInternal(IProgressMonitor monitor) throws Exception {
		File templDir = prepareTemplatesFolder();

		List<String> genClasses = new java.util.ArrayList<String>();
		List<String> tableNames = this.customizer.getGenTableNames();

		/* .java per table, persistence.xml, refresh package folder */
		SubMonitor sm = SubMonitor.convert(monitor, tableNames.size() + 2);


		for (Iterator<String> iter = tableNames.iterator(); iter.hasNext();) {
			if (sm.isCanceled()) {
				return;
			}
			String tableName = iter.next();
			ORMGenTable table = this.customizer.getTable(tableName);

			String className = table.getQualifiedClassName();

			generateClass(table, templDir.getAbsolutePath(), sm.newChild(1, SubMonitor.SUPPRESS_NONE));

			genClasses.add(className);
			/*
			 * add the composite key class to persistence.xml because some 
			 * JPA provider(e.g. Kodo) requires it. Hibernate doesn't seem to care). 
			 */
			if (table.isCompositeKey()) {
				genClasses.add(table.getQualifiedCompositeKeyClassName());
			}
		}
		if (sm.isCanceled()) {
			return;
		}
		
		//update persistence.xml
		if (this.customizer.updatePersistenceXml()) {
			updatePersistenceXml(genClasses);
		}
		sm.worked(2);
	}
	
	private void updatePersistenceXml(final List<String> genClasses) {
		JpaXmlResource resource = this.jpaProject.getPersistenceXmlResource();
		if (resource == null) {
			//the resource would only be null if the persistence.xml file had an invalid content type,
			//do not attempt to update
			return;
		}

		resource.modify(new Runnable() {
			public void run() {
				Persistence persistence = PackageGenerator.this.jpaProject.getRootContextNode().getPersistenceXml().getPersistence();
				if (persistence == null) {
					// invalid content, do not attempt to update
					return;
				}
				PersistenceUnit persistenceUnit;
				// create a persistence unit if one doesn't already exist
				if (persistence.getPersistenceUnitsSize() == 0) {
					persistenceUnit = persistence.addPersistenceUnit();
					persistenceUnit.setName(PackageGenerator.this.jpaProject.getName());
				} else {
					// we only support one persistence unit - take the first one
					persistenceUnit = persistence.getPersistenceUnits().iterator().next();
				}
				for (Iterator<String> stream = genClasses.iterator(); stream.hasNext();) {
					String className = stream.next();
					if (CollectionTools.isEmpty(persistenceUnit.getMappingFileRefsContaining(className)) && !persistenceUnit.specifiesPersistentType(className)) {
						persistenceUnit.addSpecifiedClassRef(className);
					}
				}
			}
		});
	}

	private File prepareTemplatesFolder() throws IOException, Exception,
			CoreException {
		//Prepare the Velocity template folder:
		//If the plug-in is packaged as a JAR, we need extract the template 
		//folder into the plug-in state location. This is required by Velocity
		//since we use included templates.
		Bundle bundle = Platform.getBundle(JptJpaGenPlugin.PLUGIN_ID);
		String templatesPath = "templates/entities/";  //$NON-NLS-1$
		Path path = new Path( templatesPath);
		URL url = FileLocator.find(bundle, path, null);
		if (url == null) {
			throw new CoreException(new Status(IStatus.ERROR, JptJpaGenPlugin.PLUGIN_ID,  JptGenMessages.Templates_notFound + " "+  JptJpaGenPlugin.PLUGIN_ID + "/" + templatesPath) );//$NON-NLS-1$
		}		
		URL templUrl = FileLocator.resolve(url);
		
		//Have this check so that the code would work in both PDE and JARed plug-in at runtime
		File templDir = null;
		if (UrlUtil.isJarUrl(templUrl)) {
			templDir = FileUtil.extractFilesFromBundle( templUrl, bundle, templatesPath );
		} else {
			templDir = UrlUtil.getUrlFile(templUrl);
		}
		

		if (templDir == null || !templDir.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, JptJpaGenPlugin.PLUGIN_ID,  JptGenMessages.Templates_notFound + " "+  JptJpaGenPlugin.PLUGIN_ID ) );//$NON-NLS-1$
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
		SubMonitor sm = SubMonitor.convert(monitor, subTaskName, 10);

		try {
			IFolder javaPackageFolder = getJavaPackageFolder(table, monitor);
			IFile javaFile = javaPackageFolder.getFile( table.getClassName() + ".java"); //$NON-NLS-1$
			
			if (javaFile.exists()) {
				if (this.overwriteConfirmer != null && !this.overwriteConfirmer.overwrite(javaFile.getName())) {
					return;
				}
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
		    sm.worked(2);
		    
		    generateJavaFile(table, javaFile, ve, "main.java.vm", true/*isDomainClass*/, sm.newChild(6)); //$NON-NLS-1$
		    
		    if (table.isCompositeKey()) {
		    	IFile compositeKeyFile = javaPackageFolder.getFile( table.getCompositeKeyClassName()+".java"); //$NON-NLS-1$
		    	generateJavaFile(table, compositeKeyFile, ve, "pk.java.vm", false/*isDomainClass*/, sm.newChild(1)); //$NON-NLS-1$
		    }
		    else {
		    	sm.setWorkRemaining(1);
		    }
			javaFile.refreshLocal(1, sm.newChild(1));
			
		} catch (Throwable e) {
			CoreException ce = new CoreException(new Status(IStatus.ERROR, JptJpaGenPlugin.PLUGIN_ID, JptGenMessages.Templates_notFound + "" + JptJpaGenPlugin.PLUGIN_ID , e) );//$NON-NLS-1$
			JptJpaGenPlugin.logException( ce );
		}
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
				byte[] content = fileContent.getBytes(javaFile.getCharset());
				javaFile.setContents(new ByteArrayInputStream(content), true, true, monitor);
			}
		} else {
			byte[] content = fileContent.getBytes(javaFile.getCharset());
			createFile(javaFile, new ByteArrayInputStream(content));
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
		CompilationUnitModifier modifier = new CompilationUnitModifier(this.getJavaProject(), className);
		modifier.setJavaSource(fileContent);
		modifier.save();
	}
	
	public void createFile(IFile file, java.io.InputStream contents) throws CoreException {		
		file.create(contents, false, null/*monitor*/);
	}
	
	public IFolder getJavaPackageFolder(ORMGenTable table, IProgressMonitor monitor) throws CoreException {
		IPackageFragmentRoot root = getDefaultJavaSourceLocation(this.getJavaProject(), table.getSourceFolder());
		String packageName = table.getPackage();
		if (packageName == null) packageName = ""; //$NON-NLS-1$
		IPackageFragment packageFragment = root.getPackageFragment(packageName);
		if( !packageFragment.exists()){
			root.createPackageFragment(packageName, true, monitor);
		}		
		return (IFolder) packageFragment.getResource();
	}

	private IPackageFragmentRoot getDefaultJavaSourceLocation(IJavaProject jproject, String sourceFolder){
		IPackageFragmentRoot defaultSrcPath = null;
		if (jproject != null && jproject.exists()) {
			try {
				IPackageFragmentRoot[] roots = jproject.getPackageFragmentRoots();
				for (int i = 0; i < roots.length; i++) {
					if (roots[i].getKind() == IPackageFragmentRoot.K_SOURCE ){
						if (defaultSrcPath == null) {
							defaultSrcPath = roots[i];
						}
						String path = roots[i].getPath().toString(); 
						if (path.equals('/' + sourceFolder)) {
							return roots[i] ; 
						}
					}
				}
			} catch (JavaModelException e) {
				JptJpaGenPlugin.logException(e);
			}
		}
		return defaultSrcPath;
	}
}
