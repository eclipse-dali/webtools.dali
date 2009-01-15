/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * A JPA project is associated with an Eclipse project (and its corresponding
 * Java project). It holds the "resource" model that corresponds to the various
 * JPA-related resources (the persistence.xml file, its mapping files [orm.xml],
 * and the Java source files). It also holds the "context" model that represents
 * the JPA metadata, as derived from spec-defined defaults, Java source code
 * annotations, and XML descriptors.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
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


	// ********** Java resources **********

	/**
	 * Return the names of the JPA project's annotated classes.
	 */
	Iterator<String> annotatedClassNames();

	/**
	 * Return the Java resource persistent type for the specified type.
	 * Return null if absent.
	 */
	JavaResourcePersistentType getJavaResourcePersistentType(String typeName);


	// ********** model synchronization **********

	/**
	 * Synchronize the JPA project with the specified project resource
	 * delta, watching for added and removed files in particular.
	 */
	void projectChanged(IResourceDelta delta) throws CoreException;

	/**
	 * Synchronize the JPA project with the specified Java change.
	 */
	void javaElementChanged(ElementChangedEvent event);


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
	 * This is called when
	 *     - (almost) any state in the JPA project changes
	 *     - the JPA project's database connection is changed, opened, or closed
	 */
	void update();

	/**
	 * This is the callback used by the updater to perform the actual
	 * "update", which most likely will happen asynchronously.
	 */
	IStatus update(IProgressMonitor monitor);


	// TODO abstract out to utility
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


	// ********** validation **********

	/**
	 * Return JPA project's validation messages.
	 */
	Iterator<IMessage> validationMessages();


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
	 * following:
	 *   - the user override schema
	 *   - the default catalog's default schema
	 *   - the database's default schema (if catalogs are not supported)
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
	 * Return the name of the catalog to be used as a default for the JPA
	 * project instead of the one that is associated by default with the
	 * connection profile.
	 * @return The catalog name. May be null (implies that the connection profile
	 *   default catalog should be used).
	 */
	String getUserOverrideDefaultCatalog();

	/**
	 * Set the name of the catalog to be used as a default for the project
	 * instead of the one that is associated by default with the connection profile.
	 * @parameter catalog The default catalog name to use instead of
	 *   the default catalog of the connection profile. May be null (implies that
	 *   the connection profile default catalog should be used).
	 */
	void setUserOverrideDefaultCatalog(String catalog);


	// ********** user override default schema **********

	/** 
	 * ID string used when the JPA project's user override default schema changes.
	 * ID string used when userOverrideDefaultSchema property is changed.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY = "userOverrideDefaultSchema"; //$NON-NLS-1$

	/**
	 * Return the name of the schema to be used as a default for the project
	 * instead of the one that is associated by default with the connection profile.
	 * @return The schema name. May be null (implies that the connection profile
	 *   default schema should be used).
	 */
	String getUserOverrideDefaultSchema();

	/**
	 * Set the name of the schema to be used as a default for the project
	 * instead of the one that is associated by default with the connection profile.
	 * @parameter schema The default schema name to use instead of
	 *   the default schema of the connection profile. May be null (implies that
	 *   the connection profile default schema should be used).
	 */
	void setUserOverrideDefaultSchema(String schema);


	// ********** discover annotated classes **********

	/** 
	 * ID string used when discoversAnnotatedClasses property is changed.
	 * @see org.eclipse.jpt.utility.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String DISCOVERS_ANNOTATED_CLASSES_PROPERTY = "discoversAnnotatedClasses"; //$NON-NLS-1$

	/**
	 * Return whether the JPA project will "discover" annotated classes
	 * automatically, as opposed to requiring the classes being
	 * listed in persistence.xml.
	 */
	boolean discoversAnnotatedClasses();

	/**
	 * Set whether the JPA project will "discover" annotated classes
	 * automatically, as opposed to requiring the classes being
	 * listed in persistence.xml.
	 */
	void setDiscoversAnnotatedClasses(boolean discoversAnnotatedClasses);


	// ********** modifying shared documents **********

	/**
	 * Set a thread-specific implementation of the CommandExecutor
	 * interface that will be used to execute a command to modify a shared
	 * document. If necessary, the command executor can be cleared by
	 * setting it to null.
	 * This allows background clients to modify documents that are
	 * already present in the UI. See implementations of CommandExecutor.
	 */
	void setThreadLocalModifySharedDocumentCommandExecutor(CommandExecutor commandExecutor);

	/**
	 * Return the project-wide implementation of the CommandExecutor interface.
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
