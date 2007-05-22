/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEmbeddable;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Embedded</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEmbedded()
 * @model kind="class"
 * @generated
 */
public class XmlEmbedded extends XmlAttributeMapping implements IEmbedded
{
	/**
	 * The cached value of the '{@link #getSpecifiedAttributeOverrides() <em>Specified Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAttributeOverride> specifiedAttributeOverrides;

	/**
	 * The cached value of the '{@link #getDefaultAttributeOverrides() <em>Default Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAttributeOverride> defaultAttributeOverrides;

	private IEmbeddable embeddable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEmbedded() {
		super();
	}

	
	@Override
	protected void addInsignificantFeatureIdsTo(Set<Integer> insignificantFeatureIds) {
		super.addInsignificantFeatureIdsTo(insignificantFeatureIds);
		insignificantFeatureIds.add(OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES);
	}

	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlEmbeddedMapping(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_EMBEDDED;
	}

	public EList<IAttributeOverride> getAttributeOverrides() {
		EList<IAttributeOverride> list = new EObjectEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES);
		list.addAll(getSpecifiedAttributeOverrides());
		list.addAll(getDefaultAttributeOverrides());
		return list;
	}

	/**
	 * Returns the value of the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEmbedded_SpecifiedAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
		if (specifiedAttributeOverrides == null) {
			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES);
		}
		return specifiedAttributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEmbedded_DefaultAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
		if (defaultAttributeOverrides == null) {
			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES);
		}
		return defaultAttributeOverrides;
	}

	public IEmbeddable embeddable() {
		return this.embeddable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getSpecifiedAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getDefaultAttributeOverrides()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES :
				return getAttributeOverrides();
			case OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return getSpecifiedAttributeOverrides();
			case OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				return getDefaultAttributeOverrides();
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
			case OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				getSpecifiedAttributeOverrides().clear();
				getSpecifiedAttributeOverrides().addAll((Collection<? extends IAttributeOverride>) newValue);
				return;
			case OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				getDefaultAttributeOverrides().clear();
				getDefaultAttributeOverrides().addAll((Collection<? extends IAttributeOverride>) newValue);
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
			case OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				getSpecifiedAttributeOverrides().clear();
				return;
			case OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				getDefaultAttributeOverrides().clear();
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
			case OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES :
				return !getAttributeOverrides().isEmpty();
			case OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return specifiedAttributeOverrides != null && !specifiedAttributeOverrides.isEmpty();
			case OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				return defaultAttributeOverrides != null && !defaultAttributeOverrides.isEmpty();
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
		if (baseClass == IEmbedded.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IEMBEDDED__ATTRIBUTE_OVERRIDES;
				case OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES;
				case OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IEMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES;
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
		if (baseClass == IEmbedded.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IEMBEDDED__ATTRIBUTE_OVERRIDES :
					return OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
					return OrmPackage.XML_EMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IEMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
					return OrmPackage.XML_EMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public int xmlSequence() {
		return 7;
	}

	public String getKey() {
		return IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public boolean containsAttributeOverride(String name) {
		return containsAttributeOverride(name, getAttributeOverrides());
	}

	public boolean containsSpecifiedAttributeOverride(String name) {
		return containsAttributeOverride(name, getSpecifiedAttributeOverrides());
	}

	private boolean containsAttributeOverride(String name, List<IAttributeOverride> attributeOverrides) {
		for (IAttributeOverride attributeOverride : attributeOverrides) {
			String attributeOverrideName = attributeOverride.getName();
			if (attributeOverrideName != null && attributeOverrideName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new TransformationIterator<IPersistentAttribute, String>(this.allOverridableAttributes()) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	public Iterator<IPersistentAttribute> allOverridableAttributes() {
		if (this.embeddable() == null) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<IPersistentAttribute>(this.embeddable().getPersistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAttribute();
			}
		};
	}

	public IAttributeOverride createAttributeOverride(int index) {
		return OrmFactory.eINSTANCE.createXmlAttributeOverride(new AttributeOverrideOwner(this));
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		refreshEmbeddable(defaultsContext);
	}

	private void refreshEmbeddable(DefaultsContext defaultsContext) {
		this.embeddable = embeddableFor(getPersistentAttribute().getAttribute(), defaultsContext);
	}

	//******* static methods *********
	public static IEmbeddable embeddableFor(Attribute attribute, DefaultsContext defaultsContext) {
		if (attribute == null) {
			return null;
		}
		String resolvedTypeName = attribute.resolvedTypeName();
		if (resolvedTypeName == null) {
			return null;
		}
		IPersistentType persistentType = defaultsContext.persistentType(resolvedTypeName);
		if (persistentType != null) {
			if (persistentType.getMapping() instanceof IEmbeddable) {
				return (IEmbeddable) persistentType.getMapping();
			}
		}
		return null;
	}
} // XmlEmbedded
