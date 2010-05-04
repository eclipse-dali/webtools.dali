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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaIdMapping
	extends AbstractJavaAttributeMapping<IdAnnotation>
	implements JavaIdMapping, IdMapping2_0
{
	protected final JavaColumn column;
	
	/* 2.0 feature - a relationship may map this id */
	protected boolean mappedByRelationship;
	
	protected JavaGeneratedValue generatedValue;
	
	protected final JavaGeneratorContainer generatorContainer;
	
	protected JavaConverter converter;
	
	protected final JavaConverter nullConverter;
	
	
	protected AbstractJavaIdMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = this.getJpaFactory().buildJavaColumn(this, this);
		this.generatorContainer = this.buildGeneratorContainer();
		this.nullConverter = getJpaFactory().buildJavaNullConverter(this);
		this.converter = this.nullConverter;
	}
	
	
	@Override
	protected void initialize() {
		super.initialize();
		this.column.initialize(this.getResourceColumn());
		this.mappedByRelationship = calculateMappedByRelationship();
		this.generatorContainer.initialize(this.getResourcePersistentAttribute());
		this.initializeGeneratedValue();
		this.converter = this.buildConverter(this.getResourceConverterType());
	}
	
	protected void initializeGeneratedValue() {
		GeneratedValueAnnotation resourceGeneratedValue = this.getResourceGeneratedValue();
		if (resourceGeneratedValue != null) {
			this.generatedValue = this.buildGeneratedValue(resourceGeneratedValue);
		}
	}
	
	public ColumnAnnotation getResourceColumn() {
		return (ColumnAnnotation) this.getResourcePersistentAttribute().
				getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}
	
	protected boolean isColumnSpecified() {
		return getResourcePersistentAttribute().getAnnotation(ColumnAnnotation.ANNOTATION_NAME) != null;
	}
	
	private JavaGeneratorContainer buildGeneratorContainer() {
		return this.getJpaFactory().buildJavaGeneratorContainer(this);
	}
	
	
	//************** JavaAttributeMapping implementation ***************
	
	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return IdAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.COLUMN);
		names.add(JPA.GENERATED_VALUE);
		names.add(JPA.TEMPORAL);
		names.add(JPA.TABLE_GENERATOR);
		names.add(JPA.SEQUENCE_GENERATOR);
	}
	
	
	//************** NamedColumn.Owner implementation ***************
	
	public String getDefaultColumnName() {
		return (isMappedByRelationship() && ! isColumnSpecified()) ? null : getName();
	}
	
	
	//************** BaseColumn.Owner implementation ***************
	
	public String getDefaultTableName() {
		return (isMappedByRelationship() && ! isColumnSpecified()) ? null : getTypeMapping().getPrimaryTableName();
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return getTypeMapping().tableNameIsInvalid(tableName);
	}
	
	public Iterator<String> candidateTableNames() {
		return getTypeMapping().associatedTableNamesIncludingInherited();
	}
	
	
	//************** IdMapping implementation ***************
	
	public JavaColumn getColumn() {
		return this.column;
	}
	
	public JavaGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists"); //$NON-NLS-1$
		}
		this.generatedValue = getJpaFactory().buildJavaGeneratedValue(this);
		GeneratedValueAnnotation generatedValueResource = 
				(GeneratedValueAnnotation) getResourcePersistentAttribute().
					addAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		this.generatedValue.initialize(generatedValueResource);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed"); //$NON-NLS-1$
		}
		JavaGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = null;
		getResourcePersistentAttribute().removeAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, null);
	}
	
	public JavaGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}
	
	protected void setGeneratedValue(JavaGeneratedValue newGeneratedValue) {
		JavaGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = newGeneratedValue;
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, newGeneratedValue);
	}
	
	public JavaGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}
	
	public JavaConverter getConverter() {
		return this.converter;
	}
	
	protected String getConverterType() {
		return this.converter.getType();
	}
	
	public void setConverter(String converterType) {
		if (this.valuesAreEqual(getConverterType(), converterType)) {
			return;
		}
		JavaConverter oldConverter = this.converter;
		JavaConverter newConverter = buildConverter(converterType);
		this.converter = this.nullConverter;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.converter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(JavaConverter newConverter) {
		JavaConverter oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	@Override
	protected void update() {
		super.update();
		this.column.update(this.getResourceColumn());
		setMappedByRelationship(calculateMappedByRelationship());
		this.generatorContainer.update(this.getResourcePersistentAttribute());
		this.updateGeneratedValue();
		if (this.valuesAreEqual(getResourceConverterType(), getConverterType())) {
			getConverter().update(this.getResourcePersistentAttribute());
		}
		else {
			JavaConverter javaConverter = buildConverter(getResourceConverterType());
			setConverter(javaConverter);
		}
	}
	
	protected void updateGeneratedValue() {
		GeneratedValueAnnotation resourceGeneratedValue = getResourceGeneratedValue();
		if (resourceGeneratedValue == null) {
			if (getGeneratedValue() != null) {
				setGeneratedValue(null);
			}
		}
		else {
			if (getGeneratedValue() == null) {
				setGeneratedValue(buildGeneratedValue(resourceGeneratedValue));
			}
			else {
				getGeneratedValue().update(resourceGeneratedValue);
			}
		}
	}
	
	protected JavaGeneratedValue buildGeneratedValue(GeneratedValueAnnotation resourceGeneratedValue) {
		JavaGeneratedValue gv = getJpaFactory().buildJavaGeneratedValue(this);
		gv.initialize(resourceGeneratedValue);
		return gv;
	}
	
	protected TableGeneratorAnnotation getResourceTableGenerator() {
		return (TableGeneratorAnnotation) this.getResourcePersistentAttribute().
				getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected SequenceGeneratorAnnotation getResourceSequenceGenerator() {
		return (SequenceGeneratorAnnotation) this.getResourcePersistentAttribute().
				getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected GeneratedValueAnnotation getResourceGeneratedValue() {
		return (GeneratedValueAnnotation) this.getResourcePersistentAttribute().
				getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}
	
	protected JavaConverter buildConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.NO_CONVERTER)) {
			return this.nullConverter;		
		}
		if (this.valuesAreEqual(converterType, Converter.TEMPORAL_CONVERTER)) {
			return getJpaFactory().buildJavaTemporalConverter(this, this.getResourcePersistentAttribute());
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.getResourcePersistentAttribute().getAnnotation(TemporalAnnotation.ANNOTATION_NAME) != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		return Converter.NO_CONVERTER;
	}
	
	
	// **************** IdMapping2_0 impl *************************************
	
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
			if (getName().equals(each.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getValue())) {
				return true;
			}
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
	
	
	// ********** code assist **********
	
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
		if (this.generatorTouches(pos, astRoot)) {
			result = this.persistenceGeneratorNames(filter);
			if (result != null) {
				return result;
			}
		}
		result = this.getGeneratorContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = getConverter().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	
	// ********** code assist: generator 
	
	protected boolean generatorTouches(int pos, CompilationUnit astRoot) {
		if (getResourceGeneratedValue() != null) {
			return this.getResourceGeneratedValue().generatorTouches(pos, astRoot);
		}
		return false;
	}
	
	protected Iterator<String> persistenceGeneratorNames() {
		if(this.getPersistenceUnit().generatorsSize() == 0) {
			return EmptyIterator.<String> instance();
		}
		return CollectionTools.iterator(this.getPersistenceUnit().uniqueGeneratorNames());
	}
	
	private Iterator<String> generatorNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.persistenceGeneratorNames(), filter);
	}
	
	protected Iterator<String> persistenceGeneratorNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.generatorNames(filter));
	}
	
	
	// *************************************************************************
	
	@Override
	public String getPrimaryKeyColumnName() {
		return this.getColumn().getName();
	}
	
	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}
	
	
	//*********** Validation ************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		// [JPA 2.0] if the column is specified, or if the id is not mapped by a relationship,
		// then the column is validated.
		// (In JPA 1.0, the column will always be validated, since the id is never mapped by a
		//  relationship)
		if (isColumnSpecified() || ! isMappedByRelationship()) {
			this.getColumn().validate(messages, reporter, astRoot);
		}
		
		// [JPA 2.0] if the column is specified and the id is mapped by a relationship, 
		// then that is an error
		// (In JPA 1.0, this will never be the case, since the id is never mapped by a relationship)
		if (isColumnSpecified() && isMappedByRelationship()) {
			messages.add(
					buildMessage(
						JpaValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED,
						new String[] {},
						getColumn().getValidationTextRange(astRoot)));
		}
		
		if (this.getGeneratedValue() != null) {
			this.getGeneratedValue().validate(messages, reporter, astRoot);
		}
		this.getGeneratorContainer().validate(messages, reporter, astRoot);
		this.getConverter().validate(messages, reporter, astRoot);
	}
	
	public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			JpaValidationMessages.COLUMN_TABLE_NOT_VALID,
			new String[] {
				column.getTable(),
				column.getName(),
				JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY}, 
			column,
			textRange
		);
	}
	
	public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
			new String[] {column.getName(), column.getDbTable().getName()}, 
			column,
			textRange
		);
	}
	
	/* TODO - move to AbstractOrmAttributeMapping? */
	protected IMessage buildMessage(String msgID, String[] params, TextRange textRange) {
		PersistentAttribute attribute = getPersistentAttribute();
		String attributeDesc = NLS.bind(JpaValidationDescriptionMessages.ATTRIBUTE_DESC, attribute.getName());
		
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				ArrayTools.add(params, 0, attributeDesc),
				this,
				textRange);
	}
}
