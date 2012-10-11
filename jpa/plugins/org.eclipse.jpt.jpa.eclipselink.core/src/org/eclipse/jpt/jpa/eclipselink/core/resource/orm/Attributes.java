/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attributes</b></em>'.
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getBasicCollections <em>Basic Collections</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getBasicMaps <em>Basic Maps</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getTransformations <em>Transformations</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.Attributes#getVariableOneToOnes <em>Variable One To Ones</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes()
 * @model kind="class"
 * @generated
 */
public class Attributes extends org.eclipse.jpt.jpa.core.resource.orm.Attributes implements XmlAttributes_2_3
{
	/**
	 * The cached value of the '{@link #getStructures() <em>Structures</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructures()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlStructure_2_3> structures;

	/**
	 * The cached value of the '{@link #getArrays() <em>Arrays</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrays()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlArray_2_3> arrays;

	/**
	 * The cached value of the '{@link #getBasicCollections() <em>Basic Collections</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicCollections()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlBasicCollection> basicCollections;

	/**
	 * The cached value of the '{@link #getBasicMaps() <em>Basic Maps</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicMaps()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlBasicMap> basicMaps;

	/**
	 * The cached value of the '{@link #getTransformations() <em>Transformations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformations()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTransformation> transformations;

	/**
	 * The cached value of the '{@link #getVariableOneToOnes() <em>Variable One To Ones</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariableOneToOnes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlVariableOneToOne> variableOneToOnes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Attributes()
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
		return EclipseLinkOrmPackage.Literals.ATTRIBUTES;
	}

	/**
	 * Returns the value of the '<em><b>Structures</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Structures</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structures</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAttributes_2_3_Structures()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlStructure_2_3> getStructures()
	{
		if (structures == null)
		{
			structures = new EObjectContainmentEList<XmlStructure_2_3>(XmlStructure_2_3.class, this, EclipseLinkOrmPackage.ATTRIBUTES__STRUCTURES);
		}
		return structures;
	}

	/**
	 * Returns the value of the '<em><b>Arrays</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arrays</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arrays</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAttributes_2_3_Arrays()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlArray_2_3> getArrays()
	{
		if (arrays == null)
		{
			arrays = new EObjectContainmentEList<XmlArray_2_3>(XmlArray_2_3.class, this, EclipseLinkOrmPackage.ATTRIBUTES__ARRAYS);
		}
		return arrays;
	}

	/**
	 * Returns the value of the '<em><b>Basic Collections</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Basic Collections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic Collections</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes_BasicCollections()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlBasicCollection> getBasicCollections()
	{
		if (basicCollections == null)
		{
			basicCollections = new EObjectContainmentEList<XmlBasicCollection>(XmlBasicCollection.class, this, EclipseLinkOrmPackage.ATTRIBUTES__BASIC_COLLECTIONS);
		}
		return basicCollections;
	}

	/**
	 * Returns the value of the '<em><b>Basic Maps</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Basic Maps</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic Maps</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes_BasicMaps()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlBasicMap> getBasicMaps()
	{
		if (basicMaps == null)
		{
			basicMaps = new EObjectContainmentEList<XmlBasicMap>(XmlBasicMap.class, this, EclipseLinkOrmPackage.ATTRIBUTES__BASIC_MAPS);
		}
		return basicMaps;
	}

	/**
	 * Returns the value of the '<em><b>Transformations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformations</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes_Transformations()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlTransformation> getTransformations()
	{
		if (transformations == null)
		{
			transformations = new EObjectContainmentEList<XmlTransformation>(XmlTransformation.class, this, EclipseLinkOrmPackage.ATTRIBUTES__TRANSFORMATIONS);
		}
		return transformations;
	}

	/**
	 * Returns the value of the '<em><b>Variable One To Ones</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable One To Ones</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable One To Ones</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes_VariableOneToOnes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlVariableOneToOne> getVariableOneToOnes()
	{
		if (variableOneToOnes == null)
		{
			variableOneToOnes = new EObjectContainmentEList<XmlVariableOneToOne>(XmlVariableOneToOne.class, this, EclipseLinkOrmPackage.ATTRIBUTES__VARIABLE_ONE_TO_ONES);
		}
		return variableOneToOnes;
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
			case EclipseLinkOrmPackage.ATTRIBUTES__STRUCTURES:
				return ((InternalEList<?>)getStructures()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.ATTRIBUTES__ARRAYS:
				return ((InternalEList<?>)getArrays()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_COLLECTIONS:
				return ((InternalEList<?>)getBasicCollections()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_MAPS:
				return ((InternalEList<?>)getBasicMaps()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.ATTRIBUTES__TRANSFORMATIONS:
				return ((InternalEList<?>)getTransformations()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.ATTRIBUTES__VARIABLE_ONE_TO_ONES:
				return ((InternalEList<?>)getVariableOneToOnes()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.ATTRIBUTES__STRUCTURES:
				return getStructures();
			case EclipseLinkOrmPackage.ATTRIBUTES__ARRAYS:
				return getArrays();
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_COLLECTIONS:
				return getBasicCollections();
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_MAPS:
				return getBasicMaps();
			case EclipseLinkOrmPackage.ATTRIBUTES__TRANSFORMATIONS:
				return getTransformations();
			case EclipseLinkOrmPackage.ATTRIBUTES__VARIABLE_ONE_TO_ONES:
				return getVariableOneToOnes();
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
			case EclipseLinkOrmPackage.ATTRIBUTES__STRUCTURES:
				getStructures().clear();
				getStructures().addAll((Collection<? extends XmlStructure_2_3>)newValue);
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__ARRAYS:
				getArrays().clear();
				getArrays().addAll((Collection<? extends XmlArray_2_3>)newValue);
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_COLLECTIONS:
				getBasicCollections().clear();
				getBasicCollections().addAll((Collection<? extends XmlBasicCollection>)newValue);
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_MAPS:
				getBasicMaps().clear();
				getBasicMaps().addAll((Collection<? extends XmlBasicMap>)newValue);
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__TRANSFORMATIONS:
				getTransformations().clear();
				getTransformations().addAll((Collection<? extends XmlTransformation>)newValue);
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__VARIABLE_ONE_TO_ONES:
				getVariableOneToOnes().clear();
				getVariableOneToOnes().addAll((Collection<? extends XmlVariableOneToOne>)newValue);
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
			case EclipseLinkOrmPackage.ATTRIBUTES__STRUCTURES:
				getStructures().clear();
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__ARRAYS:
				getArrays().clear();
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_COLLECTIONS:
				getBasicCollections().clear();
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_MAPS:
				getBasicMaps().clear();
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__TRANSFORMATIONS:
				getTransformations().clear();
				return;
			case EclipseLinkOrmPackage.ATTRIBUTES__VARIABLE_ONE_TO_ONES:
				getVariableOneToOnes().clear();
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
			case EclipseLinkOrmPackage.ATTRIBUTES__STRUCTURES:
				return structures != null && !structures.isEmpty();
			case EclipseLinkOrmPackage.ATTRIBUTES__ARRAYS:
				return arrays != null && !arrays.isEmpty();
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_COLLECTIONS:
				return basicCollections != null && !basicCollections.isEmpty();
			case EclipseLinkOrmPackage.ATTRIBUTES__BASIC_MAPS:
				return basicMaps != null && !basicMaps.isEmpty();
			case EclipseLinkOrmPackage.ATTRIBUTES__TRANSFORMATIONS:
				return transformations != null && !transformations.isEmpty();
			case EclipseLinkOrmPackage.ATTRIBUTES__VARIABLE_ONE_TO_ONES:
				return variableOneToOnes != null && !variableOneToOnes.isEmpty();
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
		if (baseClass == XmlAttributes_2_3.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.ATTRIBUTES__STRUCTURES: return EclipseLinkOrmV2_3Package.XML_ATTRIBUTES_23__STRUCTURES;
				case EclipseLinkOrmPackage.ATTRIBUTES__ARRAYS: return EclipseLinkOrmV2_3Package.XML_ATTRIBUTES_23__ARRAYS;
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
		if (baseClass == XmlAttributes_2_3.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_3Package.XML_ATTRIBUTES_23__STRUCTURES: return EclipseLinkOrmPackage.ATTRIBUTES__STRUCTURES;
				case EclipseLinkOrmV2_3Package.XML_ATTRIBUTES_23__ARRAYS: return EclipseLinkOrmPackage.ATTRIBUTES__ARRAYS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public List<XmlAttributeMapping> getAttributeMappings() {
		// convert lists to arrays to avoid ConcurrentModificationException while adding to result list
		ArrayList<XmlAttributeMapping> attributeMappings = new ArrayList<XmlAttributeMapping>();
		CollectionTools.addAll(attributeMappings, this.getIds().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getEmbeddedIds().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getBasics().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getBasicCollections().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getBasicMaps().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getVersions().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getManyToOnes().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getOneToManys().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getOneToOnes().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getVariableOneToOnes().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getManyToManys().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getElementCollections().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getEmbeddeds().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getTransformations().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getTransients().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getArrays().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getStructures().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		return attributeMappings;
	}

	protected static final XmlAttributeMapping[] EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY = new XmlAttributeMapping[0];

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLinkOrmPackage.eINSTANCE.getAttributes(), 
			buildTranslatorChildren());
	}
	
	public static Translator buildTranslator() {
		return buildTranslator(
			EclipseLink.ATTRIBUTES, 
			OrmPackage.eINSTANCE.getXmlTypeMapping_Attributes());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			XmlId.buildTranslator(EclipseLink2_0.ID, OrmPackage.eINSTANCE.getAttributes_Ids()),
			XmlEmbeddedId.buildTranslator(EclipseLink2_0.EMBEDDED_ID, OrmPackage.eINSTANCE.getAttributes_EmbeddedIds()),
			XmlBasic.buildTranslator(EclipseLink2_0.BASIC, OrmPackage.eINSTANCE.getAttributes_Basics()),
			XmlBasicCollection.buildTranslator(EclipseLink2_0.BASIC_COLLECTION, EclipseLinkOrmPackage.eINSTANCE.getAttributes_BasicCollections()),
			XmlBasicMap.buildTranslator(EclipseLink2_0.BASIC_MAP, EclipseLinkOrmPackage.eINSTANCE.getAttributes_BasicMaps()),
			XmlVersion.buildTranslator(EclipseLink2_0.VERSION, OrmPackage.eINSTANCE.getAttributes_Versions()),
			XmlManyToOne.buildTranslator(EclipseLink2_0.MANY_TO_ONE, OrmPackage.eINSTANCE.getAttributes_ManyToOnes()),
			XmlOneToMany.buildTranslator(EclipseLink2_0.ONE_TO_MANY, OrmPackage.eINSTANCE.getAttributes_OneToManys()),
			XmlOneToOne.buildTranslator(EclipseLink2_0.ONE_TO_ONE, OrmPackage.eINSTANCE.getAttributes_OneToOnes()),
			XmlVariableOneToOne.buildTranslator(EclipseLink2_0.VARIABLE_ONE_TO_ONE, EclipseLinkOrmPackage.eINSTANCE.getAttributes_VariableOneToOnes()),
			XmlManyToMany.buildTranslator(EclipseLink2_0.MANY_TO_MANY, OrmPackage.eINSTANCE.getAttributes_ManyToManys()),
			XmlElementCollection.buildTranslator(EclipseLink2_0.ELEMENT_COLLECTION, OrmV2_0Package.eINSTANCE.getXmlAttributes_2_0_ElementCollections()),
			XmlEmbedded.buildTranslator(EclipseLink2_0.EMBEDDED, OrmPackage.eINSTANCE.getAttributes_Embeddeds()),
			XmlTransformation.buildTranslator(EclipseLink2_0.TRANSFORMATION, EclipseLinkOrmPackage.eINSTANCE.getAttributes_Transformations()),
			XmlTransient.buildTranslator(EclipseLink2_0.TRANSIENT, OrmPackage.eINSTANCE.getAttributes_Transients()),
			XmlStructure.buildTranslator(EclipseLink2_3.STRUCTURE, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlAttributes_2_3_Structures()),
			XmlArray.buildTranslator(EclipseLink2_3.ARRAY, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlAttributes_2_3_Arrays()),
		};
	}
}
