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
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.ElementChangedEvent;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.JpaFile#getContentId <em>Content Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaFile()
 * @model kind="class"
 * @generated
 */
public class JpaFile extends JpaEObject implements IJpaFile
{
	/**
	 * The default value of the '{@link #getContentId() <em>Content Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentId()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContentId() <em>Content Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentId()
	 * @generated
	 * @ordered
	 */
	protected String contentId = CONTENT_ID_EDEFAULT;

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
	protected JpaFile() {
		super();
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

	/**
	 * Returns the value of the '<em><b>Content Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content Id</em>' attribute.
	 * @see #setContentId(String)
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaFile_ContentId()
	 * @model required="true"
	 * @generated
	 */
	public String getContentId() {
		return contentId;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.JpaFile#getContentId <em>Content Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content Id</em>' attribute.
	 * @see #getContentId()
	 * @generated
	 */
	public void setContentId(String newContentId) {
		String oldContentId = contentId;
		contentId = newContentId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_FILE__CONTENT_ID, oldContentId, contentId));
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaCorePackage.JPA_FILE__CONTENT_ID :
				return getContentId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaCorePackage.JPA_FILE__CONTENT_ID :
				setContentId((String) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JpaCorePackage.JPA_FILE__CONTENT_ID :
				setContentId(CONTENT_ID_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JpaCorePackage.JPA_FILE__CONTENT_ID :
				return CONTENT_ID_EDEFAULT == null ? contentId != null : !CONTENT_ID_EDEFAULT.equals(contentId);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (contentId: ");
		result.append(contentId);
		result.append(')');
		return result.toString();
	}

	public IJpaContentNode getContentNode(int offset) {
		return getResourceModel().getContentNode(offset);
	}

	@Override
	public IResource getResource() {
		return file;
	}
}
