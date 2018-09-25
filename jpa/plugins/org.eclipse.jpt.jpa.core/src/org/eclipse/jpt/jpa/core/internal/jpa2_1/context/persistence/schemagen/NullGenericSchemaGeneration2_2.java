/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.schemagen;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2_2.context.persistence.schemagen.SchemaGeneration2_2;

/**
 * schema generation
 */
public class NullGenericSchemaGeneration2_2 extends NullGenericSchemaGeneration2_1 implements SchemaGeneration2_2 {

	public NullGenericSchemaGeneration2_2(PersistenceUnit parent) {
		super(parent);
	}
}
