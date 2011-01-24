/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaManyToOneRelationship;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;

public abstract class AbstractJavaManyToOneMapping
	extends AbstractJavaSingleRelationshipMapping<ManyToOneAnnotation>
	implements JavaManyToOneMapping2_0
{
	protected AbstractJavaManyToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
	}


	// ********** relationship **********

	@Override
	public JavaManyToOneRelationship getRelationship() {
		return (JavaManyToOneRelationship) super.getRelationship();
	}

	@Override
	protected JavaManyToOneRelationship buildRelationship() {
		return new GenericJavaManyToOneRelationship(this);
	}	


	// ********** misc **********

	public String getKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return ManyToOneAnnotation.ANNOTATION_NAME;
	}
}
