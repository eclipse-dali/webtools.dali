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

import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSequenceGenerator2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;

public class GenericEntityMappings2_0
	extends AbstractEntityMappings
{
	
	public GenericEntityMappings2_0(GenericOrmXml2_0 parent, XmlEntityMappings xmlEntityMapping) {
		super(parent, xmlEntityMapping);
	}
	
	@Override
	protected PersistenceUnitMetadata buildPersistenceUnitMetadata() {
		return 	getJpaFactory().buildPersistenceUnitMetadata2_0(this, (XmlEntityMappings) this.xmlEntityMappings);
	}
	
	@Override
	protected OrmPersistentType buildPersistentType(XmlTypeMapping resourceMapping) {
		return getJpaFactory().buildOrmPersistentType2_0(this, resourceMapping);
	}

	@Override
	protected XmlSequenceGenerator buildResourceSequenceGenerator() {
		return Orm2_0Factory.eINSTANCE.createXmlSequenceGenerator();
	}
	
	@Override
	protected OrmSequenceGenerator2_0 buildSequenceGenerator(org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator resourceSequenceGenerator) {
		return getJpaFactory().buildOrmSequenceGenerator2_0(this, (XmlSequenceGenerator) resourceSequenceGenerator);
	}
	
	// **************** JpaNode impl *******************************************
	
	@Override
	protected JpaFactory2_0 getJpaFactory() {
		return (JpaFactory2_0) super.getJpaFactory();
	}
	
	@Override
	public GenericOrmXml2_0 getParent() {
		return (GenericOrmXml2_0) super.getParent();
	}	
}
