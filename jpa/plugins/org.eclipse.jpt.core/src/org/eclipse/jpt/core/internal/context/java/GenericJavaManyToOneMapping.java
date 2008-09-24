/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericJavaManyToOneMapping
	extends AbstractJavaSingleRelationshipMapping<ManyToOneAnnotation>
	implements JavaManyToOneMapping
{

	public GenericJavaManyToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
						JPA.JOIN_COLUMN,
						JPA.JOIN_COLUMNS,
						JPA.JOIN_TABLE
					);
	}

	public String getAnnotationName() {
		return ManyToOneAnnotation.ANNOTATION_NAME;
	}

	public String getKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
	
	@Override
	protected void setOptionalOnResourceModel(Boolean newOptional) {
		this.getResourceMapping().setOptional(newOptional);
	}
	
	@Override
	protected Boolean buildSpecifiedOptional(ManyToOneAnnotation relationshipMapping) {
		return relationshipMapping.getOptional();
	}
	
	//ManyToOne mapping is always the owning side
	public boolean isRelationshipOwner() {
		return true;
	}

	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
	}

}
