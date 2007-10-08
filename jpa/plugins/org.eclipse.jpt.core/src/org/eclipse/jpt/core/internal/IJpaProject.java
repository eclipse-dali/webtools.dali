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

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.utility.internal.CommandExecutor;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IJpa Project</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaProject()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJpaProject extends IJpaEObject
{
	/**
	 * Return the IProject associated with this JPA project
	 */
	IProject getProject();

	/**
	 * Return the IJavaProject associated with the JPA project
	 */
	IJavaProject getJavaProject();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	IJpaModel getModel();

	IJpaPlatform getPlatform();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model platformIdRequired="true" platformIdOrdered="false"
	 * @generated
	 */
	void setPlatform(String platformId);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	IJpaDataSource getDataSource();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model connectionProfileNameUnique="false" connectionProfileNameRequired="true" connectionProfileNameOrdered="false"
	 * @generated
	 */
	void setDataSource(String connectionProfileName);

	ConnectionProfile connectionProfile();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	boolean isDiscoverAnnotatedClasses();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model discoverAnnotatedClassesUnique="false" discoverAnnotatedClassesRequired="true" discoverAnnotatedClassesOrdered="false"
	 * @generated
	 */
	void setDiscoverAnnotatedClasses(boolean discoverAnnotatedClasses);

	/**
	 * Return the root "deploy path" for this project.
	 * 
	 * Web projects return "WEB-INF/classes".
	 * All other projects simply return "".
	 */
	String rootDeployLocation();

	/**
	 * Returns the IJpaFile corresponding to the given IFile.
	 * Returns <code>null</code> if unable to associate the given IFile
	 * with an IJpaFile.
	 */
	IJpaFile getJpaFile(IFile file) throws CoreException;

	/**
	 * Return a Collection of IJpaFiles for the given contentType.
	 * The contentType should match that given in the IJpaFileContentProvider
	 */
	Collection<IJpaFile> jpaFiles(String contentType);

	/**
	 * Returns a (non-modifiable) Iterator on all the IJpaFiles in the project.
	 */
	Iterator<IJpaFile> jpaFiles();
	
	/**
	 * Return the context model associated with the project
	 */
	IContextModel getContextModel();
	
	/**
	 * Update the context model, which is the representation of all the included
	 * resource models with context and defaults applied
	 */
	void updateContextModel();
	
	//	/**
	//	 * Return a JavaPersistentType for the IType, if it exists, null otherwise.
	//	 */
	//	JavaPersistentType findJavaPersistentType(IType type);
	//	
	//	/**
	//	 * Returns all the validation messages for this project
	//	 */
	//	Iterator<IMessage> validationMessages();
	
	/**
	 * Return a thread-specific implementation of the CommandExecutor
	 * interface that will be used to execute a command to modify a shared
	 * document.
	 */
	CommandExecutor getThreadLocalModifySharedDocumentCommandExecutor();

	/**
	 * Set a thread-specific implementation of the CommandExecutor
	 * interface that will be used to execute a command to modify a shared
	 * document. This allows background clients to modify documents that are
	 * already present in the UI.
	 */
	void setThreadLocalModifySharedDocumentCommandExecutor(CommandExecutor commandExecutor);

	CommandExecutorProvider modifySharedDocumentCommandExecutorProvider();
}
