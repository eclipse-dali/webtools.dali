/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Iterator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.ICascade;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.RelationshipMappingTools;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Relationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlRelationshipMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class XmlRelationshipMapping extends XmlAttributeMapping
	implements IRelationshipMapping
{
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlRelationshipMapping() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_RELATIONSHIP_MAPPING;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIRelationshipMapping_TargetEntity()
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIRelationshipMapping_SpecifiedTargetEntity()
	 * @model
	 * @generated
	 */
	public String getSpecifiedTargetEntity() {
		return specifiedTargetEntity;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping#getSpecifiedTargetEntity <em>Specified Target Entity</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY, oldSpecifiedTargetEntity, specifiedTargetEntity));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIRelationshipMapping_DefaultTargetEntity()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultTargetEntity() {
		return defaultTargetEntity;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIRelationshipMapping_ResolvedTargetEntity()
	 * @model
	 * @generated
	 */
	public IEntity getResolvedTargetEntity() {
		if (resolvedTargetEntity != null && resolvedTargetEntity.eIsProxy()) {
			InternalEObject oldResolvedTargetEntity = (InternalEObject) resolvedTargetEntity;
			resolvedTargetEntity = (IEntity) eResolveProxy(oldResolvedTargetEntity);
			if (resolvedTargetEntity != oldResolvedTargetEntity) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY, oldResolvedTargetEntity, resolvedTargetEntity));
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
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping#getResolvedTargetEntity <em>Resolved Target Entity</em>}' reference.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY, oldResolvedTargetEntity, resolvedTargetEntity));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIRelationshipMapping_Cascade()
	 * @model containment="true"
	 * @generated
	 */
	public ICascade getCascade() {
		return cascade;
	}

	public ICascade createCascade() {
		return OrmFactory.eINSTANCE.createXmlCascade();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE, oldCascade, newCascade);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping#getCascade <em>Cascade</em>}' containment reference.
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
				msgs = ((InternalEObject) cascade).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE, null, msgs);
			if (newCascade != null)
				msgs = ((InternalEObject) newCascade).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE, null, msgs);
			msgs = basicSetCascade(newCascade, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE, newCascade, newCascade));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE :
				return basicSetCascade(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	//TODO should we allow setting through the ecore, that would make this method
	//public and part of the ITable api.  only the model needs to be setting the default,
	//but the ui needs to be listening for changes to the default.
	protected void setDefaultTargetEntity(String newDefaultTargetEntity) {
		String oldDefaultTargetEntity = this.defaultTargetEntity;
		this.defaultTargetEntity = newDefaultTargetEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY, oldDefaultTargetEntity, this.defaultTargetEntity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_RELATIONSHIP_MAPPING__TARGET_ENTITY :
				return getTargetEntity();
			case OrmPackage.XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				return getSpecifiedTargetEntity();
			case OrmPackage.XML_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY :
				return getDefaultTargetEntity();
			case OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
				if (resolve)
					return getResolvedTargetEntity();
				return basicGetResolvedTargetEntity();
			case OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE :
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
			case OrmPackage.XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				setSpecifiedTargetEntity((String) newValue);
				return;
			case OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
				setResolvedTargetEntity((IEntity) newValue);
				return;
			case OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE :
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
			case OrmPackage.XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				setSpecifiedTargetEntity(SPECIFIED_TARGET_ENTITY_EDEFAULT);
				return;
			case OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
				setResolvedTargetEntity((IEntity) null);
				return;
			case OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE :
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
			case OrmPackage.XML_RELATIONSHIP_MAPPING__TARGET_ENTITY :
				return TARGET_ENTITY_EDEFAULT == null ? getTargetEntity() != null : !TARGET_ENTITY_EDEFAULT.equals(getTargetEntity());
			case OrmPackage.XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
				return SPECIFIED_TARGET_ENTITY_EDEFAULT == null ? specifiedTargetEntity != null : !SPECIFIED_TARGET_ENTITY_EDEFAULT.equals(specifiedTargetEntity);
			case OrmPackage.XML_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY :
				return DEFAULT_TARGET_ENTITY_EDEFAULT == null ? defaultTargetEntity != null : !DEFAULT_TARGET_ENTITY_EDEFAULT.equals(defaultTargetEntity);
			case OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
				return resolvedTargetEntity != null;
			case OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE :
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
				case OrmPackage.XML_RELATIONSHIP_MAPPING__TARGET_ENTITY :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__TARGET_ENTITY;
				case OrmPackage.XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;
				case OrmPackage.XML_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;
				case OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
					return JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;
				case OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE :
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
					return OrmPackage.XML_RELATIONSHIP_MAPPING__TARGET_ENTITY;
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY :
					return OrmPackage.XML_RELATIONSHIP_MAPPING__SPECIFIED_TARGET_ENTITY;
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY :
					return OrmPackage.XML_RELATIONSHIP_MAPPING__DEFAULT_TARGET_ENTITY;
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY :
					return OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY;
				case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__CASCADE :
					return OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE;
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

	@Override
	public void initializeFromXmlRelationshipMapping(XmlRelationshipMapping oldMapping) {
		super.initializeFromXmlRelationshipMapping(oldMapping);
		setSpecifiedTargetEntity(oldMapping.getSpecifiedTargetEntity());
	}

	public boolean targetEntityIsValid(String targetEntity) {
		return RelationshipMappingTools.targetEntityIsValid(targetEntity);
	}

	public IEntity getEntity() {
		ITypeMapping typeMapping = getPersistentType().getMapping();
		if (typeMapping instanceof IEntity) {
			return (IEntity) typeMapping;
		}
		return null;
	}

	public String fullyQualifiedTargetEntity(CompilationUnit astRoot) {
		if (getTargetEntity() == null) {
			return null;
		}
		if (targetEntityIncludesPackage()) {
			return getTargetEntity();
		}
		String package_ = getPersistentType().getMapping().getEntityMappings().getPackage();
		if (package_ != null) {
			return package_ + '.' + getTargetEntity();
		}
		return getTargetEntity();
	}

	private boolean targetEntityIncludesPackage() {
		return getTargetEntity().lastIndexOf('.') != -1;
	}

	public Iterator<String> allTargetEntityAttributeNames() {
		IEntity targetEntity = this.getResolvedTargetEntity();
		return (targetEntity == null) ? EmptyIterator.<String> instance() : targetEntity.getPersistentType().allAttributeNames();
	}

	public Iterator<String> candidateMappedByAttributeNames() {
		return this.allTargetEntityAttributeNames();
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		setDefaultTargetEntity((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TARGET_ENTITY_KEY));
		String targetEntity = fullyQualifiedTargetEntity(defaultsContext.astRoot());
		if (targetEntity != null) {
			IPersistentType persistentType = defaultsContext.persistentType(targetEntity);
			if (persistentType != null) {
				if (persistentType.getMapping() instanceof IEntity) {
					setResolvedTargetEntity((IEntity) persistentType.getMapping());
					return;
				}
			}
		}
		setResolvedTargetEntity(null);
	}

	/**
	 * the default 'targetEntity' is calculated from the attribute type;
	 * return null if the attribute type cannot possibly be an entity
	 */
	public String javaDefaultTargetEntity(CompilationUnit astRoot) {
		ITypeBinding typeBinding = this.getPersistentAttribute().getAttribute().typeBinding(astRoot);
		if (typeBinding != null) {
			return this.javaDefaultTargetEntity(typeBinding);
		}
		return null;
	}

	protected String javaDefaultTargetEntity(ITypeBinding typeBinding) {
		return buildReferenceEntityTypeName(typeBinding);
	}

	protected String buildReferenceEntityTypeName(ITypeBinding typeBinding) {
		return JavaRelationshipMapping.buildReferenceEntityTypeName(typeBinding);
	}
}
