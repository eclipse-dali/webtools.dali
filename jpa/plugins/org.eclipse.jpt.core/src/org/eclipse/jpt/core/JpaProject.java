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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.platform.JpaPlatform;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.utility.internal.CommandExecutor;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public interface JpaProject extends JpaNode {

	/**
	 * Return the JPA project's name, which is the Eclipse project's name.
	 */
	String name();

	/**
	 * Return the Eclipse project associated with the JPA project.
	 */
	IProject project();

	/**
	 * Return the Java project associated with the JPA project.
	 */
	IJavaProject javaProject();

	/**
	 * Return the vendor-specific JPA platform that builds the JPA project
	 * and its contents.
	 */
	JpaPlatform jpaPlatform();

	/**
	 * Return the project's connection
	 */
	ConnectionProfile connectionProfile();

	/**
	 * Return the project's default schema, taken from the ConnectionProfile
	 */
	Schema defaultSchema();
	
	/**
	 * Return the data source the JPA project is mapped to.
	 */
	JpaDataSource dataSource();
	
	
	// **************** discover annotated classes *****************************
	
	/** 
	 * ID string used when discoversAnnotatedClasses property is changed.
	 * @see org.eclipse.jpt.utility.internal.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener)
	 */
	String DISCOVERS_ANNOTATED_CLASSES_PROPERTY = "discoversAnnotatedClasses";
	
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
	 * @see org.eclipse.jpt.utility.internal.model.Model#addCollectionChangeListener(String, org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener)
	 */
	String JPA_FILES_COLLECTION = "jpaFiles";

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
	JpaFile jpaFile(IFile file);

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
	String rootDeployLocation();
	
	/**
	 * Return the context model representing the JPA content of this project
	 */
	ContextModel contextModel();
	
	/**
	 * Return an iterator on all ITypes that are annotated within this project
	 */
	Iterator<IType> annotatedClasses();
	
	/**
	 * Return the Java persistent type resource for the specified fully qualified type name;
	 * null, if none exists.
	 */
	JavaResourcePersistentType javaPersistentTypeResource(String typeName);
	
	
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
	CommandExecutorProvider modifySharedDocumentCommandExecutorProvider();


	// **************** project "update" ***************************************

	/**
	 * Return the implementation of the Updater
	 * interface that will be used to "update" a JPA project.
	 */
	Updater updater();

	/**
	 * Set the implementation of the Updater
	 * interface that will be used to "update" a JPA project.
	 */
	void setUpdater(Updater updater);

	/**
	 * Something in the JPA project has changed, "update" those parts of the
	 * JPA project that are dependent on other parts of the JPA project.
	 * This is called when
	 * - the JPA project is first constructed
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
			public static final Updater INSTANCE = new Null();
			public static Updater instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			public void update() {
				// do nothing
			}
			public void dispose() {
				// do nothing
			}
			@Override
			public String toString() {
				return "IJpaProject.Updater.Null";
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
		IProject project();

		/**
		 * Return the JPA platform to be associated with the new JPA project.
		 */
		JpaPlatform jpaPlatform();

		/**
		 * Return the name of the connection profile to be associated
		 * with the new JPA project. (This connection profile wraps a DTP
		 * connection profile.)
		 */
		String connectionProfileName();

		/**
		 * Return whether the new JPA project is to "discover" annotated
		 * classes.
		 */
		boolean discoverAnnotatedClasses();

	}

}
