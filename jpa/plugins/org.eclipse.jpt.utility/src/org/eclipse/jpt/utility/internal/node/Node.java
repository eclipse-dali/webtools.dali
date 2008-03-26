/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.node;

import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.Model;

/**
 * This interface defines the methods that must be implemented
 * by any class whose instances are to be part of a containment hierarchy
 * that supports a "dirty" state and validation "problems".
 * 
 * Note: Methods marked "INTRA-TREE API" are typically only used by
 * the nodes themselves, as opposed to clients of the nodes. These
 * methods are called by a node on either its parent or its children.
 */
public interface Node extends Model, Comparable<Node> {


	// ********** containment hierarchy (parent/children) **********

	/**
	 * INTRA-TREE API?
	 * Return the node's parent in the containment hierarchy.
	 * Most nodes must have a parent. The parent is immutable.
	 * @see #children()
	 */
	Node getParent();

	/**
	 * INTRA-TREE API?
	 * Return the node's children, which are also nodes.
	 * @see #getParent()
	 */
	Iterator<Node> children();

	/**
	 * INTRA-TREE API?
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
	 * Add all the nodes in the object's branch of the tree,
	 * including the node itself, to the specified collection.
	 * Only really used for testing and debugging.
	 */
	void addAllNodesTo(Collection<Node> nodes);


	// ********** model synchronization support **********

	/**
	 * INTRA-TREE API
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
		String DIRTY_BRANCH_PROPERTY = "dirtyBranch";

	/**
	 * INTRA-TREE API
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
	 * Return a validator that will be invoked whenever a
	 * "validated" aspect of the node tree changes.
	 * Typically only the root node directly holds a validator.
	 */
	Validator validator();

	/**
	 * Set a validator that will be invoked whenever a
	 * "validated" aspect of the node tree changes.
	 * Typically only the root node directly holds a validator.
	 */
	void setValidator(Validator validator);

	/**
	 * Validate the node and its descendants.
	 * This is an explicit request invoked by a client; and it will
	 * typically be followed by a call to one of the following methods:
	 * 	#branchProblems()
	 * 	#hasBranchProblems()
	 * Whether the node maintains its problems on the fly
	 * or waits until this method is called is determined by the
	 * implementation.
	 * @see Problem
	 */
	void validateBranch();

	/**
	 * INTRA-TREE API
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
		String BRANCH_PROBLEMS_LIST = "branchProblems";

	/**
	 * Return the size of all the node's problems along with all the
	 * node's descendants' problems.
	 */
	int branchProblemsSize();

	/**
	 * Return whether the node or any of its descendants have problems.
	 */
	boolean hasBranchProblems();
		String HAS_BRANCH_PROBLEMS_PROPERTY = "hasBranchProblems";

	/**
	 * Return whether the node contains the specified branch problem.
	 */
	boolean containsBranchProblem(Problem problem);

	/**
	 * INTRA-TREE API
	 * Something changed, rebuild the node's collection of branch problems.
	 */
	void rebuildBranchProblems();

	/**
	 * INTRA-TREE API
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
		String COMMENT_PROPERTY = "comment";

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
	 * A validator will validate a node as appropriate.
	 * Typically the validation will
	 * 	- occur whenever a node has changed
	 * 	- encompass the entire tree containing the node
	 * 	- execute asynchronously
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


	// ********** helper implementations **********

	/**
	 * Straightforward implementation of the Reference interface
	 * defined above.
	 */
	public class SimpleReference implements Reference {
		private Node source;
		private Node target;
		public SimpleReference(Node source, Node target) {
			super();
			if (source == null || target == null) {
				throw new NullPointerException();
			}
			this.source = source;
			this.target = target;
		}
		public Node source() {
			return this.source;
		}
		public Node target() {
			return this.target;
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.source + " => " + this.target);
		}
	}

	/**
	 * Typical comparator that can be used to sort a collection of nodes.
	 * Sort based on display string:
	 * 	- identical objects are equal (which means that cannot
	 * 		co-exist in a SortedSet)
	 * 	- use the default collator (which typically interleaves
	 * 		lower- and upper-case letters)
	 * 	- allow duplicate display strings (from different objects)
	 * 	- try to return consistent results for same object pairs
	 */
	Comparator<Node> DEFAULT_COMPARATOR =
		new Comparator<Node>() {
			public int compare(Node node1, Node node2) {
				// disallow duplicates based on object identity
				if (node1 == node2) {
					return 0;
				}

				// first compare display strings using the default collator
				int result = Collator.getInstance().compare(node1.displayString(), node2.displayString());
				if (result != 0) {
					return result;
				}

				// then compare using object-id
				result = System.identityHashCode(node1) - System.identityHashCode(node2);
				if (result != 0) {
					return result;
				}

				// It's unlikely that we get to this point; but, just in case, we will return -1.
				// Unfortunately, this introduces some mild unpredictability to the sort order
				// (unless the objects are always passed into this method in the same order).
				return -1;		// if all else fails, indicate that o1 < o2
			}
			@Override
			public String toString() {
				return "Node.DEFAULT_COMPARATOR";
			}
		};


	/**
	 * This validator does nothing to validate the node.
	 */
	Validator NULL_VALIDATOR =
		new PluggableValidator(PluggableValidator.Delegate.Null.instance()) {
			@Override
			public String toString() {
				return "Node.NULL_VALIDATOR";
			}
		};

}
