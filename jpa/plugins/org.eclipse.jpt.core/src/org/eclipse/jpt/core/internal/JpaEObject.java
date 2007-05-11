/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;

/**
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaEObject()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class JpaEObject extends EObjectImpl implements IJpaEObject
{
	/**
	 * Sets of "insignificant" feature ids, keyed by class.
	 * This is built up lazily, as the objects are modified.
	 */
	private static final Map<Class<? extends JpaEObject>, Set<Integer>> insignificantFeatureIdSets = new Hashtable<Class<? extends JpaEObject>, Set<Integer>>();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JpaEObject() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaCorePackage.Literals.JPA_EOBJECT;
	}

	public IJpaProject getJpaProject() {
		IJpaEObject container = (IJpaEObject) this.eContainer();
		return (container == null) ? null : container.getJpaProject();
	}

	public IResource getResource() {
		return this.getJpaProject().getProject();
	}

	public ConnectionProfile connectionProfile() {
		return this.getJpaProject().connectionProfile();
	}

	public Database database() {
		return this.connectionProfile().getDatabase();
	}

	// ********** change notification **********
	/**
	 * override to prevent notification when the object's state is unchanged
	 */
	@Override
	public void eNotify(Notification notification) {
		if (!notification.isTouch()) {
			super.eNotify(notification);
			this.featureChanged(notification.getFeatureID(this.getClass()));
		}
	}

	protected void featureChanged(int featureId) {
		if (this.featureIsSignificant(featureId)) {
			IJpaProject project = this.getJpaProject();
			// check that the model is fully initialized
			if (project != null) {
				project.resynch();
			}
		}
	}

	protected boolean featureIsSignificant(int featureId) {
		return !this.featureIsInsignificant(featureId);
	}

	protected boolean featureIsInsignificant(int featureId) {
		return this.insignificantFeatureIds().contains(featureId);
	}

	/**
	 * Return a set of the object's "insignificant" feature ids.
	 * These are the EMF features that, when they change, will NOT cause the
	 * object (or its containing tree) to be resynched, i.e. defaults calculated.
	 * If you need instance-based calculation of your "insignificant" aspects,
	 * override this method. If class-based calculation is sufficient,
	 * override #addInsignificantFeatureIdsTo(Set).
	 */
	protected Set<Integer> insignificantFeatureIds() {
		synchronized (insignificantFeatureIdSets) {
			Set<Integer> insignificantFeatureIds = insignificantFeatureIdSets.get(this.getClass());
			if (insignificantFeatureIds == null) {
				insignificantFeatureIds = new HashSet<Integer>();
				this.addInsignificantFeatureIdsTo(insignificantFeatureIds);
				insignificantFeatureIdSets.put(this.getClass(), insignificantFeatureIds);
			}
			return insignificantFeatureIds;
		}
	}

	/**
	 * Add the object's "insignificant" feature ids to the specified set.
	 * These are the EMF features that, when they change, will NOT cause the
	 * object (or its containing tree) to be resynched, i.e. defaults calculated.
	 * If class-based calculation of your "insignificant" features is sufficient,
	 * override this method. If you need instance-based calculation,
	 * override #insignificantFeatureIds().
	 */
	protected void addInsignificantFeatureIdsTo(Set<Integer> insignificantFeatureIds) {
	// when you override this method, don't forget to include:
	//	super.addInsignificantFeatureIdsTo(insignificantFeatureIds);
	}
}
