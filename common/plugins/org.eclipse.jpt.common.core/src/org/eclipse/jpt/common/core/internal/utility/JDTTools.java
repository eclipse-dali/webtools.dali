/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IParent;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;

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
			JptCommonCorePlugin.log(ex);
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
				JptCommonCorePlugin.log(ex);
			}
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}

	private static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];

	/**
	 * Climb the specified type's inheritance hierarchy looking for the
	 * specified interface.
	 */
	public static boolean typeNamedImplementsInterfaceNamed(IJavaProject javaProject, String typeName, String interfaceName) {
		try {
			return typeImplementsInterface(javaProject, javaProject.findType(typeName), javaProject.findType(interfaceName));
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	private static boolean typeImplementsInterfaceNamed(IJavaProject javaProject, IType type, String interfaceName) throws JavaModelException {
		return typeImplementsInterface(javaProject, type, javaProject.findType(interfaceName));
	}

	private static boolean typeImplementsInterface(IJavaProject javaProject, IType type, IType interfase) throws JavaModelException {
		if ((type == null) || (interfase == null)) {
			return false;
		}

		String interfaceName = interfase.getFullyQualifiedName();
		for (String superInterfaceName : resolveSuperInterfaceNames(type)) {
			if (superInterfaceName.equals(interfaceName)) {
				return true;
			}
			// recurse into super interface
			if (typeImplementsInterface(javaProject, javaProject.findType(superInterfaceName), interfase)) {
				return true;
			}
		}

		if (type.getSuperclassName() == null) {
			return false;
		}
		// recurse into superclass
		return typeImplementsInterface(javaProject, javaProject.findType(resolveSuperclassName(type)), interfase);
	}

	/**
	 * Return the names of the specified type's super interfaces.
	 * This is necessary because, for whatever reason, {@link IType#getSuperInterfaceNames()}
	 * returns unqualified names when the type is from Java source.
	 */
	private static Iterable<String> resolveSuperInterfaceNames(IType type) throws JavaModelException {
		if (type.isBinary()) {
			return new ArrayIterable<String>(type.getSuperInterfaceNames());
		}
		ArrayList<String> resolvedSuperInterfaceNames = new ArrayList<String>();
		for (String superInterfaceName : type.getSuperInterfaceNames()) {
			resolvedSuperInterfaceNames.add(resolveType(type, superInterfaceName));
		}
		return resolvedSuperInterfaceNames;
	}

	/**
	 * Return the name of the specified type's superclass.
	 * This is necessary because, for whatever reason, {@link IType#getSuperclassName()}
	 * returns unqualified names when the type is from Java source.
	 */
	private static String resolveSuperclassName(IType type) throws JavaModelException {
		return type.isBinary() ?
				type.getSuperclassName() :
				resolveType(type, type.getSuperclassName());
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
			JptCommonCorePlugin.log(ex);
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
			JptCommonCorePlugin.log(ex);
			return EmptyIterable.instance();
		}
	}

	private static Iterable<IPackageFragmentRoot> getPackageFragmentRoots_(IJavaProject javaProject) throws JavaModelException {
		return new ArrayIterable<IPackageFragmentRoot>(javaProject.getPackageFragmentRoots());
	}

	/**
	 * Return whether the specified type is "basic".
	 * @param fullyQualifiedName may include array brackets but not generic type arguments
	 */
	public static boolean typeIsBasic(IJavaProject javaProject, String fullyQualifiedName) {
		try {
			return typeIsBasic(javaProject, javaProject.findType(fullyQualifiedName));
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	private static boolean typeIsBasic(IJavaProject javaProject, IType type) throws JavaModelException {
		if (type == null) {
			return false;
		}
		String fullyQualifiedName = type.getFullyQualifiedName();

		if (fullyQualifiedName == null) {
			return false;
		}

		int arrayDepth = ReflectionTools.getArrayDepthForTypeDeclaration(fullyQualifiedName);
		if (arrayDepth > 1) {
			return false;  // multi-dimensional arrays are not supported
		}

		if (arrayDepth == 1) {
			String elementTypeName = ReflectionTools.getElementTypeNameForTypeDeclaration(fullyQualifiedName, 1);
			return elementTypeIsValidForBasicArray(elementTypeName);
		}

		// arrayDepth == 0
		if (ClassName.isVariablePrimitive(fullyQualifiedName)) {
			return true;  // any primitive but 'void'
		}
		if (ClassName.isVariablePrimitiveWrapper(fullyQualifiedName)) {
			return true;  // any primitive wrapper but 'java.lang.Void'
		}
		if (typeIsOtherValidBasicType(fullyQualifiedName)) {
			return true;
		}
		if (typeImplementsInterfaceNamed(javaProject, type, SERIALIZABLE_CLASS_NAME)) {
			return true;
		}
		if (type.isEnum()) {
			return true;
		}
		return false;	
	}

	private static final String SERIALIZABLE_CLASS_NAME = java.io.Serializable.class.getName();

	/**
	 * Return whether the specified type is a valid element type for
	 * a one-dimensional array that can default to a basic mapping:<ul>
	 * <li><code>byte</code>
	 * <li><code>java.lang.Byte</code>
	 * <li><code>char</code>
	 * <li><code>java.lang.Character</code>
	 * </ul>
	 */
	public static boolean elementTypeIsValidForBasicArray(String elementTypeName) {
		return ArrayTools.contains(VALID_BASIC_ARRAY_ELEMENT_TYPE_NAMES, elementTypeName);
	}

	private static final String[] VALID_BASIC_ARRAY_ELEMENT_TYPE_NAMES = {
		byte.class.getName(),
		char.class.getName(),
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName()
	};

	/**
	 * Return whether the specified type is among the various "other" types
	 * that can default to a basic mapping.
	 */
	public static boolean typeIsOtherValidBasicType(String typeName) {
		return ArrayTools.contains(OTHER_VALID_BASIC_TYPE_NAMES, typeName);
	}

	private static final String[] OTHER_VALID_BASIC_TYPE_NAMES = {
		java.lang.String.class.getName(),
		java.math.BigInteger.class.getName(),
		java.math.BigDecimal.class.getName(),
		java.util.Date.class.getName(),
		java.util.Calendar.class.getName(),
		java.sql.Date.class.getName(),
		java.sql.Time.class.getName(),
		java.sql.Timestamp.class.getName(),
	};
}
