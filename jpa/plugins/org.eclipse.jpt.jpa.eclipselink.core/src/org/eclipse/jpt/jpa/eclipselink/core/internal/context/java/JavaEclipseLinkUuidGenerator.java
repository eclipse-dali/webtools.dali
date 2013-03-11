/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkUuidGeneratorAnnotation2_4;

/**
 * Java UUID generator
 */
public class JavaEclipseLinkUuidGenerator
	extends AbstractJavaGenerator<EclipseLinkJavaGeneratorContainer, EclipseLinkUuidGeneratorAnnotation2_4>
	implements EclipseLinkJavaUuidGenerator
{
	public JavaEclipseLinkUuidGenerator(EclipseLinkJavaGeneratorContainer parent, EclipseLinkUuidGeneratorAnnotation2_4 generatorAnnotation) {
		super(parent, generatorAnnotation);
	}


	// ********** misc **********
	
	public Class<EclipseLinkUuidGenerator> getType() {
		return EclipseLinkUuidGenerator.class;
	}


	// ********** metadata conversion **********

	public void convertTo(EntityMappings entityMappings) {
		((EclipseLinkEntityMappings) entityMappings).addUuidGenerator().convertFrom(this);
	}

	public void delete() {
		this.parent.removeUuidGenerator();	
	}
}
