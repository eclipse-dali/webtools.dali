/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragment;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

/**
 * binary package fragment root
 */
public final class BinaryPackageFragmentRoot
	extends RootBinaryNode
	implements JavaResourcePackageFragmentRoot
{
	/** JDT package fragment root */
	private final IPackageFragmentRoot packageFragmentRoot;

	/** package fragments in the JAR */
	private final Vector<JavaResourcePackageFragment> packageFragments = new Vector<JavaResourcePackageFragment>();


	// ********** construction/initialization **********

	public BinaryPackageFragmentRoot(IPackageFragmentRoot packageFragmentRoot, AnnotationProvider annotationProvider) {
		super(null, annotationProvider);  // the package fragment root is the root of its sub-tree
		this.packageFragmentRoot = packageFragmentRoot;
		this.packageFragments.addAll(this.buildPackageFragments());
	}

	private Collection<JavaResourcePackageFragment> buildPackageFragments() {
		IJavaElement[] jdtChildren = this.getJDTChildren();
		ArrayList<JavaResourcePackageFragment> result = new ArrayList<JavaResourcePackageFragment>(jdtChildren.length);
		for (IJavaElement child : jdtChildren) {
			result.add(new BinaryPackageFragment(this, (IPackageFragment) child));
		}
		return result;
	}


	// ********** overrides **********

	@Override
	public IFile getFile() {
		return (IFile) this.packageFragmentRoot.getResource();
	}

	@Override
	public void update() {
		super.update();
		this.updatePackageFragments();
	}

	// TODO
	private void updatePackageFragments() {
		throw new UnsupportedOperationException();
	}


	// ********** JavaResourceNode.Root implementation **********

	/**
	 * NB: we hold only annotated types
	 */
	public Iterable<JavaResourceAbstractType> getTypes() {
		return new CompositeIterable<JavaResourceAbstractType>(this.persistedTypesLists());
	}

	private Iterable<Iterable<JavaResourceAbstractType>> persistedTypesLists() {
		return new TransformationIterable<JavaResourcePackageFragment, Iterable<JavaResourceAbstractType>>(this.getPackageFragments()) {
			@Override
			protected Iterable<JavaResourceAbstractType> transform(JavaResourcePackageFragment fragment) {
				return fragment.getTypes();
			}
		};
	}


	// ********** JavaResourcePackageFragmentRoot implementation **********

	public Iterable<JavaResourcePackageFragment> getPackageFragments() {
		return new LiveCloneIterable<JavaResourcePackageFragment>(this.packageFragments);
	}

	public int getPackageFragmentsSize() {
		return this.packageFragments.size();
	}


	// ********** misc **********

	private IJavaElement[] getJDTChildren() {
		return JDTTools.getChildren(this.packageFragmentRoot);
	}

}
