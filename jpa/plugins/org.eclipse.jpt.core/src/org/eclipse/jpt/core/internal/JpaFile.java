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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
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
 *   <li>{@link org.eclipse.jpt.core.internal.JpaFile#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaFile()
 * @model kind="class"
 * @generated
 */
public class JpaFile extends JpaEObject implements IJpaFile
{
	// temporary bridge until we remove EMF stuff
	private IJpaProject jpaProject;

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
	 * The cached value of the '{@link #getContent() <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected IJpaRootContentNode content;

	/**
	 * The IFile associated with this JPA file
	 */
	protected IFile file;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JpaFile() {
		super();
	}

	public JpaFile(IJpaProject jpaProject, IFile file, IJpaFileContentProvider provider) {
		this();
		this.jpaProject = jpaProject;
		this.setFile(file);
		this.setContentId(provider.contentType());
		provider.buildRootContent(this);
	}

	// temporary bridge until we get rid of EMF stuff
	@Override
	public IJpaProject getJpaProject() {
		return this.jpaProject;
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
	 * Returns the value of the '<em><b>Content</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.jpt.core.internal.IJpaRootContentNode#getJpaFile <em>Jpa File</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' containment reference.
	 * @see #setContent(IJpaRootContentNode)
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaFile_Content()
	 * @see org.eclipse.jpt.core.internal.IJpaRootContentNode#getJpaFile
	 * @model opposite="jpaFile" containment="true"
	 * @generated
	 */
	public IJpaRootContentNode getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContent(IJpaRootContentNode newContent, NotificationChain msgs) {
		IJpaRootContentNode oldContent = content;
		content = newContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_FILE__CONTENT, oldContent, newContent);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.JpaFile#getContent <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' containment reference.
	 * @see #getContent()
	 * @generated
	 */
	public void setContent(IJpaRootContentNode newContent) {
		if (newContent != content) {
			NotificationChain msgs = null;
			if (content != null)
				msgs = ((InternalEObject) content).eInverseRemove(this, JpaCorePackage.IJPA_ROOT_CONTENT_NODE__JPA_FILE, IJpaRootContentNode.class, msgs);
			if (newContent != null)
				msgs = ((InternalEObject) newContent).eInverseAdd(this, JpaCorePackage.IJPA_ROOT_CONTENT_NODE__JPA_FILE, IJpaRootContentNode.class, msgs);
			msgs = basicSetContent(newContent, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_FILE__CONTENT, newContent, newContent));
	}

	public IFile getFile() {
		return file;
	}

	void setFile(IFile theFile) {
		file = theFile;
	}

	public void dispose() {
		this.content.dispose();
	}

	public void javaElementChanged(ElementChangedEvent event) {
		this.content.javaElementChanged(event);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaCorePackage.JPA_FILE__CONTENT :
				if (content != null)
					msgs = ((InternalEObject) content).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaCorePackage.JPA_FILE__CONTENT, null, msgs);
				return basicSetContent((IJpaRootContentNode) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaCorePackage.JPA_FILE__CONTENT :
				return basicSetContent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
			case JpaCorePackage.JPA_FILE__CONTENT :
				return getContent();
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
			case JpaCorePackage.JPA_FILE__CONTENT :
				setContent((IJpaRootContentNode) newValue);
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
			case JpaCorePackage.JPA_FILE__CONTENT :
				setContent((IJpaRootContentNode) null);
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
			case JpaCorePackage.JPA_FILE__CONTENT :
				return content != null;
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
		return getContent().getContentNode(offset);
	}

	@Override
	public IResource getResource() {
		return file;
	}
}
