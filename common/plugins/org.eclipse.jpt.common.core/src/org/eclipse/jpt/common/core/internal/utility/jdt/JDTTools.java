/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;

/**
 * Convenience methods for dealing with JDT core
 */
public final class JDTTools
{

	public static boolean packageFragmentRootIsSourceFolder(IPackageFragmentRoot pfr) {
		try {
			return packageFragmentRootIsSourceFolder_(pfr);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	protected static boolean packageFragmentRootIsSourceFolder_(IPackageFragmentRoot pfr) throws JavaModelException {
		return pfr.exists() && (pfr.getKind() == IPackageFragmentRoot.K_SOURCE);		
	}
	
	public static IJavaElement[] getJDTChildren(IPackageFragmentRoot root) {
		try {
			return getJDTChildren_(root);
		} catch (JavaModelException ex) {
			// ignore FNFE - which can happen when the workspace is out of synch with O/S file system
			if ( ! (ex.getCause() instanceof FileNotFoundException)) {
				JptCommonCorePlugin.log(ex);
			}
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}
	
	private static IJavaElement[] getJDTChildren_(IPackageFragmentRoot root) throws JavaModelException {
		return root.getChildren();
	}

	private static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];
	
	public static boolean typeNamedImplementsInterfaceNamed(IJavaProject javaProject, String typeName, String interfaceNamed) {
		try {
			return typeImplementsInterface(javaProject, javaProject.findType(typeName), javaProject.findType(interfaceNamed));
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}
	
	public static boolean typeImplementsInterface(IJavaProject javaProject, IType type, IType interfase) {
		try {
			if (type == null || interfase == null) {
				return false;
			}
			
			Iterable<String> resolvedSuperInterfaceNames = resolveSuperInterfaceNames(type);
			if (CollectionTools.contains(resolvedSuperInterfaceNames, interfase.getFullyQualifiedName())) {
				return true;
			}

			for (String interfaceName : resolveSuperInterfaceNames(type)) {
				IType superInterface = javaProject.findType(interfaceName);
				if (superInterface != null && typeImplementsInterface(javaProject, superInterface, interfase)) {
					return true;
				}
			}

			if (type.getSuperclassName() == null) {
				return false;
			}

			return typeImplementsInterface(javaProject, getJDTSuperclass(javaProject, type), interfase);
		}
		catch (JavaModelException ex) {
			return false;
		}

	}
	
	/**
	 * This is necessary because for whatever reason getSuperClassName() on IType returns unqualified names 
	 * when the type is java sourced.
	 * @param type
	 * @return String - resolved super class name or null
	 */
	private static String resolveSuperClassName(final IType type) {
		try {
			if (!type.isBinary()) {
				String superClassName = type.getSuperclassName();
				String[][] resolvedSuperClassName = type.resolveType(superClassName);
				return resolvedSuperClassName[0][0] + "." + resolvedSuperClassName[0][1]; //$NON-NLS-1$
			}
			return type.getSuperclassName();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return null;
		}
	}
	
	/**
	 * This is necessary because for whatever reason getSuperInterfaceNames() on IType returns unqualified names
	 *  when the type is java sourced.
	 * @param type
	 * @return Iterable<String> - resolved super interface names
	 */
	private static Iterable<String> resolveSuperInterfaceNames(final IType type) {
		try {
			if (!type.isBinary()) {
				ArrayList<String> resolvedSuperInterfaceNames = new ArrayList<String>();
				for (String superInterfaceName : new ArrayIterable<String>(type.getSuperInterfaceNames())) {
					String[][] resolvedTypeNames = type.resolveType(superInterfaceName);
					for (String[] resolvedTypeName : resolvedTypeNames) {
						resolvedSuperInterfaceNames.add(resolvedTypeName[0] + "." + resolvedTypeName[1]); //$NON-NLS-1$
					}
				}
				return resolvedSuperInterfaceNames;
			}
			return new ArrayIterable<String>(type.getSuperInterfaceNames());
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return EmptyIterable.instance();
		}
	}
	
	public static IType getJDTType(IJavaProject javaProject, String fullyQualifiedName) {
		try {
			return javaProject.findType(fullyQualifiedName);
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return null;
		}
	}
	
	public static IType getJDTSuperclass(IJavaProject javaProject, IType child) {
		 try {
			 return javaProject.findType(resolveSuperClassName(child));
		 }
		 catch (JavaModelException ex) {
			 JptCommonCorePlugin.log(ex);
			 return null;
		 }
	}
	
	public static Iterable<IType> getJDTSuperInterfaces(IJavaProject javaProject, IType child) {
		ArrayList<IType> superclassInterfaces = new ArrayList<IType>();
		try {
			Iterable<String> superInterfaceNameIterable = resolveSuperInterfaceNames(child);
			for (String superInterfaceName : superInterfaceNameIterable) {
				superclassInterfaces.add(javaProject.findType(superInterfaceName));
			}
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return EmptyIterable.instance();
		}
		
		return superclassInterfaces;
	}

	public static Iterable<IPackageFragmentRoot> getJavaSourceFolders(IJavaProject javaProject) {
		try {
			return new FilteringIterable<IPackageFragmentRoot>(
					getPackageFragmentRoots(javaProject),
					SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER);
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return EmptyIterable.instance();
		}
	}

	/**
	 * This returns the first package fragment root found on this project.
	 * I am not completely sure why, but the JavaTypeCompletionProcessor works with this.
	 */
	//TODO move this method to JpaProject once API freeze is over
	public static IPackageFragmentRoot getCodeCompletionContextRoot(IJavaProject javaProject) {
		try {
			return javaProject.getPackageFragmentRoots()[0];
		}
		catch (JavaModelException e) {
			JptCommonCorePlugin.log(e);
			return null;
		}
	}

	protected static Iterable<IPackageFragmentRoot> getJavaSourceFolders_(IJavaProject javaProject) throws JavaModelException {
		return new FilteringIterable<IPackageFragmentRoot>(
				getPackageFragmentRoots(javaProject),
				SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER
			);
	}

	protected static final Filter<IPackageFragmentRoot> SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER =
		new Filter<IPackageFragmentRoot>() {
			public boolean accept(IPackageFragmentRoot pfr) {
				try {
					return this.accept_(pfr);
				} catch (JavaModelException ex) {
					return false;
				}
			}
			private boolean accept_(IPackageFragmentRoot pfr) throws JavaModelException {
				return packageFragmentRootIsSourceFolder_(pfr);
			}
		};

	protected static Iterable<IPackageFragmentRoot> getPackageFragmentRoots(IJavaProject javaProject) throws JavaModelException {
		return new ArrayIterable<IPackageFragmentRoot>(javaProject.getPackageFragmentRoots());
	}

	/**
	 *This returns true if the given fully qualified type name is of the basic type.
	 *@param fullyQualifiedName - may include array brackets but not generic type arguments
	 */
	public static boolean typeIsBasic(IJavaProject javaProject, String fullyQualifiedName) {
		return typeIsBasic(javaProject, getJDTType(javaProject, fullyQualifiedName));
	}

	public static boolean typeIsBasic(IJavaProject javaProject, IType type) {
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
		if (typeImplementsInterface(javaProject, type, getJDTType(javaProject, java.io.Serializable.class.getName()))) {
			return true;
		}
		try {
			if (type.isEnum()) {
				return true;
			}
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;			
		}
		return false;	
	}

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

	protected static final String[] VALID_BASIC_ARRAY_ELEMENT_TYPE_NAMES = {
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

	protected static final String[] OTHER_VALID_BASIC_TYPE_NAMES = {
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
