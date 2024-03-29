/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm;

import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXmlContextModelFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.EntityMappings2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmQueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmXmlContextModelFactory2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;

public class GenericOrmXmlContextModelFactory2_1
	extends GenericOrmXmlContextModelFactory2_0
	implements OrmXmlContextModelFactory2_1
{
	public OrmConverterType2_1 buildOrmConverterType(EntityMappings2_1 parent, XmlConverter xmlConverter) {
		return new GenericOrmConverterType2_1(parent, xmlConverter);
	}

	public OrmNamedStoredProcedureQuery2_1 buildOrmNamedStoredProcedureQuery(OrmQueryContainer2_1 parent, XmlNamedStoredProcedureQuery xmlNamedStoredProcedureQuery) {
		return new GenericOrmNamedStoredProcedureQuery2_1(parent, xmlNamedStoredProcedureQuery);
	}
	
	public OrmStoredProcedureParameter2_1 buildOrmStoredProcedureParameter(OrmNamedStoredProcedureQuery2_1 parent, XmlStoredProcedureParameter xmlParameter) {
		return new GenericOrmStoredProcedureParameter2_1(parent, xmlParameter);
	}
}
