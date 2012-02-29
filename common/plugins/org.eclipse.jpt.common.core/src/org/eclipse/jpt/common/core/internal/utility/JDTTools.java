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
import org.eclipse.jdt.core.Signature;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;

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
	 * Climb the specified type's inheritance hierarchy looking for the specified interface.
	 */
	public static boolean typeIsSubType(IJavaProject javaProject, String potentialSubType, String potentialSuperType) {
		try {
			return typeIsSubType(javaProject, javaProject.findType(potentialSubType), javaProject.findType(potentialSuperType));
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	public static boolean typeIsSubType(IJavaProject javaProject, IType potentialSubType, String potentialSuperType) {
		try {
			return typeIsSubType_(javaProject, potentialSubType, potentialSuperType);
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
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
	 * Return true if the given type named contains a method name as given with the given parameter types
	 */
	public static boolean typeNamedImplementsMethod(IJavaProject javaProject, String typeName, String methodName, String[] parameterTypeNames) {
		try {
			return typeImplementsMethod(javaProject.findType(typeName), methodName, parameterTypeNames);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	private static boolean typeImplementsMethod(IType type, String methodName, String[] parameterTypeNames) {
		if ((type == null) || methodName == null) {
			return false;
		}

		try {
			IMethod[] methods = type.getMethods();
			for (IMethod method : methods) {
				if (StringTools.stringsAreEqual(method.getElementName(), methodName)) {
					if (parameterTypeNames.length == 0 && method.getNumberOfParameters() == 0) {
						return true;
					} else if (parameterTypeNames.length == method.getNumberOfParameters()) {
						int index = 0;
						String[] parameters = method.getParameterTypes();
						String resolvedParameterTypeName = parameters[0];
						if (!type.isResolved()) {
							resolvedParameterTypeName = resolveType(type, Signature.getSignatureSimpleName(parameters[index]));
						}
						for (String parameterTypeName : parameterTypeNames) {
							if (!StringTools.stringsAreEqual(resolvedParameterTypeName, parameterTypeName)) {
								return false;
							}
							index++;
						}
						return true;
					}
				}
			}
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;			
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
		if (typeIsSubType_(javaProject, type, SERIALIZABLE_CLASS_NAME)) {
			return true;
		}
		if (type.isEnum()) {
			return true;
		}
		return false;	
	}

	public static final String SERIALIZABLE_CLASS_NAME = java.io.Serializable.class.getName();

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
			JptCommonCorePlugin.log(ex);
		}
		return false;
	}
}
