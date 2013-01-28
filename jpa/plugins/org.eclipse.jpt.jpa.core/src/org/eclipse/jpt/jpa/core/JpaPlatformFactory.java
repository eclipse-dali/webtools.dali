/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

/**
 * This interface is to be implemented by a JPA vendor to extend Dali by
 * building a {@link JpaPlatform}.
 * <p>
 * See the  extension point
 * <code>org.eclipse.jpt.jpa.core.jpaPlatforms</code>
 * in
 * <code>org.eclipse.jpt.jpa.core/plugin.xml</code>
 * and
 * <code>org.eclipse.jpt.jpa.core/schema/jpaPlatforms.exsd</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JpaPlatformFactory {
	/**
	 * Build the JPA platform with the specified config.
	 */
	JpaPlatform buildJpaPlatform(JpaPlatform.Config config);
}
