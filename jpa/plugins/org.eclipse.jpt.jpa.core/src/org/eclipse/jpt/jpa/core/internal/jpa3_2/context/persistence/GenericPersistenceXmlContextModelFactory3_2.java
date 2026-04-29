/*******************************************************************************
 * Copyright (c) 2024, 2026 Lakshminarayana Nekkanti and others.
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
package org.eclipse.jpt.jpa.core.internal.jpa3_2.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa3_1.context.persistence.GenericPersistenceXmlContextModelFactory3_1;
import org.eclipse.jpt.jpa.core.internal.jpa3_2.context.persistence.schemagen.GenericSchemaGeneration3_2;
import org.eclipse.jpt.jpa.core.jpa3_2.context.persistence.PersistenceXmlContextModelFactory3_2;
import org.eclipse.jpt.jpa.core.jpa3_2.context.persistence.schemagen.SchemaGeneration3_2;

public class GenericPersistenceXmlContextModelFactory3_2 extends GenericPersistenceXmlContextModelFactory3_1
		implements PersistenceXmlContextModelFactory3_2 {

	public SchemaGeneration3_2 buildSchemaGeneration(PersistenceUnit parent) {
		return new GenericSchemaGeneration3_2(parent);
	}
}
