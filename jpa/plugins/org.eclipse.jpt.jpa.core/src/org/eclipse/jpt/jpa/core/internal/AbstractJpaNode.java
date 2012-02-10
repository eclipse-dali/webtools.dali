/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTModifiedDeclaration.Adapter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneIterator;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(Class adapter) {
		return PlatformTools.getAdapter(this, adapter);
	}


	// ********** JpaNode implementation **********

	public JpaNode getParent() {
		return this.parent;
	}

	public IResource getResource() {
		return this.parent.getResource();
	}

	/**
	 * @see AbstractJpaProject#getJpaProject()
	 */
	public JpaProject getJpaProject() {
		return this.parent.getJpaProject();
	}

	public JpaProject.Manager getJpaProjectManager() {
		return this.getJpaProject().getManager();
	}


	// ********** convenience methods **********

	protected IJavaProject getJavaProject() {
		return this.getJpaProject().getJavaProject();
	}

	/**
	 * This is <code>public</code> as a convenience because various subclasses
	 * implement interfaces that require this method.
	 */
	public JpaPlatform getJpaPlatform() {
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


	// ********** logging **********

	/**
	 * Log the specified message.
	 */
	protected void log(String msg) {
		this.getJpaProjectManager().log(msg);
	}

	/**
	 * Log the specified exception/error.
	 */
	protected void log(Throwable throwable) {
		this.getJpaProjectManager().log(throwable);
	}

	/**
	 * Log the specified message and exception/error.
	 */
	protected void log(String msg, Throwable throwable) {
		this.getJpaProjectManager().log(msg, throwable);
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
	protected abstract class CollectionContainer<C, R>
		implements Iterable<C>
	{
		protected final Vector<C> contextElements = new Vector<C>();

		protected CollectionContainer() {
			super();
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

		/**
		 * clients needs to call initialize if necessary
		 */
		public void initialize() {
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

		public Iterator<C> iterator() {
			return new CloneIterator<C>(this.contextElements);
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
		 * Add the specified context element to the container at the specified
		 * index.
		 */
		protected abstract C addContextElement_(int index, C element);

		/**
		 * Add context elements for the specified resource elements at the
		 * specified index.
		 */
		public Iterable<C> addContextElements(int index, Iterable<R> resourceElements) {
			ArrayList<C> newContextElements = new ArrayList<C>();
			for (R resourceElement : resourceElements) {
				newContextElements.add(this.buildContextElement(resourceElement));
			}
			return this.addAll(index, newContextElements);
		}

		/**
		 * Add the specified context elements to the collection.
		 */
		protected abstract Iterable<C> addAll(int index, Iterable<C> elements);

		/**
		 * Remove the specified context element from the container.
		 */
		public abstract void removeContextElement(C element);

		/**
		 * Remove the specified context elements from the container.
		 */
		public abstract void removeAll(Iterable<C> elements);

		protected abstract void moveContextElement(int index, C element);

		@Override
		public String toString() {
			return this.contextElements.toString();
		}
	}


	/**
	 * Adapter used to synchronize a context collection container with its corresponding
	 * resource container.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	protected abstract class AbstractContextCollectionContainer<C extends JpaContextNode, R>
		extends CollectionContainer<C, R>
	{
		protected AbstractContextCollectionContainer() {
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
	 * Adapter used to synchronize a context collection container with its corresponding
	 * resource container.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	protected abstract class ContextCollectionContainer<C extends JpaContextNode, R>
		extends AbstractContextCollectionContainer<C, R>
	{
		protected ContextCollectionContainer() {
			super();
		}

		@Override
		protected C addContextElement_(int index, C element) {
			// ignore the index - not a list
			AbstractJpaNode.this.addItemToCollection(element, this.contextElements, this.getContextElementsPropertyName());
			return element;
		}

		@Override
		protected Iterable<C> addAll(int index, Iterable<C> elements) {
			// ignore the index - not a list
			AbstractJpaNode.this.addItemsToCollection(elements, this.contextElements, this.getContextElementsPropertyName());
			return elements;
		}

		@Override
		public void removeContextElement(C element) {
			AbstractJpaNode.this.removeItemFromCollection(element, this.contextElements, this.getContextElementsPropertyName());
		}

		@Override
		public void removeAll(Iterable<C> elements) {
			AbstractJpaNode.this.removeItemsFromCollection(elements, this.contextElements, this.getContextElementsPropertyName());
		}

		@Override
		protected void moveContextElement(int index, C element) {
			// NOP - not a list
		}
	}


	/**
	 * Adapter used to synchronize a context list container with its corresponding
	 * resource container.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	protected abstract class ContextListContainer<C extends JpaContextNode, R>
		extends AbstractContextCollectionContainer<C, R>
	{
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
		public int indexOfContextElement(C element) {
			return this.contextElements.indexOf(element);
		}

		public C getContextElement(int index) {
			return this.contextElements.elementAt(index);
		}

		@Override
		protected C addContextElement_(int index, C element) {
			AbstractJpaNode.this.addItemToList(index, element, this.contextElements, this.getContextElementsPropertyName());
			return element;
		}

		@Override
		protected Iterable<C> addAll(int index, Iterable<C> elements) {
			AbstractJpaNode.this.addItemsToList(index, elements, this.contextElements, this.getContextElementsPropertyName());
			return elements;
		}

		/**
		 * Move the context element at the specified target index to the 
		 * specified source index.
		 */
		public void moveContextElement(int targetIndex, int sourceIndex) {
			AbstractJpaNode.this.moveItemInList(targetIndex, sourceIndex, this.contextElements, this.getContextElementsPropertyName());
		}

		@Override
		public void moveContextElement(int index, C element) {
			AbstractJpaNode.this.moveItemInList(index, element, this.contextElements, this.getContextElementsPropertyName());
		}

		/**
		 * clear the list of context elements
		 */
		public void clearContextList() {
			AbstractJpaNode.this.clearList(this.contextElements, getContextElementsPropertyName());
		}

		/**
		 * Remove the context element at the specified index from the container.
		 */
		public C removeContextElement(int index) {
			return AbstractJpaNode.this.removeItemFromList(index, this.contextElements, this.getContextElementsPropertyName());
		}

		@Override
		public void removeContextElement(C element) {
			this.removeContextElement(this.indexOfContextElement(element));
		}

		@Override
		public void removeAll(Iterable<C> elements) {
			AbstractJpaNode.this.removeItemsFromList(elements, this.contextElements, this.getContextElementsPropertyName());
		}
	}


	/**
	 * Adapter used to synchronize a context list container with its corresponding
	 * resource container.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	protected abstract class ListContainer<C, R>
		extends CollectionContainer<C, R>
	{
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
		public int indexOfContextElement(C element) {
			return this.contextElements.indexOf(element);
		}

		public C getContextElement(int index) {
			return this.contextElements.get(index);
		}

		@Override
		protected C addContextElement_(int index, C element) {
			AbstractJpaNode.this.addItemToList(index, element, this.contextElements, this.getContextElementsPropertyName());
			return element;
		}

		@Override
		protected Iterable<C> addAll(int index, Iterable<C> newContextElements) {
			AbstractJpaNode.this.addItemsToList(index, newContextElements, this.contextElements, this.getContextElementsPropertyName());
			return newContextElements;
		}

		/**
		 * Move the context element at the specified target index to the 
		 * specified source index.
		 */
		public void moveContextElement(int targetIndex, int sourceIndex) {
			AbstractJpaNode.this.moveItemInList(targetIndex, sourceIndex, this.contextElements, this.getContextElementsPropertyName());
		}

		@Override
		public void moveContextElement(int index, C element) {
			AbstractJpaNode.this.moveItemInList(index, element, this.contextElements, this.getContextElementsPropertyName());
		}

		@Override
		public void removeContextElement(C element) {
			this.removeContextElement(this.indexOfContextElement(element));
		}

		/**
		 * Remove the context element at the specified index from the container.
		 */
		public void removeContextElement(int index) {
			AbstractJpaNode.this.removeItemFromList(index, this.contextElements, this.getContextElementsPropertyName());
		}

		@Override
		public void removeAll(Iterable<C> elements) {
			AbstractJpaNode.this.removeItemsFromList(elements, this.contextElements, this.getContextElementsPropertyName());
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
}
