/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddableProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEntityProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaId;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.AttributeAnnotationTools;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.FieldAttribute;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.MethodAttribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getMapping <em>Mapping</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getAccess <em>Access</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentType()
 * @model kind="class"
 * @generated
 */
public class JavaPersistentType extends JavaEObject implements IPersistentType
{
	/**
	 * The default value of the '{@link #getMappingKey() <em>Mapping Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingKey()
	 * @generated
	 * @ordered
	 */
	protected static final String MAPPING_KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMappingKey() <em>Mapping Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingKey()
	 * @generated
	 * @ordered
	 */
	protected String mappingKey = MAPPING_KEY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMapping() <em>Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapping()
	 * @generated
	 * @ordered
	 */
	protected IJavaTypeMapping mapping;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<JavaPersistentAttribute> attributes;

	/**
	 * The default value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected static final AccessType ACCESS_EDEFAULT = AccessType.DEFAULT;

	/**
	 * The cached value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected AccessType access = ACCESS_EDEFAULT;

	private Type type;

	private IJavaTypeMappingProvider[] typeMappingProviders;

	private DeclarationAnnotationAdapter[] attributeMappingAnnotationAdapters;

	/**
	 * Store the parentPersistentType during default calculation.  This will
	 * be the first persisentType found in the hierarchy, the JPA spec allows
	 * for non-persistent types to be part of the hierarchy.
	 * Example:
	 * 
	 * @Entity public abstract class Model {}
	 * 
	 * public abstract class Animal extends Model {}
	 * 
	 * @Entity public class Cat extends Animal {}
	 * 
	 * If this is the Cat JavaPersistentType then parentPersistentType is the Model JavaPersistentType
	 * The parentPersistentType could be found in java or xml.
	 */
	private IPersistentType parentPersistentType;

	protected JavaPersistentType() {
		super();
		this.typeMappingProviders = this.buildTypeMappingProviders();
		this.attributeMappingAnnotationAdapters = this.buildAttributeMappingAnnotationAdapters();
	}

	private IJavaTypeMappingProvider[] buildTypeMappingProviders() {
		ArrayList<IJavaTypeMappingProvider> providers = new ArrayList<IJavaTypeMappingProvider>();
		this.addTypeMappingProvidersTo(providers);
		return providers.toArray(new IJavaTypeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different type mapping providers.
	 * The default includes the JPA spec-defined type mappings of 
	 * Entity, MappedSuperclass, and Embeddable
	 */
	protected void addTypeMappingProvidersTo(Collection<IJavaTypeMappingProvider> providers) {
		providers.add(JavaNullTypeMappingProvider.instance());
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
		providers.add(JavaEmbeddableProvider.instance());
	}

	private DeclarationAnnotationAdapter[] buildAttributeMappingAnnotationAdapters() {
		ArrayList<DeclarationAnnotationAdapter> adapters = new ArrayList<DeclarationAnnotationAdapter>();
		this.addAttributeMappingAnnotationAdaptersTo(adapters);
		return adapters.toArray(new DeclarationAnnotationAdapter[adapters.size()]);
	}

	/**
	 * Override this to specify more or different attribute mapping annotation
	 * adapters. The default includes the JPA spec-defined attribute mapping
	 * annotations.
	 */
	protected void addAttributeMappingAnnotationAdaptersTo(Collection<DeclarationAnnotationAdapter> adapters) {
		adapters.add(JavaAssociationOverride.SINGLE_DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaAssociationOverride.MULTIPLE_DECLARATION_ANNOTATION_ADAPTER); // AssociationOverrides
		adapters.add(JavaAttributeOverride.SINGLE_DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaAttributeOverride.MULTIPLE_DECLARATION_ANNOTATION_ADAPTER); // AttributeOverrides
		adapters.add(JavaBasic.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaColumn.MAPPING_DECLARATION_ANNOTATION_ADAPTER); // standalone Column
		adapters.add(JavaEmbedded.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaEmbeddedId.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaBasic.ENUMERATED_ADAPTER);
		adapters.add(JavaGeneratedValue.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaId.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaJoinColumn.SINGLE_DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaJoinColumn.MULTIPLE_DECLARATION_ANNOTATION_ADAPTER); // JoinColumns
		adapters.add(JavaJoinTable.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaBasic.LOB_ADAPTER);
		adapters.add(JavaManyToMany.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaManyToOne.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaMultiRelationshipMapping.ORDER_BY_ADAPTER);
		adapters.add(JavaMultiRelationshipMapping.MAP_KEY_ADAPTER);
		adapters.add(JavaOneToMany.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaOneToOne.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaPrimaryKeyJoinColumn.MULTIPLE_DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaPrimaryKeyJoinColumn.SINGLE_DECLARATION_ANNOTATION_ADAPTER); // PrimaryKeyJoinColumns
		adapters.add(JavaSequenceGenerator.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaTableGenerator.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaBasic.TEMPORAL_ADAPTER);
		adapters.add(JavaTransient.DECLARATION_ANNOTATION_ADAPTER);
		adapters.add(JavaVersion.DECLARATION_ANNOTATION_ADAPTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaPackage.Literals.JAVA_PERSISTENT_TYPE;
	}

	/**
	 * Returns the value of the '<em><b>Mapping Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Key</em>' attribute.
	 * @see #setMappingKey(String)
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getIPersistentType_MappingKey()
	 * @model required="true"
	 * @generated
	 */
	public String getMappingKey() {
		return mappingKey;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getMappingKey <em>Mapping Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping Key</em>' attribute.
	 * @see #getMappingKey()
	 * @generated
	 */
	public void setMappingKeyGen(String newMappingKey) {
		String oldMappingKey = mappingKey;
		mappingKey = newMappingKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING_KEY, oldMappingKey, mappingKey));
	}

	/**
	 * This is called by the UI, it should not be called when updating
	 * the persistence model from the java model.
	 */
	public void setMappingKey(String newMappingKey) {
		if (newMappingKey == this.mappingKey) {
			return;
		}
		setMapping(buildJavaTypeMapping(newMappingKey));
		setMappingKeyGen(newMappingKey);
	}

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' containment reference.
	 * @see #setMapping(IJavaTypeMapping)
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentType_Mapping()
	 * @model containment="true" required="true"
	 * @generated
	 */
	public IJavaTypeMapping getMapping() {
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapping(IJavaTypeMapping newMapping, NotificationChain msgs) {
		IJavaTypeMapping oldMapping = mapping;
		mapping = newMapping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING, oldMapping, newMapping);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType#getMapping <em>Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping</em>' containment reference.
	 * @see #getMapping()
	 * @generated
	 */
	public void setMappingGen(IJavaTypeMapping newMapping) {
		if (newMapping != mapping) {
			NotificationChain msgs = null;
			if (mapping != null)
				msgs = ((InternalEObject) mapping).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING, null, msgs);
			if (newMapping != null)
				msgs = ((InternalEObject) newMapping).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING, null, msgs);
			msgs = basicSetMapping(newMapping, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING, newMapping, newMapping));
	}

	/**
	 * This should not be called when updating the persistence model
	 * from the java model, it should only be called when going in the 
	 * other direction.  This will update the java code appropriately
	 * to the change in mapping in the persistence model.
	 */
	public void setMapping(IJavaTypeMapping newMapping) {
		this.type.removeAnnotation(this.annotationAdapterForTypeMappingKey(this.mapping.getKey()));
		this.type.newMarkerAnnotation(this.annotationAdapterForTypeMappingKey(newMapping.getKey()));
		this.setMappingGen(newMapping);
	}

	private DeclarationAnnotationAdapter annotationAdapterForTypeMappingKey(String typeMappingKey) {
		return this.typeMappingProviderFor(typeMappingKey).declarationAnnotationAdapter();
	}

	private IJavaTypeMappingProvider typeMappingProviderFor(String typeMappingKey) {
		for (IJavaTypeMappingProvider provider : this.typeMappingProviders) {
			if (provider.key() == typeMappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported type mapping key: " + typeMappingKey);
	}

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentType_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<JavaPersistentAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<JavaPersistentAttribute>(JavaPersistentAttribute.class, this, JpaJavaPackage.JAVA_PERSISTENT_TYPE__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentType_Access()
	 * @model changeable="false"
	 * @generated
	 */
	public AccessType getAccess() {
		return access;
	}

	private void setAccess(AccessType newAccess) {
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_TYPE__ACCESS, oldAccess, access));
	}

	/* @see IJpaContentNode#getId() */
	public Object getId() {
		return IJavaContentNodes.PERSISTENT_TYPE_ID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING :
				return basicSetMapping(null, msgs);
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__ATTRIBUTES :
				return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
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
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING_KEY :
				return getMappingKey();
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING :
				return getMapping();
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__ATTRIBUTES :
				return getAttributes();
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__ACCESS :
				return getAccess();
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
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING_KEY :
				setMappingKey((String) newValue);
				return;
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING :
				setMapping((IJavaTypeMapping) newValue);
				return;
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__ATTRIBUTES :
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends JavaPersistentAttribute>) newValue);
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
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING_KEY :
				setMappingKey(MAPPING_KEY_EDEFAULT);
				return;
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING :
				setMapping((IJavaTypeMapping) null);
				return;
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__ATTRIBUTES :
				getAttributes().clear();
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
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING_KEY :
				return MAPPING_KEY_EDEFAULT == null ? mappingKey != null : !MAPPING_KEY_EDEFAULT.equals(mappingKey);
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING :
				return mapping != null;
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__ATTRIBUTES :
				return attributes != null && !attributes.isEmpty();
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE__ACCESS :
				return access != ACCESS_EDEFAULT;
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
		if (baseClass == IJpaContentNode.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IPersistentType.class) {
			switch (derivedFeatureID) {
				case JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING_KEY :
					return JpaCorePackage.IPERSISTENT_TYPE__MAPPING_KEY;
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
		if (baseClass == IJpaContentNode.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IPersistentType.class) {
			switch (baseFeatureID) {
				case JpaCorePackage.IPERSISTENT_TYPE__MAPPING_KEY :
					return JpaJavaPackage.JAVA_PERSISTENT_TYPE__MAPPING_KEY;
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
		result.append(" (mappingKey: ");
		result.append(mappingKey);
		result.append(", access: ");
		result.append(access);
		result.append(')');
		return result.toString();
	}

	public void setJdtType(IType iType, CompilationUnit astRoot) {
		this.type = new Type(iType);
		this.setAccess(this.javaAccessType(astRoot));
		this.createAndSetPersistentTypeMappingFromJava(this.javaTypeMappingKey(astRoot));
	}

	public JavaPersistentAttribute addJavaPersistentAttribute(IMember jdtMember) {
		JavaPersistentAttribute persistentAttribute = createJavaPersistentAttribute(jdtMember);
		getAttributes().add(persistentAttribute);
		return persistentAttribute;
	}

	public JavaPersistentAttribute createJavaPersistentAttribute(IMember member) {
		Attribute attribute = null;
		if (member instanceof IField) {
			attribute = new FieldAttribute((IField) member);
		}
		else if (member instanceof IMethod) {
			attribute = new MethodAttribute((IMethod) member);
		}
		else {
			throw new IllegalArgumentException();
		}
		return JpaJavaFactory.eINSTANCE.createJavaPersistentAttribute(attribute);
	}

	private void createAndSetPersistentTypeMappingFromJava(String key) {
		setMappingGen(buildJavaTypeMapping(key));
		setMappingKeyGen(key);
	}

	private IJavaTypeMapping buildJavaTypeMapping(String key) {
		return this.typeMappingProviderFor(key).buildMapping(this.type);
	}

	public Type getType() {
		return this.type;
	}

	public String fullyQualifiedTypeName() {
		return jdtType().getFullyQualifiedName();
	}

	public IType jdtType() {
		return getType().getJdtMember();
	}

	/**
	 * This implementation of IPersistentType#findJdtType() will
	 * *always* find its type
	 */
	public IType findJdtType() {
		return jdtType();
	}

	public boolean isFor(IType member) {
		return this.type.wraps(member);
	}

	protected void updateFromJava(CompilationUnit astRoot) {
		this.setAccess(this.javaAccessType(astRoot));
		String jpaKey = this.getMapping().getKey();
		String javaKey = this.javaTypeMappingKey(astRoot);
		if (jpaKey != javaKey) {
			this.createAndSetPersistentTypeMappingFromJava(javaKey);
		}
		this.getMapping().updateFromJava(astRoot);
		this.updatePersistentAttributes(astRoot);
	}

	private void updatePersistentAttributes(CompilationUnit astRoot) {
		List<JavaPersistentAttribute> persistentAttributesToRemove = new ArrayList<JavaPersistentAttribute>(getAttributes());
		if (getAccess() == AccessType.FIELD) {
			updatePersistentFields(astRoot, persistentAttributesToRemove);
		}
		else if (getAccess() == AccessType.PROPERTY) {
			updatePersistentProperties(astRoot, persistentAttributesToRemove);
		}
		getAttributes().removeAll(persistentAttributesToRemove);
	}

	private void updatePersistentFields(CompilationUnit astRoot, List<JavaPersistentAttribute> persistentAttributesToRemove) {
		for (IField field : this.jdtPersistableFields()) {
			JavaPersistentAttribute persistentAttribute = persistentAttributeFor(field);
			if (persistentAttribute == null) {
				persistentAttribute = addJavaPersistentAttribute(field);
			}
			else {
				persistentAttributesToRemove.remove(persistentAttribute);
			}
			persistentAttribute.updateFromJava(astRoot);
		}
	}

	private void updatePersistentProperties(CompilationUnit astRoot, List<JavaPersistentAttribute> persistentAttributesToRemove) {
		for (IMethod method : this.jdtPersistableProperties()) {
			JavaPersistentAttribute persistentAttribute = persistentAttributeFor(method);
			if (persistentAttribute == null) {
				addJavaPersistentAttribute(method);
			}
			else {
				persistentAttributesToRemove.remove(persistentAttribute);
				persistentAttribute.updateFromJava(astRoot);
			}
		}
	}

	private IField[] jdtPersistableFields() {
		return AttributeAnnotationTools.persistableFields(jdtType());
	}

	private IMethod[] jdtPersistableProperties() {
		return AttributeAnnotationTools.persistablePropertyGetters(jdtType());
	}

	private String javaTypeMappingKey(CompilationUnit astRoot) {
		for (IJavaTypeMappingProvider provider : this.typeMappingProviders) {
			if (this.type.containsAnnotation(provider.declarationAnnotationAdapter(), astRoot)) {
				return provider.key();
			}
		}
		return null;
	}

	protected Iterator<JavaPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<JavaPersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(Object o) {
				return attributeName.equals(((JavaPersistentAttribute) o).getName());
			}
		};
	}

	public JavaPersistentAttribute attributeNamed(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

	public IPersistentAttribute resolveAttribute(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		if (stream.hasNext()) {
			JavaPersistentAttribute attribute = stream.next();
			return (stream.hasNext()) ? null /*more than one*/: attribute;
		}
		return (parentPersistentType() == null) ? null : parentPersistentType().resolveAttribute(attributeName);
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		Iterator<String> values = this.mapping.candidateValuesFor(pos, filter, astRoot);
		if (values != null) {
			return values;
		}
		for (Iterator<JavaPersistentAttribute> stream = attributes(); stream.hasNext();) {
			values = stream.next().candidateValuesFor(pos, filter, astRoot);
			if (values != null) {
				return values;
			}
		}
		return null;
	}

	public IJpaContentNode contentNodeAt(int offset) {
		for (Iterator<JavaPersistentAttribute> i = attributes(); i.hasNext();) {
			JavaPersistentAttribute persistentAttribute = i.next();
			if (persistentAttribute.includes(offset)) {
				return persistentAttribute;
			}
		}
		return null;
	}

	public boolean includes(int offset) {
		return this.fullTextRange().includes(offset);
	}

	public ITextRange fullTextRange() {
		return this.type.textRange();
	}

	public ITextRange validationTextRange() {
		return this.selectionTextRange();
	}

	public ITextRange selectionTextRange() {
		return this.type.nameTextRange();
	}

	public ITextRange getTextRange() {
		return type.textRange();
	}

	private JavaPersistentAttribute persistentAttributeFor(IMember member) {
		for (Iterator<JavaPersistentAttribute> i = attributes(); i.hasNext();) {
			JavaPersistentAttribute attribute = i.next();
			if (attribute.isFor(member)) {
				return attribute;
			}
		}
		return null;
	}

	//TODO CloneIterator
	public Iterator<JavaPersistentAttribute> attributes() {
		return new CloneIterator<JavaPersistentAttribute>(getAttributes());
	}

	public Iterator<String> attributeNames() {
		return this.attributeNames(this.attributes());
	}

	private Iterator<String> attributeNames(Iterator<? extends IPersistentAttribute> attrs) {
		return new TransformationIterator<IPersistentAttribute, String>(attrs) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	public Iterator<IPersistentAttribute> allAttributes() {
		return new CompositeIterator<IPersistentAttribute>(new TransformationIterator<IPersistentType, Iterator<IPersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<IPersistentAttribute> transform(IPersistentType pt) {
				//TODO how to remove this warning?
				return (Iterator<IPersistentAttribute>) pt.attributes();
			}
		});
	}

	public Iterator<String> allAttributeNames() {
		return this.attributeNames(this.allAttributes());
	}

	public Iterator<IPersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<IPersistentType>(this) {
			@Override
			protected IPersistentType nextLink(IPersistentType pt) {
				return pt.parentPersistentType();
			}
		};
	}

	public IPersistentType parentPersistentType() {
		return this.parentPersistentType;
	}

	private String superclassTypeSignature() {
		try {
			return this.jdtType().getSuperclassTypeSignature();
		}
		catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Return the AccessType currently implied by the Java source code:
	 *     - if only Fields are annotated => FIELD
	 *     - if only Properties are annotated => PROPERTY
	 *     - if both Fields and Properties are annotated => FIELD
	 *     - if nothing is annotated
	 *     		- and fields exist => FIELD
	 *     		- and properties exist, but no fields exist => PROPERTY
	 *     		- and neither fields nor properties exist => FIELD
	 */
	private AccessType javaAccessType(CompilationUnit astRoot) {
		IType jdtType = this.jdtType();
		boolean hasPersistableFields = false;
		boolean hasPersistableProperties = false;
		for (IField field : AttributeAnnotationTools.persistableFields(jdtType)) {
			hasPersistableFields = true;
			FieldAttribute fa = new FieldAttribute(field);
			if (fa.containsAnyAnnotation(this.attributeMappingAnnotationAdapters, astRoot)) {
				// any field is annotated => FIELD
				return AccessType.FIELD;
			}
		}
		for (IMethod method : AttributeAnnotationTools.persistablePropertyGetters(jdtType)) {
			hasPersistableProperties = true;
			MethodAttribute ma = new MethodAttribute(method);
			if (ma.containsAnyAnnotation(this.attributeMappingAnnotationAdapters, astRoot)) {
				// none of the fields are annotated and a getter is annotated => PROPERTY
				return AccessType.PROPERTY;
			}
		}
		// no annotations exist - default to fields, unless it's *obvious* to use properties
		if (hasPersistableProperties && !hasPersistableFields) {
			return AccessType.PROPERTY;
		}
		return AccessType.FIELD;
	}

	public void refreshDefaults(DefaultsContext context) {
		refreshParentPersistentType(context);
	}

	private void refreshParentPersistentType(DefaultsContext context) {
		String superclassTypeSignature = this.superclassTypeSignature();
		if (superclassTypeSignature == null) {
			this.parentPersistentType = null;
			return;
		}
		String fullyQualifiedTypeName = JDTTools.resolveSignature(superclassTypeSignature, this.jdtType());
		if (fullyQualifiedTypeName == null) {
			this.parentPersistentType = null;
			return;
		}
		IPersistentType possibleParent = context.persistentType(fullyQualifiedTypeName);
		if (possibleParent == null) {
			//TODO look to superclass
			this.parentPersistentType = null;
			return;
		}
		if (possibleParent.getMappingKey() != null) {
			this.parentPersistentType = possibleParent;
		}
		else {
			this.parentPersistentType = possibleParent.parentPersistentType();
		}
	}
}