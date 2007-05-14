/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.jpt.core.internal.IJpaSourceObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ICascade</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ICascade#isAll <em>All</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ICascade#isPersist <em>Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ICascade#isMerge <em>Merge</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ICascade#isRemove <em>Remove</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ICascade#isRefresh <em>Refresh</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getICascade()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface ICascade extends IJpaSourceObject
{
	/**
	 * Returns the value of the '<em><b>All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All</em>' attribute.
	 * @see #setAll(boolean)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getICascade_All()
	 * @model
	 * @generated
	 */
	boolean isAll();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ICascade#isAll <em>All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All</em>' attribute.
	 * @see #isAll()
	 * @generated
	 */
	void setAll(boolean value);

	/**
	 * Returns the value of the '<em><b>Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persist</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persist</em>' attribute.
	 * @see #setPersist(boolean)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getICascade_Persist()
	 * @model
	 * @generated
	 */
	boolean isPersist();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ICascade#isPersist <em>Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persist</em>' attribute.
	 * @see #isPersist()
	 * @generated
	 */
	void setPersist(boolean value);

	/**
	 * Returns the value of the '<em><b>Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merge</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Merge</em>' attribute.
	 * @see #setMerge(boolean)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getICascade_Merge()
	 * @model
	 * @generated
	 */
	boolean isMerge();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ICascade#isMerge <em>Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Merge</em>' attribute.
	 * @see #isMerge()
	 * @generated
	 */
	void setMerge(boolean value);

	/**
	 * Returns the value of the '<em><b>Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remove</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remove</em>' attribute.
	 * @see #setRemove(boolean)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getICascade_Remove()
	 * @model
	 * @generated
	 */
	boolean isRemove();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ICascade#isRemove <em>Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove</em>' attribute.
	 * @see #isRemove()
	 * @generated
	 */
	void setRemove(boolean value);

	/**
	 * Returns the value of the '<em><b>Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refresh</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refresh</em>' attribute.
	 * @see #setRefresh(boolean)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getICascade_Refresh()
	 * @model
	 * @generated
	 */
	boolean isRefresh();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ICascade#isRefresh <em>Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refresh</em>' attribute.
	 * @see #isRefresh()
	 * @generated
	 */
	void setRefresh(boolean value);
} // ICascade
