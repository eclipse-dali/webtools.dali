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
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.ManyToOne;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericJavaManyToOneMapping extends AbstractJavaSingleRelationshipMapping<ManyToOne>
	implements JavaManyToOneMapping
{

	public GenericJavaManyToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS,
			JPA.JOIN_TABLE);
	}

	public String getAnnotationName() {
		return ManyToOne.ANNOTATION_NAME;
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
		this.getMappingResource().setOptional(newOptional);
	}
	
	@Override
	protected Boolean specifiedOptional(ManyToOne relationshipMapping) {
		return relationshipMapping.getOptional();
	}
	
	//ManyToOne mapping is always the owning side
	public boolean isRelationshipOwner() {
		return true;
	}

	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
	}
	
	//***************** ISingleRelationshipMapping implementation *****************
	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<JavaJoinColumn> joinColumns() {
		return super.joinColumns();
	}

	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return super.specifiedJoinColumns();
	}


}