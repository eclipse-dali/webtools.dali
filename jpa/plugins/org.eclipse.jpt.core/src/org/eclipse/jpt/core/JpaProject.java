/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentTypeCache;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * A JPA project is associated with an Eclipse project (and its corresponding
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
 * @version 2.3
 * @since 2.0
 */
public interface JpaProject
	extends JpaNode
{

	// ********** general **********

	/**
	 * Return the JPA project's name, which is the same as the associated
	 * Eclipse project's name.
	 */
	String getName();

	/**
	 * Return the Eclipse project associated with the JPA project.
	 */
	IProject getProject();

	/**
	 * Return the Java project associated with the JPA project.
	 */
	IJavaProject getJavaProject();

	/**
	 * Return the vendor-specific JPA platform that builds the JPA project
	 * and its contents.
	 */
	JpaPlatform getJpaPlatform();

	/**
	 * Return the root of the JPA project's context model.
	 */
	JpaRootContextNode getRootContextNode();

	/**
	 * The JPA project has been removed from the JPA model. Clean up any
	 * hooks to external resources etc.
	 */
	void dispose();


	// ********** JPA files **********

	/** 
	 * ID string used when the JPA project's collection of JPA files changes.
	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.utility.model.listener.CollectionChangeListener)
	 */
	String JPA_FILES_COLLECTION = "jpaFiles"; //$NON-NLS-1$

	/**
	 * Return the JPA project's JPA files.
	 */
	Iterator<JpaFile> jpaFiles();

	/**
	 * Return the size of the JPA project's JPA files.
	 */
	int jpaFilesSize();

	/**
	 * Return the JPA file corresponding to the specified file.
	 * Return null if there is no JPA file associated with the specified file.
	 */
	JpaFile getJpaFile(IFile file);


	// ********** external Java resource compilation units **********

	/** 
	 * ID string used when the JPA project's collection of external Java
	 * resource compilation units changes.
	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.utility.model.listener.CollectionChangeListener)
	 */
	String EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION = "externalJavaResourceCompilationUnits"; //$NON-NLS-1$

	/**
	 * Return the JPA project's external Java resource compilation units.
	 */
	Iterator<JavaResourceCompilationUnit> externalJavaResourceCompilationUnits();

	/**
	 * Return the size of the JPA project's external Java resource compilation units.
	 */
	int externalJavaResourceCompilationUnitsSize();


	// ********** external Java resource persistent types **********

	/**
	 * Return the JPA project's external Java resource persistent type cache.
	 */
	JavaResourcePersistentTypeCache getExternalJavaResourcePersistentTypeCache();


	// ********** XML resources **********

	/**
	 * Return the XML resource model corresponding to the file
	 * <code>META-INF/persistence.xml</code> if that file has the persistence content type
	 * (<code>"org.eclipse.jpt.core.content.persistence"</code>).
	 * 
	 * @see JptCorePlugin.DEFAULT_PERSISTENCE_XML_FILE_PATH
	 */
	JpaXmlResource getPersistenceXmlResource();
	
	/**
	 * Return the XML resource model corresponding to the specified file
	 * if that file has the mapping file content type
	 * (<code>"org.eclipse.jpt.core.content.mappingFile"</code>)
	 * 
	 * @see JptCorePlugin#MAPPING_FILE_CONTENT_TYPE
	 */
	JpaXmlResource getMappingFileXmlResource(String filePath);

	/**
	 * Return the XML resource model corresponding to the file
	 * <code>META-INF/orm.xml</code> if that file has the mapping file content type.
	 * 
	 * @see JptCorePlugin#DEFAULT_ORM_XML_FILE_PATH
	 */
	JpaXmlResource getDefaultOrmXmlResource();
	
	
	// ********** Java resources **********

	/**
	 * Return the names of the JPA project's annotated Java classes
	 * (ignoring classes in JARs referenced in the persistence.xml).
	 */
	Iterator<String> annotatedJavaSourceClassNames();
	
	/**
	 * Return the names of the JPA project's mapped (i.e. annotated with @Entity, etc.) Java 
	 * classes (ignoring classes in JARs referenced in the persistence.xml).
	 */
	Iterator<String> mappedJavaSourceClassNames();

	/**
	 * Return the Java resource persistent type for the specified type.
	 * Return null if invalid or absent.
	 */
	JavaResourcePersistentType getJavaResourcePersistentType(String typeName);

	/**
	 * Return the Java resource package fragement root for the specified JAR.
	 * Return null if absent.
	 */
	JavaResourcePackageFragmentRoot getJavaResourcePackageFragmentRoot(String jarFileName);


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
		 * {@link JpaProject#update()} will call {@link Updater#update()},
		 * from which the updater is to call {@link JpaProject#update(IProgressMonitor)}
		 * as appropriate (typically from an asynchronously executing job).
		 * Once the updating has quiesced (i.e. there are no outstanding requests
		 * for another update), the updater is to call {@link JpaProject#updateQuiesced()}.
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


	// ********** utility **********

	/**
	 * Convert the specified file name to a file mapped to the appropriate
	 * deployment location.
	 */
	IFile convertToPlatformFile(String fileName);


	// ********** validation **********

	/**
	 * Return JPA project's validation messages.
	 */
	Iterator<IMessage> validationMessages(IReporter reporter);


	// ********** database **********

	/**
	 * Return the data source the JPA project is mapped to.
	 */
	JpaDataSource getDataSource();

	/**
	 * Return the JPA project's connection.
	 * The connection profile is null if the JPA project's connection profile
	 * name does not match the name of a DTP connection profile.
	 */
	ConnectionProfile getConnectionProfile();

	/**
	 * Return the JPA project's default database schema container;
	 * which is either the JPA project's default catalog or the JPA project's
	 * database, depending on how the DTP model is implemented.
	 */
	SchemaContainer getDefaultDbSchemaContainer();

	/**
	 * Return the JPA project's default catalog; which is either the user
	 * override catalog or the database's default catalog.
	 */
	String getDefaultCatalog();

	/**
	 * Return the JPA project's default database catalog.
	 * @see #getDefaultCatalog()
	 */
	Catalog getDefaultDbCatalog();

	/**
	 * Return the JPA project's default schema; which can be one of the
	 * following:<ul>
	 * <li>the user override schema
	 * <li>the default catalog's default schema
	 * <li>the database's default schema (if catalogs are not supported)
	 * </ul>
	 */
	String getDefaultSchema();

	/**
	 * Return the JPA project's default database schema.
	 * @see #getDefaultSchema()
	 * @see #getDefaultDbSchemaContainer()
	 */
	Schema getDefaultDbSchema();


	// ********** user override default catalog **********

	/** 
	 * ID string used when the JPA project's user override default catalog changes.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY = "userOverrideDefaultCatalog"; //$NON-NLS-1$

	/**
	 * Return the <em>identifier</em> of the catalog to be used as a default
	 * for the JPA project instead of the one that is associated by default
	 * with the connection profile.
	 * @return The catalog identifier. May be null (implying the connection profile
	 * default catalog should be used).
	 */
	String getUserOverrideDefaultCatalog();

	/**
	 * Set the <em>identifier</em> of the catalog to be used as a default
	 * for the JPA project instead of the one that is associated by default
	 * with the connection profile.
	 * @parameter catalog The catalog identifier. May be null (implying the connection profile
	 * default catalog should be used).
	 */
	void setUserOverrideDefaultCatalog(String catalog);


	// ********** user override default schema **********

	/** 
	 * ID string used when the JPA project's user override default schema changes.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY = "userOverrideDefaultSchema"; //$NON-NLS-1$

	/**
	 * Return the <em>identifier</em> of the schema to be used as a default
	 * for the JPA project instead of the one that is associated by default
	 * with the connection profile.
	 * @return The schema identifier. May be null (implying the connection profile
	 * default schema should be used).
	 */
	String getUserOverrideDefaultSchema();

	/**
	 * Set the <em>identifier</em> of the schema to be used as a default
	 * for the JPA project instead of the one that is associated by default
	 * with the connection profile.
	 * @parameter schema The schema identifier. May be null (implying the connection profile
	 * default schema should be used).
	 */
	void setUserOverrideDefaultSchema(String schema);


	// ********** discover annotated classes **********

	/** 
	 * ID string used when discoversAnnotatedClasses property is changed.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String DISCOVERS_ANNOTATED_CLASSES_PROPERTY = "discoversAnnotatedClasses"; //$NON-NLS-1$

	/**
	 * Return whether the JPA project will "discover" annotated classes
	 * automatically, as opposed to requiring the classes to be listed in the
	 * persistence.xml or one of its mapping files.
	 * This is a user-specified preference that is probably
	 * only helpful when deploying to a JavaSE environment. The JPA spec
	 * says annotated classes are to be discovered automatically in a JavaEE
	 * environment; while the managed persistence classes must be explicitly
	 * listed in a JavaSE environment: "A list of all named managed persistence
	 * classes must be specified in Java SE environments to insure portability".
	 * This flag allows Dali to behave consistently with the user's JPA
	 * implementation, which may allow "discovery" in a JavaSE environment
	 * (e.g. EclipseLink). This setting can also be used when the user wants
	 * to explicitly list classes, even when the classes are "discovered"
	 * by the JPA implementation. If this flag is set to false, error messages
	 * will be generated for all of the annotated classes that are not
	 * explicitly listed.
	 */
	boolean discoversAnnotatedClasses();

	/**
	 * Set whether the JPA project will "discover" annotated classes
	 * automatically, as opposed to requiring the classes to be listed in the
	 * persistence.xml.
	 */
	void setDiscoversAnnotatedClasses(boolean discoversAnnotatedClasses);


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
	 * The settings used to construct a JPA project.
	 */
	interface Config {

		/**
		 * Return the Eclipse project to be associated with the new JPA project.
		 */
		IProject getProject();

		/**
		 * Return the JPA platform to be associated with the new JPA project.
		 */
		JpaPlatform getJpaPlatform();

		/**
		 * Return the name of the connection profile to be associated
		 * with the new JPA project. (This connection profile wraps a DTP
		 * connection profile.)
		 */
		String getConnectionProfileName();

		/**
		 * Return the catalog to use instead of the connection profile's
		 * default catalog.
		 * May be null.
		 */
		String getUserOverrideDefaultCatalog();

		/**
		 * Return the name of the schema to use instead of the default schema
		 * of the connection profile.
		 * May be null.
		 */
		String getUserOverrideDefaultSchema();

		/**
		 * Return whether the new JPA project is to "discover" annotated
		 * classes.
		 */
		boolean discoverAnnotatedClasses();

	}

}
