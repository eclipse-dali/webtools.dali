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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.utility.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCascadeType_2_0;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cascade Type</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeAll <em>Cascade All</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadePersist <em>Cascade Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeMerge <em>Cascade Merge</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRemove <em>Cascade Remove</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRefresh <em>Cascade Refresh</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType()
 * @model kind="class"
 * @generated
 */
public class CascadeType extends AbstractJpaEObject implements XmlCascadeType_2_0
{
	/**
	 * The default value of the '{@link #isCascadeDetach() <em>Cascade Detach</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeDetach()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_DETACH_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeDetach() <em>Cascade Detach</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeDetach()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeDetach = CASCADE_DETACH_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadeAll() <em>Cascade All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_ALL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeAll() <em>Cascade All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeAll()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeAll = CASCADE_ALL_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadePersist() <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_PERSIST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadePersist() <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadePersist = CASCADE_PERSIST_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadeMerge() <em>Cascade Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeMerge()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_MERGE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeMerge() <em>Cascade Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeMerge()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeMerge = CASCADE_MERGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadeRemove() <em>Cascade Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeRemove()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_REMOVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeRemove() <em>Cascade Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeRemove()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeRemove = CASCADE_REMOVE_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadeRefresh() <em>Cascade Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeRefresh()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_REFRESH_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadeRefresh() <em>Cascade Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadeRefresh()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadeRefresh = CASCADE_REFRESH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CascadeType()
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
		return OrmPackage.Literals.CASCADE_TYPE;
	}

	/**
	 * Returns the value of the '<em><b>Cascade Detach</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Detach</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Detach</em>' attribute.
	 * @see #setCascadeDetach(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlCascadeType_2_0_CascadeDetach()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeDetach()
	{
		return cascadeDetach;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeDetach <em>Cascade Detach</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Detach</em>' attribute.
	 * @see #isCascadeDetach()
	 * @generated
	 */
	public void setCascadeDetach(boolean newCascadeDetach)
	{
		boolean oldCascadeDetach = cascadeDetach;
		cascadeDetach = newCascadeDetach;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_DETACH, oldCascadeDetach, cascadeDetach));
	}

	/**
	 * Returns the value of the '<em><b>Cascade All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade All</em>' attribute.
	 * @see #setCascadeAll(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadeAll()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeAll()
	{
		return cascadeAll;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeAll <em>Cascade All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade All</em>' attribute.
	 * @see #isCascadeAll()
	 * @generated
	 */
	public void setCascadeAll(boolean newCascadeAll)
	{
		boolean oldCascadeAll = cascadeAll;
		cascadeAll = newCascadeAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_ALL, oldCascadeAll, cascadeAll));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist</em>' attribute.
	 * @see #setCascadePersist(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadePersist()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadePersist()
	{
		return cascadePersist;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadePersist <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist</em>' attribute.
	 * @see #isCascadePersist()
	 * @generated
	 */
	public void setCascadePersist(boolean newCascadePersist)
	{
		boolean oldCascadePersist = cascadePersist;
		cascadePersist = newCascadePersist;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_PERSIST, oldCascadePersist, cascadePersist));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Merge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Merge</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Merge</em>' attribute.
	 * @see #setCascadeMerge(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadeMerge()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeMerge()
	{
		return cascadeMerge;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeMerge <em>Cascade Merge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Merge</em>' attribute.
	 * @see #isCascadeMerge()
	 * @generated
	 */
	public void setCascadeMerge(boolean newCascadeMerge)
	{
		boolean oldCascadeMerge = cascadeMerge;
		cascadeMerge = newCascadeMerge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_MERGE, oldCascadeMerge, cascadeMerge));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Remove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Remove</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Remove</em>' attribute.
	 * @see #setCascadeRemove(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadeRemove()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeRemove()
	{
		return cascadeRemove;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRemove <em>Cascade Remove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Remove</em>' attribute.
	 * @see #isCascadeRemove()
	 * @generated
	 */
	public void setCascadeRemove(boolean newCascadeRemove)
	{
		boolean oldCascadeRemove = cascadeRemove;
		cascadeRemove = newCascadeRemove;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_REMOVE, oldCascadeRemove, cascadeRemove));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Refresh</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Refresh</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Refresh</em>' attribute.
	 * @see #setCascadeRefresh(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getCascadeType_CascadeRefresh()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadeRefresh()
	{
		return cascadeRefresh;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.CascadeType#isCascadeRefresh <em>Cascade Refresh</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Refresh</em>' attribute.
	 * @see #isCascadeRefresh()
	 * @generated
	 */
	public void setCascadeRefresh(boolean newCascadeRefresh)
	{
		boolean oldCascadeRefresh = cascadeRefresh;
		cascadeRefresh = newCascadeRefresh;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.CASCADE_TYPE__CASCADE_REFRESH, oldCascadeRefresh, cascadeRefresh));
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
			case OrmPackage.CASCADE_TYPE__CASCADE_DETACH:
				return isCascadeDetach();
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				return isCascadeAll();
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				return isCascadePersist();
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				return isCascadeMerge();
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				return isCascadeRemove();
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				return isCascadeRefresh();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.CASCADE_TYPE__CASCADE_DETACH:
				setCascadeDetach((Boolean)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				setCascadeAll((Boolean)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				setCascadePersist((Boolean)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				setCascadeMerge((Boolean)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				setCascadeRemove((Boolean)newValue);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				setCascadeRefresh((Boolean)newValue);
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
			case OrmPackage.CASCADE_TYPE__CASCADE_DETACH:
				setCascadeDetach(CASCADE_DETACH_EDEFAULT);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				setCascadeAll(CASCADE_ALL_EDEFAULT);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				setCascadePersist(CASCADE_PERSIST_EDEFAULT);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				setCascadeMerge(CASCADE_MERGE_EDEFAULT);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				setCascadeRemove(CASCADE_REMOVE_EDEFAULT);
				return;
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				setCascadeRefresh(CASCADE_REFRESH_EDEFAULT);
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
			case OrmPackage.CASCADE_TYPE__CASCADE_DETACH:
				return cascadeDetach != CASCADE_DETACH_EDEFAULT;
			case OrmPackage.CASCADE_TYPE__CASCADE_ALL:
				return cascadeAll != CASCADE_ALL_EDEFAULT;
			case OrmPackage.CASCADE_TYPE__CASCADE_PERSIST:
				return cascadePersist != CASCADE_PERSIST_EDEFAULT;
			case OrmPackage.CASCADE_TYPE__CASCADE_MERGE:
				return cascadeMerge != CASCADE_MERGE_EDEFAULT;
			case OrmPackage.CASCADE_TYPE__CASCADE_REMOVE:
				return cascadeRemove != CASCADE_REMOVE_EDEFAULT;
			case OrmPackage.CASCADE_TYPE__CASCADE_REFRESH:
				return cascadeRefresh != CASCADE_REFRESH_EDEFAULT;
		}
		return super.eIsSet(featureID);
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
		result.append(" (cascadeDetach: ");
		result.append(cascadeDetach);
		result.append(", cascadeAll: ");
		result.append(cascadeAll);
		result.append(", cascadePersist: ");
		result.append(cascadePersist);
		result.append(", cascadeMerge: ");
		result.append(cascadeMerge);
		result.append(", cascadeRemove: ");
		result.append(cascadeRemove);
		result.append(", cascadeRefresh: ");
		result.append(cascadeRefresh);
		result.append(')');
		return result.toString();
	}
	
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildCascadeAllTranslator(),
			buildCascadePersistTranslator(),
			buildCascadeMergeTranslator(),
			buildCascadeRemoveTranslator(),
			buildCascadeRefreshTranslator(),
			buildCascadeDetachTranslator()
		};
	}
	
	protected static Translator buildCascadeAllTranslator() {
		return new EmptyTagBooleanTranslator(JPA.CASCADE_ALL, OrmPackage.eINSTANCE.getCascadeType_CascadeAll());
	}
	
	protected static Translator buildCascadePersistTranslator() {
		return new EmptyTagBooleanTranslator(JPA.CASCADE_PERSIST, OrmPackage.eINSTANCE.getCascadeType_CascadePersist());
	}
	
	protected static Translator buildCascadeMergeTranslator() {
		return new EmptyTagBooleanTranslator(JPA.CASCADE_MERGE, OrmPackage.eINSTANCE.getCascadeType_CascadeMerge());
	}
	
	protected static Translator buildCascadeRemoveTranslator() {
		return new EmptyTagBooleanTranslator(JPA.CASCADE_REMOVE, OrmPackage.eINSTANCE.getCascadeType_CascadeRemove());
	}

	protected static Translator buildCascadeRefreshTranslator() {
		return new EmptyTagBooleanTranslator(JPA.CASCADE_REFRESH, OrmPackage.eINSTANCE.getCascadeType_CascadeRefresh());
	}
	
	protected static Translator buildCascadeDetachTranslator() {
		return new EmptyTagBooleanTranslator(JPA.CASCADE_DETACH, OrmV2_0Package.eINSTANCE.getXmlCascadeType_2_0_CascadeDetach());
	}	
}
