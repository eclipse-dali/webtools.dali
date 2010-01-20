/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOneToMany_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>One To Many</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOneToMany()
 * @model kind="class"
 * @generated
 */
public class XmlOneToMany extends AbstractXmlMultiRelationshipMapping implements XmlJoinColumnsMapping, XmlOneToMany_2_0
{

	/**
	 * The cached value of the '{@link #getJoinColumns() <em>Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> joinColumns;

	/**
	 * The default value of the '{@link #getOrphanRemoval() <em>Orphan Removal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrphanRemoval()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ORPHAN_REMOVAL_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOrphanRemoval() <em>Orphan Removal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrphanRemoval()
	 * @generated
	 * @ordered
	 */
	protected Boolean orphanRemoval = ORPHAN_REMOVAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlOneToMany()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OrmPackage.Literals.XML_ONE_TO_MANY;
	}

	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinColumnsMapping_JoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getJoinColumns()
	{
		if (joinColumns == null)
		{
			joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS);
		}
		return joinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orphan Removal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orphan Removal</em>' attribute.
	 * @see #setOrphanRemoval(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrphanRemovable_2_0_OrphanRemoval()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getOrphanRemoval()
	{
		return orphanRemoval;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlOneToMany#getOrphanRemoval <em>Orphan Removal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orphan Removal</em>' attribute.
	 * @see #getOrphanRemoval()
	 * @generated
	 */
	public void setOrphanRemoval(Boolean newOrphanRemoval)
	{
		Boolean oldOrphanRemoval = orphanRemoval;
		orphanRemoval = newOrphanRemoval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL, oldOrphanRemoval, orphanRemoval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				return ((InternalEList<?>)getJoinColumns()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				return getJoinColumns();
			case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL:
				return getOrphanRemoval();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				getJoinColumns().clear();
				getJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
				return;
			case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL:
				setOrphanRemoval((Boolean)newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				getJoinColumns().clear();
				return;
			case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL:
				setOrphanRemoval(ORPHAN_REMOVAL_EDEFAULT);
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				return joinColumns != null && !joinColumns.isEmpty();
			case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL:
				return ORPHAN_REMOVAL_EDEFAULT == null ? orphanRemoval != null : !ORPHAN_REMOVAL_EDEFAULT.equals(orphanRemoval);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlJoinColumnsMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS: return OrmPackage.XML_JOIN_COLUMNS_MAPPING__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlOrphanRemovable_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL: return OrmV2_0Package.XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL;
				default: return -1;
			}
		}
		if (baseClass == XmlOneToMany_2_0.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlJoinColumnsMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_JOIN_COLUMNS_MAPPING__JOIN_COLUMNS: return OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlOrphanRemovable_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL: return OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL;
				default: return -1;
			}
		}
		if (baseClass == XmlOneToMany_2_0.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
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
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (orphanRemoval: ");
		result.append(orphanRemoval);
		result.append(')');
		return result.toString();
	}

	public String getMappingKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildTargetEntityTranslator(),
			buildFetchTranslator(),
			buildAccessTranslator(),
			buildMappedByTranslator(),
			buildOrphanRemovalTranslator(),
			buildOrderByTranslator(),
			buildOrderColumnTranslator(),		
			buildMapKeyTranslator(),
			buildMapKeyClassTranslator(),		
			buildMapKeyTemporalTranslator(),
			buildMapKeyEnumeratedTranslator(),
			buildMapKeyAttributeOverrideTranslator(),		
			buildMapKeyColumnTranslator(),		
			buildMapKeyJoinColumnTranslator(),		
			buildJoinTableTranslator(),
			buildJoinColumnTranslator(),
			buildCascadeTranslator()
		};
	}
	
	protected static Translator buildMappedByTranslator() {
		return new Translator(JPA.MAPPED_BY, OrmPackage.eINSTANCE.getXmlMappedByMapping_MappedBy(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildOrphanRemovalTranslator() {
		return new Translator(JPA2_0.ORPHAN_REMOVAL, OrmV2_0Package.eINSTANCE.getXmlOrphanRemovable_2_0_OrphanRemoval(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildOrderColumnTranslator() {
		return XmlOrderColumn.buildTranslator(JPA2_0.ORDER_COLUMN, OrmV2_0Package.eINSTANCE.getXmlOrderable_2_0_OrderColumn());
	}
	
	protected static Translator buildMapKeyTemporalTranslator() {
		return new Translator(JPA2_0.MAP_KEY_TEMPORAL, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyTemporal());
	}
	
	protected static Translator buildMapKeyEnumeratedTranslator() {
		return new Translator(JPA2_0.MAP_KEY_ENUMERATED, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyEnumerated());
	}
	
	protected static Translator buildMapKeyClassTranslator() {
		return XmlMapKeyClass.buildTranslator(JPA2_0.MAP_KEY_CLASS, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyClass());
	}

	protected static Translator buildMapKeyAttributeOverrideTranslator() {
		return XmlAttributeOverride.buildTranslator(JPA2_0.MAP_KEY_ATTRIBUTE_OVERRIDE, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyAttributeOverrides());
	}
	
	protected static Translator buildMapKeyColumnTranslator() {
		return XmlColumn.buildTranslator(JPA2_0.MAP_KEY_COLUMN, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyColumn());
	}
	
	protected static Translator buildMapKeyJoinColumnTranslator() {
		return XmlJoinColumn.buildTranslator(JPA2_0.MAP_KEY_JOIN_COLUMN, OrmV2_0Package.eINSTANCE.getXmlMultiRelationshipMapping_2_0_MapKeyJoinColumns());
	}
	
	protected static Translator buildJoinTableTranslator() {
		return XmlJoinTable.buildTranslator(JPA.JOIN_TABLE, OrmPackage.eINSTANCE.getXmlJoinTableMapping_JoinTable());
	}
	
	protected static Translator buildJoinColumnTranslator() {
		return XmlJoinColumn.buildTranslator(JPA.JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlJoinColumnsMapping_JoinColumns());
	}
}
