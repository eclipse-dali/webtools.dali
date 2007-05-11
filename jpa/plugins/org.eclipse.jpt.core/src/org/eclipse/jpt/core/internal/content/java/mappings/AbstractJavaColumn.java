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

import java.util.Iterator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IAbstractColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Java Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getAbstractJavaColumn()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractJavaColumn extends JavaNamedColumn
	implements IAbstractColumn
{
	/**
	 * The default value of the '{@link #getUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnique()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultFalseBoolean UNIQUE_EDEFAULT = DefaultFalseBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnique()
	 * @generated
	 * @ordered
	 */
	protected DefaultFalseBoolean unique = UNIQUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNullable() <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullable()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean NULLABLE_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getNullable() <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullable()
	 * @generated
	 * @ordered
	 */
	protected DefaultTrueBoolean nullable = NULLABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInsertable() <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertable()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean INSERTABLE_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getInsertable() <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertable()
	 * @generated
	 * @ordered
	 */
	protected DefaultTrueBoolean insertable = INSERTABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpdatable() <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdatable()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean UPDATABLE_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getUpdatable() <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdatable()
	 * @generated
	 * @ordered
	 */
	protected DefaultTrueBoolean updatable = UPDATABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected static final String TABLE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedTable() <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTable()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_TABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedTable() <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTable()
	 * @generated
	 * @ordered
	 */
	protected String specifiedTable = SPECIFIED_TABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultTable() <em>Default Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultTable()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_TABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultTable() <em>Default Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultTable()
	 * @generated
	 * @ordered
	 */
	protected String defaultTable = DEFAULT_TABLE_EDEFAULT;

	// hold this so we can get the 'table' text range
	private final DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;

	private final AnnotationElementAdapter<String> tableAdapter;

	private final AnnotationElementAdapter<String> uniqueAdapter;

	private final AnnotationElementAdapter<String> nullableAdapter;

	private final AnnotationElementAdapter<String> insertableAdapter;

	private final AnnotationElementAdapter<String> updatableAdapter;

	protected AbstractJavaColumn() {
		super();
		throw new UnsupportedOperationException("Use AbstractJavaColumn(Owner, Member, DeclarationAnnotationAdapter) instead");
	}

	protected AbstractJavaColumn(Owner owner, Member member, DeclarationAnnotationAdapter daa) {
		super(owner, member, daa);
		this.tableDeclarationAdapter = this.buildStringElementAdapter(this.tableElementName());
		this.tableAdapter = this.buildShortCircuitElementAdapter(this.tableDeclarationAdapter);
		this.uniqueAdapter = this.buildShortCircuitBooleanElementAdapter(this.uniqueElementName());
		this.nullableAdapter = this.buildShortCircuitBooleanElementAdapter(this.nullableElementName());
		this.insertableAdapter = this.buildShortCircuitBooleanElementAdapter(this.insertableElementName());
		this.updatableAdapter = this.buildShortCircuitBooleanElementAdapter(this.updatableElementName());
	}

	protected abstract String tableElementName();

	protected abstract String uniqueElementName();

	protected abstract String nullableElementName();

	protected abstract String insertableElementName();

	protected abstract String updatableElementName();

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IAbstractColumn.class)) {
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE :
				this.tableAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UNIQUE :
				this.uniqueAdapter.setValue(((DefaultFalseBoolean) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__NULLABLE :
				this.nullableAdapter.setValue(((DefaultTrueBoolean) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__INSERTABLE :
				this.insertableAdapter.setValue(((DefaultTrueBoolean) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UPDATABLE :
				this.updatableAdapter.setValue(((DefaultTrueBoolean) notification.getNewValue()).convertToJavaAnnotationValue());
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
		return JpaJavaMappingsPackage.Literals.ABSTRACT_JAVA_COLUMN;
	}

	/**
	 * Returns the value of the '<em><b>Unique</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see #setUnique(DefaultFalseBoolean)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractColumn_Unique()
	 * @model
	 * @generated
	 */
	public DefaultFalseBoolean getUnique() {
		return unique;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn#getUnique <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see #getUnique()
	 * @generated
	 */
	public void setUnique(DefaultFalseBoolean newUnique) {
		DefaultFalseBoolean oldUnique = unique;
		unique = newUnique == null ? UNIQUE_EDEFAULT : newUnique;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UNIQUE, oldUnique, unique));
	}

	/**
	 * Returns the value of the '<em><b>Nullable</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nullable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nullable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setNullable(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractColumn_Nullable()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getNullable() {
		return nullable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn#getNullable <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nullable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getNullable()
	 * @generated
	 */
	public void setNullable(DefaultTrueBoolean newNullable) {
		DefaultTrueBoolean oldNullable = nullable;
		nullable = newNullable == null ? NULLABLE_EDEFAULT : newNullable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__NULLABLE, oldNullable, nullable));
	}

	/**
	 * Returns the value of the '<em><b>Insertable</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insertable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insertable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setInsertable(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractColumn_Insertable()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getInsertable() {
		return insertable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn#getInsertable <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insertable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getInsertable()
	 * @generated
	 */
	public void setInsertable(DefaultTrueBoolean newInsertable) {
		DefaultTrueBoolean oldInsertable = insertable;
		insertable = newInsertable == null ? INSERTABLE_EDEFAULT : newInsertable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__INSERTABLE, oldInsertable, insertable));
	}

	/**
	 * Returns the value of the '<em><b>Updatable</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Updatable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Updatable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setUpdatable(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractColumn_Updatable()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getUpdatable() {
		return updatable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn#getUpdatable <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updatable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getUpdatable()
	 * @generated
	 */
	public void setUpdatable(DefaultTrueBoolean newUpdatable) {
		DefaultTrueBoolean oldUpdatable = updatable;
		updatable = newUpdatable == null ? UPDATABLE_EDEFAULT : newUpdatable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UPDATABLE, oldUpdatable, updatable));
	}

	public String getTable() {
		return (this.getSpecifiedTable() == null) ? getDefaultTable() : this.getSpecifiedTable();
	}

	/**
	 * Returns the value of the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Table</em>' attribute.
	 * @see #setSpecifiedTable(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractColumn_SpecifiedTable()
	 * @model
	 * @generated
	 */
	public String getSpecifiedTable() {
		return specifiedTable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn#getSpecifiedTable <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Table</em>' attribute.
	 * @see #getSpecifiedTable()
	 * @generated
	 */
	public void setSpecifiedTable(String newSpecifiedTable) {
		String oldSpecifiedTable = specifiedTable;
		specifiedTable = newSpecifiedTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE, oldSpecifiedTable, specifiedTable));
	}

	protected void setDefaultTable(String newDefaultTable) {
		String oldDefaultTable = this.defaultTable;
		this.defaultTable = newDefaultTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__DEFAULT_TABLE, oldDefaultTable, newDefaultTable));
	}

	/**
	 * Returns the value of the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Table</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractColumn_DefaultTable()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultTable() {
		return defaultTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UNIQUE :
				return getUnique();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__NULLABLE :
				return getNullable();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__INSERTABLE :
				return getInsertable();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UPDATABLE :
				return getUpdatable();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__TABLE :
				return getTable();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE :
				return getSpecifiedTable();
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__DEFAULT_TABLE :
				return getDefaultTable();
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UNIQUE :
				setUnique((DefaultFalseBoolean) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__NULLABLE :
				setNullable((DefaultTrueBoolean) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__INSERTABLE :
				setInsertable((DefaultTrueBoolean) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UPDATABLE :
				setUpdatable((DefaultTrueBoolean) newValue);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE :
				setSpecifiedTable((String) newValue);
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UNIQUE :
				setUnique(UNIQUE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__NULLABLE :
				setNullable(NULLABLE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__INSERTABLE :
				setInsertable(INSERTABLE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UPDATABLE :
				setUpdatable(UPDATABLE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE :
				setSpecifiedTable(SPECIFIED_TABLE_EDEFAULT);
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
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UNIQUE :
				return unique != UNIQUE_EDEFAULT;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__NULLABLE :
				return nullable != NULLABLE_EDEFAULT;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__INSERTABLE :
				return insertable != INSERTABLE_EDEFAULT;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UPDATABLE :
				return updatable != UPDATABLE_EDEFAULT;
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__TABLE :
				return TABLE_EDEFAULT == null ? getTable() != null : !TABLE_EDEFAULT.equals(getTable());
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE :
				return SPECIFIED_TABLE_EDEFAULT == null ? specifiedTable != null : !SPECIFIED_TABLE_EDEFAULT.equals(specifiedTable);
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__DEFAULT_TABLE :
				return DEFAULT_TABLE_EDEFAULT == null ? defaultTable != null : !DEFAULT_TABLE_EDEFAULT.equals(defaultTable);
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
		if (baseClass == IAbstractColumn.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UNIQUE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__UNIQUE;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__NULLABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__NULLABLE;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__INSERTABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__INSERTABLE;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UPDATABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__UPDATABLE;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__TABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__TABLE;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__SPECIFIED_TABLE;
				case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__DEFAULT_TABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__DEFAULT_TABLE;
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
		if (baseClass == IAbstractColumn.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__UNIQUE :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UNIQUE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__NULLABLE :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__NULLABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__INSERTABLE :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__INSERTABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__UPDATABLE :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__UPDATABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__TABLE :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__TABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__SPECIFIED_TABLE :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__SPECIFIED_TABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__DEFAULT_TABLE :
					return JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN__DEFAULT_TABLE;
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
		result.append(" (unique: ");
		result.append(unique);
		result.append(", nullable: ");
		result.append(nullable);
		result.append(", insertable: ");
		result.append(insertable);
		result.append(", updatable: ");
		result.append(updatable);
		result.append(", specifiedTable: ");
		result.append(specifiedTable);
		result.append(", defaultTable: ");
		result.append(defaultTable);
		result.append(')');
		return result.toString();
	}

	@Override
	protected String tableName() {
		return this.getTable();
	}

	public ITextRange getTableTextRange() {
		return this.elementTextRange(this.tableDeclarationAdapter);
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.tableDeclarationAdapter, pos, astRoot);
	}

	private Iterator<String> candidateTableNames() {
		return this.tableIsAllowed() ?
			this.getOwner().getTypeMapping().associatedTableNamesIncludingInherited()
		:
			EmptyIterator.<String>instance();
	}

	private Iterator<String> candidateTableNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateTableNames(), filter);
	}

	private Iterator<String> quotedCandidateTableNames(Filter<String> filter) {
		return StringTools.quote(this.candidateTableNames(filter));
	}

	/**
	 * Return whether the 'table' element is allowed. It is not allowed for
	 * join columns inside of join tables.
	 */
	public abstract boolean tableIsAllowed();

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos, astRoot)) {
			return this.quotedCandidateTableNames(filter);
		}
		return null;
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setSpecifiedTable(this.tableAdapter.getValue(astRoot));
		this.setUnique(DefaultFalseBoolean.fromJavaAnnotationValue(this.uniqueAdapter.getValue(astRoot)));
		this.setNullable(DefaultTrueBoolean.fromJavaAnnotationValue(this.nullableAdapter.getValue(astRoot)));
		this.setInsertable(DefaultTrueBoolean.fromJavaAnnotationValue(this.insertableAdapter.getValue(astRoot)));
		this.setUpdatable(DefaultTrueBoolean.fromJavaAnnotationValue(this.updatableAdapter.getValue(astRoot)));
	}
}
