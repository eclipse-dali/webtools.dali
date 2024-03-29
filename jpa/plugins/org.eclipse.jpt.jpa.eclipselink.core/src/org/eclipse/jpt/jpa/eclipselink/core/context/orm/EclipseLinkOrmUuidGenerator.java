/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4;

/**
 * <code>eclipselink-orm.xml</code> 2.4 uuid generator 
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface EclipseLinkOrmUuidGenerator
	extends EclipseLinkUuidGenerator, OrmGenerator
{
	XmlUuidGenerator_2_4 getXmlGenerator();

	//********* metadata conversion *********

	/**
	 * Build up a mapping file generator from the given Java generator.
	 */
	void convertFrom(EclipseLinkJavaUuidGenerator javaGenerator);
}
