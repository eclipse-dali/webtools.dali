/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaRelationshipReference;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class GenericJavaOneToOneMapping
	extends AbstractJavaSingleRelationshipMapping<OneToOneAnnotation>
	implements JavaOneToOneMapping
{
	public GenericJavaOneToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	
	@Override
	protected JavaRelationshipReference buildRelationshipReference() {
		return new GenericJavaOneToOneRelationshipReference(this);
	}
	
	public String getAnnotationName() {
		return OneToOneAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	public OneToOneAnnotation getMappingAnnotation() {
		return super.getMappingAnnotation();
	}
	
	public Iterator<String> supportingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.PRIMARY_KEY_JOIN_COLUMN,
			JPA.PRIMARY_KEY_JOIN_COLUMNS,
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS,
			JPA.JOIN_TABLE);
	}
	
	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public JavaOneToOneRelationshipReference getRelationshipReference() {
		return (JavaOneToOneRelationshipReference) super.getRelationshipReference();
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
