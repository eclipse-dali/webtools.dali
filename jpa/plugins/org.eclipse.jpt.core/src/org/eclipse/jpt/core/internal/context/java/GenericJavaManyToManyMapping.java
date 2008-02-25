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
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.ManyToMany;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaManyToManyMapping extends AbstractJavaMultiRelationshipMapping<ManyToMany>
	implements JavaManyToManyMapping
{
	
	public GenericJavaManyToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public String annotationName() {
		return ManyToMany.ANNOTATION_NAME;
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ORDER_BY,
			JPA.MAP_KEY,
			JPA.JOIN_TABLE);
	}
	
	@Override
	protected ManyToMany relationshipMapping() {
		return (ManyToMany) this.persistentAttributeResource.mappingAnnotation();
	}
	
	// ********** JavaMultiRelationshipMapping implementation **********

	@Override
	protected boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.relationshipMapping().mappedByTouches(pos, astRoot);
	}
	
	
	@Override
	protected void setMappedByOnResourceModel(String mappedBy) {
		relationshipMapping().setMappedBy(mappedBy);
	}
	
	@Override
	protected String mappedBy(ManyToMany relationshipMapping) {
		return relationshipMapping.getMappedBy();
	}
	
	// ********** INonOwningMapping implementation **********
	
	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
	}
	
	public TextRange mappedByTextRange(CompilationUnit astRoot) {
		return this.relationshipMapping().mappedByTextRange(astRoot);
	}
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
	}
}