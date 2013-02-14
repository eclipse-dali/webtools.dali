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

import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.EntityMappings2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmXml2_1ContextNodeFactory;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1;

public class GenericOrmXml2_1ContextNodeFactory
	extends GenericOrmXml2_0ContextNodeFactory
	implements OrmXml2_1ContextNodeFactory
{

	public OrmConverterType2_1 buildOrmConverter(EntityMappings2_1 parent, XmlConverter_2_1 xmlConverter) {
		return new GenericOrmConverterType(parent, xmlConverter);
	}
}
