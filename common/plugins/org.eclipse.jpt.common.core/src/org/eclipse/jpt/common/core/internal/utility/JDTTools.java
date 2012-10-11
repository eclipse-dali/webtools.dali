/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IParent;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.filter.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.iterable.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;

/**
 * Convenience methods for dealing with JDT core
 */
public final class JDTTools {

	/**
	 * Wrap checked exception.
	 */
	public static boolean packageFragmentRootIsSourceFolder(IPackageFragmentRoot pfr) {
		try {
			return packageFragmentRootIsSourceFolder_(pfr);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	static boolean packageFragmentRootIsSourceFolder_(IPackageFragmentRoot pfr) throws JavaModelException {
		return pfr.exists() && (pfr.getKind() == IPackageFragmentRoot.K_SOURCE);
	}

	/**
	 * Wrap checked exception and check for out of sync workspace.
	 */
	public static IJavaElement[] getChildren(IParent parent) {
		try {
			return parent.getChildren();
		} catch (JavaModelException ex) {
			// ignore FNFE - which can happen when the workspace is out of sync with O/S file system
			if ( ! (ex.getCause() instanceof FileNotFoundException)) {
				JptCommonCorePlugin.instance().logError(ex);
			}
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}

	private static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];

	/**
	 * Climb the specified type's inheritance hierarchy looking for the specified interface.
	 */
	public static boolean typeIsSubType(IJavaProject javaProject, String potentialSubType, String potentialSuperType) {
		try {
			return typeIsSubType(javaProject, javaProject.findType(potentialSubType), javaProject.findType(potentialSuperType));
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	public static boolean typeIsSubType(IJavaProject javaProject, IType potentialSubType, String potentialSuperType) {
		try {
			return typeIsSubType_(javaProject, potentialSubType, potentialSuperType);
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	private static boolean typeIsSubType_(IJavaProject javaProject, IType potentialSubType, String potentialSuperType) throws JavaModelException {
		return typeIsSubType(javaProject, potentialSubType, javaProject.findType(potentialSuperType));
	}

	private static boolean typeIsSubType(IJavaProject javaProject, IType potentialSubType, IType potentialSuperType) throws JavaModelException {
		if ((potentialSubType == null) || (potentialSuperType == null)) {
			return false;
		}
		
		// short cut if types are the same
		if (potentialSubType.equals(potentialSuperType)) {
			return true;
		}
		
		// short cut if potential supertype is java.lang.Object
		if (javaProject.findType(Object.class.getName()).equals(potentialSuperType)) {
			return true;
		}
		
		String potentialSuperTypeName = potentialSuperType.getFullyQualifiedName();
		
		for (String superTypeName : getResolvedSuperTypeNames(potentialSubType)) {
			if (superTypeName.equals(potentialSuperTypeName)) {
				return true;
			}
			
			// recurse into super type
			if (typeIsSubType(javaProject, javaProject.findType(superTypeName), potentialSuperType)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Return the names of the specified type's supertypes (class and interfaces).
	 * This is necessary because, for whatever reason, { @link IType#getSuperInterfaceNames()}
	 * {@link IType#getSuperclassName()} return unqualified names when the type is from Java source.
	 */
	private static Iterable<String> getResolvedSuperTypeNames(IType type) throws JavaModelException {
		Iterable<String> nonResolvedSuperTypeNames = getNonResolvedSuperTypeNames(type);
		if (type.isBinary()) {
			// if type is binary, the types are already resolved
			return nonResolvedSuperTypeNames;
		}
		ArrayList<String> resolvedSuperTypeNames = new ArrayList<String>();
		for (String superTypeName : nonResolvedSuperTypeNames) {
			resolvedSuperTypeNames.add(resolveType(type, superTypeName));
		}
		return resolvedSuperTypeNames;
	}
	
	/**
	 * Return the (potentially) non-resolved names of the specified type's supertypes (class and interfaces).
	 * This is necessary because, for whatever reason, { @link IType#getSuperInterfaceNames()} and
	 * {@link IType#getSuperclassName()} return unqualified names when the type is from Java source.
	 */
	private static Iterable<String> getNonResolvedSuperTypeNames(IType type) throws JavaModelException {
		return new CompositeIterable<String>(
					new FilteringIterable<String>(
							new SingleElementIterable<String>(type.getSuperclassName()),
							NotNullFilter.<String>instance()),
					new ArrayIterable<String>(type.getSuperInterfaceNames()));
	}

	/**
	 * Just grab the first candidate type.
	 */
	private static String resolveType(IType type, String className) throws JavaModelException {
		String[][] resolvedClassNames = type.resolveType(className);
		if (resolvedClassNames == null) {
			return null;
		}
		String pkg = resolvedClassNames[0][0];
		String cls = resolvedClassNames[0][1];
		// check for default package
		return (pkg.length() == 0) ? cls : (pkg + '.' + cls);
	}

	public static IType findType(IJavaProject javaProject, String fullyQualifiedName) {
		try {
			return javaProject.findType(fullyQualifiedName);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return null;
		}
	}

	public static Iterable<IPackageFragmentRoot> getJavaSourceFolders(IJavaProject javaProject) {
		return new FilteringIterable<IPackageFragmentRoot>(
				getPackageFragmentRoots(javaProject),
				SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER
			);
	}

	private static final Filter<IPackageFragmentRoot> SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER =
		new Filter<IPackageFragmentRoot>() {
			public boolean accept(IPackageFragmentRoot pfr) {
				return packageFragmentRootIsSourceFolder(pfr);
			}
		};

	private static Iterable<IPackageFragmentRoot> getPackageFragmentRoots(IJavaProject javaProject) {
		try {
			return getPackageFragmentRoots_(javaProject);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return EmptyIterable.instance();
		}
	}

	private static Iterable<IPackageFragmentRoot> getPackageFragmentRoots_(IJavaProject javaProject) throws JavaModelException {
		return new ArrayIterable<IPackageFragmentRoot>(javaProject.getPackageFragmentRoots());
	}

	public static boolean typeIsEnum(IType type) {
		try {
			if (type.isEnum()) {
				return true;
			}
		}
		catch (JavaModelException e) {
			JptCommonCorePlugin.instance().logError(e);
		}
		return false;
	}

	public static boolean typeIsSerializable(IJavaProject javaProject, IType type) {
		return typeIsSubType(javaProject, type, SERIALIZABLE_CLASS_NAME);
	}

	public static final String SERIALIZABLE_CLASS_NAME = java.io.Serializable.class.getName();
	
	public static boolean classHasPublicZeroArgConstructor(IJavaProject javaProject, String className) {
		if (javaProject != null && className != null) {
			IType type = findType(javaProject, className);
			if (type != null) {
				return typeHasPublicZeroArgConstructor(type);
			}
		}
		return false;
	}

	public static boolean typeHasPublicZeroArgConstructor(IType type) {
		boolean hasDefinedConstructor = false;
		try {
			for (IMethod method : type.getMethods()) {
				if (method.isConstructor()) {
					if ((method.getNumberOfParameters() == 0) && (Flags.isPublic(method.getFlags()))) {
						return true;
					}
					hasDefinedConstructor = true;
				}
			}
			//When there's no defined constructor, the default constructor is in place.
			if (!hasDefinedConstructor) {
				return true;
			}
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
		}
		return false;
	}
}
