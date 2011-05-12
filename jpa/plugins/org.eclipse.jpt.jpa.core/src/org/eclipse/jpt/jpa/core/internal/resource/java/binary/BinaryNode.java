/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.AbstractJavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;

/**
 * Binary convenience methods
 */
// TODO hopefully this class can go away with some sort of refactoring of the
// source and binary hierarchies...
public abstract class BinaryNode
	extends AbstractJavaResourceNode
{
	// ********** construction **********
	
	protected BinaryNode(JavaResourceNode parent) {
		super(parent);
	}


	// ********** JavaResourceNode implementation **********

	@Override
	public IFile getFile() {
		return null;  // only BinaryPackageFragmentRoot has a file...
	}

	public void update() {
		// nothing by default
	}

	public JavaResourceCompilationUnit getJavaResourceCompilationUnit() {
		throw new UnsupportedOperationException();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public void initialize(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
