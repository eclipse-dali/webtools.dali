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

import org.eclipse.jpt.jpa.core.jpa3_1.JpaFactory3_1;

/**
 * EclipseLink 4.0 factory.
 * <p>
 * Jakarta Persistence 3.1 adds no new context model type over its predecessor, so this
 * contributes nothing beyond the version marker interface.
 */
public class EclipseLinkJpaFactory4_0
	extends EclipseLinkJpaFactory3_0
	implements JpaFactory3_1
{
	public EclipseLinkJpaFactory4_0() {
		super();
	}
}
