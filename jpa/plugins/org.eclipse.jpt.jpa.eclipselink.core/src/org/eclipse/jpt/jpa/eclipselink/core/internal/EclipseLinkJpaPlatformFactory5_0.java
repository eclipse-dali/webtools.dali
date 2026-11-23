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
 * EclipseLink 5.0 platform.
 * <p>
 * Implements Jakarta Persistence 3.2 and requires Java 17, which the
 * <code>jpt.jpa</code> 3.2 facet already enforces.
 * <p>
 * <strong>JPQL grammar:</strong> this platform should use
 * {@code EclipseLinkJPQLGrammar5_0}, which ships only in the 5.x
 * {@code org.eclipse.persistence.jpa.jpql} bundle. The target platform resolves
 * 2.7.3 today, so the most recent grammar available here is 2.6. Moving the
 * parser (issue #9, tracked separately) is what makes 5_0 usable; until then
 * JPQL content assist and validation run on the 2.6 grammar.
 */
public class EclipseLinkJpaPlatformFactory5_0
	extends EclipseLinkJpaPlatformFactory2_5
{
	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "eclipselink5_0"; //$NON-NLS-1$

	/**
	 * Version string for EclipseLink platform version 5.0
	 */
	public static final String VERSION = "5.0";  //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public EclipseLinkJpaPlatformFactory5_0() {
		super();
	}

	@Override
	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			buildJpaVersion(config.getJpaFacetVersion()),
			new EclipseLinkJpaFactory5_0(),
			buildAnnotationProvider(),
			EclipseLinkJpaPlatformProvider5_0.instance(),
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
