/*******************************************************************************
 * Copyright (c) 2010, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.util.ArrayList;
import java.util.HashSet;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link IType} convenience methods.
 */
public final class TypeTools {

	// ********** inheritance hierarchy **********

	/**
	 * Climb the specified type's inheritance hierarchy looking for the specified interface.
	 */
	public static boolean isSubTypeOf(String typeName, String possibleSuperTypeName, IJavaProject javaProject) {
		try {
			return isSubTypeOf_(javaProject.findType(typeName), javaProject.findType(possibleSuperTypeName));
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	public static boolean isSubTypeOf(IType type, String possibleSuperTypeName) {
		try {
			return isSubTypeOf_(type, possibleSuperTypeName);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	private static boolean isSubTypeOf_(IType type, String possibleSuperTypeName) throws JavaModelException {
		return isSubTypeOf_(type, type.getJavaProject().findType(possibleSuperTypeName));
	}

	private static boolean isSubTypeOf_(IType type, IType possibleSuperType) throws JavaModelException {
		if (type == null) {
			return false;
		}
		HashSet<String> visitedTypeNames = new HashSet<>();
		visitedTypeNames.add(type.getFullyQualifiedName());
		return isSubTypeOf_(type, possibleSuperType, visitedTypeNames);
	}

	private static boolean isSubTypeOf_(IType type, IType possibleSuperType, HashSet<String> visitedTypeNames) throws JavaModelException {
		if ((type == null) || (possibleSuperType == null)) {
			return false;
		}

		// short cut if types are the same
		if (type.equals(possibleSuperType)) {
			return true;
		}

		IJavaProject javaProject = type.getJavaProject();
		// short cut if potential supertype is java.lang.Object
		if (javaProject.findType(Object.class.getName()).equals(possibleSuperType)) {
			return true;
		}

		String possibleSuperTypeName = possibleSuperType.getFullyQualifiedName();

		for (String superTypeName : getResolvedSuperTypeNames(type)) {
			if (superTypeName == null) {
				continue;
			}
			if (visitedTypeNames.contains(superTypeName)) {
				// no need to revisit any types;
				// and(!) stop any inheritance cycles (which is possible in source)
				continue;
			}
			visitedTypeNames.add(superTypeName);

			if (superTypeName.equals(possibleSuperTypeName)) {
				return true;
			}

			// recurse into super type
			if (isSubTypeOf_(javaProject.findType(superTypeName), possibleSuperType, visitedTypeNames)) {
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
		ArrayList<String> resolvedSuperTypeNames = new ArrayList<>();
		for (String superTypeName : nonResolvedSuperTypeNames) {
			resolvedSuperTypeNames.add(resolveType_(type, superTypeName));
		}
		return resolvedSuperTypeNames;
	}

	/**
	 * Return the (potentially) non-resolved names of the specified type's supertypes (class and interfaces).
	 * This is necessary because, for whatever reason, { @link IType#getSuperInterfaceNames()} and
	 * {@link IType#getSuperclassName()} return unqualified names when the type is from Java source.
	 */
	private static Iterable<String> getNonResolvedSuperTypeNames(IType type) throws JavaModelException {
		String superclassName = type.getSuperclassName();
		Iterable<String> superInterfaceNames = IterableTools.iterable(type.getSuperInterfaceNames());
		return (superclassName == null) ? superInterfaceNames : IterableTools.add(superInterfaceNames, superclassName);
	}

	/**
	 * Just grab the first candidate type.
	 */
	private static String resolveType_(IType type, String typeName) throws JavaModelException {
		String[][] resolvedClassNames = type.resolveType(typeName);
		if (resolvedClassNames == null) {
			return null;
		}
		String pkg = resolvedClassNames[0][0];
		String cls = resolvedClassNames[0][1];
		// check for default package
		return (pkg.length() == 0) ? cls : (pkg + '.' + cls);
	}

	public static String resolveType(IType type, String typeName) {
		try {
			return resolveType_(type, typeName);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return null;
		}
	}

	public static boolean isSerializable(IType type) {
		return isSubTypeOf(type, SERIALIZABLE_NAME);
	}

	public static boolean isSerializable(String typeName, IJavaProject javaProject) {
		return isSubTypeOf(typeName, SERIALIZABLE_NAME, javaProject);
	}

	public static final String SERIALIZABLE_NAME = java.io.Serializable.class.getName();


	// ********** public zero-arg ctor **********

	public static boolean hasPublicZeroArgConstructor(String typeName, IJavaProject javaProject) {
		if ((javaProject != null) && (typeName != null)) {
			IType type = JavaProjectTools.findType(javaProject, typeName);
			if (type != null) {
				return hasPublicZeroArgConstructor(type);
			}
		}
		return false;
	}

	public static boolean hasPublicZeroArgConstructor(IType type) {
		try {
			return hasPublicZeroArgConstructor_(type);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	private static boolean hasPublicZeroArgConstructor_(IType type) throws JavaModelException {
		boolean ctorDefined = false;
		for (IMethod method : type.getMethods()) {
			if (method.isConstructor()) {
				if ((method.getNumberOfParameters() == 0) && (Flags.isPublic(method.getFlags()))) {
					return true;
				}
				ctorDefined = true;
			}
		}
		// if there are no constructors defined in the source,
		// the compiler generates a default constructor
		return ! ctorDefined;
	}


	// ********** is class **********

	public static final Predicate<IType> IS_CLASS = new IsClass();
	public static class IsClass
		extends PredicateAdapter<IType>
	{
		@Override
		public boolean evaluate(IType type) {
			return isClass(type);
		}
	}

	public static boolean isClass(IType type) {
		try {
			return type.isClass();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}


	// ********** is interface **********

	public static final Predicate<IType> IS_INTERFACE = new IsInterface();
	public static class IsInterface
		extends PredicateAdapter<IType>
	{
		@Override
		public boolean evaluate(IType type) {
			return isInterface(type);
		}
	}

	public static boolean isInterface(IType type) {
		try {
			return type.isInterface();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}


	// ********** is enum **********

	public static final Predicate<IType> IS_ENUM = new IsEnum();
	public static class IsEnum
		extends PredicateAdapter<IType>
	{
		@Override
		public boolean evaluate(IType type) {
			return isEnum(type);
		}
	}

	public static boolean isEnum(IType type) {
		try {
			return type.isEnum();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}


	// ********** name **********

	public static IType[] getTypes(IType type) {
		try {
			return type.getTypes();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return EMPTY_ARRAY;
		}
	}
	public static final IType[] EMPTY_ARRAY = new IType[0];


	// ********** name **********

	public static final Transformer<IType, String> NAME_TRANSFORMER = new NameTransformer();
	public static class NameTransformer
		extends TransformerAdapter<IType, String>
	{
		@Override
		public String transform(IType type) {
			return type.getFullyQualifiedName();
		}
	}


	// ********** disabled constructor **********

	private TypeTools() {
		throw new UnsupportedOperationException();
	}
}
