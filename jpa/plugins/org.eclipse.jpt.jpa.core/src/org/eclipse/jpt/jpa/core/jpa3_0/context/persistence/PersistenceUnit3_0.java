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
package org.eclipse.jpt.jpa.core.jpa3_0.context.persistence;

import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.PersistenceUnit2_1;
import org.eclipse.jpt.jpa.core.jpa3_0.context.persistence.schemagen.SchemaGeneration3_0;

/**
 * JPA 3.0 <code>persistence-unit</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistenceUnit3_0 extends PersistenceUnit2_1 {
	// ********** properties **********

	SchemaGeneration3_0 getSchemaGeneration();
}
