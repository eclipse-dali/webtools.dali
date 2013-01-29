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

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.common.core.utility.TextRange;

import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Named Stored Procedure Query</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedStoredProcedureQuery()
 * @model kind="class"
 * @generated
 */
public class XmlNamedStoredProcedureQuery extends EBaseObjectImpl implements XmlNamedStoredProcedureQuery_2_1
{
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

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
	 * The cached value of the '{@link #getHints() <em>Hints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHints()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlQueryHint> hints;

	/**
	 * The cached value of the '{@link #getResultClasses() <em>Result Classes</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<String> resultClasses;

	/**
	 * The cached value of the '{@link #getResultSetMappings() <em>Result Set Mappings</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultSetMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<String> resultSetMappings;

	/**
	 * The default value of the '{@link #getProcedureName() <em>Procedure Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcedureName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROCEDURE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProcedureName() <em>Procedure Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcedureName()
	 * @generated
	 * @ordered
	 */
	protected String procedureName = PROCEDURE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlStoredProcedureParameter_2_1> parameters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlNamedStoredProcedureQuery()
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
		return OrmPackage.Literals.XML_NAMED_STORED_PROCEDURE_QUERY;
	}

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlQuery_2_0_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	public void setDescription(String newDescription)
	{
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__DESCRIPTION, oldDescription, description));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlQuery_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Hints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlQueryHint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hints</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlQuery_Hints()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlQueryHint> getHints()
	{
		if (hints == null)
		{
			hints = new EObjectContainmentEList<XmlQueryHint>(XmlQueryHint.class, this, OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS);
		}
		return hints;
	}

	/**
	 * Returns the value of the '<em><b>Result Classes</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Classes</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Classes</em>' attribute list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedStoredProcedureQuery_2_1_ResultClasses()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public EList<String> getResultClasses()
	{
		if (resultClasses == null)
		{
			resultClasses = new EDataTypeUniqueEList<String>(String.class, this, OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES);
		}
		return resultClasses;
	}

	/**
	 * Returns the value of the '<em><b>Result Set Mappings</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Set Mappings</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Set Mappings</em>' attribute list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedStoredProcedureQuery_2_1_ResultSetMappings()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public EList<String> getResultSetMappings()
	{
		if (resultSetMappings == null)
		{
			resultSetMappings = new EDataTypeUniqueEList<String>(String.class, this, OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS);
		}
		return resultSetMappings;
	}

	/**
	 * Returns the value of the '<em><b>Procedure Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Procedure Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Procedure Name</em>' attribute.
	 * @see #setProcedureName(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedStoredProcedureQuery_2_1_ProcedureName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getProcedureName()
	{
		return procedureName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery#getProcedureName <em>Procedure Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Procedure Name</em>' attribute.
	 * @see #getProcedureName()
	 * @generated
	 */
	public void setProcedureName(String newProcedureName)
	{
		String oldProcedureName = procedureName;
		procedureName = newProcedureName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME, oldProcedureName, procedureName));
	}

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlStoredProcedureParameter_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlNamedStoredProcedureQuery_2_1_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlStoredProcedureParameter_2_1> getParameters()
	{
		if (parameters == null)
		{
			parameters = new EObjectContainmentEList<XmlStoredProcedureParameter_2_1>(XmlStoredProcedureParameter_2_1.class, this, OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS);
		}
		return parameters;
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
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				return ((InternalEList<?>)getHints()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__DESCRIPTION:
				return getDescription();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME:
				return getName();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				return getHints();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES:
				return getResultClasses();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS:
				return getResultSetMappings();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME:
				return getProcedureName();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				return getParameters();
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
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				getHints().clear();
				getHints().addAll((Collection<? extends XmlQueryHint>)newValue);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES:
				getResultClasses().clear();
				getResultClasses().addAll((Collection<? extends String>)newValue);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS:
				getResultSetMappings().clear();
				getResultSetMappings().addAll((Collection<? extends String>)newValue);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME:
				setProcedureName((String)newValue);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends XmlStoredProcedureParameter_2_1>)newValue);
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
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				getHints().clear();
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES:
				getResultClasses().clear();
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS:
				getResultSetMappings().clear();
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME:
				setProcedureName(PROCEDURE_NAME_EDEFAULT);
				return;
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				getParameters().clear();
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
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				return hints != null && !hints.isEmpty();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES:
				return resultClasses != null && !resultClasses.isEmpty();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS:
				return resultSetMappings != null && !resultSetMappings.isEmpty();
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME:
				return PROCEDURE_NAME_EDEFAULT == null ? procedureName != null : !PROCEDURE_NAME_EDEFAULT.equals(procedureName);
			case OrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
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
		result.append(" (description: ");
		result.append(description);
		result.append(", name: ");
		result.append(name);
		result.append(", resultClasses: ");
		result.append(resultClasses);
		result.append(", resultSetMappings: ");
		result.append(resultSetMappings);
		result.append(", procedureName: ");
		result.append(procedureName);
		result.append(')');
		return result.toString();
	}


	public TextRange getNameTextRange() {
		return getAttributeTextRange(JPA.NAME);
	}


	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			OrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery(),
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildProcedureNameTranslator(),
			buildDescriptionTranslator(),
			XmlStoredProcedureParameter.buildTranslator(JPA2_1.PARAMETER, OrmV2_1Package.eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_Parameters()),
			buildResultClassTranslator(),
			buildResultSetMappingTranslator(),
			XmlQueryHint.buildTranslator(JPA.HINT, OrmPackage.eINSTANCE.getXmlQuery_Hints()),
		};
	}

	protected static Translator buildNameTranslator() {
		return new Translator(JPA.NAME, OrmPackage.eINSTANCE.getXmlQuery_Name(), Translator.DOM_ATTRIBUTE);
	}
	protected static Translator buildProcedureNameTranslator() {
		return new Translator(JPA2_1.PROCEDURE_NAME, OrmV2_1Package.eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ProcedureName(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildDescriptionTranslator() {
		return new Translator(JPA.DESCRIPTION, OrmV2_0Package.eINSTANCE.getXmlQuery_2_0_Description());
	}

	protected static Translator buildResultClassTranslator() {
		return new Translator(JPA2_1.RESULT_CLASS, OrmV2_1Package.eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ResultClasses());
	}

	protected static Translator buildResultSetMappingTranslator() {
		return new Translator(JPA2_1.RESULT_SET_MAPPING, OrmV2_1Package.eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ResultSetMappings());
	}
} // XmlNamedStoredProcedureQuery
