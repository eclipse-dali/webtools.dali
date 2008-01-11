/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.OneToMany;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class JavaOneToManyMapping extends JavaMultiRelationshipMapping<OneToMany>
	implements IJavaOneToManyMapping
{
	
	public JavaOneToManyMapping(IJavaPersistentAttribute parent) {
		super(parent);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ORDER_BY,
			JPA.MAP_KEY,
			JPA.JOIN_TABLE,
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS);
	}
	
	
	public String getKey() {
		return IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public String annotationName() {
		return OneToMany.ANNOTATION_NAME;
	}
	
	@Override
	protected OneToMany relationshipMapping() {
		return (OneToMany) this.persistentAttributeResource.mappingAnnotation();
	}

	// ********** JavaMultiRelationshipMapping implementation **********

	@Override
	protected boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.relationshipMapping().mappedByTouches(pos, astRoot);
	}

	@Override
	protected void setMappedByOnResourceModel(String mappedBy) {
		this.relationshipMapping().setMappedBy(mappedBy);
	}
	
	@Override
	protected String mappedBy(OneToMany relationshipMapping) {
		return relationshipMapping.getMappedBy();
	}

	
	// ********** INonOwningMapping implementation **********
	public boolean mappedByIsValid(IAttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}
		
	public ITextRange mappedByTextRange(CompilationUnit astRoot) {
		return this.relationshipMapping().mappedByTextRange(astRoot);
	}
}