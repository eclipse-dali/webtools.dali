/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.INonOwningMapping;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.OneToOne;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaOneToOneMapping extends JavaSingleRelationshipMapping<OneToOne>
	implements IJavaOneToOneMapping
{
	protected String mappedBy;
	
	public JavaOneToOneMapping(IJavaPersistentAttribute parent) {
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
	
	public String annotationName() {
		return OneToOne.ANNOTATION_NAME;
	}
	
	public String getKey() {
		return IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected OneToOne relationshipMapping() {
		return (OneToOne) this.persistentAttributeResource.mappingAnnotation();
	}

	
	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = this.mappedBy;
		this.mappedBy = newMappedBy;
		this.relationshipMapping().setMappedBy(newMappedBy);
		firePropertyChanged(INonOwningMapping.MAPPED_BY_PROPERTY, oldMappedBy, newMappedBy);
	}

	public boolean mappedByIsValid(IAttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	@Override
	protected void setOptionalOnResourceModel(Boolean newOptional) {
		this.relationshipMapping().setOptional(newOptional);
	}
	
	public ITextRange mappedByTextRange(CompilationUnit astRoot) {
		return this.relationshipMapping().mappedByTextRange(astRoot);
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.relationshipMapping().mappedByTouches(pos, astRoot);
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
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
	protected void initialize(OneToOne oneToOneResource) {
		super.initialize(oneToOneResource);
		this.mappedBy = oneToOneResource.getMappedBy();
	}

	@Override
	protected void update(OneToOne oneToOneResource) {
		super.initialize(oneToOneResource);
		this.setMappedBy(oneToOneResource.getMappedBy());
	}

	@Override
	protected Boolean specifiedOptional(OneToOne relationshipMapping) {
		return relationshipMapping.getOptional();
	}
	
	//***************** ISingleRelationshipMapping implementation *****************
	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<IJavaJoinColumn> joinColumns() {
		return super.joinColumns();
	}

	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<IJavaJoinColumn> defaultJoinColumns() {
		return super.defaultJoinColumns();
	}

	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<IJavaJoinColumn> specifiedJoinColumns() {
		return super.specifiedJoinColumns();
	}

}
