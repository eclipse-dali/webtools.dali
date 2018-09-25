/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_2.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.GenericPersistenceXmlContextModelFactory2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.persistence.schemagen.GenericSchemaGeneration2_2;
import org.eclipse.jpt.jpa.core.jpa2_2.context.persistence.PersistenceXmlContextModelFactory2_2;
import org.eclipse.jpt.jpa.core.jpa2_2.context.persistence.schemagen.SchemaGeneration2_2;

public class GenericPersistenceXmlContextModelFactory2_2 extends GenericPersistenceXmlContextModelFactory2_1
		implements PersistenceXmlContextModelFactory2_2 {
	
	public SchemaGeneration2_2 buildSchemaGeneration(PersistenceUnit parent) {
		return new GenericSchemaGeneration2_2(parent);
	}
}
