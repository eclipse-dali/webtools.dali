/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlTableGenerator;

public class GenericEntityMappings2_0
	extends AbstractEntityMappings
{
	
	public GenericEntityMappings2_0(OrmXml parent, XmlEntityMappings xmlEntityMapping) {
		super(parent, xmlEntityMapping);
	}

	@Override
	protected XmlSequenceGenerator buildResourceSequenceGenerator() {
		return Orm2_0Factory.eINSTANCE.createXmlSequenceGenerator();
	}
	
	@Override
	protected XmlTableGenerator buildResourceTableGenerator() {
		return Orm2_0Factory.eINSTANCE.createXmlTableGenerator();
	}

}
