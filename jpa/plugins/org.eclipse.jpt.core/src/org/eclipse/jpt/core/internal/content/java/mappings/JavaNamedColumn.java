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
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.BooleanStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.NumberStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Named Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedColumn()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class JavaNamedColumn extends JavaEObject
	implements INamedColumn
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

	private final Owner owner;

	private final Member member;

	// hold this so we can get the annotation's text range
	private final DeclarationAnnotationAdapter daa;

	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> columnDefinitionAdapter;

	protected JavaNamedColumn() {
		super();
		throw new UnsupportedOperationException("Use JavaNamedColumn(Owner, Member, DeclarationAnnotationAdapter) instead");
	}

	protected JavaNamedColumn(Owner owner, Member member, DeclarationAnnotationAdapter daa) {
		super();
		this.owner = owner;
		this.member = member;
		this.daa = daa;
		this.nameDeclarationAdapter = this.buildStringElementAdapter(this.nameElementName());
		this.nameAdapter = this.buildShortCircuitElementAdapter(this.nameDeclarationAdapter);
		this.columnDefinitionAdapter = this.buildShortCircuitStringElementAdapter(this.columnDefinitionElementName());
	}

	protected DeclarationAnnotationElementAdapter<String> buildStringElementAdapter(String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, elementName);
	}

	protected DeclarationAnnotationElementAdapter<String> buildBooleanElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(this.daa, elementName, BooleanStringExpressionConverter.instance());
	}

	protected DeclarationAnnotationElementAdapter<String> buildIntElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(this.daa, elementName, NumberStringExpressionConverter.instance());
	}

	protected AnnotationElementAdapter<String> buildShortCircuitElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.member, daea);
	}

	protected AnnotationElementAdapter<String> buildShortCircuitStringElementAdapter(String elementName) {
		return this.buildShortCircuitElementAdapter(this.buildStringElementAdapter(elementName));
	}

	protected AnnotationElementAdapter<String> buildShortCircuitBooleanElementAdapter(String elementName) {
		return this.buildShortCircuitElementAdapter(this.buildBooleanElementAdapter(elementName));
	}

	protected IntAnnotationElementAdapter buildShortCircuitIntElementAdapter(String elementName) {
		return new IntAnnotationElementAdapter(this.buildShortCircuitElementAdapter(this.buildIntElementAdapter(elementName)));
	}

	protected abstract String nameElementName();

	protected abstract String columnDefinitionElementName();

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(INamedColumn.class)) {
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_NAME :
				this.nameAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_COLUMN__COLUMN_DEFINITION :
				this.columnDefinitionAdapter.setValue((String) notification.getNewValue());
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
		return JpaJavaMappingsPackage.Literals.JAVA_NAMED_COLUMN;
	}

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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getINamedColumn_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn#getSpecifiedName <em>Specified Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
	}

	//TODO should we allow setting through the ecore, that would make this method
	//public and part of the ITable api.  only the model needs to be setting the default,
	//but the ui needs to be listening for changes to the default.
	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__DEFAULT_NAME, oldDefaultName, this.defaultName));
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
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn#getColumnDefinition <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Definition</em>' attribute.
	 * @see #getColumnDefinition()
	 * @generated
	 */
	public void setColumnDefinition(String newColumnDefinition) {
		String oldColumnDefinition = columnDefinition;
		columnDefinition = newColumnDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__COLUMN_DEFINITION, oldColumnDefinition, columnDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__NAME :
				return getName();
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__SPECIFIED_NAME :
				return getSpecifiedName();
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__DEFAULT_NAME :
				return getDefaultName();
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__COLUMN_DEFINITION :
				return getColumnDefinition();
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
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition(COLUMN_DEFINITION_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__NAME :
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__COLUMN_DEFINITION :
				return COLUMN_DEFINITION_EDEFAULT == null ? columnDefinition != null : !COLUMN_DEFINITION_EDEFAULT.equals(columnDefinition);
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
				case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__NAME;
				case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME;
				case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__DEFAULT_NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME;
				case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__COLUMN_DEFINITION :
					return JpaCoreMappingsPackage.INAMED_COLUMN__COLUMN_DEFINITION;
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
					return JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME :
					return JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME :
					return JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__DEFAULT_NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__COLUMN_DEFINITION :
					return JpaJavaMappingsPackage.JAVA_NAMED_COLUMN__COLUMN_DEFINITION;
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
		result.append(')');
		return result.toString();
	}

	public Owner getOwner() {
		return this.owner;
	}

	public ITextRange validationTextRange() {
		ITextRange textRange = this.member.annotationTextRange(this.daa);
		return (textRange != null) ? textRange : this.owner.validationTextRange();
	}

	protected ITextRange elementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter) {
		return this.elementTextRange(this.member.annotationElementTextRange(elementAdapter));
	}

	protected ITextRange elementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, CompilationUnit astRoot) {
		return this.elementTextRange(this.member.annotationElementTextRange(elementAdapter, astRoot));
	}

	public ITextRange nameTextRange() {
		return this.elementTextRange(this.nameDeclarationAdapter);
	}

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}

	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.member.annotationElementTextRange(elementAdapter, astRoot), pos);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setSpecifiedName(this.nameAdapter.getValue(astRoot));
		this.setColumnDefinition(this.columnDefinitionAdapter.getValue(astRoot));
	}

	public Column dbColumn() {
		Table table = this.dbTable();
		return (table == null) ? null : table.columnNamed(this.getName());
	}

	public Table dbTable() {
		return this.owner.dbTable(this.tableName());
	}

	/**
	 * Return the name of the column's table.
	 */
	protected abstract String tableName();

	public boolean isResolved() {
		return this.dbColumn() != null;
	}

	@Override
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.quotedCandidateNames(filter);
		}
		return null;
	}

	private Iterator<String> candidateNames() {
		Table dbTable = this.dbTable();
		return (dbTable != null) ? dbTable.columnNames() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateNames(), filter);
	}

	private Iterator<String> quotedCandidateNames(Filter<String> filter) {
		return StringTools.quote(this.candidateNames(filter));
	}
}
