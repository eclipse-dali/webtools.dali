/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;

public class GenericOrmGeneratorContainer extends AbstractOrmGeneratorContainer
	implements OrmGeneratorContainer
{
	
	public GenericOrmGeneratorContainer(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer) {
		super(parent, resourceGeneratorContainer);
	}
	
	@Override
	protected XmlSequenceGenerator buildResourceSequenceGenerator() {
		return OrmFactory.eINSTANCE.createXmlSequenceGenerator();
	}
	
	@Override
	protected XmlTableGenerator buildResourceTableGenerator() {
		return OrmFactory.eINSTANCE.createXmlTableGenerator();
	}
	
	@Override
	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator resourceSequenceGenerator) {
		return getJpaFactory().buildOrmSequenceGenerator(this, resourceSequenceGenerator);
	}
	
	@Override
	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator resourceTableGenerator) {
		return getJpaFactory().buildOrmTableGenerator(this, resourceTableGenerator);
	}

}
