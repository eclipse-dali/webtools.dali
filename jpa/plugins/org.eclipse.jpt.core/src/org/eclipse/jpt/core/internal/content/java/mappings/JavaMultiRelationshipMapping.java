/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.INonOwningMapping;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Multi Relationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaMultiRelationshipMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class JavaMultiRelationshipMapping
	extends JavaRelationshipMapping implements IMultiRelationshipMapping
{
	/**
	 * The default value of the '{@link #getMappedBy() <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String MAPPED_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMappedBy() <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedBy()
	 * @generated
	 * @ordered
	 */
	protected String mappedBy = MAPPED_BY_EDEFAULT;

	/**
	 * The default value of the '{@link #getOrderBy() <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderBy()
	 * @generated
	 * @ordered
	 */
	protected static final String ORDER_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOrderBy() <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderBy()
	 * @generated
	 * @ordered
	 */
	protected String orderBy = ORDER_BY_EDEFAULT;

	/**
	 * The default value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultLazyFetchType FETCH_EDEFAULT = DefaultLazyFetchType.DEFAULT;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected DefaultLazyFetchType fetch = FETCH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getJoinTable() <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinTable()
	 * @generated
	 * @ordered
	 */
	protected IJoinTable joinTable;

	/**
	 * The default value of the '{@link #getMapKey() <em>Map Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKey()
	 * @generated
	 * @ordered
	 */
	protected static final String MAP_KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMapKey() <em>Map Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKey()
	 * @generated
	 * @ordered
	 */
	protected String mapKey = MAP_KEY_EDEFAULT;

	private final AnnotationElementAdapter<String> mappedByAdapter;

	/*
	 * The @OrderBy annotation is a bit wack:
	 *     - no annotation at all means "no ordering"
	 *     - an annotation with no 'value' means "order by ascending primary key"
	 *     - an annotation with a 'value' means "order by the settings in the 'value' string"
	 */
	private final AnnotationAdapter orderByAnnotationAdapter;

	private final AnnotationElementAdapter<String> orderByValueAdapter;

	public static final DeclarationAnnotationAdapter ORDER_BY_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ORDER_BY);

	private static final DeclarationAnnotationElementAdapter<String> ORDER_BY_VALUE_ADAPTER = buildOrderByValueAdapter();

	private final AnnotationAdapter mapKeyAnnotationAdapter;

	private final AnnotationElementAdapter<String> mapKeyNameAdapter;

	public static final DeclarationAnnotationAdapter MAP_KEY_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.MAP_KEY);

	private static final DeclarationAnnotationElementAdapter<String> MAP_KEY_NAME_ADAPTER = buildMapKeyNameAdapter();

	protected JavaMultiRelationshipMapping() {
		throw new UnsupportedOperationException("Use JavaMultiRelationshipMapping(Attribute) instead");
	}

	protected JavaMultiRelationshipMapping(Attribute attribute) {
		super(attribute);
		this.mappedByAdapter = this.buildAnnotationElementAdapter(this.mappedByAdapter());
		this.orderByAnnotationAdapter = new MemberAnnotationAdapter(attribute, ORDER_BY_ADAPTER);
		this.orderByValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, ORDER_BY_VALUE_ADAPTER);
		this.mapKeyAnnotationAdapter = new MemberAnnotationAdapter(attribute, MAP_KEY_ADAPTER);
		this.mapKeyNameAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, MAP_KEY_NAME_ADAPTER);
		this.joinTable = JpaJavaMappingsFactory.eINSTANCE.createJavaJoinTable(buildOwner(), attribute);
		((InternalEObject) this.joinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE, null, null);
	}

	/**
	 * return the Java adapter's 'mappedBy' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> mappedByAdapter();

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(INonOwningMapping.class)) {
			case JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY :
				this.mappedByAdapter.setValue((String) notification.getNewValue());
				break;
			default :
				break;
		}
		switch (notification.getFeatureID(IMultiRelationshipMapping.class)) {
			case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__ORDER_BY :
				String orderBy = (String) notification.getNewValue();
				if (orderBy == null) {
					this.orderByAnnotationAdapter.removeAnnotation();
				}
				else if ("".equals(orderBy)) {
					Annotation orderByAnnotation = this.orderByAnnotationAdapter.getAnnotation();
					if (orderByAnnotation != null) {
						// if the value is already "", then leave it alone (short circuit java change cycle)
						if (!"".equals(orderByValueAdapter.getValue())) {
							this.orderByValueAdapter.setValue(null);
						}
					}
					else {
						this.orderByAnnotationAdapter.newMarkerAnnotation();
					}
				}
				else {
					this.orderByValueAdapter.setValue(orderBy);
				}
				break;
			case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAP_KEY :
				String mk = (String) notification.getNewValue();
				if (mk == null) {
					this.mapKeyAnnotationAdapter.removeAnnotation();
				}
				else {
					this.mapKeyNameAdapter.setValue(mk);
				}
				break;
			case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH :
				this.getFetchAdapter().setValue(((DefaultLazyFetchType) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			default :
				break;
		}
	}

	private ITable.Owner buildOwner() {
		return new ITable.Owner() {
			public ITextRange validationTextRange() {
				return JavaMultiRelationshipMapping.this.validationTextRange();
			}

			public ITypeMapping getTypeMapping() {
				return JavaMultiRelationshipMapping.this.typeMapping();
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
		return JpaJavaMappingsPackage.Literals.JAVA_MULTI_RELATIONSHIP_MAPPING;
	}

	/**
	 * Returns the value of the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped By</em>' attribute.
	 * @see #setMappedBy(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getINonOwningMapping_MappedBy()
	 * @model
	 * @generated
	 */
	public String getMappedBy() {
		return mappedBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping#getMappedBy <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By</em>' attribute.
	 * @see #getMappedBy()
	 * @generated
	 */
	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = mappedBy;
		mappedBy = newMappedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY, oldMappedBy, mappedBy));
	}

	/**
	 * Returns the value of the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order By</em>' attribute.
	 * @see #setOrderBy(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIMultiRelationshipMapping_OrderBy()
	 * @model unique="false" ordered="false"
	 * @generated
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping#getOrderBy <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order By</em>' attribute.
	 * @see #getOrderBy()
	 * @generated
	 */
	public void setOrderBy(String newOrderBy) {
		String oldOrderBy = orderBy;
		orderBy = newOrderBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY, oldOrderBy, orderBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false" required="true" ordered="false"
	 * @generated NOT
	 */
	public boolean isNoOrdering() {
		return getOrderBy() == null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void setNoOrdering() {
		setOrderBy(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false" required="true" ordered="false"
	 * @generated NOT
	 */
	public boolean isOrderByPk() {
		return "".equals(getOrderBy());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void setOrderByPk() {
		setOrderBy("");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false" required="true" ordered="false"
	 * @generated NOT
	 */
	public boolean isCustomOrdering() {
		return !StringTools.stringIsEmpty(getOrderBy());
	}

	public ITextRange mappedByTextRange() {
		return this.elementTextRange(this.mappedByAdapter());
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
	 * @see #setFetch(DefaultLazyFetchType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIMultiRelationshipMapping_Fetch()
	 * @model
	 * @generated
	 */
	public DefaultLazyFetchType getFetch() {
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(DefaultLazyFetchType newFetch) {
		DefaultLazyFetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH, oldFetch, fetch));
	}

	/**
	 * Returns the value of the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Table</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIMultiRelationshipMapping_JoinTable()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public IJoinTable getJoinTable() {
		return joinTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinTable(IJoinTable newJoinTable, NotificationChain msgs) {
		IJoinTable oldJoinTable = joinTable;
		joinTable = newJoinTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public boolean isJoinTableSpecified() {
		return getJavaJoinTable().isSpecified();
	}

	/**
	 * Returns the value of the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key</em>' attribute.
	 * @see #setMapKey(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIMultiRelationshipMapping_MapKey()
	 * @model
	 * @generated
	 */
	public String getMapKey() {
		return mapKey;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping#getMapKey <em>Map Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key</em>' attribute.
	 * @see #getMapKey()
	 * @generated
	 */
	public void setMapKey(String newMapKey) {
		String oldMapKey = mapKey;
		mapKey = newMapKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY, oldMapKey, mapKey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE :
				return basicSetJoinTable(null, msgs);
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
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY :
				return getMappedBy();
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY :
				return getOrderBy();
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH :
				return getFetch();
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE :
				return getJoinTable();
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY :
				return getMapKey();
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
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY :
				setMappedBy((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY :
				setOrderBy((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH :
				setFetch((DefaultLazyFetchType) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY :
				setMapKey((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY :
				setMappedBy(MAPPED_BY_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY :
				setOrderBy(ORDER_BY_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH :
				setFetch(FETCH_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY :
				setMapKey(MAP_KEY_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY :
				return MAPPED_BY_EDEFAULT == null ? mappedBy != null : !MAPPED_BY_EDEFAULT.equals(mappedBy);
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY :
				return ORDER_BY_EDEFAULT == null ? orderBy != null : !ORDER_BY_EDEFAULT.equals(orderBy);
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH :
				return fetch != FETCH_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE :
				return joinTable != null;
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY :
				return MAP_KEY_EDEFAULT == null ? mapKey != null : !MAP_KEY_EDEFAULT.equals(mapKey);
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
		if (baseClass == INonOwningMapping.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY :
					return JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY;
				default :
					return -1;
			}
		}
		if (baseClass == IMultiRelationshipMapping.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY :
					return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__ORDER_BY;
				case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH :
					return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH;
				case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE :
					return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;
				case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY :
					return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAP_KEY;
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
		if (baseClass == INonOwningMapping.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY :
					return JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;
				default :
					return -1;
			}
		}
		if (baseClass == IMultiRelationshipMapping.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__ORDER_BY :
					return JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__ORDER_BY;
				case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH :
					return JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__FETCH;
				case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE :
					return JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;
				case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAP_KEY :
					return JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING__MAP_KEY;
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
		result.append(" (mappedBy: ");
		result.append(mappedBy);
		result.append(", orderBy: ");
		result.append(orderBy);
		result.append(", fetch: ");
		result.append(fetch);
		result.append(", mapKey: ");
		result.append(mapKey);
		result.append(')');
		return result.toString();
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		// TODO
		//		if (isOrderByPk()) {
		//			refreshDefaultOrderBy(defaultsContext);
		//		}
	}

	//primary key ordering when just the @OrderBy annotation is present
	protected void refreshDefaultOrderBy(DefaultsContext defaultsContext) {
		IEntity targetEntity = getResolvedTargetEntity();
		if (targetEntity != null) {
			setOrderBy(targetEntity.primaryKeyAttributeName() + " ASC");
		}
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setMappedBy(this.mappedByAdapter.getValue(astRoot));
		updateOrderByFromJava(astRoot);
		this.getJavaJoinTable().updateFromJava(astRoot);
		updateMapKeyFromJava(astRoot);
	}

	private void updateOrderByFromJava(CompilationUnit astRoot) {
		String orderBy = this.orderByValueAdapter.getValue(astRoot);
		if (orderBy == null) {
			if (orderByAnnotation(astRoot) == null) {
				this.setNoOrdering();
			}
			else {
				this.setOrderByPk();
			}
		}
		else if ("".equals(orderBy)) {
			this.setOrderByPk();
		}
		else {
			this.setOrderBy(orderBy);
		}
	}

	private Annotation orderByAnnotation(CompilationUnit astRoot) {
		return this.orderByAnnotationAdapter.getAnnotation(astRoot);
	}

	private void updateMapKeyFromJava(CompilationUnit astRoot) {
		this.setMapKey(this.mapKeyNameAdapter.getValue(astRoot));
	}

	private JavaJoinTable getJavaJoinTable() {
		return (JavaJoinTable) this.joinTable;
	}

	@Override
	protected void updateFetchFromJava(CompilationUnit astRoot) {
		setFetch(DefaultLazyFetchType.fromJavaAnnotationValue(this.getFetchAdapter().getValue(astRoot)));
	}

	private boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.mappedByAdapter(), pos, astRoot);
	}

	private boolean mapKeyNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(MAP_KEY_NAME_ADAPTER, pos, astRoot);
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}

	protected Iterator<String> candidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateMapKeyNames(), filter);
	}

	protected Iterator<String> quotedCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.quote(this.candidateMapKeyNames(filter));
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getJavaJoinTable().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mappedByTouches(pos, astRoot)) {
			return this.quotedCandidateMappedByAttributeNames(filter);
		}
		if (this.mapKeyNameTouches(pos, astRoot)) {
			return this.quotedCandidateMapKeyNames(filter);
		}
		return null;
	}

	/**
	 * extract the element type from the specified container signature and
	 * convert it into a reference entity type name;
	 * return null if the type is not a valid reference entity type (e.g. it's
	 * another container or an array or a primitive or other Basic type)
	 */
	@Override
	protected String javaDefaultTargetEntity(ITypeBinding typeBinding) {
		String typeName = super.javaDefaultTargetEntity(typeBinding);
		return typeNamedIsContainer(typeName) ? javaDefaultTargetEntityFromContainer(typeBinding) : null;
	}

	public static String javaDefaultTargetEntityFromContainer(ITypeBinding typeBinding) {
		ITypeBinding[] typeArguments = typeBinding.getTypeArguments();
		if (typeArguments.length != 1) {
			return null;
		}
		ITypeBinding elementTypeBinding = typeArguments[0];
		String elementTypeName = buildReferenceEntityTypeName(elementTypeBinding);
		return typeNamedIsContainer(elementTypeName) ? null : elementTypeName;
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildOrderByValueAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(ORDER_BY_ADAPTER, JPA.ORDER_BY__VALUE, false);
	}

	private static DeclarationAnnotationElementAdapter<String> buildMapKeyNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(MAP_KEY_ADAPTER, JPA.MAP_KEY__NAME, false);
	}
}
