/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.jdtutility.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Attribute Override</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAttributeOverride()
 * @model kind="class"
 * @generated
 */
public class JavaAttributeOverride extends JavaOverride
	implements IAttributeOverride
{
	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected IColumn column;

	public static final SimpleDeclarationAnnotationAdapter SINGLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ATTRIBUTE_OVERRIDE);

	public static final SimpleDeclarationAnnotationAdapter MULTIPLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ATTRIBUTE_OVERRIDES);

	protected JavaAttributeOverride() {
		throw new UnsupportedOperationException("use JavaAttributeOverride(Owner, Member, IndexedDeclarationAnnotationAdapter)");
	}

	protected JavaAttributeOverride(Owner owner, Member member, IndexedDeclarationAnnotationAdapter daa) {
		super(owner, member, daa);
		this.column = JavaColumn.createAttributeOverrideColumn(this.buildColumnOwner(), member, daa);
		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE__COLUMN, null, null);
	}

	@Override
	protected String nameElementName() {
		return JPA.ATTRIBUTE_OVERRIDE__NAME;
	}

	// TODO figure out how to use [stupid] EMF to implement the Column.Owner interface directly
	protected INamedColumn.Owner buildColumnOwner() {
		return new INamedColumn.Owner() {
			public ITypeMapping getTypeMapping() {
				return JavaAttributeOverride.this.getOwner().getTypeMapping();
			}

			public ITextRange getTextRange() {
				return JavaAttributeOverride.this.getTextRange();
			}

			public Table dbTable(String tableName) {
				return getTypeMapping().dbTable(tableName);
			}
		};
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_ATTRIBUTE_OVERRIDE;
	}

	/**
	 * Returns the value of the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAttributeOverride_Column()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public IColumn getColumn() {
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColumn(IColumn newColumn, NotificationChain msgs) {
		IColumn oldColumn = column;
		column = newColumn;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE__COLUMN, oldColumn, newColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE__COLUMN :
				return basicSetColumn(null, msgs);
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
			case JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE__COLUMN :
				return getColumn();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE__COLUMN :
				return column != null;
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
		if (baseClass == IColumnMapping.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IAttributeOverride.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE__COLUMN :
					return JpaCoreMappingsPackage.IATTRIBUTE_OVERRIDE__COLUMN;
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
		if (baseClass == IColumnMapping.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IAttributeOverride.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IATTRIBUTE_OVERRIDE__COLUMN :
					return JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE__COLUMN;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		((JavaColumn) getColumn()).updateFromJava(astRoot);
	}

	// ********** static methods **********
	static JavaAttributeOverride createAttributeOverride(Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaAttributeOverride(owner, member, buildAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildAnnotationAdapter(int index) {
		return new CombinationIndexedDeclarationAnnotationAdapter(SINGLE_DECLARATION_ANNOTATION_ADAPTER, MULTIPLE_DECLARATION_ANNOTATION_ADAPTER, index, JPA.ATTRIBUTE_OVERRIDE);
	}
} // JavaAttributeOverride
