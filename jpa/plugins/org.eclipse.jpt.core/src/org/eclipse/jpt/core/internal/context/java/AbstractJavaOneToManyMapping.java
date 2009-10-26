/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaOneToManyRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToManyMapping2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.utility.internal.ArrayTools;


public abstract class AbstractJavaOneToManyMapping<T extends OneToManyAnnotation>
	extends AbstractJavaMultiRelationshipMapping<T>
	implements JavaOneToManyMapping2_0
{
	
	protected AbstractJavaOneToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	
	public String getAnnotationName() {
		return OneToManyAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected String[] buildSupportingAnnotationNames() {
		return ArrayTools.addAll(
			super.buildSupportingAnnotationNames(),
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS);
	}

	@Override
	public T getMappingAnnotation() {
		return super.getMappingAnnotation();
	}
	
	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public JavaOneToManyRelationshipReference getRelationshipReference() {
		return (JavaOneToManyRelationshipReference) super.getRelationshipReference();
	}
	
	// ********** JPA 2.0 behavior **********

	public boolean isOrphanRemoval() {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}

	public Boolean getSpecifiedOrphanRemoval() {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}

	public void setSpecifiedOrphanRemoval(Boolean newOrphanRemoval) {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}

	public boolean isDefaultOrphanRemoval() {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}
}
