/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmGeneratorContainer;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;

/**
 *  GenericOrmGeneratorContainer2_0
 */
public class GenericOrmGeneratorContainer2_0 extends AbstractOrmGeneratorContainer
{
	public GenericOrmGeneratorContainer2_0(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer) {
		super(parent, resourceGeneratorContainer);
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
	