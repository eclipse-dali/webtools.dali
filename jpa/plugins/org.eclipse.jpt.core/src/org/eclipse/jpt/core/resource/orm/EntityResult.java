/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

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
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Result</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityResult#getDiscriminatorColumn <em>Discriminator Column</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityResult#getEntityClass <em>Entity Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityResult#getFieldResults <em>Field Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityResult()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class EntityResult extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The default value of the '{@link #getDiscriminatorColumn() <em>Discriminator Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorColumn()
	 * @generated
	 * @ordered
	 */
	protected static final String DISCRIMINATOR_COLUMN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiscriminatorColumn() <em>Discriminator Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorColumn()
	 * @generated
	 * @ordered
	 */
	protected String discriminatorColumn = DISCRIMINATOR_COLUMN_EDEFAULT;

	/**
	 * The default value of the '{@link #getEntityClass() <em>Entity Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityClass()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTITY_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntityClass() <em>Entity Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityClass()
	 * @generated
	 * @ordered
	 */
	protected String entityClass = ENTITY_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFieldResults() <em>Field Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldResults()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldResult> fieldResults;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityResult()
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
		return OrmPackage.Literals.ENTITY_RESULT;
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Column</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Column</em>' attribute.
	 * @see #setDiscriminatorColumn(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityResult_DiscriminatorColumn()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDiscriminatorColumn()
	{
		return discriminatorColumn;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityResult#getDiscriminatorColumn <em>Discriminator Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Column</em>' attribute.
	 * @see #getDiscriminatorColumn()
	 * @generated
	 */
	public void setDiscriminatorColumn(String newDiscriminatorColumn)
	{
		String oldDiscriminatorColumn = discriminatorColumn;
		discriminatorColumn = newDiscriminatorColumn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_RESULT__DISCRIMINATOR_COLUMN, oldDiscriminatorColumn, discriminatorColumn));
	}

	/**
	 * Returns the value of the '<em><b>Entity Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Class</em>' attribute.
	 * @see #setEntityClass(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityResult_EntityClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getEntityClass()
	{
		return entityClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityResult#getEntityClass <em>Entity Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Class</em>' attribute.
	 * @see #getEntityClass()
	 * @generated
	 */
	public void setEntityClass(String newEntityClass)
	{
		String oldEntityClass = entityClass;
		entityClass = newEntityClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_RESULT__ENTITY_CLASS, oldEntityClass, entityClass));
	}

	/**
	 * Returns the value of the '<em><b>Field Results</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.FieldResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Field Results</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityResult_FieldResults()
	 * @model containment="true"
	 * @generated
	 */
	public EList<FieldResult> getFieldResults()
	{
		if (fieldResults == null)
		{
			fieldResults = new EObjectContainmentEList<FieldResult>(FieldResult.class, this, OrmPackage.ENTITY_RESULT__FIELD_RESULTS);
		}
		return fieldResults;
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
			case OrmPackage.ENTITY_RESULT__FIELD_RESULTS:
				return ((InternalEList<?>)getFieldResults()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.ENTITY_RESULT__DISCRIMINATOR_COLUMN:
				return getDiscriminatorColumn();
			case OrmPackage.ENTITY_RESULT__ENTITY_CLASS:
				return getEntityClass();
			case OrmPackage.ENTITY_RESULT__FIELD_RESULTS:
				return getFieldResults();
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
			case OrmPackage.ENTITY_RESULT__DISCRIMINATOR_COLUMN:
				setDiscriminatorColumn((String)newValue);
				return;
			case OrmPackage.ENTITY_RESULT__ENTITY_CLASS:
				setEntityClass((String)newValue);
				return;
			case OrmPackage.ENTITY_RESULT__FIELD_RESULTS:
				getFieldResults().clear();
				getFieldResults().addAll((Collection<? extends FieldResult>)newValue);
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
			case OrmPackage.ENTITY_RESULT__DISCRIMINATOR_COLUMN:
				setDiscriminatorColumn(DISCRIMINATOR_COLUMN_EDEFAULT);
				return;
			case OrmPackage.ENTITY_RESULT__ENTITY_CLASS:
				setEntityClass(ENTITY_CLASS_EDEFAULT);
				return;
			case OrmPackage.ENTITY_RESULT__FIELD_RESULTS:
				getFieldResults().clear();
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
			case OrmPackage.ENTITY_RESULT__DISCRIMINATOR_COLUMN:
				return DISCRIMINATOR_COLUMN_EDEFAULT == null ? discriminatorColumn != null : !DISCRIMINATOR_COLUMN_EDEFAULT.equals(discriminatorColumn);
			case OrmPackage.ENTITY_RESULT__ENTITY_CLASS:
				return ENTITY_CLASS_EDEFAULT == null ? entityClass != null : !ENTITY_CLASS_EDEFAULT.equals(entityClass);
			case OrmPackage.ENTITY_RESULT__FIELD_RESULTS:
				return fieldResults != null && !fieldResults.isEmpty();
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
		result.append(" (discriminatorColumn: ");
		result.append(discriminatorColumn);
		result.append(", entityClass: ");
		result.append(entityClass);
		result.append(')');
		return result.toString();
	}

	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildEntityClassTranslator(),
			buildDiscriminatorColumnTranslator(),
			FieldResult.buildTranslator(JPA.FIELD_RESULT, OrmPackage.eINSTANCE.getEntityResult_FieldResults()),
		};
	}
	
	protected static Translator buildEntityClassTranslator() {
		return new Translator(JPA.ENTITY_CLASS, OrmPackage.eINSTANCE.getEntityResult_EntityClass(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildDiscriminatorColumnTranslator() {
		return new Translator(JPA.DISCRIMINATOR_COLUMN, OrmPackage.eINSTANCE.getEntityResult_DiscriminatorColumn(), Translator.DOM_ATTRIBUTE);
	}
} // EntityResult
