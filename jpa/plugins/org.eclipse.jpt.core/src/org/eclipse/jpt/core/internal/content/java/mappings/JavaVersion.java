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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.IVersion;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.TemporalType;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Version</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaVersion()
 * @model kind="class"
 * @generated
 */
public class JavaVersion extends JavaAttributeMapping implements IVersion
{
	private final AnnotationAdapter temporalAnnotationAdapter;

	private final AnnotationElementAdapter temporalValueAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.VERSION);

	private static final DeclarationAnnotationAdapter TEMPORAL_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.TEMPORAL);

	private static final DeclarationAnnotationElementAdapter TEMPORAL_VALUE_ADAPTER = buildTemporalValueAdapter();

	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected IColumn column;

	/**
	 * The default value of the '{@link #getTemporal() <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporal()
	 * @generated
	 * @ordered
	 */
	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.NULL;

	/**
	 * The cached value of the '{@link #getTemporal() <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporal()
	 * @generated
	 * @ordered
	 */
	protected TemporalType temporal = TEMPORAL_EDEFAULT;

	protected JavaVersion() {
		throw new UnsupportedOperationException("Use JavaVersion(Attribute) instead");
	}

	protected JavaVersion(Attribute attribute) {
		super(attribute);
		this.column = JavaColumn.createColumnMappingColumn(buildColumnOwner(), getAttribute());
		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_VERSION__COLUMN, null, null);
		this.temporalAnnotationAdapter = new MemberAnnotationAdapter(this.getAttribute(), TEMPORAL_ADAPTER);
		this.temporalValueAdapter = new ShortCircuitAnnotationElementAdapter(attribute, TEMPORAL_VALUE_ADAPTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_VERSION;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIVersion_Column()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_VERSION__COLUMN, oldColumn, newColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIVersion_Temporal()
	 * @model
	 * @generated
	 */
	public TemporalType getTemporal() {
		return temporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	public void setTemporalGen(TemporalType newTemporal) {
		TemporalType oldTemporal = temporal;
		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_VERSION__TEMPORAL, oldTemporal, temporal));
	}

	public void setTemporal(TemporalType newTemporal) {
		if (newTemporal != TemporalType.NULL) {
			if (this.temporalAnnotationAdapter.getAnnotation() == null) {
				this.temporalAnnotationAdapter.newMarkerAnnotation();
			}
			this.temporalValueAdapter.setValue(newTemporal.convertToJavaAnnotationValue());
		}
		else if (this.temporalAnnotationAdapter.getAnnotation() != null) {
			this.temporalAnnotationAdapter.removeAnnotation();
		}
		setTemporalGen(newTemporal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_VERSION__COLUMN :
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
			case JpaJavaMappingsPackage.JAVA_VERSION__COLUMN :
				return getColumn();
			case JpaJavaMappingsPackage.JAVA_VERSION__TEMPORAL :
				return getTemporal();
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
			case JpaJavaMappingsPackage.JAVA_VERSION__TEMPORAL :
				setTemporal((TemporalType) newValue);
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
			case JpaJavaMappingsPackage.JAVA_VERSION__TEMPORAL :
				setTemporal(TEMPORAL_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_VERSION__COLUMN :
				return column != null;
			case JpaJavaMappingsPackage.JAVA_VERSION__TEMPORAL :
				return temporal != TEMPORAL_EDEFAULT;
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
		if (baseClass == IVersion.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_VERSION__COLUMN :
					return JpaCoreMappingsPackage.IVERSION__COLUMN;
				case JpaJavaMappingsPackage.JAVA_VERSION__TEMPORAL :
					return JpaCoreMappingsPackage.IVERSION__TEMPORAL;
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
		if (baseClass == IVersion.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IVERSION__COLUMN :
					return JpaJavaMappingsPackage.JAVA_VERSION__COLUMN;
				case JpaCoreMappingsPackage.IVERSION__TEMPORAL :
					return JpaJavaMappingsPackage.JAVA_VERSION__TEMPORAL;
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
		result.append(" (temporal: ");
		result.append(temporal);
		result.append(')');
		return result.toString();
	}

	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getKey() {
		return IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updateTemporalFromJava(astRoot);
		this.getJavaColumn().updateFromJava(astRoot);
	}

	/*
	 * The @Temporal annotation is a bit different than most JPA annotations.
	 * For some indecipherable reason it has no default value (e.g. TIMESTAMP).
	 * Also, it is *required* for any attribute declared with a type of
	 * java.util.Date or java.util.Calendar; otherwise, it is *prohibited*.
	 * As a result we allow a Basic mapping to have a null 'temporal',
	 * indicating that the annotation is completely missing, as opposed
	 * to the annotation being present but its value is invalid (e.g.
	 * @Temporal(FRIDAY)).
	 * 
	 * TODO this comment is wrong now, revisit this with Brian at some point
	 */
	private void updateTemporalFromJava(CompilationUnit astRoot) {
		if (this.temporalAnnotationAdapter.getAnnotation(astRoot) == null) {
			setTemporalGen(TemporalType.NULL);
		}
		else {
			setTemporalGen(TemporalType.fromJavaAnnotationValue(this.temporalValueAdapter.getValue(astRoot)));
		}
	}

	private JavaColumn getJavaColumn() {
		return (JavaColumn) this.column;
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getJavaColumn().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter buildTemporalValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(TEMPORAL_ADAPTER, JPA.TEMPORAL__VALUE, false);
	}
}
