/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4;

public class EclipseLinkOrmXmlContextModelFactory2_4
	extends EclipseLinkOrmXmlContextModelFactory2_3
{
	@Override
	public OrmGeneratorContainer buildOrmGeneratorContainer(JpaContextModel parent, XmlGeneratorContainer resourceGeneratorContainer) {
		return new EclipseLinkOrmGeneratorContainer(parent, (XmlGeneratorContainer2_4) resourceGeneratorContainer);
	}
}
