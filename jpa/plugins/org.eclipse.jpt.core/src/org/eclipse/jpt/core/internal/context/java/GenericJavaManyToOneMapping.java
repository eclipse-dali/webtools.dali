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

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class GenericJavaManyToOneMapping
	extends AbstractJavaSingleRelationshipMapping<ManyToOneAnnotation>
	implements JavaManyToOneMapping
{
	public GenericJavaManyToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	
	@Override
	protected JavaManyToOneRelationshipReference buildRelationshipReference() {
		return new GenericJavaManyToOneRelationshipReference(this);
	}
	
	public String getAnnotationName() {
		return ManyToOneAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	public ManyToOneAnnotation getMappingAnnotation() {
		return super.getMappingAnnotation();
	}
	
	public Iterator<String> supportingAnnotationNames() {
		return new ArrayIterator<String>(
						JPA.JOIN_COLUMN,
						JPA.JOIN_COLUMNS,
						JPA.JOIN_TABLE
					);
	}
	
	public String getKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public JavaManyToOneRelationshipReference getRelationshipReference() {
		return (JavaManyToOneRelationshipReference) super.getRelationshipReference();
	}
		
	@Override
	protected Boolean getResourceOptional() {
		return this.resourceMapping.getOptional();
	}
	
	@Override
	protected void setOptionalOnResourceModel(Boolean newOptional) {
		this.resourceMapping.setOptional(newOptional);
	}
	
	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
}
