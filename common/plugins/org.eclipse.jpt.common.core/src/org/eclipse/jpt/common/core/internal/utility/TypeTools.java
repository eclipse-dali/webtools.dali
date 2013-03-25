/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.util.ArrayList;
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

	/**
	 * Climb the specified type's inheritance hierarchy looking for the specified interface.
	 */
	public static boolean isSubType(String potentialSubTypeName, String potentialSuperTypeName, IJavaProject javaProject) {
		try {
			return isSubType(javaProject.findType(potentialSubTypeName), javaProject.findType(potentialSuperTypeName));
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	public static boolean isSubType(IType potentialSubType, String potentialSuperTypeName) {
		try {
			return isSubType_(potentialSubType, potentialSuperTypeName);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	private static boolean isSubType_(IType potentialSubType, String potentialSuperTypeName) throws JavaModelException {
		return isSubType(potentialSubType, potentialSubType.getJavaProject().findType(potentialSuperTypeName));
	}

	private static boolean isSubType(IType potentialSubType, IType potentialSuperType) throws JavaModelException {
		if ((potentialSubType == null) || (potentialSuperType == null)) {
			return false;
		}

		// short cut if types are the same
		if (potentialSubType.equals(potentialSuperType)) {
			return true;
		}

		IJavaProject javaProject = potentialSubType.getJavaProject();
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
			if (isSubType(javaProject.findType(superTypeName), potentialSuperType)) {
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
			resolvedSuperTypeNames.add(resolveType_(type, superTypeName));
		}
		return resolvedSuperTypeNames;
	}

	/**
	 * Return the (potentially) non-resolved names of the specified type's supertypes (class and interfaces).
	 * This is necessary because, for whatever reason, { @link IType#getSuperInterfaceNames()} and
	 * {@link IType#getSuperclassName()} return unqualified names when the type is from Java source.
	 */
	@SuppressWarnings("unchecked")
	private static Iterable<String> getNonResolvedSuperTypeNames(IType type) throws JavaModelException {
		return IterableTools.concatenate(
					IterableTools.removeNulls(IterableTools.singletonIterable(type.getSuperclassName())),
					IterableTools.iterable(type.getSuperInterfaceNames()));
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

	public static boolean isEnum(IType type) {
		try {
			return type.isEnum();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	public static boolean isSerializable(IType type) {
		return isSubType(type, SERIALIZABLE_NAME);
	}

	public static boolean isSerializable(String typeName, IJavaProject javaProject) {
		return isSubType(typeName, SERIALIZABLE_NAME, javaProject);
	}

	public static final String SERIALIZABLE_NAME = java.io.Serializable.class.getName();

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

	public static final Predicate<IType> IS_INTERFACE = new IsInterface();
	public static class IsInterface
		extends PredicateAdapter<IType>
	{
		@Override
		public boolean evaluate(IType type) {
			try {
				return type.isInterface();
			} catch (JavaModelException e) {
				JptCommonCorePlugin.instance().logError(e);
				return false;
			}
		}
	}

	public static final Predicate<IType> IS_CLASS = new IsClass();
	public static class IsClass
		extends PredicateAdapter<IType>
	{
		@Override
		public boolean evaluate(IType type) {
			try {
				return type.isClass();
			} catch (JavaModelException e) {
				JptCommonCorePlugin.instance().logError(e);
				return false;
			}
		}
	}

	public static final Predicate<IType> IS_ENUM = new IsEnum();
	public static class IsEnum
		extends PredicateAdapter<IType>
	{
		@Override
		public boolean evaluate(IType type) {
			try {
				return type.isEnum();
			} catch (JavaModelException e) {
				JptCommonCorePlugin.instance().logError(e);
				return false;
			}
		}
	}

	public static final Transformer<IType, String> NAME_TRANSFORMER = new NameTransformer();
	public static class NameTransformer
		extends TransformerAdapter<IType, String>
	{
		@Override
		public String transform(IType type) {
			return type.getFullyQualifiedName();
		}
	}

	private TypeTools() {
		throw new UnsupportedOperationException();
	}
}
