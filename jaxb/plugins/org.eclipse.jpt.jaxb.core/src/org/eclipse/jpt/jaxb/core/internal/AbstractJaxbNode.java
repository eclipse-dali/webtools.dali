/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

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
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.AspectChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;

/**
 * Some common Dali behavior:<ul>
 * <li>containment hierarchy
 * <li>Eclipse adaptable
 * <li>update triggers
 * </ul>
 */
public abstract class AbstractJaxbNode
	extends AbstractModel
	implements JaxbNode
{
	private final JaxbNode parent;


	// ********** constructor/initialization **********

	protected AbstractJaxbNode(JaxbNode parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}

	protected void checkParent(JaxbNode p) {
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
		return new AspectChangeSupport(this, this.buildChangeSupportListener());
	}

	protected AspectChangeSupport.Listener buildChangeSupportListener() {
		return new AspectChangeSupport.Listener() {
			public void aspectChanged(String aspectName) {
				AbstractJaxbNode.this.aspectChanged(aspectName);
			}
		};
	}


	// ********** IAdaptable implementation **********

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}


	// ********** JaxbNode implementation **********

	public JaxbNode getParent() {
		return this.parent;
	}

	public IResource getResource() {
		return this.parent.getResource();
	}

	public JaxbProject getJaxbProject() {
		return this.parent.getJaxbProject();
	}


	// ********** convenience methods **********

	protected JaxbPlatform getPlatform() {
		return this.getJaxbProject().getPlatform();
	}

//	protected JaxbPlatform.Version getJaxbPlatformVersion() {
//		return this.getJaxbPlatform().getJaxbPlatformVersion();
//	}

	protected JaxbFactory getFactory() {
		return this.getPlatform().getFactory();
	}

	protected JaxbFile getJaxbFile(IFile file) {
		return this.getJaxbProject().getJaxbFile(file);
	}


	// ********** CallbackChangeSupport.Listener support **********

	protected void aspectChanged(String aspectName) {
		if (this.aspectTriggersUpdate(aspectName)) {
//			String msg = Thread.currentThread() + " aspect change: " + this + ": " + aspectName;
//			System.out.println(msg);
//			new Exception(msg).printStackTrace(System.out);
			this.stateChanged();
		}
	}

	private boolean aspectTriggersUpdate(String aspectName) {
		return ! this.aspectDoesNotTriggerUpdate(aspectName);
	}

	protected boolean aspectDoesNotTriggerUpdate(String aspectName) {
		// ignore state changes so we don't get a stack overflow :-)
		// (and we don't use state changes except here)
		return (aspectName == null) ||
				this.nonUpdateAspectNames().contains(aspectName);
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

	private static final HashMap<Class<? extends AbstractJaxbNode>, HashSet<String>> nonUpdateAspectNameSets = new HashMap<Class<? extends AbstractJaxbNode>, HashSet<String>>();

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
			AbstractJaxbNode.this.addItemToCollection(contextElement, this.contextElements, this.getContextElementsPropertyName());
			return contextElement;
		}

		/**
		 * Remove the specified context element from the container.
		 */
		public void removeContextElement(C element) {
			AbstractJaxbNode.this.removeItemFromCollection(element, this.contextElements, this.getContextElementsPropertyName());
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
	protected abstract class ContextCollectionContainer<C extends JaxbContextNode, R> extends CollectionContainer<C, R> {

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
			int resourceIndex = 0;

			for (R resourceElement : this.getResourceElements()) {
				boolean match = false;
				for (Iterator<C> stream = contextElements.iterator(); stream.hasNext(); ) {
					C contextElement = stream.next();
					if (Tools.valuesAreEqual(this.getResourceElement(contextElement), resourceElement)) {
						// we don't know the source index because the element has been moved by previously moved elements
						this.moveContextElement(resourceIndex, contextElement);
						stream.remove();
						if (sync) {
							contextElement.synchronizeWithResourceModel();
						} else {
							contextElement.update();
						}
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
				this.removeContextElement(contextElement);
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
			AbstractJaxbNode.this.addItemToList(index, contextElement, this.contextElements, this.getContextElementsPropertyName());
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
			AbstractJaxbNode.this.moveItemInList(index, element, this.contextElements, this.getContextElementsPropertyName());
		}

		/**
		 * Remove the context element at the specified index from the container.
		 */
		public void removeContextElement(int index) {
			AbstractJaxbNode.this.removeItemFromList(index, this.contextElements, this.getContextElementsPropertyName());
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
	protected abstract class ContextListContainer<C extends JaxbContextNode, R>
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
			AbstractJaxbNode.this.addItemToList(index, contextElement, this.contextElements, this.getContextElementsPropertyName());
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
			AbstractJaxbNode.this.moveItemInList(index, element, this.contextElements, this.getContextElementsPropertyName());
		}

		/**
		 * Remove the context element at the specified index from the container.
		 */
		public void removeContextElement(int index) {
			AbstractJaxbNode.this.removeItemFromList(index, this.contextElements, this.getContextElementsPropertyName());
		}

		@Override
		public void removeContextElement(C contextElement) {
			AbstractJaxbNode.this.removeItemFromList(contextElement, this.contextElements, this.getContextElementsPropertyName());
		}
	}
}
