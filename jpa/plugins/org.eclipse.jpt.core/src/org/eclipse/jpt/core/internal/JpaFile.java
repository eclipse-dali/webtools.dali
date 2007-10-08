/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jdt.core.ElementChangedEvent;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence File</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaFile()
 * @model kind="class"
 * @generated
 */
public class JpaFile extends JpaEObject implements IJpaFile
{
	/**
	 * The owning project
	 * TODO - remove this once we have "parents" throughout the model
	 */
	protected IJpaProject project;
	
	/**
	 * The IFile associated with this JPA file
	 */
	protected IFile file;

	/**
	 * The resource model represented by this JPA file
	 */
	protected IResourceModel resourceModel;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JpaFile(IJpaProject project) {
		super();
		this.project = project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaCorePackage.Literals.JPA_FILE;
	}
	
	@Override
	public IJpaProject getJpaProject() {
		return project;
	}
	
	/**
	 * @see IJpaFile#getFile()
	 */
	public IFile getFile() {
		return file;
	}
	
	void setFile(IFile theFile) {
		file = theFile;
	}
	
	/**
	 * @see IJpaFile#getResourceModel()
	 */
	public IResourceModel getResourceModel() {
		return resourceModel;
	}
	
	void setResourceModel(IResourceModel theResourceModel) {
		resourceModel = theResourceModel;
	}
	
	/**
	 * @see IJpaFile#getResourceType()
	 */
	public String getResourceType() {
		return resourceModel.getResourceType();
	}
	
	/**
	 * INTERNAL ONLY
	 * Dispose of file before it is removed
	 */
	void dispose() {
		getResourceModel().dispose();
		((JpaProject) getJpaProject()).getFiles().remove(this);
	}

	/**
	 * INTERNAL ONLY
	 * Handle java element change event.
	 */
	void handleEvent(ElementChangedEvent event) {
		getResourceModel().handleJavaElementChangedEvent(event);
	}

	public IJpaContentNode getContentNode(int offset) {
		return getResourceModel().getContentNode(offset);
	}

	@Override
	public IResource getResource() {
		return file;
	}
}
