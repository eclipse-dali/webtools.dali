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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.IUniqueConstraint;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

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

	/**
	 * The cached value of the '{@link #getUniqueConstraints() <em>Unique Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IUniqueConstraint> uniqueConstraints;

	private final Owner owner;

	private final Member member;

	// hold this so we can get the annotation's text range
	private final DeclarationAnnotationAdapter daa;

	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	// hold this so we can get the 'schema' text range
	private final DeclarationAnnotationElementAdapter<String> schemaDeclarationAdapter;

	// hold this so we can get the 'catalog' text range
	private final DeclarationAnnotationElementAdapter<String> catalogDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> schemaAdapter;

	private final AnnotationElementAdapter<String> catalogAdapter;

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
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.member, this.nameDeclarationAdapter);
		this.schemaAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.member, this.schemaDeclarationAdapter);
		this.catalogAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.member, this.catalogDeclarationAdapter);
	}

	/**
	 * Build and return a declaration element adapter for the table's 'name' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	/**
	 * Build and return a declaration element adapter for the table's 'schema' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> schemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	/**
	 * Build and return a declaration element adapter for the table's 'catalog' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> catalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter);

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(ITable.class)) {
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME :
				this.nameAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA :
				this.schemaAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG :
				this.catalogAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS :
				uniqueConstraintsChanged(notification);
				break;
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void uniqueConstraintsChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				uniqueConstraintAdded(notification.getPosition(), (IUniqueConstraint) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				uniqueConstraintsAdded(notification.getPosition(), (List<IUniqueConstraint>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				uniqueConstraintRemoved(notification.getPosition(), (IUniqueConstraint) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					uniqueConstraintsCleared((List<IUniqueConstraint>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					uniqueConstraintsRemoved((int[]) notification.getNewValue(), (List<IUniqueConstraint>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					uniqueConstraintSet(notification.getPosition(), (IUniqueConstraint) notification.getOldValue(), (IUniqueConstraint) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				uniqueConstraintMoved(notification.getOldIntValue(), notification.getPosition(), (IUniqueConstraint) notification.getNewValue());
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
	 * Returns the value of the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IUniqueConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Constraints</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getITable_UniqueConstraints()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IUniqueConstraint> getUniqueConstraints() {
		if (uniqueConstraints == null) {
			uniqueConstraints = new EObjectContainmentEList<IUniqueConstraint>(IUniqueConstraint.class, this, JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS);
		}
		return uniqueConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS :
				return ((InternalEList<?>) getUniqueConstraints()).basicRemove(otherEnd, msgs);
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS :
				return getUniqueConstraints();
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_CATALOG :
				setSpecifiedCatalog((String) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__SPECIFIED_SCHEMA :
				setSpecifiedSchema((String) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS :
				getUniqueConstraints().clear();
				getUniqueConstraints().addAll((Collection<? extends IUniqueConstraint>) newValue);
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS :
				getUniqueConstraints().clear();
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS :
				return uniqueConstraints != null && !uniqueConstraints.isEmpty();
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
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS :
					return JpaCoreMappingsPackage.ITABLE__UNIQUE_CONSTRAINTS;
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
				case JpaCoreMappingsPackage.ITABLE__UNIQUE_CONSTRAINTS :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE__UNIQUE_CONSTRAINTS;
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
	public ITextRange nameTextRange() {
		return this.elementTextRange(this.nameDeclarationAdapter);
	}

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}

	public ITextRange schemaTextRange() {
		return this.elementTextRange(this.schemaDeclarationAdapter);
	}

	public ITextRange schemaTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.schemaDeclarationAdapter, astRoot);
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.schemaDeclarationAdapter, pos, astRoot);
	}

	public ITextRange catalogTextRange() {
		return this.elementTextRange(this.catalogDeclarationAdapter);
	}

	public ITextRange catalogTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.catalogDeclarationAdapter, astRoot);
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.catalogDeclarationAdapter, pos, astRoot);
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

	protected DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return this.daa;
	}

	public IUniqueConstraint createUniqueConstraint(int index) {
		return createJavaUniqueConstraint(index);
	}

	protected abstract JavaUniqueConstraint createJavaUniqueConstraint(int index);

	//set these defaults here or call setDefaultCatalog from JavaTableContext instead
	public void refreshDefaults(DefaultsContext defaultsContext) {
		this.setDefaultCatalog((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY));
		this.setDefaultSchema((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY));
	}

	protected void updateFromJava(CompilationUnit astRoot) {
		this.setSpecifiedName(this.nameAdapter.getValue(astRoot));
		this.setSpecifiedSchema(this.schemaAdapter.getValue(astRoot));
		this.setSpecifiedCatalog(this.catalogAdapter.getValue(astRoot));
		this.updateUniqueConstraintsFromJava(astRoot);
	}

	/**
	 * here we just worry about getting the unique constraints lists the same size;
	 * then we delegate to the unique constraints to synch themselves up
	 */
	private void updateUniqueConstraintsFromJava(CompilationUnit astRoot) {
		// synchronize the model join columns with the Java source
		List<IUniqueConstraint> constraints = this.getUniqueConstraints();
		int persSize = constraints.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaUniqueConstraint uniqueConstraint = (JavaUniqueConstraint) constraints.get(i);
			if (uniqueConstraint.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			uniqueConstraint.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model join columns beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				constraints.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaUniqueConstraint uniqueConstraint = this.createJavaUniqueConstraint(javaSize);
				if (uniqueConstraint.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					this.getUniqueConstraints().add(uniqueConstraint);
					uniqueConstraint.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	public ITextRange validationTextRange() {
		ITextRange textRange = this.member.annotationTextRange(this.daa);
		return (textRange != null) ? textRange : this.getOwner().validationTextRange();
	}

	public Table dbTable() {
		Schema schema = this.dbSchema();
		return (schema == null) ? null : schema.tableNamed(this.getName());
	}

	public Schema dbSchema() {
		return this.database().schemaNamed(this.getSchema());
	}

	public boolean hasResolvedSchema() {
		return this.dbSchema() != null;
	}

	public boolean isResolved() {
		return this.dbTable() != null;
	}

	protected ITextRange elementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter) {
		return this.elementTextRange(this.member.annotationElementTextRange(elementAdapter));
	}

	protected ITextRange elementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, CompilationUnit astRoot) {
		return this.elementTextRange(this.member.annotationElementTextRange(elementAdapter, astRoot));
	}

	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos) {
		return this.elementTouches(this.member.annotationElementTextRange(elementAdapter), pos);
	}

	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.member.annotationElementTextRange(elementAdapter, astRoot), pos);
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (IUniqueConstraint constraint : this.getUniqueConstraints()) {
			result = ((JavaUniqueConstraint) constraint).candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * called if the database is connected
	 * name, schema, catalog
	 */
	@Override
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.quotedCandidateNames(filter);
		}
		if (this.schemaTouches(pos, astRoot)) {
			return this.quotedCandidateSchemas(filter);
		}
		if (this.catalogTouches(pos, astRoot)) {
			return this.quotedCandidateCatalogs(filter);
		}
		return null;
	}

	private Iterator<String> candidateNames() {
		Schema dbSchema = this.dbSchema();
		return (dbSchema != null) ? dbSchema.tableNames() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateNames(), filter);
	}

	private Iterator<String> quotedCandidateNames(Filter<String> filter) {
		return StringTools.quote(this.candidateNames(filter));
	}

	private Iterator<String> candidateSchemas() {
		return this.database().schemaNames();
	}

	private Iterator<String> candidateSchemas(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateSchemas(), filter);
	}

	private Iterator<String> quotedCandidateSchemas(Filter<String> filter) {
		return StringTools.quote(this.candidateSchemas(filter));
	}

	private Iterator<String> candidateCatalogs() {
		return this.database().catalogNames();
	}

	private Iterator<String> candidateCatalogs(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateCatalogs(), filter);
	}

	private Iterator<String> quotedCandidateCatalogs(Filter<String> filter) {
		return StringTools.quote(this.candidateCatalogs(filter));
	}

	// ********** jpa model -> java annotations **********
	////////////////////////////////////////////////////////
	/**
	 * slide over all the annotations that follow the new join column
	 */
	public void uniqueConstraintAdded(int index, IUniqueConstraint uniqueConstraint) {
		// JoinColumn was added to jpa model when updating from java, do not need
		// to edit the java in this case. TODO is there a better way to handle this??
		if (((JavaUniqueConstraint) uniqueConstraint).annotation(getMember().astRoot()) == null) {
			this.synchUniqueConstraintAnnotationsAfterAdd(index + 1);
			((JavaUniqueConstraint) uniqueConstraint).newAnnotation();
		}
	}

	// bjv look at this
	public void uniqueConstraintsAdded(int index, List<IUniqueConstraint> constraints) {
		// JoinColumn was added to jpa model when updating from java, do not need
		// to edit the java in this case. TODO is there a better way to handle this??
		if (!constraints.isEmpty() && ((JavaUniqueConstraint) constraints.get(0)).annotation(getMember().astRoot()) == null) {
			this.synchUniqueConstraintAnnotationsAfterAdd(index + constraints.size());
			for (IUniqueConstraint uniqueConstraint : constraints) {
				((JavaUniqueConstraint) uniqueConstraint).newAnnotation();
			}
		}
	}

	public void uniqueConstraintRemoved(int index, IUniqueConstraint uniqueConstraint) {
		((JavaUniqueConstraint) uniqueConstraint).removeAnnotation();
		this.synchUniqueConstraintAnnotationsAfterRemove(index);
	}

	public void uniqueConstraintsRemoved(int[] indexes, List<IUniqueConstraint> constraints) {
		for (IUniqueConstraint uniqueConstraint : constraints) {
			((JavaUniqueConstraint) uniqueConstraint).removeAnnotation();
		}
		this.synchUniqueConstraintAnnotationsAfterRemove(indexes[0]);
	}

	public void uniqueConstraintsCleared(List<IUniqueConstraint> constraints) {
		for (IUniqueConstraint uniqueConstraint : constraints) {
			((JavaUniqueConstraint) uniqueConstraint).removeAnnotation();
		}
	}

	public void uniqueConstraintSet(int index, IUniqueConstraint oldUniqueConstraint, IUniqueConstraint newUniqueConstraint) {
		((JavaUniqueConstraint) newUniqueConstraint).newAnnotation();
	}

	public void uniqueConstraintMoved(int sourceIndex, int targetIndex, IUniqueConstraint uniqueConstraint) {
		List<IUniqueConstraint> constraints = this.getUniqueConstraints();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch(constraints.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterAdd(int index) {
		List<IUniqueConstraint> constraints = this.getUniqueConstraints();
		for (int i = constraints.size(); i-- > index;) {
			this.synch(constraints.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterRemove(int index) {
		List<IUniqueConstraint> joinColumns = this.getUniqueConstraints();
		for (int i = index; i < joinColumns.size(); i++) {
			this.synch(joinColumns.get(i), i);
		}
	}

	private void synch(IUniqueConstraint uniqueConstraint, int index) {
		((JavaUniqueConstraint) uniqueConstraint).moveAnnotation(index);
	}
}
