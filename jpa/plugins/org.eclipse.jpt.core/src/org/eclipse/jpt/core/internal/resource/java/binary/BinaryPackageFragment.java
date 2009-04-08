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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.resource.java.JavaResourceClassFile;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragment;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * binary package fragment
 */
final class BinaryPackageFragment
	extends BinaryNode
	implements JavaResourcePackageFragment
{
	/** JDT package fragment */
	private final IPackageFragment packageFragment;

	/** class files in the package fragment */
	private final Vector<JavaResourceClassFile> classFiles;


	// ********** construction/initialization **********

	BinaryPackageFragment(JavaResourcePackageFragmentRoot parent, IPackageFragment packageFragment) {
		super(parent);
		this.packageFragment = packageFragment;
		this.classFiles = this.buildClassFiles();
	}

	private Vector<JavaResourceClassFile> buildClassFiles() {
		Vector<JavaResourceClassFile> result = new Vector<JavaResourceClassFile>();
		for (IJavaElement child : this.getJDTChildren()) {
			IClassFile classFile = (IClassFile) child;
			IType type = this.findType(classFile);
			if (this.typeIsPersistable(type)) {  // we only hold persistable types
				result.add(new BinaryClassFile(this, classFile, type));
			}
		}
		return result;
	}

	/**
	 * Return the JDT type corresponding to the specified class file
	 * by searching the Java project's build path; as opposed to taking
	 * the type directly from the class file, since another type with the
	 * same name *may* precede the class file on the build path.
	 */
	private IType findType(IClassFile classFile) {
		IType type = classFile.getType();
		try {
			return type.getJavaProject().findType(type.getFullyQualifiedName(), (IProgressMonitor) null);
		} catch (JavaModelException ex) {
			return null;  // ignore exception?
		}
	}

	private boolean typeIsPersistable(IType type) {
		return (type != null)
				&& type.exists()
				&& JPTTools.typeIsPersistable(new JPTToolsAdapter(type));
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

	public Iterator<JavaResourcePersistentType> persistableTypes() {
		return new TransformationIterator<JavaResourceClassFile, JavaResourcePersistentType>(this.classFiles()) {
			@Override
			protected JavaResourcePersistentType transform(JavaResourceClassFile classFile) {
				return classFile.getPersistentType();  // we only hold persistable types
			}
		};
	}


	// ********** misc **********

	private IJavaElement[] getJDTChildren() {
		try {
			return this.packageFragment.getChildren();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}
	protected static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.packageFragment.getElementName());
	}


	// ********** JPT tools adapter **********

	/**
	 * JPTTools needs an adapter so it can work with either an IType
	 * or an ITypeBinding etc.
	 */
	protected class JPTToolsAdapter implements JPTTools.TypeAdapter {
		private final IType type;

		protected JPTToolsAdapter(IType type) {
			super();
			if (type == null) {
				throw new NullPointerException();
			}
			this.type = type;
		}

		public int getModifiers() {
			try {
				return this.type.getFlags();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return 0;
			}
		}

		public boolean isAnnotation() {
			try {
				return this.type.isAnnotation();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isAnonymous() {
			try {
				return this.type.isAnonymous();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isArray() {
			return false;  // ???
		}

		public boolean isEnum() {
			try {
				return this.type.isEnum();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isInterface() {
			try {
				return this.type.isInterface();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isLocal() {
			try {
				return this.type.isLocal();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isMember() {
			try {
				return this.type.isMember();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isPrimitive() {
			return false;  // ???
		}
	
	}

}
