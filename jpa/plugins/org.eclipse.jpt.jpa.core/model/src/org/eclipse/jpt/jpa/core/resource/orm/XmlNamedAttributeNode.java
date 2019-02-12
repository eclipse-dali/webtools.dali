/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Named Attribute Node</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedAttributeNode()
 * @model kind="class"
 * @generated
 */
public class XmlNamedAttributeNode extends EBaseObjectImpl implements XmlNamedAttributeNode_2_1
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
	 * The default value of the '{@link #getSubgraph() <em>Subgraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubgraph()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBGRAPH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubgraph() <em>Subgraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubgraph()
	 * @generated
	 * @ordered
	 */
	protected String subgraph = SUBGRAPH_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeySubgraph() <em>Key Subgraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeySubgraph()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_SUBGRAPH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKeySubgraph() <em>Key Subgraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeySubgraph()
	 * @generated
	 * @ordered
	 */
	protected String keySubgraph = KEY_SUBGRAPH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlNamedAttributeNode()
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
		return OrmPackage.Literals.XML_NAMED_ATTRIBUTE_NODE;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedAttributeNode_2_1_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedAttributeNode#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_NAMED_ATTRIBUTE_NODE__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Subgraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subgraph</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subgraph</em>' attribute.
	 * @see #setSubgraph(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedAttributeNode_2_1_Subgraph()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSubgraph()
	{
		return subgraph;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedAttributeNode#getSubgraph <em>Subgraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subgraph</em>' attribute.
	 * @see #getSubgraph()
	 * @generated
	 */
	public void setSubgraph(String newSubgraph)
	{
		String oldSubgraph = subgraph;
		subgraph = newSubgraph;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_NAMED_ATTRIBUTE_NODE__SUBGRAPH, oldSubgraph, subgraph));
	}

	/**
	 * Returns the value of the '<em><b>Key Subgraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key Subgraph</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Subgraph</em>' attribute.
	 * @see #setKeySubgraph(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedAttributeNode_2_1_KeySubgraph()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getKeySubgraph()
	{
		return keySubgraph;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedAttributeNode#getKeySubgraph <em>Key Subgraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Subgraph</em>' attribute.
	 * @see #getKeySubgraph()
	 * @generated
	 */
	public void setKeySubgraph(String newKeySubgraph)
	{
		String oldKeySubgraph = keySubgraph;
		keySubgraph = newKeySubgraph;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_NAMED_ATTRIBUTE_NODE__KEY_SUBGRAPH, oldKeySubgraph, keySubgraph));
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
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__NAME:
				return getName();
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__SUBGRAPH:
				return getSubgraph();
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__KEY_SUBGRAPH:
				return getKeySubgraph();
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
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__SUBGRAPH:
				setSubgraph((String)newValue);
				return;
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__KEY_SUBGRAPH:
				setKeySubgraph((String)newValue);
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
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__SUBGRAPH:
				setSubgraph(SUBGRAPH_EDEFAULT);
				return;
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__KEY_SUBGRAPH:
				setKeySubgraph(KEY_SUBGRAPH_EDEFAULT);
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
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__SUBGRAPH:
				return SUBGRAPH_EDEFAULT == null ? subgraph != null : !SUBGRAPH_EDEFAULT.equals(subgraph);
			case OrmPackage.XML_NAMED_ATTRIBUTE_NODE__KEY_SUBGRAPH:
				return KEY_SUBGRAPH_EDEFAULT == null ? keySubgraph != null : !KEY_SUBGRAPH_EDEFAULT.equals(keySubgraph);
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
		result.append(", subgraph: ");
		result.append(subgraph);
		result.append(", keySubgraph: ");
		result.append(keySubgraph);
		result.append(')');
		return result.toString();
	}


	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			OrmPackage.eINSTANCE.getXmlNamedAttributeNode(),
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildSubgraphTranslator(),
			buildKeySubgraphTranslator(),
		};
	}

	protected static Translator buildNameTranslator() {
		return new Translator(JPA.NAME, OrmV2_1Package.eINSTANCE.getXmlNamedAttributeNode_2_1_Name(), Translator.DOM_ATTRIBUTE);
	}	

	protected static Translator buildSubgraphTranslator() {
		return new Translator(JPA2_1.SUBGRAPH, OrmV2_1Package.eINSTANCE.getXmlNamedAttributeNode_2_1_Subgraph(), Translator.DOM_ATTRIBUTE);
	}	

	protected static Translator buildKeySubgraphTranslator() {
		return new Translator(JPA2_1.KEY_SUBGRAPH, OrmV2_1Package.eINSTANCE.getXmlNamedAttributeNode_2_1_KeySubgraph(), Translator.DOM_ATTRIBUTE);
	}	

} // XmlNamedAttributeNode
