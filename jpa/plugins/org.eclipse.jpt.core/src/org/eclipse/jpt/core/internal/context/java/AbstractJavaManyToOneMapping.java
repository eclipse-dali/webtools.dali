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
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneRelationshipReference2_0;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;


public abstract class AbstractJavaManyToOneMapping
	extends AbstractJavaSingleRelationshipMapping<ManyToOneAnnotation>
	implements JavaManyToOneMapping2_0
{
	protected AbstractJavaManyToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ManyToOneAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	public ManyToOneAnnotation getMappingAnnotation() {
		return super.getMappingAnnotation();
	}
	
	public String getKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public JavaManyToOneRelationshipReference2_0 getRelationshipReference() {
		return (JavaManyToOneRelationshipReference2_0) super.getRelationshipReference();
	}
		
	@Override
	protected Boolean getResourceOptional() {
		return this.mappingAnnotation.getOptional();
	}
	
	@Override
	protected void setResourceOptional(Boolean newOptional) {
		this.mappingAnnotation.setOptional(newOptional);
	}
}
