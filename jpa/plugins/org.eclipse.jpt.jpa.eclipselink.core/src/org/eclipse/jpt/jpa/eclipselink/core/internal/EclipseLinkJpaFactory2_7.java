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

import org.eclipse.jpt.jpa.core.jpa2_2.JpaFactory2_2;

/**
 * EclipseLink 2.7 factory.
 * <p>
 * EclipseLink 2.7 implements JPA 2.2, which adds no new context model types
 * over JPA 2.1, so this contributes nothing beyond the 2.2 marker interface.
 */
public class EclipseLinkJpaFactory2_7
	extends EclipseLinkJpaFactory2_5
	implements JpaFactory2_2
{
	public EclipseLinkJpaFactory2_7() {
		super();
	}
}
