/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformFactory;
import org.eclipse.jpt.core.JpaPlatformVariation;
import org.eclipse.jpt.core.internal.jpa1.GenericJpaFactory;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * zero-argument constructor
	 */
	public GenericJpaPlatformFactory() {
		super();
	}
	
	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			this.buildJpaVersion(),
			new GenericJpaFactory(), 
			new GenericJpaAnnotationProvider(GenericJpaAnnotationDefinitionProvider.instance()),
			GenericJpaPlatformProvider.instance(), 
			this.buildJpaPlatformVariation());
	}
	
	
	private JpaPlatform.Version buildJpaVersion() {
		return new JpaPlatform.Version() {
			public boolean is2_0Compatible() {
				return false;
			}
			
			public int getJpaVersion() {
				return 1;
			}
		};
	}
	
	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.MAYBE;
			}
			public boolean isJoinTableOverridable() {
				return false;
			}
		};
	}
}
