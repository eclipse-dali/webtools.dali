/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IDiscriminator Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDefaultLength <em>Default Length</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getSpecifiedLength <em>Specified Length</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIDiscriminatorColumn()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IDiscriminatorColumn extends INamedColumn
{
	/**
	 * Returns the value of the '<em><b>Discriminator Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DiscriminatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @see #setDiscriminatorType(DiscriminatorType)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIDiscriminatorColumn_DiscriminatorType()
	 * @model
	 * @generated
	 */
	DiscriminatorType getDiscriminatorType();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @see #getDiscriminatorType()
	 * @generated
	 */
	void setDiscriminatorType(DiscriminatorType value);

	/**
	 * Returns the value of the '<em><b>Default Length</b></em>' attribute.
	 * The default value is <code>"31"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Length</em>' attribute.
	 * @see #setDefaultLength(int)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIDiscriminatorColumn_DefaultLength()
	 * @model default="31"
	 * @generated
	 */
	int getDefaultLength();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getDefaultLength <em>Default Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Length</em>' attribute.
	 * @see #getDefaultLength()
	 * @generated
	 */
	void setDefaultLength(int value);

	/**
	 * Returns the value of the '<em><b>Specified Length</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Length</em>' attribute.
	 * @see #setSpecifiedLength(int)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIDiscriminatorColumn_SpecifiedLength()
	 * @model default="-1"
	 * @generated
	 */
	int getSpecifiedLength();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn#getSpecifiedLength <em>Specified Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Length</em>' attribute.
	 * @see #getSpecifiedLength()
	 * @generated
	 */
	void setSpecifiedLength(int value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIDiscriminatorColumn_Length()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getLength();
	
	class DiscriminatorColumnOwner implements INamedColumn.Owner {
		
		private IEntity entity;
		
		public DiscriminatorColumnOwner(IEntity entity) {
			super();
			this.entity = entity;
		}
		
		public Table dbTable(String tableName) {
			return this.entity.dbTable(tableName);
		}
		
		public ITextRange getTextRange() {
			return this.entity.getDiscriminatorColumn().getTextRange();
		}
		
		public ITypeMapping getTypeMapping() {
			return this.entity;
		}
	}
} // IDiscriminatorColumn
