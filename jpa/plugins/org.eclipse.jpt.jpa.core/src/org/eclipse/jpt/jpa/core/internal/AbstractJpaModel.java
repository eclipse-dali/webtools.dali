/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.AspectChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaFactory2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Database;

/**
 * Some common Dali behavior:<ul>
 * <li>containment hierarchy
 * <li>Eclipse adaptable
 * <li>update triggers
 * </ul>
 */
public abstract class AbstractJpaModel<P extends JpaModel>
	extends AbstractModel
	implements JpaModel
{
	protected final P parent;


	// ********** constructor/initialization **********

	protected AbstractJpaModel(P parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}

	protected void checkParent(JpaModel p) {
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
		return new AspectChangeSupportListener();
	}

	public class AspectChangeSupportListener
		implements AspectChangeSupport.Listener
	{
		public void aspectChanged(String aspectName) {
			AbstractJpaModel.this.aspectChanged(aspectName);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	// ********** IAdaptable implementation **********

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(Class adapter) {
		return PlatformTools.getAdapter(this, adapter);
	}


	// ********** JpaModel implementation **********

	public P getParent() {
		return this.parent;
	}

	/**
	 * Overridden in:<ul>
	 * <li>{@link AbstractJpaProject#getResource() AbstractJpaProject}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericContextRoot#getResource() GenericContextModelRoot}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJarFile#getResource() GenericJarFile}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXml#getResource() GenericOrmXml}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXml#getResource() GenericPersistenceXml}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaManagedType#getResource() AbstractJavaManagedType}
	 * </ul>
	 */
	public IResource getResource() {
		return this.parent.getResource();
	}

	/**
	 * Overridden in:<ul>
	 * <li>{@link AbstractJpaProject#getJpaProject() AbstractJpaProject}
	 * </ul>
	 */
	public JpaProject getJpaProject() {
		return this.parent.getJpaProject();
	}

	public JpaProject.Manager getJpaProjectManager() {
		return this.getJpaProject().getManager();
	}

	public JpaPlatform getJpaPlatform() {
		return this.getJpaProject().getJpaPlatform();
	}
	

	// ********** convenience methods **********

	protected IJavaProject getJavaProject() {
		return this.getJpaProject().getJavaProject();
	}

	protected JpaPlatform.Version getJpaPlatformVersion() {
		return this.getJpaPlatform().getJpaVersion();
	}

	protected boolean isJpa2_0Compatible() {
		return this.getJpaPlatformVersion().isCompatibleWithJpaVersion(JpaProject2_0.FACET_VERSION_STRING);
	}

	protected boolean isJpa2_1Compatible() {
		return this.getJpaPlatformVersion().isCompatibleWithJpaVersion(JpaProject2_1.FACET_VERSION_STRING);
	}

	/**
	 * Call {@link #isJpa2_0Compatible()} before calling this method.
	 */
	protected JpaFactory2_0 getJpaFactory2_0() {
		return (JpaFactory2_0) this.getJpaFactory();
	}

	/**
	 * Call {@link #isJpa2_1Compatible()} before calling this method.
	 */
	protected JpaFactory2_1 getJpaFactory2_1() {
		return (JpaFactory2_1) this.getJpaFactory();
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
	 * Pre-condition: specified catalog <em>identifier</em> is not
	 * <code>null</code>.
	 * <p>
	 * <strong>NB:</strong> Do not use the catalog <em>name</em>.
	 */
	protected Catalog resolveDbCatalog(String catalog) {
		Database database = this.getDatabase();
		return (database == null) ? null : database.getCatalogForIdentifier(catalog);
	}


	// ********** AspectChangeSupport.Listener support **********

	/**
	 * The specified aspect name will be <code>null</code> for <em>state</em>
	 * changes.
	 */
	protected void aspectChanged(String aspectName) {
		if (this.aspectTriggersUpdate(aspectName)) {
//			String msg = Thread.currentThread() + " aspect change: " + this + ": " + aspectName;
//			System.out.println(msg);
//			new Exception(msg).printStackTrace(System.out);
			this.stateChanged();
		}
	}

	/**
	 * The specified aspect name will be <code>null</code> for <em>state</em>
	 * changes.
	 */
	protected boolean aspectTriggersUpdate(String aspectName) {
		return ! this.aspectDoesNotTriggerUpdate(aspectName);
	}

	/**
	 * The specified aspect name will be <code>null</code> for <em>state</em>
	 * changes.
	 */
	protected boolean aspectDoesNotTriggerUpdate(String aspectName) {
		// ignore state changes so we don't get a stack overflow :-)
		// (and we don't use state changes except here)
		return (aspectName == null) ||
				this.nonUpdateAspectNames().contains(aspectName);
	}

	protected final Set<String> nonUpdateAspectNames() {
		@SuppressWarnings("unchecked")
		Class<? extends AbstractJpaModel<?>> thisClass = (Class<? extends AbstractJpaModel<?>>) this.getClass();
		synchronized (NON_UPDATE_ASPECT_NAME_SETS) {
			HashSet<String> nonUpdateAspectNames = NON_UPDATE_ASPECT_NAME_SETS.get(thisClass);
			if (nonUpdateAspectNames == null) {
				nonUpdateAspectNames = new HashSet<String>();
				this.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
				NON_UPDATE_ASPECT_NAME_SETS.put(thisClass, nonUpdateAspectNames);
			}
			return nonUpdateAspectNames;
		}
	}

	/**
	 * Map sub-classes to the names of the aspects that should <em>not</em>
	 * trigger a {@link AbstractJpaProject#update() JPA project update}.
	 * This map is populated on-demand (i.e. as changes occur to instances of
	 * the sub-classes).
	 */
	private static final HashMap<Class<? extends AbstractJpaModel<?>>, HashSet<String>> NON_UPDATE_ASPECT_NAME_SETS = new HashMap<Class<? extends AbstractJpaModel<?>>, HashSet<String>>();

	/**
	 * Extend this method to add (to the specified collection) the names of
	 * aspects that should <em>not</em> trigger a JPA project <em>update</em>.
	 * This list will be effective for <em>all</em> instances of the model's
	 * class and will be built only once (per class loader).
	 * <p>
	 * To disable the JPA project <em>update</em> for changes to a particular
	 * <em>instance</em>, override {@link #aspectTriggersUpdate(String)}.
	 */
	protected void addNonUpdateAspectNamesTo(@SuppressWarnings("unused") Set<String> nonUpdateAspectNames) {
	// when you override this method, don't forget to include:
	//	super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
	}

	/**
	 * @see #aspectChanged(String)
	 * @see AbstractJpaProject#stateChanged()
	 * @see org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericContextRoot#stateChanged()
	 */
	public void stateChanged() {
		this.fireStateChanged();
		if (this.parent != null) {
			this.parent.stateChanged();
		}
	}
}
