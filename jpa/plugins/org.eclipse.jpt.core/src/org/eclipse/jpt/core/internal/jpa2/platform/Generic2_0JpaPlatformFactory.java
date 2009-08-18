/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.platform;

import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformFactory;
import org.eclipse.jpt.core.JpaPlatformVariation;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaPlatform;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class Generic2_0JpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * zero-argument constructor
	 */
	public Generic2_0JpaPlatformFactory() {
		super();
	}
	
	
	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			new Generic2_0JpaFactory(), 
			new GenericJpaAnnotationProvider(Generic2_0JpaAnnotationDefinitionProvider.instance()),
			Generic2_0JpaPlatformProvider.instance(),
			buildJpaPlatformVariation());
	}
	
	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			//table_per_class inheritance support is optional in the 2.0 spec
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.MAYBE;
			}
			public boolean isJoinTableOverridable() {
				return true;
			}
		};
	}
}
