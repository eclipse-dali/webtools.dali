/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.dom.ASTNode;
/**
 * For a give range finds the node covered and the node covering.
 * 
 * @since		2.1
 */
//copied from  org.eclipse.jdt.internal.corext.dom.NodeFinder
//deleted code to limit the number of classes I had to copy
public class NodeFinder extends GenericVisitor {

	/**
	 * A visitor that maps a selection to a given ASTNode. The result node is
	 * determined as follows:
	 * <ul>
	 *   <li>first the visitor tries to find a node with the exact start and length</li>
	 * 	 <li>if no such node exists than the node that encloses the range defined by
	 *       start and end is returned.</li>
	 *   <li>if the length is zero than also nodes are considered where the node's
	 *       start or end position matches <code>start</code>.</li>
	 *   <li>otherwise <code>null</code> is returned.</li>
	 * </ul>
	 * 
	 * @param root the root node from which the search starts
	 * @param start the start offset
	 * @param length the length
	 * 
	 * @return the result node
	 * 
	 * @since		2.1
	 */
	public static ASTNode perform(ASTNode root, int start, int length) {
		NodeFinder finder= new NodeFinder(start, length);
		root.accept(finder);
		ASTNode result= finder.getCoveredNode();
		if (result == null || result.getStartPosition() != start || result.getLength() != length) {
			return finder.getCoveringNode();
		}
		return result;
	}
	
	public static ASTNode perform(ASTNode root, ISourceRange range) {
		return perform(root, range.getOffset(), range.getLength());
	}
	
	private int fStart;
	private int fEnd;
	
	private ASTNode fCoveringNode;
	private ASTNode fCoveredNode;
	
	public NodeFinder(int offset, int length) {
		super(true); // include Javadoc tags
		fStart= offset;
		fEnd= offset + length;
	}

	@Override
	protected boolean visitNode(ASTNode node) {
		int nodeStart= node.getStartPosition();
		int nodeEnd= nodeStart + node.getLength();
		if (nodeEnd < fStart || fEnd < nodeStart) {
			return false;
		}
		if (nodeStart <= fStart && fEnd <= nodeEnd) {
			fCoveringNode= node;
		}
		if (fStart <= nodeStart && nodeEnd <= fEnd) {
			if (fCoveringNode == node) { // nodeStart == fStart && nodeEnd == fEnd
				fCoveredNode= node;
				return true; // look further for node with same length as parent
			} else if (fCoveredNode == null) { // no better found
				fCoveredNode= node;
			}
			return false;
		}
		return true;
	}

	/**
	 * Returns the covered node. If more than one nodes are covered by the selection, the
	 * returned node is first covered node found in a top-down traversal of the AST
	 * @return ASTNode
	 */
	public ASTNode getCoveredNode() {
		return fCoveredNode;
	}

	/**
	 * Returns the covering node. If more than one nodes are covering the selection, the
	 * returned node is last covering node found in a top-down traversal of the AST
	 * @return ASTNode
	 */
	public ASTNode getCoveringNode() {
		return fCoveringNode;
	}
	
}

