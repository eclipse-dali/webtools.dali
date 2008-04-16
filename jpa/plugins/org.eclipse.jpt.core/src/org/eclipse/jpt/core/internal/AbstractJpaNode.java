/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.CallbackChangeSupport;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;

/**
 * 
 */
public abstract class AbstractJpaNode
	extends AbstractModel
	implements JpaNode, CallbackChangeSupport.Source
{
	private final JpaNode parent;


	// ********** constructor **********

	protected AbstractJpaNode(JpaNode parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}
	
	protected void checkParent(JpaNode p) {
		if (p == null) {
			if (this.requiresParent()) {
				throw new IllegalArgumentException("'parent' cannot be null");
			}
		} else {
			if (this.forbidsParent()) {
				throw new IllegalArgumentException("'parent' must be null");
			}
		}
	}

	protected boolean requiresParent() {
		return true;
	}

	protected boolean forbidsParent() {
		return ! this.requiresParent();  // assume 'parent' is not optional
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new CallbackChangeSupport(this);
	}

	
	// ********** IAdaptable implementation **********
	
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}


	// ********** IJpaNode implementation **********

	public JpaNode getParent() {
		return this.parent;
	}
	
	public IResource getResource() {
		return this.parent.getResource();
	}

	public JpaProject getJpaProject() {
		return this.parent.getJpaProject();
	}

	public String displayString() {
		return this.toString();
	}


	// ********** convenience methods **********

	protected JpaPlatform getJpaPlatform() {
		return this.getJpaProject().getJpaPlatform();
	}

	protected JpaFactory getJpaFactory() {
		return this.getJpaPlatform().getJpaFactory();
	}

	protected JpaFile getJpaFile(ResourceModel resourceModel) {
		if (resourceModel == null) {
			System.out.println("resourceModel is null");
		}
		if (getJpaProject() == null) {
			System.out.println("JpaProject is null");
		}
		return getJpaProject().getJpaFile(resourceModel.getFile());
	}

	protected ConnectionProfile getConnectionProfile() {
		return this.getJpaProject().getConnectionProfile();
	}

	protected Database getDatabase() {
		return this.getConnectionProfile().getDatabase();
	}

	public boolean connectionProfileIsActive() {
		return this.getConnectionProfile().isActive();
	}

	// ********** update model **********

	private static final HashMap<Class<? extends AbstractJpaNode>, HashSet<String>> nonUpdateAspectNameSets = new HashMap<Class<? extends AbstractJpaNode>, HashSet<String>>();

	public void aspectChanged(String aspectName) {
		if (this.aspectTriggersUpdate(aspectName)) {
			// System.out.println(Thread.currentThread() + " \"update\" change: " + this + ": " + aspectName);
			this.getJpaProject().update();
		}
	}

	private boolean aspectTriggersUpdate(String aspectName) {
		return ! this.aspectDoesNotTriggerUpdate(aspectName);
	}

	private boolean aspectDoesNotTriggerUpdate(String aspectName) {
		return this.nonUpdateAspectNames().contains(aspectName);
	}

	protected final Set<String> nonUpdateAspectNames() {
		synchronized (nonUpdateAspectNameSets) {
			HashSet<String> nonUpdateAspectNames = nonUpdateAspectNameSets.get(this.getClass());
			if (nonUpdateAspectNames == null) {
				nonUpdateAspectNames = new HashSet<String>();
				this.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
				nonUpdateAspectNameSets.put(this.getClass(), nonUpdateAspectNames);
			}
			return nonUpdateAspectNames;
		}
	}

	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
	// when you override this method, don't forget to include:
	//	super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
	}

}
