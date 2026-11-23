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

import org.eclipse.jpt.jpa.core.jpa3_2.JpaFactory3_2;

/**
 * EclipseLink 5.0 factory.
 * <p>
 * Jakarta Persistence 3.2 adds no new context model type that Dali builds
 * here, so this contributes nothing beyond the version marker interface.
 */
public class EclipseLinkJpaFactory5_0
	extends EclipseLinkJpaFactory4_0
	implements JpaFactory3_2
{
	public EclipseLinkJpaFactory5_0() {
		super();
	}
}
