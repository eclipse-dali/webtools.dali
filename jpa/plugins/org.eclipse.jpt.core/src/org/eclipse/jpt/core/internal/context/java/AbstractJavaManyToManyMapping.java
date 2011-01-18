/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaManyToManyRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaMappingRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToManyMapping2_0;
import org.eclipse.jpt.core.resource.java.ManyToManyAnnotation;

public abstract class AbstractJavaManyToManyMapping
	extends AbstractJavaMultiRelationshipMapping<ManyToManyAnnotation>
	implements JavaManyToManyMapping2_0
{
	protected AbstractJavaManyToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
	}


	// ********** relationship **********

	@Override
	public JavaManyToManyRelationshipReference getRelationshipReference() {
		return (JavaManyToManyRelationshipReference) super.getRelationshipReference();
	}

	@Override
	protected JavaMappingRelationshipReference buildRelationshipReference() {
		return new GenericJavaManyToManyRelationshipReference(this);
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
