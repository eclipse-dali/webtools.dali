/*******************************************************************************
 * Copyright (c) 2007, 2024 Oracle and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.JdkLogChute;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.manipulation.ConvertLineDelimitersOperation;
import org.eclipse.core.filebuffers.manipulation.FileBufferOperationRunner;
import org.eclipse.core.filebuffers.manipulation.TextFileBufferOperation;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.gen.JptJpaGenMessages;
import org.eclipse.jpt.jpa.gen.internal.plugin.JptJpaGenPlugin;
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

	public static void generate(JpaProject jpaProject, ORMGenCustomizer customizer, OverwriteConfirmer overwriteConfirmer, IProgressMonitor monitor, boolean generateXml) throws CoreException {
		SubMonitor sm = SubMonitor.convert(monitor, 20);
		PackageGenerator generator = new PackageGenerator(jpaProject, customizer, overwriteConfirmer);
		sm.worked(1);
		try {
			if (generateXml) {
				generator.doXmlGenerate(sm.newChild(19));
			}
			else {
				generator.doGenerate(sm.newChild(19));
			}
		} catch (Exception e) {
			throw new CoreException(JptJpaGenPlugin.instance().buildErrorStatus(JptJpaGenMessages.ERROR_GENERATING_ENTITIES, e));
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

	protected void doXmlGenerate(IProgressMonitor monitor) throws Exception {
		generateXmlInternal(monitor);
	}

	protected void generateInternal(IProgressMonitor monitor) throws Exception {
		File templDir = prepareTemplatesFolder("templates/entities/"); //$NON-NLS-1$

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
		JptXmlResource resource = this.jpaProject.getPersistenceXmlResource();
		if (resource == null) {
			//the resource would only be null if the persistence.xml file had an invalid content type,
			//do not attempt to update
			return;
		}

		Persistence persistence = this.jpaProject.getContextRoot().getPersistenceXml().getRoot();
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
			persistenceUnit = persistence.getPersistenceUnit(0);
		}
		for (String className : genClasses) {
			if (IterableTools.isEmpty(persistenceUnit.getMappingFileRefsContaining(className)) && !persistenceUnit.specifiesManagedType(className)) {
				persistenceUnit.addSpecifiedClassRef(className);
			}
		}
		resource.save();
	}

	protected void generateXmlInternal(IProgressMonitor monitor) throws Exception {
		File templDir = prepareTemplatesFolder("templates/xml_entities/"); //$NON-NLS-1$

		List<String> tableNames = this.customizer.getGenTableNames();

		//TODO Need to fix progress monitor
		SubMonitor sm = SubMonitor.convert(monitor, tableNames.size() + 2);

		generateXmlMappingFile(tableNames, templDir.getAbsolutePath(), sm.newChild(1, SubMonitor.SUPPRESS_NONE));

		if (sm.isCanceled()) {
			return;
		}

		updatePersistenceXmlForMappingFile(this.customizer.getXmlMappingFile());

		sm.worked(2);
	}

	private File prepareTemplatesFolder(String templatesPath) throws IOException, Exception,
			CoreException {
		//Prepare the Velocity template folder:
		//If the plug-in is packaged as a JAR, we need extract the template
		//folder into the plug-in state location. This is required by Velocity
		//since we use included templates.
		Bundle bundle = JptJpaGenPlugin.instance().getBundle();
		Path path = new Path( templatesPath);
		URL url = FileLocator.find(bundle, path, null);
		if (url == null) {
			throw new CoreException(JptJpaGenPlugin.instance().buildErrorStatus(JptJpaGenMessages.TEMPLATES_NOT_FOUND));
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
			throw new CoreException(JptJpaGenPlugin.instance().buildErrorStatus(JptJpaGenMessages.TEMPLATES_NOT_FOUND));
		}
		return templDir;
	}

	private void updatePersistenceXmlForMappingFile(final String mappingFile) {
		JptXmlResource resource = this.jpaProject.getPersistenceXmlResource();
		if (resource == null) {
			//the resource would only be null if the persistence.xml file had an invalid content type,
			//do not attempt to update
			return;
		}

		Persistence persistence = this.jpaProject.getContextRoot().getPersistenceXml().getRoot();
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
			persistenceUnit = persistence.getPersistenceUnit(0);
		}
		boolean addSpecifiedMappingFile = true;
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFileRefs()) {
			if (mappingFileRef.getFileName().equals(mappingFile)){
				addSpecifiedMappingFile = false;
			}
		}
		if (addSpecifiedMappingFile){
			persistenceUnit.addSpecifiedMappingFileRef(mappingFile);
			resource.save();
		}
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

		String subTaskName = NLS.bind(JptJpaGenMessages.ENTITY_GENERATOR_TASK_NAME, table.getName());
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
			//Massage TCCL to deal with bug in m2e - see 396554
			ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(ve.getClass().getClassLoader());
			try {
		    	ve.init(vep);
			} finally {
				Thread.currentThread().setContextClassLoader(oldClassLoader);
			}

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
			JptJpaGenPlugin.instance().logError(e, JptJpaGenMessages.ERROR_GENERATING_ENTITIES);
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

		convertLineDelimiter(javaFile);
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
				JptJpaGenPlugin.instance().logError(e);
			}
		}
		return defaultSrcPath;
	}

	protected void generateXmlMappingFile(List<String> tableNames, String templateDirPath, IProgressMonitor monitor) throws Exception {

		try {
			String xmlMappingFileLocation = this.customizer.getXmlMappingFile();
			JptXmlResource xmlResource = this.jpaProject.getMappingFileXmlResource(new Path(xmlMappingFileLocation));
			IFile xmlFile;
			if (xmlResource != null) {
				xmlFile = xmlResource.getFile();
			}
			else{
				//TODO We currently don't support mapping files very well if in non source/class folders so force file
				//into the know default directory for resources to ensure that things work.
				IProject project = jpaProject.getProject();
				IContainer container = project.getAdapter(ProjectResourceLocator.class).getDefaultLocation();
				xmlFile = container.getFile(new Path(xmlMappingFileLocation.substring(xmlMappingFileLocation.lastIndexOf("/")))); //$NON-NLS-1$
			}

			if (xmlFile.exists()) {
				if (this.overwriteConfirmer != null && !this.overwriteConfirmer.overwrite(xmlFile.getName())) {
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
			//Massage TCCL to deal with bug in m2e - 396554
			ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(ve.getClass().getClassLoader());
			try {
		    	ve.init(vep);
			} finally {
				Thread.currentThread().setContextClassLoader(oldClassLoader);
			}

		    StringBuilder xmlFileContents = new StringBuilder();
		    xmlFileContents.append(generateXmlHeaderFooter(ve, "header.vm")); //$NON-NLS-1$

		    // Build sample named queries
		    for (Iterator<String> names = tableNames.iterator(); names.hasNext();) {
		    	ORMGenTable table = this.customizer.getTable(names.next());
		    	xmlFileContents.append(generateXmlTypeMetadata(table, ve, "namedQuery.vm"));
		    }

		    List<ORMGenTable> compositeKeyTables = new ArrayList<ORMGenTable>();

			for (Iterator<String> names = tableNames.iterator(); names.hasNext();) {

				ORMGenTable table = this.customizer.getTable(names.next());
				String subTaskName = NLS.bind(JptJpaGenMessages.ENTITY_GENERATOR_TASK_NAME, table.getName());
				SubMonitor sm = SubMonitor.convert(monitor, subTaskName, 10);

				if (sm.isCanceled()) {
					return;
				}

				xmlFileContents.append(generateXmlTypeMetadata(table, ve, "main.xml.vm")); //$NON-NLS-1$

				if (table.isCompositeKey()) {
					compositeKeyTables.add(table);
				}
			}

			//Embeddables need to come after entities in the XML
			for (ORMGenTable table : compositeKeyTables) {
				SubMonitor sm = SubMonitor.convert(monitor, NLS.bind(JptJpaGenMessages.ENTITY_GENERATOR_TASK_NAME, table.getName()), 1);
			    if (table.isCompositeKey()) {
			    	xmlFileContents.append(generateXmlTypeMetadata(table, ve, "embeddable.vm")); //$NON-NLS-1$
			    }
			}

			xmlFileContents.append(generateXmlHeaderFooter(ve, "footer.vm")); //$NON-NLS-1$

			if(xmlFile.exists()){
				byte[] content = xmlFileContents.toString().getBytes(xmlFile.getCharset());
				xmlFile.setContents(new ByteArrayInputStream(content), false, true, null);
			}
			else {
				byte[] content = xmlFileContents.toString().getBytes(xmlFile.getCharset());
				createFile(xmlFile, new ByteArrayInputStream(content));
			}

		    xmlFile.refreshLocal(1, null);

		} catch (Throwable e) {
			JptJpaGenPlugin.instance().logError(e, JptJpaGenMessages.ERROR_GENERATING_ENTITIES);
		}
	}

	private String generateXmlHeaderFooter(VelocityEngine ve, String templateName) throws Exception{
		StringWriter stringWriter = new StringWriter();
		VelocityContext context = new VelocityContext();
		context.put("customizer", getCustomizer());
		ve.mergeTemplate(templateName, context, stringWriter);
		return stringWriter.toString();
	}


	private String generateXmlTypeMetadata(ORMGenTable table, VelocityEngine ve
			, String templateName) throws Exception {
		VelocityContext context = new VelocityContext();
        context.put("table", table); //$NON-NLS-1$
        context.put("customizer", getCustomizer()); //$NON-NLS-1$

		StringWriter w = new StringWriter();
		ve.mergeTemplate(templateName, context, w);

		return w.toString();
	}

	private static void convertLineDelimiter(IFile file) {
		IPath[] paths = new IPath[] {file.getFullPath()};
		ITextFileBufferManager buffManager = FileBuffers.getTextFileBufferManager();
		String lineDelimiter = PlatformTools.getNewTextFileLineDelimiter();
		TextFileBufferOperation convertOperation = new ConvertLineDelimitersOperation(lineDelimiter);
		FileBufferOperationRunner runner = new FileBufferOperationRunner(buffManager, null);
		try {
			runner.execute(paths, convertOperation, new NullProgressMonitor());
		} catch (OperationCanceledException oce) {
			JptJpaGenPlugin.instance().logError(oce);
		} catch (CoreException ce) {
			JptJpaGenPlugin.instance().logError(ce);
		}
	}

}
