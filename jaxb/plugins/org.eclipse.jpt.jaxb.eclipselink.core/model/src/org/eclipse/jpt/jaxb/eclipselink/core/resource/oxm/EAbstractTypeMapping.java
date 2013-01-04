/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.BooleanTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAbstract Type Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlTransient <em>Xml Transient</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlType <em>Xml Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlRootElement <em>Xml Root Element</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlSeeAlso <em>Xml See Also</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractTypeMapping()
 * @model kind="class" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public abstract class EAbstractTypeMapping extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getXmlTransient() <em>Xml Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlTransient()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean XML_TRANSIENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXmlTransient() <em>Xml Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlTransient()
	 * @generated
	 * @ordered
	 */
	protected Boolean xmlTransient = XML_TRANSIENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getXmlType() <em>Xml Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlType()
	 * @generated
	 * @ordered
	 */
	protected EXmlType xmlType;

	/**
	 * The cached value of the '{@link #getXmlRootElement() <em>Xml Root Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlRootElement()
	 * @generated
	 * @ordered
	 */
	protected EXmlRootElement xmlRootElement;

	/**
	 * The default value of the '{@link #getXmlSeeAlso() <em>Xml See Also</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlSeeAlso()
	 * @generated
	 * @ordered
	 */
	protected static final List<String> XML_SEE_ALSO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXmlSeeAlso() <em>Xml See Also</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlSeeAlso()
	 * @generated
	 * @ordered
	 */
	protected List<String> xmlSeeAlso = XML_SEE_ALSO_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EAbstractTypeMapping()
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
		return OxmPackage.Literals.EABSTRACT_TYPE_MAPPING;
	}

	/**
	 * Returns the value of the '<em><b>Xml Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Transient</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Transient</em>' attribute.
	 * @see #setXmlTransient(Boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractTypeMapping_XmlTransient()
	 * @model
	 * @generated
	 */
	public Boolean getXmlTransient()
	{
		return xmlTransient;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlTransient <em>Xml Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Transient</em>' attribute.
	 * @see #getXmlTransient()
	 * @generated
	 */
	public void setXmlTransient(Boolean newXmlTransient)
	{
		Boolean oldXmlTransient = xmlTransient;
		xmlTransient = newXmlTransient;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TRANSIENT, oldXmlTransient, xmlTransient));
	}

	/**
	 * Returns the value of the '<em><b>Xml Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Type</em>' containment reference.
	 * @see #setXmlType(EXmlType)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractTypeMapping_XmlType()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlType getXmlType()
	{
		return xmlType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlType(EXmlType newXmlType, NotificationChain msgs)
	{
		EXmlType oldXmlType = xmlType;
		xmlType = newXmlType;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE, oldXmlType, newXmlType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlType <em>Xml Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Type</em>' containment reference.
	 * @see #getXmlType()
	 * @generated
	 */
	public void setXmlType(EXmlType newXmlType)
	{
		if (newXmlType != xmlType)
		{
			NotificationChain msgs = null;
			if (xmlType != null)
				msgs = ((InternalEObject)xmlType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE, null, msgs);
			if (newXmlType != null)
				msgs = ((InternalEObject)newXmlType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE, null, msgs);
			msgs = basicSetXmlType(newXmlType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE, newXmlType, newXmlType));
	}

	/**
	 * Returns the value of the '<em><b>Xml Root Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Root Element</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Root Element</em>' containment reference.
	 * @see #setXmlRootElement(EXmlRootElement)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractTypeMapping_XmlRootElement()
	 * @model containment="true"
	 * @generated
	 */
	public EXmlRootElement getXmlRootElement()
	{
		return xmlRootElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetXmlRootElement(EXmlRootElement newXmlRootElement, NotificationChain msgs)
	{
		EXmlRootElement oldXmlRootElement = xmlRootElement;
		xmlRootElement = newXmlRootElement;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT, oldXmlRootElement, newXmlRootElement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlRootElement <em>Xml Root Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Root Element</em>' containment reference.
	 * @see #getXmlRootElement()
	 * @generated
	 */
	public void setXmlRootElement(EXmlRootElement newXmlRootElement)
	{
		if (newXmlRootElement != xmlRootElement)
		{
			NotificationChain msgs = null;
			if (xmlRootElement != null)
				msgs = ((InternalEObject)xmlRootElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT, null, msgs);
			if (newXmlRootElement != null)
				msgs = ((InternalEObject)newXmlRootElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT, null, msgs);
			msgs = basicSetXmlRootElement(newXmlRootElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT, newXmlRootElement, newXmlRootElement));
	}

	/**
	 * Returns the value of the '<em><b>Xml See Also</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml See Also</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml See Also</em>' attribute.
	 * @see #setXmlSeeAlso(List)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractTypeMapping_XmlSeeAlso()
	 * @model dataType="org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSeeAlso" many="false"
	 * @generated
	 */
	public List<String> getXmlSeeAlso()
	{
		return xmlSeeAlso;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractTypeMapping#getXmlSeeAlso <em>Xml See Also</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml See Also</em>' attribute.
	 * @see #getXmlSeeAlso()
	 * @generated
	 */
	public void setXmlSeeAlso(List<String> newXmlSeeAlso)
	{
		List<String> oldXmlSeeAlso = xmlSeeAlso;
		xmlSeeAlso = newXmlSeeAlso;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO, oldXmlSeeAlso, xmlSeeAlso));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE:
				return basicSetXmlType(null, msgs);
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT:
				return basicSetXmlRootElement(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TRANSIENT:
				return getXmlTransient();
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE:
				return getXmlType();
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT:
				return getXmlRootElement();
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO:
				return getXmlSeeAlso();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TRANSIENT:
				setXmlTransient((Boolean)newValue);
				return;
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE:
				setXmlType((EXmlType)newValue);
				return;
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT:
				setXmlRootElement((EXmlRootElement)newValue);
				return;
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO:
				setXmlSeeAlso((List<String>)newValue);
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
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TRANSIENT:
				setXmlTransient(XML_TRANSIENT_EDEFAULT);
				return;
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE:
				setXmlType((EXmlType)null);
				return;
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT:
				setXmlRootElement((EXmlRootElement)null);
				return;
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO:
				setXmlSeeAlso(XML_SEE_ALSO_EDEFAULT);
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
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TRANSIENT:
				return XML_TRANSIENT_EDEFAULT == null ? xmlTransient != null : !XML_TRANSIENT_EDEFAULT.equals(xmlTransient);
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_TYPE:
				return xmlType != null;
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_ROOT_ELEMENT:
				return xmlRootElement != null;
			case OxmPackage.EABSTRACT_TYPE_MAPPING__XML_SEE_ALSO:
				return XML_SEE_ALSO_EDEFAULT == null ? xmlSeeAlso != null : !XML_SEE_ALSO_EDEFAULT.equals(xmlSeeAlso);
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
		result.append(" (xmlTransient: ");
		result.append(xmlTransient);
		result.append(", xmlSeeAlso: ");
		result.append(xmlSeeAlso);
		result.append(')');
		return result.toString();
	}
	
	
	// ***** translators *****
	
	protected static Translator buildXmlTransientTranslator() {
		return new BooleanTranslator(
			Oxm.XML_TRANSIENT,
			OxmPackage.eINSTANCE.getEAbstractTypeMapping_XmlTransient(), 
			Translator.DOM_ATTRIBUTE | Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
}