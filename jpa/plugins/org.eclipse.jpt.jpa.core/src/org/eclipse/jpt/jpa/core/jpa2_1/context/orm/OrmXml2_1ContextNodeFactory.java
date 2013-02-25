/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmQueryContainer2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;

/**
 * JPA 2.1 <code>orm.xml</code> context node factory
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface OrmXml2_1ContextNodeFactory
	extends OrmXmlContextModelFactory
{
	OrmConverterType2_1 buildOrmConverterType(EntityMappings2_1 parent, XmlConverter xmlConverter);

	OrmNamedStoredProcedureQuery2_1 buildOrmNamedStoredProcedureQuery2_1(
			OrmQueryContainer2_1 parent,
			XmlNamedStoredProcedureQuery xmlNamedQuery);

	OrmStoredProcedureParameter2_1 buildOrmStoredProcedureParameter(
			OrmNamedStoredProcedureQuery2_1 parent,
			XmlStoredProcedureParameter xmlParameter);
}
