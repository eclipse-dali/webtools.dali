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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Primary Key Join Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaPrimaryKeyJoinColumn()
 * @model kind="class"
 * @generated
 */
public class JavaPrimaryKeyJoinColumn extends JavaEObject
	implements IPrimaryKeyJoinColumn
{
	private final IAbstractJoinColumn.Owner owner;

	private final Member member;

	private final IndexedDeclarationAnnotationAdapter daa;

	private final IndexedAnnotationAdapter annotationAdapter;

	private final AnnotationElementAdapter nameAdapter;

	// hold this so we can get the 'referenced column name' text range
	private final DeclarationAnnotationElementAdapter referencedColumnNameDeclarationAdapter;

	private final AnnotationElementAdapter referencedColumnNameAdapter;

	private final AnnotationElementAdapter columnDefinitionAdapter;

	public static final SimpleDeclarationAnnotationAdapter SINGLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.PRIMARY_KEY_JOIN_COLUMN);

	public static final SimpleDeclarationAnnotationAdapter MULTIPLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.PRIMARY_KEY_JOIN_COLUMNS);

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
	 * The default value of the '{@link #getColumnDefinition() <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final String COLUMN_DEFINITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColumnDefinition() <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnDefinition()
	 * @generated
	 * @ordered
	 */
	protected String columnDefinition = COLUMN_DEFINITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getReferencedColumnName() <em>Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedReferencedColumnName() <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedReferencedColumnName() <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedReferencedColumnName = SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultReferencedColumnName() <em>Default Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultReferencedColumnName() <em>Default Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected String defaultReferencedColumnName = DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT;

	protected JavaPrimaryKeyJoinColumn() {
		throw new UnsupportedOperationException();
	}

	protected JavaPrimaryKeyJoinColumn(IAbstractJoinColumn.Owner owner, Member member, int index) {
		super();
		this.owner = owner;
		this.member = member;
		this.daa = new CombinationIndexedDeclarationAnnotationAdapter(SINGLE_DECLARATION_ANNOTATION_ADAPTER, MULTIPLE_DECLARATION_ANNOTATION_ADAPTER, index, JPA.PRIMARY_KEY_JOIN_COLUMN);
		this.annotationAdapter = new MemberIndexedAnnotationAdapter(member, this.daa);
		this.nameAdapter = this.buildAdapter(JPA.PRIMARY_KEY_JOIN_COLUMN__NAME);
		this.referencedColumnNameDeclarationAdapter = this.buildStringElementAdapter(JPA.PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		this.referencedColumnNameAdapter = this.buildShortCircuitElementAdapter(this.referencedColumnNameDeclarationAdapter);
		this.columnDefinitionAdapter = this.buildAdapter(JPA.PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION);
	}

	private AnnotationElementAdapter buildAdapter(String elementName) {
		return new ShortCircuitAnnotationElementAdapter(this.member, new ConversionDeclarationAnnotationElementAdapter(this.daa, elementName));
	}

	protected DeclarationAnnotationElementAdapter buildStringElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter(this.daa, elementName);
	}

	protected AnnotationElementAdapter buildShortCircuitElementAdapter(DeclarationAnnotationElementAdapter daea) {
		return new ShortCircuitAnnotationElementAdapter(this.member, daea);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_PRIMARY_KEY_JOIN_COLUMN;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIPrimaryKeyJoinColumn_Name()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getName() {
		return (this.specifiedName == null) ? this.defaultName : this.specifiedName;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getINamedColumn_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	public void setSpecifiedNameGen(String newSpecifiedName) {
		String oldSpecifiedName = specifiedName;
		specifiedName = newSpecifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
	}

	public void setSpecifiedName(String newSpecifiedName) {
		this.nameAdapter.setValue(newSpecifiedName);
		setSpecifiedNameGen(newSpecifiedName);
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getINamedColumn_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultName() {
		return defaultName;
	}

	//TODO should we allow setting through the ecore, that would make this method
	//public and part of the ITable api.  only the model needs to be setting the default,
	//but the ui needs to be listening for changes to the default.
	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_NAME, oldDefaultName, this.defaultName));
	}

	/**
	 * Returns the value of the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIPrimaryKeyJoinColumn_ReferencedColumnName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName == null) ? this.defaultReferencedColumnName : this.specifiedReferencedColumnName;
	}

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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractJoinColumn_SpecifiedReferencedColumnName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedReferencedColumnName() {
		return specifiedReferencedColumnName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn#getSpecifiedReferencedColumnName <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Referenced Column Name</em>' attribute.
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 */
	public void setSpecifiedReferencedColumnNameGen(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = specifiedReferencedColumnName;
		specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME, oldSpecifiedReferencedColumnName, specifiedReferencedColumnName));
	}

	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		this.referencedColumnNameAdapter.setValue(newSpecifiedReferencedColumnName);
		setSpecifiedReferencedColumnNameGen(newSpecifiedReferencedColumnName);
	}

	/**
	 * Returns the value of the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Referenced Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractJoinColumn_DefaultReferencedColumnName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultReferencedColumnName() {
		return defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME, oldDefaultReferencedColumnName, newDefaultReferencedColumnName));
	}

	/**
	 * Returns the value of the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Definition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Definition</em>' attribute.
	 * @see #setColumnDefinition(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getINamedColumn_ColumnDefinition()
	 * @model
	 * @generated
	 */
	public String getColumnDefinition() {
		return columnDefinition;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn#getColumnDefinition <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Definition</em>' attribute.
	 * @see #getColumnDefinition()
	 * @generated
	 */
	public void setColumnDefinitionGen(String newColumnDefinition) {
		String oldColumnDefinition = columnDefinition;
		columnDefinition = newColumnDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION, oldColumnDefinition, columnDefinition));
	}

	public void setColumnDefinition(String newColumnDefinition) {
		this.columnDefinitionAdapter.setValue(newColumnDefinition);
		setColumnDefinitionGen(newColumnDefinition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__NAME :
				return getName();
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME :
				return getSpecifiedName();
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_NAME :
				return getDefaultName();
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION :
				return getColumnDefinition();
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
				return getReferencedColumnName();
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				return getSpecifiedReferencedColumnName();
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
				return getDefaultReferencedColumnName();
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
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				setSpecifiedReferencedColumnName((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition(COLUMN_DEFINITION_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				setSpecifiedReferencedColumnName(SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__NAME :
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION :
				return COLUMN_DEFINITION_EDEFAULT == null ? columnDefinition != null : !COLUMN_DEFINITION_EDEFAULT.equals(columnDefinition);
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
				return REFERENCED_COLUMN_NAME_EDEFAULT == null ? getReferencedColumnName() != null : !REFERENCED_COLUMN_NAME_EDEFAULT.equals(getReferencedColumnName());
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				return SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT == null ? specifiedReferencedColumnName != null : !SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT.equals(specifiedReferencedColumnName);
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
				return DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT == null ? defaultReferencedColumnName != null : !DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT.equals(defaultReferencedColumnName);
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
		if (baseClass == INamedColumn.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__NAME;
				case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME;
				case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME;
				case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION :
					return JpaCoreMappingsPackage.INAMED_COLUMN__COLUMN_DEFINITION;
				default :
					return -1;
			}
		}
		if (baseClass == IAbstractJoinColumn.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME;
				default :
					return -1;
			}
		}
		if (baseClass == IPrimaryKeyJoinColumn.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == INamedColumn.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.INAMED_COLUMN__NAME :
					return JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME :
					return JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME :
					return JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__COLUMN_DEFINITION :
					return JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION;
				default :
					return -1;
			}
		}
		if (baseClass == IAbstractJoinColumn.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME;
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME;
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME;
				default :
					return -1;
			}
		}
		if (baseClass == IPrimaryKeyJoinColumn.class) {
			switch (baseFeatureID) {
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
		result.append(", columnDefinition: ");
		result.append(columnDefinition);
		result.append(", specifiedReferencedColumnName: ");
		result.append(specifiedReferencedColumnName);
		result.append(", defaultReferencedColumnName: ");
		result.append(defaultReferencedColumnName);
		result.append(')');
		return result.toString();
	}

	public Column dbReferencedColumn() {
		Table table = this.dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(this.getReferencedColumnName());
	}

	public Table dbReferencedColumnTable() {
		return getOwner().dbReferencedColumnTable();
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

	public ITextRange getReferencedColumnNameTextRange() {
		return elementTextRange(this.referencedColumnNameDeclarationAdapter);
	}

	public ITextRange getTextRange() {
		return this.member.annotationTextRange(this.daa);
	}

	protected ITextRange elementTextRange(DeclarationAnnotationElementAdapter elementAdapter) {
		return this.elementTextRange(this.member.annotationElementTextRange(elementAdapter));
	}

	public IAbstractJoinColumn.Owner getOwner() {
		return this.owner;
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		setDefaultReferencedColumnName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_REFERENCED_COLUMN_NAME_KEY));
		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_NAME_KEY));
	}

	/**
	 * allow owners to verify the annotation
	 */
	public Annotation annotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	// ********** persistence model -> java annotations **********
	public void updateFromJava(CompilationUnit astRoot) {
		setSpecifiedName((String) this.nameAdapter.getValue(astRoot));
		setSpecifiedReferencedColumnName((String) this.referencedColumnNameAdapter.getValue(astRoot));
		setColumnDefinition((String) this.columnDefinitionAdapter.getValue(astRoot));
	}

	void moveAnnotation(int newIndex) {
		this.annotationAdapter.moveAnnotation(newIndex);
	}

	void newAnnotation() {
		this.annotationAdapter.newMarkerAnnotation();
	}

	void removeAnnotation() {
		this.annotationAdapter.removeAnnotation();
	}

	// ********** static methods **********
	static JavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaPrimaryKeyJoinColumn(owner, member, index);
	}
}