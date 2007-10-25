/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

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
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.utility.internal.CommandExecutor;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

/**
 * 
 */
public interface IJpaProject extends IJpaNode {

	/**
	 * Return the Eclipse project associated with the JPA project.
	 */
	IProject project();

	/**
	 * Return the JPA project's name, which is the Eclipse project's name.
	 */
	String name();

	/**
	 * Return the Java project associated with the JPA project.
	 */
	IJavaProject javaProject();

	/**
	 * Return the vendor-specific JPA platform that builds the JPA project
	 * and its contents.
	 */
	IJpaPlatform jpaPlatform();

	/**
	 * Return the project's connection
	 */
	ConnectionProfile connectionProfile();

	/**
	 * Return the data source the JPA project is mapped to.
	 */
	IJpaDataSource dataSource();

	/**
	 * Return the JPA project's JPA files.
	 */
	Iterator<IJpaFile> jpaFiles();
		String JPA_FILES_COLLECTION = "jpaFiles";

	/**
	 * Return the size of the JPA project's JPA files.
	 */
	int jpaFilesSize();

	/**
	 * Return the JPA file corresponding to the specified file.
	 * Return null if unable to associate the given file
	 * with a JPA file.
	 */
	IJpaFile jpaFile(IFile file);

	/**
	 * Return the JPA project's JPA files for the specified content type ID.
	 * The content type ID should match that given in the
	 * JPA file content provider.
	 */
	Iterator<IJpaFile> jpaFiles(String contentTypeId);

	/**
	 * Return the JPA project's Java JPA files.
	 */
	Iterator<IJpaFile> javaJpaFiles();

	/**
	 * Return the JPA project's Java persistent types.
	 */
	Iterator<JavaPersistentType> javaPersistentTypes();

	/**
	 * Return the Java persistent type for the specified JDT type;
	 * null, if none exists.
	 */
	JavaPersistentType javaPersistentType(IType type);

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
	 * Return whether the JPA project will "discover" annotated classes
	 * automatically, as opposed to requiring the classes to be
	 * listed in persistence.xml.
	 */
	boolean discoversAnnotatedClasses();
		String DISCOVERS_ANNOTATED_CLASSES_PROPERTY = "discoversAnnotatedClasses";

	/**
	 * Set whether the JPA project will "discover" annotated classes
	 * automatically, as opposed to requiring the classes to be
	 * listed in persistence.xml.
	 */
	void setDiscoversAnnotatedClasses(boolean discoversAnnotatedClasses);

	/**
	 * Return project's validation messages.
	 */
	@SuppressWarnings("restriction")
	Iterator<org.eclipse.wst.validation.internal.provisional.core.IMessage> validationMessages();

	/**
	 * Return the JPA project's root "deploy path".
	 * JPA projects associated with Web projects return "WEB-INF/classes";
	 * all others simply return an empty string.
	 */
	String rootDeployLocation();

	/**
	 * The JPA project has been removed from the JPA model. Clean up any
	 * hooks to external resources etc.
	 */
	void dispose();


	// ********** support for modifying shared documents **********

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


	// ********** updating defaults etc. **********

	/**
	 * Reconnect the model together, recalculating default values as needed
	 */
	void update();

	IStatus update(IProgressMonitor monitor);


	// ********** config that can be used to construct a JPA project **********

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
		IJpaPlatform jpaPlatform();

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
