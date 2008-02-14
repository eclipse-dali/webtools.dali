/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.resource.common.IJpaEObject;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getIds <em>Ids</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getEmbeddedIds <em>Embedded Ids</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getBasics <em>Basics</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getVersions <em>Versions</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getManyToOnes <em>Many To Ones</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getOneToManys <em>One To Manys</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getOneToOnes <em>One To Ones</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getManyToManys <em>Many To Manys</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getEmbeddeds <em>Embeddeds</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Attributes#getTransients <em>Transients</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes()
 * @model kind="class"
 * @extends IJpaEObject
 * @generated
 */
public class Attributes extends JpaEObject implements IJpaEObject
{
	/**
	 * The cached value of the '{@link #getIds() <em>Ids</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIds()
	 * @generated
	 * @ordered
	 */
	protected EList<Id> ids;

	/**
	 * The cached value of the '{@link #getEmbeddedIds() <em>Embedded Ids</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmbeddedIds()
	 * @generated
	 * @ordered
	 */
	protected EList<EmbeddedId> embeddedIds;

	/**
	 * The cached value of the '{@link #getBasics() <em>Basics</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasics()
	 * @generated
	 * @ordered
	 */
	protected EList<Basic> basics;

	/**
	 * The cached value of the '{@link #getVersions() <em>Versions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<Version> versions;

	/**
	 * The cached value of the '{@link #getManyToOnes() <em>Many To Ones</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyToOnes()
	 * @generated
	 * @ordered
	 */
	protected EList<ManyToOne> manyToOnes;

	/**
	 * The cached value of the '{@link #getOneToManys() <em>One To Manys</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOneToManys()
	 * @generated
	 * @ordered
	 */
	protected EList<OneToMany> oneToManys;

	/**
	 * The cached value of the '{@link #getOneToOnes() <em>One To Ones</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOneToOnes()
	 * @generated
	 * @ordered
	 */
	protected EList<OneToOne> oneToOnes;

	/**
	 * The cached value of the '{@link #getManyToManys() <em>Many To Manys</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManyToManys()
	 * @generated
	 * @ordered
	 */
	protected EList<ManyToMany> manyToManys;

	/**
	 * The cached value of the '{@link #getEmbeddeds() <em>Embeddeds</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmbeddeds()
	 * @generated
	 * @ordered
	 */
	protected EList<Embedded> embeddeds;

	/**
	 * The cached value of the '{@link #getTransients() <em>Transients</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransients()
	 * @generated
	 * @ordered
	 */
	protected EList<Transient> transients;

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
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.Id}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ids</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ids</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_Ids()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Id> getIds()
	{
		if (ids == null)
		{
			ids = new EObjectContainmentEList<Id>(Id.class, this, OrmPackage.ATTRIBUTES__IDS);
		}
		return ids;
	}

	/**
	 * Returns the value of the '<em><b>Embedded Ids</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedId}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embedded Ids</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embedded Ids</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_EmbeddedIds()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EmbeddedId> getEmbeddedIds()
	{
		if (embeddedIds == null)
		{
			embeddedIds = new EObjectContainmentEList<EmbeddedId>(EmbeddedId.class, this, OrmPackage.ATTRIBUTES__EMBEDDED_IDS);
		}
		return embeddedIds;
	}

	/**
	 * Returns the value of the '<em><b>Basics</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.Basic}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Basics</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basics</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_Basics()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Basic> getBasics()
	{
		if (basics == null)
		{
			basics = new EObjectContainmentEList<Basic>(Basic.class, this, OrmPackage.ATTRIBUTES__BASICS);
		}
		return basics;
	}

	/**
	 * Returns the value of the '<em><b>Versions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.Version}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Versions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Versions</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_Versions()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Version> getVersions()
	{
		if (versions == null)
		{
			versions = new EObjectContainmentEList<Version>(Version.class, this, OrmPackage.ATTRIBUTES__VERSIONS);
		}
		return versions;
	}

	/**
	 * Returns the value of the '<em><b>Many To Ones</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Many To Ones</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many To Ones</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_ManyToOnes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<ManyToOne> getManyToOnes()
	{
		if (manyToOnes == null)
		{
			manyToOnes = new EObjectContainmentEList<ManyToOne>(ManyToOne.class, this, OrmPackage.ATTRIBUTES__MANY_TO_ONES);
		}
		return manyToOnes;
	}

	/**
	 * Returns the value of the '<em><b>One To Manys</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.OneToMany}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>One To Manys</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One To Manys</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_OneToManys()
	 * @model containment="true"
	 * @generated
	 */
	public EList<OneToMany> getOneToManys()
	{
		if (oneToManys == null)
		{
			oneToManys = new EObjectContainmentEList<OneToMany>(OneToMany.class, this, OrmPackage.ATTRIBUTES__ONE_TO_MANYS);
		}
		return oneToManys;
	}

	/**
	 * Returns the value of the '<em><b>One To Ones</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.OneToOne}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>One To Ones</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One To Ones</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_OneToOnes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<OneToOne> getOneToOnes()
	{
		if (oneToOnes == null)
		{
			oneToOnes = new EObjectContainmentEList<OneToOne>(OneToOne.class, this, OrmPackage.ATTRIBUTES__ONE_TO_ONES);
		}
		return oneToOnes;
	}

	/**
	 * Returns the value of the '<em><b>Many To Manys</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Many To Manys</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many To Manys</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_ManyToManys()
	 * @model containment="true"
	 * @generated
	 */
	public EList<ManyToMany> getManyToManys()
	{
		if (manyToManys == null)
		{
			manyToManys = new EObjectContainmentEList<ManyToMany>(ManyToMany.class, this, OrmPackage.ATTRIBUTES__MANY_TO_MANYS);
		}
		return manyToManys;
	}

	/**
	 * Returns the value of the '<em><b>Embeddeds</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.Embedded}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embeddeds</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embeddeds</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_Embeddeds()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Embedded> getEmbeddeds()
	{
		if (embeddeds == null)
		{
			embeddeds = new EObjectContainmentEList<Embedded>(Embedded.class, this, OrmPackage.ATTRIBUTES__EMBEDDEDS);
		}
		return embeddeds;
	}

	/**
	 * Returns the value of the '<em><b>Transients</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.Transient}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transients</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transients</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getAttributes_Transients()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Transient> getTransients()
	{
		if (transients == null)
		{
			transients = new EObjectContainmentEList<Transient>(Transient.class, this, OrmPackage.ATTRIBUTES__TRANSIENTS);
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
				getIds().addAll((Collection<? extends Id>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__EMBEDDED_IDS:
				getEmbeddedIds().clear();
				getEmbeddedIds().addAll((Collection<? extends EmbeddedId>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__BASICS:
				getBasics().clear();
				getBasics().addAll((Collection<? extends Basic>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__VERSIONS:
				getVersions().clear();
				getVersions().addAll((Collection<? extends Version>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__MANY_TO_ONES:
				getManyToOnes().clear();
				getManyToOnes().addAll((Collection<? extends ManyToOne>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__ONE_TO_MANYS:
				getOneToManys().clear();
				getOneToManys().addAll((Collection<? extends OneToMany>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__ONE_TO_ONES:
				getOneToOnes().clear();
				getOneToOnes().addAll((Collection<? extends OneToOne>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__MANY_TO_MANYS:
				getManyToManys().clear();
				getManyToManys().addAll((Collection<? extends ManyToMany>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__EMBEDDEDS:
				getEmbeddeds().clear();
				getEmbeddeds().addAll((Collection<? extends Embedded>)newValue);
				return;
			case OrmPackage.ATTRIBUTES__TRANSIENTS:
				getTransients().clear();
				getTransients().addAll((Collection<? extends Transient>)newValue);
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

} // Attributes
