/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.UuidGeneratorAnnotation2_4;

/**
 * Java UUID generator
 */
public class EclipseLinkJavaUuidGeneratorImpl
	extends AbstractJavaGenerator<EclipseLinkJavaGeneratorContainer, UuidGeneratorAnnotation2_4>
	implements EclipseLinkJavaUuidGenerator
{
	public EclipseLinkJavaUuidGeneratorImpl(EclipseLinkJavaGeneratorContainer parent, UuidGeneratorAnnotation2_4 generatorAnnotation) {
		super(parent, generatorAnnotation);
	}


	// ********** misc **********
	
	public Class<EclipseLinkUuidGenerator> getGeneratorType() {
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
