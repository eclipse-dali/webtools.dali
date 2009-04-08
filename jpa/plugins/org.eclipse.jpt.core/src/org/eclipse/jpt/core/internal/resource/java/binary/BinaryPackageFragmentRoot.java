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

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaResourceModelListener;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragment;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * binary package fragment root
 */
public final class BinaryPackageFragmentRoot
	extends BinaryNode
	implements JavaResourcePackageFragmentRoot
{
	/** JDT package fragment root */
	private final IPackageFragmentRoot packageFragmentRoot;

	/** pluggable annotation provider */
	private final JpaAnnotationProvider annotationProvider;

	/** listeners notified whenever the resource model changes */
	private final ListenerList<JpaResourceModelListener> resourceModelListenerList;

	/** package fragments in the JAR */
	private final Vector<JavaResourcePackageFragment> packageFragments;


	// ********** construction/initialization **********

	public BinaryPackageFragmentRoot(IPackageFragmentRoot packageFragmentRoot, JpaAnnotationProvider annotationProvider) {
		super(null);  // the package fragment root is the root of its sub-tree
		this.packageFragmentRoot = packageFragmentRoot;
		this.annotationProvider = annotationProvider;
		this.resourceModelListenerList = new ListenerList<JpaResourceModelListener>(JpaResourceModelListener.class);
		this.packageFragments = this.buildPackageFragments();
	}

	private Vector<JavaResourcePackageFragment> buildPackageFragments() {
		Vector<JavaResourcePackageFragment> result = new Vector<JavaResourcePackageFragment>();
		for (IJavaElement pf : this.getJDTChildren()) {
			result.add(new BinaryPackageFragment(this, (IPackageFragment) pf));
		}
		return result;
	}


	// ********** overrides **********

	@Override
	protected boolean requiresParent() {
		return false;
	}

	@Override
	public JavaResourcePackageFragmentRoot getRoot() {
		return this;
	}

	@Override
	public IFile getFile() {
		return (IFile) this.packageFragmentRoot.getResource();
	}

	@Override
	public JpaAnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
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

	public Iterator<JavaResourcePersistentType> persistableTypes() {
		return new CompositeIterator<JavaResourcePersistentType>(this.persistableTypeIterators());
	}

	private Iterator<Iterator<JavaResourcePersistentType>> persistableTypeIterators() {
		return new TransformationIterator<JavaResourcePackageFragment, Iterator<JavaResourcePersistentType>>(this.packageFragments()) {
			@Override
			protected Iterator<JavaResourcePersistentType> transform(JavaResourcePackageFragment pf) {
				return pf.persistableTypes();
			}
		};
	}

	public void resourceModelChanged() {
		for (JpaResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged();
		}
	}


	// ********** JpaResourceModel implementation **********

	public void addResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}


	// ********** JavaResourcePackageFragmentRoot implementation **********

	public ListIterator<JavaResourcePackageFragment> packageFragments() {
		return new CloneListIterator<JavaResourcePackageFragment>(this.packageFragments);
	}

	public int packageFragmentsSize() {
		return this.packageFragments.size();
	}


	// ********** misc **********

	private IJavaElement[] getJDTChildren() {
		try {
			return this.packageFragmentRoot.getChildren();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}
	protected static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getFile().getName());
	}

}
