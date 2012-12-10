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
 * A representation of the model object '<em><b>EXml Virtual Access Methods</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getGetMethod <em>Get Method</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getSetMethod <em>Set Method</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getSchema <em>Schema</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethods()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class EXmlVirtualAccessMethods extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getGetMethod() <em>Get Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String GET_METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGetMethod() <em>Get Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetMethod()
	 * @generated
	 * @ordered
	 */
	protected String getMethod = GET_METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getSetMethod() <em>Set Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String SET_METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSetMethod() <em>Set Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetMethod()
	 * @generated
	 * @ordered
	 */
	protected String setMethod = SET_METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected static final EXmlVirtualAccessMethodsSchema SCHEMA_EDEFAULT = EXmlVirtualAccessMethodsSchema.NODES;

	/**
	 * The cached value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected EXmlVirtualAccessMethodsSchema schema = SCHEMA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlVirtualAccessMethods()
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
		return OxmPackage.Literals.EXML_VIRTUAL_ACCESS_METHODS;
	}

	/**
	 * Returns the value of the '<em><b>Get Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Get Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Get Method</em>' attribute.
	 * @see #setGetMethod(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethods_GetMethod()
	 * @model
	 * @generated
	 */
	public String getGetMethod()
	{
		return getMethod;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getGetMethod <em>Get Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Get Method</em>' attribute.
	 * @see #getGetMethod()
	 * @generated
	 */
	public void setGetMethod(String newGetMethod)
	{
		String oldGetMethod = getMethod;
		getMethod = newGetMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__GET_METHOD, oldGetMethod, getMethod));
	}

	/**
	 * Returns the value of the '<em><b>Set Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Set Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Set Method</em>' attribute.
	 * @see #setSetMethod(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethods_SetMethod()
	 * @model
	 * @generated
	 */
	public String getSetMethod()
	{
		return setMethod;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getSetMethod <em>Set Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Set Method</em>' attribute.
	 * @see #getSetMethod()
	 * @generated
	 */
	public void setSetMethod(String newSetMethod)
	{
		String oldSetMethod = setMethod;
		setMethod = newSetMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SET_METHOD, oldSetMethod, setMethod));
	}

	/**
	 * Returns the value of the '<em><b>Schema</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema
	 * @see #setSchema(EXmlVirtualAccessMethodsSchema)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethods_Schema()
	 * @model
	 * @generated
	 */
	public EXmlVirtualAccessMethodsSchema getSchema()
	{
		return schema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethods#getSchema <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlVirtualAccessMethodsSchema
	 * @see #getSchema()
	 * @generated
	 */
	public void setSchema(EXmlVirtualAccessMethodsSchema newSchema)
	{
		EXmlVirtualAccessMethodsSchema oldSchema = schema;
		schema = newSchema == null ? SCHEMA_EDEFAULT : newSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SCHEMA, oldSchema, schema));
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
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__GET_METHOD:
				return getGetMethod();
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SET_METHOD:
				return getSetMethod();
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SCHEMA:
				return getSchema();
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
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__GET_METHOD:
				setGetMethod((String)newValue);
				return;
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SET_METHOD:
				setSetMethod((String)newValue);
				return;
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SCHEMA:
				setSchema((EXmlVirtualAccessMethodsSchema)newValue);
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
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__GET_METHOD:
				setGetMethod(GET_METHOD_EDEFAULT);
				return;
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SET_METHOD:
				setSetMethod(SET_METHOD_EDEFAULT);
				return;
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SCHEMA:
				setSchema(SCHEMA_EDEFAULT);
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
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__GET_METHOD:
				return GET_METHOD_EDEFAULT == null ? getMethod != null : !GET_METHOD_EDEFAULT.equals(getMethod);
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SET_METHOD:
				return SET_METHOD_EDEFAULT == null ? setMethod != null : !SET_METHOD_EDEFAULT.equals(setMethod);
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS__SCHEMA:
				return schema != SCHEMA_EDEFAULT;
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
		result.append(" (getMethod: ");
		result.append(getMethod);
		result.append(", setMethod: ");
		result.append(setMethod);
		result.append(", schema: ");
		result.append(schema);
		result.append(')');
		return result.toString();
	}

} // EXmlVirtualAccessMethods
