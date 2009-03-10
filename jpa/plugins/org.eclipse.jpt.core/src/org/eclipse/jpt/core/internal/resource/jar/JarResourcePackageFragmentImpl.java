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

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.resource.jar.JarResourceClassFile;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragment;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * JAR package fragment
 */
public class JarResourcePackageFragmentImpl
	extends AbstractJarResourceNode
	implements JarResourcePackageFragment
{
	/** JDT package fragment */
	private final IPackageFragment packageFragment;

	/** class files in the package fragment */
	private final Vector<JarResourceClassFile> classFiles;


	// ********** construction/initialization **********

	public JarResourcePackageFragmentImpl(JarResourcePackageFragmentRoot parent, IPackageFragment packageFragment) {
		super(parent);
		this.packageFragment = packageFragment;
		this.classFiles = this.buildClassFiles();
	}

	protected Vector<JarResourceClassFile> buildClassFiles() {
		Vector<JarResourceClassFile> result = new Vector<JarResourceClassFile>();
		for (IJavaElement child : this.getJDTChildren()) {
			IClassFile classFile = (IClassFile) child;
			IType type = classFile.getType();
			if (JPTTools.typeIsPersistable(new JPTToolsAdapter(type))) {  // we only hold persistable types
				result.add(new JarResourceClassFileImpl(this, classFile));
			}
		}
		return result;
	}

	protected IJavaElement[] getJDTChildren() {
		try {
			return this.packageFragment.getChildren();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}
	protected static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];


	// ********** JarResourceNode implementation **********

	@Override
	public void update() {
		super.update();
		// TODO
	}


	// ********** JarResourcePackageFragment implementation **********

	public IPackageFragment getPackageFragment() {
		return this.packageFragment;
	}

	public ListIterator<JarResourceClassFile> classFiles() {
		return new CloneListIterator<JarResourceClassFile>(this.classFiles);
	}

	public int classFilesSize() {
		return this.classFiles.size();
	}

	public Iterator<JavaResourcePersistentType> persistableTypes() {
		return new TransformationIterator<JarResourceClassFile, JavaResourcePersistentType>(this.classFiles()) {
			@Override
			protected JavaResourcePersistentType transform(JarResourceClassFile classFile) {
				return classFile.getPersistentType();  // we only hold persistable types
			}
		};
	}


	// ********** misc **********

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
