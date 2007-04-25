/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Java Table</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getAbstractJavaTable()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractJavaTable extends JavaEObject implements ITable
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
	 * The default value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedName = SPECIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected String defaultName = DEFAULT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getCatalog() <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String CATALOG_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedCatalog() <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_CATALOG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedCatalog() <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedCatalog()
	 * @generated
	 * @ordered
	 */
	protected String specifiedCatalog = SPECIFIED_CATALOG_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultCatalog() <em>Default Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_CATALOG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultCatalog() <em>Default Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultCatalog()
	 * @generated
	 * @ordered
	 */
	protected String defaultCatalog = DEFAULT_CATALOG_EDEFAULT;

	/**
	 * The default value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEMA_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedSchema() <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_SCHEMA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedSchema() <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSchema()
	 * @generated
	 * @ordered
	 */
	protected String specifiedSchema = SPECIFIED_SCHEMA_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultSchema() <em>Default Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_SCHEMA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultSchema() <em>Default Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSchema()
	 * @generated
	 * @ordered
	 */
	protected String defaultSchema = DEFAULT_SCHEMA_EDEFAULT;

	private final Owner owner;

	private final Member member;

	// hold this so we can get the annotation's text range
	private final DeclarationAnnotationAdapter daa;

	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter nameDeclarationAdapter;

	// hold this so we can get the 'schema' text range
	private final DeclarationAnnotationElementAdapter schemaDeclarationAdapter;

	// hold this so we can get the 'catalog' text range
	private final DeclarationAnnotationElementAdapter catalogDeclarationAdapter;

	private final AnnotationElementAdapter nameAdapter;

	private final AnnotationElementAdapter schemaAdapter;

	private final AnnotationElementAdapter catalogAdapter;

	protected AbstractJavaTable() {
		super();
		throw new UnsupportedOperationException("Use AbstractJavaTable(Owner, Member) instead");
	}

	protected AbstractJavaTable(Owner owner, Member member, DeclarationAnnotationAdapter daa) {
		super();
		this.owner = owner;
		this.member = member;
		this.daa = daa;
		this.nameDeclarationAdapter = this.nameAdapter(daa);
		this.schemaDeclarationAdapter = this.schemaAdapter(daa);
		this.catalogDeclarationAdapter = this.catalogAdapter(daa);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter(this.member, this.nameDeclarationAdapter);
		this.schemaAdapter = new ShortCircuitAnnotationElementAdapter(this.member, this.schemaDeclarationAdapter);
		this.catalogAdapter = new ShortCircuitAnnotationElementAdapter(this.member, this.catalogDeclarationAdapter);
	}

	/**
	 * Build and return a declaration element adapter for the table's 'name' element
	 */
	protected abstract DeclarationAnnotationElementAdapter nameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	/**
	 * Build and return a declaration element adapter for the table's 'schema' element
	 */
	protected abstract DeclarationAnnotationElementAdapter schemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	/**
	 * Build and return a declaration element adapter for the table's 'catalog' element
	 */
	protected abstract DeclarationAnnotationElementAdapter catalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(INamedColumn.class)) {
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME :
				this.nameAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA :
				this.schemaAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG :
				this.catalogAdapter.setValue(notification.getNewValue());
				break;
			default :
				break;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.ABSTRACT_JAVA_TABLE;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_Name()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getName() {
		return (this.getSpecifiedName() == null) ? getDefaultName() : this.getSpecifiedName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Name</em>' attribute.
	 * @see #setSpecifiedName(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = specifiedName;
		specifiedName = newSpecifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
	}

	/**
	 * Returns the value of the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * Returns the value of the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_Catalog()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	/**
	 * Returns the value of the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Catalog</em>' attribute.
	 * @see #setSpecifiedCatalog(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_SpecifiedCatalog()
	 * @model
	 * @generated
	 */
	public String getSpecifiedCatalog() {
		return specifiedCatalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable#getSpecifiedCatalog <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Catalog</em>' attribute.
	 * @see #getSpecifiedCatalog()
	 * @generated
	 */
	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = specifiedCatalog;
		specifiedCatalog = newSpecifiedCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG, oldSpecifiedCatalog, specifiedCatalog));
	}

	/**
	 * Returns the value of the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Catalog</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_DefaultCatalog()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultCatalog() {
		return defaultCatalog;
	}

	/**
	 * Returns the value of the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_Schema()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? getDefaultSchema() : this.getSpecifiedSchema();
	}

	/**
	 * Returns the value of the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Schema</em>' attribute.
	 * @see #setSpecifiedSchema(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_SpecifiedSchema()
	 * @model
	 * @generated
	 */
	public String getSpecifiedSchema() {
		return specifiedSchema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable#getSpecifiedSchema <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Schema</em>' attribute.
	 * @see #getSpecifiedSchema()
	 * @generated
	 */
	public void setSpecifiedSchema(String newSpecifiedSchema) {
		String oldSpecifiedSchema = specifiedSchema;
		specifiedSchema = newSpecifiedSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA, oldSpecifiedSchema, specifiedSchema));
	}

	/**
	 * Returns the value of the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Schema</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_DefaultSchema()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultSchema() {
		return defaultSchema;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__NAME :
				return getName();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME :
				return getSpecifiedName();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_NAME :
				return getDefaultName();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__CATALOG :
				return getCatalog();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG :
				return getSpecifiedCatalog();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_CATALOG :
				return getDefaultCatalog();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SCHEMA :
				return getSchema();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA :
				return getSpecifiedSchema();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_SCHEMA :
				return getDefaultSchema();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG :
				setSpecifiedCatalog((String) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA :
				setSpecifiedSchema((String) newValue);
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG :
				setSpecifiedCatalog(SPECIFIED_CATALOG_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA :
				setSpecifiedSchema(SPECIFIED_SCHEMA_EDEFAULT);
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__NAME :
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__CATALOG :
				return CATALOG_EDEFAULT == null ? getCatalog() != null : !CATALOG_EDEFAULT.equals(getCatalog());
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG :
				return SPECIFIED_CATALOG_EDEFAULT == null ? specifiedCatalog != null : !SPECIFIED_CATALOG_EDEFAULT.equals(specifiedCatalog);
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_CATALOG :
				return DEFAULT_CATALOG_EDEFAULT == null ? defaultCatalog != null : !DEFAULT_CATALOG_EDEFAULT.equals(defaultCatalog);
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SCHEMA :
				return SCHEMA_EDEFAULT == null ? getSchema() != null : !SCHEMA_EDEFAULT.equals(getSchema());
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA :
				return SPECIFIED_SCHEMA_EDEFAULT == null ? specifiedSchema != null : !SPECIFIED_SCHEMA_EDEFAULT.equals(specifiedSchema);
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_SCHEMA :
				return DEFAULT_SCHEMA_EDEFAULT == null ? defaultSchema != null : !DEFAULT_SCHEMA_EDEFAULT.equals(defaultSchema);
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
		if (baseClass == ITable.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__NAME :
					return JpaCoreMappingsPackage.ITABLE__NAME;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.ITABLE__SPECIFIED_NAME;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_NAME :
					return JpaCoreMappingsPackage.ITABLE__DEFAULT_NAME;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__CATALOG :
					return JpaCoreMappingsPackage.ITABLE__CATALOG;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG :
					return JpaCoreMappingsPackage.ITABLE__SPECIFIED_CATALOG;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_CATALOG :
					return JpaCoreMappingsPackage.ITABLE__DEFAULT_CATALOG;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SCHEMA :
					return JpaCoreMappingsPackage.ITABLE__SCHEMA;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA :
					return JpaCoreMappingsPackage.ITABLE__SPECIFIED_SCHEMA;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_SCHEMA :
					return JpaCoreMappingsPackage.ITABLE__DEFAULT_SCHEMA;
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
		if (baseClass == ITable.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ITABLE__NAME :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__NAME;
				case JpaCoreMappingsPackage.ITABLE__SPECIFIED_NAME :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.ITABLE__DEFAULT_NAME :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_NAME;
				case JpaCoreMappingsPackage.ITABLE__CATALOG :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__CATALOG;
				case JpaCoreMappingsPackage.ITABLE__SPECIFIED_CATALOG :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG;
				case JpaCoreMappingsPackage.ITABLE__DEFAULT_CATALOG :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_CATALOG;
				case JpaCoreMappingsPackage.ITABLE__SCHEMA :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SCHEMA;
				case JpaCoreMappingsPackage.ITABLE__SPECIFIED_SCHEMA :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA;
				case JpaCoreMappingsPackage.ITABLE__DEFAULT_SCHEMA :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__DEFAULT_SCHEMA;
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
		result.append(" (specifiedName: ");
		result.append(specifiedName);
		result.append(", defaultName: ");
		result.append(defaultName);
		result.append(", specifiedCatalog: ");
		result.append(specifiedCatalog);
		result.append(", defaultCatalog: ");
		result.append(defaultCatalog);
		result.append(", specifiedSchema: ");
		result.append(specifiedSchema);
		result.append(", defaultSchema: ");
		result.append(defaultSchema);
		result.append(')');
		return result.toString();
	}

	// ********** ITable implementation **********
	public ITextRange getNameTextRange() {
		return this.elementTextRange(this.nameDeclarationAdapter);
	}

	public ITextRange getSchemaTextRange() {
		return this.elementTextRange(this.schemaDeclarationAdapter);
	}

	public ITextRange getCatalogTextRange() {
		return this.elementTextRange(this.catalogDeclarationAdapter);
	}

	//TODO should we allow setting through the ecore, that would make this method
	//public and part of the ITable api.  only the model needs to be setting the default,
	//but the ui needs to be listening for changes to the default.
	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE__DEFAULT_NAME, oldDefaultName, this.defaultName));
	}

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE__DEFAULT_CATALOG, oldDefaultCatalog, this.defaultCatalog));
	}

	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_TABLE__DEFAULT_SCHEMA, oldDefaultSchema, this.defaultSchema));
	}

	public Owner getOwner() {
		return owner;
	}

	protected Member getMember() {
		return this.member;
	}

	//set these defaults here or call setDefaultCatalog from JavaTableContext instead
	public void refreshDefaults(DefaultsContext defaultsContext) {
		this.setDefaultCatalog((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY));
		this.setDefaultSchema((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY));
	}

	protected void updateFromJava(CompilationUnit astRoot) {
		this.setSpecifiedName((String) this.nameAdapter.getValue(astRoot));
		this.setSpecifiedSchema((String) this.schemaAdapter.getValue(astRoot));
		this.setSpecifiedCatalog((String) this.catalogAdapter.getValue(astRoot));
	}

	public ITextRange getTextRange() {
		ITextRange textRange = this.member.annotationTextRange(this.daa);
		return (textRange != null) ? textRange : this.getOwner().getTextRange();
	}

	public Table dbTable() {
		Schema schema = this.dbSchema();
		return (schema == null) ? null : schema.tableNamed(this.getName());
	}

	public Schema dbSchema() {
		return this.getJpaProject().connectionProfile().getDatabase().schemaNamed(this.getSchema());
	}

	public boolean isConnected() {
		ConnectionProfile connectionProfile = this.getJpaProject().connectionProfile();
		return connectionProfile != null && connectionProfile.isConnected();
	}

	public boolean hasResolvedSchema() {
		return this.dbSchema() != null;
	}

	public boolean isResolved() {
		return this.dbTable() != null;
	}

	protected ITextRange elementTextRange(DeclarationAnnotationElementAdapter elementAdapter) {
		return this.elementTextRange(this.member.annotationElementTextRange(elementAdapter));
	}
}
