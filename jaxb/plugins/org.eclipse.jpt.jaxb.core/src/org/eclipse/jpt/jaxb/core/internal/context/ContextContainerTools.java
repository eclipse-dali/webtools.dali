/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;

/**
 * Utility methods for manipulating context containers.
 */
public class ContextContainerTools {

	/**
	 * Adapter used to synchronize a context container with its corresponding
	 * resource container.
	 * 
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	public interface Adapter<C extends JaxbContextNode, R> {

		/**
		 * Return the container's context elements.
		 */
		Iterable<C> getContextElements();

		/**
		 * Return the container's current set of resource elements.
		 * These are what the context elements will be synchronized to.
		 */
		Iterable<R> getResourceElements();

		/**
		 * Return the resource element corresponding to the specified context
		 * element.
		 */
		R getResourceElement(C contextElement);

		/**
		 * Move the specified context element to the specified index.
		 */
		void moveContextElement(int index, C element);

		/**
		 * Add a context element for the specified resource element at the
		 * specified index.
		 */
		void addContextElement(int index, R resourceElement);

		/**
		 * Remove the specified context element from the container.
		 */
		void removeContextElement(C element);
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
	public static <C extends JaxbContextNode, R> void synchronizeWithResourceModel(Adapter<C, R> adapter) {
		sync(adapter, true);  // true = sync
	}

	/**
	 * @see #synchronizeWithResourceModel(Adapter)
	 */
	public static <C extends JaxbContextNode, R> void update(Adapter<C, R> adapter) {
		sync(adapter, false);  // false = update
	}

	/**
	 * The specified <code>sync</code> flag controls whether any surviving
	 * context nodes are either <em>synchronized</em> (<code>true</code>) or
	 * <em>updated</em> (<code>false</code>).
	 */
	protected static <C extends JaxbContextNode, R> void sync(Adapter<C, R> adapter, boolean sync) {
		HashSet<C> contextElements = CollectionTools.set(adapter.getContextElements());
		int resourceIndex = 0;

		for (R resourceElement : adapter.getResourceElements()) {
			boolean match = false;
			for (Iterator<C> stream = contextElements.iterator(); stream.hasNext(); ) {
				C contextElement = stream.next();
				if (Tools.valuesAreEqual(adapter.getResourceElement(contextElement), resourceElement)) {
					// we don't know the source index because the element has been moved by previously moved elements
					adapter.moveContextElement(resourceIndex, contextElement);
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
				adapter.addContextElement(resourceIndex, resourceElement);
			}
			resourceIndex++;
		}
		// remove any leftover context elements
		for (C contextElement : contextElements) {
			adapter.removeContextElement(contextElement);
		}
	}

	private ContextContainerTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
