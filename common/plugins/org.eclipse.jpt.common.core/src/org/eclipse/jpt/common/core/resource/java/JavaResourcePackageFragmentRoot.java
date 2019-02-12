/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;


/**
 * Java package fragement root (i.e. a claspath entry: either a directory or
 * a <code>.jar</code>).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JavaResourcePackageFragmentRoot
	extends JavaResourceModel.Root
{
	/**
	 * The content type for Java archives (<code>.jar</code>).
	 */
	IContentType JAR_CONTENT_TYPE = JptCommonCorePlugin.instance().getContentType("jar"); //$NON-NLS-1$

	/**
	 * Return the package fragment root's package fragments.
	 */
	Iterable<JavaResourcePackageFragment> getPackageFragments();
		String PACKAGE_FRAGMENTS_COLLECTION = "packageFragments"; //$NON-NLS-1$

	/**
	 * Return the size of the package fragment root's package fragments.
	 */
	int getPackageFragmentsSize();
}
