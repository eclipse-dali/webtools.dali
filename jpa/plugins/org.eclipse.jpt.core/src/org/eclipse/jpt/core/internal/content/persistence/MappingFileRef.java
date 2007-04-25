/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence;

import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.common.internal.emf.utilities.DOMUtilities;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping File Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.MappingFileRef#getFileName <em>File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getMappingFileRef()
 * @model kind="class"
 * @generated
 */
public class MappingFileRef extends XmlEObject
{
	/**
	 * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MappingFileRef() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PersistencePackage.Literals.MAPPING_FILE_REF;
	}

	/**
	 * Returns the value of the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Name</em>' attribute.
	 * @see #setFileName(String)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getMappingFileRef_FileName()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.MappingFileRef#getFileName <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Name</em>' attribute.
	 * @see #getFileName()
	 * @generated
	 */
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.MAPPING_FILE_REF__FILE_NAME, oldFileName, fileName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PersistencePackage.MAPPING_FILE_REF__FILE_NAME :
				return getFileName();
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
			case PersistencePackage.MAPPING_FILE_REF__FILE_NAME :
				setFileName((String) newValue);
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
			case PersistencePackage.MAPPING_FILE_REF__FILE_NAME :
				setFileName(FILE_NAME_EDEFAULT);
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
			case PersistencePackage.MAPPING_FILE_REF__FILE_NAME :
				return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
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
		result.append(" (fileName: ");
		result.append(fileName);
		result.append(')');
		return result.toString();
	}

	@Override
	public ITextRange getTextRange() {
		IDOMNode textNode = (IDOMNode) DOMUtilities.getChildTextNode(node);
		return (textNode == null) ? getTextRange(node) : getTextRange(textNode);
	}

	public IJpaFile getMappingFile() {
		IJpaFile mappingFile = null;
		for (Iterator stream = javaSourceFolders(); stream.hasNext();) {
			IFolder sourceFolder = (IFolder) ((IPackageFragmentRoot) stream.next()).getResource();
			IFile file = sourceFolder.getFile(fileName);
			IJpaFile jpaFile = JptCorePlugin.getJpaFile(file);
			if (mappingFile != null && jpaFile != null) {
				return null; // multiple possibilities == not resolved
			}
			mappingFile = jpaFile;
		}
		return mappingFile;
	}

	private Iterator javaSourceFolders() {
		Iterator nestedIterator = EmptyIterator.instance();
		try {
			nestedIterator = CollectionTools.iterator(getJpaProject().getJavaProject().getPackageFragmentRoots());
		}
		catch (JavaModelException jme) { /* do nothing */}
		return new FilteringIterator(nestedIterator) {
			@Override
			protected boolean accept(Object o) {
				try {
					return ((IPackageFragmentRoot) o).getKind() == IPackageFragmentRoot.K_SOURCE;
				}
				catch (JavaModelException jme) {
					return false;
				}
			}
		};
	}
}
