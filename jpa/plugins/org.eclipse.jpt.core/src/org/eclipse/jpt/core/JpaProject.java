/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.Iterator;
import java.util.List;

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
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaProject extends JpaNode {

	/**
	 * Return the JPA project's name, which is the Eclipse project's name.
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

	
	// **************** database **********************
	
	/**
	 * Return the data source the JPA project is mapped to.
	 */
	JpaDataSource getDataSource();
	
	/**
	 * Return the project's connection.
	 * The connection profile is null if the connection profile name is invalid.
	 */
	ConnectionProfile getConnectionProfile();

	/**
	 * Return the project's default database schema container
	 * (either the project's default catalog or the project's database).
	 */
	SchemaContainer getDefaultDbSchemaContainer();
	
	/**
	 * Return the project's default catalog:
	 *   - the user override catalog
	 *   - the database's default catalog
	 */
	String getDefaultCatalog();
	
	/**
	 * Return the project's default database catalog.
	 * @see #getDefaultCatalog()
	 */
	Catalog getDefaultDbCatalog();
	
	/**
	 * Return the project's default schema:
	 *   - the user override schema
	 *   - the default catalog's default schema
	 *   - the database's default schema
	 */
	String getDefaultSchema();
	
	/**
	 * Return the project's default database schema.
	 * @see #getDefaultSchema()
	 */
	Schema getDefaultDbSchema();
	
	
	// **************** user override default catalog **********************
	
	/** 
	 * ID string used when userOverrideDefaultCatalog property is changed.
	 * @see org.eclipse.jpt.utility.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY = "userOverrideDefaultCatalog"; //$NON-NLS-1$
	
	/**
	 * Return the name of the catalog to be used as a default for the project
	 * instead of the one that is associated by default with the connection profile.
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


	// **************** user override default schema **********************
	
	/** 
	 * ID string used when userOverrideDefaultSchema property is changed.
	 * @see org.eclipse.jpt.utility.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
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
	
	
	// **************** discover annotated classes *****************************
	
	/** 
	 * ID string used when discoversAnnotatedClasses property is changed.
	 * @see org.eclipse.jpt.utility.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String DISCOVERS_ANNOTATED_CLASSES_PROPERTY = "discoversAnnotatedClasses"; //$NON-NLS-1$
	
	/**
	 * Return whether the JPA project will "discover" annotated classes
	 * automatically, as opposed to requiring the classes to be
	 * listed in persistence.xml.
	 */
	boolean discoversAnnotatedClasses();
	
	/**
	 * Set whether the JPA project will "discover" annotated classes
	 * automatically, as opposed to requiring the classes to be
	 * listed in persistence.xml.
	 */
	void setDiscoversAnnotatedClasses(boolean discoversAnnotatedClasses);
	
	
	// **************** jpa files **********************************************
	
	/** 
	 * ID string used when jpaFiles collection is changed.
	 * @see org.eclipse.jpt.utility.model.Model#addCollectionChangeListener(String, org.eclipse.jpt.utility.model.listener.CollectionChangeListener)
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
	 * Return null if unable to associate the given file
	 * with a JPA file.
	 */
	JpaFile getJpaFile(IFile file);

	/**
	 * Return the JPA project's JPA files for the specified content type ID.
	 * The content type ID should match that given in the
	 * JPA file content provider.
	 */
	Iterator<JpaFile> jpaFiles(String contentTypeId);
	
	
	// **************** various queries ****************************************
	
	/**
	 * Return the JPA project's root "deploy path".
	 * JPA projects associated with Web projects return "WEB-INF/classes";
	 * all others simply return an empty string.
	 */
	String getRootDeployLocation();
	
	/**
	 * Return the {@link JpaRootContextNode} representing the JPA content of this project
	 */
	JpaRootContextNode getRootContext();
	
	/**
	 * Return the names of the JPA project's annotated classes.
	 */
	Iterator<String> annotatedClassNames();
	
	/**
	 * Return the Java persistent type resource for the specified fully qualified type name;
	 * null, if none exists.
	 */
	JavaResourcePersistentType getJavaResourcePersistentType(String typeName);
	
	
	// **************** jpa model synchronization and lifecycle ****************
	
	/**
	 * Synchronize the JPA project's JPA files with the specified resource
	 * delta, watching for added and removed files.
	 */
	void synchronizeJpaFiles(IResourceDelta delta) throws CoreException;

	/**
	 * Forward the Java element change event to the JPA project's JPA files.
	 */
	void javaElementChanged(ElementChangedEvent event);

	/**
	 * The JPA project has been removed from the JPA model. Clean up any
	 * hooks to external resources etc.
	 */
	void dispose();
	
	
	// **************** validation *********************************************
	
	/**
	 * Return project's validation messages.
	 */
	Iterator<IMessage> validationMessages();

	/**
	 * Add to the list of current validation messages
	 */
	void addToMessages(List<IMessage> messages);


	// **************** support for modifying shared documents *****************

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
	 * Return the project-wide implementation of the CommandExecutorProvider
	 * interface.
	 */
	CommandExecutorProvider getModifySharedDocumentCommandExecutorProvider();


	// **************** project "update" ***************************************

	/**
	 * Return the implementation of the Updater
	 * interface that will be used to "update" a JPA project.
	 */
	Updater getUpdater();

	/**
	 * Set the implementation of the Updater
	 * interface that will be used to "update" a JPA project.
	 * Before setting the updater, Clients should save the current updater so
	 * it can be restored later.
	 */
	void setUpdater(Updater updater);

	/**
	 * Something in the JPA project has changed, "update" those parts of the
	 * JPA project that are dependent on other parts of the JPA project.
	 * This is called when
	 * - The JPA project updater is changed {@link JpaProject#setUpdater(Updater)}
	 * - anything in the JPA project changes
	 * - the JPA project's database connection is changed, opened, or closed
	 */
	void update();

	/**
	 * This is the callback used by the updater to perform the actual
	 * "update".
	 */
	IStatus update(IProgressMonitor monitor);


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
		 * The JPA project is disposed; dispose the updater.
		 */
		void dispose();

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
			public void dispose() {
				// do nothing
			}
			@Override
			public String toString() {
				return "JpaProject.Updater.Null"; //$NON-NLS-1$
			}
		}

	}


	// **************** config that can be used to construct a JPA project *****

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
