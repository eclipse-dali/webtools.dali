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

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Join Nodes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes#getXmlJoinNodes <em>Xml Join Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNodes()
 * @model kind="class"
 * @generated
 */
public class EXmlJoinNodes extends EJavaAttribute implements EContainerJavaAttribute, ETypedJavaAttribute
{
	/**
	 * The default value of the '{@link #getContainerType() <em>Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerType()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTAINER_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getContainerType() <em>Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerType()
	 * @generated
	 * @ordered
	 */
	protected String containerType = CONTAINER_TYPE_EDEFAULT;
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;
	/**
	 * The cached value of the '{@link #getXmlJoinNodes() <em>Xml Join Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlJoinNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlJoinNode> xmlJoinNodes;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlJoinNodes()
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
		return OxmPackage.Literals.EXML_JOIN_NODES;
	}
	
	
	/**
	 * Returns the value of the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container Type</em>' attribute.
	 * @see #setContainerType(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEContainerJavaAttribute_ContainerType()
	 * @model
	 * @generated
	 */
	public String getContainerType()
	{
		return containerType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes#getContainerType <em>Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container Type</em>' attribute.
	 * @see #getContainerType()
	 * @generated
	 */
	public void setContainerType(String newContainerType)
	{
		String oldContainerType = containerType;
		containerType = newContainerType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_JOIN_NODES__CONTAINER_TYPE, oldContainerType, containerType));
	}

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getETypedJavaAttribute_Type()
	 * @model
	 * @generated
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNodes#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	public void setType(String newType)
	{
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_JOIN_NODES__TYPE, oldType, type));
	}

	/**
	 * Returns the value of the '<em><b>Xml Join Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJoinNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Join Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Join Nodes</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlJoinNodes_XmlJoinNodes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlJoinNode> getXmlJoinNodes()
	{
		if (xmlJoinNodes == null)
		{
			xmlJoinNodes = new EObjectContainmentEList<EXmlJoinNode>(EXmlJoinNode.class, this, OxmPackage.EXML_JOIN_NODES__XML_JOIN_NODES);
		}
		return xmlJoinNodes;
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
			case OxmPackage.EXML_JOIN_NODES__XML_JOIN_NODES:
				return ((InternalEList<?>)getXmlJoinNodes()).basicRemove(otherEnd, msgs);
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
			case OxmPackage.EXML_JOIN_NODES__CONTAINER_TYPE:
				return getContainerType();
			case OxmPackage.EXML_JOIN_NODES__TYPE:
				return getType();
			case OxmPackage.EXML_JOIN_NODES__XML_JOIN_NODES:
				return getXmlJoinNodes();
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
			case OxmPackage.EXML_JOIN_NODES__CONTAINER_TYPE:
				setContainerType((String)newValue);
				return;
			case OxmPackage.EXML_JOIN_NODES__TYPE:
				setType((String)newValue);
				return;
			case OxmPackage.EXML_JOIN_NODES__XML_JOIN_NODES:
				getXmlJoinNodes().clear();
				getXmlJoinNodes().addAll((Collection<? extends EXmlJoinNode>)newValue);
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
			case OxmPackage.EXML_JOIN_NODES__CONTAINER_TYPE:
				setContainerType(CONTAINER_TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_JOIN_NODES__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case OxmPackage.EXML_JOIN_NODES__XML_JOIN_NODES:
				getXmlJoinNodes().clear();
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
			case OxmPackage.EXML_JOIN_NODES__CONTAINER_TYPE:
				return CONTAINER_TYPE_EDEFAULT == null ? containerType != null : !CONTAINER_TYPE_EDEFAULT.equals(containerType);
			case OxmPackage.EXML_JOIN_NODES__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case OxmPackage.EXML_JOIN_NODES__XML_JOIN_NODES:
				return xmlJoinNodes != null && !xmlJoinNodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == EContainerJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_JOIN_NODES__CONTAINER_TYPE: return OxmPackage.ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE;
				default: return -1;
			}
		}
		if (baseClass == ETypedJavaAttribute.class)
		{
			switch (derivedFeatureID)
			{
				case OxmPackage.EXML_JOIN_NODES__TYPE: return OxmPackage.ETYPED_JAVA_ATTRIBUTE__TYPE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
	{
		if (baseClass == EContainerJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.ECONTAINER_JAVA_ATTRIBUTE__CONTAINER_TYPE: return OxmPackage.EXML_JOIN_NODES__CONTAINER_TYPE;
				default: return -1;
			}
		}
		if (baseClass == ETypedJavaAttribute.class)
		{
			switch (baseFeatureID)
			{
				case OxmPackage.ETYPED_JAVA_ATTRIBUTE__TYPE: return OxmPackage.EXML_JOIN_NODES__TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (containerType: ");
		result.append(containerType);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}


	// ***** translators *****
	
	static class XmlJoinNodesTranslator
			extends AbstractJavaAttributeTranslator {
		
		XmlJoinNodesTranslator(String domPathAndName, EStructuralFeature eStructuralFeature) {
			super(domPathAndName, eStructuralFeature, buildTranslatorChildren());
		}
		
		private static Translator[] buildTranslatorChildren() {
			return new Translator[] {
			};
		}
		
		@Override
		public EObject createEMFObject(String nodeName, String readAheadName) {
			return OxmFactory.eINSTANCE.createEXmlJoinNodes();
		}
	}
}
