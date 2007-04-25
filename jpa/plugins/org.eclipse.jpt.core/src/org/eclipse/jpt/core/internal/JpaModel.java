/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Jpa Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.JpaModel#getProjects <em>Projects</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaModel()
 * @model kind="class"
 * @generated
 */
public class JpaModel extends JpaEObject implements IJpaModel
{
	/**
	 * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<IJpaProject> projects;

	/**
	 * Flag to indicate whether the model has been filled with projects
	 */
	private boolean filled = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JpaModel() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaCorePackage.Literals.JPA_MODEL;
	}

	/**
	 * Returns the value of the '<em><b>Projects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.IJpaProject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projects</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaModel_Projects()
	 * @model type="org.eclipse.jpt.core.internal.IJpaProject" containment="true"
	 * @generated
	 */
	public EList<IJpaProject> getProjects() {
		if (projects == null) {
			projects = new EObjectContainmentEList<IJpaProject>(IJpaProject.class, this, JpaCorePackage.JPA_MODEL__PROJECTS);
		}
		return projects;
	}

	/**
	 * @see IJpaModel#getJpaProject(IProject)
	 */
	public synchronized IJpaProject getJpaProject(IProject project) {
		for (IJpaProject jpaProject : getProjects()) {
			if (jpaProject.getProject().equals(project)) {
				return jpaProject;
			}
		}
		if (!this.filled) {
			try {
				return JpaModelManager.instance().createJpaProject(project);
			}
			catch (CoreException ce) {
				JptCorePlugin.log(ce.getStatus());
				return null;
			}
		}
		return null;
	}

	/**
	 * @see IJpaModel#jpaProjects()
	 */
	public Iterator<IJpaProject> jpaProjects() {
		return new ReadOnlyIterator<IJpaProject>(getProjects().iterator());
	}

	@Override
	public IResource getResource() {
		return null;
	}

	/**
	 * INTERNAL ONLY
	 * Initialize model with workspace resources
	 */
	void fill() throws CoreException {
		if (filled)
			return;
		IResourceProxyVisitor visitor = new IResourceProxyVisitor() {
			public boolean visit(IResourceProxy resourceProxy) throws CoreException {
				if (!resourceProxy.isAccessible()) {
					return false;
				}
				switch (resourceProxy.getType()) {
					case IResource.ROOT :
						return true;
					case IResource.PROJECT :
						JpaModelManager.instance().fillJpaProject((IProject) resourceProxy.requestResource());
					default :
						return false;
				}
			}
		};
		ResourcesPlugin.getWorkspace().getRoot().accept(visitor, IResource.NONE);
		filled = true;
	}

	/**
	 * INTERNAL ONLY
	 * Dispose of model
	 */
	void dispose() {
		for (Iterator<IJpaProject> stream = new CloneIterator<IJpaProject>(getProjects()); stream.hasNext();) {
			disposeProject((JpaProject) stream.next());
		}
	}

	/**
	 * INTERNAL ONLY
	 * Dispose project and remove it
	 */
	void disposeProject(JpaProject jpaProject) {
		jpaProject.dispose();
		getProjects().remove(jpaProject);
	}

	/**
	 * INTERNAL ONLY
	 * Handle java element change event.
	 */
	void handleEvent(ElementChangedEvent event) {
		for (Iterator<IJpaProject> stream = getProjects().iterator(); stream.hasNext();) {
			((JpaProject) stream.next()).handleEvent(event);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaCorePackage.JPA_MODEL__PROJECTS :
				return ((InternalEList<?>) getProjects()).basicRemove(otherEnd, msgs);
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
			case JpaCorePackage.JPA_MODEL__PROJECTS :
				return getProjects();
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
			case JpaCorePackage.JPA_MODEL__PROJECTS :
				getProjects().clear();
				getProjects().addAll((Collection<? extends IJpaProject>) newValue);
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
			case JpaCorePackage.JPA_MODEL__PROJECTS :
				getProjects().clear();
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
			case JpaCorePackage.JPA_MODEL__PROJECTS :
				return projects != null && !projects.isEmpty();
		}
		return super.eIsSet(featureID);
	}
}