/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4;

/**
 * <code>eclipselink-orm.xml</code> uuid generator
 */
public class EclipseLinkOrmUuidGeneratorImpl
	extends AbstractOrmGenerator<XmlUuidGenerator_2_4>
	implements EclipseLinkOrmUuidGenerator
{


	public EclipseLinkOrmUuidGeneratorImpl(JpaContextModel parent, XmlUuidGenerator_2_4 xmlSequenceGenerator) {
		super(parent, xmlSequenceGenerator);
	}

	// ********** metadata conversion **********

	public void convertFrom(EclipseLinkJavaUuidGenerator javaGenerator) {
		super.convertFrom(javaGenerator);		
	}

	// ********** misc **********

	public Class<EclipseLinkUuidGenerator> getGeneratorType() {
		return EclipseLinkUuidGenerator.class;
	}
}
