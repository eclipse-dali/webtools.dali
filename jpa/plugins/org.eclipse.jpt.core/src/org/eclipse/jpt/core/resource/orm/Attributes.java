/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

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
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.jpt.utility.internal.CollectionTools;
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
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getIds <em>Ids</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getEmbeddedIds <em>Embedded Ids</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getBasics <em>Basics</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getVersions <em>Versions</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getManyToOnes <em>Many To Ones</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getOneToManys <em>One To Manys</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getOneToOnes <em>One To Ones</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getManyToManys <em>Many To Manys</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getEmbeddeds <em>Embeddeds</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.Attributes#getTransients <em>Transients</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class Attributes extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The cached value of the '{@link #getIds() <em>Ids</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIds()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlId> ids;

	/**
	 * The cached value of the '{@link #getEmbeddedIds() <em>Embedded Ids</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmbeddedIds()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlEmbeddedId> embeddedIds;

	/**
	 * The cached value of the '{@link #getBasics() <em>Basics</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasics()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlBasic> basics;

	/**
	 * The cached value of the '{@link #getVersions() <em>Versions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlVersion> versions;

	/**
	 * The cached value of the '{@link #getManyToOnes() <em>Many To Ones</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyToOnes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlManyToOne> manyToOnes;

	/**
	 * The cached value of the '{@link #getOneToManys() <em>One To Manys</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOneToManys()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlOneToMany> oneToManys;

	/**
	 * The cached value of the '{@link #getOneToOnes() <em>One To Ones</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOneToOnes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlOneToOne> oneToOnes;

	/**
	 * The cached value of the '{@link #getManyToManys() <em>Many To Manys</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyToManys()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlManyToMany> manyToManys;

	/**
	 * The cached value of the '{@link #getEmbeddeds() <em>Embeddeds</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmbeddeds()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlEmbedded> embeddeds;

	/**
	 * The cached value of the '{@link #getTransients() <em>Transients</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransients()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTransient> transients;

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
		return OrmPackage.Literals.ATTRIBUTES;
	}

	/**
	 * Returns the value of the '<em><b>Ids</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlId}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ids</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ids</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_Ids()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlId> getIds()
	{
		if (ids == null)
		{
			ids = new EObjectContainmentEList<XmlId>(XmlId.class, this, OrmPackage.ATTRIBUTES__IDS);
		}
		return ids;
	}

	/**
	 * Returns the value of the '<em><b>Embedded Ids</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlEmbeddedId}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embedded Ids</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embedded Ids</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_EmbeddedIds()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlEmbeddedId> getEmbeddedIds()
	{
		if (embeddedIds == null)
		{
			embeddedIds = new EObjectContainmentEList<XmlEmbeddedId>(XmlEmbeddedId.class, this, OrmPackage.ATTRIBUTES__EMBEDDED_IDS);
		}
		return embeddedIds;
	}

	/**
	 * Returns the value of the '<em><b>Basics</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlBasic}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Basics</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basics</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_Basics()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlBasic> getBasics()
	{
		if (basics == null)
		{
			basics = new EObjectContainmentEList<XmlBasic>(XmlBasic.class, this, OrmPackage.ATTRIBUTES__BASICS);
		}
		return basics;
	}

	/**
	 * Returns the value of the '<em><b>Versions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlVersion}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Versions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Versions</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_Versions()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlVersion> getVersions()
	{
		if (versions == null)
		{
			versions = new EObjectContainmentEList<XmlVersion>(XmlVersion.class, this, OrmPackage.ATTRIBUTES__VERSIONS);
		}
		return versions;
	}

	/**
	 * Returns the value of the '<em><b>Many To Ones</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlManyToOne}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Many To Ones</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many To Ones</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_ManyToOnes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlManyToOne> getManyToOnes()
	{
		if (manyToOnes == null)
		{
			manyToOnes = new EObjectContainmentEList<XmlManyToOne>(XmlManyToOne.class, this, OrmPackage.ATTRIBUTES__MANY_TO_ONES);
		}
		return manyToOnes;
	}

	/**
	 * Returns the value of the '<em><b>One To Manys</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlOneToMany}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>One To Manys</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One To Manys</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_OneToManys()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlOneToMany> getOneToManys()
	{
		if (oneToManys == null)
		{
			oneToManys = new EObjectContainmentEList<XmlOneToMany>(XmlOneToMany.class, this, OrmPackage.ATTRIBUTES__ONE_TO_MANYS);
		}
		return oneToManys;
	}

	/**
	 * Returns the value of the '<em><b>One To Ones</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlOneToOne}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>One To Ones</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One To Ones</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_OneToOnes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlOneToOne> getOneToOnes()
	{
		if (oneToOnes == null)
		{
			oneToOnes = new EObjectContainmentEList<XmlOneToOne>(XmlOneToOne.class, this, OrmPackage.ATTRIBUTES__ONE_TO_ONES);
		}
		return oneToOnes;
	}

	/**
	 * Returns the value of the '<em><b>Many To Manys</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlManyToMany}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Many To Manys</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many To Manys</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_ManyToManys()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlManyToMany> getManyToManys()
	{
		if (manyToManys == null)
		{
			manyToManys = new EObjectContainmentEList<XmlManyToMany>(XmlManyToMany.class, this, OrmPackage.ATTRIBUTES__MANY_TO_MANYS);
		}
		return manyToManys;
	}

	/**
	 * Returns the value of the '<em><b>Embeddeds</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlEmbedded}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embeddeds</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embeddeds</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_Embeddeds()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlEmbedded> getEmbeddeds()
	{
		if (embeddeds == null)
		{
			embeddeds = new EObjectContainmentEList<XmlEmbedded>(XmlEmbedded.class, this, OrmPackage.ATTRIBUTES__EMBEDDEDS);
		}
		return embeddeds;
	}

	/**
	 * Returns the value of the '<em><b>Transients</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlTransient}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transients</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transients</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAttributes_Transients()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlTransient> getTransients()
	{
		if (transients == null)
		{
			transients = new EObjectContainmentEList<XmlTransient>(XmlTransient.class, this, OrmPackage.ATTRIBUTES__TRANSIENTS);
		}
		return transients;
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
			case OrmPackage.ATTRIBUTES__IDS:
				return ((InternalEList<?>)getIds()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__EMBEDDED_IDS:
				return ((InternalEList<?>)getEmbeddedIds()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__BASICS:
				return ((InternalEList<?>)getBasics()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__VERSIONS:
				return ((InternalEList<?>)getVersions()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__MANY_TO_ONES:
				return ((InternalEList<?>)getManyToOnes()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__ONE_TO_MANYS:
				return ((InternalEList<?>)getOneToManys()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__ONE_TO_ONES:
				return ((InternalEList<?>)getOneToOnes()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__MANY_TO_MANYS:
				return ((InternalEList<?>)getManyToManys()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__EMBEDDEDS:
				return ((InternalEList<?>)getEmbeddeds()).basicRemove(otherEnd, msgs);
			case OrmPackage.ATTRIBUTES__TRANSIENTS:
				return ((InternalEList<?>)getTransients()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.ATTRIBUTES__IDS:
				return getIds();
			case OrmPackage.ATTRIBUTES__EMBEDDED_IDS:
				return getEmbeddedIds();
			case OrmPackage.ATTRIBUTES__BASICS:
				return getBasics();
			case OrmPackage.ATTRIBUTES__VERSIONS:
				return getVersions();
			case OrmPackage.ATTRIBUTES__MANY_TO_ONES:
				return getManyToOnes();
			case OrmPackage.ATTRIBUTES__ONE_TO_MANYS:
				return getOneToManys();
			case OrmPackage.ATTRIBUTES__ONE_TO_ONES:
				return getOneToOnes();
			case OrmPackage.ATTRIBUTES__MANY_TO_MANYS:
				return getManyToManys();
			case OrmPackage.ATTRIBUTES__EMBEDDEDS:
				return getEmbeddeds();
			case OrmPackage.ATTRIBUTES__TRANSIENTS:
				return getTransients();
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
			case OrmPackage.ATTRIBUTES__IDS:
				getIds().clear();
				getIds().addAll((Collection<? extends XmlId>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__EMBEDDED_IDS:
				getEmbeddedIds().clear();
				getEmbeddedIds().addAll((Collection<? extends XmlEmbeddedId>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__BASICS:
				getBasics().clear();
				getBasics().addAll((Collection<? extends XmlBasic>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__VERSIONS:
				getVersions().clear();
				getVersions().addAll((Collection<? extends XmlVersion>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__MANY_TO_ONES:
				getManyToOnes().clear();
				getManyToOnes().addAll((Collection<? extends XmlManyToOne>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__ONE_TO_MANYS:
				getOneToManys().clear();
				getOneToManys().addAll((Collection<? extends XmlOneToMany>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__ONE_TO_ONES:
				getOneToOnes().clear();
				getOneToOnes().addAll((Collection<? extends XmlOneToOne>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__MANY_TO_MANYS:
				getManyToManys().clear();
				getManyToManys().addAll((Collection<? extends XmlManyToMany>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__EMBEDDEDS:
				getEmbeddeds().clear();
				getEmbeddeds().addAll((Collection<? extends XmlEmbedded>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__TRANSIENTS:
				getTransients().clear();
				getTransients().addAll((Collection<? extends XmlTransient>)newValue);
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
			case OrmPackage.ATTRIBUTES__IDS:
				getIds().clear();
				return;
			case OrmPackage.ATTRIBUTES__EMBEDDED_IDS:
				getEmbeddedIds().clear();
				return;
			case OrmPackage.ATTRIBUTES__BASICS:
				getBasics().clear();
				return;
			case OrmPackage.ATTRIBUTES__VERSIONS:
				getVersions().clear();
				return;
			case OrmPackage.ATTRIBUTES__MANY_TO_ONES:
				getManyToOnes().clear();
				return;
			case OrmPackage.ATTRIBUTES__ONE_TO_MANYS:
				getOneToManys().clear();
				return;
			case OrmPackage.ATTRIBUTES__ONE_TO_ONES:
				getOneToOnes().clear();
				return;
			case OrmPackage.ATTRIBUTES__MANY_TO_MANYS:
				getManyToManys().clear();
				return;
			case OrmPackage.ATTRIBUTES__EMBEDDEDS:
				getEmbeddeds().clear();
				return;
			case OrmPackage.ATTRIBUTES__TRANSIENTS:
				getTransients().clear();
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
			case OrmPackage.ATTRIBUTES__IDS:
				return ids != null && !ids.isEmpty();
			case OrmPackage.ATTRIBUTES__EMBEDDED_IDS:
				return embeddedIds != null && !embeddedIds.isEmpty();
			case OrmPackage.ATTRIBUTES__BASICS:
				return basics != null && !basics.isEmpty();
			case OrmPackage.ATTRIBUTES__VERSIONS:
				return versions != null && !versions.isEmpty();
			case OrmPackage.ATTRIBUTES__MANY_TO_ONES:
				return manyToOnes != null && !manyToOnes.isEmpty();
			case OrmPackage.ATTRIBUTES__ONE_TO_MANYS:
				return oneToManys != null && !oneToManys.isEmpty();
			case OrmPackage.ATTRIBUTES__ONE_TO_ONES:
				return oneToOnes != null && !oneToOnes.isEmpty();
			case OrmPackage.ATTRIBUTES__MANY_TO_MANYS:
				return manyToManys != null && !manyToManys.isEmpty();
			case OrmPackage.ATTRIBUTES__EMBEDDEDS:
				return embeddeds != null && !embeddeds.isEmpty();
			case OrmPackage.ATTRIBUTES__TRANSIENTS:
				return transients != null && !transients.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	public List<XmlAttributeMapping> getAttributeMappings() {
		// convert lists to arrays to avoid ConcurrentModificationException while adding to result list
		ArrayList<XmlAttributeMapping> attributeMappings = new ArrayList<XmlAttributeMapping>();
		CollectionTools.addAll(attributeMappings, this.getIds().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getEmbeddedIds().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getBasics().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getVersions().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getManyToOnes().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getOneToManys().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getOneToOnes().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getManyToManys().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getEmbeddeds().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		CollectionTools.addAll(attributeMappings, this.getTransients().toArray(EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY));
		return attributeMappings;
	}

	private static final XmlAttributeMapping[] EMPTY_XML_ATTRIBUTE_MAPPING_ARRAY = new XmlAttributeMapping[0];

	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			XmlId.buildTranslator(JPA.ID, OrmPackage.eINSTANCE.getAttributes_Ids()),
			XmlEmbeddedId.buildTranslator(JPA.EMBEDDED_ID, OrmPackage.eINSTANCE.getAttributes_EmbeddedIds()),
			XmlBasic.buildTranslator(JPA.BASIC, OrmPackage.eINSTANCE.getAttributes_Basics()),
			XmlVersion.buildTranslator(JPA.VERSION, OrmPackage.eINSTANCE.getAttributes_Versions()),
			XmlManyToOne.buildTranslator(JPA.MANY_TO_ONE, OrmPackage.eINSTANCE.getAttributes_ManyToOnes()),
			XmlOneToMany.buildTranslator(JPA.ONE_TO_MANY, OrmPackage.eINSTANCE.getAttributes_OneToManys()),
			XmlOneToOne.buildTranslator(JPA.ONE_TO_ONE, OrmPackage.eINSTANCE.getAttributes_OneToOnes()),
			XmlManyToMany.buildTranslator(JPA.MANY_TO_MANY, OrmPackage.eINSTANCE.getAttributes_ManyToManys()),
			XmlEmbedded.buildTranslator(JPA.EMBEDDED, OrmPackage.eINSTANCE.getAttributes_Embeddeds()),
			buildTransientTranslator()
		};
	}
	
	protected static Translator buildTransientTranslator() {
		return XmlTransient.buildTranslator(JPA.TRANSIENT, OrmPackage.eINSTANCE.getAttributes_Transients());
	}
} // Attributes
