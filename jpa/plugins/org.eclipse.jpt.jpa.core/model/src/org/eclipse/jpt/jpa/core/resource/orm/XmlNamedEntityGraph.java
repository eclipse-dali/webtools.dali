/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedEntityGraph_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Named Entity Graph</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedEntityGraph()
 * @model kind="class"
 * @generated
 */
public class XmlNamedEntityGraph extends EBaseObjectImpl implements XmlNamedEntityGraph_2_1
{
	/**
	 * The cached value of the '{@link #getNamedAttributeNodes() <em>Named Attribute Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedAttributeNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlNamedAttributeNode_2_1> namedAttributeNodes;

	/**
	 * The cached value of the '{@link #getSubgraphs() <em>Subgraphs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubgraphs()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlNamedSubgraph_2_1> subgraphs;

	/**
	 * The cached value of the '{@link #getSubclassSubgraphs() <em>Subclass Subgraphs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubclassSubgraphs()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlNamedSubgraph_2_1> subclassSubgraphs;

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
	 * The default value of the '{@link #getIncludeAllAttributes() <em>Include All Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeAllAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean INCLUDE_ALL_ATTRIBUTES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIncludeAllAttributes() <em>Include All Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeAllAttributes()
	 * @generated
	 * @ordered
	 */
	protected Boolean includeAllAttributes = INCLUDE_ALL_ATTRIBUTES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlNamedEntityGraph()
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
		return OrmPackage.Literals.XML_NAMED_ENTITY_GRAPH;
	}

	/**
	 * Returns the value of the '<em><b>Named Attribute Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedAttributeNode_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Attribute Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Attribute Nodes</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedEntityGraph_2_1_NamedAttributeNodes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlNamedAttributeNode_2_1> getNamedAttributeNodes()
	{
		if (namedAttributeNodes == null)
		{
			namedAttributeNodes = new EObjectContainmentEList<XmlNamedAttributeNode_2_1>(XmlNamedAttributeNode_2_1.class, this, OrmPackage.XML_NAMED_ENTITY_GRAPH__NAMED_ATTRIBUTE_NODES);
		}
		return namedAttributeNodes;
	}

	/**
	 * Returns the value of the '<em><b>Subgraphs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subgraphs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subgraphs</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedEntityGraph_2_1_Subgraphs()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlNamedSubgraph_2_1> getSubgraphs()
	{
		if (subgraphs == null)
		{
			subgraphs = new EObjectContainmentEList<XmlNamedSubgraph_2_1>(XmlNamedSubgraph_2_1.class, this, OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBGRAPHS);
		}
		return subgraphs;
	}

	/**
	 * Returns the value of the '<em><b>Subclass Subgraphs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedSubgraph_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subclass Subgraphs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subclass Subgraphs</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedEntityGraph_2_1_SubclassSubgraphs()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlNamedSubgraph_2_1> getSubclassSubgraphs()
	{
		if (subclassSubgraphs == null)
		{
			subclassSubgraphs = new EObjectContainmentEList<XmlNamedSubgraph_2_1>(XmlNamedSubgraph_2_1.class, this, OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBCLASS_SUBGRAPHS);
		}
		return subclassSubgraphs;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedEntityGraph_2_1_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedEntityGraph#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_NAMED_ENTITY_GRAPH__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Include All Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include All Attributes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include All Attributes</em>' attribute.
	 * @see #setIncludeAllAttributes(Boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedEntityGraph_2_1_IncludeAllAttributes()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getIncludeAllAttributes()
	{
		return includeAllAttributes;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedEntityGraph#getIncludeAllAttributes <em>Include All Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Include All Attributes</em>' attribute.
	 * @see #getIncludeAllAttributes()
	 * @generated
	 */
	public void setIncludeAllAttributes(Boolean newIncludeAllAttributes)
	{
		Boolean oldIncludeAllAttributes = includeAllAttributes;
		includeAllAttributes = newIncludeAllAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_NAMED_ENTITY_GRAPH__INCLUDE_ALL_ATTRIBUTES, oldIncludeAllAttributes, includeAllAttributes));
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
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAMED_ATTRIBUTE_NODES:
				return ((InternalEList<?>)getNamedAttributeNodes()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBGRAPHS:
				return ((InternalEList<?>)getSubgraphs()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBCLASS_SUBGRAPHS:
				return ((InternalEList<?>)getSubclassSubgraphs()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAMED_ATTRIBUTE_NODES:
				return getNamedAttributeNodes();
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBGRAPHS:
				return getSubgraphs();
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBCLASS_SUBGRAPHS:
				return getSubclassSubgraphs();
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAME:
				return getName();
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__INCLUDE_ALL_ATTRIBUTES:
				return getIncludeAllAttributes();
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
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAMED_ATTRIBUTE_NODES:
				getNamedAttributeNodes().clear();
				getNamedAttributeNodes().addAll((Collection<? extends XmlNamedAttributeNode_2_1>)newValue);
				return;
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBGRAPHS:
				getSubgraphs().clear();
				getSubgraphs().addAll((Collection<? extends XmlNamedSubgraph_2_1>)newValue);
				return;
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBCLASS_SUBGRAPHS:
				getSubclassSubgraphs().clear();
				getSubclassSubgraphs().addAll((Collection<? extends XmlNamedSubgraph_2_1>)newValue);
				return;
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__INCLUDE_ALL_ATTRIBUTES:
				setIncludeAllAttributes((Boolean)newValue);
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
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAMED_ATTRIBUTE_NODES:
				getNamedAttributeNodes().clear();
				return;
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBGRAPHS:
				getSubgraphs().clear();
				return;
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBCLASS_SUBGRAPHS:
				getSubclassSubgraphs().clear();
				return;
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__INCLUDE_ALL_ATTRIBUTES:
				setIncludeAllAttributes(INCLUDE_ALL_ATTRIBUTES_EDEFAULT);
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
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAMED_ATTRIBUTE_NODES:
				return namedAttributeNodes != null && !namedAttributeNodes.isEmpty();
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBGRAPHS:
				return subgraphs != null && !subgraphs.isEmpty();
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__SUBCLASS_SUBGRAPHS:
				return subclassSubgraphs != null && !subclassSubgraphs.isEmpty();
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_NAMED_ENTITY_GRAPH__INCLUDE_ALL_ATTRIBUTES:
				return INCLUDE_ALL_ATTRIBUTES_EDEFAULT == null ? includeAllAttributes != null : !INCLUDE_ALL_ATTRIBUTES_EDEFAULT.equals(includeAllAttributes);
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
		result.append(", includeAllAttributes: ");
		result.append(includeAllAttributes);
		result.append(')');
		return result.toString();
	}


	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			OrmPackage.eINSTANCE.getXmlNamedEntityGraph(),
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildIncludeAllAttributesTranslator(),
			buildNamedAttributeNodeTranslator(),
			buildSubgraphTranslator(),
			buildSubclassSubgraphTranslator(),
		};
	}

	protected static Translator buildNameTranslator() {
		return new Translator(JPA.NAME, OrmV2_1Package.eINSTANCE.getXmlNamedEntityGraph_2_1_Name(), Translator.DOM_ATTRIBUTE);
	}	

	protected static Translator buildIncludeAllAttributesTranslator() {
		return new Translator(JPA2_1.INCLUDE_ALL_ATTRIBUTES, OrmV2_1Package.eINSTANCE.getXmlNamedEntityGraph_2_1_IncludeAllAttributes(), Translator.DOM_ATTRIBUTE);
	}	

	protected static Translator buildNamedAttributeNodeTranslator() {
		return XmlNamedAttributeNode.buildTranslator(JPA2_1.NAMED_ATTRIBUTE_NODE, OrmV2_1Package.eINSTANCE.getXmlNamedEntityGraph_2_1_NamedAttributeNodes());
	}	

	protected static Translator buildSubgraphTranslator() {
		return XmlNamedSubgraph.buildTranslator(JPA2_1.SUBGRAPH, OrmV2_1Package.eINSTANCE.getXmlNamedEntityGraph_2_1_Subgraphs());
	}

	protected static Translator buildSubclassSubgraphTranslator() {
		return XmlNamedSubgraph.buildTranslator(JPA2_1.SUBCLASS_SUBGRAPH, OrmV2_1Package.eINSTANCE.getXmlNamedEntityGraph_2_1_SubclassSubgraphs());
	}	

} // XmlNamedEntityGraph
