/**
 * <copyright>
 * </copyright>
 *
 * $Id: EmbeddableAttributes.java,v 1.1.2.3 2007/11/14 23:36:57 pfullbright Exp $
 */
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
 * A representation of the model object '<em><b>Embeddable Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes#getBasics <em>Basics</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes#getTransients <em>Transients</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddableAttributes()
 * @model kind="class"
 * @extends IJpaEObject
 * @generated
 */
public class EmbeddableAttributes extends JpaEObject implements IJpaEObject
{
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
	protected EmbeddableAttributes()
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
		return OrmPackage.Literals.EMBEDDABLE_ATTRIBUTES;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddableAttributes_Basics()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Basic> getBasics()
	{
		if (basics == null)
		{
			basics = new EObjectContainmentEList<Basic>(Basic.class, this, OrmPackage.EMBEDDABLE_ATTRIBUTES__BASICS);
		}
		return basics;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmbeddableAttributes_Transients()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Transient> getTransients()
	{
		if (transients == null)
		{
			transients = new EObjectContainmentEList<Transient>(Transient.class, this, OrmPackage.EMBEDDABLE_ATTRIBUTES__TRANSIENTS);
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
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__BASICS:
				return ((InternalEList<?>)getBasics()).basicRemove(otherEnd, msgs);
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__TRANSIENTS:
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
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__BASICS:
				return getBasics();
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__TRANSIENTS:
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
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__BASICS:
				getBasics().clear();
				getBasics().addAll((Collection<? extends Basic>)newValue);
				return;
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__TRANSIENTS:
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
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__BASICS:
				getBasics().clear();
				return;
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__TRANSIENTS:
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
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__BASICS:
				return basics != null && !basics.isEmpty();
			case OrmPackage.EMBEDDABLE_ATTRIBUTES__TRANSIENTS:
				return transients != null && !transients.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // EmbeddableAttributes
