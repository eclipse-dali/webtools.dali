/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.AspectChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Database;

/**
 * Some common Dali behavior:<ul>
 * <li>containment hierarchy
 * <li>Eclipse adaptable
 * <li>update triggers
 * </ul>
 */
public abstract class AbstractJpaNode
	extends AbstractModel
	implements JpaNode
{
	protected final JpaNode parent;


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

	protected final boolean forbidsParent() {
		return ! this.requiresParent();  // assume 'parent' is not optional
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new AspectChangeSupport(this, this.buildChangeSupportListener());
	}

	protected AspectChangeSupport.Listener buildChangeSupportListener() {
		return new AspectChangeSupport.Listener() {
			public void aspectChanged(String aspectName) {
				AbstractJpaNode.this.aspectChanged(aspectName);
			}
		};
	}


	// ********** IAdaptable implementation **********

	@SuppressWarnings("rawtypes")
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
	
	protected JpaPlatform.Version getJpaPlatformVersion() {
		return this.getJpaPlatform().getJpaVersion();
	}

	protected boolean isJpa2_0Compatible() {
		return JptJpaCorePlugin.nodeIsJpa2_0Compatible(this);
	}

	/**
	 * Call {@link #isJpa2_0Compatible()} before calling this method.
	 */
	protected JpaFactory2_0 getJpaFactory2_0() {
		return (JpaFactory2_0) this.getJpaFactory();
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
	 * Pre-condition: specified catalog <em>identifier</em> is not null.
	 * NB: Do not use the catalog <em>name</em>.
	 */
	protected Catalog resolveDbCatalog(String catalog) {
		Database database = this.getDatabase();
		return (database == null) ? null : database.getCatalogForIdentifier(catalog);
	}


	// ********** AspectChangeSupport.Listener support **********

	protected void aspectChanged(String aspectName) {
		if (this.aspectTriggersUpdate(aspectName)) {
//			String msg = Thread.currentThread() + " aspect change: " + this + ": " + aspectName;
//			System.out.println(msg);
//			new Exception(msg).printStackTrace(System.out);
			this.stateChanged();
		}
	}

	protected boolean aspectTriggersUpdate(String aspectName) {
		return ! this.aspectDoesNotTriggerUpdate(aspectName);
	}

	protected boolean aspectDoesNotTriggerUpdate(String aspectName) {
		// ignore state changes so we don't get a stack overflow :-)
		// (and we don't use state changes except here)
		return (aspectName == null) ||
				this.nonUpdateAspectNames().contains(aspectName);
	}

	protected final Set<String> nonUpdateAspectNames() {
		synchronized (NON_UPDATE_ASPECT_NAME_SETS) {
			HashSet<String> nonUpdateAspectNames = NON_UPDATE_ASPECT_NAME_SETS.get(this.getClass());
			if (nonUpdateAspectNames == null) {
				nonUpdateAspectNames = new HashSet<String>();
				this.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
				NON_UPDATE_ASPECT_NAME_SETS.put(this.getClass(), nonUpdateAspectNames);
			}
			return nonUpdateAspectNames;
		}
	}

	private static final HashMap<Class<? extends AbstractJpaNode>, HashSet<String>> NON_UPDATE_ASPECT_NAME_SETS = new HashMap<Class<? extends AbstractJpaNode>, HashSet<String>>();

	protected void addNonUpdateAspectNamesTo(@SuppressWarnings("unused") Set<String> nonUpdateAspectNames) {
	// when you override this method, don't forget to include:
	//	super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
	}

	public void stateChanged() {
		this.fireStateChanged();
		if (this.parent != null) {
			this.parent.stateChanged();
		}
	}


	// ********** convenience stuff **********

	/**
	 * Useful for building validation messages.
	 */
	public static final String[] EMPTY_STRING_ARRAY = StringTools.EMPTY_STRING_ARRAY;
}
