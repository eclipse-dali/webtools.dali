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
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToManyMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;

public abstract class AbstractJavaManyToManyMapping
	extends AbstractJavaMultiRelationshipMapping<ManyToManyAnnotation>
	implements ManyToManyMapping2_0, JavaManyToManyMapping
{
	protected AbstractJavaManyToManyMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
	}


	// ********** relationship **********

	@Override
	public JavaManyToManyRelationship getRelationship() {
		return (JavaManyToManyRelationship) super.getRelationship();
	}

	@Override
	protected JavaMappingRelationship buildRelationship() {
		return new GenericJavaManyToManyRelationship(this);
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return ManyToManyAnnotation.ANNOTATION_NAME;
	}
}
