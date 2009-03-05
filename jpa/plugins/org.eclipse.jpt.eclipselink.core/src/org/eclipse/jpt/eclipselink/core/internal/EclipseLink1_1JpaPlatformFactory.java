/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformFactory;
import org.eclipse.jpt.core.JpaValidation;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.platform.GenericJpaPlatformProvider;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class EclipseLink1_1JpaPlatformFactory
	implements JpaPlatformFactory
{

	/**
	 * zero-argument constructor
	 */
	public EclipseLink1_1JpaPlatformFactory() {
		super();
	}
	
	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			buildJpaFactory(), 
			buildJpaAnnotationProvider(), 
			buildJpaValidation(),
			EclipseLinkJpaPlatformProvider.instance(), 
			//put eclipselink first because of the default java attribute mapping providers order,
			//maybe there is a better way to handle that order dependency
			GenericJpaPlatformProvider.instance(),
			EclipseLink1_1JpaPlatformProvider.instance());
	}
	
	protected JpaFactory buildJpaFactory() {
		return new EclipseLink1_1JpaFactory();
	}
	
	protected JpaAnnotationProvider buildJpaAnnotationProvider() {
		return new GenericJpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance(),
			EclipseLink1_1JpaAnnotationDefinitionProvider.instance());
	}
	
	protected JpaValidation buildJpaValidation() {
		return new JpaValidation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.YES;
			}
		};
	}
}
