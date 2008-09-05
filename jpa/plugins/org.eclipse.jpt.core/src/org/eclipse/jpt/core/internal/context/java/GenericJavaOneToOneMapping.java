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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericJavaOneToOneMapping extends AbstractJavaSingleRelationshipMapping<OneToOneAnnotation>
	implements JavaOneToOneMapping
{
	protected String mappedBy;
	
	protected final List<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns;
	
	public GenericJavaOneToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.primaryKeyJoinColumns = new ArrayList<JavaPrimaryKeyJoinColumn>();
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
	
	
	public ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return new CloneListIterator<JavaPrimaryKeyJoinColumn>(this.primaryKeyJoinColumns);
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumns.size();
	}
	
	public JavaPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn pkJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, createJoinColumnOwner());
		this.primaryKeyJoinColumns.add(index, pkJoinColumn);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = (PrimaryKeyJoinColumnAnnotation) getResourcePersistentAttribute().addAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		pkJoinColumn.initialize(pkJoinColumnResource);
		this.fireItemAdded(OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST, index, pkJoinColumn);
		return pkJoinColumn;
	}

	protected void addPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.primaryKeyJoinColumns, OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST);
	}

	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumns.indexOf(pkJoinColumn));
	}
	
	public void removePrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn removedPkJoinColumn = this.primaryKeyJoinColumns.remove(index);
		getResourcePersistentAttribute().removeAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		fireItemRemoved(OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST, index, removedPkJoinColumn);
	}

	protected void removePrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.primaryKeyJoinColumns, OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.primaryKeyJoinColumns, targetIndex, sourceIndex);
		getResourcePersistentAttribute().move(targetIndex, sourceIndex, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		fireItemMoved(OneToOneMapping.PRIMAY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	public boolean containsPrimaryKeyJoinColumns() {
		return !this.primaryKeyJoinColumns.isEmpty();
	}
	
	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		this.getResourceMapping().setMappedBy(newMappedBy);
		firePropertyChanged(NonOwningMapping.MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}

	protected void setMappedBy_(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		firePropertyChanged(NonOwningMapping.MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}

	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	@Override
	protected void setOptionalOnResourceModel(Boolean newOptional) {
		this.getResourceMapping().setOptional(newOptional);
	}
	
	public TextRange getMappedByTextRange(CompilationUnit astRoot) {
		return this.getResourceMapping().getMappedByTextRange(astRoot);
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceMapping().mappedByTouches(pos, astRoot);
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
	protected void initialize(OneToOneAnnotation resourceOneToOne) {
		super.initialize(resourceOneToOne);
		this.mappedBy = resourceOneToOne.getMappedBy();
	}

	@Override
	protected Boolean specifiedOptional(OneToOneAnnotation oneToOneResource) {
		return oneToOneResource.getOptional();
	}
	
	
	@Override
	public void initialize(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super.initialize(resourcePersistentAttribute);
		this.initializePrimaryKeyJoinColumns(resourcePersistentAttribute);
	}
	
	protected void initializePrimaryKeyJoinColumns(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		ListIterator<JavaResourceNode> annotations = resourcePersistentAttribute.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.primaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) annotations.next()));
		}
	}

	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation primaryKeyJoinColumnResource) {
		JavaPrimaryKeyJoinColumn pkJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, createJoinColumnOwner());
		pkJoinColumn.initialize(primaryKeyJoinColumnResource);
		return pkJoinColumn;
	}
	
	@Override
	protected void update(OneToOneAnnotation resourceOneToOne) {
		super.update(resourceOneToOne);
		this.setMappedBy_(resourceOneToOne.getMappedBy());
	}

	@Override
	public void update(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super.update(resourcePersistentAttribute);
		this.updatePrimaryKeyJoinColumns(resourcePersistentAttribute);
	}
	
	protected void updatePrimaryKeyJoinColumns(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		ListIterator<JavaPrimaryKeyJoinColumn> pkJoinColumns = primaryKeyJoinColumns();
		ListIterator<JavaResourceNode> resourcePkJoinColumns = resourcePersistentAttribute.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		while (pkJoinColumns.hasNext()) {
			JavaPrimaryKeyJoinColumn pkJoinColumn = pkJoinColumns.next();
			if (resourcePkJoinColumns.hasNext()) {
				pkJoinColumn.update((PrimaryKeyJoinColumnAnnotation) resourcePkJoinColumns.next());
			}
			else {
				removePrimaryKeyJoinColumn_(pkJoinColumn);
			}
		}
		
		while (resourcePkJoinColumns.hasNext()) {
			addPrimaryKeyJoinColumn(specifiedJoinColumnsSize(), buildPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) resourcePkJoinColumns.next()));
		}
	}
	


	//***************** Validation ***********************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		if (this.getMappedBy() != null) {
			addMappedByMessages(messages ,astRoot);
		}
	}
	
	@Override
	protected boolean addJoinColumnMessages() {
		if (containsPrimaryKeyJoinColumns() && !containsSpecifiedJoinColumns()) {
			return false;
		}
		return super.addJoinColumnMessages();
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

}
