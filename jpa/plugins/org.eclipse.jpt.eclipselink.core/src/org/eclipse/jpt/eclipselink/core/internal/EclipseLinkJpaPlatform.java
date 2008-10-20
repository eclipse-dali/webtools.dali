/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import java.util.List;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaPlatform;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMappingProvider;

/**
 * EclipseLink platform
 */
public class EclipseLinkJpaPlatform
	extends GenericJpaPlatform
{
	public static final String ID = "org.eclipse.eclipselink.platform"; //$NON-NLS-1$


	public EclipseLinkJpaPlatform() {
		super();
	}

	@Override
	public String getId() {
		return EclipseLinkJpaPlatform.ID;
	}

	// ********* factory *********
	@Override
	protected JpaFactory buildJpaFactory() {
		return new EclipseLinkJpaFactory();
	}

	// ********* JPA files *********	
	@Override
	protected void addJpaFileProvidersTo(List<JpaFileProvider> providers) {
		super.addJpaFileProvidersTo(providers);
		providers.add(EclipseLinkOrmJpaFileProvider.instance());
	}

	// ********* java annotations *********	
	@Override
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new EclipseLinkJpaAnnotationProvider();
	}
	
	@Override
	protected void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers) {
		providers.add(EclipseLinkJavaOneToOneMappingProvider.instance());
		providers.add(EclipseLinkJavaOneToManyMappingProvider.instance());
		//add these before calling super, want to check for Basic last in case the reference object is Serializable
		super.addDefaultJavaAttributeMappingProvidersTo(providers);
	}

}
