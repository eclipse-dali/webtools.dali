/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkConverterContainerImpl
	extends AbstractJavaContextModel<EclipseLinkJavaConverterContainer.Parent>
	implements EclipseLinkJavaConverterContainer
{
	protected final ContextListContainer<JavaEclipseLinkCustomConverter, ConverterAnnotation> customConverterContainer;
	protected final ContextListContainer<JavaEclipseLinkObjectTypeConverter, EclipseLinkObjectTypeConverterAnnotation> objectTypeConverterContainer;
	protected final ContextListContainer<JavaEclipseLinkStructConverter, EclipseLinkStructConverterAnnotation> structConverterContainer;
	protected final ContextListContainer<JavaEclipseLinkTypeConverter, EclipseLinkTypeConverterAnnotation> typeConverterContainer;


	public JavaEclipseLinkConverterContainerImpl(Parent parent) {
		super(parent);
		this.customConverterContainer = this.buildCustomConverterContainer();
		this.objectTypeConverterContainer = this.buildObjectTypeConverterContainer();
		this.structConverterContainer = this.buildStructConverterContainer();
		this.typeConverterContainer = this.buildTypeConverterContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncCustomConverters();
		this.syncObjectTypeConverters();
		this.syncStructConverters();
		this.syncTypeConverters();
	}

	@Override
	public void update() {
		super.update();
		this.updateModels(this.getCustomConverters());
		this.updateModels(this.getObjectTypeConverters());
		this.updateModels(this.getStructConverters());
		this.updateModels(this.getTypeConverters());
	}


	// ********** custom converters **********

	public ListIterable<JavaEclipseLinkCustomConverter> getCustomConverters() {
		return this.customConverterContainer.getContextElements();
	}
	
	public int getCustomConvertersSize() {
		return this.customConverterContainer.getContextElementsSize();
	}

	public JavaEclipseLinkCustomConverter addCustomConverter(String name) {
		return this.addCustomConverter(name, this.getCustomConvertersSize());
	}

	public JavaEclipseLinkCustomConverter addCustomConverter(String name, int index) {
		ConverterAnnotation annotation = this.addCustomConverterAnnotation(name, index);
		return this.customConverterContainer.addContextElement(index, annotation);
	}

	protected ConverterAnnotation addCustomConverterAnnotation(String name, int index) {
		ConverterAnnotation annotation = (ConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, ConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeCustomConverter(EclipseLinkCustomConverter customConverter) {
		this.removeCustomConverter(this.customConverterContainer.indexOfContextElement((JavaEclipseLinkCustomConverter) customConverter));
	}

	public void removeCustomConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, ConverterAnnotation.ANNOTATION_NAME);
		this.customConverterContainer.removeContextElement(index);
	}

	public void moveCustomConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, ConverterAnnotation.ANNOTATION_NAME);
		this.customConverterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaEclipseLinkCustomConverter buildCustomConverter(ConverterAnnotation converterAnnotation) {
		return new JavaEclipseLinkCustomConverter(this, converterAnnotation);
	}

	protected void syncCustomConverters() {
		this.customConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<ConverterAnnotation> getCustomConverterAnnotations() {
		return this.parent.supportsConverters() ?
				new SubListIterableWrapper<NestableAnnotation, ConverterAnnotation>(this.getNestableCustomConverterAnnotations_()) :
				EmptyListIterable.<ConverterAnnotation>instance();
	}

	protected ListIterable<NestableAnnotation> getNestableCustomConverterAnnotations_() {
		return this.getJavaResourceAnnotatedElement().getAnnotations(ConverterAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaEclipseLinkCustomConverter, ConverterAnnotation> buildCustomConverterContainer() {
		CustomConverterContainer container = new CustomConverterContainer();
		container.initialize();
		return container;
	}

	/**
	 * custom converter container
	 */
	protected class CustomConverterContainer
		extends ContextListContainer<JavaEclipseLinkCustomConverter, ConverterAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return CUSTOM_CONVERTERS_LIST;
		}
		@Override
		protected JavaEclipseLinkCustomConverter buildContextElement(ConverterAnnotation resourceElement) {
			return JavaEclipseLinkConverterContainerImpl.this.buildCustomConverter(resourceElement);
		}
		@Override
		protected ListIterable<ConverterAnnotation> getResourceElements() {
			return JavaEclipseLinkConverterContainerImpl.this.getCustomConverterAnnotations();
		}
		@Override
		protected ConverterAnnotation getResourceElement(JavaEclipseLinkCustomConverter contextElement) {
			return contextElement.getConverterAnnotation();
		}
	}

	// ********** object type converters **********

	public ListIterable<JavaEclipseLinkObjectTypeConverter> getObjectTypeConverters() {
		return this.objectTypeConverterContainer.getContextElements();
	}
	
	public int getObjectTypeConvertersSize() {
		return this.objectTypeConverterContainer.getContextElementsSize();
	}

	public JavaEclipseLinkObjectTypeConverter addObjectTypeConverter(String name) {
		return this.addObjectTypeConverter(name, this.getObjectTypeConvertersSize());
	}

	public JavaEclipseLinkObjectTypeConverter addObjectTypeConverter(String name, int index) {
		EclipseLinkObjectTypeConverterAnnotation annotation = this.addObjectTypeConverterAnnotation(name, index);
		return this.objectTypeConverterContainer.addContextElement(index, annotation);
	}

	protected EclipseLinkObjectTypeConverterAnnotation addObjectTypeConverterAnnotation(String name, int index) {
		EclipseLinkObjectTypeConverterAnnotation annotation =  (EclipseLinkObjectTypeConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeObjectTypeConverter(EclipseLinkObjectTypeConverter objectTypeConverter) {
		this.removeObjectTypeConverter(this.objectTypeConverterContainer.indexOfContextElement((JavaEclipseLinkObjectTypeConverter) objectTypeConverter));
	}

	public void removeObjectTypeConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME);
		this.objectTypeConverterContainer.removeContextElement(index);
	}

	public void moveObjectTypeConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME);
		this.objectTypeConverterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaEclipseLinkObjectTypeConverter buildObjectTypeConverter(EclipseLinkObjectTypeConverterAnnotation converterAnnotation) {
		return new JavaEclipseLinkObjectTypeConverter(this, converterAnnotation);
	}

	protected void syncObjectTypeConverters() {
		this.objectTypeConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<EclipseLinkObjectTypeConverterAnnotation> getObjectTypeConverterAnnotations() {
		return this.parent.supportsConverters() ? 
				new SubListIterableWrapper<NestableAnnotation, EclipseLinkObjectTypeConverterAnnotation>(this.getNestableObjectTypeConverterAnnotations_()) :
				EmptyListIterable.<EclipseLinkObjectTypeConverterAnnotation>instance();
	}

	protected ListIterable<NestableAnnotation> getNestableObjectTypeConverterAnnotations_() {
		return this.getJavaResourceAnnotatedElement().getAnnotations(EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaEclipseLinkObjectTypeConverter, EclipseLinkObjectTypeConverterAnnotation> buildObjectTypeConverterContainer() {
		ObjectTypeConverterContainer container = new ObjectTypeConverterContainer();
		container.initialize();
		return container;
	}

	/**
	 * objectType converter container
	 */
	protected class ObjectTypeConverterContainer
		extends ContextListContainer<JavaEclipseLinkObjectTypeConverter, EclipseLinkObjectTypeConverterAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return OBJECT_TYPE_CONVERTERS_LIST;
		}
		@Override
		protected JavaEclipseLinkObjectTypeConverter buildContextElement(EclipseLinkObjectTypeConverterAnnotation resourceElement) {
			return JavaEclipseLinkConverterContainerImpl.this.buildObjectTypeConverter(resourceElement);
		}
		@Override
		protected ListIterable<EclipseLinkObjectTypeConverterAnnotation> getResourceElements() {
			return JavaEclipseLinkConverterContainerImpl.this.getObjectTypeConverterAnnotations();
		}
		@Override
		protected EclipseLinkObjectTypeConverterAnnotation getResourceElement(JavaEclipseLinkObjectTypeConverter contextElement) {
			return contextElement.getConverterAnnotation();
		}
	}

	// ********** struct converters **********

	public ListIterable<JavaEclipseLinkStructConverter> getStructConverters() {
		return this.structConverterContainer.getContextElements();
	}
	
	public int getStructConvertersSize() {
		return this.structConverterContainer.getContextElementsSize();
	}

	public JavaEclipseLinkStructConverter addStructConverter(String name) {
		return this.addStructConverter(name, this.getStructConvertersSize());
	}

	public JavaEclipseLinkStructConverter addStructConverter(String name, int index) {
		EclipseLinkStructConverterAnnotation annotation = this.addStructConverterAnnotation(name, index);
		return this.structConverterContainer.addContextElement(index, annotation);
	}

	protected EclipseLinkStructConverterAnnotation addStructConverterAnnotation(String name, int index) {
		EclipseLinkStructConverterAnnotation annotation = (EclipseLinkStructConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeStructConverter(EclipseLinkStructConverter structConverter) {
		this.removeStructConverter(this.structConverterContainer.indexOfContextElement((JavaEclipseLinkStructConverter) structConverter));
	}

	public void removeStructConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);
		this.structConverterContainer.removeContextElement(index);
	}

	public void moveStructConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);
		this.structConverterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaEclipseLinkStructConverter buildStructConverter(EclipseLinkStructConverterAnnotation converterAnnotation) {
		return new JavaEclipseLinkStructConverter(this, converterAnnotation);
	}

	protected void syncStructConverters() {
		this.structConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<EclipseLinkStructConverterAnnotation> getStructConverterAnnotations() {
		return this.parent.supportsConverters() ?
				new SubListIterableWrapper<NestableAnnotation, EclipseLinkStructConverterAnnotation>(this.getNestableStructConverterAnnotations_()) :
				EmptyListIterable.<EclipseLinkStructConverterAnnotation>instance();
	}

	protected ListIterable<NestableAnnotation> getNestableStructConverterAnnotations_() {
		return this.getJavaResourceAnnotatedElement().getAnnotations(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaEclipseLinkStructConverter, EclipseLinkStructConverterAnnotation> buildStructConverterContainer() {
		StructConverterContainer container = new StructConverterContainer();
		container.initialize();
		return container;
	}

	/**
	 * struct converter container
	 */
	protected class StructConverterContainer
		extends ContextListContainer<JavaEclipseLinkStructConverter, EclipseLinkStructConverterAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return STRUCT_CONVERTERS_LIST;
		}
		@Override
		protected JavaEclipseLinkStructConverter buildContextElement(EclipseLinkStructConverterAnnotation resourceElement) {
			return JavaEclipseLinkConverterContainerImpl.this.buildStructConverter(resourceElement);
		}
		@Override
		protected ListIterable<EclipseLinkStructConverterAnnotation> getResourceElements() {
			return JavaEclipseLinkConverterContainerImpl.this.getStructConverterAnnotations();
		}
		@Override
		protected EclipseLinkStructConverterAnnotation getResourceElement(JavaEclipseLinkStructConverter contextElement) {
			return contextElement.getConverterAnnotation();
		}
	}


	// ********** type converters **********

	public ListIterable<JavaEclipseLinkTypeConverter> getTypeConverters() {
		return this.typeConverterContainer.getContextElements();
	}
	
	public int getTypeConvertersSize() {
		return this.typeConverterContainer.getContextElementsSize();
	}

	public JavaEclipseLinkTypeConverter addTypeConverter(String name) {
		return this.addTypeConverter(name, this.getTypeConvertersSize());
	}

	public JavaEclipseLinkTypeConverter addTypeConverter(String name, int index) {
		EclipseLinkTypeConverterAnnotation annotation = this.addTypeConverterAnnotation(name, index);
		return this.typeConverterContainer.addContextElement(index, annotation);
	}

	protected EclipseLinkTypeConverterAnnotation addTypeConverterAnnotation(String name, int index) {
		EclipseLinkTypeConverterAnnotation annotation =  (EclipseLinkTypeConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeTypeConverter(EclipseLinkTypeConverter typeConverter) {
		this.removeTypeConverter(this.typeConverterContainer.indexOfContextElement((JavaEclipseLinkTypeConverter) typeConverter));
	}

	public void removeTypeConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		this.typeConverterContainer.removeContextElement(index);
	}

	public void moveTypeConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		this.typeConverterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaEclipseLinkTypeConverter buildTypeConverter(EclipseLinkTypeConverterAnnotation converterAnnotation) {
		return new JavaEclipseLinkTypeConverter(this, converterAnnotation);
	}

	protected void syncTypeConverters() {
		this.typeConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<EclipseLinkTypeConverterAnnotation> getTypeConverterAnnotations() {
		return this.parent.supportsConverters() ? 
				new SubListIterableWrapper<NestableAnnotation, EclipseLinkTypeConverterAnnotation>(this.getNestableTypeConverterAnnotations_()) :
				EmptyListIterable.<EclipseLinkTypeConverterAnnotation>instance();
	}

	protected ListIterable<NestableAnnotation> getNestableTypeConverterAnnotations_() {
		return this.getJavaResourceAnnotatedElement().getAnnotations(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaEclipseLinkTypeConverter, EclipseLinkTypeConverterAnnotation> buildTypeConverterContainer() {
		TypeConverterContainer container = new TypeConverterContainer();
		container.initialize();
		return container;
	}

	/**
	 * type converter container
	 */
	protected class TypeConverterContainer
		extends ContextListContainer<JavaEclipseLinkTypeConverter, EclipseLinkTypeConverterAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return TYPE_CONVERTERS_LIST;
		}
		@Override
		protected JavaEclipseLinkTypeConverter buildContextElement(EclipseLinkTypeConverterAnnotation resourceElement) {
			return JavaEclipseLinkConverterContainerImpl.this.buildTypeConverter(resourceElement);
		}
		@Override
		protected ListIterable<EclipseLinkTypeConverterAnnotation> getResourceElements() {
			return JavaEclipseLinkConverterContainerImpl.this.getTypeConverterAnnotations();
		}
		@Override
		protected EclipseLinkTypeConverterAnnotation getResourceElement(JavaEclipseLinkTypeConverter contextElement) {
			return contextElement.getConverterAnnotation();
		}
	}


	// ********** misc **********

	protected JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.parent.getJavaResourceAnnotatedElement();
	}

	@SuppressWarnings("unchecked")
	public Iterable<EclipseLinkConverter> getConverters() {
		return IterableTools.<EclipseLinkConverter>concatenate(
					this.getCustomConverters(),
					this.getObjectTypeConverters(),
					this.getStructConverters(),
					this.getTypeConverters()
				);
	}

	public int getConvertersSize() {
		return this.getCustomConvertersSize()
			+ this.getObjectTypeConvertersSize()
			+ this.getStructConvertersSize()
			+ this.getTypeConvertersSize();
	}

	public int getMaximumAllowedConverters() {
		return Integer.MAX_VALUE;
	}


	// ********** validation **********

	/**
	 * The converters are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit#validateConverters(List, IReporter)
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// converters are validated in the persistence unit
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getJavaResourceAnnotatedElement().getTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}
}
