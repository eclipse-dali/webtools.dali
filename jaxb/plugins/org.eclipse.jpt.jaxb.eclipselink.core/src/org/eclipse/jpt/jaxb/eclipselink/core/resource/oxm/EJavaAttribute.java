/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.wst.common.internal.emf.resource.MultiObjectTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EJava Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute#getJavaAttribute <em>Java Attribute</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute#getXmlAccessorType <em>Xml Accessor Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaAttribute()
 * @model kind="class" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public abstract class EJavaAttribute extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getJavaAttribute() <em>Java Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final String JAVA_ATTRIBUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJavaAttribute() <em>Java Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaAttribute()
	 * @generated
	 * @ordered
	 */
	protected String javaAttribute = JAVA_ATTRIBUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getXmlAccessorType() <em>Xml Accessor Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAccessorType()
	 * @generated
	 * @ordered
	 */
	protected static final EXmlAccessType XML_ACCESSOR_TYPE_EDEFAULT = EXmlAccessType.FIELD;

	/**
	 * The cached value of the '{@link #getXmlAccessorType() <em>Xml Accessor Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlAccessorType()
	 * @generated
	 * @ordered
	 */
	protected EXmlAccessType xmlAccessorType = XML_ACCESSOR_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EJavaAttribute()
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
		return OxmPackage.Literals.EJAVA_ATTRIBUTE;
	}

	/**
	 * Returns the value of the '<em><b>Java Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Attribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Attribute</em>' attribute.
	 * @see #setJavaAttribute(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaAttribute_JavaAttribute()
	 * @model
	 * @generated
	 */
	public String getJavaAttribute()
	{
		return javaAttribute;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute#getJavaAttribute <em>Java Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Attribute</em>' attribute.
	 * @see #getJavaAttribute()
	 * @generated
	 */
	public void setJavaAttribute(String newJavaAttribute)
	{
		String oldJavaAttribute = javaAttribute;
		javaAttribute = newJavaAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE, oldJavaAttribute, javaAttribute));
	}

	/**
	 * Returns the value of the '<em><b>Xml Accessor Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Accessor Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Accessor Type</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType
	 * @see #setXmlAccessorType(EXmlAccessType)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEJavaAttribute_XmlAccessorType()
	 * @model
	 * @generated
	 */
	public EXmlAccessType getXmlAccessorType()
	{
		return xmlAccessorType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute#getXmlAccessorType <em>Xml Accessor Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Accessor Type</em>' attribute.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType
	 * @see #getXmlAccessorType()
	 * @generated
	 */
	public void setXmlAccessorType(EXmlAccessType newXmlAccessorType)
	{
		EXmlAccessType oldXmlAccessorType = xmlAccessorType;
		xmlAccessorType = newXmlAccessorType == null ? XML_ACCESSOR_TYPE_EDEFAULT : newXmlAccessorType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE, oldXmlAccessorType, xmlAccessorType));
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
			case OxmPackage.EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE:
				return getJavaAttribute();
			case OxmPackage.EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE:
				return getXmlAccessorType();
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
			case OxmPackage.EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE:
				setJavaAttribute((String)newValue);
				return;
			case OxmPackage.EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE:
				setXmlAccessorType((EXmlAccessType)newValue);
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
			case OxmPackage.EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE:
				setJavaAttribute(JAVA_ATTRIBUTE_EDEFAULT);
				return;
			case OxmPackage.EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE:
				setXmlAccessorType(XML_ACCESSOR_TYPE_EDEFAULT);
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
			case OxmPackage.EJAVA_ATTRIBUTE__JAVA_ATTRIBUTE:
				return JAVA_ATTRIBUTE_EDEFAULT == null ? javaAttribute != null : !JAVA_ATTRIBUTE_EDEFAULT.equals(javaAttribute);
			case OxmPackage.EJAVA_ATTRIBUTE__XML_ACCESSOR_TYPE:
				return xmlAccessorType != XML_ACCESSOR_TYPE_EDEFAULT;
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
		result.append(" (javaAttribute: ");
		result.append(javaAttribute);
		result.append(", xmlAccessorType: ");
		result.append(xmlAccessorType);
		result.append(')');
		return result.toString();
	}
	
	
	// ***** misc *****
	
	/** return the xml element name associated with this java attribute (e.g. "xml-transformation") */
	public abstract String getElementName();
	
	
	// ***** translators *****
	
	static class JavaAttributesTranslator
			extends MultiObjectTranslator {
		
		protected static String DOM_PATH = 
				Oxm.JAVA_ATTRIBUTES + "/"
						+ Oxm.XML_ANY_ATTRIBUTE + ","
						+ Oxm.XML_ANY_ELEMENT + ","
						+ Oxm.XML_ATTRIBUTE + ","
						+ Oxm.XML_ELEMENT + ","
						+ Oxm.XML_ELEMENT_REF + ","
						+ Oxm.XML_ELEMENT_REFS + ","
						+ Oxm.XML_ELEMENTS + ","
						+ Oxm.XML_INVERSE_REFERENCE + ","
						+ Oxm.XML_JAVA_TYPE_ADAPTER + ","
						+ Oxm.XML_JOIN_NODES + ","
						+ Oxm.XML_TRANSFORMATION + ","
						+ Oxm.XML_TRANSIENT + ","
						+ Oxm.XML_VALUE;
		
		protected static Map<String, Translator> DELEGATES = new HashMap<String, Translator>();
		
						
		JavaAttributesTranslator() {
			super(DOM_PATH, OxmPackage.eINSTANCE.getEJavaType_JavaAttributes());
		}
		
		
		protected static Map<String, Translator> delegates() {
			if (DELEGATES.isEmpty()) {
				
				String pathPrefix = Oxm.JAVA_ATTRIBUTES + "/";
				EStructuralFeature eStructuralFeature = OxmPackage.eINSTANCE.getEJavaType_JavaAttributes();
				
				String path = Oxm.XML_ANY_ATTRIBUTE;
				DELEGATES.put(path, 
					new EXmlAnyAttribute.XmlAnyAttributeTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_ANY_ELEMENT;
				DELEGATES.put(path, 
					new EXmlAnyElement.XmlAnyElementTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_ATTRIBUTE;
				DELEGATES.put(path, 
					new EXmlAttribute.XmlAttributeTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_ELEMENT;
				DELEGATES.put(path, 
					new EXmlElement.XmlElementTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_ELEMENT_REF;
				DELEGATES.put(path, 
					new EXmlElementRef.XmlElementRefTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_ELEMENT_REFS;
				DELEGATES.put(path, 
					new EXmlElementRefs.XmlElementRefsTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_ELEMENTS;
				DELEGATES.put(path, 
					new EXmlElements.XmlElementsTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_INVERSE_REFERENCE;
				DELEGATES.put(path, 
					new EXmlInverseReference.XmlInverseReferenceTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_JAVA_TYPE_ADAPTER;
				DELEGATES.put(path, 
					new EXmlJavaTypeAdapter.XmlJavaTypeAdapterTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_JOIN_NODES;
				DELEGATES.put(path, 
					new EXmlJoinNodes.XmlJoinNodesTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_TRANSFORMATION;
				DELEGATES.put(path, 
					new EXmlTransformation.XmlTransformationTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_TRANSIENT;
				DELEGATES.put(path, 
					new EXmlTransient.XmlTransientTranslator(pathPrefix + path, eStructuralFeature));
				
				path = Oxm.XML_VALUE;
				DELEGATES.put(path, 
					new EXmlValue.XmlValueTranslator(pathPrefix + path, eStructuralFeature));
			}
			return DELEGATES;
		}
		
		protected static String wrappedPath(String path) {
			return Oxm.JAVA_ATTRIBUTES + "/" + path;
		}
		
		@Override
		public Translator getDelegateFor(EObject o) {
			switch (o.eClass().getClassifierID()) {
				case OxmPackage.EXML_ANY_ATTRIBUTE :
					return delegates().get(Oxm.XML_ANY_ATTRIBUTE);
				case OxmPackage.EXML_ANY_ELEMENT :
					return delegates().get(Oxm.XML_ANY_ELEMENT);
				case OxmPackage.EXML_ATTRIBUTE :
					return delegates().get(Oxm.XML_ATTRIBUTE);
				case OxmPackage.EXML_ELEMENT :
					return delegates().get(Oxm.XML_ELEMENT);
				case OxmPackage.EXML_ELEMENT_REF :
					return delegates().get(Oxm.XML_ELEMENT_REF);
				case OxmPackage.EXML_ELEMENT_REFS:
					return delegates().get(Oxm.XML_ELEMENT_REFS);
				case OxmPackage.EXML_ELEMENTS:
					return delegates().get(Oxm.XML_ELEMENTS);
				case OxmPackage.EXML_INVERSE_REFERENCE :
					return delegates().get(Oxm.XML_INVERSE_REFERENCE);
				case OxmPackage.EXML_JAVA_TYPE_ADAPTER :
					return delegates().get(Oxm.XML_JAVA_TYPE_ADAPTER);
				case OxmPackage.EXML_JOIN_NODES :
					return delegates().get(Oxm.XML_JOIN_NODES);
				case OxmPackage.EXML_TRANSFORMATION :
					return delegates().get(Oxm.XML_TRANSFORMATION);
				case OxmPackage.EXML_TRANSIENT :
					return delegates().get(Oxm.XML_TRANSIENT);
				case OxmPackage.EXML_VALUE :
					return delegates().get(Oxm.XML_VALUE);
				
			}
			throw new IllegalStateException("Java attribute expected"); //$NON-NLS-1$
		}
		
		@Override
		public Translator getDelegateFor(String domName, String readAheadName) {
			return delegates().get(domName);
		}
	}
	
	
	abstract static class AbstractJavaAttributeTranslator
			extends SimpleTranslator {
		
		protected AbstractJavaAttributeTranslator(
				String domPathAndName, EStructuralFeature eStructuralFeature, Translator[] translatorChildren) {
			super(Oxm.JAVA_ATTRIBUTES + "/" + domPathAndName, eStructuralFeature, translatorChildren);
		}
		
		protected static Translator buildJavaAttributeTranslator() {
			return new Translator(
					Oxm.JAVA_ATTRIBUTE,
					OxmPackage.eINSTANCE.getEJavaAttribute_JavaAttribute(), 
					Translator.DOM_ATTRIBUTE);
		}
		
		protected static Translator buildXmlAccessorTypeTranslator() {
			return new Translator(
					Oxm.XML_ACCESSOR_TYPE,
					OxmPackage.eINSTANCE.getEJavaAttribute_XmlAccessorType(), 
					Translator.DOM_ATTRIBUTE);
		}
	}
}
