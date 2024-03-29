/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.libprov;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.jst.common.project.facet.core.libprov.LegacyLibraryProviderDetector;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderFramework;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;

public class LegacyJpaLibraryProviderDetector
	extends LegacyLibraryProviderDetector
{
	public static final String LEGACY_JPA_LIBRARY_PROVIDER_ID
		= "jpa-legacy-library-provider"; //$NON-NLS-1$
	
	
	@Override
	public ILibraryProvider detect(
			final IProject project, final IProjectFacet facet) {
		if (facet.equals(JpaProject.FACET)) {
			return LibraryProviderFramework.getProvider(LEGACY_JPA_LIBRARY_PROVIDER_ID);
		}
		return null;
	}
}
