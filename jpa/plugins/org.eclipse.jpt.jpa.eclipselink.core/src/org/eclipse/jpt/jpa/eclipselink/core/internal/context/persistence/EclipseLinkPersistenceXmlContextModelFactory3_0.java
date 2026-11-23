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
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.persistence.schemagen.GenericSchemaGeneration3_0;
import org.eclipse.jpt.jpa.core.jpa3_0.context.persistence.PersistenceXmlContextModelFactory3_0;
import org.eclipse.jpt.jpa.core.jpa3_0.context.persistence.schemagen.SchemaGeneration3_0;

/**
 * EclipseLink 3.0 <code>persistence.xml</code> context model factory.
 * <p>
 * Keeps the EclipseLink persistence unit from 2.5 while picking up the
 * Jakarta Persistence 3.0 schema generation properties.
 */
public class EclipseLinkPersistenceXmlContextModelFactory3_0
	extends EclipseLinkPersistenceXmlContextModelFactory2_7
	implements PersistenceXmlContextModelFactory3_0
{
	@Override
	public SchemaGeneration3_0 buildSchemaGeneration(PersistenceUnit parent) {
		return new GenericSchemaGeneration3_0(parent);
	}
}
