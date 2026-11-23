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

import org.eclipse.jpt.jpa.core.jpa3_0.JpaFactory3_0;

/**
 * EclipseLink 3.0 factory.
 * <p>
 * Jakarta Persistence 3.0 adds no new context model type over its predecessor, so this
 * contributes nothing beyond the version marker interface.
 */
public class EclipseLinkJpaFactory3_0
	extends EclipseLinkJpaFactory2_7
	implements JpaFactory3_0
{
	public EclipseLinkJpaFactory3_0() {
		super();
	}
}
