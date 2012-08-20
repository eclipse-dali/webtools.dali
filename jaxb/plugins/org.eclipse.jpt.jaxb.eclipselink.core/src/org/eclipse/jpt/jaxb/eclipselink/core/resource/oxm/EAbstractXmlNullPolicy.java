/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.wst.common.internal.emf.resource.MultiObjectTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAbstract Xml Null Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#isXsiNilRepresentsNull <em>Xsi Nil Represents Null</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#isEmptyNodeRepresentsNull <em>Empty Node Represents Null</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#getNullRepresentationForXml <em>Null Representation For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlNullPolicy()
 * @model kind="class" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public abstract class EAbstractXmlNullPolicy extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #isXsiNilRepresentsNull() <em>Xsi Nil Represents Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXsiNilRepresentsNull()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XSI_NIL_REPRESENTS_NULL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isXsiNilRepresentsNull() <em>Xsi Nil Represents Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXsiNilRepresentsNull()
	 * @generated
	 * @ordered
	 */
	protected boolean xsiNilRepresentsNull = XSI_NIL_REPRESENTS_NULL_EDEFAULT;

	/**
	 * The default value of the '{@link #isEmptyNodeRepresentsNull() <em>Empty Node Represents Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEmptyNodeRepresentsNull()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EMPTY_NODE_REPRESENTS_NULL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEmptyNodeRepresentsNull() <em>Empty Node Represents Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEmptyNodeRepresentsNull()
	 * @generated
	 * @ordered
	 */
	protected boolean emptyNodeRepresentsNull = EMPTY_NODE_REPRESENTS_NULL_EDEFAULT;

	/**
	 * The default value of the '{@link #getNullRepresentationForXml() <em>Null Representation For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullRepresentationForXml()
	 * @generated
	 * @ordered
	 */
	protected static final EXmlMarshalNullRepresentation NULL_REPRESENTATION_FOR_XML_EDEFAULT = EXmlMarshalNullRepresentation.XSI_NIL;

	/**
	 * The cached value of the '{@link #getNullRepresentationForXml() <em>Null Representation For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullRepresentationForXml()
	 * @generated
	 * @ordered
	 */
	protected EXmlMarshalNullRepresentation nullRepresentationForXml = NULL_REPRESENTATION_FOR_XML_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EAbstractXmlNullPolicy()
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
		return OxmPackage.Literals.EABSTRACT_XML_NULL_POLICY;
	}

	/**
	 * Returns the value of the '<em><b>Xsi Nil Represents Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xsi Nil Represents Null</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xsi Nil Represents Null</em>' attribute.
	 * @see #setXsiNilRepresentsNull(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlNullPolicy_XsiNilRepresentsNull()
	 * @model
	 * @generated
	 */
	public boolean isXsiNilRepresentsNull()
	{
		return xsiNilRepresentsNull;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#isXsiNilRepresentsNull <em>Xsi Nil Represents Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xsi Nil Represents Null</em>' attribute.
	 * @see #isXsiNilRepresentsNull()
	 * @generated
	 */
	public void setXsiNilRepresentsNull(boolean newXsiNilRepresentsNull)
	{
		boolean oldXsiNilRepresentsNull = xsiNilRepresentsNull;
		xsiNilRepresentsNull = newXsiNilRepresentsNull;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL, oldXsiNilRepresentsNull, xsiNilRepresentsNull));
	}

	/**
	 * Returns the value of the '<em><b>Empty Node Represents Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Empty Node Represents Null</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Empty Node Represents Null</em>' attribute.
	 * @see #setEmptyNodeRepresentsNull(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlNullPolicy_EmptyNodeRepresentsNull()
	 * @model
	 * @generated
	 */
	public boolean isEmptyNodeRepresentsNull()
	{
		return emptyNodeRepresentsNull;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#isEmptyNodeRepresentsNull <em>Empty Node Represents Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Empty Node Represents Null</em>' attribute.
	 * @see #isEmptyNodeRepresentsNull()
	 * @generated
	 */
	public void setEmptyNodeRepresentsNull(boolean newEmptyNodeRepresentsNull)
	{
		boolean oldEmptyNodeRepresentsNull = emptyNodeRepresentsNull;
		emptyNodeRepresentsNull = newEmptyNodeRepresentsNull;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL, oldEmptyNodeRepresentsNull, emptyNodeRepresentsNull));
	}

	/**
	 * Returns the value of the '<em><b>Null Representation For Xml</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Null Representation For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Null Representation For Xml</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation
	 * @see #setNullRepresentationForXml(EXmlMarshalNullRepresentation)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlNullPolicy_NullRepresentationForXml()
	 * @model
	 * @generated
	 */
	public EXmlMarshalNullRepresentation getNullRepresentationForXml()
	{
		return nullRepresentationForXml;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlNullPolicy#getNullRepresentationForXml <em>Null Representation For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Null Representation For Xml</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlMarshalNullRepresentation
	 * @see #getNullRepresentationForXml()
	 * @generated
	 */
	public void setNullRepresentationForXml(EXmlMarshalNullRepresentation newNullRepresentationForXml)
	{
		EXmlMarshalNullRepresentation oldNullRepresentationForXml = nullRepresentationForXml;
		nullRepresentationForXml = newNullRepresentationForXml == null ? NULL_REPRESENTATION_FOR_XML_EDEFAULT : newNullRepresentationForXml;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML, oldNullRepresentationForXml, nullRepresentationForXml));
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
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL:
				return isXsiNilRepresentsNull();
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL:
				return isEmptyNodeRepresentsNull();
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML:
				return getNullRepresentationForXml();
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
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL:
				setXsiNilRepresentsNull((Boolean)newValue);
				return;
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL:
				setEmptyNodeRepresentsNull((Boolean)newValue);
				return;
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML:
				setNullRepresentationForXml((EXmlMarshalNullRepresentation)newValue);
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
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL:
				setXsiNilRepresentsNull(XSI_NIL_REPRESENTS_NULL_EDEFAULT);
				return;
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL:
				setEmptyNodeRepresentsNull(EMPTY_NODE_REPRESENTS_NULL_EDEFAULT);
				return;
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML:
				setNullRepresentationForXml(NULL_REPRESENTATION_FOR_XML_EDEFAULT);
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
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__XSI_NIL_REPRESENTS_NULL:
				return xsiNilRepresentsNull != XSI_NIL_REPRESENTS_NULL_EDEFAULT;
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__EMPTY_NODE_REPRESENTS_NULL:
				return emptyNodeRepresentsNull != EMPTY_NODE_REPRESENTS_NULL_EDEFAULT;
			case OxmPackage.EABSTRACT_XML_NULL_POLICY__NULL_REPRESENTATION_FOR_XML:
				return nullRepresentationForXml != NULL_REPRESENTATION_FOR_XML_EDEFAULT;
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
		result.append(" (xsiNilRepresentsNull: ");
		result.append(xsiNilRepresentsNull);
		result.append(", emptyNodeRepresentsNull: ");
		result.append(emptyNodeRepresentsNull);
		result.append(", nullRepresentationForXml: ");
		result.append(nullRepresentationForXml);
		result.append(')');
		return result.toString();
	}


	// ***** translators *****
	
	static class AbstractXmlNullPolicyTranslator
			extends MultiObjectTranslator {
		
		protected static String DOM_PATH = 
				EclipseLink.XML_IS_SET_NULL_POLICY + ","
						+ EclipseLink.XML_NULL_POLICY;
		
		protected static Map<String, Translator> DELEGATES = new HashMap<String, Translator>();
		
						
		protected AbstractXmlNullPolicyTranslator() {
			super(DOM_PATH, OxmPackage.eINSTANCE.getEXmlAttribute_XmlAbstractNullPolicy());
		}
		
		
		protected static Map<String, Translator> delegates() {
			if (DELEGATES.isEmpty()) {
				String path = EclipseLink.XML_IS_SET_NULL_POLICY;
				DELEGATES.put(path, new EXmlIsSetNullPolicy.XmlIsSetNullPolicyTranslator());
				
				path = EclipseLink.XML_NULL_POLICY;
				DELEGATES.put(path, new EXmlNullPolicy.XmlNullPolicyTranslator());
			}
			return DELEGATES;
		}
		
		@Override
		public Translator getDelegateFor(EObject o) {
			switch (o.eClass().getClassifierID()) {
				case OxmPackage.EXML_IS_SET_NULL_POLICY :
					return delegates().get(EclipseLink.XML_IS_SET_NULL_POLICY);
				case OxmPackage.EXML_NULL_POLICY :
					return delegates().get(EclipseLink.XML_NULL_POLICY);
			}
			throw new IllegalStateException("Null policy expected"); //$NON-NLS-1$
		}
		
		@Override
		public Translator getDelegateFor(String domName, String readAheadName) {
			return delegates().get(domName);
		}
	}
	
	
	abstract static class AbstractAbstractXmlNullPolicyTranslator
			extends SimpleTranslator {
		
		protected AbstractAbstractXmlNullPolicyTranslator(String domPathAndName, Translator[] translatorChildren) {
			super(domPathAndName, OxmPackage.eINSTANCE.getEXmlAttribute_XmlAbstractNullPolicy(), translatorChildren);
		}
	}
}
