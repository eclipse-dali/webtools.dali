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

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * {@link IPackageFragmentRoot} convenience methods.
 */
public final class PackageFragmentRootTools {

	// ********** source folder **********

	public static final Predicate<IPackageFragmentRoot> IS_SOURCE_FOLDER = new IsSourceFolder();

	public static class IsSourceFolder
		extends PredicateAdapter<IPackageFragmentRoot>
	{
		@Override
		public boolean evaluate(IPackageFragmentRoot pfr) {
			return isSourceFolder(pfr);
		}
	}

	/**
	 * Wrap checked exception.
	 */
	public static boolean isSourceFolder(IPackageFragmentRoot pfr) {
		try {
			return isSourceFolder_(pfr);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	private static boolean isSourceFolder_(IPackageFragmentRoot pfr) throws JavaModelException {
		return pfr.exists() && (pfr.getKind() == IPackageFragmentRoot.K_SOURCE);
	}


	// ********** folder (source or class) **********

	public static final Predicate<IPackageFragmentRoot> IS_FOLDER = new IsFolder();

	public static class IsFolder
		extends PredicateAdapter<IPackageFragmentRoot>
	{
		@Override
		public boolean evaluate(IPackageFragmentRoot pfr) {
			return isFolder(pfr);
		}
	}

	/**
	 * Wrap checked exception.
	 */
	public static boolean isFolder(IPackageFragmentRoot pfr) {
		try {
			return isFolder_(pfr);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	private static boolean isFolder_(IPackageFragmentRoot pfr) throws JavaModelException {
		IResource resource = pfr.getUnderlyingResource();
		return (resource != null) && (resource.getType() == IResource.FOLDER);
	}


	// ********** package fragment **********

	/**
	 * Transform a package fragment root into the configured package fragment.
	 * @see IPackageFragmentRoot#getPackageFragment(String)
	 */
	public static class PackageFragmentTransformer
		extends TransformerAdapter<IPackageFragmentRoot, IPackageFragment>
	{
		private final String packageName;
		public PackageFragmentTransformer(String packageName) {
			super();
			this.packageName = packageName;
		}
		@Override
		public IPackageFragment transform(IPackageFragmentRoot pfr) {
			return pfr.getPackageFragment(this.packageName);
		}
	}


	// ********** disabled constructor **********

	private PackageFragmentRootTools() {
		throw new UnsupportedOperationException();
	}
}
