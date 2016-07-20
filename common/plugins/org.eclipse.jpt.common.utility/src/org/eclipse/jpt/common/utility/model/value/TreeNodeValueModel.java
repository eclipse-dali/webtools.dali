/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.value;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Extend {@link ModifiablePropertyValueModel} to better support
 * {@link org.eclipse.jpt.common.utility.internal.model.value.swing.TreeModelAdapter}.
 * <p>
 * Implementors of this interface should fire a "state change" event
 * whenever the node's internal state changes in a way that the
 * tree listeners should be notified.
 * <p>
 * Implementors of this interface should also fire a "value property change"
 * event whenever the node's value changes. Typically, only nodes that
 * hold "primitive" data will fire this event.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <V> the type of values held by the model
 * 
 * @see org.eclipse.jpt.common.utility.internal.model.value.AbstractTreeNodeValueModel
 */
public interface TreeNodeValueModel<V>
	extends ModifiablePropertyValueModel<V>
{
	/**
	 * Return the node's parent node; null if the node
	 * is the root.
	 */
	TreeNodeValueModel<V> parent();
		@SuppressWarnings("rawtypes")
		Transformer PARENT_TRANSFORMER = new ParentTransformer();
		class ParentTransformer<V>
			extends TransformerAdapter<TreeNodeValueModel<V>, TreeNodeValueModel<V>>
		{
			@Override
			public TreeNodeValueModel<V> transform(TreeNodeValueModel<V> treeNodeValueModel) {
				return treeNodeValueModel.parent();
			}
		}

	/**
	 * Return the path to the node.
	 */
	TreeNodeValueModel<V>[] path();

	/**
	 * Return a list value model of the node's child nodes.
	 */
	ListValueModel<TreeNodeValueModel<V>> childrenModel();

	/**
	 * Return the node's child at the specified index.
	 */
	TreeNodeValueModel<V> child(int index);

	/**
	 * Return the size of the node's list of children.
	 */
	int childrenSize();

	/**
	 * Return the index in the node's list of children of the specified child.
	 */
	int indexOfChild(TreeNodeValueModel<V> child);

	/**
	 * Return whether the node is a leaf (i.e. it has no children)
	 */
	boolean isLeaf();

}
