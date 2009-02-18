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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * 
 */
public class GenericJavaOneToOneMapping
	extends AbstractJavaSingleRelationshipMapping<OneToOneAnnotation>
	implements JavaOneToOneMapping
{
	protected String mappedBy;
	
	protected final List<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns;
	

	public GenericJavaOneToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.primaryKeyJoinColumns = new ArrayList<JavaPrimaryKeyJoinColumn>();
	}


	// ********** mapped by **********

	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		String old = this.mappedBy;
		this.mappedBy = mappedBy;
		this.resourceMapping.setMappedBy(mappedBy);
		this.firePropertyChanged(NonOwningMapping.MAPPED_BY_PROPERTY, old, mappedBy);
	}

	protected void setMappedBy_(String mappedBy) {
		String old = this.mappedBy;
		this.mappedBy = mappedBy;
		this.firePropertyChanged(NonOwningMapping.MAPPED_BY_PROPERTY, old, mappedBy);
	}

	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}


	// ********** primary key join columns **********

	public ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return new CloneListIterator<JavaPrimaryKeyJoinColumn>(this.primaryKeyJoinColumns);
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumns.size();
	}
	
	public JavaPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn pkJoinColumn = this.getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, this.createJoinColumnOwner());
		this.primaryKeyJoinColumns.add(index, pkJoinColumn);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation = (PrimaryKeyJoinColumnAnnotation) this.getResourcePersistentAttribute().addSupportingAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		pkJoinColumn.initialize(pkJoinColumnAnnotation);
		this.fireItemAdded(PRIMARY_KEY_JOIN_COLUMNS_LIST, index, pkJoinColumn);
		return pkJoinColumn;
	}

	protected void addPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn joinColumn) {
		this.addItemToList(index, joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected void addPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn joinColumn) {
		this.addPrimaryKeyJoinColumn(this.primaryKeyJoinColumns.size(), joinColumn);
	}

	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumns.indexOf(pkJoinColumn));
	}
	
	public void removePrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn pkJoinColumn = this.primaryKeyJoinColumns.remove(index);
		this.getResourcePersistentAttribute().removeSupportingAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		this.fireItemRemoved(PRIMARY_KEY_JOIN_COLUMNS_LIST, index, pkJoinColumn);
	}

	protected void removePrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.primaryKeyJoinColumns, targetIndex, sourceIndex);
		this.getResourcePersistentAttribute().moveSupportingAnnotation(targetIndex, sourceIndex, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		this.fireItemMoved(PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	public boolean containsPrimaryKeyJoinColumns() {
		return ! this.primaryKeyJoinColumns.isEmpty();
	}
	

	// ********** JavaAttributeMapping implementation **********

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
	

	// ********** AttributeMapping implementation **********

	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}


	// ********** RelationshipMapping implementation **********

	public boolean isRelationshipOwner() {
		return this.getMappedBy() == null;
	}
	

	// ********** AbstractJavaSingleRelationshipMapping implementation **********

	@Override
	protected void setOptionalOnResourceModel(Boolean newOptional) {
		this.resourceMapping.setOptional(newOptional);
	}
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mappedByTouches(pos, astRoot)) {
			return this.javaCandidateMappedByAttributeNames(filter);
		}
		return null;
	}

	protected boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.resourceMapping.mappedByTouches(pos, astRoot);
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}

	@Override
	protected Boolean getResourceOptional() {
		return this.resourceMapping.getOptional();
	}


	// ********** resource => context **********

	@Override
	protected void initialize() {
		super.initialize();
		this.initializePrimaryKeyJoinColumns();
		this.mappedBy = getResourceMappedBy();
	}
	
	protected void initializePrimaryKeyJoinColumns() {
		for (ListIterator<NestableAnnotation> stream = this.resourcePersistentAttribute.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME); stream.hasNext(); ) {
			this.primaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) stream.next()));
		}
	}

	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation primaryKeyJoinColumnResource) {
		JavaPrimaryKeyJoinColumn pkJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, createJoinColumnOwner());
		pkJoinColumn.initialize(primaryKeyJoinColumnResource);
		return pkJoinColumn;
	}

	@Override
	protected void update() {
		super.update();
		this.updatePrimaryKeyJoinColumns();
		this.setMappedBy_(getResourceMappedBy());
	}
	
	protected void updatePrimaryKeyJoinColumns() {
		ListIterator<JavaPrimaryKeyJoinColumn> contextPkJoinColumns = primaryKeyJoinColumns();
		ListIterator<NestableAnnotation> resourcePkJoinColumns = this.resourcePersistentAttribute.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		while (contextPkJoinColumns.hasNext()) {
			JavaPrimaryKeyJoinColumn pkJoinColumn = contextPkJoinColumns.next();
			if (resourcePkJoinColumns.hasNext()) {
				pkJoinColumn.update((PrimaryKeyJoinColumnAnnotation) resourcePkJoinColumns.next());
			}
			else {
				removePrimaryKeyJoinColumn_(pkJoinColumn);
			}
		}
		
		while (resourcePkJoinColumns.hasNext()) {
			addPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) resourcePkJoinColumns.next()));
		}
	}

	protected String getResourceMappedBy() {
		return this.resourceMapping.getMappedBy();
	}
	// ********** Validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (this.mappedBy != null) {
			this.validateMappedBy(messages ,astRoot);
		}
	}

	@Override
	protected void validateJoinColumns(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.primaryKeyJoinColumns.isEmpty() || this.containsSpecifiedJoinColumns()) {
			super.validateJoinColumns(messages, astRoot);
		}
	}

	protected void validateMappedBy(List<IMessage> messages, CompilationUnit astRoot) {
		Entity targetEntity = this.getResolvedTargetEntity();
		if (targetEntity == null) {
			return;  // null target entity is validated elsewhere
		}
		
		PersistentAttribute attribute = targetEntity.getPersistentType().resolveAttribute(this.mappedBy);
		
		if (attribute == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
					new String[] {this.mappedBy},
					this,
					this.getMappedByTextRange(astRoot)
				)
			);
			return;
		}
		
		AttributeMapping mappedByMapping = attribute.getMapping();
		if ( ! this.mappedByIsValid(mappedByMapping)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
					new String[] {this.mappedBy}, 
					this,
					this.getMappedByTextRange(astRoot)
				)
			);
			return;
		}
		
		if ((mappedByMapping instanceof NonOwningMapping)
				&& ((NonOwningMapping) mappedByMapping).getMappedBy() != null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
					this,
					this.getMappedByTextRange(astRoot)
				)
			);
		}
	}

	protected TextRange getMappedByTextRange(CompilationUnit astRoot) {
		return this.resourceMapping.getMappedByTextRange(astRoot);
	}

}
