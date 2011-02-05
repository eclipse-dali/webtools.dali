/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.Association;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.internal.jpa1.context.java.AbstractJavaConverter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkConvert
	extends AbstractJavaConverter
	implements EclipseLinkConvert
{
	private final EclipseLinkConvertAnnotation convertAnnotation;

	private String specifiedConverterName;
	private String defaultConverterName;

	private JavaEclipseLinkConverter<?> converter;


	protected static final JavaEclipseLinkConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new JavaEclipseLinkConverter.Adapter[] {
		JavaEclipseLinkCustomConverter.Adapter.instance(),
		JavaEclipseLinkTypeConverter.Adapter.instance(),
		JavaEclipseLinkObjectTypeConverter.Adapter.instance(),
		JavaEclipseLinkStructConverter.Adapter.instance()
	};
	protected static final Iterable<JavaEclipseLinkConverter.Adapter> CONVERTER_ADAPTERS = new ArrayIterable<JavaEclipseLinkConverter.Adapter>(CONVERTER_ADAPTER_ARRAY);
                                                                                                                      

	public JavaEclipseLinkConvert(JavaAttributeMapping parent, EclipseLinkConvertAnnotation convertAnnotation) {
		super(parent);
		this.convertAnnotation = convertAnnotation;
		this.specifiedConverterName = convertAnnotation.getValue();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedConverterName_(this.convertAnnotation.getValue());
		this.syncConverter();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultConverterName(this.buildDefaultConverterName());
		if (this.converter != null) {
			this.converter.update();
		}
	}


	// ********** converter name **********

	public String getConverterName() {
		return (this.specifiedConverterName != null) ? this.specifiedConverterName : this.defaultConverterName;
	}

	public String getSpecifiedConverterName() {
		return this.specifiedConverterName;
	}

	public void setSpecifiedConverterName(String name) {
		this.convertAnnotation.setValue(name);
		this.setSpecifiedConverterName_(name);
	}

	protected void setSpecifiedConverterName_(String name) {
		String old = this.specifiedConverterName;
		this.specifiedConverterName = name;
		this.firePropertyChanged(SPECIFIED_CONVERTER_NAME_PROPERTY, old, name);
	}

	public String getDefaultConverterName() {
		return this.defaultConverterName;
	}

	protected void setDefaultConverterName(String name) {
		String old = this.defaultConverterName;
		this.defaultConverterName = name;
		this.firePropertyChanged(DEFAULT_CONVERTER_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultConverterName() {
		return DEFAULT_CONVERTER_NAME;
	}


	// ********** converter **********

	public JavaEclipseLinkConverter<?> getConverter() {
		return this.converter;
	}

	public void setConverter(Class<? extends EclipseLinkConverter> converterType) {
		if (converterType == null) {
			if (this.converter != null) {
				this.setConverter_(null);
				this.retainConverterAnnotation(null);
			}
		} else {
			if ((this.converter == null) || (this.converter.getType() != converterType)) {
				JavaEclipseLinkConverter.Adapter converterAdapter = this.getConverterAdapter(converterType);
				this.retainConverterAnnotation(converterAdapter);
				this.setConverter_(converterAdapter.buildNewConverter(this.getResourcePersistentAttribute(), this));
			}
		}
	}

	protected void setConverter_(JavaEclipseLinkConverter<?> converter) {
		JavaEclipseLinkConverter<?> old = this.converter;
		this.converter = converter;
		this.firePropertyChanged(CONVERTER_PROPERTY, old, converter);
	}

	protected JavaEclipseLinkConverter<?> buildConverter() {
		JavaResourcePersistentAttribute resourceAttribute = this.getResourcePersistentAttribute();
		for (JavaEclipseLinkConverter.Adapter adapter : this.getConverterAdapters()) {
			JavaEclipseLinkConverter<?> javaConverter = adapter.buildConverter(resourceAttribute, this);
			if (javaConverter != null) {
				return javaConverter;
			}
		}
		return null;
	}

	/**
	 * Clear all the converter annotations <em>except</em> for the annotation
	 * corresponding to the specified adapter. If the specified adapter is
	 * <code>null</code>, remove <em>all</em> the converter annotations.
	 */
	protected void retainConverterAnnotation(JavaEclipseLinkConverter.Adapter converterAdapter) {
		JavaResourcePersistentAttribute resourceAttribute = this.getResourcePersistentAttribute();
		for (JavaEclipseLinkConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter != converterAdapter) {
				adapter.removeConverterAnnotation(resourceAttribute);
			}
		}
	}

	protected void syncConverter() {
		Association<JavaEclipseLinkConverter.Adapter, EclipseLinkNamedConverterAnnotation> assoc = this.getEclipseLinkConverterAnnotation();
		if (assoc == null) {
			if (this.converter != null) {
				this.setConverter_(null);
			}
		} else {
			JavaEclipseLinkConverter.Adapter adapter = assoc.getKey();
			EclipseLinkNamedConverterAnnotation annotation = assoc.getValue();
			if ((this.converter != null) &&
					(this.converter.getType() == adapter.getConverterType()) &&
					(this.converter.getConverterAnnotation() == annotation)) {
				this.converter.synchronizeWithResourceModel();
			} else {
				this.setConverter_(adapter.buildConverter(annotation, this));
			}
		}
	}

	/**
	 * Return the first EclipseLink converter annotation we find along with its
	 * corresponding adapter. Return <code>null</code> if there are no
	 * converter annotations.
	 */
	protected Association<JavaEclipseLinkConverter.Adapter, EclipseLinkNamedConverterAnnotation> getEclipseLinkConverterAnnotation() {
		JavaResourcePersistentAttribute resourceAttribute = this.getResourcePersistentAttribute();
		for (JavaEclipseLinkConverter.Adapter adapter : this.getConverterAdapters()) {
			EclipseLinkNamedConverterAnnotation annotation = adapter.getConverterAnnotation(resourceAttribute);
			if (annotation != null) {
				return new SimpleAssociation<JavaEclipseLinkConverter.Adapter, EclipseLinkNamedConverterAnnotation>(adapter, annotation);
			}
		}
		return null;
	}


	// ********** converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected JavaEclipseLinkConverter.Adapter getConverterAdapter(Class<? extends EclipseLinkConverter> converterType) {
		for (JavaEclipseLinkConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter.getConverterType() == converterType) {
				return adapter;
			}
		}
		throw new IllegalArgumentException("unknown converter type: " + converterType.getName()); //$NON-NLS-1$
	}

	protected Iterable<JavaEclipseLinkConverter.Adapter> getConverterAdapters() {
		return CONVERTER_ADAPTERS;
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return EclipseLinkConvert.class;
	}

	@Override
	protected String getAnnotationName() {
		return EclipseLinkConvertAnnotation.ANNOTATION_NAME;
	}

	@Override
	public void dispose() {
		super.dispose();
		this.setConverter(null);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.convertValueTouches(pos, astRoot)) {
			result = this.javaCandidateConverterNames(filter);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	protected boolean convertValueTouches(int pos, CompilationUnit astRoot) {
		return this.convertAnnotation.valueTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateConverterNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateConverterNames(filter));
	}

	protected Iterator<String> candidateConverterNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.converterNames(), filter);
	}

	protected Iterator<String> converterNames() {
		return this.getEclipseLinkPersistenceUnit().getUniqueConverterNames().iterator();
	}

	protected EclipseLinkPersistenceUnit getEclipseLinkPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) this.getPersistenceUnit();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.converter != null) {
			this.converter.validate(messages, reporter, astRoot);
		}
		this.validateConvertValue(messages, astRoot);
	}
	
	private void validateConvertValue(List<IMessage> messages, CompilationUnit astRoot) {
		String converterName = this.getConverterName();
		if (converterName == null) {
			return;
		}

		for (Iterator<EclipseLinkConverter> converters = this.getEclipseLinkPersistenceUnit().allConverters(); converters.hasNext(); ) {
			if (converterName.equals(converters.next().getName())) {
				return;
			}
		}
		
		if (ArrayTools.contains(RESERVED_CONVERTER_NAMES, converterName)) {
			return;
		}
		
		messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						EclipseLinkJpaValidationMessages.ID_MAPPING_UNRESOLVED_CONVERTER_NAME,
						new String[] {converterName, this.getParent().getName()},
						this.getParent(),
						this.getValidationTextRange(astRoot)
				)
		);	
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.convertAnnotation.getTextRange(astRoot);
	}


	// ********** adapter **********

	public static class Adapter
		extends JavaConverter.AbstractAdapter
	{
		private static final Adapter INSTANCE = new Adapter();
		public static Adapter instance() {
			return INSTANCE;
		}

		private Adapter() {
			super();
		}

		public Class<? extends Converter> getConverterType() {
			return EclipseLinkConvert.class;
		}

		@Override
		protected String getAnnotationName() {
			return EclipseLinkConvertAnnotation.ANNOTATION_NAME;
		}

		public JavaConverter buildConverter(Annotation converterAnnotation, JavaAttributeMapping parent, JpaFactory factory) {
			return new JavaEclipseLinkConvert(parent, (EclipseLinkConvertAnnotation) converterAnnotation);
		}
	}
}
