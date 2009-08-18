/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaPlatformVariation;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.CallbackChangeSupport;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;

/**
 * some common Dali behavior:
 *   - containment hierarchy
 *   - update triggers
 */
public abstract class AbstractJpaNode
	extends AbstractModel
	implements JpaNode
{
	private final JpaNode parent;


	// ********** constructor/initialization **********

	protected AbstractJpaNode(JpaNode parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}
	
	protected void checkParent(JpaNode p) {
		if (p == null) {
			if (this.requiresParent()) {
				throw new IllegalArgumentException("'parent' cannot be null"); //$NON-NLS-1$
			}
		} else {
			if (this.forbidsParent()) {
				throw new IllegalArgumentException("'parent' must be null"); //$NON-NLS-1$
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
		return new CallbackChangeSupport(this, this.buildChangeSupportListener());
	}

	protected CallbackChangeSupport.Listener buildChangeSupportListener() {
		return new CallbackChangeSupport.Listener() {
			public void aspectChanged(String aspectName) {
				AbstractJpaNode.this.aspectChanged(aspectName);
			}
		};
	}

	
	// ********** IAdaptable implementation **********
	
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}


	// ********** JpaNode implementation **********

	public JpaNode getParent() {
		return this.parent;
	}
	
	public IResource getResource() {
		return this.parent.getResource();
	}

	public JpaProject getJpaProject() {
		return this.parent.getJpaProject();
	}


	// ********** convenience methods **********

	protected JpaPlatform getJpaPlatform() {
		return this.getJpaProject().getJpaPlatform();
	}

	protected JpaFactory getJpaFactory() {
		return this.getJpaPlatform().getJpaFactory();
	}

	protected JpaPlatformVariation getJpaPlatformVariation() {
		return this.getJpaPlatform().getJpaVariation();
	}

	protected JpaFile getJpaFile(IFile file) {
		return this.getJpaProject().getJpaFile(file);
	}

	protected JpaDataSource getDataSource() {
		return this.getJpaProject().getDataSource();
	}

	protected Database getDatabase() {
		return this.getDataSource().getDatabase();
	}

	protected boolean connectionProfileIsActive() {
		return this.getDataSource().connectionProfileIsActive();
	}

	/**
	 * pre-condition: specified catalog name is not null
	 */
	protected Catalog getDbCatalog(String catalog) {
		Database database = this.getDatabase();
		return (database == null) ? null : database.getCatalogForIdentifier(catalog);
	}


	// ********** CallbackChangeSupport.Source implementation **********

	protected void aspectChanged(String aspectName) {
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

	private static final HashMap<Class<? extends AbstractJpaNode>, HashSet<String>> nonUpdateAspectNameSets = new HashMap<Class<? extends AbstractJpaNode>, HashSet<String>>();

	protected void addNonUpdateAspectNamesTo(@SuppressWarnings("unused") Set<String> nonUpdateAspectNames) {
	// when you override this method, don't forget to include:
	//	super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
	}

}
