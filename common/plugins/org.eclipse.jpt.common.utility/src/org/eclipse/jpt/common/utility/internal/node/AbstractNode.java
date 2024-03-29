/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.AspectChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.node.Node;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Base class for {@link Node} classes.
 * Provides support for the following:<ul>
 * <li>initialization
 * <li>enforced object identity wrt #equals()/#hashCode()
 * <li>containment hierarchy (parent/child)
 * <li>user comment
 * <li>dirty flag
 * <li>problems
 * <li>sorting
 * </ul>
 * Typically, subclasses should consider implementing the following methods:<ul>
 * <li>the appropriate constructors
 *     (with the appropriately-restrictive type declaration for parent)
 * <li>{@link #initialize()}
 * <li>{@link #initialize(Node)}
 * <li>{@link #checkParent(Node)}
 * <li>{@link #addChildrenTo(List)}
 * <li>{@link #nodeRemoved(Node)}
 * <li>{@link #getValidator()}
 * <li>{@link #transientAspectNames()} or
 *     {@link #addTransientAspectNamesTo(Set)}
 * <li>{@link #addProblemsTo(List)}
 * <li>{@link #nonValidatedAspectNames()}
 * <li>{@link #addNonValidatedAspectNamesTo(Set)}
 * <li>{@link #displayString()}
 * <li>{@link #toString(StringBuilder)}
 * </ul>
 */
public abstract class AbstractNode 
	extends AbstractModel
	implements Node
{

	/** Containment hierarchy. */
	private Node parent;  // pseudo-final

	/** Track whether the node has changed. */
	private volatile boolean dirty;
	private volatile boolean dirtyBranch;

	/**
	 * The node's problems, as calculated during validation.
	 * This list should only be modified via a ProblemSynchronizer,
	 * allowing for asynchronous modification from another thread.
	 */
	private Vector<Problem> problems;		// pseudo-final

	/**
	 * Cache the node's "branch" problems, as calculated during validation.
	 * This list should only be modified via a ProblemSynchronizer,
	 * allowing for asynchronous modification from another thread.
	 * This must be recalculated every time this node or one of its
	 * descendants changes it problems.
	 */
	private Vector<Problem> branchProblems;		// pseudo-final

	/** User comment. */
	private volatile String comment;


	// ********** static fields **********

	/**
	 * Sets of transient aspect names, keyed by class.
	 * This is built up lazily, as the objects are modified.
	 */
	private static final HashMap<Class<? extends AbstractNode>, HashSet<String>> transientAspectNameSets = new HashMap<Class<? extends AbstractNode>, HashSet<String>>();

	/**
	 * Sets of non-validated aspect names, keyed by class.
	 * This is built up lazily, as the objects are modified.
	 */
	private static final HashMap<Class<? extends AbstractNode>, HashSet<String>> nonValidatedAspectNameSets = new HashMap<Class<? extends AbstractNode>, HashSet<String>>();


	// ********** constructors **********

	/**
	 * Most objects must have a parent.
	 * Use this constructor to create a new node.
	 * @see #initialize(Node)
	 */
	protected AbstractNode(Node parent) {
		super();
		this.initialize();
		this.initialize(parent);
	}


	// ********** initialization **********

	/**
	 * Initialize a newly-created instance.
	 * @see #initialize(Node)
	 */
	protected void initialize() {
		this.comment = ""; //$NON-NLS-1$

		// a new object is dirty, by definition
		this.dirty = true;
		this.dirtyBranch = true;

		this.problems = new Vector<Problem>();
		this.branchProblems = new Vector<Problem>();

	// when you override this method, don't forget to include:
	//	super.initialize();
	}

	/**
	 * Initialize a newly-created instance.
	 * @see #initialize()
	 */
	protected void initialize(Node parentNode) {
		this.checkParent(parentNode);
		this.parent = parentNode;
	// when you override this method, don't forget to include:
	//	super.initialize(parentNode);
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new AspectChangeSupport(this, this.buildChangeSupportListener());
	}

	protected AspectChangeSupport.Listener buildChangeSupportListener() {
		return new AspectChangeSupport.Listener() {
			public void aspectChanged(String aspectName) {
				AbstractNode.this.aspectChanged(aspectName);
			}
		};
	}


	// ********** equality **********

	/**
	 * Enforce object identity - do not allow objects to be equal unless
	 * they are the same object.
	 * Do NOT override this method - we rely on object identity extensively.
	 */
	@Override
	public final boolean equals(Object o) {
		return this == o;
	}

	/**
	 * Enforce object identity - do not allow objects to be equal unless
	 * they are the same object.
	 * Do NOT override this method - we rely on object identity extensively.
	 */
	@Override
	public final int hashCode() {
		return super.hashCode();
	}


	// ********** containment hierarchy (parent/children) **********

	/**
	 * INTRA-TREE API?
	 * Return the node's parent in the containment hierarchy.
	 * Most nodes must have a parent.
	 * @see #children()
	 */
	public Node getParent() {
		return this.parent;
	}

	/**
	 * Throw an IllegalArgumentException if the parent is not valid
	 * for the node.
	 * By default require a non-null parent. Override if other restrictions exist
	 * or the parent should be null.
	 * NB: Root node model implementations will need to override this method.
	 */
	protected void checkParent(Node parentNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("The parent node cannot be null"); //$NON-NLS-1$
		}
	}

	/**
	 * INTRA-TREE API?
	 * Return the node's children, which are also nodes.
	 * Do NOT override this method.
	 * Override #addChildrenTo(List).
	 * @see #getParent()
	 * @see #addChildrenTo(java.util.List)
	 */
	public final Iterator<Node> children() {
		List<Node> children = new ArrayList<Node>();
		this.addChildrenTo(children);
		return children.iterator();
	}

	/**
	 * Subclasses should override this method to add their children
	 * to the specified list.
	 * @see #children()
	 */
	protected void addChildrenTo(@SuppressWarnings("unused") List<Node> list) {
		// this class has no children, subclasses will...
	// when you override this method, don't forget to include:
	//	super.addChildrenTo(list);
	}

	/**
	 * INTRA-TREE API?
	 * Return the containment hierarchy's root node.
	 * Most nodes must have a root.
	 * @see #getParent()
	 * NB: Assume the root has no parent.
	 */
	public Node root() {
		Node p = this.parent;
		return (p == null) ? this : p.root();
	}

	/**
	 * Return whether the node is a descendant of the specified node.
	 * By definition, a node is a descendant of itself.
	 */
	public boolean isDescendantOf(Node node) {
		return (this == node) || this.parentIsDescendantOf(node);
	}

	protected boolean parentIsDescendantOf(Node node) {
		return (this.parent != null) && this.parent.isDescendantOf(node);
	}

	/**
	 * Return a collection holding all the node's "references", and all
	 * the node's descendants' "references". "References" are
	 * objects that are "referenced" by another object, as opposed
	 * to "owned" by another object.
	 */
	public Iterator<Node.Reference> branchReferences() {
		Collection<Node.Reference> branchReferences = new ArrayList<Node.Reference>(1000);		// start big
		this.addBranchReferencesTo(branchReferences);
		return branchReferences.iterator();
	}

	/**
	 * INTRA-TREE API
	 * Add the node's "references", and all the node's descendants'
	 * "references", to the specified collection. "References" are
	 * objects that are "referenced" by another object, as opposed
	 * to "owned" by another object.
	 * This method is of particular concern to Handles, since most
	 * (hopefully all) "references" are held by Handles.
	 * @see org.eclipse.jpt.common.utility.node.Node.Reference
	 * @see #children()
	 */
	public void addBranchReferencesTo(Collection<Node.Reference> branchReferences) {
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			child.addBranchReferencesTo(branchReferences);
		}
	}

	/**
	 * Return all the nodes in the object's branch of the tree,
	 * including the node itself. The nodes will probably returned
	 * in "depth-first" order.
	 * Only really used for testing and debugging.
	 */
	public Iterator<Node> allNodes() {
		Collection<Node> nodes = new ArrayList<Node>(1000);		// start big
		this.addAllNodesTo(nodes);
		return nodes.iterator();
	}

	/**
	 * INTRA-TREE API?
	 * Add all the nodes in the object's branch of the tree,
	 * including the node itself, to the specified collection.
	 * Only really used for testing and debugging.
	 */
	public void addAllNodesTo(Collection<Node> nodes) {
		nodes.add(this);
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			child.addAllNodesTo(nodes);
		}
	}


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
	public void nodeRemoved(Node node) {
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			child.nodeRemoved(node);
		}
	// when you override this method, don't forget to include:
	//	super.nodeRemoved(node);
	}

	/**
	 * convenience method
	 * return whether node1 is a descendant of node2;
	 * node1 can be null
	 */
	protected boolean nodeIsDescendantOf(Node node1, Node node2) {
		return (node1 != null) && node1.isDescendantOf(node2);
	}

	/**
	 * INTRA-TREE API
	 * This is a general notification that the specified node has been
	 * renamed. The node receiving this notification should mark its
	 * branch dirty if necessary (i.e. it references the renamed node
	 * or one of its descendants). This method is of particular concern
	 * to Handles.
	 * @see #isDescendantOf(Node)
	 */
	public void nodeRenamed(Node node) {
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			child.nodeRenamed(node);
		}
	// when you override this method, don't forget to include:
	//	super.nodeRenamed(node);
	}
	
	
	// ********** user comment **********

	/**
	 * Return the object's user comment.
	 */
	public final String comment() {
		return this.comment;
	}

	/**
	 * Set the object's user comment.
	 */
	public final void setComment(String comment) {
		Object old = this.comment;
		this.comment = comment;
		this.firePropertyChanged(COMMENT_PROPERTY, old, comment);
	}


	// ********** change support **********

	/**
	 * An aspect of the node has changed:
	 * 	- if it is a persistent aspect, mark the object dirty
	 * 	- if it is a significant aspect, validate the object
	 */
	protected void aspectChanged(String aspectName) {
		if (this.aspectIsPersistent(aspectName)) {
			// System.out.println(Thread.currentThread() + " dirty change: " + this + ": " + aspectName);
			this.markDirty();
		}
		if (this.aspectChangeRequiresValidation(aspectName)) {
			// System.out.println(Thread.currentThread() + " validation change: " + this + ": " + aspectName);
			this.validate();
		}
	}

	protected void validate() {
		this.getValidator().validate();
	}

	/**
	 * INTRA-TREE API
	 * Return a validator that will be invoked whenever a
	 * "validated" aspect of the node tree changes.
	 * Typically only the root node directly holds a validator.
	 * NB: Root node model implementations will need to override this method.
	 */
	public Node.Validator getValidator() {
		if (this.parent == null) {
			throw new IllegalStateException("This node should not be firing change events during its construction."); //$NON-NLS-1$
		}
		return this.parent.getValidator();
	}

	/**
	 * Set a validator that will be invoked whenever a
	 * "validated" aspect of the node tree changes.
	 * Typically only the root node directly holds a validator.
	 * NB: Root node model implementations will need to override this method.
	 */
	public void setValidator(Node.Validator validator) {
		if (this.parent == null) {
			throw new IllegalStateException("This root node should implement #setValidator(Node.Validator)."); //$NON-NLS-1$
		}
		throw new UnsupportedOperationException("Only root nodes implement #setValidator(Node.Validator)."); //$NON-NLS-1$
	}


	// ********** dirty flag support **********

	/**
	 * Return whether any persistent aspects of the object
	 * have changed since the object was last read or saved.
	 * This does NOT include changes to the object's descendants.
	 */
	public final boolean isDirty() {
		return this.dirty;
	}

	/**
	 * Return whether any persistent aspects of the object,
	 * or any of its descendants, have changed since the object and
	 * its descendants were last read or saved.
	 */
	public final boolean isDirtyBranch() {
		return this.dirtyBranch;
	}

	/**
	 * Return whether the object is unmodified
	 * since it was last read or saved.
	 * This does NOT include changes to the object's descendants.
	 */
	public final boolean isClean() {
		return ! this.dirty;
	}

	/**
	 * Return whether the object and all of its descendants
	 * are unmodified since the object and
	 * its descendants were last read or saved.
	 */
	public final boolean isCleanBranch() {
		return ! this.dirtyBranch;
	}

	/**
	 * Set the dirty branch flag setting. This is set to true
	 * when either the object or one of its descendants becomes dirty.
	 */
	private void setIsDirtyBranch(boolean dirtyBranch) {
		boolean old = this.dirtyBranch;
		this.dirtyBranch = dirtyBranch;
		this.firePropertyChanged(DIRTY_BRANCH_PROPERTY, old, dirtyBranch);
	}

	/**
	 * Mark the object as dirty and as a dirty branch.
	 * An object is marked dirty when either a "persistent" attribute
	 * has changed or its save location has changed.
	 */
	private void markDirty() {
		this.dirty = true;
		this.markBranchDirty();
	}

	/**
	 * INTRA-TREE API
	 * Mark the node and its parent as dirty branches.
	 * This message is propagated up the containment
	 * tree when a particular node becomes dirty.
	 */
	public void markBranchDirty() {
		// short-circuit any unnecessary propagation
		if (this.dirtyBranch) {
			// if this is already a dirty branch, the parent must be also
			return;
		}

		this.setIsDirtyBranch(true);
		this.markParentBranchDirty();
	}

	protected void markParentBranchDirty() {
		if (this.parent != null) {
			this.parent.markBranchDirty();
		}
	}

	/**
	 * Mark the object and all its descendants as dirty.
	 * This is used when the save location of some
	 * top-level object is changed and the entire
	 * containment tree must be marked dirty so it
	 * will be written out.
	 */
	public final void markEntireBranchDirty() {
		this.markDirty();
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			child.markEntireBranchDirty();
		}
	}

	/**
	 * Mark the object and all its descendants as clean.
	 * Then notify the object's parent that it (the parent)
	 * might now be a clean branch also.
	 * Typically used when the object has just been
	 * read in or written out.
	 */
	public final void markEntireBranchClean() {
		this.cascadeMarkEntireBranchClean();
		this.markParentBranchCleanIfPossible();
	}

	protected void markParentBranchCleanIfPossible() {
		if (this.parent != null) {
			this.parent.markBranchCleanIfPossible();
		}
	}

	/**
	 * INTRA-TREE API
	 * Mark the node and all its descendants as clean.
	 * Typically used when the node has just been
	 * read in or written out.
	 * This method is for internal use only; it is not for
	 * client use.
	 * Not the best of method names.... :-(
	 */
	public final void cascadeMarkEntireBranchClean() {
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			child.cascadeMarkEntireBranchClean();
		}
		this.dirty = false;
		this.setIsDirtyBranch(false);
	}

	/**
	 * INTRA-TREE API
	 * A child node's branch has been marked clean. If the node
	 * itself is clean and if all of its children are also clean, the
	 * node's branch can be marked clean. Then, if the node's
	 * branch is clean, the node will notify its parent that it might
	 * be clean also. This message is propagated up the containment
	 * tree when a particular node becomes clean.
	 */
	public final void markBranchCleanIfPossible() {
		// short-circuit any unnecessary propagation
		if (this.dirty) {
			// if the object is "locally" dirty, it is still a dirty branch
			return;
		}

		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			if (child.isDirtyBranch()) {
				return;
			}
		}

		this.setIsDirtyBranch(false);
		this.markParentBranchCleanIfPossible();
	}

	private boolean aspectIsPersistent(String aspectName) {
		return ! this.aspectIsTransient(aspectName);
	}

	private boolean aspectIsTransient(String aspectName) {
		return this.transientAspectNames().contains(aspectName);
	}

	/**
	 * Return a set of the object's transient aspect names.
	 * These are the aspects that, when they change, will NOT cause the
	 * object to be marked dirty.
	 * If you need instance-based calculation of your transient aspects,
	 * override this method. If class-based calculation is sufficient,
	 * override #addTransientAspectNamesTo(Set).
	 */
	protected final Set<String> transientAspectNames() {
		synchronized (transientAspectNameSets) {
			HashSet<String> transientAspectNames = transientAspectNameSets.get(this.getClass());
			if (transientAspectNames == null) {
				transientAspectNames = new HashSet<String>();
				this.addTransientAspectNamesTo(transientAspectNames);
				transientAspectNameSets.put(this.getClass(), transientAspectNames);
			}
			return transientAspectNames;
		}
	}

	/**
	 * Add the object's transient aspect names to the specified set.
	 * These are the aspects that, when they change, will NOT cause the
	 * object to be marked dirty.
	 * If class-based calculation of your transient aspects is sufficient,
	 * override this method. If you need instance-based calculation,
	 * override #transientAspectNames().
	 */
	protected void addTransientAspectNamesTo(Set<String> transientAspectNames) {
		transientAspectNames.add(DIRTY_BRANCH_PROPERTY);
		transientAspectNames.add(BRANCH_PROBLEMS_LIST);
		transientAspectNames.add(HAS_BRANCH_PROBLEMS_PROPERTY);
	// when you override this method, don't forget to include:
	//	super.addTransientAspectNamesTo(transientAspectNames);
	}

	/**
	 * Return the dirty nodes in the object's branch of the tree,
	 * including the node itself (if appropriate).
	 * Only really used for testing and debugging.
	 */
	public final Iterator<Node> allDirtyNodes() {
		return IteratorTools.filter(this.allNodes(), IS_DIRTY);
	}

	public static final Predicate<Node> IS_DIRTY = new IsDirty();
	public static class IsDirty
		extends PredicateAdapter<Node>
	{
		@Override
		public boolean evaluate(Node node) {
			return (node instanceof AbstractNode) && ((AbstractNode) node).isDirty();
		}
	}


	// ********** problems **********

	/**
	 * Return the node's problems.
	 * This does NOT include the problems of the node's descendants.
	 * @see #branchProblems()
	 */
	public final Iterator<Problem> problems() {
		return IteratorTools.clone(this.problems);	// removes are not allowed
	}

	/**
	 * Return the size of the node's problems.
	 * This does NOT include the problems of the node's descendants.
	 * @see #branchProblemsSize()
	 */
	public final int problemsSize() {
		return this.problems.size();
	}

	/**
	 * Return whether the node has problems
	 * This does NOT include the problems of the node's descendants.
	 * @see #hasBranchProblems()
	 */
	public final boolean hasProblems() {
		return ! this.problems.isEmpty();
	}

	/**
	 * Return all the node's problems along with all the
	 * node's descendants' problems.
	 */
	public final ListIterator<Problem> branchProblems() {
		return IteratorTools.clone(this.branchProblems);	// removes are not allowed
	}

	/**
	 * Return the size of all the node's problems along with all the
	 * node's descendants' problems.
	 */
	public final int branchProblemsSize() {
		return this.branchProblems.size();
	}

	/**
	 * Return whether the node or any of its descendants have problems.
	 */
	public final boolean hasBranchProblems() {
		return ! this.branchProblems.isEmpty();
	}

	public final boolean containsBranchProblem(Problem problem) {
		return this.branchProblems.contains(problem);
	}

	protected final Problem buildProblem(String messageKey, int messageType, Object... messageArguments) {
		return new DefaultNodeProblem(this, messageKey, messageType, messageArguments);
	}

	protected final Problem buildProblem(String messageKey, int messageType) {
		return this.buildProblem(messageKey, messageType, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Validate the node and all of its descendants,
	 * and update their sets of "branch" problems.
	 * If the node's "branch" problems have changed,
	 * notify the node's parent.
	 */
	public void validateBranch() {
		if (this.validateBranchInternal()) {
			// if our "branch" problems have changed, then
			// our parent must rebuild its "branch" problems also
			this.rebuildParentBranchProblems();
		}
	}

	protected void rebuildParentBranchProblems() {
		if (this.parent != null) {
			this.parent.rebuildBranchProblems();
		}
	}

	/**
	 * INTRA-TREE API
	 * Validate the node and all of its descendants,
	 * and update their sets of "branch" problems.
	 * Return true if the collection of "branch" problems has changed.
	 * This method is for internal use only; it is not for
	 * client use.
	 */
	public boolean validateBranchInternal() {
		// rebuild "branch" problems in children first
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			// ignore the return value because we are going to rebuild our "branch"
			// problems no matter what, to see if they have changed
			child.validateBranchInternal();
		}

		this.problems.clear();
		this.addProblemsTo(this.problems);

		return this.checkBranchProblems();
	}

	/**
	 * Check for any problems and add them to the specified list.
	 * This method should ONLY add problems for this particular node;
	 * it should NOT add problems for any of this node's descendants
	 * or ancestors. (Although there will be times when it is debatable
	 * as to which node a problem "belongs" to....)
	 * 
	 * NB: This method should NOT modify ANY part of the node's state!
	 * It is a READ-ONLY behavior. ONLY the list of current problems
	 * passed in to the method should be modified.
	 */
	protected void addProblemsTo(@SuppressWarnings("unused") List<Problem> currentProblems) {
		// The default is to do nothing.
		// When you override this method, don't forget to include:
	//	super.addProblemsTo(currentProblems);
	}

	/**
	 * Rebuild the "branch" problems and return whether they have
	 * changed.
	 * NB: The entire collection of "branch" problems must be re-calculated
	 * with EVERY "significant" change - we cannot keep it in sync via
	 * change notifications because if a descendant with problems is
	 * removed or replaced we will not receive notification that its
	 * problems were removed from our "branch" problems.
	 */
	private boolean checkBranchProblems() {
		Vector<Problem> oldBranchProblems = new Vector<Problem>(this.branchProblems);
		int oldSize = this.branchProblems.size();

		this.branchProblems.clear();
		this.branchProblems.addAll(this.problems);
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			child.addBranchProblemsTo(this.branchProblems);
		}

		// if the size has changed to or from zero, our virtual flag has changed
		int newSize = this.branchProblems.size();
		if ((oldSize == 0) && (newSize != 0)) {
			this.firePropertyChanged(HAS_BRANCH_PROBLEMS_PROPERTY, false, true);
		} else if ((oldSize != 0) && (newSize == 0)) {
			this.firePropertyChanged(HAS_BRANCH_PROBLEMS_PROPERTY, true, false);
		}

		if (oldBranchProblems.equals(this.branchProblems)) {
			return false;		// our "branch" problems did not change
		}
		// our "branch" problems changed
		this.fireListChanged(BRANCH_PROBLEMS_LIST, this.branchProblems);
		return true;
	}

	/**
	 * INTRA-TREE API
	 * Add all the problems of the node and all
	 * the problems of its descendants to the
	 * specified collection.
	 */
	public final void addBranchProblemsTo(List<Problem> list) {
		list.addAll(this.branchProblems);
	}

	/**
	 * INTRA-TREE API
	 * A child node's "branch" problems changed;
	 * therefore the node's "branch" problems have changed also and
	 * must be rebuilt.
	 */
	public final void rebuildBranchProblems() {
		if ( ! this.checkBranchProblems()) {
			throw new IllegalStateException("we should not get here unless our \"branch\" problems have changed"); //$NON-NLS-1$
		}
		this.rebuildParentBranchProblems();
	}

	/**
	 * Clear the node's "branch" problems and the "branch"
	 * problems of all of its descendants.
	 * If the node's "branch" problems have changed,
	 * notify the node's parent.
	 */
	public final void clearAllBranchProblems() {
		if (this.clearAllBranchProblemsInternal()) {
			// if our "branch" problems have changed, then
			// our parent must rebuild its "branch" problems also
			this.rebuildParentBranchProblems();
		}
	}

	/**
	 * INTRA-TREE API
	 * Clear the node's "branch" problems and the "branch"
	 * problems of all of its descendants.
	 * Return true if the collection of "branch" problems has changed.
	 * This method is for internal use only; it is not for
	 * client use.
	 */
	public final boolean clearAllBranchProblemsInternal() {
		if (this.branchProblems.isEmpty()) {
			return false;
		}
		for (Iterator<Node> stream = this.children(); stream.hasNext(); ) {
			Node child = stream.next();		// pull out the child to ease debugging
			// ignore the return value because we are going to clear our "branch"
			// problems no matter what
			child.clearAllBranchProblemsInternal();
		}
		this.problems.clear();
		this.branchProblems.clear();
		this.firePropertyChanged(HAS_BRANCH_PROBLEMS_PROPERTY, true, false);
		this.fireListChanged(BRANCH_PROBLEMS_LIST, this.branchProblems);
		return true;
	}

	/**
	 * Return whether a change to specified aspect requires a re-validation
	 * of the node's tree.
	 */
	private boolean aspectChangeRequiresValidation(String aspectName) {
		return ! this.aspectChangeDoesNotRequireValidation(aspectName);
	}

	private boolean aspectChangeDoesNotRequireValidation(String aspectName) {
		return this.nonValidatedAspectNames().contains(aspectName);
	}

	/**
	 * Return a set of the object's "non-validated" aspect names.
	 * These are the aspects that, when they change, will NOT cause the
	 * object (or its containing tree) to be validated, i.e. checked for problems.
	 * If you need instance-based calculation of your "non-validated" aspects,
	 * override this method. If class-based calculation is sufficient,
	 * override #addNonValidatedAspectNamesTo(Set).
	 */
	protected final Set<String> nonValidatedAspectNames() {
		synchronized (nonValidatedAspectNameSets) {
			HashSet<String> nonValidatedAspectNames = nonValidatedAspectNameSets.get(this.getClass());
			if (nonValidatedAspectNames == null) {
				nonValidatedAspectNames = new HashSet<String>();
				this.addNonValidatedAspectNamesTo(nonValidatedAspectNames);
				nonValidatedAspectNameSets.put(this.getClass(), nonValidatedAspectNames);
			}
			return nonValidatedAspectNames;
		}
	}

	/**
	 * Add the object's "non-validated" aspect names to the specified set.
	 * These are the aspects that, when they change, will NOT cause the
	 * object (or its containing tree) to be validated, i.e. checked for problems.
	 * If class-based calculation of your "non-validated" aspects is sufficient,
	 * override this method. If you need instance-based calculation,
	 * override #nonValidatedAspectNames().
	 */
	protected void addNonValidatedAspectNamesTo(Set<String> nonValidatedAspectNames) {
		nonValidatedAspectNames.add(COMMENT_PROPERTY);
		nonValidatedAspectNames.add(DIRTY_BRANCH_PROPERTY);
		nonValidatedAspectNames.add(BRANCH_PROBLEMS_LIST);
		nonValidatedAspectNames.add(HAS_BRANCH_PROBLEMS_PROPERTY);
	// when you override this method, don't forget to include:
	//	super.addNonValidatedAspectNamesTo(nonValidatedAspectNames);
	}


	// ********** display methods **********

	/**
	 * Return a developer-friendly String. If you want something useful for
	 * displaying in a user interface, use #displayString().
	 * If you want to give more information in your #toString(),
	 * override #toString(StringBuilder sb). 
	 * Whatever you add to that string buffer will show up between the parentheses.
	 * @see AbstractModel#toString(StringBuilder sb)
	 * @see #displayString()
	 */
	@Override
	public final String toString() {
		return super.toString();
	}
}
