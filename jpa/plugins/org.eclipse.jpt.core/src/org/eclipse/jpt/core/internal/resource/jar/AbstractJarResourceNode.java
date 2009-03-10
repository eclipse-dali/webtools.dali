/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.jar;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractJavaResourceNode;
import org.eclipse.jpt.core.resource.jar.JarResourceNode;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragmentRoot;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * resource containment hierarchy
 */
public abstract class AbstractJarResourceNode
	extends AbstractJavaResourceNode
	implements JarResourceNode
{

	// ********** construction **********
	
	protected AbstractJarResourceNode(JarResourceNode parent) {
		super(parent);
	}


	// ********** JarResourceNode implementation **********
	
	@Override
	public JarResourcePackageFragmentRoot getRoot() {
		return (JarResourcePackageFragmentRoot) super.getRoot();
	}

	public void update() {
		// nothing by default
	}

	// TODO remove... ======================
	public TextRange getTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	public void update(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	public void initialize(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
