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
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumArrayDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.StringExpressionConverter;
import org.eclipse.jpt.core.internal.mappings.ICascade;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.RelationshipMappingTools;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Relationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaRelationshipMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class JavaRelationshipMapping extends JavaAttributeMapping
	implements IRelationshipMapping
{
	private AnnotationElementAdapter<String> targetEntityAdapter;

	/**
	 * all the relationship mappings have a 'fetch' setting;
	 * but the 1:1 and m:1 mappings have a default of EAGER,
	 * while the 1:m and m:m mappings have a default of LAZY
	 */
	private AnnotationElementAdapter<String> fetchAdapter;

	/**
	 * The default value of the '{@link #getTargetEntity() <em>Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_ENTITY_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedTargetEntity() <em>Specified Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_TARGET_ENTITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedTargetEntity() <em>Specified Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected String specifiedTargetEntity = SPECIFIED_TARGET_ENTITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultTargetEntity() <em>Default Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_TARGET_ENTITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultTargetEntity() <em>Default Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected String defaultTargetEntity = DEFAULT_TARGET_ENTITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getResolvedTargetEntity() <em>Resolved Target Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResolvedTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected IEntity resolvedTargetEntity;

	/**
	 * The cached value of the '{@link #getCascade() <em>Cascade</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascade()
	 * @generated
	 * @ordered
	 */
	protected ICascade cascade;

	protected JavaRelationshipMapping() {
		throw new UnsupportedOperationException("Use JavaRelationshipMapping(Attribute) instead");
	}

	protected JavaRelationshipMapping(Attribute attribute) {
		super(attribute);
		this.targetEntityAdapter = this.buildAnnotationElementAdapter(this.targetEntityAdapter());
		this.fetchAdapter = this.buildAnnotationElementAdapter(this.fetchAdapter());
	}

	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.getAttribute(), daea);
	}

	/**
	 * return the Java adapter's 'targetEntity' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> targetEntityAdapter();

	/**
	 * return the Java adapter's 'cascade' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String[]> cascadeAdapter();

	/**
	 * return the Java adapter's 'fetch' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> fetchAdapter();

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IRelationshipMapping.class)) {
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				this.targetEntityAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE :
				if (notification.getNewValue() == null && notification.getOldValue() != null) {
					((JavaCascade) notification.getOldValue()).getCascadeAdapter().setValue(null);
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
		return JpaJavaMappingsPackage.Literals.JAVA_RELATIONSHIP_MAPPING;
	}

	/**
	 * Returns the value of the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Entity</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIRelationshipMapping_TargetEntity()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getTargetEntity() {
		return (this.getSpecifiedTargetEntity() == null) ? getDefaultTargetEntity() : this.getSpecifiedTargetEntity();
	}

	/**
	 * Returns the value of the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Target Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Target Entity</em>' attribute.
	 * @see #setSpecifiedTargetEntity(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIRelationshipMapping_SpecifiedTargetEntity()
	 * @model
	 * @generated
	 */
	public String getSpecifiedTargetEntity() {
		return specifiedTargetEntity;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping#getSpecifiedTargetEntity <em>Specified Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Target Entity</em>' attribute.
	 * @see #getSpecifiedTargetEntity()
	 * @generated
	 */
	public void setSpecifiedTargetEntity(String newSpecifiedTargetEntity) {
		String oldSpecifiedTargetEntity = specifiedTargetEntity;
		specifiedTargetEntity = newSpecifiedTargetEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY, oldSpecifiedTargetEntity, specifiedTargetEntity));
	}

	/**
	 * Returns the value of the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Target Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Target Entity</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIRelationshipMapping_DefaultTargetEntity()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultTargetEntity() {
		return defaultTargetEntity;
	}

	protected void setDefaultTargetEntity(String newDefaultTargetEntity) {
		String oldDefaultTargetEntity = this.defaultTargetEntity;
		this.defaultTargetEntity = newDefaultTargetEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY, oldDefaultTargetEntity, this.defaultTargetEntity));
	}

	/**
	 * Returns the value of the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resolved Target Entity</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resolved Target Entity</em>' reference.
	 * @see #setResolvedTargetEntity(IEntity)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIRelationshipMapping_ResolvedTargetEntity()
	 * @model
	 * @generated
	 */
	public IEntity getResolvedTargetEntity() {
		if (resolvedTargetEntity != null && resolvedTargetEntity.eIsProxy()) {
			InternalEObject oldResolvedTargetEntity = (InternalEObject) resolvedTargetEntity;
			resolvedTargetEntity = (IEntity) eResolveProxy(oldResolvedTargetEntity);
			if (resolvedTargetEntity != oldResolvedTargetEntity) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY, oldResolvedTargetEntity, resolvedTargetEntity));
			}
		}
		return resolvedTargetEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEntity basicGetResolvedTargetEntity() {
		return resolvedTargetEntity;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping#getResolvedTargetEntity <em>Resolved Target Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resolved Target Entity</em>' reference.
	 * @see #getResolvedTargetEntity()
	 * @generated
	 */
	public void setResolvedTargetEntity(IEntity newResolvedTargetEntity) {
		IEntity oldResolvedTargetEntity = resolvedTargetEntity;
		resolvedTargetEntity = newResolvedTargetEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY, oldResolvedTargetEntity, resolvedTargetEntity));
	}

	/**
	 * Returns the value of the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade</em>' containment reference.
	 * @see #setCascade(ICascade)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIRelationshipMapping_Cascade()
	 * @model containment="true"
	 * @generated
	 */
	public ICascade getCascade() {
		return cascade;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascade(ICascade newCascade, NotificationChain msgs) {
		ICascade oldCascade = cascade;
		cascade = newCascade;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE, oldCascade, newCascade);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping#getCascade <em>Cascade</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade</em>' containment reference.
	 * @see #getCascade()
	 * @generated
	 */
	public void setCascade(ICascade newCascade) {
		if (newCascade != cascade) {
			NotificationChain msgs = null;
			if (cascade != null)
				msgs = ((InternalEObject) cascade).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE, null, msgs);
			if (newCascade != null)
				msgs = ((InternalEObject) newCascade).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE, null, msgs);
			msgs = basicSetCascade(newCascade, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE, newCascade, newCascade));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE :
				return basicSetCascade(null, msgs);
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
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__TARGET_ENTITY :
				return getTargetEntity();
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				return getSpecifiedTargetEntity();
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY :
				return getDefaultTargetEntity();
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
				if (resolve)
					return getResolvedTargetEntity();
				return basicGetResolvedTargetEntity();
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE :
				return getCascade();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				setSpecifiedTargetEntity((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
				setResolvedTargetEntity((IEntity) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE :
				setCascade((ICascade) newValue);
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
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				setSpecifiedTargetEntity(SPECIFIED_TARGET_ENTITY_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
				setResolvedTargetEntity((IEntity) null);
				return;
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE :
				setCascade((ICascade) null);
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
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__TARGET_ENTITY :
				return TARGET_ENTITY_EDEFAULT == null ? getTargetEntity() != null : !TARGET_ENTITY_EDEFAULT.equals(getTargetEntity());
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				return SPECIFIED_TARGET_ENTITY_EDEFAULT == null ? specifiedTargetEntity != null : !SPECIFIED_TARGET_ENTITY_EDEFAULT.equals(specifiedTargetEntity);
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY :
				return DEFAULT_TARGET_ENTITY_EDEFAULT == null ? defaultTargetEntity != null : !DEFAULT_TARGET_ENTITY_EDEFAULT.equals(defaultTargetEntity);
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
				return resolvedTargetEntity != null;
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE :
				return cascade != null;
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
		if (baseClass == IRelationshipMapping.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__TARGET_ENTITY :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__TARGET_ENTITY;
				case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;
				case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;
				case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;
				case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__CASCADE;
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
		if (baseClass == IRelationshipMapping.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__TARGET_ENTITY :
					return JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__TARGET_ENTITY;
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
					return JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY :
					return JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
					return JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__CASCADE :
					return JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING__CASCADE;
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
		result.append(" (specifiedTargetEntity: ");
		result.append(specifiedTargetEntity);
		result.append(", defaultTargetEntity: ");
		result.append(defaultTargetEntity);
		result.append(')');
		return result.toString();
	}

	public String fullyQualifiedTargetEntity() {
		return (getTargetEntity() == null) ? null : JDTTools.resolve(getTargetEntity(), this.jdtType());
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setDefaultTargetEntity(this.javaDefaultTargetEntity());
		setSpecifiedTargetEntity(this.targetEntityAdapter.getValue(astRoot));
		this.updateFetchFromJava(astRoot);
		if (this.cascade != null) {
			((JavaCascade) this.cascade).updateFromJava(astRoot);
		}
		else if (cascadeAdapter().expression(getAttribute().modifiedDeclaration()) != null) {
			setCascade(createCascade());
			((JavaCascade) this.cascade).updateFromJava(astRoot);
		}
	}

	/**
	 * delegate to subclasses because there are different 'fetch'
	 * defaults across the subclasses
	 */
	protected abstract void updateFetchFromJava(CompilationUnit astRoot);

	/**
	 * the default 'targetEntity' is calculated from the attribute type;
	 * return null if the attribute type cannot possibly be an entity
	 */
	protected String javaDefaultTargetEntity() {
		return this.javaDefaultTargetEntity(this.getAttribute().typeSignature());
	}

	protected String javaDefaultTargetEntity(String signature) {
		return buildReferenceEntityTypeName(signature, jdtType());
	}

	// TODO Embeddable???
	public static String buildReferenceEntityTypeName(String signature, IType jdtType) {
		if (Signature.getArrayCount(signature) > 0) {
			return null; // arrays cannot be entities
		}
		return JDTTools.resolve(Signature.toString(signature), jdtType);
	}

	public IEntity getEntity() {
		ITypeMapping typeMapping = typeMapping();
		if (typeMapping instanceof IEntity) {
			return (IEntity) typeMapping;
		}
		return null;
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		String targetEntityName = fullyQualifiedTargetEntity();
		if (targetEntityName != null) {
			IPersistentType persistentType = defaultsContext.persistentType(targetEntityName);
			if (persistentType != null) {
				if (persistentType.getMapping() instanceof IEntity) {
					setResolvedTargetEntity((IEntity) persistentType.getMapping());
					return;
				}
			}
		}
		setResolvedTargetEntity(null);
	}

	public Iterator<String> allTargetEntityAttributeNames() {
		IEntity targetEntity = this.getResolvedTargetEntity();
		return (targetEntity == null) ? EmptyIterator.<String> instance() : targetEntity.getPersistentType().allAttributeNames();
	}

	public Iterator<String> candidateMappedByAttributeNames() {
		return this.allTargetEntityAttributeNames();
	}

	protected Iterator<String> candidateMappedByAttributeNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateMappedByAttributeNames(), filter);
	}

	protected Iterator<String> quotedCandidateMappedByAttributeNames(Filter<String> filter) {
		return StringTools.quote(this.candidateMappedByAttributeNames(filter));
	}

	public ICascade createCascade() {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaCascade(getAttribute(), cascadeAdapter());
	}

	// ********** convenience methods **********
	protected AnnotationElementAdapter<String> getFetchAdapter() {
		return this.fetchAdapter;
	}

	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		// TODO what about QualifiedType?
		return buildAnnotationElementAdapter(annotationAdapter, elementName, SimpleTypeStringExpressionConverter.instance());
	}

	protected static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildAnnotationElementAdapter(annotationAdapter, elementName, StringExpressionConverter.instance());
	}

	protected static <T extends Expression> DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String, T> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String, T>(annotationAdapter, elementName, false, converter);
	}

	protected static DeclarationAnnotationElementAdapter<String> buildEnumAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}

	protected static DeclarationAnnotationElementAdapter<String[]> buildEnumArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumArrayDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}

	public boolean targetEntityIsValid(String targetEntity) {
		return RelationshipMappingTools.targetEntityIsValid(targetEntity);
	}

	/**
	 * return whether the specified non-array type is one of the container
	 * types allowed by the JPA spec
	 */
	protected static boolean typeNamedIsContainer(String typeName) {
		return CollectionTools.contains(CONTAINER_TYPE_NAMES, typeName);
	}

	private static final String[] CONTAINER_TYPE_NAMES = {
		java.util.Collection.class.getName(),
		java.util.Set.class.getName(),
		java.util.List.class.getName(),
		java.util.Map.class.getName()
	};
} // JavaRelationshipMapping
