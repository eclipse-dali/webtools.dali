/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.common.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.common.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.jpa.core.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePackageFragment;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;

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

	public BinaryPackageFragmentRoot(IPackageFragmentRoot packageFragmentRoot, JpaAnnotationProvider annotationProvider) {
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
	public Iterator<JavaResourcePersistentType> persistentTypes() {
		return new CompositeIterator<JavaResourcePersistentType>(this.persistedTypesLists());
	}

	private Iterator<Iterator<JavaResourcePersistentType>> persistedTypesLists() {
		return new TransformationIterator<JavaResourcePackageFragment, Iterator<JavaResourcePersistentType>>(this.packageFragments()) {
			@Override
			protected Iterator<JavaResourcePersistentType> transform(JavaResourcePackageFragment fragment) {
				return fragment.persistedTypes();
			}
		};
	}


	// ********** JavaResourcePackageFragmentRoot implementation **********

	public Iterator<JavaResourcePackageFragment> packageFragments() {
		return new CloneIterator<JavaResourcePackageFragment>(this.packageFragments);
	}

	public int packageFragmentsSize() {
		return this.packageFragments.size();
	}


	// ********** misc **********

	private IJavaElement[] getJDTChildren() {
		return JDTTools.getJDTChildren(this.packageFragmentRoot);
	}

}
