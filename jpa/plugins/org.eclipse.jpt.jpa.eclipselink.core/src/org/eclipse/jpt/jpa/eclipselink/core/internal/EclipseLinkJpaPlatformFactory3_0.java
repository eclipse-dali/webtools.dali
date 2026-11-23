/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.GenericJpaAnnotationDefinitionProvider3_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.persistence.jpa.jpql.parser.EclipseLinkJPQLGrammar2_6;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * EclipseLink 3.0 platform - the first EclipseLink release on jakarta.persistence.
 * <p>
 * Implements Jakarta Persistence 3.0. EclipseLink's own annotations stay in
 * {@code org.eclipse.persistence.annotations} across the Jakarta move, so only
 * the Jakarta Persistence annotation provider changes.
 * <p>
 * EclipseLink ships no JPQL grammar between 2.6 and 4.0, so this platform uses
 * {@code EclipseLinkJPQLGrammar2_6}, the most recent grammar that applies to it.
 */
public class EclipseLinkJpaPlatformFactory3_0
	extends EclipseLinkJpaPlatformFactory2_5
{
	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "eclipselink3_0"; //$NON-NLS-1$

	/**
	 * Version string for EclipseLink platform version 3.0
	 */
	public static final String VERSION = "3.0";  //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public EclipseLinkJpaPlatformFactory3_0() {
		super();
	}

	@Override
	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			buildJpaVersion(config.getJpaFacetVersion()),
			new EclipseLinkJpaFactory3_0(),
			buildAnnotationProvider(),
			EclipseLinkJpaPlatformProvider3_0.instance(),
			buildJpaVariation(),
			// EclipseLink 2.6 grammar: newest one in the bundled JPQL parser (2.7.3); 4.0/5.0 grammars need a newer parser (#9)
			EclipseLinkJPQLGrammar2_6.instance());
	}

	@Override
	protected JpaPlatform.Version buildJpaVersion(IProjectFacetVersion jpaFacetVersion) {
		return new EclipseLinkJpaPlatformVersion(VERSION, jpaFacetVersion.getVersionString());
	}

	@Override
	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(
				GenericJpaAnnotationDefinitionProvider3_0.instance(),
				EclipseLinkJpaAnnotationDefinitionProvider2_4.instance());
	}
}
