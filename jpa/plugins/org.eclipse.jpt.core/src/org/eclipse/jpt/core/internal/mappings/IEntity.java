/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedName <em>Specified Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultName <em>Default Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getTable <em>Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedSecondaryTables <em>Specified Secondary Tables</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedPrimaryKeyJoinColumns <em>Specified Primary Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultPrimaryKeyJoinColumns <em>Default Primary Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getInheritanceStrategy <em>Inheritance Strategy</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultDiscriminatorValue <em>Default Discriminator Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedDiscriminatorValue <em>Specified Discriminator Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDiscriminatorValue <em>Discriminator Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDiscriminatorColumn <em>Discriminator Column</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSequenceGenerator <em>Sequence Generator</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getTableGenerator <em>Table Generator</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getAttributeOverrides <em>Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedAttributeOverrides <em>Specified Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultAttributeOverrides <em>Default Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getAssociationOverrides <em>Association Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedAssociationOverrides <em>Specified Association Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultAssociationOverrides <em>Default Association Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getNamedQueries <em>Named Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getNamedNativeQueries <em>Named Native Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IEntity#getIdClass <em>Id Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IEntity extends ITypeMapping
{
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_SpecifiedName()
	 * @model
	 * @generated
	 */
	String getSpecifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	void setSpecifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultName();

	/**
	 * Returns the value of the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_Table()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	ITable getTable();

	/**
	 * Returns the value of the '<em><b>Specified Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_SpecifiedSecondaryTables()
	 * @model type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable" containment="true"
	 * @generated
	 */
	EList<ISecondaryTable> getSpecifiedSecondaryTables();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable"
	 * @generated
	 */
	EList<ISecondaryTable> getSecondaryTables();

	/**
	 * Returns the value of the '<em><b>Inheritance Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.InheritanceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inheritance Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inheritance Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see #setInheritanceStrategy(InheritanceType)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_InheritanceStrategy()
	 * @model
	 * @generated
	 */
	InheritanceType getInheritanceStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getInheritanceStrategy <em>Inheritance Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inheritance Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see #getInheritanceStrategy()
	 * @generated
	 */
	void setInheritanceStrategy(InheritanceType value);

	/**
	 * Returns the value of the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Column</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_DiscriminatorColumn()
	 * @model containment="true" changeable="false"
	 * @generated
	 */
	IDiscriminatorColumn getDiscriminatorColumn();

	/**
	 * Returns the value of the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #setSequenceGenerator(ISequenceGenerator)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_SequenceGenerator()
	 * @model containment="true"
	 * @generated
	 */
	ISequenceGenerator getSequenceGenerator();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSequenceGenerator <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	void setSequenceGenerator(ISequenceGenerator value);

	/**
	 * Returns the value of the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generator</em>' containment reference.
	 * @see #setTableGenerator(ITableGenerator)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_TableGenerator()
	 * @model containment="true"
	 * @generated
	 */
	ITableGenerator getTableGenerator();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getTableGenerator <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Generator</em>' containment reference.
	 * @see #getTableGenerator()
	 * @generated
	 */
	void setTableGenerator(ITableGenerator value);

	/**
	 * Returns the value of the '<em><b>Default Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Discriminator Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Discriminator Value</em>' attribute.
	 * @see #setDefaultDiscriminatorValue(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_DefaultDiscriminatorValue()
	 * @model
	 * @generated
	 */
	String getDefaultDiscriminatorValue();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getDefaultDiscriminatorValue <em>Default Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Discriminator Value</em>' attribute.
	 * @see #getDefaultDiscriminatorValue()
	 * @generated
	 */
	void setDefaultDiscriminatorValue(String value);

	/**
	 * Returns the value of the '<em><b>Specified Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Discriminator Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Discriminator Value</em>' attribute.
	 * @see #setSpecifiedDiscriminatorValue(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_SpecifiedDiscriminatorValue()
	 * @model
	 * @generated
	 */
	String getSpecifiedDiscriminatorValue();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getSpecifiedDiscriminatorValue <em>Specified Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Discriminator Value</em>' attribute.
	 * @see #getSpecifiedDiscriminatorValue()
	 * @generated
	 */
	void setSpecifiedDiscriminatorValue(String value);

	/**
	 * Returns the value of the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_DiscriminatorValue()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getDiscriminatorValue();

	/**
	 * Returns the value of the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_PrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();

	/**
	 * Returns the value of the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_SpecifiedPrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true"
	 * @generated
	 */
	EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();

	/**
	 * Returns the value of the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_DefaultPrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true"
	 * @generated
	 */
	EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns();

	/**
	 * Returns the value of the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_AttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList<IAttributeOverride> getAttributeOverrides();

	/**
	 * Returns the value of the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Attribute Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_SpecifiedAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	EList<IAttributeOverride> getSpecifiedAttributeOverrides();

	/**
	 * Returns the value of the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Attribute Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_DefaultAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	EList<IAttributeOverride> getDefaultAttributeOverrides();

	/**
	 * Returns the value of the '<em><b>Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Association Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_AssociationOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAssociationOverride" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList<IAssociationOverride> getAssociationOverrides();

	/**
	 * Returns the value of the '<em><b>Specified Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Association Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_SpecifiedAssociationOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAssociationOverride" containment="true"
	 * @generated
	 */
	EList<IAssociationOverride> getSpecifiedAssociationOverrides();

	/**
	 * Returns the value of the '<em><b>Default Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Association Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_DefaultAssociationOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAssociationOverride" containment="true"
	 * @generated
	 */
	EList<IAssociationOverride> getDefaultAssociationOverrides();

	/**
	 * Returns the value of the '<em><b>Named Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.INamedQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_NamedQueries()
	 * @model type="org.eclipse.jpt.core.internal.mappings.INamedQuery" containment="true"
	 * @generated
	 */
	EList<INamedQuery> getNamedQueries();

	/**
	 * Returns the value of the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Native Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Native Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_NamedNativeQueries()
	 * @model type="org.eclipse.jpt.core.internal.mappings.INamedNativeQuery" containment="true"
	 * @generated
	 */
	EList<INamedNativeQuery> getNamedNativeQueries();

	/**
	 * Returns the value of the '<em><b>Id Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class</em>' attribute.
	 * @see #setIdClass(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIEntity_IdClass()
	 * @model
	 * @generated
	 */
	String getIdClass();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IEntity#getIdClass <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' attribute.
	 * @see #getIdClass()
	 * @generated
	 */
	void setIdClass(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean discriminatorValueIsAllowed();

	/**
	 * Return the ultimate top of the inheritance hierarchy 
	 * This method should never return null. The root
	 * is defined as the persistent type in the inheritance hierarchy
	 * that has no parent.  The root should be an entity
	 *  
	 * Non-entities in the hierarchy should be ignored, ie skip
	 * over them in the search for the root. 
	 * 
	 * @model
	 * @generated
	 */
	IEntity rootEntity();

	/**
	 * <!-- begin-user-doc -->
	 * The first parent in the class hierarchy that is an entity. 
	 * This is the parent in the entity (persistent) inheritance hierarchy
	 * (vs class inheritance hierarchy)
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	IEntity parentEntity();

	/**
	 * Return the name of the entity's primary key column.
	 * Return null if the entity's primary key is "compound"
	 * (i.e. the primary key is composed of multiple columns).
	 */
	String primaryKeyColumnName();

	/**
	 * Return the name of the entity's primary key attribute.
	 * Return null if the entity's primary key is "compound"
	 * (i.e. the primary key is composed of multiple columns).
	 */
	String primaryKeyAttributeName();

	IAttributeOverride createAttributeOverride(int index);

	IAssociationOverride createAssociationOverride(int index);

	Iterator<String> allOverridableAttributeNames();

	Iterator<String> allOverridableAssociationNames();

	boolean containsAttributeOverride(String name);

	boolean containsSpecifiedAttributeOverride(String name);

	boolean containsAssociationOverride(String name);

	boolean containsSpecifiedAssociationOverride(String name);

	boolean containsSecondaryTable(String name);

	boolean containsSpecifiedSecondaryTable(String name);

	ISecondaryTable createSecondaryTable(int index);

	boolean containsSpecifiedPrimaryKeyJoinColumns();

	IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index);

	INamedQuery createNamedQuery(int index);

	INamedNativeQuery createNamedNativeQuery(int index);


	abstract class OverrideOwner implements IOverride.Owner
	{
		protected IEntity entity;

		public OverrideOwner(IEntity entity) {
			this.entity = entity;
		}

		public ITypeMapping getTypeMapping() {
			return this.entity;
		}

		public ITextRange validationTextRange() {
			return entity.validationTextRange();
		}
	}


	class AttributeOverrideOwner extends OverrideOwner
	{
		public AttributeOverrideOwner(IEntity entity) {
			super(entity);
		}

		public IAttributeMapping attributeMapping(String attributeName) {
			return (IAttributeMapping) columnMapping(attributeName);
		}

		private IColumnMapping columnMapping(String attributeName) {
			for (Iterator<IPersistentAttribute> stream = this.entity.getPersistentType().allAttributes(); stream.hasNext();) {
				IPersistentAttribute persAttribute = stream.next();
				if (attributeName.equals(persAttribute.getName())) {
					if (persAttribute.getMapping() instanceof IColumnMapping) {
						return (IColumnMapping) persAttribute.getMapping();
					}
				}
			}
			return null;
		}

		public boolean isVirtual(IOverride override) {
			return entity.getDefaultAttributeOverrides().contains(override);
		}
	}


	class AssociationOverrideOwner extends OverrideOwner
	{
		public AssociationOverrideOwner(IEntity entity) {
			super(entity);
		}

		public IAttributeMapping attributeMapping(String attributeName) {
			for (Iterator<IPersistentAttribute> stream = this.entity.getPersistentType().allAttributes(); stream.hasNext();) {
				IPersistentAttribute persAttribute = stream.next();
				if (attributeName.equals(persAttribute.getName())) {
					if (persAttribute.getMapping() instanceof IColumnMapping) {
						return persAttribute.getMapping();
					}
				}
			}
			return null;
		}

		public boolean isVirtual(IOverride override) {
			return entity.getDefaultAssociationOverrides().contains(override);
		}
	}


	class PrimaryKeyJoinColumnOwner implements IAbstractJoinColumn.Owner
	{
		private IEntity entity;

		public PrimaryKeyJoinColumnOwner(IEntity entity) {
			this.entity = entity;
		}

		public ITextRange validationTextRange() {
			return entity.validationTextRange();
		}

		public ITypeMapping getTypeMapping() {
			return entity;
		}

		public Table dbTable(String tableName) {
			return entity.dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity parentEntity = entity.parentEntity();
			return (parentEntity == null) ? null : parentEntity.primaryDbTable();
		}
	}
}
