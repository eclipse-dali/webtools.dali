/**
 * <copyright>
 * </copyright>
 *
 * $Id: IAbstractJoinColumn.java,v 1.9 2007/05/30 20:39:02 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.mappings;

import java.util.List;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IAbstract Join Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getReferencedColumnName <em>Referenced Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getSpecifiedReferencedColumnName <em>Specified Referenced Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getDefaultReferencedColumnName <em>Default Referenced Column Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractJoinColumn()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IAbstractJoinColumn extends INamedColumn
{
	/**
	 * Returns the value of the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractJoinColumn_ReferencedColumnName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getReferencedColumnName();

	/**
	 * Returns the value of the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Referenced Column Name</em>' attribute.
	 * @see #setSpecifiedReferencedColumnName(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractJoinColumn_SpecifiedReferencedColumnName()
	 * @model
	 * @generated
	 */
	String getSpecifiedReferencedColumnName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn#getSpecifiedReferencedColumnName <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Referenced Column Name</em>' attribute.
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 */
	void setSpecifiedReferencedColumnName(String value);

	/**
	 * Returns the value of the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Referenced Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIAbstractJoinColumn_DefaultReferencedColumnName()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultReferencedColumnName();

	void refreshDefaults(DefaultsContext defaultsContext);

	/**
	 * Return the wrapper for the datasource referenced column
	 */
	Column dbReferencedColumn();

	/**
	 * Return whether the reference column is found on the datasource
	 */
	boolean isReferencedColumnResolved();

	/**
	 * Return the (best guess) text location of the referenced column name
	 */
	ITextRange referencedColumnNameTextRange();

	/**
	 * Re-declared to return IAbstractJoinColumn.Owner.
	 */
	IAbstractJoinColumn.Owner getOwner();


	interface Owner extends INamedColumn.Owner
	{
		/**
		 * Return the wrapper for the datasource table for the referenced column
		 */
		Table dbReferencedColumnTable();

		/**
		 * return the list of join columns containing the join column
		 */
		List<? extends IAbstractJoinColumn> joinColumns();
	}
}
