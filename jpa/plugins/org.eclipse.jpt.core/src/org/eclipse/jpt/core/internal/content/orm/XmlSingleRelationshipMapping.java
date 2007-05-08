/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Single Relationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSingleRelationshipMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class XmlSingleRelationshipMapping
	extends XmlRelationshipMapping implements ISingleRelationshipMapping
{
	/**
	 * The default value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultEagerFetchType FETCH_EDEFAULT = DefaultEagerFetchType.DEFAULT;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected DefaultEagerFetchType fetch = FETCH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSpecifiedJoinColumns() <em>Specified Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> specifiedJoinColumns;

	/**
	 * The cached value of the '{@link #getDefaultJoinColumns() <em>Default Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> defaultJoinColumns;

	/**
	 * The default value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean OPTIONAL_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected DefaultTrueBoolean optional = OPTIONAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected XmlSingleRelationshipMapping() {
		super();
		this.getDefaultJoinColumns().add(this.createJoinColumn(new JoinColumnOwner(this)));
		this.eAdapters().add(this.buildListener());
	}

	private IJoinColumn createJoinColumn(IJoinColumn.Owner owner) {
		return OrmFactory.eINSTANCE.createXmlJoinColumn(owner);
	}

	private Adapter buildListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				XmlSingleRelationshipMapping.this.notifyChanged(notification);
			}
		};
	}

	/**
	 * check for changes to the 'specifiedJoinColumns' and
	 * 'specifiedInverseJoinColumns' lists so we can notify the
	 * model adapter of any changes;
	 * also listen for changes to the 'defaultJoinColumns' and
	 * 'defaultInverseJoinColumns' lists so we can spank the developer
	 */
	void notifyChanged(Notification notification) {
		switch (notification.getFeatureID(ISingleRelationshipMapping.class)) {
			case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				defaultJoinColumnsChanged(notification);
				break;
			default :
				break;
		}
	}

	void defaultJoinColumnsChanged(Notification notification) {
		throw new IllegalStateException("'defaultJoinColumns' cannot be changed");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_SINGLE_RELATIONSHIP_MAPPING;
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #setFetch(DefaultEagerFetchType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISingleRelationshipMapping_Fetch()
	 * @model
	 * @generated
	 */
	public DefaultEagerFetchType getFetch() {
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(DefaultEagerFetchType newFetch) {
		DefaultEagerFetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__FETCH, oldFetch, fetch));
	}

	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISingleRelationshipMapping_JoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public EList<IJoinColumn> getJoinColumns() {
		return this.getSpecifiedJoinColumns().isEmpty() ? this.getDefaultJoinColumns() : this.getSpecifiedJoinColumns();
	}

	/**
	 * Returns the value of the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISingleRelationshipMapping_SpecifiedJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getSpecifiedJoinColumns() {
		if (specifiedJoinColumns == null) {
			specifiedJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS);
		}
		return specifiedJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISingleRelationshipMapping_DefaultJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getDefaultJoinColumns() {
		if (defaultJoinColumns == null) {
			defaultJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS);
		}
		return defaultJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setOptional(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISingleRelationshipMapping_Optional()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getOptional() {
		return optional;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping#getOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getOptional()
	 * @generated
	 */
	public void setOptional(DefaultTrueBoolean newOptional) {
		DefaultTrueBoolean oldOptional = optional;
		optional = newOptional == null ? OPTIONAL_EDEFAULT : newOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL, oldOptional, optional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
				return ((InternalEList<?>) getJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultJoinColumns()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__FETCH :
				return getFetch();
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
				return getJoinColumns();
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				return getSpecifiedJoinColumns();
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				return getDefaultJoinColumns();
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				return getOptional();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__FETCH :
				setFetch((DefaultEagerFetchType) newValue);
				return;
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				getSpecifiedJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				getDefaultJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				setOptional((DefaultTrueBoolean) newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__FETCH :
				setFetch(FETCH_EDEFAULT);
				return;
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				return;
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				return;
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				setOptional(OPTIONAL_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__FETCH :
				return fetch != FETCH_EDEFAULT;
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
				return !getJoinColumns().isEmpty();
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				return specifiedJoinColumns != null && !specifiedJoinColumns.isEmpty();
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				return defaultJoinColumns != null && !defaultJoinColumns.isEmpty();
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				return optional != OPTIONAL_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ISingleRelationshipMapping.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__FETCH :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__FETCH;
				case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;
				case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;
				case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;
				case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__OPTIONAL;
				default :
					return -1;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ISingleRelationshipMapping.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__FETCH :
					return OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__FETCH;
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
					return OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
					return OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
					return OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
					return OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;
				default :
					return -1;
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
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (fetch: ");
		result.append(fetch);
		result.append(", optional: ");
		result.append(optional);
		result.append(')');
		return result.toString();
	}

	@Override
	public void initializeFromXmlSingleRelationshipMapping(XmlSingleRelationshipMapping oldMapping) {
		super.initializeFromXmlSingleRelationshipMapping(oldMapping);
		setFetch(oldMapping.getFetch());
	}

	public IJoinColumn createJoinColumn(int index) {
		return OrmFactory.eINSTANCE.createXmlJoinColumn(new JoinColumnOwner(this));
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.getSpecifiedJoinColumns().isEmpty();
	}
} // XmlSingleRelationshipMapping
