/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericJavaOneToOneMapping extends AbstractJavaSingleRelationshipMapping<OneToOneAnnotation>
	implements JavaOneToOneMapping
{
	protected String mappedBy;
	
	public GenericJavaOneToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.PRIMARY_KEY_JOIN_COLUMN,
			JPA.PRIMARY_KEY_JOIN_COLUMNS,
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS,
			JPA.JOIN_TABLE);
	}
	
	public String getAnnotationName() {
		return OneToOneAnnotation.ANNOTATION_NAME;
	}
	
	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	public boolean isRelationshipOwner() {
		return getMappedBy() == null;
	}
	
	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		this.getMappingResource().setMappedBy(newMappedBy);
		firePropertyChanged(NonOwningMapping.MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}

	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	@Override
	protected void setOptionalOnResourceModel(Boolean newOptional) {
		this.getMappingResource().setOptional(newOptional);
	}
	
	public TextRange getMappedByTextRange(CompilationUnit astRoot) {
		return this.getMappingResource().getMappedByTextRange(astRoot);
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.getMappingResource().mappedByTouches(pos, astRoot);
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mappedByTouches(pos, astRoot)) {
			return this.quotedCandidateMappedByAttributeNames(filter);
		}
		return null;
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
		
	@Override
	protected void initialize(OneToOneAnnotation oneToOneResource) {
		super.initialize(oneToOneResource);
		this.mappedBy = oneToOneResource.getMappedBy();
	}

	@Override
	protected void update(OneToOneAnnotation oneToOneResource) {
		super.update(oneToOneResource);
		this.setMappedBy(oneToOneResource.getMappedBy());
	}

	@Override
	protected Boolean specifiedOptional(OneToOneAnnotation relationshipMapping) {
		return relationshipMapping.getOptional();
	}
	
	//***************** Validation ***********************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		if (this.getMappedBy() != null) {
			addMappedByMessages(messages ,astRoot);
		}
	}
	
	protected void addMappedByMessages(List<IMessage> messages, CompilationUnit astRoot) {
		String mappedBy = this.getMappedBy();
		Entity targetEntity = this.getResolvedTargetEntity();
		
		if (targetEntity == null) {
			// already have validation messages for that
			return;
		}
		
		PersistentAttribute attribute = targetEntity.getPersistentType().resolveAttribute(mappedBy);
		
		if (attribute == null) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
						new String[] {mappedBy}, 
						this, this.getMappedByTextRange(astRoot))
				);
			return;
		}
		
		if (! this.mappedByIsValid(attribute.getMapping())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
						new String[] {mappedBy}, 
						this, this.getMappedByTextRange(astRoot))
				);
			return;
		}
		
		NonOwningMapping mappedByMapping;
		try {
			mappedByMapping = (NonOwningMapping) attribute.getMapping();
		} catch (ClassCastException cce) {
			// there is no error then
			return;
		}
		
		if (mappedByMapping.getMappedBy() != null) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
						this, this.getMappedByTextRange(astRoot))
				);
		}
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
