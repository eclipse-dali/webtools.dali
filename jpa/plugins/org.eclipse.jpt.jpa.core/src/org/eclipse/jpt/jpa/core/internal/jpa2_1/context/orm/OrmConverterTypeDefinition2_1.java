/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.jpa2_1.context.ConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.EntityMappings2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmXmlContextModelFactory2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;

public class OrmConverterTypeDefinition2_1
	implements OrmManagedTypeDefinition
{	
	// singleton
	private static final OrmManagedTypeDefinition INSTANCE = new OrmConverterTypeDefinition2_1();

	/**
	 * Return the singleton.
	 */
	public static OrmManagedTypeDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private OrmConverterTypeDefinition2_1() {
		super();
	}

	public Class<ConverterType2_1> getContextManagedTypeType() {
		return ConverterType2_1.class;
	}

	public Class<XmlConverter> getResourceManagedTypeType() {
		return XmlConverter.class;
	}

	public OrmManagedType buildContextManagedType(JpaContextModel parent, XmlManagedType resourceManagedType, OrmXmlContextModelFactory factory) {
		return ((OrmXmlContextModelFactory2_1) factory).buildOrmConverterType((EntityMappings2_1) parent, (XmlConverter) resourceManagedType);
	}
}
