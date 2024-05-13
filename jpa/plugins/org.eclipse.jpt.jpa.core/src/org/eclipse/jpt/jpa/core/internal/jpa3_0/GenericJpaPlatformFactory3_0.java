/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
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
package org.eclipse.jpt.jpa.core.internal.jpa3_0;

import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.GenericJpaAnnotationDefinitionProvider2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_2.GenericJpaPlatformFactory2_2;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar2_1;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once it
 * is initialized).
 */
public class GenericJpaPlatformFactory3_0 extends GenericJpaPlatformFactory2_2 {
	public static final String ID = "generic3_0"; //$NON-NLS-1$

	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(config, this.buildJpaVersion(config.getJpaFacetVersion()),
				new GenericJpaFactory3_0(),
				new JpaAnnotationProvider(GenericJpaAnnotationDefinitionProvider2_1.instance()),
				GenericJpaPlatformProvider3_0.instance(), this.buildJpaPlatformVariation(), JPQLGrammar2_1.instance());
	}
}
