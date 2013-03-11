/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaColumnMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaConverter;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkArrayMapping2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ArrayAnnotation2_3;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkArrayMapping2_3
	extends AbstractJavaAttributeMapping<ArrayAnnotation2_3>
	implements 
		EclipseLinkArrayMapping2_3,
		EclipseLinkJavaConvertibleMapping, 
		JavaColumnMapping
{

	protected final JavaSpecifiedColumn column;

	protected JavaConverter converter;  // never null


	protected static final JavaConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaBaseEnumeratedConverter.BasicAdapter.instance(),
		JavaBaseTemporalConverter.BasicAdapter.instance(),
		JavaLobConverter.Adapter.instance(),
		JavaEclipseLinkConvert.Adapter.instance()
	};

	protected static final Iterable<JavaConverter.Adapter> CONVERTER_ADAPTERS = IterableTools.iterable(CONVERTER_ADAPTER_ARRAY);

	protected final EclipseLinkJavaConverterContainer converterContainer;

	public JavaEclipseLinkArrayMapping2_3(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.column = this.buildColumn();
		this.converter = this.buildConverter();
		this.converterContainer = this.buildConverterContainer();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.column.synchronizeWithResourceModel();
		this.syncConverter();
		this.converterContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
		this.converter.update();
		this.converterContainer.update();
	}

	// ********** column **********

	public JavaSpecifiedColumn getColumn() {
		return this.column;
	}

	protected JavaSpecifiedColumn buildColumn() {
		return this.getJpaFactory().buildJavaColumn(this);
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


	// ********** converters **********

	public EclipseLinkJavaConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkJavaConverterContainer buildConverterContainer() {
		return new JavaEclipseLinkConverterContainerImpl(this);
	}

	// ********** converter container parent **********

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean supportsConverters() {
		return ! this.getPersistentAttribute().isVirtual();
	}

	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((SpecifiedPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		//TODO should get this from targetClass, also need metamodel support for OrmEclipseLinkArrayMapping2_3 bug 385638
		String targetTypeName = this.getPersistentAttribute().getMultiReferenceTargetTypeName();
		return (targetTypeName != null) ? targetTypeName : MetamodelField2_0.DEFAULT_TYPE_NAME;
	}


	// ********** misc **********

	public String getKey() {
		return EclipseLinkMappingKeys.ARRAY_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return ArrayAnnotation2_3.ANNOTATION_NAME;
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

	public JptValidator buildColumnValidator(NamedColumn col) {
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
		this.column.validate(messages, reporter);
		this.converter.validate(messages, reporter);
	}
}
