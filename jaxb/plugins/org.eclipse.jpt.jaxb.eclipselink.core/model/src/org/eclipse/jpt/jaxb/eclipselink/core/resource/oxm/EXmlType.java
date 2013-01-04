/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getFactoryClass <em>Factory Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getFactoryMethod <em>Factory Method</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getPropOrder <em>Prop Order</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlType()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class EXmlType extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

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
	 * The default value of the '{@link #getFactoryClass() <em>Factory Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFactoryClass()
	 * @generated
	 * @ordered
	 */
	protected static final String FACTORY_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFactoryClass() <em>Factory Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFactoryClass()
	 * @generated
	 * @ordered
	 */
	protected String factoryClass = FACTORY_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getFactoryMethod() <em>Factory Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFactoryMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String FACTORY_METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFactoryMethod() <em>Factory Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFactoryMethod()
	 * @generated
	 * @ordered
	 */
	protected String factoryMethod = FACTORY_METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropOrder() <em>Prop Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropOrder()
	 * @generated
	 * @ordered
	 */
	protected static final List<String> PROP_ORDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropOrder() <em>Prop Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropOrder()
	 * @generated
	 * @ordered
	 */
	protected List<String> propOrder = PROP_ORDER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlType()
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
		return OxmPackage.Literals.EXML_TYPE;
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlType_Name()
	 * @model
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_TYPE__NAME, oldName, name));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlType_Namespace()
	 * @model
	 * @generated
	 */
	public String getNamespace()
	{
		return namespace;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getNamespace <em>Namespace</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_TYPE__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * Returns the value of the '<em><b>Factory Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Factory Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Factory Class</em>' attribute.
	 * @see #setFactoryClass(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlType_FactoryClass()
	 * @model
	 * @generated
	 */
	public String getFactoryClass()
	{
		return factoryClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getFactoryClass <em>Factory Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Factory Class</em>' attribute.
	 * @see #getFactoryClass()
	 * @generated
	 */
	public void setFactoryClass(String newFactoryClass)
	{
		String oldFactoryClass = factoryClass;
		factoryClass = newFactoryClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_TYPE__FACTORY_CLASS, oldFactoryClass, factoryClass));
	}

	/**
	 * Returns the value of the '<em><b>Factory Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Factory Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Factory Method</em>' attribute.
	 * @see #setFactoryMethod(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlType_FactoryMethod()
	 * @model
	 * @generated
	 */
	public String getFactoryMethod()
	{
		return factoryMethod;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getFactoryMethod <em>Factory Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Factory Method</em>' attribute.
	 * @see #getFactoryMethod()
	 * @generated
	 */
	public void setFactoryMethod(String newFactoryMethod)
	{
		String oldFactoryMethod = factoryMethod;
		factoryMethod = newFactoryMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_TYPE__FACTORY_METHOD, oldFactoryMethod, factoryMethod));
	}

	/**
	 * Returns the value of the '<em><b>Prop Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prop Order</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prop Order</em>' attribute.
	 * @see #setPropOrder(List)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlType_PropOrder()
	 * @model dataType="org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EPropOrder" many="false"
	 * @generated
	 */
	public List<String> getPropOrder()
	{
		return propOrder;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType#getPropOrder <em>Prop Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prop Order</em>' attribute.
	 * @see #getPropOrder()
	 * @generated
	 */
	public void setPropOrder(List<String> newPropOrder)
	{
		List<String> oldPropOrder = propOrder;
		propOrder = newPropOrder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_TYPE__PROP_ORDER, oldPropOrder, propOrder));
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
			case OxmPackage.EXML_TYPE__NAME:
				return getName();
			case OxmPackage.EXML_TYPE__NAMESPACE:
				return getNamespace();
			case OxmPackage.EXML_TYPE__FACTORY_CLASS:
				return getFactoryClass();
			case OxmPackage.EXML_TYPE__FACTORY_METHOD:
				return getFactoryMethod();
			case OxmPackage.EXML_TYPE__PROP_ORDER:
				return getPropOrder();
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
			case OxmPackage.EXML_TYPE__NAME:
				setName((String)newValue);
				return;
			case OxmPackage.EXML_TYPE__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case OxmPackage.EXML_TYPE__FACTORY_CLASS:
				setFactoryClass((String)newValue);
				return;
			case OxmPackage.EXML_TYPE__FACTORY_METHOD:
				setFactoryMethod((String)newValue);
				return;
			case OxmPackage.EXML_TYPE__PROP_ORDER:
				setPropOrder((List<String>)newValue);
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
			case OxmPackage.EXML_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OxmPackage.EXML_TYPE__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case OxmPackage.EXML_TYPE__FACTORY_CLASS:
				setFactoryClass(FACTORY_CLASS_EDEFAULT);
				return;
			case OxmPackage.EXML_TYPE__FACTORY_METHOD:
				setFactoryMethod(FACTORY_METHOD_EDEFAULT);
				return;
			case OxmPackage.EXML_TYPE__PROP_ORDER:
				setPropOrder(PROP_ORDER_EDEFAULT);
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
			case OxmPackage.EXML_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OxmPackage.EXML_TYPE__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case OxmPackage.EXML_TYPE__FACTORY_CLASS:
				return FACTORY_CLASS_EDEFAULT == null ? factoryClass != null : !FACTORY_CLASS_EDEFAULT.equals(factoryClass);
			case OxmPackage.EXML_TYPE__FACTORY_METHOD:
				return FACTORY_METHOD_EDEFAULT == null ? factoryMethod != null : !FACTORY_METHOD_EDEFAULT.equals(factoryMethod);
			case OxmPackage.EXML_TYPE__PROP_ORDER:
				return PROP_ORDER_EDEFAULT == null ? propOrder != null : !PROP_ORDER_EDEFAULT.equals(propOrder);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", namespace: ");
		result.append(namespace);
		result.append(", factoryClass: ");
		result.append(factoryClass);
		result.append(", factoryMethod: ");
		result.append(factoryMethod);
		result.append(", propOrder: ");
		result.append(propOrder);
		result.append(')');
		return result.toString();
	}
	
	
	// ***** validation *****
	
	public TextRange getNamespaceTextRange() {
		return getAttributeTextRange(Oxm.NAMESPACE);
	}
	
	public boolean namespaceTouches(int pos) {
		TextRange textRange = getAttributeCodeAssistTextRange(Oxm.NAMESPACE);
		return (textRange != null) && (textRange.touches(pos));
	}
	
	public TextRange getNameTextRange() {
		return getAttributeTextRange(Oxm.NAME);
	}
	
	public boolean nameTouches(int pos) {
		TextRange textRange = getAttributeCodeAssistTextRange(Oxm.NAME);
		return (textRange != null) && (textRange.touches(pos));
	}
	
	
	// ***** translators *****
	
	public static Translator buildTranslator() {
		return new SimpleTranslator(Oxm.XML_TYPE, OxmPackage.eINSTANCE.getEAbstractTypeMapping_XmlType(), buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildNamespaceTranslator(),
			buildFactoryClassTranslator(),
			buildFactoryMethodTranslator(),
			buildPropOrderTranslator(),
		};
	}
	
	protected static Translator buildNameTranslator() {
		return new Translator(
			Oxm.NAME,
			OxmPackage.eINSTANCE.getEXmlType_Name(), 
			Translator.DOM_ATTRIBUTE | Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
	
	protected static Translator buildNamespaceTranslator() {
		return new Translator(
			Oxm.NAMESPACE,
			OxmPackage.eINSTANCE.getEXmlType_Namespace(), 
			Translator.DOM_ATTRIBUTE | Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
	
	protected static Translator buildFactoryClassTranslator() {
		return new Translator(
			Oxm.FACTORY_CLASS,
			OxmPackage.eINSTANCE.getEXmlType_FactoryClass(), 
			Translator.DOM_ATTRIBUTE | Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
	
	protected static Translator buildFactoryMethodTranslator() {
		return new Translator(
			Oxm.FACTORY_METHOD,
			OxmPackage.eINSTANCE.getEXmlType_FactoryMethod(), 
			Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildPropOrderTranslator() {
		return new Translator(
			Oxm.PROP_ORDER,
			OxmPackage.eINSTANCE.getEXmlType_PropOrder(), 
			Translator.DOM_ATTRIBUTE);
	}
}
