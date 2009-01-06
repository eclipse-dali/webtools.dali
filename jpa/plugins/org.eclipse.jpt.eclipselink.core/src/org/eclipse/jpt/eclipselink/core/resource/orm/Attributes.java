/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

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
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.Attributes#getBasicCollections <em>Basic Collections</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.Attributes#getBasicMaps <em>Basic Maps</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.Attributes#getTransformations <em>Transformations</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.Attributes#getVariableOneToOnes <em>Variable One To Ones</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes()
 * @model kind="class"
 * @generated
 */
public class Attributes extends org.eclipse.jpt.core.resource.orm.Attributes
{
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
	 * Returns the value of the '<em><b>Basic Collections</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Basic Collections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic Collections</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes_BasicCollections()
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
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Basic Maps</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic Maps</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes_BasicMaps()
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
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformations</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes_Transformations()
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
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable One To Ones</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable One To Ones</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getAttributes_VariableOneToOnes()
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
	
	@Override
	public List<XmlAttributeMapping> getAttributeMappings() {
		List<XmlAttributeMapping> attributeMappings = new ArrayList<XmlAttributeMapping>();
		ListIterator<XmlId> ids = new CloneListIterator<XmlId>(this.getIds());//prevent ConcurrentModificiationException
		for (XmlId mapping : CollectionTools.iterable(ids)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlEmbeddedId> embeddedIds = new CloneListIterator<XmlEmbeddedId>(this.getEmbeddedIds());//prevent ConcurrentModificiationException
		for (XmlEmbeddedId mapping : CollectionTools.iterable(embeddedIds)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlBasic> basics = new CloneListIterator<XmlBasic>(this.getBasics());//prevent ConcurrentModificiationException
		for (XmlBasic mapping : CollectionTools.iterable(basics)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlBasicCollection> basicCollections = new CloneListIterator<XmlBasicCollection>(this.getBasicCollections());//prevent ConcurrentModificiationException
		for (XmlBasicCollection mapping : CollectionTools.iterable(basicCollections)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlBasicMap> basicMaps = new CloneListIterator<XmlBasicMap>(this.getBasicMaps());//prevent ConcurrentModificiationException
		for (XmlBasicMap mapping : CollectionTools.iterable(basicMaps)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlVersion> versions = new CloneListIterator<XmlVersion>(this.getVersions());//prevent ConcurrentModificiationException
		for (XmlVersion mapping : CollectionTools.iterable(versions)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlManyToOne> manyToOnes = new CloneListIterator<XmlManyToOne>(this.getManyToOnes());//prevent ConcurrentModificiationException
		for (XmlManyToOne mapping : CollectionTools.iterable(manyToOnes)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlOneToMany> oneToManys = new CloneListIterator<XmlOneToMany>(this.getOneToManys());//prevent ConcurrentModificiationException
		for (XmlOneToMany mapping : CollectionTools.iterable(oneToManys)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlOneToOne> oneToOnes = new CloneListIterator<XmlOneToOne>(this.getOneToOnes());//prevent ConcurrentModificiationException
		for (XmlOneToOne mapping : CollectionTools.iterable(oneToOnes)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlVariableOneToOne> variableOneToOnes = new CloneListIterator<XmlVariableOneToOne>(this.getVariableOneToOnes());//prevent ConcurrentModificiationException
		for (XmlVariableOneToOne mapping : CollectionTools.iterable(variableOneToOnes)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlManyToMany> manyToManys = new CloneListIterator<XmlManyToMany>(this.getManyToManys());//prevent ConcurrentModificiationException
		for (XmlManyToMany mapping : CollectionTools.iterable(manyToManys)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlEmbedded> embeddeds = new CloneListIterator<XmlEmbedded>(this.getEmbeddeds());//prevent ConcurrentModificiationException
		for (XmlEmbedded mapping : CollectionTools.iterable(embeddeds)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlTransformation> transformations = new CloneListIterator<XmlTransformation>(this.getTransformations());//prevent ConcurrentModificiationException
		for (XmlTransformation mapping : CollectionTools.iterable(transformations)) {
			attributeMappings.add(mapping);
		}
		ListIterator<XmlTransient> transients = new CloneListIterator<XmlTransient>(this.getTransients());//prevent ConcurrentModificiationException
		for (XmlTransient mapping : CollectionTools.iterable(transients)) {
			attributeMappings.add(mapping);
		}
		return attributeMappings;
	}


} // Attributes
