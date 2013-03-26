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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.StructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TypeConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaConverterContainerImpl
	extends AbstractJavaContextModel<EclipseLinkJavaConverterContainer.Parent>
	implements EclipseLinkJavaConverterContainer
{
	protected final ContextListContainer<EclipseLinkJavaCustomConverter, ConverterAnnotation> customConverterContainer;
	protected final ContextListContainer<JavaEclipseLinkObjectTypeConverter, ObjectTypeConverterAnnotation> objectTypeConverterContainer;
	protected final ContextListContainer<JavaEclipseLinkStructConverter, StructConverterAnnotation> structConverterContainer;
	protected final ContextListContainer<JavaEclipseLinkTypeConverter, TypeConverterAnnotation> typeConverterContainer;


	public EclipseLinkJavaConverterContainerImpl(Parent parent) {
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

	public ListIterable<EclipseLinkJavaCustomConverter> getCustomConverters() {
		return this.customConverterContainer.getContextElements();
	}
	
	public int getCustomConvertersSize() {
		return this.customConverterContainer.getContextElementsSize();
	}

	public EclipseLinkJavaCustomConverter addCustomConverter(String name) {
		return this.addCustomConverter(name, this.getCustomConvertersSize());
	}

	public EclipseLinkJavaCustomConverter addCustomConverter(String name, int index) {
		ConverterAnnotation annotation = this.addCustomConverterAnnotation(name, index);
		return this.customConverterContainer.addContextElement(index, annotation);
	}

	protected ConverterAnnotation addCustomConverterAnnotation(String name, int index) {
		ConverterAnnotation annotation = (ConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, ConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeCustomConverter(EclipseLinkCustomConverter customConverter) {
		this.removeCustomConverter(this.customConverterContainer.indexOfContextElement((EclipseLinkJavaCustomConverter) customConverter));
	}

	public void removeCustomConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, ConverterAnnotation.ANNOTATION_NAME);
		this.customConverterContainer.removeContextElement(index);
	}

	public void moveCustomConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, ConverterAnnotation.ANNOTATION_NAME);
		this.customConverterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected EclipseLinkJavaCustomConverter buildCustomConverter(ConverterAnnotation converterAnnotation) {
		return new EclipseLinkJavaCustomConverter(this, converterAnnotation);
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

	protected ContextListContainer<EclipseLinkJavaCustomConverter, ConverterAnnotation> buildCustomConverterContainer() {
		CustomConverterContainer container = new CustomConverterContainer();
		container.initialize();
		return container;
	}

	/**
	 * custom converter container
	 */
	protected class CustomConverterContainer
		extends ContextListContainer<EclipseLinkJavaCustomConverter, ConverterAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return CUSTOM_CONVERTERS_LIST;
		}
		@Override
		protected EclipseLinkJavaCustomConverter buildContextElement(ConverterAnnotation resourceElement) {
			return EclipseLinkJavaConverterContainerImpl.this.buildCustomConverter(resourceElement);
		}
		@Override
		protected ListIterable<ConverterAnnotation> getResourceElements() {
			return EclipseLinkJavaConverterContainerImpl.this.getCustomConverterAnnotations();
		}
		@Override
		protected ConverterAnnotation getResourceElement(EclipseLinkJavaCustomConverter contextElement) {
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
		ObjectTypeConverterAnnotation annotation = this.addObjectTypeConverterAnnotation(name, index);
		return this.objectTypeConverterContainer.addContextElement(index, annotation);
	}

	protected ObjectTypeConverterAnnotation addObjectTypeConverterAnnotation(String name, int index) {
		ObjectTypeConverterAnnotation annotation =  (ObjectTypeConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeObjectTypeConverter(EclipseLinkObjectTypeConverter objectTypeConverter) {
		this.removeObjectTypeConverter(this.objectTypeConverterContainer.indexOfContextElement((JavaEclipseLinkObjectTypeConverter) objectTypeConverter));
	}

	public void removeObjectTypeConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		this.objectTypeConverterContainer.removeContextElement(index);
	}

	public void moveObjectTypeConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		this.objectTypeConverterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaEclipseLinkObjectTypeConverter buildObjectTypeConverter(ObjectTypeConverterAnnotation converterAnnotation) {
		return new JavaEclipseLinkObjectTypeConverter(this, converterAnnotation);
	}

	protected void syncObjectTypeConverters() {
		this.objectTypeConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<ObjectTypeConverterAnnotation> getObjectTypeConverterAnnotations() {
		return this.parent.supportsConverters() ? 
				new SubListIterableWrapper<NestableAnnotation, ObjectTypeConverterAnnotation>(this.getNestableObjectTypeConverterAnnotations_()) :
				EmptyListIterable.<ObjectTypeConverterAnnotation>instance();
	}

	protected ListIterable<NestableAnnotation> getNestableObjectTypeConverterAnnotations_() {
		return this.getJavaResourceAnnotatedElement().getAnnotations(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaEclipseLinkObjectTypeConverter, ObjectTypeConverterAnnotation> buildObjectTypeConverterContainer() {
		ObjectTypeConverterContainer container = new ObjectTypeConverterContainer();
		container.initialize();
		return container;
	}

	/**
	 * objectType converter container
	 */
	protected class ObjectTypeConverterContainer
		extends ContextListContainer<JavaEclipseLinkObjectTypeConverter, ObjectTypeConverterAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return OBJECT_TYPE_CONVERTERS_LIST;
		}
		@Override
		protected JavaEclipseLinkObjectTypeConverter buildContextElement(ObjectTypeConverterAnnotation resourceElement) {
			return EclipseLinkJavaConverterContainerImpl.this.buildObjectTypeConverter(resourceElement);
		}
		@Override
		protected ListIterable<ObjectTypeConverterAnnotation> getResourceElements() {
			return EclipseLinkJavaConverterContainerImpl.this.getObjectTypeConverterAnnotations();
		}
		@Override
		protected ObjectTypeConverterAnnotation getResourceElement(JavaEclipseLinkObjectTypeConverter contextElement) {
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
		StructConverterAnnotation annotation = this.addStructConverterAnnotation(name, index);
		return this.structConverterContainer.addContextElement(index, annotation);
	}

	protected StructConverterAnnotation addStructConverterAnnotation(String name, int index) {
		StructConverterAnnotation annotation = (StructConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, StructConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeStructConverter(EclipseLinkStructConverter structConverter) {
		this.removeStructConverter(this.structConverterContainer.indexOfContextElement((JavaEclipseLinkStructConverter) structConverter));
	}

	public void removeStructConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, StructConverterAnnotation.ANNOTATION_NAME);
		this.structConverterContainer.removeContextElement(index);
	}

	public void moveStructConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, StructConverterAnnotation.ANNOTATION_NAME);
		this.structConverterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaEclipseLinkStructConverter buildStructConverter(StructConverterAnnotation converterAnnotation) {
		return new JavaEclipseLinkStructConverter(this, converterAnnotation);
	}

	protected void syncStructConverters() {
		this.structConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<StructConverterAnnotation> getStructConverterAnnotations() {
		return this.parent.supportsConverters() ?
				new SubListIterableWrapper<NestableAnnotation, StructConverterAnnotation>(this.getNestableStructConverterAnnotations_()) :
				EmptyListIterable.<StructConverterAnnotation>instance();
	}

	protected ListIterable<NestableAnnotation> getNestableStructConverterAnnotations_() {
		return this.getJavaResourceAnnotatedElement().getAnnotations(StructConverterAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaEclipseLinkStructConverter, StructConverterAnnotation> buildStructConverterContainer() {
		StructConverterContainer container = new StructConverterContainer();
		container.initialize();
		return container;
	}

	/**
	 * struct converter container
	 */
	protected class StructConverterContainer
		extends ContextListContainer<JavaEclipseLinkStructConverter, StructConverterAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return STRUCT_CONVERTERS_LIST;
		}
		@Override
		protected JavaEclipseLinkStructConverter buildContextElement(StructConverterAnnotation resourceElement) {
			return EclipseLinkJavaConverterContainerImpl.this.buildStructConverter(resourceElement);
		}
		@Override
		protected ListIterable<StructConverterAnnotation> getResourceElements() {
			return EclipseLinkJavaConverterContainerImpl.this.getStructConverterAnnotations();
		}
		@Override
		protected StructConverterAnnotation getResourceElement(JavaEclipseLinkStructConverter contextElement) {
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
		TypeConverterAnnotation annotation = this.addTypeConverterAnnotation(name, index);
		return this.typeConverterContainer.addContextElement(index, annotation);
	}

	protected TypeConverterAnnotation addTypeConverterAnnotation(String name, int index) {
		TypeConverterAnnotation annotation =  (TypeConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, TypeConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeTypeConverter(EclipseLinkTypeConverter typeConverter) {
		this.removeTypeConverter(this.typeConverterContainer.indexOfContextElement((JavaEclipseLinkTypeConverter) typeConverter));
	}

	public void removeTypeConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, TypeConverterAnnotation.ANNOTATION_NAME);
		this.typeConverterContainer.removeContextElement(index);
	}

	public void moveTypeConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, TypeConverterAnnotation.ANNOTATION_NAME);
		this.typeConverterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaEclipseLinkTypeConverter buildTypeConverter(TypeConverterAnnotation converterAnnotation) {
		return new JavaEclipseLinkTypeConverter(this, converterAnnotation);
	}

	protected void syncTypeConverters() {
		this.typeConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<TypeConverterAnnotation> getTypeConverterAnnotations() {
		return this.parent.supportsConverters() ? 
				new SubListIterableWrapper<NestableAnnotation, TypeConverterAnnotation>(this.getNestableTypeConverterAnnotations_()) :
				EmptyListIterable.<TypeConverterAnnotation>instance();
	}

	protected ListIterable<NestableAnnotation> getNestableTypeConverterAnnotations_() {
		return this.getJavaResourceAnnotatedElement().getAnnotations(TypeConverterAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaEclipseLinkTypeConverter, TypeConverterAnnotation> buildTypeConverterContainer() {
		TypeConverterContainer container = new TypeConverterContainer();
		container.initialize();
		return container;
	}

	/**
	 * type converter container
	 */
	protected class TypeConverterContainer
		extends ContextListContainer<JavaEclipseLinkTypeConverter, TypeConverterAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return TYPE_CONVERTERS_LIST;
		}
		@Override
		protected JavaEclipseLinkTypeConverter buildContextElement(TypeConverterAnnotation resourceElement) {
			return EclipseLinkJavaConverterContainerImpl.this.buildTypeConverter(resourceElement);
		}
		@Override
		protected ListIterable<TypeConverterAnnotation> getResourceElements() {
			return EclipseLinkJavaConverterContainerImpl.this.getTypeConverterAnnotations();
		}
		@Override
		protected TypeConverterAnnotation getResourceElement(JavaEclipseLinkTypeConverter contextElement) {
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
