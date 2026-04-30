/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti. All rights reserved.
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
package org.eclipse.jpt.jpa.core.resource.java;

/**
 * Jakarta Persistence 3.1 Java-related constants.
 *
 * @since 3.1
 */
public interface JPA3_1 extends JPA3_0 {

	// ====================================================================
	// JPA 3.1 additions
	// ====================================================================

	/** Added in JPA 3.1 ({@code GenerationType.UUID}). */
	String GENERATION_TYPE__UUID = GENERATION_TYPE_ + "UUID";
}
