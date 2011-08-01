/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_2;

import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.jpa2.Generic2_0JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.EclipseLink2_0JpaFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.EclipseLink2_0JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_1.EclipseLink2_1JpaAnnotationDefinitionProvider;

public class EclipseLink2_2JpaPlatformFactory
	extends EclipseLink2_0JpaPlatformFactory
{
	/**
	 * zero-argument constructor
	 */
	public EclipseLink2_2JpaPlatformFactory() {
		super();
	}
	
	
	@Override
	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			buildJpaVersion(),
			new EclipseLink2_0JpaFactory(), 
			this.buildAnnotationProvider(),
			EclipseLink2_2JpaPlatformProvider.instance(), 
			this.buildJpaVariation());
	}
	
	@Override
	protected JpaPlatform.Version buildJpaVersion() {
		return new EclipseLinkVersion(
				JptJpaEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_2_2,
				JpaFacet.VERSION_2_0.getVersionString());
	}
	
	@Override
	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(
			Generic2_0JpaAnnotationDefinitionProvider.instance(),
			EclipseLink2_1JpaAnnotationDefinitionProvider.instance());
	}
	
//	@Override
//	protected JpaPlatformVariation buildJpaVariation() {
//		return new JpaPlatformVariation() {
//				public Supported getTablePerConcreteClassInheritanceIsSupported() {
//					return Supported.YES;
//				}
//				
//				public boolean isJoinTableOverridable() {
//					return true;
//				}
//			};
//	}
}