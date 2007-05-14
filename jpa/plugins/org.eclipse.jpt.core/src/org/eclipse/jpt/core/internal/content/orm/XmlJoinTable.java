/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Collection;
import java.util.Set;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Join Table</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlJoinTable()
 * @model kind="class"
 * @generated
 */
public class XmlJoinTable extends AbstractXmlTable implements IJoinTable
{
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
	 * The cached value of the '{@link #getSpecifiedInverseJoinColumns() <em>Specified Inverse Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedInverseJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> specifiedInverseJoinColumns;

	/**
	 * The cached value of the '{@link #getDefaultInverseJoinColumns() <em>Default Inverse Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultInverseJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> defaultInverseJoinColumns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected XmlJoinTable() {
		throw new UnsupportedOperationException();
	}

	protected XmlJoinTable(IJoinTable.Owner owner) {
		super(owner);
		this.getDefaultJoinColumns().add(this.createJoinColumn(new JoinColumnOwner(this)));
		this.getDefaultInverseJoinColumns().add(this.createJoinColumn(new InverseJoinColumnOwner(this)));
		this.eAdapters().add(this.buildListener());
	}

	@Override
	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_JOIN_COLUMNS);
		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS);
		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IJOIN_TABLE__JOIN_COLUMNS);
		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IJOIN_TABLE__INVERSE_JOIN_COLUMNS);
	}

	private IJoinColumn createJoinColumn(IJoinColumn.Owner owner) {
		return OrmFactory.eINSTANCE.createXmlJoinColumn(owner);
	}

	private Adapter buildListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				XmlJoinTable.this.notifyChanged(notification);
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
		switch (notification.getFeatureID(IJoinTable.class)) {
			case JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				defaultJoinColumnsChanged(notification);
				break;
			case JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				defaultInverseJoinColumnsChanged(notification);
				break;
			default :
				break;
		}
	}

	void defaultJoinColumnsChanged(Notification notification) {
		throw new IllegalStateException("'defaultJoinColumns' cannot be changed");
	}

	void defaultInverseJoinColumnsChanged(Notification notification) {
		throw new IllegalStateException("'defaultInverseJoinColumns' cannot be changed");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_JOIN_TABLE;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIJoinTable_JoinColumns()
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIJoinTable_SpecifiedJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getSpecifiedJoinColumns() {
		if (specifiedJoinColumns == null) {
			specifiedJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS);
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIJoinTable_DefaultJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getDefaultJoinColumns() {
		if (defaultJoinColumns == null) {
			defaultJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS);
		}
		return defaultJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIJoinTable_InverseJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public EList<IJoinColumn> getInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumns().isEmpty() ? this.getDefaultInverseJoinColumns() : this.getSpecifiedInverseJoinColumns();
	}

	/**
	 * Returns the value of the '<em><b>Specified Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIJoinTable_SpecifiedInverseJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getSpecifiedInverseJoinColumns() {
		if (specifiedInverseJoinColumns == null) {
			specifiedInverseJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS);
		}
		return specifiedInverseJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Default Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIJoinTable_DefaultInverseJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getDefaultInverseJoinColumns() {
		if (defaultInverseJoinColumns == null) {
			defaultInverseJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS);
		}
		return defaultInverseJoinColumns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS :
				return ((InternalEList<?>) getJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS :
				return ((InternalEList<?>) getInverseJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedInverseJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultInverseJoinColumns()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS :
				return getJoinColumns();
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				return getSpecifiedJoinColumns();
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				return getDefaultJoinColumns();
			case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS :
				return getInverseJoinColumns();
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				return getSpecifiedInverseJoinColumns();
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				return getDefaultInverseJoinColumns();
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
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				getSpecifiedJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				getDefaultJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				getSpecifiedInverseJoinColumns().clear();
				getSpecifiedInverseJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				getDefaultInverseJoinColumns().clear();
				getDefaultInverseJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
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
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				return;
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				return;
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				getSpecifiedInverseJoinColumns().clear();
				return;
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				getDefaultInverseJoinColumns().clear();
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
			case OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS :
				return !getJoinColumns().isEmpty();
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				return specifiedJoinColumns != null && !specifiedJoinColumns.isEmpty();
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				return defaultJoinColumns != null && !defaultJoinColumns.isEmpty();
			case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS :
				return !getInverseJoinColumns().isEmpty();
			case OrmPackage.XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				return specifiedInverseJoinColumns != null && !specifiedInverseJoinColumns.isEmpty();
			case OrmPackage.XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				return defaultInverseJoinColumns != null && !defaultInverseJoinColumns.isEmpty();
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
		if (baseClass == IJoinTable.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__JOIN_COLUMNS;
				case OrmPackage.XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS;
				case OrmPackage.XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_JOIN_COLUMNS;
				case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__INVERSE_JOIN_COLUMNS;
				case OrmPackage.XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS;
				case OrmPackage.XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS;
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
		if (baseClass == IJoinTable.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IJOIN_TABLE__JOIN_COLUMNS :
					return OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
					return OrmPackage.XML_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_JOIN_COLUMNS :
					return OrmPackage.XML_JOIN_TABLE__DEFAULT_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__INVERSE_JOIN_COLUMNS :
					return OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
					return OrmPackage.XML_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
					return OrmPackage.XML_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	private XmlMultiRelationshipMappingInternal getMultiRelationshipMapping() {
		return (XmlMultiRelationshipMappingInternal) eContainer();
	}

	@Override
	protected void makeTableForXmlNonNull() {
		getMultiRelationshipMapping().makeJoinTableForXmlNonNull();
	}

	@Override
	protected void makeTableForXmlNull() {
		getMultiRelationshipMapping().makeJoinTableForXmlNull();
	}

	// ********** IJoinTable implementation **********
	public IJoinColumn createJoinColumn(int index) {
		return this.createXmlJoinColumn(index);
	}

	private XmlJoinColumn createXmlJoinColumn(int index) {
		return OrmFactory.eINSTANCE.createXmlJoinColumn(new JoinColumnOwner(this));
		//return XmlJoinColumn.createJoinTableJoinColumn(new JoinColumnOwner(), this.getMember(), index);
	}

	public IJoinColumn createInverseJoinColumn(int index) {
		return this.createXmlInverseJoinColumn(index);
	}

	private XmlJoinColumn createXmlInverseJoinColumn(int index) {
		return OrmFactory.eINSTANCE.createXmlJoinColumn(new InverseJoinColumnOwner(this));
		//return JavaJoinColumn.createJoinTableInverseJoinColumn(new InverseJoinColumnOwner(), this.getMember(), index);
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.getSpecifiedJoinColumns().isEmpty();
	}

	public boolean containsSpecifiedInverseJoinColumns() {
		return !this.getSpecifiedInverseJoinColumns().isEmpty();
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_TABLE_NAME_KEY));
	}

	public boolean isSpecified() {
		return node != null;
	}

	public IRelationshipMapping relationshipMapping() {
		return (IRelationshipMapping) this.eContainer();
	}
} // XmlJoinTable
