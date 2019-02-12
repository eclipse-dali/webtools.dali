/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import java.util.Collection;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;

/**
 * Interface implemented by any object to appear in the JPA Structure view
 * and JPA Details view. This interface is also used by the JPA Selection
 * Managers.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface JpaStructureNode
	extends JpaContextModel
{
	/**
	 * Return the text range for the structure node's <em>full</em> text
	 * representation.
	 * @see #getSelectionTextRange()
	 */
	TextRange getFullTextRange();

	/**
	 * Return whether the structure node's full text representation contains
	 * the specified text offset.
	 * @see #getFullTextRange()
	 */
	boolean containsOffset(int textOffset);

	/**
	 * Return the structure node at the specified offset in the structure node's
	 * corresponding text file.
	 */
	JpaStructureNode getStructureNode(int textOffset);

	/**
	 * Return the text range to be used to select text in the editor
	 * corresponding to the structure node.
	 * @see #getFullTextRange()
	 */
	TextRange getSelectionTextRange();

	/**
	 * Return the structure node's context type.
	 * Type used to identify a JPA structure node's type with respect to the
	 * structure node's context
	 * (i.e. its type in the scope of the JPA platform that created the
	 * structure node and the structure node's resource).
	 * 
	 * @see #getJpaPlatform()
	 * @see #getResourceType()
	 * @see #getStructureType()
	 */
	ContextType getContextType();

	/**
	 * Return the structure node's type.
	 * This is used to find the appropriate UI provider for building the
	 * structure node's JPA Details page.
	 */
	Class<? extends JpaStructureNode> getStructureType();

	/**
	 * Add the to the specified collection the specified JPA file's root
	 * structure nodes (i.e. the nodes that will be the root elements in the
	 * JPA Structure view when the JPA file is displayed by the active editor).
	 * @see JpaFile#getRootStructureNodes()
	 */
	void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes);


	// ********** children **********

	/**
	 * String constant associated with changes to the structure
	 * node's children.
	 */
	String STRUCTURE_CHILDREN_COLLECTION = "structureChildren"; //$NON-NLS-1$

	/**
	 * Return the node's children, as to be displayed in the JPA Structure view.
	 */
	Iterable<? extends JpaStructureNode> getStructureChildren();

	/**
	 * Return the number of the node's children.
	 * @see #getStructureChildren()
	 */
	int getStructureChildrenSize();


	// ********** context type **********

	/**
	 * Type used to identify a JPA structure node's type with respect to the
	 * structure node's context
	 * (i.e. its type in the scope of the JPA platform that created the
	 * structure node and the structure node's resource).
	 */
	final class ContextType {
		private final JpaStructureNode node;

		public ContextType(JpaStructureNode node) {
			super();
			if (node == null) {
				throw new NullPointerException();
			}
			this.node = node;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if ((obj == null) || (obj.getClass() != this.getClass())) {
				return false;
			}
			ContextType other = (ContextType) obj;
			return this.node.getJpaPlatform().equals(other.node.getJpaPlatform()) &&
					this.node.getResourceType().equals(other.node.getResourceType()) &&
					this.node.getStructureType().equals(other.node.getStructureType());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + this.node.getJpaPlatform().hashCode();
			hash = hash * prime + this.node.getResourceType().hashCode();
			hash = hash * prime + this.node.getStructureType().hashCode();
			return hash;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.node);
		}
	}
}
