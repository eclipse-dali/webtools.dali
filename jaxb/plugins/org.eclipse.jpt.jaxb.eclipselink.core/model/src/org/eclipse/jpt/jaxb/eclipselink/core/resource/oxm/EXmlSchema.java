/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Schema</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getAttributeFormDefault <em>Attribute Form Default</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getElementFormDefault <em>Element Form Default</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getLocation <em>Location</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getXmlns <em>Xmlns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchema()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class EXmlSchema extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getAttributeFormDefault() <em>Attribute Form Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeFormDefault()
	 * @generated
	 * @ordered
	 */
	protected static final EXmlNsForm ATTRIBUTE_FORM_DEFAULT_EDEFAULT = EXmlNsForm.UNQUALIFIED;
	/**
	 * The cached value of the '{@link #getAttributeFormDefault() <em>Attribute Form Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeFormDefault()
	 * @generated
	 * @ordered
	 */
	protected EXmlNsForm attributeFormDefault = ATTRIBUTE_FORM_DEFAULT_EDEFAULT;
	/**
	 * The default value of the '{@link #getElementFormDefault() <em>Element Form Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementFormDefault()
	 * @generated
	 * @ordered
	 */
	protected static final EXmlNsForm ELEMENT_FORM_DEFAULT_EDEFAULT = EXmlNsForm.UNQUALIFIED;
	/**
	 * The cached value of the '{@link #getElementFormDefault() <em>Element Form Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementFormDefault()
	 * @generated
	 * @ordered
	 */
	protected EXmlNsForm elementFormDefault = ELEMENT_FORM_DEFAULT_EDEFAULT;
	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCATION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected String location = LOCATION_EDEFAULT;
	/**
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected String namespace = NAMESPACE_EDEFAULT;
	/**
	 * The cached value of the '{@link #getXmlns() <em>Xmlns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlns()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlNs> xmlns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlSchema()
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
		return OxmPackage.Literals.EXML_SCHEMA;
	}

	/**
	 * Returns the value of the '<em><b>Attribute Form Default</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Form Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Form Default</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm
	 * @see #setAttributeFormDefault(EXmlNsForm)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchema_AttributeFormDefault()
	 * @model
	 * @generated
	 */
	public EXmlNsForm getAttributeFormDefault()
	{
		return attributeFormDefault;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getAttributeFormDefault <em>Attribute Form Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Form Default</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm
	 * @see #getAttributeFormDefault()
	 * @generated
	 */
	public void setAttributeFormDefault(EXmlNsForm newAttributeFormDefault)
	{
		EXmlNsForm oldAttributeFormDefault = attributeFormDefault;
		attributeFormDefault = newAttributeFormDefault == null ? ATTRIBUTE_FORM_DEFAULT_EDEFAULT : newAttributeFormDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_SCHEMA__ATTRIBUTE_FORM_DEFAULT, oldAttributeFormDefault, attributeFormDefault));
	}

	/**
	 * Returns the value of the '<em><b>Element Form Default</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Form Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Form Default</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm
	 * @see #setElementFormDefault(EXmlNsForm)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchema_ElementFormDefault()
	 * @model
	 * @generated
	 */
	public EXmlNsForm getElementFormDefault()
	{
		return elementFormDefault;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getElementFormDefault <em>Element Form Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Form Default</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNsForm
	 * @see #getElementFormDefault()
	 * @generated
	 */
	public void setElementFormDefault(EXmlNsForm newElementFormDefault)
	{
		EXmlNsForm oldElementFormDefault = elementFormDefault;
		elementFormDefault = newElementFormDefault == null ? ELEMENT_FORM_DEFAULT_EDEFAULT : newElementFormDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_SCHEMA__ELEMENT_FORM_DEFAULT, oldElementFormDefault, elementFormDefault));
	}

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchema_Location()
	 * @model
	 * @generated
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	public void setLocation(String newLocation)
	{
		String oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_SCHEMA__LOCATION, oldLocation, location));
	}

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchema_Namespace()
	 * @model
	 * @generated
	 */
	public String getNamespace()
	{
		return namespace;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	public void setNamespace(String newNamespace)
	{
		String oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_SCHEMA__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * Returns the value of the '<em><b>Xmlns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNs}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xmlns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xmlns</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlSchema_Xmlns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlNs> getXmlns()
	{
		if (xmlns == null)
		{
			xmlns = new EObjectContainmentEList<EXmlNs>(EXmlNs.class, this, OxmPackage.EXML_SCHEMA__XMLNS);
		}
		return xmlns;
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
			case OxmPackage.EXML_SCHEMA__XMLNS:
				return ((InternalEList<?>)getXmlns()).basicRemove(otherEnd, msgs);
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
			case OxmPackage.EXML_SCHEMA__ATTRIBUTE_FORM_DEFAULT:
				return getAttributeFormDefault();
			case OxmPackage.EXML_SCHEMA__ELEMENT_FORM_DEFAULT:
				return getElementFormDefault();
			case OxmPackage.EXML_SCHEMA__LOCATION:
				return getLocation();
			case OxmPackage.EXML_SCHEMA__NAMESPACE:
				return getNamespace();
			case OxmPackage.EXML_SCHEMA__XMLNS:
				return getXmlns();
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
			case OxmPackage.EXML_SCHEMA__ATTRIBUTE_FORM_DEFAULT:
				setAttributeFormDefault((EXmlNsForm)newValue);
				return;
			case OxmPackage.EXML_SCHEMA__ELEMENT_FORM_DEFAULT:
				setElementFormDefault((EXmlNsForm)newValue);
				return;
			case OxmPackage.EXML_SCHEMA__LOCATION:
				setLocation((String)newValue);
				return;
			case OxmPackage.EXML_SCHEMA__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case OxmPackage.EXML_SCHEMA__XMLNS:
				getXmlns().clear();
				getXmlns().addAll((Collection<? extends EXmlNs>)newValue);
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
			case OxmPackage.EXML_SCHEMA__ATTRIBUTE_FORM_DEFAULT:
				setAttributeFormDefault(ATTRIBUTE_FORM_DEFAULT_EDEFAULT);
				return;
			case OxmPackage.EXML_SCHEMA__ELEMENT_FORM_DEFAULT:
				setElementFormDefault(ELEMENT_FORM_DEFAULT_EDEFAULT);
				return;
			case OxmPackage.EXML_SCHEMA__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case OxmPackage.EXML_SCHEMA__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case OxmPackage.EXML_SCHEMA__XMLNS:
				getXmlns().clear();
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
			case OxmPackage.EXML_SCHEMA__ATTRIBUTE_FORM_DEFAULT:
				return attributeFormDefault != ATTRIBUTE_FORM_DEFAULT_EDEFAULT;
			case OxmPackage.EXML_SCHEMA__ELEMENT_FORM_DEFAULT:
				return elementFormDefault != ELEMENT_FORM_DEFAULT_EDEFAULT;
			case OxmPackage.EXML_SCHEMA__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case OxmPackage.EXML_SCHEMA__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case OxmPackage.EXML_SCHEMA__XMLNS:
				return xmlns != null && !xmlns.isEmpty();
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
		result.append(" (attributeFormDefault: ");
		result.append(attributeFormDefault);
		result.append(", elementFormDefault: ");
		result.append(elementFormDefault);
		result.append(", location: ");
		result.append(location);
		result.append(", namespace: ");
		result.append(namespace);
		result.append(')');
		return result.toString();
	}

} // EXmlSchema
