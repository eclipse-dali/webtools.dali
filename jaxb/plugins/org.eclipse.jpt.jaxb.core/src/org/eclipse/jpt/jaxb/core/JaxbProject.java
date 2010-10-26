/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.utility.CommandExecutor;

/**
 * A JAXB project is associated with an Eclipse project (and its corresponding
 * Java project). It holds the "resource" model that corresponds to the various
 * JPA-related resources (the <code>persistence.xml</code> file, its mapping files
 * [<code>orm.xml</code>],
 * and the Java source files). It also holds the "context" model that represents
 * the JPA metadata, as derived from spec-defined defaults, Java source code
 * annotations, and XML descriptors.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbProject
	extends JaxbNode
{

	// ********** general **********

	/**
	 * Return the JAXB project's name, which is the same as the associated
	 * Eclipse project's name.
	 */
	String getName();

	/**
	 * Return the Eclipse project associated with the JAXB project.
	 */
	IProject getProject();

	/**
	 * Return the Java project associated with the JAXB project.
	 */
	IJavaProject getJavaProject();

	/**
	 * Return the vendor-specific JAXB platform that builds the JAXB project
	 * and its contents.
	 */
	JaxbPlatform getJaxbPlatform();
//
//	/**
//	 * Return the root of the JPA project's context model.
//	 */
//	JpaRootContextNode getRootContextNode();

	/**
	 * The JAXB project has been removed from the JJAXBPA model. Clean up any
	 * hooks to external resources etc.
	 */
	void dispose();


	// ********** JAXB files **********

	/** 
	 * ID string used when the JAXB project's collection of JAXB files changes.
	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.utility.model.listener.CollectionChangeListener)
	 */
	String JAXB_FILES_COLLECTION = "jaxbFiles"; //$NON-NLS-1$

	/**
	 * Return the JAXB project's JAXB files.
	 */
	Iterable<JaxbFile> getJaxbFiles();

	/**
	 * Return the size of the JAXB project's JAXB files.
	 */
	int getJaxbFilesSize();

	/**
	 * Return the JAXB file corresponding to the specified file.
	 * Return null if there is no JAXB file associated with the specified file.
	 */
	JaxbFile getJaxbFile(IFile file);


//	// ********** external Java resource compilation units **********
//
//	/** 
//	 * ID string used when the JPA project's collection of external Java
//	 * resource compilation units changes.
//	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.utility.model.listener.CollectionChangeListener)
//	 */
//	String EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION = "externalJavaResourceCompilationUnits"; //$NON-NLS-1$
//
//	/**
//	 * Return the JPA project's external Java resource compilation units.
//	 */
//	Iterator<JavaResourceCompilationUnit> externalJavaResourceCompilationUnits();
//
//	/**
//	 * Return the size of the JPA project's external Java resource compilation units.
//	 */
//	int externalJavaResourceCompilationUnitsSize();
//
//
//	// ********** external Java resource persistent types **********
//
//	/**
//	 * Return the JPA project's external Java resource persistent type cache.
//	 */
//	JavaResourcePersistentTypeCache getExternalJavaResourcePersistentTypeCache();
//
//
//	// ********** XML resources **********
//	
//	/**
//	 * Return the XML resource model corresponding to the file with runtime path
//	 * <code>META-INF/persistence.xml</code> if that file has the persistence content type
//	 * (<code>"org.eclipse.jpt.core.content.persistence"</code>).
//	 * 
//	 * @see JptCorePlugin#DEFAULT_PERSISTENCE_XML_RUNTIME_PATH
//	 * @see JptCorePlugin#PERSISTENCE_XML_CONTENT_TYPE
//	 */
//	JpaXmlResource getPersistenceXmlResource();
//	
//	/**
//	 * Return the XML resource model corresponding to the file with the specified
//	 * runtime path if that file has the mapping file content type
//	 * (<code>"org.eclipse.jpt.core.content.mappingFile"</code>)
//	 * 
//	 * @see JptCorePlugin#MAPPING_FILE_CONTENT_TYPE
//	 */
//	JpaXmlResource getMappingFileXmlResource(IPath runtimePath);
//
//	/**
//	 * Return the XML resource model corresponding to the file
//	 * <code>META-INF/orm.xml</code> if that file has the mapping file content type.
//	 * 
//	 * @see JptCorePlugin#DEFAULT_ORM_XML_RUNTIME_PATH
//	 */
//	JpaXmlResource getDefaultOrmXmlResource();
//	
//	
//	// ********** Java resources **********
//
//	/**
//	 * Return the names of the JPA project's annotated Java classes
//	 * (ignoring classes in JARs referenced in the persistence.xml).
//	 */
//	Iterator<String> annotatedJavaSourceClassNames();
//	
//	/**
//	 * Return the names of the JPA project's mapped (i.e. annotated with @Entity, etc.) Java 
//	 * classes (ignoring classes in JARs referenced in the persistence.xml).
//	 */
//	Iterable<String> getMappedJavaSourceClassNames();
//
//	/**
//	 * Return the Java resource persistent type for the specified type.
//	 * Return null if invalid or absent.
//	 */
//	JavaResourcePersistentType getJavaResourcePersistentType(String typeName);
//
//	/**
//	 * Return the Java resource package for the specified package.
//	 * Return null if invalid or absent.
//	 */
//	JavaResourcePackage getJavaResourcePackage(String packageName);
//
//	/**
//	 * Return the Java resource packages for the project.
//	 * Return null if invalid or absent.
//	 */
//	Iterable<JavaResourcePackage> getJavaResourcePackages();
//
//	/**
//	 * Return the Java resource package fragement root for the specified JAR.
//	 * Return null if absent.
//	 */
//	JavaResourcePackageFragmentRoot getJavaResourcePackageFragmentRoot(String jarFileName);


	// ********** model synchronization **********

	/**
	 * Synchronize the JPA project with the specified project resource
	 * delta, watching for added and removed files in particular.
	 */
	void projectChanged(IResourceDelta delta);

	/**
	 * Synchronize the JPA project with the specified Java change.
	 */
	void javaElementChanged(ElementChangedEvent event);


	// ********** synchronize context model with resource model **********

	void synchronizeContextModel();


	// ********** project "update" **********

	/**
	 * Return the implementation of the Updater
	 * interface that will be used to "update" the JPA project.
	 */
	Updater getUpdater();

	/**
	 * Set the implementation of the Updater
	 * interface that will be used to "update" the JPA project.
	 * Before setting the updater, clients should save the current updater so
	 * it can be restored later.
	 */
	void setUpdater(Updater updater);

	/**
	 * The JPA project's state has changed, "update" those parts of the
	 * JPA project that are dependent on other parts of the JPA project.
	 * This is called when<ul>
	 * <li>(almost) any state in the JPA project changes
	 * <li>the JPA project's database connection is changed, opened, or closed
	 * </ul>
	 */
	void update();

	/**
	 * This is the callback used by the updater to perform the actual
	 * "update", which most likely will happen asynchronously.
	 */
	IStatus update(IProgressMonitor monitor);

	/**
	 * This is the callback used by the updater to notify the JPA project that
	 * the "update" has quiesced (i.e. the "update" has completed and there
	 * are no outstanding requests for further "updates").
	 */
	void updateQuiesced();


	/**
	 * Define a strategy that can be used to "update" a JPA project whenever
	 * something changes.
	 */
	interface Updater {

		/**
		 * The updater has just been assigned to its JPA project.
		 */
		void start();

		/**
		 * Update the JPA project.
		 * <p>
		 * {@link JaxbProject#update()} will call {@link Updater#update()},
		 * from which the updater is to call {@link JaxbProject#update(IProgressMonitor)}
		 * as appropriate (typically from an asynchronously executing job).
		 * Once the updating has quiesced (i.e. there are no outstanding requests
		 * for another update), the updater is to call {@link JaxbProject#updateQuiesced()}.
		 */
		void update();

		/**
		 * The JPA project is disposed; stop the updater.
		 */
		void stop();

		/**
		 * This updater does nothing. Useful for testing.
		 */
		final class Null implements Updater {
			private static final Updater INSTANCE = new Null();
			public static Updater instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			public void start() {
				// do nothing
			}
			public void update() {
				// do nothing
			}
			public void stop() {
				// do nothing
			}
			@Override
			public String toString() {
				return "JpaProject.Updater.Null"; //$NON-NLS-1$
			}
		}

	}


//	// ********** utility **********
//
//	/**
//	 * Return an {@link IFile} that best represents the given runtime location
//	 */
//	IFile getPlatformFile(IPath runtimePath);
//
//
//	// ********** validation **********
//
//	/**
//	 * Return JPA project's validation messages.
//	 */
//	Iterator<IMessage> validationMessages(IReporter reporter);



	// ********** modifying shared documents **********

	/**
	 * Set a thread-specific implementation of the {@link CommandExecutor}
	 * interface that will be used to execute a command to modify a shared
	 * document. If necessary, the command executor can be cleared by
	 * setting it to null.
	 * This allows background clients to modify documents that are
	 * already present in the UI. See implementations of {@link CommandExecutor}.
	 */
	void setThreadLocalModifySharedDocumentCommandExecutor(CommandExecutor commandExecutor);

	/**
	 * Return the project-wide implementation of the
	 * {@link CommandExecutor} interface.
	 */
	CommandExecutor getModifySharedDocumentCommandExecutor();


	// ********** construction config **********

	/**
	 * The settings used to construct a JAXB project.
	 */
	interface Config {

		/**
		 * Return the Eclipse project to be associated with the new JPA project.
		 */
		IProject getProject();

		/**
		 * Return the JPA platform to be associated with the new JPA project.
		 */
		JaxbPlatform getJaxbPlatform();

	}

}
