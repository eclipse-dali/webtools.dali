/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.UuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4;

/**
 * <code>eclipselink-orm.xml</code> uuid generator
 */
public class OrmEclipseLinkUuidGenerator
	extends AbstractOrmGenerator<XmlUuidGenerator_2_4>
	implements OrmUuidGenerator
{


	public OrmEclipseLinkUuidGenerator(XmlContextNode parent, XmlUuidGenerator_2_4 xmlSequenceGenerator) {
		super(parent, xmlSequenceGenerator);
	}

	// ********** metadata conversion **********

	public void convertFrom(JavaUuidGenerator javaGenerator) {
		this.setName(javaGenerator.getName());		
	}

	// ********** misc **********

	public Class<UuidGenerator> getType() {
		return UuidGenerator.class;
	}

	//TODO remove these methods after refactoring UuidGenerator to extend JpaNamedContextNode
	@Override
	protected int buildDefaultInitialValue() {
		return 0;
	}
	@Override
	protected String getSchema() {
		return null;
	}
	@Override
	protected String getCatalog() {
		return null;
	}
}
