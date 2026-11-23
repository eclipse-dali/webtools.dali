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
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa3_1.context.persistence.schemagen.GenericSchemaGeneration3_1;
import org.eclipse.jpt.jpa.core.jpa3_1.context.persistence.PersistenceXmlContextModelFactory3_1;
import org.eclipse.jpt.jpa.core.jpa3_1.context.persistence.schemagen.SchemaGeneration3_1;

/**
 * EclipseLink 3.1 <code>persistence.xml</code> context model factory, used by
 * the EclipseLink 4.0 platform.
 * <p>
 * Keeps the EclipseLink persistence unit from 2.5 while picking up the
 * Jakarta Persistence 3.1 schema generation properties.
 */
public class EclipseLinkPersistenceXmlContextModelFactory3_1
	extends EclipseLinkPersistenceXmlContextModelFactory3_0
	implements PersistenceXmlContextModelFactory3_1
{
	@Override
	public SchemaGeneration3_1 buildSchemaGeneration(PersistenceUnit parent) {
		return new GenericSchemaGeneration3_1(parent);
	}
}
