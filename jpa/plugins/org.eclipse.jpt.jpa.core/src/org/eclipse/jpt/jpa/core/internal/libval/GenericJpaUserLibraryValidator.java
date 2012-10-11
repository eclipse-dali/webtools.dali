/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;

public class GenericJpaUserLibraryValidator
	implements LibraryValidator
{
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JpaUserLibraryProviderInstallOperationConfig jpaConfig = (JpaUserLibraryProviderInstallOperationConfig) config;
		ArrayList<String> classNames = new ArrayList<String>(2);
		classNames.add(EntityAnnotation.ANNOTATION_NAME);
		if (config.getProjectFacetVersion().compareTo(JpaProject2_0.FACET_VERSION) >= 0) {
			classNames.add(ElementCollection2_0Annotation.ANNOTATION_NAME);
		}
		return LibraryValidatorTools.validateClasspathEntries(jpaConfig.resolve(), classNames);
	}
}
