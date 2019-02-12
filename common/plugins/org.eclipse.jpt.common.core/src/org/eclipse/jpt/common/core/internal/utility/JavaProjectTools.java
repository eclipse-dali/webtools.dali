/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;

/**
 * {@link IJavaProject} convenience methods.
 */
public final class JavaProjectTools {

	public static IType findType(IJavaProject javaProject, String fullyQualifiedName) {
		try {
			return javaProject.findType(fullyQualifiedName);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return null;
		}
	}
	
	/**
	 * Return the specified Java project's <em>existing</em>
	 * {@link IPackageFragment}s with the specified name.
	 */
	public static Iterable<IPackageFragment> getPackageFragments(IJavaProject javaProject, String packageName) {
		return IterableTools.filter(
				IterableTools.transform(
						getPackageFragmentRoots(javaProject),
						new PackageFragmentRootTools.PackageFragmentTransformer(packageName)),
				JavaElementTools.EXISTS);
	}

	public static Iterable<IPackageFragmentRoot> getSourceFolders(IJavaProject javaProject) {
		return IterableTools.filter(
				getPackageFragmentRoots(javaProject),
				PackageFragmentRootTools.IS_SOURCE_FOLDER
			);
	}

	private static Iterable<IPackageFragmentRoot> getPackageFragmentRoots(IJavaProject javaProject) {
		try {
			return getPackageFragmentRoots_(javaProject);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return EmptyIterable.instance();
		}
	}

	private static Iterable<IPackageFragmentRoot> getPackageFragmentRoots_(IJavaProject javaProject) throws JavaModelException {
		return IterableTools.iterable(javaProject.getPackageFragmentRoots());
	}


	public static class ResourceIsOnClasspath
		extends CriterionPredicate<IResource, IJavaProject>
	{
		public ResourceIsOnClasspath(IJavaProject javaProject) {
			super(javaProject);
		}
		public boolean evaluate(IResource resource) {
			return this.criterion.isOnClasspath(resource);
		}
	}

	public static class JavaElementIsOnClasspath
		extends CriterionPredicate<IJavaElement, IJavaProject>
	{
		public JavaElementIsOnClasspath(IJavaProject javaProject) {
			super(javaProject);
		}
		public boolean evaluate(IJavaElement javaElement) {
			return this.criterion.isOnClasspath(javaElement);
		}
	}

	/**
	 * Return the names of the specified Java project's classes, sorted.
	 */
	public static Iterable<String> getJavaClassNames(IJavaProject javaProject) {
		return IterableTools.sort(getClassNames(javaProject));
	}

	/**
	 * Return the names of the specified Java project's classes.
	 */
	public static Iterable<String> getClassNames(IJavaProject javaProject) {
		return IterableTools.transform(getClasses(javaProject), TypeTools.NAME_TRANSFORMER);
	}

	/**
	 * Return the specified Java project's classes.
	 */
	public static Iterable<IType> getClasses(IJavaProject javaProject) {
		return IterableTools.filter(getTypes(javaProject), TypeTools.IS_CLASS);
	}

	/**
	 * Return the names of the specified Java project's interfaces, sorted.
	 */
	public static Iterable<String> getSortedInterfaceNames(IJavaProject javaProject) {
		return IterableTools.sort(getInterfaceNames(javaProject));
	}

	/**
	 * Return the names of the specified Java project's interfaces.
	 */
	public static Iterable<String> getInterfaceNames(IJavaProject javaProject) {
		return IterableTools.transform(getInterfaces(javaProject), TypeTools.NAME_TRANSFORMER);
	}

	/**
	 * Return the specified Java project's interfaces.
	 */
	public static Iterable<IType> getInterfaces(IJavaProject javaProject) {
		return IterableTools.filter(getTypes(javaProject), TypeTools.IS_INTERFACE);
	}

	/**
	 * Return the names of the specified Java project's enums, sorted.
	 */
	public static Iterable<String> getSortedEnumNames(IJavaProject javaProject) {
		return IterableTools.sort(getEnumNames(javaProject));
	}

	/**
	 * Return the names of the specified Java project's enums.
	 */
	public static Iterable<String> getEnumNames(IJavaProject javaProject) {
		return IterableTools.transform(getEnums(javaProject), TypeTools.NAME_TRANSFORMER);
	}

	/**
	 * Return the specified Java project's enums.
	 */
	public static Iterable<IType> getEnums(IJavaProject javaProject) {
		return IterableTools.filter(getTypes(javaProject), TypeTools.IS_ENUM);
	}

	/**
	 * Return <em>all</em> the specified Java project's types
	 * (classes, interfaces, and enums).
	 */
	public static Iterable<IType> getTypes(IJavaProject javaProject) {
		try {
			return getTypes_(javaProject);
		} catch (JavaModelException e) {
			JptCommonCorePlugin.instance().logError(e);
			return IterableTools.emptyIterable();
		}
	}

	private static Iterable<IType> getTypes_(IJavaProject javaProject) throws JavaModelException {
		List<IType> typesList = new ArrayList<IType>();
		IPackageFragmentRoot[] pkgRoots = javaProject.getAllPackageFragmentRoots();
		for (IPackageFragmentRoot root : pkgRoots) {
				IJavaElement[] jElements = root.getChildren();
				for (IJavaElement jElement : jElements) {
					if (jElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
						ICompilationUnit[] units = ((IPackageFragment) jElement).getCompilationUnits();
						for (ICompilationUnit unit : units) {
							CollectionTools.addAll(typesList, unit.getTypes());
						}
					}
				}
		}
		return typesList;
	}

	private JavaProjectTools() {
		throw new UnsupportedOperationException();
	}
}
