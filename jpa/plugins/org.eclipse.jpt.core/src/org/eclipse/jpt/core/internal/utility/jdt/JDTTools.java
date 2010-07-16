/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.io.FileNotFoundException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;

/**
 * Convenience methods for dealing with JDT core
 */
public final class JDTTools
{

	public static boolean packageFragmentRootIsSourceFolder(IPackageFragmentRoot pfr) {
		try {
			return packageFragmentRootIsSourceFolder_(pfr);
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
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
				JptCorePlugin.log(ex);
			}
			return EMPTY_JAVA_ELEMENT_ARRAY;
		}
	}
	
	private static IJavaElement[] getJDTChildren_(IPackageFragmentRoot root) throws JavaModelException {
		return root.getChildren();
	}

	private static final IJavaElement[] EMPTY_JAVA_ELEMENT_ARRAY = new IJavaElement[0];

	//TODO move this method to JpaProject once API freeze is over
	public static Iterable<IPackageFragmentRoot> getJavaSourceFolders(IJavaProject javaProject) {
		try {
			return new FilteringIterable<IPackageFragmentRoot>(
					getPackageFragmentRoots(javaProject),
					SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER
				);
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
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
			JptCorePlugin.log(e);
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
}
