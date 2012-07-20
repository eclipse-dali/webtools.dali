/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceClassFile;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragment;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

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
		Collection<String> annotationNames = CollectionTools.collection(this.getAnnotationProvider().getAnnotationNames());
		for (IJavaElement child : children) {
			IClassFile jdtClassFile = (IClassFile) child;
			IType jdtType = jdtClassFile.getType();
			if (typeIsRelevant(jdtType, annotationNames)) {
				result.add(new BinaryClassFile(this, jdtClassFile, jdtType));
			}
		}
		return result;
	}

	//we will limit to classes, interfaces, and enums. Annotation types will be ignored.
	static boolean typeIsRelevant(IType type, Collection<String> annotationNames) {
		try {
			return (type != null)
					&& type.exists()
					&& (type.isClass() || type.isInterface() || type.isEnum())
					&& typeHasAnnotations(type, annotationNames); // we only hold annotated types
		}
		catch (JavaModelException e) {
			return false;
		}
	}

	static boolean typeHasAnnotations(IType type, Collection<String> annotationNames) {
		IAnnotation[] annotations;
		try {
			annotations = type.getAnnotations();
		}
		catch (JavaModelException e) {
			return false;
		}
		for (IAnnotation annotation : annotations) {
			if (annotationNames.contains(annotation.getElementName())) {
				return true;
			}
		}
		return false;
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

	public ListIterable<JavaResourceClassFile> getClassFiles() {
		return new LiveCloneListIterable<JavaResourceClassFile>(this.classFiles);
	}

	public int getClassFilesSize() {
		return this.classFiles.size();
	}

	public Iterable<JavaResourceAbstractType> getTypes() {
		return new TransformationIterable<JavaResourceClassFile, JavaResourceAbstractType>(this.getClassFiles()) {
			@Override
			protected JavaResourceAbstractType transform(JavaResourceClassFile classFile) {
				return classFile.getType();  // we only hold annotated types
			}
		};
	}


	// ********** misc **********

	private IJavaElement[] getJDTChildren() {
		try {
			return this.packageFragment.getChildren();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}
	protected static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.packageFragment.getElementName());
	}

}
