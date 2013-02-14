/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformFactory.GenericJpaPlatformVersion;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar2_1;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class Generic2_1JpaPlatformFactory
	implements JpaPlatformFactory
{
	public static final String ID = "generic2_1"; //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public Generic2_1JpaPlatformFactory() {
		super();
	}

	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			this.buildJpaVersion(),
			new GenericJpaFactory2_1(),
			new JpaAnnotationProvider(Generic2_1JpaAnnotationDefinitionProvider.instance()),
			Generic2_1JpaPlatformProvider.instance(),
			this.buildJpaPlatformVariation(),
			JPQLGrammar2_1.instance());
	}

	private JpaPlatform.Version buildJpaVersion() {
		return new GenericJpaPlatformVersion(JpaProject2_1.FACET_VERSION_STRING);
	}

	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			//table_per_class inheritance support is optional in the 2.1 spec
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.MAYBE;
			}
			public boolean isJoinTableOverridable() {
				return true;
			}
			public AccessType[] getSupportedAccessTypes(JptResourceType resourceType) {
				return GENERIC_SUPPORTED_ACCESS_TYPES;
			}
		};
	}
}
