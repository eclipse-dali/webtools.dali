/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;

/**
 * Java embeddable type mapping
 */
public abstract class AbstractJavaEmbeddable
	extends AbstractJavaTypeMapping<EmbeddableAnnotation>
	implements JavaEmbeddable
{
	protected AbstractJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
	}

	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

	public JavaPersistentType getIdClass() {
		return null;
	}

	public boolean isMapped() {
		return true;
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	@Override
	public boolean validatesAgainstDatabase() {
		return false;
	}
}
