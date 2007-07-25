/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.WorkingCopyOwner;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.BooleanAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.BooleanStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleBooleanAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.EnumType;
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.TemporalType;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Basic</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaBasic()
 * @model kind="class"
 * @generated
 */
public class JavaBasic extends JavaAttributeMapping implements IJavaBasic
{
	private final AnnotationElementAdapter<String> optionalAdapter;

	private final AnnotationElementAdapter<String> fetchAdapter;

	private final AnnotationAdapter temporalAnnotationAdapter;

	private final AnnotationElementAdapter<String> temporalValueAdapter;

	private final AnnotationAdapter enumeratedAnnotationAdapter;

	private final AnnotationElementAdapter<String> enumeratedValueAdapter;

	private final BooleanAnnotationAdapter lobAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.BASIC);

	private static final DeclarationAnnotationElementAdapter<String> OPTIONAL_ADAPTER = buildOptionalAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	public static final DeclarationAnnotationAdapter TEMPORAL_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.TEMPORAL);

	private static final DeclarationAnnotationElementAdapter<String> TEMPORAL_VALUE_ADAPTER = buildTemporalValueAdapter();

	public static final DeclarationAnnotationAdapter ENUMERATED_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ENUMERATED);

	private static final DeclarationAnnotationElementAdapter<String> ENUMERATED_VALUE_ADAPTER = buildEnumeratedValueAdapter();

	public static final DeclarationAnnotationAdapter LOB_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.LOB);

	/**
	 * The default value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultEagerFetchType FETCH_EDEFAULT = DefaultEagerFetchType.DEFAULT;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected DefaultEagerFetchType fetch = FETCH_EDEFAULT;

	/**
	 * The default value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean OPTIONAL_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected DefaultTrueBoolean optional = OPTIONAL_EDEFAULT;

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
	 * The default value of the '{@link #isLob() <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLob()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOB_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLob() <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLob()
	 * @generated
	 * @ordered
	 */
	protected boolean lob = LOB_EDEFAULT;

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

	/**
	 * The default value of the '{@link #getEnumerated() <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumerated()
	 * @generated
	 * @ordered
	 */
	protected static final EnumType ENUMERATED_EDEFAULT = EnumType.DEFAULT;

	/**
	 * The cached value of the '{@link #getEnumerated() <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumerated()
	 * @generated
	 * @ordered
	 */
	protected EnumType enumerated = ENUMERATED_EDEFAULT;

	protected JavaBasic() {
		this(null);
	}

	protected JavaBasic(Attribute attribute) {
		super(attribute);
		this.column = JavaColumn.createColumnMappingColumn(buildColumnOwner(), getAttribute());
		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_BASIC__COLUMN, null, null);
		this.optionalAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, OPTIONAL_ADAPTER);
		this.fetchAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, FETCH_ADAPTER);
		this.lobAdapter = new SimpleBooleanAnnotationAdapter(new MemberAnnotationAdapter(attribute, LOB_ADAPTER));
		this.temporalAnnotationAdapter = new MemberAnnotationAdapter(this.getAttribute(), TEMPORAL_ADAPTER);
		this.temporalValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, TEMPORAL_VALUE_ADAPTER);
		this.enumeratedAnnotationAdapter = new MemberAnnotationAdapter(this.getAttribute(), ENUMERATED_ADAPTER);
		this.enumeratedValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, ENUMERATED_VALUE_ADAPTER);
	}

	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IBasic.class)) {
			case JpaJavaMappingsPackage.JAVA_BASIC__FETCH :
				this.fetchAdapter.setValue(((DefaultEagerFetchType) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			case JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL :
				this.optionalAdapter.setValue(((DefaultTrueBoolean) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			case JpaJavaMappingsPackage.JAVA_BASIC__LOB :
				this.lobAdapter.setValue(notification.getNewBooleanValue());
				break;
			case JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL :
				TemporalType newTemporal = (TemporalType) notification.getNewValue();
				if (newTemporal == TemporalType.NULL) {
					this.temporalAnnotationAdapter.removeAnnotation();
				}
				else {
					this.temporalValueAdapter.setValue(newTemporal.convertToJavaAnnotationValue());
				}
				break;
			case JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED :
				EnumType newEnumerated = (EnumType) notification.getNewValue();
				if (newEnumerated == EnumType.DEFAULT) {
					this.enumeratedAnnotationAdapter.removeAnnotation();
				}
				else {
					this.enumeratedValueAdapter.setValue(newEnumerated.convertToJavaAnnotationValue());
				}
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
		return JpaJavaMappingsPackage.Literals.JAVA_BASIC;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIBasic_Column()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__COLUMN, oldColumn, newColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #setFetch(DefaultEagerFetchType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIBasic_Fetch()
	 * @model
	 * @generated
	 */
	public DefaultEagerFetchType getFetch() {
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(DefaultEagerFetchType newFetch) {
		DefaultEagerFetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__FETCH, oldFetch, fetch));
	}

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setOptional(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIBasic_Optional()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getOptional() {
		return optional;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic#getOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getOptional()
	 * @generated
	 */
	public void setOptional(DefaultTrueBoolean newOptional) {
		DefaultTrueBoolean oldOptional = optional;
		optional = newOptional == null ? OPTIONAL_EDEFAULT : newOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL, oldOptional, optional));
	}

	/**
	 * Returns the value of the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lob</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lob</em>' attribute.
	 * @see #setLob(boolean)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIBasic_Lob()
	 * @model
	 * @generated
	 */
	public boolean isLob() {
		return lob;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic#isLob <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lob</em>' attribute.
	 * @see #isLob()
	 * @generated
	 */
	public void setLob(boolean newLob) {
		boolean oldLob = lob;
		lob = newLob;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__LOB, oldLob, lob));
	}

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIBasic_Temporal()
	 * @model
	 * @generated
	 */
	public TemporalType getTemporal() {
		return temporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = temporal;
		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL, oldTemporal, temporal));
	}

	/**
	 * Returns the value of the '<em><b>Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.EnumType
	 * @see #setEnumerated(EnumType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIBasic_Enumerated()
	 * @model
	 * @generated
	 */
	public EnumType getEnumerated() {
		return enumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic#getEnumerated <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.EnumType
	 * @see #getEnumerated()
	 * @generated
	 */
	public void setEnumerated(EnumType newEnumerated) {
		EnumType oldEnumerated = enumerated;
		enumerated = newEnumerated == null ? ENUMERATED_EDEFAULT : newEnumerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED, oldEnumerated, enumerated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_BASIC__COLUMN :
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
			case JpaJavaMappingsPackage.JAVA_BASIC__FETCH :
				return getFetch();
			case JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL :
				return getOptional();
			case JpaJavaMappingsPackage.JAVA_BASIC__COLUMN :
				return getColumn();
			case JpaJavaMappingsPackage.JAVA_BASIC__LOB :
				return isLob() ? Boolean.TRUE : Boolean.FALSE;
			case JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL :
				return getTemporal();
			case JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED :
				return getEnumerated();
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
			case JpaJavaMappingsPackage.JAVA_BASIC__FETCH :
				setFetch((DefaultEagerFetchType) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL :
				setOptional((DefaultTrueBoolean) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_BASIC__LOB :
				setLob(((Boolean) newValue).booleanValue());
				return;
			case JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL :
				setTemporal((TemporalType) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED :
				setEnumerated((EnumType) newValue);
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
			case JpaJavaMappingsPackage.JAVA_BASIC__FETCH :
				setFetch(FETCH_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL :
				setOptional(OPTIONAL_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_BASIC__LOB :
				setLob(LOB_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL :
				setTemporal(TEMPORAL_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED :
				setEnumerated(ENUMERATED_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_BASIC__FETCH :
				return fetch != FETCH_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL :
				return optional != OPTIONAL_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_BASIC__COLUMN :
				return column != null;
			case JpaJavaMappingsPackage.JAVA_BASIC__LOB :
				return lob != LOB_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL :
				return temporal != TEMPORAL_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED :
				return enumerated != ENUMERATED_EDEFAULT;
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
		if (baseClass == IBasic.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_BASIC__FETCH :
					return JpaCoreMappingsPackage.IBASIC__FETCH;
				case JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL :
					return JpaCoreMappingsPackage.IBASIC__OPTIONAL;
				case JpaJavaMappingsPackage.JAVA_BASIC__COLUMN :
					return JpaCoreMappingsPackage.IBASIC__COLUMN;
				case JpaJavaMappingsPackage.JAVA_BASIC__LOB :
					return JpaCoreMappingsPackage.IBASIC__LOB;
				case JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL :
					return JpaCoreMappingsPackage.IBASIC__TEMPORAL;
				case JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED :
					return JpaCoreMappingsPackage.IBASIC__ENUMERATED;
				default :
					return -1;
			}
		}
		if (baseClass == IJavaBasic.class) {
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
		if (baseClass == IColumnMapping.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IBasic.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IBASIC__FETCH :
					return JpaJavaMappingsPackage.JAVA_BASIC__FETCH;
				case JpaCoreMappingsPackage.IBASIC__OPTIONAL :
					return JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL;
				case JpaCoreMappingsPackage.IBASIC__COLUMN :
					return JpaJavaMappingsPackage.JAVA_BASIC__COLUMN;
				case JpaCoreMappingsPackage.IBASIC__LOB :
					return JpaJavaMappingsPackage.JAVA_BASIC__LOB;
				case JpaCoreMappingsPackage.IBASIC__TEMPORAL :
					return JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL;
				case JpaCoreMappingsPackage.IBASIC__ENUMERATED :
					return JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED;
				default :
					return -1;
			}
		}
		if (baseClass == IJavaBasic.class) {
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
		result.append(" (fetch: ");
		result.append(fetch);
		result.append(", optional: ");
		result.append(optional);
		result.append(", lob: ");
		result.append(lob);
		result.append(", temporal: ");
		result.append(temporal);
		result.append(", enumerated: ");
		result.append(enumerated);
		result.append(')');
		return result.toString();
	}

	public String getKey() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setOptional(DefaultTrueBoolean.fromJavaAnnotationValue(this.optionalAdapter.getValue(astRoot)));
		this.setFetch(DefaultEagerFetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot)));
		this.getJavaColumn().updateFromJava(astRoot);
		this.setLob(this.lobAdapter.getValue(astRoot));
		this.updateTemporalFromJava(astRoot);
		this.updateEnumeratedFromJava(astRoot);
	}

	private JavaColumn getJavaColumn() {
		return (JavaColumn) this.column;
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
			this.setTemporal(TemporalType.NULL);
		}
		else {
			this.setTemporal(TemporalType.fromJavaAnnotationValue(this.temporalValueAdapter.getValue(astRoot)));
		}
	}

	private void updateEnumeratedFromJava(CompilationUnit astRoot) {
		if (this.enumeratedAnnotationAdapter.getAnnotation(astRoot) == null) {
			this.setEnumerated(EnumType.DEFAULT);
		}
		else {
			this.setEnumerated(EnumType.fromJavaAnnotationValue(this.enumeratedValueAdapter.getValue(astRoot)));
		}
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(DECLARATION_ANNOTATION_ADAPTER, JPA.BASIC__OPTIONAL, false, BooleanStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.BASIC__FETCH, false);
	}

	/**
	 * From the JPA spec, when the basic mapping applies:
	 * If the type of the attribute (field or property) is one of the following
	 * it must be mapped as @Basic:
	 *     primitive types
	 *     byte[]
	 *     Byte[]
	 *     char[]
	 *     Character[]
	 *     primitive wrappers
	 *     java.lang.String
	 *     java.math.BigInteger
	 *     java.math.BigDecimal
	 *     java.util.Date
	 *     java.util.Calendar
	 *     java.sql.Date
	 *     java.sql.Time
	 *     java.sql.Timestamp
	 *     enums
	 *     any other type that implements java.io.Serializable
	 */
	public static boolean signatureIsBasic(String signature, IType scope) {
		if (JDTTools.signatureIsPrimitive(signature)) {
			return true;
		}
		int arrayCount = Signature.getArrayCount(signature);
		if (arrayCount > 1) {
			return false; // multi-dimensional arrays are not supported
		}
		signature = Signature.getElementType(signature);
		String typeName = JDTTools.resolveSignature(signature, scope);
		if (typeName == null) {
			return false; // unable to resolve the type
		}
		if (arrayCount == 1) {
			return elementTypeIsValid(typeName);
		}
		if (typeIsPrimitiveWrapper(typeName)) {
			return true;
		}
		if (typeIsOtherSupportedType(typeName)) {
			return true;
		}
		IType type = findType(scope.getCompilationUnit().getJavaProject(), typeName);
		if (type == null) {
			return false; // type not found
		}
		if (typeIsEnum(type)) {
			return true;
		}
		if (typeImplementsSerializable(type)) {
			return true;
		}
		return false;
	}

	private static IType findType(IJavaProject javaProject, String typeName) {
		try {
			return javaProject.findType(typeName);
		}
		catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Return whether the specified type is a valid element type for
	 * a one-dimensional array:
	 *     byte
	 *     char
	 *     java.lang.Byte
	 *     java.lang.Character
	 */
	private static boolean elementTypeIsValid(String elementTypeName) {
		return CollectionTools.contains(VALID_ELEMENT_TYPE_NAMES, elementTypeName);
	}

	private static final String[] VALID_ELEMENT_TYPE_NAMES = {
		byte.class.getName(),
		char.class.getName(),
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName()
	};

	/**
	 * Return whether the specified type is a primitive wrapper.
	 */
	private static boolean typeIsPrimitiveWrapper(String typeName) {
		return CollectionTools.contains(PRIMITIVE_WRAPPER_TYPE_NAMES, typeName);
	}

	private static final String[] PRIMITIVE_WRAPPER_TYPE_NAMES = {
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName(),
		java.lang.Double.class.getName(),
		java.lang.Float.class.getName(),
		java.lang.Integer.class.getName(),
		java.lang.Long.class.getName(),
		java.lang.Short.class.getName(),
		java.lang.Boolean.class.getName(),
	};

	/**
	 * Return whether the specified type is among the various other types
	 * that default to a Basic mapping.
	 */
	private static boolean typeIsOtherSupportedType(String typeName) {
		return CollectionTools.contains(OTHER_SUPPORTED_TYPE_NAMES, typeName);
	}

	private static final String[] OTHER_SUPPORTED_TYPE_NAMES = {
		java.lang.String.class.getName(),
		java.math.BigInteger.class.getName(),
		java.math.BigDecimal.class.getName(),
		java.util.Date.class.getName(),
		java.util.Calendar.class.getName(),
		java.sql.Date.class.getName(),
		java.sql.Time.class.getName(),
		java.sql.Timestamp.class.getName(),
	};

	/**
	 * Return whether the specified type is an enum.
	 */
	private static boolean typeIsEnum(IType type) {
		try {
			return type.isEnum();
		}
		catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Return whether the specified type implements java.io.Serializable.
	 */
	// TODO should we be using IType.getSuperInterfaceTypeSignatures() instead?
	// would this be less of a performance hog??
	private static boolean typeImplementsSerializable(IType type) {
		ITypeHierarchy hierarchy = typeHierarchy(type);
		IType[] interfaces = hierarchy.getAllSuperInterfaces(type);
		for (int i = interfaces.length; i-- > 0;) {
			if (interfaces[i].getFullyQualifiedName().equals(SERIALIZABLE_TYPE_NAME)) {
				return true;
			}
		}
		return false;
	}

	private static final String SERIALIZABLE_TYPE_NAME = java.io.Serializable.class.getName();

	private static ITypeHierarchy typeHierarchy(IType type) {
		// TODO hmm... what to do about the working copy, probably shouldn't pass in null;
		// also, this looks like a performance hog, other ways to do this?
		try {
			return type.newSupertypeHierarchy((WorkingCopyOwner) null, null);
		}
		catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
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
	private static DeclarationAnnotationElementAdapter<String> buildTemporalValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(TEMPORAL_ADAPTER, JPA.TEMPORAL__VALUE, false);
	}

	private static DeclarationAnnotationElementAdapter<String> buildEnumeratedValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(ENUMERATED_ADAPTER, JPA.ENUMERATED__VALUE, false);
	}
}
