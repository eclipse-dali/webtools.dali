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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.core.internal.jpa1.context.java.NullJavaConverter;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.Association;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.SimpleAssociation;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java ID mapping
 */
public abstract class AbstractJavaIdMapping
	extends AbstractJavaAttributeMapping<IdAnnotation>
	implements JavaIdMapping, IdMapping2_0
{
	protected final JavaColumn column;

	protected final JavaGeneratorContainer generatorContainer;

	protected JavaGeneratedValue generatedValue;

	protected JavaConverter converter;  // never null

	/* 2.0 feature - a relationship may map this id */
	protected boolean mappedByRelationship;


	protected static final JavaConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaTemporalConverter.Adapter.instance(),
	};
	protected static final Iterable<JavaConverter.Adapter> CONVERTER_ADAPTERS = new ArrayIterable<JavaConverter.Adapter>(CONVERTER_ADAPTER_ARRAY);


	protected AbstractJavaIdMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = this.buildColumn();
		this.generatorContainer = this.buildGeneratorContainer();
		this.generatedValue = this.buildGeneratedValue();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.column.synchronizeWithResourceModel();
		this.generatorContainer.synchronizeWithResourceModel();
		this.syncGeneratedValue();
		this.syncConverter();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
		this.generatorContainer.update();
		if (this.generatedValue != null) {
			this.generatedValue.update();
		}
		this.converter.update();
		this.setMappedByRelationship(this.buildMappedByRelationship());
	}


	// ********** column **********

	public JavaColumn getColumn() {
		return this.column;
	}

	protected JavaColumn buildColumn() {
		return this.getJpaFactory().buildJavaColumn(this, this);
	}


	// ********** generator container **********

	public JavaGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	private JavaGeneratorContainer buildGeneratorContainer() {
		return this.getJpaFactory().buildJavaGeneratorContainer(this, this);
	}


	// ********** generated value **********

	public JavaGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}

	public JavaGeneratedValue addGeneratedValue() {
		if (this.generatedValue != null) {
			throw new IllegalStateException("generated value already exists: " + this.generatedValue); //$NON-NLS-1$
		}
		GeneratedValueAnnotation annotation = this.buildGeneratedValueAnnotation();
		JavaGeneratedValue value = this.buildGeneratedValue(annotation);
		this.setGeneratedValue(value);
		return value;
	}

	protected GeneratedValueAnnotation buildGeneratedValueAnnotation() {
		return (GeneratedValueAnnotation) this.getResourcePersistentAttribute().addAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}

	public void removeGeneratedValue() {
		if (this.generatedValue == null) {
			throw new IllegalStateException("generated value does not exist"); //$NON-NLS-1$
		}
		this.getResourcePersistentAttribute().removeAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		this.setGeneratedValue(null);
	}

	protected JavaGeneratedValue buildGeneratedValue() {
		GeneratedValueAnnotation annotation = this.getGeneratedValueAnnotation();
		return (annotation == null) ? null : this.buildGeneratedValue(annotation);
	}

	protected GeneratedValueAnnotation getGeneratedValueAnnotation() {
		return (GeneratedValueAnnotation) this.getResourcePersistentAttribute().getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}

	protected JavaGeneratedValue buildGeneratedValue(GeneratedValueAnnotation generatedValueAnnotation) {
		return this.getJpaFactory().buildJavaGeneratedValue(this, generatedValueAnnotation);
	}

	protected void syncGeneratedValue() {
		GeneratedValueAnnotation annotation = this.getGeneratedValueAnnotation();
		if (annotation == null) {
			if (this.generatedValue != null) {
				this.setGeneratedValue(null);
			}
		}
		else {
			if ((this.generatedValue != null) && (this.generatedValue.getGeneratedValueAnnotation() == annotation)) {
				this.generatedValue.synchronizeWithResourceModel();
			} else {
				this.setGeneratedValue(this.buildGeneratedValue(annotation));
			}
		}
	}

	protected void setGeneratedValue(JavaGeneratedValue value) {
		JavaGeneratedValue old = this.generatedValue;
		this.generatedValue = value;
		this.firePropertyChanged(GENERATED_VALUE_PROPERTY, old, value);
	}


	// ********** converter **********

	public JavaConverter getConverter() {
		return this.converter;
	}

	public void setConverter(Class<? extends Converter> converterType) {
		if (this.converter.getType() != converterType) {
			this.converter.dispose();
			JavaConverter.Adapter converterAdapter = this.getConverterAdapter(converterType);
			this.retainConverterAnnotation(converterAdapter);
			this.setConverter_(this.buildConverter(converterAdapter));
		}
	}

	protected JavaConverter buildConverter(JavaConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getJpaFactory()) :
				this.buildNullConverter();
	}

	protected void setConverter_(JavaConverter converter) {
		Converter old = this.converter;
		this.converter = converter;
		this.firePropertyChanged(CONVERTER_PROPERTY, old, converter);
	}

	/**
	 * Clear all the converter annotations <em>except</em> for the annotation
	 * corresponding to the specified adapter. If the specified adapter is
	 * <code>null</code>, remove <em>all</em> the converter annotations.
	 */
	protected void retainConverterAnnotation(JavaConverter.Adapter converterAdapter) {
		JavaResourcePersistentAttribute resourceAttribute = this.getResourcePersistentAttribute();
		for (JavaConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter != converterAdapter) {
				adapter.removeConverterAnnotation(resourceAttribute);
			}
		}
	}

	protected JavaConverter buildConverter() {
		JpaFactory jpaFactory = this.getJpaFactory();
		for (JavaConverter.Adapter adapter : this.getConverterAdapters()) {
			JavaConverter javaConverter = adapter.buildConverter(this, jpaFactory);
			if (javaConverter != null) {
				return javaConverter;
			}
		}
		return this.buildNullConverter();
	}

	protected void syncConverter() {
		Association<JavaConverter.Adapter, Annotation> assoc = this.getConverterAnnotation();
		if (assoc == null) {
			if (this.converter.getType() != null) {
				this.setConverter_(this.buildNullConverter());
			}
		} else {
			JavaConverter.Adapter adapter = assoc.getKey();
			Annotation annotation = assoc.getValue();
			if ((this.converter.getType() == adapter.getConverterType()) &&
					(this.converter.getConverterAnnotation() == annotation)) {
				this.converter.synchronizeWithResourceModel();
			} else {
				this.setConverter_(adapter.buildConverter(annotation, this, this.getJpaFactory()));
			}
		}
	}

	/**
	 * Return the first converter annotation we find along with its corresponding
	 * adapter. Return <code>null</code> if there are no converter annotations.
	 */
	protected Association<JavaConverter.Adapter, Annotation> getConverterAnnotation() {
		JavaResourcePersistentAttribute resourceAttribute = this.getResourcePersistentAttribute();
		for (JavaConverter.Adapter adapter : this.getConverterAdapters()) {
			Annotation annotation = adapter.getConverterAnnotation(resourceAttribute);
			if (annotation != null) {
				return new SimpleAssociation<JavaConverter.Adapter, Annotation>(adapter, annotation);
			}
		}
		return null;
	}

	protected JavaConverter buildNullConverter() {
		return new NullJavaConverter(this);
	}


	// ********** converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected JavaConverter.Adapter getConverterAdapter(Class<? extends Converter> converterType) {
		for (JavaConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter.getConverterType() == converterType) {
				return adapter;
			}
		}
		return null;
	}

	protected Iterable<JavaConverter.Adapter> getConverterAdapters() {
		return CONVERTER_ADAPTERS;
	}


	// ********** mapped by relationship **********

	public boolean isMappedByRelationship() {
		return this.mappedByRelationship;
	}

	protected void setMappedByRelationship(boolean mappedByRelationship) {
		boolean old = this.mappedByRelationship;
		this.mappedByRelationship = mappedByRelationship;
		this.firePropertyChanged(MAPPED_BY_RELATIONSHIP_PROPERTY, old, mappedByRelationship);
	}

	protected boolean buildMappedByRelationship() {
		return this.isJpa2_0Compatible() && this.buildMappedByRelationship_();
	}

	protected boolean buildMappedByRelationship_() {
		return CollectionTools.contains(this.getMappedByRelationshipAttributeNames(), this.getName());
	}

	protected Iterable<String> getMappedByRelationshipAttributeNames() {
		return TypeMappingTools.getMappedByRelationshipAttributeNames(this.getTypeMapping());
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return IdAnnotation.ANNOTATION_NAME;
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return this.column.getName();
	}

	protected boolean columnIsSpecified() {
		return this.getResourcePersistentAttribute().getAnnotation(ColumnAnnotation.ANNOTATION_NAME) != null;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}


	// ********** JavaGeneratorContainer implementation **********

	public JavaResourcePersistentMember getResourceAnnotatedElement() {
		return this.getResourcePersistentAttribute();
	}


	// ********** JavaColumn.Owner implementation **********

	public ColumnAnnotation getColumnAnnotation() {
		return (ColumnAnnotation) this.getResourcePersistentAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	public void removeColumnAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	public String getDefaultColumnName() {
		return (this.mappedByRelationship && ! this.columnIsSpecified()) ? null : this.getName();
	}

	public String getDefaultTableName() {
		return (this.mappedByRelationship && ! this.columnIsSpecified()) ? null : this.getTypeMapping().getPrimaryTableName();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getTypeMapping().tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return this.getTypeMapping().allAssociatedTableNames();
	}

	public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
		return new NamedColumnValidator((BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.column.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.generatorContainer.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		if (this.generatedValue != null) {
			result = this.generatedValue.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}

		result = this.converter.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

		// [JPA 2.0] if the column is specified, or if the id is not mapped by a relationship,
		// then the column is validated.
		// (In JPA 1.0, the column will always be validated, since the id is never mapped by a
		//  relationship)
		if (this.columnIsSpecified() || ! this.mappedByRelationship) {
			this.column.validate(messages, reporter, astRoot);
		}

		// [JPA 2.0] if the column is specified and the id is mapped by a relationship,
		// then that is an error
		// (In JPA 1.0, this will never be the case, since the id is never mapped by a relationship)
		if (this.columnIsSpecified() && this.mappedByRelationship) {
			messages.add(
				this.buildMessage(
					JpaValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED,
					EMPTY_STRING_ARRAY,
					this.column.getValidationTextRange(astRoot)
				)
			);
		}

		this.generatorContainer.validate(messages, reporter, astRoot);
		if (this.generatedValue != null) {
			this.generatedValue.validate(messages, reporter, astRoot);
		}
		this.converter.validate(messages, reporter, astRoot);
	}

	protected IMessage buildMessage(String msgID, String[] parms, TextRange textRange) {
		String attributeDescription = NLS.bind(JpaValidationDescriptionMessages.ATTRIBUTE_DESC, this.getPersistentAttribute().getName());
		parms = ArrayTools.add(parms, 0, attributeDescription);
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				parms,
				this,
				textRange
			);
	}
}
