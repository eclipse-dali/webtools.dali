/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Set;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmXmlMapper;
import org.eclipse.jpt.core.internal.emfutility.DOMUtilities;
import org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IAbstractColumn;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Xml Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUniqueForXml <em>Unique For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getNullableForXml <em>Nullable For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getInsertableForXml <em>Insertable For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUpdatableForXml <em>Updatable For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getSpecifiedTableForXml <em>Specified Table For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractXmlColumn extends AbstractXmlNamedColumn
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

	/**
	 * The default value of the '{@link #getUniqueForXml() <em>Unique For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueForXml()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultFalseBoolean UNIQUE_FOR_XML_EDEFAULT = DefaultFalseBoolean.DEFAULT;

	/**
	 * The default value of the '{@link #getNullableForXml() <em>Nullable For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullableForXml()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean NULLABLE_FOR_XML_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The default value of the '{@link #getInsertableForXml() <em>Insertable For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertableForXml()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean INSERTABLE_FOR_XML_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The default value of the '{@link #getUpdatableForXml() <em>Updatable For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdatableForXml()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean UPDATABLE_FOR_XML_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedTableForXml() <em>Specified Table For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTableForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_TABLE_FOR_XML_EDEFAULT = null;

	protected AbstractXmlColumn() {
		throw new UnsupportedOperationException("Use AbstractXmlColumn(IColumn.Owner) instead.");
	}

	protected AbstractXmlColumn(INamedColumn.Owner owner) {
		super(owner);
	}

	@Override
	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IABSTRACT_COLUMN__DEFAULT_TABLE);
		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IABSTRACT_COLUMN__TABLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.ABSTRACT_XML_COLUMN;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractColumn_Unique()
	 * @model
	 * @generated
	 */
	public DefaultFalseBoolean getUnique() {
		return unique;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUnique <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see #getUnique()
	 * @generated
	 */
	public void setUniqueGen(DefaultFalseBoolean newUnique) {
		DefaultFalseBoolean oldUnique = unique;
		unique = newUnique == null ? UNIQUE_EDEFAULT : newUnique;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE, oldUnique, unique));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractColumn_Nullable()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getNullable() {
		return nullable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getNullable <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nullable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getNullable()
	 * @generated
	 */
	public void setNullableGen(DefaultTrueBoolean newNullable) {
		DefaultTrueBoolean oldNullable = nullable;
		nullable = newNullable == null ? NULLABLE_EDEFAULT : newNullable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE, oldNullable, nullable));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractColumn_Insertable()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getInsertable() {
		return insertable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getInsertable <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insertable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getInsertable()
	 * @generated
	 */
	public void setInsertableGen(DefaultTrueBoolean newInsertable) {
		DefaultTrueBoolean oldInsertable = insertable;
		insertable = newInsertable == null ? INSERTABLE_EDEFAULT : newInsertable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE, oldInsertable, insertable));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractColumn_Updatable()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getUpdatable() {
		return updatable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUpdatable <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updatable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getUpdatable()
	 * @generated
	 */
	public void setUpdatableGen(DefaultTrueBoolean newUpdatable) {
		DefaultTrueBoolean oldUpdatable = updatable;
		updatable = newUpdatable == null ? UPDATABLE_EDEFAULT : newUpdatable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE, oldUpdatable, updatable));
	}

	/**
	 * Returns the value of the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractColumn_Table()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractColumn_SpecifiedTable()
	 * @model
	 * @generated
	 */
	public String getSpecifiedTable() {
		return specifiedTable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getSpecifiedTable <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Table</em>' attribute.
	 * @see #getSpecifiedTable()
	 * @generated
	 */
	public void setSpecifiedTableGen(String newSpecifiedTable) {
		String oldSpecifiedTable = specifiedTable;
		specifiedTable = newSpecifiedTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE, oldSpecifiedTable, specifiedTable));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractColumn_DefaultTable()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultTable() {
		return defaultTable;
	}

	protected void setDefaultTable(String newDefaultTable) {
		String oldDefaultTable = this.defaultTable;
		this.defaultTable = newDefaultTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLUMN__DEFAULT_TABLE, oldDefaultTable, this.defaultTable));
	}

	/**
	 * Returns the value of the '<em><b>Unique For Xml</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see #setUniqueForXml(DefaultFalseBoolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn_UniqueForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public DefaultFalseBoolean getUniqueForXml() {
		return getUnique();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUniqueForXml <em>Unique For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see #getUniqueForXml()
	 * @generated NOT
	 */
	public void setUniqueForXml(DefaultFalseBoolean newUniqueForXml) {
		setUniqueGen(newUniqueForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML, null, newUniqueForXml));
	}

	/**
	 * Returns the value of the '<em><b>Nullable For Xml</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nullable For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nullable For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setNullableForXml(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn_NullableForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public DefaultTrueBoolean getNullableForXml() {
		return getNullable();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getNullableForXml <em>Nullable For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nullable For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getNullableForXml()
	 * @generated NOT
	 */
	public void setNullableForXml(DefaultTrueBoolean newNullableForXml) {
		setNullableGen(newNullableForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML, null, newNullableForXml));
	}

	/**
	 * Returns the value of the '<em><b>Insertable For Xml</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insertable For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insertable For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setInsertableForXml(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn_InsertableForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public DefaultTrueBoolean getInsertableForXml() {
		return getInsertable();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getInsertableForXml <em>Insertable For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insertable For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getInsertableForXml()
	 * @generated NOT
	 */
	public void setInsertableForXml(DefaultTrueBoolean newInsertableForXml) {
		setInsertableGen(newInsertableForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML, null, newInsertableForXml));
	}

	/**
	 * Returns the value of the '<em><b>Updatable For Xml</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Updatable For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Updatable For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setUpdatableForXml(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn_UpdatableForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public DefaultTrueBoolean getUpdatableForXml() {
		return getUpdatable();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getUpdatableForXml <em>Updatable For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updatable For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getUpdatableForXml()
	 * @generated NOT
	 */
	public void setUpdatableForXml(DefaultTrueBoolean newUpdatableForXml) {
		setUpdatableGen(newUpdatableForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML, null, newUpdatableForXml));
	}

	/**
	 * Returns the value of the '<em><b>Column Definition For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Definition For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Definition For Xml</em>' attribute.
	 * @see #setColumnDefinitionForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn_ColumnDefinitionForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getColumnDefinitionForXml() {
		return getColumnDefinition();
	}

	/**
	 * Returns the value of the '<em><b>Specified Table For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Table For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Table For Xml</em>' attribute.
	 * @see #setSpecifiedTableForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlColumn_SpecifiedTableForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getSpecifiedTableForXml() {
		return getSpecifiedTable();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn#getSpecifiedTableForXml <em>Specified Table For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Table For Xml</em>' attribute.
	 * @see #getSpecifiedTableForXml()
	 * @generated NOT
	 */
	public void setSpecifiedTableForXml(String newSpecifiedTableForXml) {
		setSpecifiedTableGen(newSpecifiedTableForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLUMN__SPECIFIED_TABLE_FOR_XML, newSpecifiedTableForXml + " ", newSpecifiedTableForXml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE :
				return getUnique();
			case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE :
				return getNullable();
			case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE :
				return getInsertable();
			case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE :
				return getUpdatable();
			case OrmPackage.ABSTRACT_XML_COLUMN__TABLE :
				return getTable();
			case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE :
				return getSpecifiedTable();
			case OrmPackage.ABSTRACT_XML_COLUMN__DEFAULT_TABLE :
				return getDefaultTable();
			case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML :
				return getUniqueForXml();
			case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML :
				return getNullableForXml();
			case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML :
				return getInsertableForXml();
			case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML :
				return getUpdatableForXml();
			case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML :
				return getSpecifiedTableForXml();
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
			case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE :
				setUnique((DefaultFalseBoolean) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE :
				setNullable((DefaultTrueBoolean) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE :
				setInsertable((DefaultTrueBoolean) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE :
				setUpdatable((DefaultTrueBoolean) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE :
				setSpecifiedTable((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML :
				setUniqueForXml((DefaultFalseBoolean) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML :
				setNullableForXml((DefaultTrueBoolean) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML :
				setInsertableForXml((DefaultTrueBoolean) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML :
				setUpdatableForXml((DefaultTrueBoolean) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML :
				setSpecifiedTableForXml((String) newValue);
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
			case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE :
				setUnique(UNIQUE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE :
				setNullable(NULLABLE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE :
				setInsertable(INSERTABLE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE :
				setUpdatable(UPDATABLE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE :
				setSpecifiedTable(SPECIFIED_TABLE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML :
				setUniqueForXml(UNIQUE_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML :
				setNullableForXml(NULLABLE_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML :
				setInsertableForXml(INSERTABLE_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML :
				setUpdatableForXml(UPDATABLE_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML :
				setSpecifiedTableForXml(SPECIFIED_TABLE_FOR_XML_EDEFAULT);
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
			case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE :
				return unique != UNIQUE_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE :
				return nullable != NULLABLE_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE :
				return insertable != INSERTABLE_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE :
				return updatable != UPDATABLE_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_COLUMN__TABLE :
				return TABLE_EDEFAULT == null ? getTable() != null : !TABLE_EDEFAULT.equals(getTable());
			case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE :
				return SPECIFIED_TABLE_EDEFAULT == null ? specifiedTable != null : !SPECIFIED_TABLE_EDEFAULT.equals(specifiedTable);
			case OrmPackage.ABSTRACT_XML_COLUMN__DEFAULT_TABLE :
				return DEFAULT_TABLE_EDEFAULT == null ? defaultTable != null : !DEFAULT_TABLE_EDEFAULT.equals(defaultTable);
			case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE_FOR_XML :
				return getUniqueForXml() != UNIQUE_FOR_XML_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE_FOR_XML :
				return getNullableForXml() != NULLABLE_FOR_XML_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE_FOR_XML :
				return getInsertableForXml() != INSERTABLE_FOR_XML_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE_FOR_XML :
				return getUpdatableForXml() != UPDATABLE_FOR_XML_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE_FOR_XML :
				return SPECIFIED_TABLE_FOR_XML_EDEFAULT == null ? getSpecifiedTableForXml() != null : !SPECIFIED_TABLE_FOR_XML_EDEFAULT.equals(getSpecifiedTableForXml());
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
				case OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__UNIQUE;
				case OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__NULLABLE;
				case OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__INSERTABLE;
				case OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__UPDATABLE;
				case OrmPackage.ABSTRACT_XML_COLUMN__TABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__TABLE;
				case OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE :
					return JpaCoreMappingsPackage.IABSTRACT_COLUMN__SPECIFIED_TABLE;
				case OrmPackage.ABSTRACT_XML_COLUMN__DEFAULT_TABLE :
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
					return OrmPackage.ABSTRACT_XML_COLUMN__UNIQUE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__NULLABLE :
					return OrmPackage.ABSTRACT_XML_COLUMN__NULLABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__INSERTABLE :
					return OrmPackage.ABSTRACT_XML_COLUMN__INSERTABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__UPDATABLE :
					return OrmPackage.ABSTRACT_XML_COLUMN__UPDATABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__TABLE :
					return OrmPackage.ABSTRACT_XML_COLUMN__TABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__SPECIFIED_TABLE :
					return OrmPackage.ABSTRACT_XML_COLUMN__SPECIFIED_TABLE;
				case JpaCoreMappingsPackage.IABSTRACT_COLUMN__DEFAULT_TABLE :
					return OrmPackage.ABSTRACT_XML_COLUMN__DEFAULT_TABLE;
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

	public ITextRange tableTextRange() {
		if (node == null) {
			return owner.validationTextRange();
		}
		IDOMNode tableNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.ENTITY__TABLE);
		return (tableNode == null) ? validationTextRange() : buildTextRange(tableNode);
	}
}
