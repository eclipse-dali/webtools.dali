/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAccessible Java Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute#getXmlAccessMethods <em>Xml Access Methods</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAccessibleJavaAttribute()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface EAccessibleJavaAttribute extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Xml Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Access Methods</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Access Methods</em>' containment reference.
	 * @see #setXmlAccessMethods(EXmlAccessMethods)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAccessibleJavaAttribute_XmlAccessMethods()
	 * @model containment="true"
	 * @generated
	 */
	EXmlAccessMethods getXmlAccessMethods();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAccessibleJavaAttribute#getXmlAccessMethods <em>Xml Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Access Methods</em>' containment reference.
	 * @see #getXmlAccessMethods()
	 * @generated
	 */
	void setXmlAccessMethods(EXmlAccessMethods value);

} // EAccessibleJavaAttribute
