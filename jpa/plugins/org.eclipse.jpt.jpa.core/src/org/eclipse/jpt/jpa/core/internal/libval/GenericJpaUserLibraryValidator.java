/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.libval;

import java.util.ArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.jpa.core.internal.libprov.JpaUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollectionAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.core.jpa2_2.JpaProject2_2;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;

public class GenericJpaUserLibraryValidator
	implements LibraryValidator
{
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JpaUserLibraryProviderInstallOperationConfig jpaConfig = (JpaUserLibraryProviderInstallOperationConfig) config;
		ArrayList<String> classNames = new ArrayList<String>(2);
		classNames.add(EntityAnnotation.ANNOTATION_NAME);
		if (config.getProjectFacetVersion().compareTo(JpaProject2_0.FACET_VERSION) >= 0) {
			classNames.add(ElementCollectionAnnotation2_0.ANNOTATION_NAME);
		}
		if (config.getProjectFacetVersion().compareTo(JpaProject2_1.FACET_VERSION) >= 0) {
			classNames.add("javax.persistence.Convert"); //$NON-NLS-1$
		}
		if (config.getProjectFacetVersion().compareTo(JpaProject2_2.FACET_VERSION) >= 0) {
			classNames.add("javax.persistence.Convert"); //$NON-NLS-1$ 		//TODO for 2.2
		}
		return LibraryValidatorTools.validateClasses(jpaConfig, classNames);
	}
}
