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
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.persistence.schemagen.GenericSchemaGeneration2_2;
import org.eclipse.jpt.jpa.core.jpa2_2.context.persistence.PersistenceXmlContextModelFactory2_2;
import org.eclipse.jpt.jpa.core.jpa2_2.context.persistence.schemagen.SchemaGeneration2_2;

/**
 * EclipseLink 2.7 <code>persistence.xml</code> context model factory.
 * <p>
 * EclipseLink 2.7 implements JPA 2.2, whose only addition to the persistence
 * unit context model is the 2.2 schema generation properties.
 */
public class EclipseLinkPersistenceXmlContextModelFactory2_7
	extends EclipseLinkPersistenceXmlContextModelFactory2_5
	implements PersistenceXmlContextModelFactory2_2
{
	@Override
	public SchemaGeneration2_2 buildSchemaGeneration(PersistenceUnit parent) {
		return new GenericSchemaGeneration2_2(parent);
	}
}
