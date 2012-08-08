/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceClassFile;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePackageFragment;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;

/**
 * binary package fragment
 */
final class BinaryPackageFragment
	extends BinaryNode
	implements JavaResourcePackageFragment
{
	/** JDT package fragment */
	private final IPackageFragment packageFragment;

	/**
	 * class files in the package fragment;
	 * we only hold class files/types that are actually annotated;
	 * if the unannotated types are needed (e.g. for orm.xml or an
	 * inheritance tree) they can be discovered on the classpath as needed
	 */
	private final Vector<JavaResourceClassFile> classFiles = new Vector<JavaResourceClassFile>();


	// ********** construction/initialization **********

	BinaryPackageFragment(JavaResourcePackageFragmentRoot parent, IPackageFragment packageFragment) {
		super(parent);
		this.packageFragment = packageFragment;
		this.classFiles.addAll(this.buildClassFiles());
	}

	private Collection<JavaResourceClassFile> buildClassFiles() {
		IJavaElement[] children = this.getJDTChildren();
		ArrayList<JavaResourceClassFile> result = new ArrayList<JavaResourceClassFile>(children.length);
		for (IJavaElement child : children) {
			IClassFile jdtClassFile = (IClassFile) child;
			IType jdtType = jdtClassFile.getType();
			if (typeIsRelevant(jdtType)) {
				JavaResourceClassFile classFile = new BinaryClassFile(this, jdtClassFile, jdtType);
				result.add(classFile);
			}
		}
		return result;
	}

	static boolean typeIsRelevant(IType type) {
		if (BinaryPersistentType.typeIsPersistable(type)) {
			return typeHasAnyAnnotations(type); // we only hold annotated types
		}
		return false;
	}

	static boolean typeHasAnyAnnotations(IType type) {
		try {
			return (type.getAnnotations().length > 0);
		}
		catch (JavaModelException e) {
			return false;
		}
	}

	// ********** JarResourceNode implementation **********

	@Override
	public void update() {
		super.update();
		this.updateClassFiles();
	}

	// TODO
	private void updateClassFiles() {
		throw new UnsupportedOperationException();
	}


	// ********** JavaResourcePackageFragment implementation **********

	public ListIterator<JavaResourceClassFile> classFiles() {
		return new CloneListIterator<JavaResourceClassFile>(this.classFiles);
	}

	public int classFilesSize() {
		return this.classFiles.size();
	}

	public Iterator<JavaResourcePersistentType> persistedTypes() {
		return new TransformationIterator<JavaResourceClassFile, JavaResourcePersistentType>(this.classFiles()) {
			@Override
			protected JavaResourcePersistentType transform(JavaResourceClassFile classFile) {
				return classFile.getPersistentType();  // we only hold annotated types
			}
		};
	}


	// ********** misc **********

	private IJavaElement[] getJDTChildren() {
		try {
			return this.packageFragment.getChildren();
		} catch (JavaModelException ex) {
			JptJpaCorePlugin.log(ex);
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}
	protected static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.packageFragment.getElementName());
	}

}
