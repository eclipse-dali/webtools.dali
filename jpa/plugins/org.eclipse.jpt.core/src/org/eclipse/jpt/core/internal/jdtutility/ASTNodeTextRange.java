/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Straightforward implementation of ITextRange that adapts an ASTNode.
 */
public class ASTNodeTextRange implements ITextRange {
	private final ASTNode astNode;

	public ASTNodeTextRange(ASTNode astNode) {
		super();
		this.astNode = astNode;
	}

	public int getOffset() {
		return this.astNode.getStartPosition();
	}

	public int getLength() {
		return this.astNode.getLength();
	}

	public int getLineNumber() {
		return ((CompilationUnit) this.astNode.getRoot()).getLineNumber(this.getOffset());
	}

	public boolean includes(int index) {
		return (this.getOffset() <= index) && (index <= this.end());
	}

	private int end() {
		return this.getOffset() + this.getLength() - 1;
	}

	@Override
	public String toString() {
		String start = String.valueOf(this.getOffset());
		String end = String.valueOf(this.getOffset() + this.getLength() - 1);
		return StringTools.buildToStringFor(this, start + ", " + end);
	}

}
