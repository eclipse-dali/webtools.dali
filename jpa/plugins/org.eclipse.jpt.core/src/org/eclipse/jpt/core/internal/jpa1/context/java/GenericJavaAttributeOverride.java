/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOverride;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.AttributeOverride2_0;
import org.eclipse.jpt.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAttributeOverride
	extends AbstractJavaOverride
	implements AttributeOverride2_0, JavaAttributeOverride
{
	protected final JavaColumn column;
	
	/* 2.0 feature - a relationship may map this attribute override */
	protected boolean mappedByRelationship;
	
	
	public GenericJavaAttributeOverride(
			JavaAttributeOverrideContainer parent,
			JavaAttributeOverride.Owner owner) {
		
		super(parent, owner);
		this.column = getJpaFactory().buildJavaColumn(this, this);
	}
	
	
	@Override
	public JavaAttributeOverride setVirtual(boolean virtual) {
		return (JavaAttributeOverride) super.setVirtual(virtual);
	}
	
	@Override
	public AttributeOverrideAnnotation getOverrideAnnotation() {
		return (AttributeOverrideAnnotation) super.getOverrideAnnotation();
	}
	
	@Override
	public JavaAttributeOverride.Owner getOwner() {
		return (JavaAttributeOverride.Owner) super.getOwner();
	}
	
	public ColumnAnnotation getResourceColumn() {
		return this.getOverrideAnnotation().getNonNullColumn();
	}
	
	public ColumnAnnotation getResourceColumnOrNull() {
		return this.getOverrideAnnotation().getColumn();
	}
	
	protected boolean isColumnSpecified() {
		return getResourceColumn() != null;
	}
	
	
	//************* NamedColumn.Owner implementation **************
	
	public TypeMapping getTypeMapping() {
		return this.getOwner().getTypeMapping();
	}
	
	public Table getDbTable(String tableName) {
		return this.getOwner().getDbTable(tableName);
	}
	
	public String getDefaultColumnName() {
		Column column = resolveOverriddenColumn();
		if (column == null) {
			return getName();
		}
		return column.getName();
	}
	
	
	//************* BaseColumn.Owner implementation **************
	
	public String getDefaultTableName() {
		Column column = resolveOverriddenColumn();
		if (column != null) {
			String tableName = column.getSpecifiedTable();
			if (tableName != null) {
				return tableName;
			}
		}
		return getOwner().getDefaultTableName();
	}
	
	protected Column resolveOverriddenColumn() {
		return isVirtual() ? getOwner().resolveOverriddenColumn(getName()) : null;
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return getOwner().tableNameIsInvalid(tableName);
	}
	
	public Iterator<String> candidateTableNames() {
		return getOwner().candidateTableNames();
	}
	
	
	//************* AttributeOverride implementation **************
	
	public JavaColumn getColumn() {
		return this.column;
	}
	
	
	//************* JavaOverride implementation **************
	
	@Override
	protected Iterator<String> candidateNames() {
		return this.getOwner().allOverridableAttributeNames();
	}
	
	
	// **************** AttributeOverride2_0 impl *****************************
	
	public boolean isMappedByRelationship() {
		return this.mappedByRelationship;
	}
	
	protected void setMappedByRelationship(boolean newValue) {
		boolean oldValue = this.mappedByRelationship;
		this.mappedByRelationship = newValue;
		firePropertyChanged(MAPPED_BY_RELATIONSHIP_PROPERTY, oldValue, newValue);
	}
	
	protected boolean calculateMappedByRelationship() {
		for (SingleRelationshipMapping2_0 each : getMapsIdRelationships()) {
			if (getName() == null) {
				return false;
			}
			
			// overrideable names are (usually?) qualified with a container mapping, 
			// which may also be the one mapped by a relationship
			String qualifier = 
					(getName().indexOf('.') > 0) ?
						getName().substring(0, getName().indexOf('.'))
						: getName();
			return qualifier.equals(each.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getValue());
		}
		return false;
	}
	
	protected Iterable<SingleRelationshipMapping2_0> getMapsIdRelationships() {
		return new FilteringIterable<SingleRelationshipMapping2_0>(
				new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(
					new CompositeIterable<AttributeMapping>(
						getTypeMapping().getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
						getTypeMapping().getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)))) {
			@Override
			protected boolean accept(SingleRelationshipMapping2_0 o) {
				return o.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy();
			}
		};
	}
	
	
	//************* java resource model -> java context model **************	
	
	public void initialize(AttributeOverrideAnnotation attributeOverrideResource) {
		super.initialize(attributeOverrideResource);
		this.column.initialize(this.getResourceColumn());
		this.mappedByRelationship = calculateMappedByRelationship();
	}
	
	public void update(AttributeOverrideAnnotation attributeOverrideResource) {
		super.update(attributeOverrideResource);
		this.column.update(this.getResourceColumn());
		setMappedByRelationship(calculateMappedByRelationship());
	}
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		// [JPA 2.0] if the column is specified, or if the override is not mapped by a relationship,
		// then the column is validated.
		// (In JPA 1.0, the column will always be validated, since the override is never mapped by a
		//  relationship)
		if (isColumnSpecified() || ! isMappedByRelationship()) {
			getColumn().validate(messages, reporter, astRoot);
		}
		
		// [JPA 2.0] if the override is mapped by a relationship, then that actually is in itself
		// a validation error
		// (We prevent implied overrides that are mapped by a relationship ... hopefully)
		// (In JPA 1.0, this will never occur)
		if (isMappedByRelationship()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ATTRIBUTE_OVERRIDE_MAPPED_BY_RELATIONSHIP_AND_SPECIFIED,
						new String[] {},
						this,
						getValidationTextRange(astRoot)));
		}
	}
	
	public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
		return getOwner().buildColumnUnresolvedNameMessage(this, column, textRange);
	}
	
	public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
		return getOwner().buildColumnTableNotValidMessage(this, column, textRange);
	}
}
