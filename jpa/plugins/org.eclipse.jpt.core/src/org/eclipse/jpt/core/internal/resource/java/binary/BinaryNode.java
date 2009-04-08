/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractJavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Binary convenience methods
 */
public abstract class BinaryNode
	extends AbstractJavaResourceNode
{

	// ********** construction **********
	
	protected BinaryNode(JavaResourceNode parent) {
		super(parent);
	}


	// ********** JavaResourceNode implementation **********
	
	@Override
	public JavaResourcePackageFragmentRoot getRoot() {
		return (JavaResourcePackageFragmentRoot) super.getRoot();
	}

	public void update() {
		// nothing by default
	}

	public JavaResourceCompilationUnit getJavaResourceCompilationUnit() {
		throw new UnsupportedOperationException();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public void initialize(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public void update(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
