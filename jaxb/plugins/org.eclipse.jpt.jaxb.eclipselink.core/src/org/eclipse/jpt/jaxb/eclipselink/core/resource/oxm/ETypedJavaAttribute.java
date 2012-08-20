/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ETyped Java Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getETypedJavaAttribute()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface ETypedJavaAttribute extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getETypedJavaAttribute_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.ETypedJavaAttribute#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // ETypedJavaAttribute
