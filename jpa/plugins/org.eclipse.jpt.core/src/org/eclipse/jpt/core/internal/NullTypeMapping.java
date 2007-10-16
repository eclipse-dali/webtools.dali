/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Null Type Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getNullTypeMapping()
 * @model kind="class"
 * @generated
 */
public class NullTypeMapping
	implements ITypeMapping, IJpaSourceObject
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
	 * The default value of the '{@link #getTableName() <em>Table Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableName()
	 * @generated
	 * @ordered
	 */
	protected static final String TABLE_NAME_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NullTypeMapping() {
		super();
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
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getITypeMapping_Name()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getName() {
		return "";
	}

	/**
	 * Returns the value of the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.JpaCoreackage#getITypeMapping_TableName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getTableName() {
		return "";
	}

	public IPersistentType getPersistentType() {
		throw new UnsupportedOperationException();
	}

	public IResource getResource() {
		throw new UnsupportedOperationException("getResource()");
	}

	/**
	 * @model kind="operation"
	 * @generated NOT
	 */
	public IJpaFile getJpaFile() {
		throw new UnsupportedOperationException("getJpaFile()");
	}

	public ITextRange validationTextRange() {
		throw new UnsupportedOperationException("validationTextRange()");
	}

	public String getKey() {
		return IMappingKeys.NULL_TYPE_MAPPING_KEY;
	}

	public void javaElementChanged(ElementChangedEvent event) {}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTablesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Table primaryDbTable() {
		return null;
	}

	public Table dbTable(String tableName) {
		return null;
	}

	public Schema dbSchema() {
		return null;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}

	public Iterator<String> overridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		// it's not so much that all mapping keys are allowed.
		// it's just that they're not really invalid, per se
		return true;
	}
}
