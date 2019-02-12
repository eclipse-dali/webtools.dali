/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.schemagen.GenericSchemaGeneration2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.PersistenceXmlContextModelFactory2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;

public class EclipseLinkPersistenceXmlContextModelFactory2_5
	extends EclipseLinkPersistenceXmlContextModelFactory2_4
	implements PersistenceXmlContextModelFactory2_1
{
	public SchemaGeneration2_1 buildSchemaGeneration(PersistenceUnit parent) {
		return new GenericSchemaGeneration2_1(parent);
	}
}
