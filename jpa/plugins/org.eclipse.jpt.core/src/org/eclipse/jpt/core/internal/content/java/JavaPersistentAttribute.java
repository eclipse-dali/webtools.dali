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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaBasicProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedIdProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaIdProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToManyProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOneProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToManyProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOneProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTransientProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaVersionProvider;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Persistent Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute#getDefaultMapping <em>Default Mapping</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute#getSpecifiedMapping <em>Specified Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentAttribute()
 * @model kind="class"
 * @generated
 */
public class JavaPersistentAttribute extends JavaEObject
	implements IPersistentAttribute
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
	 * The cached value of the '{@link #getDefaultMapping() <em>Default Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultMapping()
	 * @generated
	 * @ordered
	 */
	protected IJavaAttributeMapping defaultMapping;

	/**
	 * The cached value of the '{@link #getSpecifiedMapping() <em>Specified Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedMapping()
	 * @generated
	 * @ordered
	 */
	protected IJavaAttributeMapping specifiedMapping;

	private Attribute attribute;

	// TODO move these to a singleton?
	private IJavaAttributeMappingProvider[] attributeMappingProviders;

	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	// TODO move this to a singleton?
	private IJavaAttributeMappingProvider nullAttributeMappingProvider;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaPersistentAttribute() {
		super();
	}

	protected JavaPersistentAttribute(Attribute attribute) {
		super();
		this.attribute = attribute;
		this.attributeMappingProviders = this.buildAttributeMappingProviders();
		this.nullAttributeMappingProvider = this.buildNullAttributeMappingProvider();
	}

	private IJavaAttributeMappingProvider[] buildAttributeMappingProviders() {
		ArrayList<IJavaAttributeMappingProvider> providers = new ArrayList<IJavaAttributeMappingProvider>();
		this.addAttributeMappingProvidersTo(providers);
		return providers.toArray(new IJavaAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined type mappings of 
	 * Basic, Id, OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId.
	 */
	protected void addAttributeMappingProvidersTo(Collection<IJavaAttributeMappingProvider> providers) {
		providers.add(JavaBasicProvider.instance());
		providers.add(JavaIdProvider.instance());
		providers.add(JavaTransientProvider.instance());
		providers.add(JavaOneToManyProvider.instance());
		providers.add(JavaManyToOneProvider.instance());
		providers.add(JavaManyToManyProvider.instance());
		providers.add(JavaOneToOneProvider.instance());
		providers.add(JavaEmbeddedProvider.instance());
		providers.add(JavaEmbeddedIdProvider.instance());
		providers.add(JavaVersionProvider.instance());
	}

	protected IJavaAttributeMappingProvider buildNullAttributeMappingProvider() {
		return JavaNullAttributeMappingProvider.instance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaPackage.Literals.JAVA_PERSISTENT_ATTRIBUTE;
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
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getIPersistentAttribute_MappingKey()
	 * @model required="true" changeable="false"
	 * @generated
	 */
	public String getMappingKey() {
		return mappingKey;
	}

	/**
	 * Returns the value of the '<em><b>Default Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Mapping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Mapping</em>' containment reference.
	 * @see #setDefaultMapping(IJavaAttributeMapping)
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentAttribute_DefaultMapping()
	 * @model containment="true" required="true"
	 * @generated
	 */
	public IJavaAttributeMapping getDefaultMapping() {
		return defaultMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefaultMapping(IJavaAttributeMapping newDefaultMapping, NotificationChain msgs) {
		IJavaAttributeMapping oldDefaultMapping = defaultMapping;
		defaultMapping = newDefaultMapping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING, oldDefaultMapping, newDefaultMapping);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute#getDefaultMapping <em>Default Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Mapping</em>' containment reference.
	 * @see #getDefaultMapping()
	 * @generated
	 */
	public void setDefaultMapping(IJavaAttributeMapping newDefaultMapping) {
		if (newDefaultMapping != defaultMapping) {
			NotificationChain msgs = null;
			if (defaultMapping != null)
				msgs = ((InternalEObject) defaultMapping).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING, null, msgs);
			if (newDefaultMapping != null)
				msgs = ((InternalEObject) newDefaultMapping).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING, null, msgs);
			msgs = basicSetDefaultMapping(newDefaultMapping, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING, newDefaultMapping, newDefaultMapping));
	}

	/**
	 * Returns the value of the '<em><b>Specified Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Mapping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Mapping</em>' containment reference.
	 * @see #setSpecifiedMapping(IJavaAttributeMapping)
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJavaPersistentAttribute_SpecifiedMapping()
	 * @model containment="true" required="true"
	 * @generated
	 */
	public IJavaAttributeMapping getSpecifiedMapping() {
		return specifiedMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSpecifiedMapping(IJavaAttributeMapping newSpecifiedMapping, NotificationChain msgs) {
		IJavaAttributeMapping oldSpecifiedMapping = specifiedMapping;
		specifiedMapping = newSpecifiedMapping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING, oldSpecifiedMapping, newSpecifiedMapping);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute#getSpecifiedMapping <em>Specified Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Mapping</em>' containment reference.
	 * @see #getSpecifiedMapping()
	 * @generated
	 */
	public void setSpecifiedMappingGen(IJavaAttributeMapping newSpecifiedMapping) {
		if (newSpecifiedMapping != specifiedMapping) {
			NotificationChain msgs = null;
			if (specifiedMapping != null)
				msgs = ((InternalEObject) specifiedMapping).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING, null, msgs);
			if (newSpecifiedMapping != null)
				msgs = ((InternalEObject) newSpecifiedMapping).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING, null, msgs);
			msgs = basicSetSpecifiedMapping(newSpecifiedMapping, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING, newSpecifiedMapping, newSpecifiedMapping));
	}

	/**
	 * This should not be called when updating the persistence model
	 * from the java model, it should only be called when going in the 
	 * other direction.  This will update the java code appropriately
	 * to the change in mapping in the persistence model.
	 */
	public void setSpecifiedMapping(IJavaAttributeMapping newMapping) {
		if (this.specifiedMapping != null) {
			this.attribute.removeAnnotation(this.annotationAdapterForAttributeMappingKey(this.specifiedMapping.getKey()));
		}
		if (newMapping != null) {
			this.attribute.newMarkerAnnotation(this.annotationAdapterForAttributeMappingKey(newMapping.getKey()));
		}
		this.setSpecifiedMappingGen(newMapping);
	}

	protected void setMappingKeyInternal(String newMappingKey) {
		String oldMappingKey = mappingKey;
		mappingKey = newMappingKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING_KEY, oldMappingKey, mappingKey));
	}

	/**
	 * This should not be called when updating the persistence model
	 * from the java model, it should only be called when going in the 
	 * other direction.  This will update the java code appropriately
	 * to the change in mapping in the persistence model.
	 */
	public void setMappingKey(String newMappingKey, boolean default_) {
		if (default_) {
			setSpecifiedMapping(null);
			setMappingKeyInternal(newMappingKey);
			return;
		}
		this.setSpecifiedMapping(this.buildMapping(newMappingKey));
		if (this.specifiedMapping != null) {
			this.specifiedMapping.initialize();
		}
		this.setMappingKeyInternal(newMappingKey);
		//TODO need to support mapping morphing, copying common settings over
		//to the new mapping.  This can't be done in the same was as XmlAttributeMapping
		//since we don't know all the possible mapping types
	}

	public IJavaAttributeMapping getMapping() {
		return getSpecifiedMapping() != null ? getSpecifiedMapping() : getDefaultMapping();
	}

	public String getName() {
		return getAttribute().attributeName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING :
				return basicSetDefaultMapping(null, msgs);
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING :
				return basicSetSpecifiedMapping(null, msgs);
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
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING_KEY :
				return getMappingKey();
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING :
				return getDefaultMapping();
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING :
				return getSpecifiedMapping();
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
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING :
				setDefaultMapping((IJavaAttributeMapping) newValue);
				return;
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING :
				setSpecifiedMapping((IJavaAttributeMapping) newValue);
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
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING :
				setDefaultMapping((IJavaAttributeMapping) null);
				return;
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING :
				setSpecifiedMapping((IJavaAttributeMapping) null);
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
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING_KEY :
				return MAPPING_KEY_EDEFAULT == null ? mappingKey != null : !MAPPING_KEY_EDEFAULT.equals(mappingKey);
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__DEFAULT_MAPPING :
				return defaultMapping != null;
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__SPECIFIED_MAPPING :
				return specifiedMapping != null;
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
		if (baseClass == IPersistentAttribute.class) {
			switch (derivedFeatureID) {
				case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING_KEY :
					return JpaCorePackage.IPERSISTENT_ATTRIBUTE__MAPPING_KEY;
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
		if (baseClass == IPersistentAttribute.class) {
			switch (baseFeatureID) {
				case JpaCorePackage.IPERSISTENT_ATTRIBUTE__MAPPING_KEY :
					return JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING_KEY;
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
		result.append(')');
		return result.toString();
	}

	public IPersistentType getPersistentType() {
		return (IPersistentType) this.eContainer();
	}

	public ITypeMapping typeMapping() {
		return this.getPersistentType().getMapping();
	}

	private IJavaAttributeMappingProvider attributeMappingProviderFor(String attributeMappingKey) {
		for (IJavaAttributeMappingProvider provider : this.attributeMappingProviders) {
			if (provider.key() == attributeMappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported attribute mapping key: " + attributeMappingKey);
	}

	private DeclarationAnnotationAdapter annotationAdapterForAttributeMappingKey(String attributeMappingKey) {
		return this.attributeMappingProviderFor(attributeMappingKey).declarationAnnotationAdapter();
	}

	public Iterator<String> candidateMappingKeys() {
		return new TransformationIterator<IJavaAttributeMappingProvider, String>(new ArrayIterator<IJavaAttributeMappingProvider>(this.attributeMappingProviders)) {
			protected String transform(IJavaAttributeMappingProvider next) {
				return next.key();
			}
		};
	}

	public Object getId() {
		return IJavaContentNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public Attribute getAttribute() {
		return this.attribute;
	}

	public ITextRange getTextRange() {
		return this.attribute.textRange();
	}

	public boolean isFor(IMember member) {
		return this.attribute.wraps(member);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		String jpaKey = null;
		if (this.specifiedMapping != null) {
			jpaKey = this.specifiedMapping.getKey();
		}
		String javaKey = this.javaAttributeMappingKey(astRoot);
		if (javaKey == null) { // no annotation
			if (this.specifiedMapping != null) {
				setSpecifiedMapping(null);
			}
		}
		else if (jpaKey != javaKey) {
			this.createAndSetMappingFromJava(javaKey);
		}
		if (getMapping() != null) {
			getMapping().updateFromJava(astRoot);
		}
	}

	private void createAndSetMappingFromJava(String key) {
		this.setSpecifiedMappingGen(this.buildMapping(key));
		this.specifiedMapping.initialize();
		this.setMappingKeyInternal(this.specifiedMapping.getKey());
	}

	//A null key means there is no "mapping" annotation on the attribute.
	//In this case check the attributeMappingProviders for one that the defaultApplies
	//and create the mapping.  If the key is not null then create the mapping
	//based on the appropriate provider, otherwise return the a nullAttributeMapping
	private IJavaAttributeMapping buildMapping(String key) {
		if (key == null) {
			return null;
		}
		for (IJavaAttributeMappingProvider provider : this.attributeMappingProviders) {
			if (provider.key() == key) {
				return provider.buildMapping(this.attribute);
			}
		}
		return this.nullAttributeMappingProvider.buildMapping(this.attribute);
	}

	private String javaAttributeMappingKey(CompilationUnit astRoot) {
		for (IJavaAttributeMappingProvider provider : this.attributeMappingProviders) {
			if (this.attribute.containsAnnotation(provider.declarationAnnotationAdapter(), astRoot)) {
				return provider.key();
			}
		}
		return null;
	}

	public String defaultKey() {
		if (this.defaultMapping != null) {
			return this.defaultMapping.getKey();
		}
		return null;
	}

	public String primaryKeyColumnName() {
		if (getMapping() != null) {
			return getMapping().primaryKeyColumnName();
		}
		return null;
	}

	public boolean isAttributeMappingDefault() {
		return this.specifiedMapping == null;
	}

	public List<String> candidateValuesFor(int pos, CompilationUnit astRoot) {
		return Collections.emptyList();
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		for (IJavaAttributeMappingProvider provider : this.attributeMappingProviders) {
			if (provider.defaultApplies(this.attribute, defaultsContext)) {
				if (getDefaultMapping() != null) {
					if (getDefaultMapping().getKey() == provider.key()) {
						return;
					}
				}
				setDefaultMapping(provider.buildMapping(this.attribute));
				getDefaultMapping().updateFromJava(getAttribute().astRoot());
				if (getSpecifiedMapping() == null) {
					setMappingKeyInternal(getDefaultMapping().getKey());
				}
				return;
			}
		}
		setDefaultMapping(this.nullAttributeMappingProvider.buildMapping(this.attribute));
		getDefaultMapping().updateFromJava(getAttribute().astRoot());
		if (getSpecifiedMapping() == null) {
			setMappingKeyInternal(getDefaultMapping().getKey());
		}
	}
} // JavaPersistentAttribute
