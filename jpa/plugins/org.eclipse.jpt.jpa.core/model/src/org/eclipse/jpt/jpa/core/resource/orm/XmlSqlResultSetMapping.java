/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlSqlResultSetMapping_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstructorResult_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSqlResultSetMapping_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sql Result Set Mapping</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlSqlResultSetMapping#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlSqlResultSetMapping#getEntityResults <em>Entity Results</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlSqlResultSetMapping#getColumnResults <em>Column Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSqlResultSetMapping()
 * @model kind="class"
 * @generated
 */
public class XmlSqlResultSetMapping extends EBaseObjectImpl implements XmlSqlResultSetMapping_2_0, XmlSqlResultSetMapping_2_1
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
	 * The cached value of the '{@link #getConstructorResults() <em>Constructor Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstructorResults()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstructorResult> constructorResults;

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
	 * The cached value of the '{@link #getEntityResults() <em>Entity Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityResults()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityResult> entityResults;

	/**
	 * The cached value of the '{@link #getColumnResults() <em>Column Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnResults()
	 * @generated
	 * @ordered
	 */
	protected EList<ColumnResult> columnResults;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlSqlResultSetMapping()
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
		return OrmPackage.Literals.XML_SQL_RESULT_SET_MAPPING;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSqlResultSetMapping_2_0_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlSqlResultSetMapping#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_SQL_RESULT_SET_MAPPING__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Constructor Results</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.ConstructorResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constructor Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constructor Results</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSqlResultSetMapping_2_1_ConstructorResults()
	 * @model containment="true"
	 * @generated
	 */
	public EList<ConstructorResult> getConstructorResults()
	{
		if (constructorResults == null)
		{
			constructorResults = new EObjectContainmentEList<ConstructorResult>(ConstructorResult.class, this, OrmPackage.XML_SQL_RESULT_SET_MAPPING__CONSTRUCTOR_RESULTS);
		}
		return constructorResults;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSqlResultSetMapping_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlSqlResultSetMapping#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_SQL_RESULT_SET_MAPPING__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Entity Results</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.EntityResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Results</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSqlResultSetMapping_EntityResults()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EntityResult> getEntityResults()
	{
		if (entityResults == null)
		{
			entityResults = new EObjectContainmentEList<EntityResult>(EntityResult.class, this, OrmPackage.XML_SQL_RESULT_SET_MAPPING__ENTITY_RESULTS);
		}
		return entityResults;
	}

	/**
	 * Returns the value of the '<em><b>Column Results</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.ColumnResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Results</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSqlResultSetMapping_ColumnResults()
	 * @model containment="true"
	 * @generated
	 */
	public EList<ColumnResult> getColumnResults()
	{
		if (columnResults == null)
		{
			columnResults = new EObjectContainmentEList<ColumnResult>(ColumnResult.class, this, OrmPackage.XML_SQL_RESULT_SET_MAPPING__COLUMN_RESULTS);
		}
		return columnResults;
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
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__CONSTRUCTOR_RESULTS:
				return ((InternalEList<?>)getConstructorResults()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				return ((InternalEList<?>)getEntityResults()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				return ((InternalEList<?>)getColumnResults()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__DESCRIPTION:
				return getDescription();
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__CONSTRUCTOR_RESULTS:
				return getConstructorResults();
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__NAME:
				return getName();
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				return getEntityResults();
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				return getColumnResults();
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
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__CONSTRUCTOR_RESULTS:
				getConstructorResults().clear();
				getConstructorResults().addAll((Collection<? extends ConstructorResult>)newValue);
				return;
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				getEntityResults().clear();
				getEntityResults().addAll((Collection<? extends EntityResult>)newValue);
				return;
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				getColumnResults().clear();
				getColumnResults().addAll((Collection<? extends ColumnResult>)newValue);
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
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__CONSTRUCTOR_RESULTS:
				getConstructorResults().clear();
				return;
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				getEntityResults().clear();
				return;
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				getColumnResults().clear();
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
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__CONSTRUCTOR_RESULTS:
				return constructorResults != null && !constructorResults.isEmpty();
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__ENTITY_RESULTS:
				return entityResults != null && !entityResults.isEmpty();
			case OrmPackage.XML_SQL_RESULT_SET_MAPPING__COLUMN_RESULTS:
				return columnResults != null && !columnResults.isEmpty();
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
		if (baseClass == XmlSqlResultSetMapping_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_SQL_RESULT_SET_MAPPING__CONSTRUCTOR_RESULTS: return OrmV2_1Package.XML_SQL_RESULT_SET_MAPPING_21__CONSTRUCTOR_RESULTS;
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
		if (baseClass == XmlSqlResultSetMapping_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_SQL_RESULT_SET_MAPPING_21__CONSTRUCTOR_RESULTS: return OrmPackage.XML_SQL_RESULT_SET_MAPPING__CONSTRUCTOR_RESULTS;
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
		result.append(" (description: ");
		result.append(description);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildDescriptionTranslator(),
			buildEntityResultTranslator(),
			buildConstructorResultTranslator(),
			buildColumnResultTranslator()
		};
	}
	
	protected static Translator buildNameTranslator() {
		return new Translator(JPA.NAME, OrmPackage.eINSTANCE.getXmlSqlResultSetMapping_Name(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildDescriptionTranslator() {
		return new Translator(JPA2_0.DESCRIPTION, OrmV2_0Package.eINSTANCE.getXmlSqlResultSetMapping_2_0_Description());
	}
	
	protected static Translator buildEntityResultTranslator() {
		return EntityResult.buildTranslator(JPA.ENTITY_RESULT, OrmPackage.eINSTANCE.getXmlSqlResultSetMapping_EntityResults());
	}
	
	protected static Translator buildConstructorResultTranslator() {
		return ConstructorResult.buildTranslator(JPA2_1.CONSTRUCTOR_RESULT, OrmV2_1Package.eINSTANCE.getXmlSqlResultSetMapping_2_1_ConstructorResults());
	}

	protected static Translator buildColumnResultTranslator() {
		return ColumnResult.buildTranslator(JPA.COLUMN_RESULT, OrmPackage.eINSTANCE.getXmlSqlResultSetMapping_ColumnResults());
	}
}
