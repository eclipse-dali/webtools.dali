/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_1;

import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkVersion;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.EclipseLink2_0JpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.EclipseLink2_0JpaPlatformFactory;

public class EclipseLink2_1JpaPlatformFactory
	extends EclipseLink2_0JpaPlatformFactory
{
	/**
	 * zero-argument constructor
	 */
	public EclipseLink2_1JpaPlatformFactory() {
		super();
	}
	
	
	@Override
	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			buildJpaVersion(),
			new EclipseLink2_0JpaFactory(), 
			this.buildJpaAnnotationProvider(),
			EclipseLink2_1JpaPlatformProvider.instance(), 
			this.buildJpaVariation());
	}
	
	@Override
	protected JpaPlatform.Version buildJpaVersion() {
		return new EclipseLinkVersion(
				JptEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_2_1,
				JptCorePlugin.JPA_FACET_VERSION_2_0);
	}
	
//	@Override
//	protected JpaAnnotationProvider buildJpaAnnotationProvider() {
//		return new GenericJpaAnnotationProvider(
//			Generic2_0JpaAnnotationDefinitionProvider.instance(),
//			EclipseLinkJpaAnnotationDefinitionProvider.instance());
//	}
	
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