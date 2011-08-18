/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.Association;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaConverter;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java version mapping
 */
public abstract class AbstractJavaVersionMapping
	extends AbstractJavaAttributeMapping<VersionAnnotation>
	implements JavaVersionMapping
{
	protected final JavaColumn column;

	protected JavaConverter converter;  // never null


	// the spec does not list Temporal explicitly,
	// but it is included in the orm.xml schema...
	protected static final JavaConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaTemporalConverter.BasicAdapter.instance(),
	};
	protected static final Iterable<JavaConverter.Adapter> CONVERTER_ADAPTERS = new ArrayIterable<JavaConverter.Adapter>(CONVERTER_ADAPTER_ARRAY);


	protected AbstractJavaVersionMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = this.buildColumn();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.column.synchronizeWithResourceModel();
		this.syncConverter();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
		this.converter.update();
	}


	// ********** column **********

	public JavaColumn getColumn() {
		return this.column;
	}

	protected JavaColumn buildColumn() {
		return this.getJpaFactory().buildJavaColumn(this, this);
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


	// ********** misc **********

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return VersionAnnotation.ANNOTATION_NAME;
	}


	// ********** JavaColumn.Owner implementation **********

	public ColumnAnnotation getColumnAnnotation() {
		return (ColumnAnnotation) this.getResourceAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	public void removeColumnAnnotation() {
		this.getResourceAttribute().removeAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	public String getDefaultColumnName(ReadOnlyNamedColumn column) {
		return this.getName();
	}

	public String getDefaultTableName() {
		return this.getTypeMapping().getPrimaryTableName();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getTypeMapping().tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.getTypeMapping().getAllAssociatedTableNames();
	}

	public JptValidator buildColumnValidator(ReadOnlyNamedColumn col, NamedColumnTextRangeResolver textRangeResolver) {
		return new NamedColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseColumn) col, (BaseColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.column.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.converter.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.column.validate(messages, reporter, astRoot);
		this.converter.validate(messages, reporter, astRoot);
		this.validateAttributeType(messages, astRoot);
	}
	
	protected void validateAttributeType(List<IMessage> messages, CompilationUnit astRoot) {
		if (!ArrayTools.contains(SUPPORTED_TYPE_NAMES, this.getPersistentAttribute().getTypeName())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.NORMAL_SEVERITY,
							JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_VERSION_MAPPING_TYPE,
							new String[] {this.getName()},
							this,
							this.getValidationTextRange(astRoot)
					)
			);
		}
	}
}
