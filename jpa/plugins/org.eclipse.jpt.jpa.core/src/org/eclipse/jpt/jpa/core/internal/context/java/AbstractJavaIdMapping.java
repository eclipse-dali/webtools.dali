/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaConverter;
import org.eclipse.jpt.jpa.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java ID mapping
 */
public abstract class AbstractJavaIdMapping
	extends AbstractJavaAttributeMapping<IdAnnotation>
	implements JavaIdMapping, IdMapping2_0, JavaGeneratorContainer.Parent
{
	protected final JavaSpecifiedColumn column;

	protected final JavaGeneratorContainer generatorContainer;

	protected JavaGeneratedValue generatedValue;

	protected JavaConverter converter;  // never null

	/* JPA 2.0 - the embedded id may be derived from a relationship */
	protected boolean derived;


	protected static final JavaConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaBaseTemporalConverter.BasicAdapter.instance(),
	};
	protected static final Iterable<JavaConverter.Adapter> CONVERTER_ADAPTERS = IterableTools.iterable(CONVERTER_ADAPTER_ARRAY);


	protected AbstractJavaIdMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.column = this.buildColumn();
		this.generatorContainer = this.buildGeneratorContainer();
		this.generatedValue = this.buildGeneratedValue();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.column.synchronizeWithResourceModel(monitor);
		this.generatorContainer.synchronizeWithResourceModel(monitor);
		this.syncGeneratedValue(monitor);
		this.syncConverter(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.column.update(monitor);
		this.generatorContainer.update(monitor);
		if (this.generatedValue != null) {
			this.generatedValue.update(monitor);
		}
		this.converter.update(monitor);
		this.setDerived(this.buildDerived());
	}


	// ********** column **********

	public JavaSpecifiedColumn getColumn() {
		return this.column;
	}

	protected JavaSpecifiedColumn buildColumn() {
		return this.getJpaFactory().buildJavaColumn(this);
	}


	// ********** generator container **********

	public JavaGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected JavaGeneratorContainer buildGeneratorContainer() {
		return this.getJpaFactory().buildJavaGeneratorContainer(this);
	}

	@Override
	public Iterable<Generator> getGenerators() {
		return this.generatorContainer.getGenerators();
	}


	// ********** generator container parent **********

	public JavaResourceMember getResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean supportsGenerators() {
		return ! this.getPersistentAttribute().isVirtual();
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
		return (GeneratedValueAnnotation) this.getResourceAttribute().addAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}

	public void removeGeneratedValue() {
		if (this.generatedValue == null) {
			throw new IllegalStateException("generated value does not exist"); //$NON-NLS-1$
		}
		this.getResourceAttribute().removeAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		this.setGeneratedValue(null);
	}

	protected JavaGeneratedValue buildGeneratedValue() {
		GeneratedValueAnnotation annotation = this.getGeneratedValueAnnotation();
		return (annotation == null) ? null : this.buildGeneratedValue(annotation);
	}

	protected GeneratedValueAnnotation getGeneratedValueAnnotation() {
		return (GeneratedValueAnnotation) this.getResourceAttribute().getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}

	protected JavaGeneratedValue buildGeneratedValue(GeneratedValueAnnotation generatedValueAnnotation) {
		return this.getJpaFactory().buildJavaGeneratedValue(this, generatedValueAnnotation);
	}

	protected void syncGeneratedValue(IProgressMonitor monitor) {
		GeneratedValueAnnotation annotation = this.getGeneratedValueAnnotation();
		if (annotation == null) {
			if (this.generatedValue != null) {
				this.setGeneratedValue(null);
			}
		}
		else {
			if ((this.generatedValue != null) && (this.generatedValue.getGeneratedValueAnnotation() == annotation)) {
				this.generatedValue.synchronizeWithResourceModel(monitor);
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
		if (this.converter.getConverterType() != converterType) {
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
		JavaResourceAttribute resourceAttribute = this.getResourceAttribute();
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

	protected void syncConverter(IProgressMonitor monitor) {
		Association<JavaConverter.Adapter, Annotation> assoc = this.getConverterAnnotation();
		if (assoc == null) {
			if (this.converter.getConverterType() != null) {
				this.setConverter_(this.buildNullConverter());
			}
		} else {
			JavaConverter.Adapter adapter = assoc.getKey();
			Annotation annotation = assoc.getValue();
			if ((this.converter.getConverterType() == adapter.getConverterType()) &&
					(this.converter.getConverterAnnotation() == annotation)) {
				this.converter.synchronizeWithResourceModel(monitor);
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
		JavaResourceAttribute resourceAttribute = this.getResourceAttribute();
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


	// ********** derived **********

	public boolean isDerived() {
		return this.derived;
	}

	protected void setDerived(boolean derived) {
		boolean old = this.derived;
		this.derived = derived;
		this.firePropertyChanged(DERIVED_PROPERTY, old, derived);
	}

	protected boolean buildDerived() {
		return this.isJpa2_0Compatible() && this.buildDerived_();
	}

	protected boolean buildDerived_() {
		return this.getTypeMapping().attributeIsDerivedId(this.getName());
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
		return this.getResourceAttribute().getAnnotation(ColumnAnnotation.ANNOTATION_NAME) != null;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}


	// ********** column parent adapter **********

	public JpaContextModel getColumnParent() {
		return this;  // no adapter
	}

	public ColumnAnnotation getColumnAnnotation() {
		return (ColumnAnnotation) this.getResourceAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	public void removeColumnAnnotation() {
		this.getResourceAttribute().removeAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	public String getDefaultColumnName(NamedColumn col) {
		return (this.derived && ! this.columnIsSpecified()) ? null : this.getName();
	}

	public String getDefaultTableName() {
		return (this.derived && ! this.columnIsSpecified()) ? null : this.getTypeMapping().getPrimaryTableName();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getTypeMapping().tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.getTypeMapping().getAllAssociatedTableNames();
	}

	public JpaValidator buildColumnValidator(NamedColumn col) {
		return new NamedColumnValidator(this.getPersistentAttribute(), (BaseColumn) col, new EntityTableDescriptionProvider());
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		result = this.column.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		result = this.generatorContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		if (this.generatedValue != null) {
			result = this.generatedValue.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}

		result = this.converter.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		// JPA 2.0: If the column is specified or if the ID is not mapped by a relationship,
		// the column is validated.
		// JPA 1.0: The column is always be validated, since the ID is never mapped by a
		// relationship.
		if (this.columnIsSpecified() || ! this.derived) {
			this.column.validate(messages, reporter);
		}

		// JPA 2.0: If the column is specified and the ID is mapped by a relationship,
		// we have an error.
		// JPA 1.0: The ID cannot be mapped by a relationship.
		if (this.columnIsSpecified() && this.derived) {
			messages.add(this.buildColumnSpecifiedAndDerivedMessage());
		}

		this.generatorContainer.validate(messages, reporter);
		if (this.generatedValue != null) {
			this.generatedValue.validate(messages, reporter);
		}
		this.converter.validate(messages, reporter);
	}

	protected IMessage buildColumnSpecifiedAndDerivedMessage() {
		return this.buildValidationMessage(
				this.getTextRange(),
				JptJpaCoreValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED,
				this.buildAttributeDescription()
			);
	}

	protected String buildAttributeDescription() {
		return NLS.bind(this.getAttributeDescriptionTemplate(), this.getPersistentAttribute().getName());
	}

	protected String getAttributeDescriptionTemplate() {
		return this.getPersistentAttribute().isVirtual() ?
				JptJpaCoreValidationArgumentMessages.VIRTUAL_ATTRIBUTE_DESC :
				JptJpaCoreValidationArgumentMessages.ATTRIBUTE_DESC;
	}

	protected TextRange getTextRange() {
		return this.getPersistentAttribute().isVirtual() ?
				this.getVirtualPersistentAttributeTextRange() :
				this.column.getValidationTextRange() ;		
	}
}
