/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaSequenceGenerator;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;

/**
 * Java sequence generator
 */
public class GenericJavaSequenceGenerator
	extends AbstractJavaSequenceGenerator<SequenceGeneratorAnnotation>
{
	public GenericJavaSequenceGenerator(JavaJpaContextNode parent, SequenceGeneratorAnnotation generatorAnnotation) {
		super(parent, generatorAnnotation);
	}


	// ********** database stuff **********

	/**
	 * The JPA 1.0 spec does not allow a sequence to specify a catalog.
	 */
	@Override
	protected String getCatalog() {
		return this.getContextDefaultCatalog();
	}

	/**
	 * The JPA 1.0 spec does not allow a sequence to specify a schema.
	 */
	@Override
	protected String getSchema() {
		return this.getContextDefaultSchema();
	}

}
