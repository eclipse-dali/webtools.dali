/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlOrphanRemovable2_0.java,v 1.1 2009/10/27 16:16:08 kmoore Exp $
 */
package org.eclipse.jpt.core.resource.orm.v2_0;

import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Orphan Removable2 0</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable2_0#getOrphanRemoval <em>Orphan Removal</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOrphanRemovable2_0()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlOrphanRemovable2_0 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orphan Removal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orphan Removal</em>' attribute.
	 * @see #setOrphanRemoval(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlOrphanRemovable2_0_OrphanRemoval()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getOrphanRemoval();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable2_0#getOrphanRemoval <em>Orphan Removal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orphan Removal</em>' attribute.
	 * @see #getOrphanRemoval()
	 * @generated
	 */
	void setOrphanRemoval(Boolean value);

} // XmlOrphanRemovable2_0
