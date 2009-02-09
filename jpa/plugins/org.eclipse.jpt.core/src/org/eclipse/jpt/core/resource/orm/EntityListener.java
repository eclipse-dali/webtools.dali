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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Listener</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityListener#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPrePersist <em>Pre Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPostPersist <em>Post Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPreRemove <em>Pre Remove</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPostRemove <em>Post Remove</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPreUpdate <em>Pre Update</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPostUpdate <em>Post Update</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPostLoad <em>Post Load</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class EntityListener extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPrePersist() <em>Pre Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrePersist()
	 * @generated
	 * @ordered
	 */
	protected PrePersist prePersist;

	/**
	 * The cached value of the '{@link #getPostPersist() <em>Post Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostPersist()
	 * @generated
	 * @ordered
	 */
	protected PostPersist postPersist;

	/**
	 * The cached value of the '{@link #getPreRemove() <em>Pre Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreRemove()
	 * @generated
	 * @ordered
	 */
	protected PreRemove preRemove;

	/**
	 * The cached value of the '{@link #getPostRemove() <em>Post Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostRemove()
	 * @generated
	 * @ordered
	 */
	protected PostRemove postRemove;

	/**
	 * The cached value of the '{@link #getPreUpdate() <em>Pre Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreUpdate()
	 * @generated
	 * @ordered
	 */
	protected PreUpdate preUpdate;

	/**
	 * The cached value of the '{@link #getPostUpdate() <em>Post Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostUpdate()
	 * @generated
	 * @ordered
	 */
	protected PostUpdate postUpdate;

	/**
	 * The cached value of the '{@link #getPostLoad() <em>Post Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostLoad()
	 * @generated
	 * @ordered
	 */
	protected PostLoad postLoad;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityListener()
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
		return OrmPackage.Literals.ENTITY_LISTENER;
	}

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener_ClassName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	public void setClassName(String newClassName)
	{
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__CLASS_NAME, oldClassName, className));
	}

	/**
	 * Returns the value of the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Persist</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Persist</em>' containment reference.
	 * @see #setPrePersist(PrePersist)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener_PrePersist()
	 * @model containment="true"
	 * @generated
	 */
	public PrePersist getPrePersist()
	{
		return prePersist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrePersist(PrePersist newPrePersist, NotificationChain msgs)
	{
		PrePersist oldPrePersist = prePersist;
		prePersist = newPrePersist;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__PRE_PERSIST, oldPrePersist, newPrePersist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPrePersist <em>Pre Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Persist</em>' containment reference.
	 * @see #getPrePersist()
	 * @generated
	 */
	public void setPrePersist(PrePersist newPrePersist)
	{
		if (newPrePersist != prePersist)
		{
			NotificationChain msgs = null;
			if (prePersist != null)
				msgs = ((InternalEObject)prePersist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__PRE_PERSIST, null, msgs);
			if (newPrePersist != null)
				msgs = ((InternalEObject)newPrePersist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__PRE_PERSIST, null, msgs);
			msgs = basicSetPrePersist(newPrePersist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__PRE_PERSIST, newPrePersist, newPrePersist));
	}

	/**
	 * Returns the value of the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Persist</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Persist</em>' containment reference.
	 * @see #setPostPersist(PostPersist)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener_PostPersist()
	 * @model containment="true"
	 * @generated
	 */
	public PostPersist getPostPersist()
	{
		return postPersist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostPersist(PostPersist newPostPersist, NotificationChain msgs)
	{
		PostPersist oldPostPersist = postPersist;
		postPersist = newPostPersist;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__POST_PERSIST, oldPostPersist, newPostPersist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPostPersist <em>Post Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Persist</em>' containment reference.
	 * @see #getPostPersist()
	 * @generated
	 */
	public void setPostPersist(PostPersist newPostPersist)
	{
		if (newPostPersist != postPersist)
		{
			NotificationChain msgs = null;
			if (postPersist != null)
				msgs = ((InternalEObject)postPersist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__POST_PERSIST, null, msgs);
			if (newPostPersist != null)
				msgs = ((InternalEObject)newPostPersist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__POST_PERSIST, null, msgs);
			msgs = basicSetPostPersist(newPostPersist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__POST_PERSIST, newPostPersist, newPostPersist));
	}

	/**
	 * Returns the value of the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Remove</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Remove</em>' containment reference.
	 * @see #setPreRemove(PreRemove)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener_PreRemove()
	 * @model containment="true"
	 * @generated
	 */
	public PreRemove getPreRemove()
	{
		return preRemove;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreRemove(PreRemove newPreRemove, NotificationChain msgs)
	{
		PreRemove oldPreRemove = preRemove;
		preRemove = newPreRemove;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__PRE_REMOVE, oldPreRemove, newPreRemove);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPreRemove <em>Pre Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Remove</em>' containment reference.
	 * @see #getPreRemove()
	 * @generated
	 */
	public void setPreRemove(PreRemove newPreRemove)
	{
		if (newPreRemove != preRemove)
		{
			NotificationChain msgs = null;
			if (preRemove != null)
				msgs = ((InternalEObject)preRemove).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__PRE_REMOVE, null, msgs);
			if (newPreRemove != null)
				msgs = ((InternalEObject)newPreRemove).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__PRE_REMOVE, null, msgs);
			msgs = basicSetPreRemove(newPreRemove, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__PRE_REMOVE, newPreRemove, newPreRemove));
	}

	/**
	 * Returns the value of the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Remove</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Remove</em>' containment reference.
	 * @see #setPostRemove(PostRemove)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener_PostRemove()
	 * @model containment="true"
	 * @generated
	 */
	public PostRemove getPostRemove()
	{
		return postRemove;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostRemove(PostRemove newPostRemove, NotificationChain msgs)
	{
		PostRemove oldPostRemove = postRemove;
		postRemove = newPostRemove;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__POST_REMOVE, oldPostRemove, newPostRemove);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPostRemove <em>Post Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Remove</em>' containment reference.
	 * @see #getPostRemove()
	 * @generated
	 */
	public void setPostRemove(PostRemove newPostRemove)
	{
		if (newPostRemove != postRemove)
		{
			NotificationChain msgs = null;
			if (postRemove != null)
				msgs = ((InternalEObject)postRemove).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__POST_REMOVE, null, msgs);
			if (newPostRemove != null)
				msgs = ((InternalEObject)newPostRemove).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__POST_REMOVE, null, msgs);
			msgs = basicSetPostRemove(newPostRemove, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__POST_REMOVE, newPostRemove, newPostRemove));
	}

	/**
	 * Returns the value of the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Update</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Update</em>' containment reference.
	 * @see #setPreUpdate(PreUpdate)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener_PreUpdate()
	 * @model containment="true"
	 * @generated
	 */
	public PreUpdate getPreUpdate()
	{
		return preUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreUpdate(PreUpdate newPreUpdate, NotificationChain msgs)
	{
		PreUpdate oldPreUpdate = preUpdate;
		preUpdate = newPreUpdate;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__PRE_UPDATE, oldPreUpdate, newPreUpdate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPreUpdate <em>Pre Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Update</em>' containment reference.
	 * @see #getPreUpdate()
	 * @generated
	 */
	public void setPreUpdate(PreUpdate newPreUpdate)
	{
		if (newPreUpdate != preUpdate)
		{
			NotificationChain msgs = null;
			if (preUpdate != null)
				msgs = ((InternalEObject)preUpdate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__PRE_UPDATE, null, msgs);
			if (newPreUpdate != null)
				msgs = ((InternalEObject)newPreUpdate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__PRE_UPDATE, null, msgs);
			msgs = basicSetPreUpdate(newPreUpdate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__PRE_UPDATE, newPreUpdate, newPreUpdate));
	}

	/**
	 * Returns the value of the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Update</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Update</em>' containment reference.
	 * @see #setPostUpdate(PostUpdate)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener_PostUpdate()
	 * @model containment="true"
	 * @generated
	 */
	public PostUpdate getPostUpdate()
	{
		return postUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostUpdate(PostUpdate newPostUpdate, NotificationChain msgs)
	{
		PostUpdate oldPostUpdate = postUpdate;
		postUpdate = newPostUpdate;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__POST_UPDATE, oldPostUpdate, newPostUpdate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPostUpdate <em>Post Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Update</em>' containment reference.
	 * @see #getPostUpdate()
	 * @generated
	 */
	public void setPostUpdate(PostUpdate newPostUpdate)
	{
		if (newPostUpdate != postUpdate)
		{
			NotificationChain msgs = null;
			if (postUpdate != null)
				msgs = ((InternalEObject)postUpdate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__POST_UPDATE, null, msgs);
			if (newPostUpdate != null)
				msgs = ((InternalEObject)newPostUpdate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__POST_UPDATE, null, msgs);
			msgs = basicSetPostUpdate(newPostUpdate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__POST_UPDATE, newPostUpdate, newPostUpdate));
	}

	/**
	 * Returns the value of the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Load</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Load</em>' containment reference.
	 * @see #setPostLoad(PostLoad)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getEntityListener_PostLoad()
	 * @model containment="true"
	 * @generated
	 */
	public PostLoad getPostLoad()
	{
		return postLoad;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostLoad(PostLoad newPostLoad, NotificationChain msgs)
	{
		PostLoad oldPostLoad = postLoad;
		postLoad = newPostLoad;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__POST_LOAD, oldPostLoad, newPostLoad);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.EntityListener#getPostLoad <em>Post Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Load</em>' containment reference.
	 * @see #getPostLoad()
	 * @generated
	 */
	public void setPostLoad(PostLoad newPostLoad)
	{
		if (newPostLoad != postLoad)
		{
			NotificationChain msgs = null;
			if (postLoad != null)
				msgs = ((InternalEObject)postLoad).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__POST_LOAD, null, msgs);
			if (newPostLoad != null)
				msgs = ((InternalEObject)newPostLoad).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_LISTENER__POST_LOAD, null, msgs);
			msgs = basicSetPostLoad(newPostLoad, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_LISTENER__POST_LOAD, newPostLoad, newPostLoad));
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
			case OrmPackage.ENTITY_LISTENER__PRE_PERSIST:
				return basicSetPrePersist(null, msgs);
			case OrmPackage.ENTITY_LISTENER__POST_PERSIST:
				return basicSetPostPersist(null, msgs);
			case OrmPackage.ENTITY_LISTENER__PRE_REMOVE:
				return basicSetPreRemove(null, msgs);
			case OrmPackage.ENTITY_LISTENER__POST_REMOVE:
				return basicSetPostRemove(null, msgs);
			case OrmPackage.ENTITY_LISTENER__PRE_UPDATE:
				return basicSetPreUpdate(null, msgs);
			case OrmPackage.ENTITY_LISTENER__POST_UPDATE:
				return basicSetPostUpdate(null, msgs);
			case OrmPackage.ENTITY_LISTENER__POST_LOAD:
				return basicSetPostLoad(null, msgs);
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
			case OrmPackage.ENTITY_LISTENER__CLASS_NAME:
				return getClassName();
			case OrmPackage.ENTITY_LISTENER__PRE_PERSIST:
				return getPrePersist();
			case OrmPackage.ENTITY_LISTENER__POST_PERSIST:
				return getPostPersist();
			case OrmPackage.ENTITY_LISTENER__PRE_REMOVE:
				return getPreRemove();
			case OrmPackage.ENTITY_LISTENER__POST_REMOVE:
				return getPostRemove();
			case OrmPackage.ENTITY_LISTENER__PRE_UPDATE:
				return getPreUpdate();
			case OrmPackage.ENTITY_LISTENER__POST_UPDATE:
				return getPostUpdate();
			case OrmPackage.ENTITY_LISTENER__POST_LOAD:
				return getPostLoad();
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
			case OrmPackage.ENTITY_LISTENER__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case OrmPackage.ENTITY_LISTENER__PRE_PERSIST:
				setPrePersist((PrePersist)newValue);
				return;
			case OrmPackage.ENTITY_LISTENER__POST_PERSIST:
				setPostPersist((PostPersist)newValue);
				return;
			case OrmPackage.ENTITY_LISTENER__PRE_REMOVE:
				setPreRemove((PreRemove)newValue);
				return;
			case OrmPackage.ENTITY_LISTENER__POST_REMOVE:
				setPostRemove((PostRemove)newValue);
				return;
			case OrmPackage.ENTITY_LISTENER__PRE_UPDATE:
				setPreUpdate((PreUpdate)newValue);
				return;
			case OrmPackage.ENTITY_LISTENER__POST_UPDATE:
				setPostUpdate((PostUpdate)newValue);
				return;
			case OrmPackage.ENTITY_LISTENER__POST_LOAD:
				setPostLoad((PostLoad)newValue);
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
			case OrmPackage.ENTITY_LISTENER__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case OrmPackage.ENTITY_LISTENER__PRE_PERSIST:
				setPrePersist((PrePersist)null);
				return;
			case OrmPackage.ENTITY_LISTENER__POST_PERSIST:
				setPostPersist((PostPersist)null);
				return;
			case OrmPackage.ENTITY_LISTENER__PRE_REMOVE:
				setPreRemove((PreRemove)null);
				return;
			case OrmPackage.ENTITY_LISTENER__POST_REMOVE:
				setPostRemove((PostRemove)null);
				return;
			case OrmPackage.ENTITY_LISTENER__PRE_UPDATE:
				setPreUpdate((PreUpdate)null);
				return;
			case OrmPackage.ENTITY_LISTENER__POST_UPDATE:
				setPostUpdate((PostUpdate)null);
				return;
			case OrmPackage.ENTITY_LISTENER__POST_LOAD:
				setPostLoad((PostLoad)null);
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
			case OrmPackage.ENTITY_LISTENER__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case OrmPackage.ENTITY_LISTENER__PRE_PERSIST:
				return prePersist != null;
			case OrmPackage.ENTITY_LISTENER__POST_PERSIST:
				return postPersist != null;
			case OrmPackage.ENTITY_LISTENER__PRE_REMOVE:
				return preRemove != null;
			case OrmPackage.ENTITY_LISTENER__POST_REMOVE:
				return postRemove != null;
			case OrmPackage.ENTITY_LISTENER__PRE_UPDATE:
				return preUpdate != null;
			case OrmPackage.ENTITY_LISTENER__POST_UPDATE:
				return postUpdate != null;
			case OrmPackage.ENTITY_LISTENER__POST_LOAD:
				return postLoad != null;
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
		result.append(" (className: ");
		result.append(className);
		result.append(')');
		return result.toString();
	}

} // EntityListener
