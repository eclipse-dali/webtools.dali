/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;

public abstract class AbstractJavaManyToOneMapping
	extends AbstractJavaSingleRelationshipMapping<ManyToOneAnnotation>
	implements ManyToOneMapping2_0, JavaManyToOneMapping
{
	protected AbstractJavaManyToOneMapping(JavaSpecifiedPersistentAttribute parent) {
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
