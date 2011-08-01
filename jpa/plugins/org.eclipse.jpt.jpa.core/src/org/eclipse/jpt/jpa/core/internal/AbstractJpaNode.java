/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTModifiedDeclaration.Adapter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
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
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
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
	protected static final String[] EMPTY_STRING_ARRAY = StringTools.EMPTY_STRING_ARRAY;


	/**
	 * Adapter used to synchronize a context collection container with its corresponding
	 * resource container.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	protected abstract class CollectionContainer<C, R> {

		protected final Vector<C> contextElements = new Vector<C>();

		protected CollectionContainer() {
			super();
			this.initializeContextElements();
		}

		/**
		 * Return the container's current set of resource elements.
		 * These are what the context elements will be synchronized to.
		 */
		protected abstract Iterable<R> getResourceElements();

		/**
		 * Return the resource element corresponding to the specified context
		 * element.
		 */
		protected abstract R getResourceElement(C contextElement);

		/**
		 * Build and return a context element for the given resource element
		 */
		protected abstract C buildContextElement(R resourceElement);

		/**
		 * Return the property name for event notification when the collection is changed.
		 */
		protected abstract String getContextElementsPropertyName();

		protected void initializeContextElements() {
			for (R resourceElement : this.getResourceElements()) {
				this.contextElements.add(this.buildContextElement(resourceElement));
			}
		}

		/**
		 * Return an Iterable of the context elements
		 */
		public Iterable<C> getContextElements() {
			return new LiveCloneIterable<C>(this.contextElements);
		}

		/**
		 * Return the size of the context elements collection
		 */
		public int getContextElementsSize() {
			return this.contextElements.size();
		}

		/**
		 * Add a context element for the specified resource element at the
		 * specified index.
		 */
		public C addContextElement(int index, R resourceElement) {
			return this.addContextElement_(index, this.buildContextElement(resourceElement));
		}

		/**
		 * Add the specified context element to the collection ignoring
		 * the specified index as we only have a collection
		 */
		protected C addContextElement_(@SuppressWarnings("unused") int index, C contextElement) {
			AbstractJpaNode.this.addItemToCollection(contextElement, this.contextElements, this.getContextElementsPropertyName());
			return contextElement;
		}

		/**
		 * Remove the specified context element from the container.
		 */
		public void removeContextElement(C element) {
			AbstractJpaNode.this.removeItemFromCollection(element, this.contextElements, this.getContextElementsPropertyName());
		}

		@SuppressWarnings("unused") 
		protected void moveContextElement(int index, C element) {
			//no-op, not a list
		}
	}

	/**
	 * Adapter used to synchronize a context collection container with its corresponding
	 * resource container.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	protected abstract class ContextCollectionContainer<C extends JpaContextNode, R> extends CollectionContainer<C, R> {

		protected ContextCollectionContainer() {
			super();
		}

		/**
		 * Using the specified adapter, synchronize a context container with its
		 * corresponding resource container: moving, removing, and adding elements
		 * as necessary.
		 * <p>
		 * We can do this because:<ul>
		 * <li>the XML translators will <em>move</em> the EMF elements when
		 * appropriate (as opposed to simply rebuilding them in place).
		 * <li>the Java resource model will re-use existing annotations when
		 * appropriate (as opposed to simply rebuilding them in place).
		 * </ul>
		 */
		public void synchronizeWithResourceModel() {
			sync(true);  // true = sync
		}

		/**
		 * @see #synchronizeWithResourceModel(Adapter)
		 */
		public void update() {
			sync(false);  // false = update
		}

		/**
		 * The specified <code>sync</code> flag controls whether any surviving
		 * context nodes are either <em>synchronized</em> (<code>true</code>) or
		 * <em>updated</em> (<code>false</code>).
		 */
		protected void sync(boolean sync) {
			HashSet<C> contextElements = CollectionTools.set(this.getContextElements());
			ArrayList<C> contextElementsToSync = new ArrayList<C>(contextElements.size());
			int resourceIndex = 0;

			for (R resourceElement : this.getResourceElements()) {
				boolean match = false;
				for (Iterator<C> stream = contextElements.iterator(); stream.hasNext(); ) {
					C contextElement = stream.next();
					if (Tools.valuesAreEqual(this.getResourceElement(contextElement), resourceElement)) {
						// we don't know the source index because the element has been moved by previously moved elements
						this.moveContextElement(resourceIndex, contextElement);
						stream.remove();
						// TODO perform update here someday...
						contextElementsToSync.add(contextElement);
						match = true;
						break;
					}
				}
				if ( ! match) {
					// added elements are sync'ed during construction or will be
					// updated during the next "update" (which is triggered by
					// their addition to the model)
					this.addContextElement(resourceIndex, resourceElement);
				}
				resourceIndex++;
			}
			// remove any leftover context elements
			for (C contextElement : contextElements) {
				removeContextElement(contextElement);
			}
			// TODO bjv
			// take care of the structural changes before sync'ing the remaining elements;
			// we might be able to do this inline once we get rid of the "list change" events
			// and go with only add, remove, etc. list events
			// (these syncs will trigger "list change" events with list aspect adapters, which
			// trigger refreshes of UI adapters (e.g. TableModelAdapter) which will prematurely
			// discover any structural changes... :( )
			// see ItemAspectListValueModelAdapter.itemAspectChanged(EventObject)
			for (C contextElement : contextElementsToSync) {
				if (sync) {
					contextElement.synchronizeWithResourceModel();
				} else {
					contextElement.update();
				}
			}
		}
	}

	/**
	 * Adapter used to synchronize a context list container with its corresponding
	 * resource container.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	protected abstract class ListContainer<C, R>
		extends CollectionContainer<C, R> {

		protected ListContainer() {
			super();
		}

		@Override
		public ListIterable<C> getContextElements() {
			return new LiveCloneListIterable<C>(this.contextElements);
		}

		@Override
		protected abstract ListIterable<R> getResourceElements();

		/**
		 * Return the index of the specified context element.
		 */
		public int indexOfContextElement(C contextElement) {
			return this.contextElements.indexOf(contextElement);
		}

		public C getContextElement(int index) {
			return this.contextElements.elementAt(index);
		}

		/**
		 * Add a context element for the specified resource element at the
		 * specified index.
		 */
		@Override
		protected C addContextElement_(int index, C contextElement) {
			AbstractJpaNode.this.addItemToList(index, contextElement, this.contextElements, this.getContextElementsPropertyName());
			return contextElement;
		}

		/**
		 * Move the context element at the specified target index to the 
		 * specified source index.
		 */
		public void moveContextElement(int targetIndex, int sourceIndex) {
			this.moveContextElement(targetIndex, this.contextElements.get(sourceIndex));
		}

		/**
		 * Move the specified context element to the specified index.
		 */
		@Override
		public void moveContextElement(int index, C element) {
			AbstractJpaNode.this.moveItemInList(index, element, this.contextElements, this.getContextElementsPropertyName());
		}

		/**
		 * Remove the context element at the specified index from the container.
		 */
		public void removeContextElement(int index) {
			AbstractJpaNode.this.removeItemFromList(index, this.contextElements, this.getContextElementsPropertyName());
		}

		public void synchronizeWithResourceModel() {
			ListIterable<R> resourceElements = getResourceElements();
			
			int index = 0;
			for (R resourceElement : resourceElements) {
				if (this.getContextElementsSize() > index) {
					if (this.getContextElement(index) != resourceElement) {
						this.addContextElement(index, resourceElement);
					}
				}
				else {
					this.addContextElement(index, resourceElement);			
				}
				index++;
			}
			
			for ( ; index < this.getContextElementsSize(); ) {
				this.removeContextElement(index);
			}
		}
	}

	/**
	 * Adapter used to synchronize a context list container with its corresponding
	 * resource container.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	protected abstract class ContextListContainer<C extends JpaContextNode, R>
		extends ContextCollectionContainer<C, R> {

		protected ContextListContainer() {
			super();
		}

		@Override
		public ListIterable<C> getContextElements() {
			return new LiveCloneListIterable<C>(this.contextElements);
		}

		@Override
		protected abstract ListIterable<R> getResourceElements();

		/**
		 * Return the index of the specified context element.
		 */
		public int indexOfContextElement(C contextElement) {
			return this.contextElements.indexOf(contextElement);
		}

		public C getContextElement(int index) {
			return this.contextElements.elementAt(index);
		}

		/**
		 * Add a context element for the specified resource element at the
		 * specified index.
		 */
		@Override
		protected C addContextElement_(int index, C contextElement) {
			AbstractJpaNode.this.addItemToList(index, contextElement, this.contextElements, this.getContextElementsPropertyName());
			return contextElement;
		}

		/**
		 * Move the context element at the specified target index to the 
		 * specified source index.
		 */
		public void moveContextElement(int targetIndex, int sourceIndex) {
			this.moveContextElement(targetIndex, this.contextElements.get(sourceIndex));
		}

		/**
		 * clear the list of context elements
		 */
		public void clearContextList() {
			AbstractJpaNode.this.clearList(this.contextElements, getContextElementsPropertyName());
		}

		/**
		 * Move the specified context element to the specified index.
		 */
		@Override
		public void moveContextElement(int index, C element) {
			AbstractJpaNode.this.moveItemInList(index, element, this.contextElements, this.getContextElementsPropertyName());
		}

		/**
		 * Remove the context element at the specified index from the container.
		 */
		public C removeContextElement(int index) {
			return AbstractJpaNode.this.removeItemFromList(index, this.contextElements, this.getContextElementsPropertyName());
		}
	}
}
