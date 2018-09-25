/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_2;

import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformFactory.GenericJpaPlatformVersion;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.GenericJpaAnnotationDefinitionProvider2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.GenericJpaPlatformFactory2_1;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar2_1;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once it
 * is initialized).
 */
public class GenericJpaPlatformFactory2_2 extends GenericJpaPlatformFactory2_1 {
	public static final String ID = "generic2_2"; //$NON-NLS-1$

	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(config, this.buildJpaVersion(config.getJpaFacetVersion()),
				new GenericJpaFactory2_2(),
				new JpaAnnotationProvider(GenericJpaAnnotationDefinitionProvider2_1.instance()),
				GenericJpaPlatformProvider2_2.instance(), this.buildJpaPlatformVariation(), JPQLGrammar2_1.instance());
	}

	private JpaPlatform.Version buildJpaVersion(IProjectFacetVersion jpaFacetVersion) {
		return new GenericJpaPlatformVersion(jpaFacetVersion.getVersionString());
	}
}
