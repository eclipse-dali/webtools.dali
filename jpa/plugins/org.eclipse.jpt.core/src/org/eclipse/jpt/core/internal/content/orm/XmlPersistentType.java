/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jem.internal.adapters.jdom.JDOMSearchHelper;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Persistent Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getClass_ <em>Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getAttributeMappings <em>Attribute Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getSpecifiedAttributeMappings <em>Specified Attribute Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getVirtualAttributeMappings <em>Virtual Attribute Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getPersistentAttributes <em>Persistent Attributes</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getSpecifiedPersistentAttributes <em>Specified Persistent Attributes</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getVirtualPersistentAttributes <em>Virtual Persistent Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType()
 * @model kind="class"
 * @generated
 */
public class XmlPersistentType extends XmlEObject implements IPersistentType
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
	 * The default value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected String class_ = CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSpecifiedAttributeMappings() <em>Specified Attribute Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAttributeMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeMapping> specifiedAttributeMappings;

	/**
	 * The cached value of the '{@link #getVirtualAttributeMappings() <em>Virtual Attribute Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVirtualAttributeMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeMapping> virtualAttributeMappings;

	/**
	 * The cached value of the '{@link #getSpecifiedPersistentAttributes() <em>Specified Persistent Attributes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPersistentAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPersistentAttribute> specifiedPersistentAttributes;

	/**
	 * The cached value of the '{@link #getVirtualPersistentAttributes() <em>Virtual Persistent Attributes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVirtualPersistentAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPersistentAttribute> virtualPersistentAttributes;

	private JavaPersistentType javaPersistentType;

	private Collection<IXmlTypeMappingProvider> typeMappingProviders;

	private IPersistentType parentPersistentType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected XmlPersistentType() {
		super();
		this.typeMappingProviders = buildTypeMappingProviders();
	}

	protected XmlPersistentType(String theMappingKey) {
		this();
		mappingKey = theMappingKey;
	}

	@Override
	protected void addInsignificantFeatureIdsTo(Set<Integer> insignificantFeatureIds) {
		super.addInsignificantFeatureIdsTo(insignificantFeatureIds);
		insignificantFeatureIds.add(OrmPackage.XML_PERSISTENT_TYPE__PERSISTENT_ATTRIBUTES);
		insignificantFeatureIds.add(OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES);
		insignificantFeatureIds.add(OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES);
	}

	protected Collection<IXmlTypeMappingProvider> buildTypeMappingProviders() {
		Collection<IXmlTypeMappingProvider> collection = new ArrayList<IXmlTypeMappingProvider>();
		collection.add(new XmlEntityProvider());
		collection.add(new XmlMappedSuperclassProvider());
		collection.add(new XmlEmbeddableProvider());
		return collection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_PERSISTENT_TYPE;
	}

	public XmlTypeMapping getMapping() {
		return (XmlTypeMapping) eContainer();
	}

	/* @see IJpaContentNode#getId() */
	public Object getId() {
		return IXmlContentNodes.PERSISTENT_TYPE_ID;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIPersistentType_MappingKey()
	 * @model required="true"
	 * @generated
	 */
	public String getMappingKey() {
		return mappingKey;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getMappingKey <em>Mapping Key</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENT_TYPE__MAPPING_KEY, oldMappingKey, mappingKey));
	}

	public void setMappingKey(String newMappingKey) {
		if (mappingKey.equals(newMappingKey)) {
			return;
		}
		XmlTypeMapping oldMapping = getMapping();
		EntityMappings entityMappings = oldMapping.getEntityMappings();
		entityMappings.changeMapping(oldMapping, newMappingKey);
		setMappingKeyGen(newMappingKey);
	}

	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' attribute.
	 * @see #setClass(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType_Class()
	 * @model
	 * @generated
	 */
	public String getClass_() {
		return class_;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' attribute.
	 * @see #getClass_()
	 * @generated
	 */
	public void setClassGen(String newClass) {
		String oldClass = class_;
		class_ = newClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENT_TYPE__CLASS, oldClass, class_));
	}

	public void setClass(String newClass) {
		setClassGen(newClass);
	}

	/**
	 * Returns the value of the '<em><b>Attribute Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Mappings</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType_AttributeMappings()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public EList<XmlAttributeMapping> getAttributeMappings() {
		EList<XmlAttributeMapping> list = new BasicEList<XmlAttributeMapping>();
		list.addAll(getSpecifiedAttributeMappings());
		list.addAll(getVirtualAttributeMappings());
		return list;
	}

	/**
	 * Returns the value of the '<em><b>Specified Attribute Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Attribute Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Attribute Mappings</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType_SpecifiedAttributeMappings()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping" containment="true"
	 * @generated
	 */
	public EList<XmlAttributeMapping> getSpecifiedAttributeMappingsGen() {
		if (specifiedAttributeMappings == null) {
			specifiedAttributeMappings = new EObjectContainmentEList<XmlAttributeMapping>(XmlAttributeMapping.class, this, OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS);
		}
		return specifiedAttributeMappings;
	}

	public EList<XmlAttributeMapping> getSpecifiedAttributeMappings() {
		if (specifiedAttributeMappings == null) {
			specifiedAttributeMappings = new SpecifiedAttributeMappingsList<XmlAttributeMapping>();
		}
		return getSpecifiedAttributeMappingsGen();
	}

	/**
	 * Returns the value of the '<em><b>Virtual Attribute Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual Attribute Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual Attribute Mappings</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType_VirtualAttributeMappings()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping" containment="true"
	 * @generated
	 */
	public EList<XmlAttributeMapping> getVirtualAttributeMappingsGen() {
		if (virtualAttributeMappings == null) {
			virtualAttributeMappings = new EObjectContainmentEList<XmlAttributeMapping>(XmlAttributeMapping.class, this, OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS);
		}
		return virtualAttributeMappings;
	}

	public EList<XmlAttributeMapping> getVirtualAttributeMappings() {
		if (virtualAttributeMappings == null) {
			virtualAttributeMappings = new VirtualAttributeMappingsList<XmlAttributeMapping>();
		}
		return getVirtualAttributeMappingsGen();
	}

	/**
	 * Returns the value of the '<em><b>Persistent Attributes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistent Attributes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistent Attributes</em>' reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType_PersistentAttributes()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute" resolveProxies="false" transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public EList<XmlPersistentAttribute> getPersistentAttributes() {
		EList<XmlPersistentAttribute> list = new BasicEList<XmlPersistentAttribute>();
		list.addAll(getSpecifiedPersistentAttributes());
		list.addAll(getVirtualPersistentAttributes());
		return list;
	}

	/**
	 * Returns the value of the '<em><b>Specified Persistent Attributes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Persistent Attributes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Persistent Attributes</em>' reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType_SpecifiedPersistentAttributes()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute" resolveProxies="false"
	 * @generated
	 */
	public EList<XmlPersistentAttribute> getSpecifiedPersistentAttributes() {
		if (specifiedPersistentAttributes == null) {
			specifiedPersistentAttributes = new EObjectEList<XmlPersistentAttribute>(XmlPersistentAttribute.class, this, OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES);
		}
		return specifiedPersistentAttributes;
	}

	/**
	 * Returns the value of the '<em><b>Virtual Persistent Attributes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Persistent Attributes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual Persistent Attributes</em>' reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPersistentType_VirtualPersistentAttributes()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute" resolveProxies="false"
	 * @generated
	 */
	public EList<XmlPersistentAttribute> getVirtualPersistentAttributes() {
		if (virtualPersistentAttributes == null) {
			virtualPersistentAttributes = new EObjectEList<XmlPersistentAttribute>(XmlPersistentAttribute.class, this, OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES);
		}
		return virtualPersistentAttributes;
	}

	protected void changeMapping(XmlAttributeMapping oldMapping, String newMappingKey) {
		boolean virtual = oldMapping.isVirtual();
		XmlAttributeMapping newAttributeMapping = buildAttributeMapping(oldMapping.getPersistentAttribute().attributeMappingProviders(), newMappingKey);
		
		if (virtual) {
			getVirtualAttributeMappings().remove(oldMapping);
		} else {
			getSpecifiedAttributeMappings().remove(oldMapping);
		}
		
		oldMapping.initializeOn(newAttributeMapping);
		
		if (virtual) {
			insertAttributeMapping(newAttributeMapping, getVirtualAttributeMappings());
		} else {
			insertAttributeMapping(newAttributeMapping, getSpecifiedAttributeMappings());
		}
	}
	
	private XmlAttributeMapping buildAttributeMapping(Collection<IXmlAttributeMappingProvider> providers, String key) {
		for (IXmlAttributeMappingProvider provider : providers) {
			if (provider.key().equals(key)) {
				return provider.buildAttributeMapping();
			}
		}
		//TODO throw an exception? what about the NullJavaTypeMapping?
		return null;
	}

	public Collection<IXmlTypeMappingProvider> typeMappingProviders() {
		return this.typeMappingProviders;
	}
	
	protected void setMappingVirtual(XmlAttributeMapping attributeMapping, boolean virtual) {
		boolean oldVirtual = attributeMapping.isVirtual();
		
		if (oldVirtual == virtual) {
			return;
		}
		
		if (virtual) {
			getSpecifiedAttributeMappings().remove(attributeMapping);
			insertAttributeMapping(attributeMapping, getVirtualAttributeMappings());
		}
		else {
			getVirtualAttributeMappings().remove(attributeMapping);
			insertAttributeMapping(attributeMapping, getSpecifiedAttributeMappings());
		}
	}

	private void insertAttributeMapping(XmlAttributeMapping newMapping, List<XmlAttributeMapping> attributeMappings) {
		int newIndex = CollectionTools.insertionIndexOf(attributeMappings, newMapping, buildMappingComparator());
		attributeMappings.add(newIndex, newMapping);
	}

	private Comparator<XmlAttributeMapping> buildMappingComparator() {
		return new Comparator<XmlAttributeMapping>() {
			public int compare(XmlAttributeMapping o1, XmlAttributeMapping o2) {
				int o1Sequence = o1.xmlSequence();
				int o2Sequence = o2.xmlSequence();
				if (o1Sequence < o2Sequence) {
					return -1;
				}
				if (o1Sequence == o2Sequence) {
					return 0;
				}
				return 1;
			}
		};
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_PERSISTENT_TYPE__ATTRIBUTE_MAPPINGS :
				return ((InternalEList<?>) getAttributeMappings()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS :
				return ((InternalEList<?>) getSpecifiedAttributeMappings()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS :
				return ((InternalEList<?>) getVirtualAttributeMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public Iterator candidateMappingKeys() {
		Collection mappingKeys = new ArrayList();
		for (IXmlTypeMappingProvider provider : typeMappingProviders()) {
			mappingKeys.add(provider.key());
		}
		return mappingKeys.iterator();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_PERSISTENT_TYPE__MAPPING_KEY :
				return getMappingKey();
			case OrmPackage.XML_PERSISTENT_TYPE__CLASS :
				return getClass_();
			case OrmPackage.XML_PERSISTENT_TYPE__ATTRIBUTE_MAPPINGS :
				return getAttributeMappings();
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS :
				return getSpecifiedAttributeMappings();
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS :
				return getVirtualAttributeMappings();
			case OrmPackage.XML_PERSISTENT_TYPE__PERSISTENT_ATTRIBUTES :
				return getPersistentAttributes();
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES :
				return getSpecifiedPersistentAttributes();
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES :
				return getVirtualPersistentAttributes();
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
			case OrmPackage.XML_PERSISTENT_TYPE__MAPPING_KEY :
				setMappingKey((String) newValue);
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__CLASS :
				setClass((String) newValue);
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS :
				getSpecifiedAttributeMappings().clear();
				getSpecifiedAttributeMappings().addAll((Collection<? extends XmlAttributeMapping>) newValue);
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS :
				getVirtualAttributeMappings().clear();
				getVirtualAttributeMappings().addAll((Collection<? extends XmlAttributeMapping>) newValue);
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES :
				getSpecifiedPersistentAttributes().clear();
				getSpecifiedPersistentAttributes().addAll((Collection<? extends XmlPersistentAttribute>) newValue);
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES :
				getVirtualPersistentAttributes().clear();
				getVirtualPersistentAttributes().addAll((Collection<? extends XmlPersistentAttribute>) newValue);
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
			case OrmPackage.XML_PERSISTENT_TYPE__MAPPING_KEY :
				setMappingKey(MAPPING_KEY_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__CLASS :
				setClass(CLASS_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS :
				getSpecifiedAttributeMappings().clear();
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS :
				getVirtualAttributeMappings().clear();
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES :
				getSpecifiedPersistentAttributes().clear();
				return;
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES :
				getVirtualPersistentAttributes().clear();
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
			case OrmPackage.XML_PERSISTENT_TYPE__MAPPING_KEY :
				return MAPPING_KEY_EDEFAULT == null ? mappingKey != null : !MAPPING_KEY_EDEFAULT.equals(mappingKey);
			case OrmPackage.XML_PERSISTENT_TYPE__CLASS :
				return CLASS_EDEFAULT == null ? class_ != null : !CLASS_EDEFAULT.equals(class_);
			case OrmPackage.XML_PERSISTENT_TYPE__ATTRIBUTE_MAPPINGS :
				return !getAttributeMappings().isEmpty();
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS :
				return specifiedAttributeMappings != null && !specifiedAttributeMappings.isEmpty();
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS :
				return virtualAttributeMappings != null && !virtualAttributeMappings.isEmpty();
			case OrmPackage.XML_PERSISTENT_TYPE__PERSISTENT_ATTRIBUTES :
				return !getPersistentAttributes().isEmpty();
			case OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_PERSISTENT_ATTRIBUTES :
				return specifiedPersistentAttributes != null && !specifiedPersistentAttributes.isEmpty();
			case OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_PERSISTENT_ATTRIBUTES :
				return virtualPersistentAttributes != null && !virtualPersistentAttributes.isEmpty();
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
				case OrmPackage.XML_PERSISTENT_TYPE__MAPPING_KEY :
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
					return OrmPackage.XML_PERSISTENT_TYPE__MAPPING_KEY;
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
		result.append(", class: ");
		result.append(class_);
		result.append(')');
		return result.toString();
	}

	public IType findJdtType() {
		String fqName = getClass_();
		if (StringTools.stringIsEmpty(fqName)) {
			return null;
		}
		// try to resolve by only the locally specified name
		IType type = resolveJdtType(fqName);
		if (type == null) {
			// try to resolve by prepending the global package name
			fqName = getMapping().getEntityMappings().getPackage() + "." + getClass_();
			type = resolveJdtType(fqName);
		}
		return type;
	}

	private IType resolveJdtType(String fullyQualifiedName) {
		// this name could be of the form "package.name.ClassName"
		// or the form "package.name.ClassName.MemberClassName"
		// so we must try multiple package and class names here
		String[] name = new String[] {
			fullyQualifiedName, ""
		};
		while (name[0].length() != 0) {
			name = moveDot(name);
			IType type = JDOMSearchHelper.findType(name[0], name[1], getJpaProject().getJavaProject());
			if (type != null)
				return type;
		}
		return null;
	}

	/**
	 * Returns a String array based on the given string array by 
	 * moving a package segment from the end of the first to the
	 * beginning of the second
	 * 
	 * e.g. ["foo.bar", "Baz"] -> ["foo", "bar.Baz"]
	 */
	private String[] moveDot(String[] packageAndClassName) {
		if (packageAndClassName[0].length() == 0) {
			throw new IllegalArgumentException();
		}
		String segmentToMove;
		String packageName = packageAndClassName[0];
		String className = packageAndClassName[1];
		if (packageName.indexOf('.') == -1) {
			segmentToMove = packageName;
			packageAndClassName[0] = "";
		}
		else {
			int dotIndex = packageName.lastIndexOf('.');
			segmentToMove = packageName.substring(dotIndex + 1, packageName.length());
			packageAndClassName[0] = packageName.substring(0, dotIndex);
		}
		if (className.length() == 0) {
			packageAndClassName[1] = segmentToMove;
		}
		else {
			packageAndClassName[1] = segmentToMove + '.' + className;
		}
		return packageAndClassName;
	}

	public JavaPersistentType findJavaPersistentType() {
		if (this.javaPersistentType == null) {
			IType iType = findJdtType();
			if (iType != null) {
				for (IJpaFile jpaFile : getJpaProject().jpaFiles(JptCorePlugin.JAVA_CONTENT_TYPE)) {
					for (Iterator i = ((JpaCompilationUnit) jpaFile.getContent()).getTypes().iterator(); i.hasNext();) {
						JavaPersistentType javaPersistentType = (JavaPersistentType) i.next();
						if (javaPersistentType.jdtType().equals(iType)) {
							this.javaPersistentType = javaPersistentType;
							break;
						}
					}
				}
			}
		}
		return this.javaPersistentType;
	}

	public Type findType() {
		JavaPersistentType javaPersistentType = findJavaPersistentType();
		return (javaPersistentType == null) ? null : javaPersistentType.getType();
	}

	public Iterator<IPersistentAttribute> attributes() {
		return new ReadOnlyIterator(getPersistentAttributes());
	}

	public Iterator<IPersistentAttribute> allAttributes() {
		return new CompositeIterator(new TransformationIterator(this.inheritanceHierarchy()) {
			protected Object transform(Object next) {
				return ((IPersistentType) next).attributes();
			}
		});
	}

	public Iterator<IPersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator(this) {
			protected Object nextLink(Object currentLink) {
				return ((IPersistentType) currentLink).parentPersistentType();
			}
		};
	}

	public IPersistentType parentPersistentType() {
		return this.parentPersistentType;
	}

	private String superclassTypeSignature() {
		IType javaType = this.findJdtType();
		if (javaType == null) {
			return null;
		}
		try {
			return javaType.getSuperclassTypeSignature();
		}
		catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	public IJpaContentNode getContentNode(int offset) {
		for (Iterator i = getSpecifiedAttributeMappings().iterator(); i.hasNext();) {
			XmlAttributeMapping mapping = (XmlAttributeMapping) i.next();
			if (mapping.getNode().contains(offset)) {
				return mapping.getContentNode(offset);
			}
		}
		return this;
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
		IType jdtType = this.findJdtType();
		if (jdtType == null) {
			this.parentPersistentType = null;
			return;
		}
		String fullyQualifiedTypeName = JDTTools.resolveSignature(superclassTypeSignature, jdtType);
		if (fullyQualifiedTypeName == null) {
			this.parentPersistentType = null;
			return;
		}
		IPersistentType possibleParent = context.persistentType(fullyQualifiedTypeName);
		if (possibleParent != null) {
			if (possibleParent.getMappingKey() != null) {
				this.parentPersistentType = possibleParent;
			}
			else {
				this.parentPersistentType = possibleParent.parentPersistentType();
			}
		}
		else {
			this.parentPersistentType = null;
		}
	}

	public XmlPersistentAttribute attributeNamed(String attributeName) {
		for (XmlPersistentAttribute attribute : getPersistentAttributes()) {
			if (attributeName.equals(attribute.getName())) {
				return attribute;
			}
		}
		return null;
	}

	@Override
	public ITextRange getTextRange() {
		return getMapping().getTextRange();
	}

	public ITextRange getClassTextRange() {
		return getMapping().getClassTextRange();
	}

	public ITextRange getAttributesTextRange() {
		return getMapping().getAttributesTextRange();
	}
	private abstract class AttributeMappingsList<E>
		extends EObjectContainmentEList<XmlAttributeMapping>
	{
		private AttributeMappingsList(int feature) {
			super(XmlAttributeMapping.class, XmlPersistentType.this, feature);
		}

		protected abstract EList<XmlPersistentAttribute> persistentAttributes();

		@Override
		protected void didAdd(int index, XmlAttributeMapping newObject) {
			if (newObject.getPersistentAttribute() == null) {
				throw new IllegalStateException("Must set the PersistentAttribute during creation");
			}
			persistentAttributes().add(index, newObject.getPersistentAttribute());
		}

		@Override
		protected void didChange() {
			// TODO Auto-generated method stub
			super.didChange();
		}

		@Override
		protected void didClear(int size, Object[] oldObjects) {
			persistentAttributes().clear();
		}

		@Override
		protected void didMove(int index, XmlAttributeMapping movedObject, int oldIndex) {
			persistentAttributes().move(index, movedObject.getPersistentAttribute());
		}

		@Override
		protected void didRemove(int index, XmlAttributeMapping oldObject) {
			persistentAttributes().remove(oldObject.getPersistentAttribute());
		}

		@Override
		protected void didSet(int index, XmlAttributeMapping newObject, XmlAttributeMapping oldObject) {
			persistentAttributes().set(index, newObject.getPersistentAttribute());
		}
	}
	private class SpecifiedAttributeMappingsList<E>
		extends AttributeMappingsList<XmlAttributeMapping>
	{
		private SpecifiedAttributeMappingsList() {
			super(OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS);
		}

		@Override
		protected EList<XmlPersistentAttribute> persistentAttributes() {
			return getSpecifiedPersistentAttributes();
		}
	}
	private class VirtualAttributeMappingsList<E>
		extends AttributeMappingsList<XmlAttributeMapping>
	{
		private VirtualAttributeMappingsList() {
			super(OrmPackage.XML_PERSISTENT_TYPE__VIRTUAL_ATTRIBUTE_MAPPINGS);
		}

		@Override
		protected EList<XmlPersistentAttribute> persistentAttributes() {
			return getVirtualPersistentAttributes();
		}
	}
}
