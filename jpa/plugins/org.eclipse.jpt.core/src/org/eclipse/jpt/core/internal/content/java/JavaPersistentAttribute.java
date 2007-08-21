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

import java.util.Iterator;
import java.util.ListIterator;
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
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMappingProvider;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.Filter;

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
		//no access to the jpaFactory() in the constructor because the parent is not set yet
		this.setDefaultMapping(this.nullAttributeMappingProvider().buildMapping(this.attribute, null));
	}

	private Iterator<IJavaAttributeMappingProvider> attributeMappingProviders() {
		return jpaPlatform().javaAttributeMappingProviders();
	}

	private ListIterator<IDefaultJavaAttributeMappingProvider> defaultAttributeMappingProviders() {
		return jpaPlatform().defaultJavaAttributeMappingProviders();
	}

	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	protected IJavaAttributeMappingProvider nullAttributeMappingProvider() {
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
	public void setDefaultMappingGen(IJavaAttributeMapping newDefaultMapping) {
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
	 * clients do not set the "default" mapping
	 */
	private void setDefaultMapping(IJavaAttributeMapping defaultMapping) {
		this.setDefaultMappingGen(defaultMapping);
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
	 * clients do not set the "specified" mapping directly;
	 * call #setMappingKey(String, boolean) instead
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
	 * clients do not set the "specified" mapping;
	 * use #setMappingKey(String)
	 */
	private void setSpecifiedMapping(IJavaAttributeMapping specifiedMapping) {
		this.setSpecifiedMappingGen(specifiedMapping);
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
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING :
				return getMapping();
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
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING :
				return getMapping() != null;
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
				case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING :
					return JpaCorePackage.IPERSISTENT_ATTRIBUTE__MAPPING;
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
				case JpaCorePackage.IPERSISTENT_ATTRIBUTE__MAPPING :
					return JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public IPersistentType getPersistentType() {
		return (IPersistentType) this.eContainer();
	}

	public IJavaAttributeMapping getMapping() {
		return (this.specifiedMapping != null) ? this.specifiedMapping : this.defaultMapping;
	}

	public String getName() {
		return this.attribute.attributeName();
	}

	public ITypeMapping typeMapping() {
		return this.getPersistentType().getMapping();
	}

	public String mappingKey() {
		return this.getMapping().getKey();
	}

	/**
	 * return null if there is no "default" mapping for the attribute
	 */
	public String defaultMappingKey() {
		return this.defaultMapping.getKey();
	}

	/**
	 * return null if there is no "specified" mapping for the attribute
	 */
	public String specifiedMappingKey() {
		return (this.specifiedMapping == null) ? null : this.specifiedMapping.getKey();
	}

	// TODO support morphing mappings, i.e. copying common settings over
	// to the new mapping; this can't be done in the same was as XmlAttributeMapping
	// since we don't know all the possible mapping types
	public void setSpecifiedMappingKey(String newKey) {
		String oldKey = this.specifiedMappingKey();
		if (newKey == oldKey) {
			return;
		}
		IJavaAttributeMapping old = this.getMapping();
		if (newKey == null) {
			// remove mapping annotation
			this.setSpecifiedMapping(null);
			this.attribute.removeAnnotation(this.declarationAnnotationAdapterForAttributeMappingKey(oldKey));
		}
		else {
			// add or replace mapping annotation
			this.setSpecifiedMapping(this.attributeMappingProvider(newKey).buildMapping(this.attribute, jpaFactory()));
			if (oldKey != null) {
				this.attribute.removeAnnotation(this.declarationAnnotationAdapterForAttributeMappingKey(oldKey));
			}
			this.attribute.newMarkerAnnotation(this.declarationAnnotationAdapterForAttributeMappingKey(newKey));
			this.specifiedMapping.updateFromJava(getAttribute().astRoot());
		}
		if (this.eNotificationRequired()) {
			this.eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING, old, this.getMapping()));
		}
	}

	private DeclarationAnnotationAdapter declarationAnnotationAdapterForAttributeMappingKey(String attributeMappingKey) {
		return this.attributeMappingProvider(attributeMappingKey).declarationAnnotationAdapter();
	}

	/**
	 * throw an exception if the provider is not found
	 */
	private IJavaAttributeMappingProvider attributeMappingProvider(String attributeMappingKey) {
		return jpaPlatform().javaAttributeMappingProvider(attributeMappingKey);
	}

	public Object getId() {
		return IJavaContentNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public Attribute getAttribute() {
		return this.attribute;
	}

	public boolean includes(int offset) {
		ITextRange fullTextRange = this.fullTextRange();
		if (fullTextRange == null) {
			//This happens if the attribute no longer exists in the java.
			//The text selection event is fired before the update from java so our
			//model has not yet had a chance to update appropriately. The list of
			//JavaPersistentAttriubtes is stale at this point.  For now, we are trying
			//to avoid the NPE, not sure of the ultimate solution to these 2 threads accessing
			//our model
			return false;
		}
		return fullTextRange.includes(offset);
	}

	public ITextRange fullTextRange() {
		return this.attribute.textRange();
	}

	public ITextRange validationTextRange() {
		return this.selectionTextRange();
	}

	public ITextRange selectionTextRange() {
		return this.attribute.nameTextRange();
	}

	public boolean isFor(IMember member) {
		return this.attribute.wraps(member);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		// synchronize the "specified" mapping with the Java source
		String jpaKey = this.specifiedMappingKey();
		IJavaAttributeMappingProvider javaProvider = this.javaAttributeMappingProvider(astRoot);
		String javaKey = ((javaProvider == null) ? null : javaProvider.key());
		if (javaKey != jpaKey) {
			IJavaAttributeMapping old = this.getMapping();
			if (javaKey == null) {
				// no mapping annotation found in Java source
				this.setSpecifiedMapping(null);
			}
			else {
				// the mapping has changed
				this.setSpecifiedMapping(javaProvider.buildMapping(this.attribute, jpaFactory()));
			}
			if (this.eNotificationRequired()) {
				this.eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING, old, this.getMapping()));
			}
		}
		// once the "specified" mapping is in place, update it from Java;
		// unless it is null, in which case we update the "default" mapping from Java
		this.getMapping().updateFromJava(astRoot);
	}

	/**
	 * return null if we can't find a mapping annotation on the attribute
	 */
	private IJavaAttributeMappingProvider javaAttributeMappingProvider(CompilationUnit astRoot) {
		for (Iterator<IJavaAttributeMappingProvider> i = this.attributeMappingProviders(); i.hasNext();) {
			IJavaAttributeMappingProvider provider = i.next();
			if (this.attribute.containsAnnotation(provider.declarationAnnotationAdapter(), astRoot)) {
				return provider;
			}
		}
		return null;
	}

	public String primaryKeyColumnName() {
		IJavaAttributeMapping mapping = this.getMapping();
		return (mapping == null) ? null : mapping.primaryKeyColumnName();
	}

	/**
	 * the mapping might be "default", but it still might be a "null" mapping...
	 */
	public boolean mappingIsDefault() {
		return this.specifiedMapping == null;
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return this.getMapping().candidateValuesFor(pos, filter, astRoot);
	}

	/**
	 * check to see whether the "default" mapping has changed
	 */
	public void refreshDefaults(DefaultsContext defaultsContext) {
		IJavaAttributeMappingProvider defaultProvider = this.defaultAttributeMappingProvider(defaultsContext);
		if (defaultProvider.key() == this.defaultMapping.getKey()) {
			return;
		}
		// the "default" mapping has changed
		IJavaAttributeMapping old = this.getMapping();
		this.setDefaultMapping(defaultProvider.buildMapping(this.attribute, jpaFactory()));
		this.defaultMapping.updateFromJava(defaultsContext.astRoot());
		if (this.eNotificationRequired()) {
			this.eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE__MAPPING, old, this.getMapping()));
		}
	}

	/**
	 * return the first(?) provider that can supply a "default" mapping for the attribute;
	 * return the null provider if we can't find a provider
	 */
	private IJavaAttributeMappingProvider defaultAttributeMappingProvider(DefaultsContext defaultsContext) {
		for (Iterator<IDefaultJavaAttributeMappingProvider> i = this.defaultAttributeMappingProviders(); i.hasNext();) {
			IDefaultJavaAttributeMappingProvider provider = i.next();
			if (provider.defaultApplies(this.attribute, defaultsContext)) {
				return provider;
			}
		}
		return this.nullAttributeMappingProvider();
	}

	public boolean isOverridableAttribute() {
		return this.getMapping().isOverridableAttributeMapping();
	}

	public boolean isOverridableAssociation() {
		return this.getMapping().isOverridableAssociationMapping();
	}

	public boolean isIdAttribute() {
		return this.getMapping().isIdMapping();
	}
}
