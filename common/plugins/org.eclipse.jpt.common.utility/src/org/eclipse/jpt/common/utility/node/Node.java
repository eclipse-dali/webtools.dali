/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.node;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.node.PluggableValidator;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * This interface defines the methods that must be implemented
 * by any class whose instances are to be part of a containment hierarchy
 * that supports a "dirty" state and validation "problems".
 * <p>
 * <strong>NB:</strong> Methods marked "INTRA-TREE API" are typically only used by
 * the nodes themselves, as opposed to clients of the nodes. These
 * methods are called by a node on either its parent or its children.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Node
	extends Model
{
	// ********** containment hierarchy (parent/children) **********

	/**
	 * INTRA-TREE API?
	 * <p>
	 * Return the node's parent in the containment hierarchy.
	 * Most nodes must have a parent. The parent is immutable.
	 * @see #children()
	 */
	Node getParent();

	/**
	 * INTRA-TREE API?
	 * <p>
	 * Return the node's children, which are also nodes.
	 * @see #getParent()
	 */
	Iterator<Node> children();

	/**
	 * INTRA-TREE API?
	 * <p>
	 * Return the containment hierarchy's root node.
	 * Most nodes must have a root.
	 * @see #getParent()
	 */
	Node root();

	/**
	 * Return whether the node is a descendant of the specified node.
	 * By definition, a node is a descendant of itself.
	 */
	boolean isDescendantOf(Node node);

	/**
	 * INTRA-TREE API
	 * <p>
	 * Add the node's "references", and all the node's descendants'
	 * "references", to the specified collection. "References" are
	 * objects that are "referenced" by another object, as opposed
	 * to "owned" by another object.
	 * This method is of particular concern to Handles, since most
	 * (hopefully all) "references" are held by Handles.
	 * @see Reference
	 * @see #children()
	 */
	void addBranchReferencesTo(Collection<Node.Reference> branchReferences);

	/**
	 * INTRA-TREE API?
	 * <p>
	 * Add all the nodes in the object's branch of the tree,
	 * including the node itself, to the specified collection.
	 * Only really used for testing and debugging.
	 */
	void addAllNodesTo(Collection<Node> nodes);


	// ********** model synchronization support **********

	/**
	 * INTRA-TREE API
	 * <p>
	 * This is a general notification that the specified node has been
	 * removed from the tree. The node receiving this notification
	 * should perform any necessary updates to remain in synch
	 * with the tree (e.g. clearing out or replacing any references
	 * to the removed node or any of the removed node's descendants).
	 * @see #isDescendantOf(Node)
	 */
	void nodeRemoved(Node node);

	/**
	 * INTRA-TREE API
	 * <p>
	 * This is a general notification that the specified node has been
	 * renamed. The node receiving this notification should mark its
	 * branch dirty if necessary (i.e. it references the renamed node
	 * or one of its descendants). This method is of particular concern
	 * to Handles.
	 * @see #isDescendantOf(Node)
	 */
	void nodeRenamed(Node node);
	

	// ********** dirty flag support **********

	/**
	 * Return whether any persistent aspects of the node,
	 * or any of its descendants, have changed since the node and
	 * its descendants were last read or saved.
	 */
	boolean isDirtyBranch();
		String DIRTY_BRANCH_PROPERTY = "dirtyBranch"; //$NON-NLS-1$

	/**
	 * INTRA-TREE API
	 * <p>
	 * Mark the node and its parent as dirty branches.
	 * This message is propagated up the containment
	 * tree when a particular node becomes dirty.
	 */
	void markBranchDirty();

	/**
	 * Mark the node and all its descendants as dirty.
	 * This is used when the save location of some
	 * top-level node is changed and the entire
	 * containment tree must be marked dirty so it
	 * will be written out.
	 */
	void markEntireBranchDirty();

	/**
	 * INTRA-TREE API
	 * <p>
	 * A child node's branch has been marked clean. If the node
	 * itself is clean and if all of its children are also clean, the
	 * node's branch can be marked clean. Then, if the node's
	 * branch is clean, the node will notify its parent that it might
	 * be clean also. This message is propagated up the containment
	 * tree when a particular node becomes clean.
	 */
	void markBranchCleanIfPossible();

	/**
	 * INTRA-TREE API
	 * <p>
	 * Mark the node and all its descendants as clean.
	 * Typically used when the node has just been
	 * read in or written out.
	 * This method is for internal use only; it is not for
	 * client use.
	 * Not the best of method names.... :-(
	 */
	void cascadeMarkEntireBranchClean();


	// ********** problems **********

	/**
	 * INTRA-TREE API
	 * <p>
	 * Return a validator that will be invoked whenever a
	 * "validated" aspect of the node tree changes.
	 * Typically only the root node directly holds a validator.
	 */
	Validator getValidator();

	/**
	 * Set a validator that will be invoked whenever a
	 * "validated" aspect of the node tree changes.
	 * Typically only the root node directly holds a validator.
	 */
	void setValidator(Validator validator);

	/**
	 * Validate the node and its descendants.
	 * This is an explicit request invoked by a client; and it will
	 * typically be followed by a call to one of the following methods:<ul>
	 * <li>{@link #branchProblems()}
	 * <li>{@link #hasBranchProblems()}
	 * </ul>
	 * Whether the node maintains its problems on the fly
	 * or waits until this method is called is determined by the
	 * implementation.
	 * @see Problem
	 */
	void validateBranch();

	/**
	 * INTRA-TREE API
	 * <p>
	 * Validate the node and all of its descendants,
	 * and update their sets of "branch" problems.
	 * Return true if the collection of "branch" problems has changed.
	 * This method is for internal use only; it is not for
	 * client use.
	 */
	boolean validateBranchInternal();

	/**
	 * Return all the node's problems along with all the
	 * node's descendants' problems.
	 */
	ListIterator<Problem> branchProblems();
		String BRANCH_PROBLEMS_LIST = "branchProblems"; //$NON-NLS-1$

	/**
	 * Return the size of all the node's problems along with all the
	 * node's descendants' problems.
	 */
	int branchProblemsSize();

	/**
	 * Return whether the node or any of its descendants have problems.
	 */
	boolean hasBranchProblems();
		String HAS_BRANCH_PROBLEMS_PROPERTY = "hasBranchProblems"; //$NON-NLS-1$

	/**
	 * Return whether the node contains the specified branch problem.
	 */
	boolean containsBranchProblem(Problem problem);

	/**
	 * INTRA-TREE API
	 * <p>
	 * Something changed, rebuild the node's collection of branch problems.
	 */
	void rebuildBranchProblems();

	/**
	 * INTRA-TREE API
	 * <p>
	 * Add the node's problems, and all the node's descendants'
	 * problems, to the specified list.
	 * A call to this method should be immediately preceded by a call to
	 * #validateBranch() or all of the problems might not be
	 * added to the list.
	 * @see Problem
	 */
	void addBranchProblemsTo(List<Problem> branchProblems);

	/**
	 * Clear the node's "branch" problems and the "branch"
	 * problems of all of its descendants.
	 */
	void clearAllBranchProblems();

	/**
	 * INTRA-TREE API
	 * <p>
	 * Clear the node's "branch" problems and the "branch"
	 * problems of all of its descendants.
	 * Return true if the collection of "branch" problems has changed.
	 * This method is for internal use only; it is not for
	 * client use.
	 */
	boolean clearAllBranchProblemsInternal();


	// ********** comment **********

	/**
	 * Return the user comment concerning the node.
	 */
	String comment();
		String COMMENT_PROPERTY = "comment"; //$NON-NLS-1$

	/**
	 * Set the user comment concerning the node.
	 */
	void setComment(String comment);


	// ********** displaying/sorting **********

	/**
	 * Return a string representation of the model, suitable for sorting.
	 */
	String displayString();


	// ********** sub-interfaces **********

	/**
	 * Simple interface defining a "reference" between two nodes.
	 * @see Node#addBranchReferencesTo(java.util.Collection)
	 */
	interface Reference {
		/**
		 * Return the "source" node of the reference, i.e. the node that
		 * references the "target" node.
		 */
		Node source();

		/**
		 * Return the "target" node of the reference, i.e. the node that
		 * is referenced by the "source" node.
		 */
		Node target();
	}


	/**
	 * Define an interface describing the problems associated with a {@link Node}.
	 */
	public interface Problem {
		/**
		 * Return the node most closely associated with the problem.
		 */
		Node source();

		/**
		 * Return a key that can be used to uniquely identify the problem's message.
		 */
		String messageKey();

		/**
		 * Return the arguments associate with the problem's message.
		 */
		Object[] messageArguments();

		/**
		 * Return the type of the identified problem's message
		 */
		int messageType();

		/**
		 * Return whether the problem is equal to the specified object.
		 * It is equal if the specified object is a implementation of the
		 * Problem interface and its source, message key, and message
		 * arguments are all equal to this problem's.
		 */
		boolean equals(Object o);

		/**
		 * Return the problem's hash code, which should calculated as an
		 * XOR of the source's hash code and the message key's hash code.
		 */
		int hashCode();
	}


	/**
	 * A validator will validate a node as appropriate.
	 * Typically the validation will<ul>
	 * <li>occur whenever a node has changed
	 * <li>encompass the entire tree containing the node
	 * <li>execute asynchronously
	 * </ul>
	 */
	interface Validator {
		/**
		 * A "significant" aspect has changed;
		 * validate the node as appropriate
		 */
		void validate();

		/**
		 * Stop all validation of the node until #resume() is called.
		 * This can be used to improve the performance of any long-running
		 * action that triggers numerous changes to the node. Be sure to
		 * match a call to this method with a corresponding call to
		 * #resume().
		 */
		void pause();

		/**
		 * Resume validation of the node. This method can only be
		 * called after a matching call to #pause().
		 */
		void resume();
	}

	/**
	 * This validator does nothing to validate the node.
	 */
	Validator NULL_VALIDATOR = new PluggableValidator(PluggableValidator.Delegate.Null.instance());
}
