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
package org.eclipse.jpt.jpa.core.internal.jpa3_0.context.persistence.schemagen;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.persistence.schemagen.GenericSchemaGeneration2_2;
import org.eclipse.jpt.jpa.core.jpa3_0.context.persistence.schemagen.SchemaGeneration3_0;

public class GenericSchemaGeneration3_0 extends GenericSchemaGeneration2_2 implements SchemaGeneration3_0 {

	public GenericSchemaGeneration3_0(PersistenceUnit parent) {
		super(parent);
	}
}
