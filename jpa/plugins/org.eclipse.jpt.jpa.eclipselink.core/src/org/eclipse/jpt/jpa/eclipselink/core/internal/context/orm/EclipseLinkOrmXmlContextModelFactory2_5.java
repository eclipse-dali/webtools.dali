/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.GenericOrmConverterType;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.GenericOrmNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.GenericOrmStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmQueryContainer2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.EntityMappings2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmXmlContextModelFactory2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;

public class EclipseLinkOrmXmlContextModelFactory2_5
	extends EclipseLinkOrmXmlContextModelFactory2_4
	implements OrmXmlContextModelFactory2_1
{
	public OrmConverterType2_1 buildOrmConverterType(EntityMappings2_1 parent, XmlConverter xmlConverter) {
		return new GenericOrmConverterType(parent, xmlConverter);
	}

	public OrmNamedStoredProcedureQuery2_1 buildOrmNamedStoredProcedureQuery(OrmQueryContainer2_1 parent, XmlNamedStoredProcedureQuery xmlNamedStoredProcedureQuery) {
		return new GenericOrmNamedStoredProcedureQuery2_1(parent, xmlNamedStoredProcedureQuery);
	}

	public OrmStoredProcedureParameter2_1 buildOrmStoredProcedureParameter(OrmNamedStoredProcedureQuery2_1 parent, XmlStoredProcedureParameter xmlParameter) {
		return new GenericOrmStoredProcedureParameter2_1(parent, xmlParameter);
	}
}
