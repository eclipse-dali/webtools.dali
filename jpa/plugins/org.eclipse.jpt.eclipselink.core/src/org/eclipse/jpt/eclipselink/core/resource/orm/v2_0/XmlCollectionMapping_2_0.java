/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlCollectionMapping_2_0.java,v 1.1 2009/09/30 23:17:51 pfullbright Exp $
 */
package org.eclipse.jpt.eclipselink.core.resource.orm.v2_0;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Collection Mapping 20</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.XmlCollectionMapping_2_0#getMapKeyConvert <em>Map Key Convert</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.XmlCollectionMapping_2_0#getMapKeyAssociationOverrides <em>Map Key Association Overrides</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlCollectionMapping_2_0()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlCollectionMapping_2_0 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Map Key Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Convert</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Convert</em>' attribute.
	 * @see #setMapKeyConvert(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlCollectionMapping_2_0_MapKeyConvert()
	 * @model
	 * @generated
	 */
	String getMapKeyConvert();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.XmlCollectionMapping_2_0#getMapKeyConvert <em>Map Key Convert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Convert</em>' attribute.
	 * @see #getMapKeyConvert()
	 * @generated
	 */
	void setMapKeyConvert(String value);

	/**
	 * Returns the value of the '<em><b>Map Key Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Association Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlCollectionMapping_2_0_MapKeyAssociationOverrides()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlAssociationOverride> getMapKeyAssociationOverrides();

} // XmlCollectionMapping_2_0
