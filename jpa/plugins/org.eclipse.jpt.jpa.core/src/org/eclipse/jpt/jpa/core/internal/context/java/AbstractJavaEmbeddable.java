/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;

/**
 * Java embeddable type mapping
 */
public abstract class AbstractJavaEmbeddable
		extends AbstractJavaTypeMapping<EmbeddableAnnotation>
		implements JavaEmbeddable {
	
	protected AbstractJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
	}
	
	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
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
	
	public Iterable<Query> getQueries() {
		return EmptyIterable.instance();
	}
	
	
	// ***** (no) inheritance *****
	
	public IdTypeMapping getSuperTypeMapping() {
		return null;
	}
	
	public Iterable<IdTypeMapping> getAncestors() {
		return EmptyIterable.instance();
	}
	
	public Iterable<? extends TypeMapping> getInheritanceHierarchy() {
		return IterableTools.singletonIterable(this);
	}
}
