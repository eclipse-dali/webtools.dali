/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Join Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode#getXmlPath <em>Xml Path</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode#getReferencedXmlPath <em>Referenced Xml Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNode()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class EXmlJoinNode extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getXmlPath() <em>Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlPath()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXmlPath() <em>Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlPath()
	 * @generated
	 * @ordered
	 */
	protected String xmlPath = XML_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getReferencedXmlPath() <em>Referenced Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedXmlPath()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCED_XML_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferencedXmlPath() <em>Referenced Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedXmlPath()
	 * @generated
	 * @ordered
	 */
	protected String referencedXmlPath = REFERENCED_XML_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlJoinNode()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OxmPackage.Literals.EXML_JOIN_NODE;
	}

	/**
	 * Returns the value of the '<em><b>Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Path</em>' attribute.
	 * @see #setXmlPath(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNode_XmlPath()
	 * @model
	 * @generated
	 */
	public String getXmlPath()
	{
		return xmlPath;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode#getXmlPath <em>Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Path</em>' attribute.
	 * @see #getXmlPath()
	 * @generated
	 */
	public void setXmlPath(String newXmlPath)
	{
		String oldXmlPath = xmlPath;
		xmlPath = newXmlPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_JOIN_NODE__XML_PATH, oldXmlPath, xmlPath));
	}

	/**
	 * Returns the value of the '<em><b>Referenced Xml Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Xml Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Xml Path</em>' attribute.
	 * @see #setReferencedXmlPath(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNode_ReferencedXmlPath()
	 * @model
	 * @generated
	 */
	public String getReferencedXmlPath()
	{
		return referencedXmlPath;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode#getReferencedXmlPath <em>Referenced Xml Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Xml Path</em>' attribute.
	 * @see #getReferencedXmlPath()
	 * @generated
	 */
	public void setReferencedXmlPath(String newReferencedXmlPath)
	{
		String oldReferencedXmlPath = referencedXmlPath;
		referencedXmlPath = newReferencedXmlPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_JOIN_NODE__REFERENCED_XML_PATH, oldReferencedXmlPath, referencedXmlPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case OxmPackage.EXML_JOIN_NODE__XML_PATH:
				return getXmlPath();
			case OxmPackage.EXML_JOIN_NODE__REFERENCED_XML_PATH:
				return getReferencedXmlPath();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OxmPackage.EXML_JOIN_NODE__XML_PATH:
				setXmlPath((String)newValue);
				return;
			case OxmPackage.EXML_JOIN_NODE__REFERENCED_XML_PATH:
				setReferencedXmlPath((String)newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case OxmPackage.EXML_JOIN_NODE__XML_PATH:
				setXmlPath(XML_PATH_EDEFAULT);
				return;
			case OxmPackage.EXML_JOIN_NODE__REFERENCED_XML_PATH:
				setReferencedXmlPath(REFERENCED_XML_PATH_EDEFAULT);
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case OxmPackage.EXML_JOIN_NODE__XML_PATH:
				return XML_PATH_EDEFAULT == null ? xmlPath != null : !XML_PATH_EDEFAULT.equals(xmlPath);
			case OxmPackage.EXML_JOIN_NODE__REFERENCED_XML_PATH:
				return REFERENCED_XML_PATH_EDEFAULT == null ? referencedXmlPath != null : !REFERENCED_XML_PATH_EDEFAULT.equals(referencedXmlPath);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (xmlPath: ");
		result.append(xmlPath);
		result.append(", referencedXmlPath: ");
		result.append(referencedXmlPath);
		result.append(')');
		return result.toString();
	}

} // EXmlJoinNode
