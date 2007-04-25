/**
 * <copyright>
 * </copyright>
 *
 * $Id: IAbstractColumn.java,v 1.1 2007/04/25 20:09:33 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IAbstract Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getUnique <em>Unique</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getNullable <em>Nullable</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getInsertable <em>Insertable</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getUpdatable <em>Updatable</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getTable <em>Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getSpecifiedTable <em>Specified Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getDefaultTable <em>Default Table</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IAbstractColumn extends INamedColumn
{
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn_Unique()
	 * @model
	 * @generated
	 */
	DefaultFalseBoolean getUnique();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getUnique <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see #getUnique()
	 * @generated
	 */
	void setUnique(DefaultFalseBoolean value);

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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn_Nullable()
	 * @model
	 * @generated
	 */
	DefaultTrueBoolean getNullable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getNullable <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nullable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getNullable()
	 * @generated
	 */
	void setNullable(DefaultTrueBoolean value);

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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn_Insertable()
	 * @model
	 * @generated
	 */
	DefaultTrueBoolean getInsertable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getInsertable <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insertable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getInsertable()
	 * @generated
	 */
	void setInsertable(DefaultTrueBoolean value);

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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn_Updatable()
	 * @model
	 * @generated
	 */
	DefaultTrueBoolean getUpdatable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getUpdatable <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updatable</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getUpdatable()
	 * @generated
	 */
	void setUpdatable(DefaultTrueBoolean value);

	/**
	 * Returns the value of the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn_Table()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getTable();

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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn_SpecifiedTable()
	 * @model
	 * @generated
	 */
	String getSpecifiedTable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn#getSpecifiedTable <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Table</em>' attribute.
	 * @see #getSpecifiedTable()
	 * @generated
	 */
	void setSpecifiedTable(String value);

	/**
	 * Returns the value of the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Table</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractColumn_DefaultTable()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultTable();

	/**
	 * Return the wrapper for the datasource column
	 */
	Column dbColumn();

	/**
	 * Return the wrapper for the datasource table
	 */
	Table dbTable();

	/**
	 * Return whether the datasource is connected
	 */
	boolean isConnected();

	/**
	 * Return whether the column is found on the datasource
	 */
	boolean isResolved();

	/**
	 * Return (best) text location of this column's name
	 */
	ITextRange getNameTextRange();

	/**
	 * Return (best) text location of this column's table
	 */
	ITextRange getTableTextRange();
}