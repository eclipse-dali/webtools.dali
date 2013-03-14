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
	protected final ContextListContainer<EclipseLinkJavaObjectTypeConverter, ObjectTypeConverterAnnotation> objectTypeConverterContainer;
	protected final ContextListContainer<EclipseLinkJavaStructConverter, StructConverterAnnotation> structConverterContainer;
	protected final ContextListContainer<EclipseLinkJavaTypeConverter, TypeConverterAnnotation> typeConverterContainer;


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
		return this.customConverterContainer;
	}
	
	public int getCustomConvertersSize() {
		return this.customConverterContainer.size();
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
		this.removeCustomConverter(this.customConverterContainer.indexOf((EclipseLinkJavaCustomConverter) customConverter));
	}

	public void removeCustomConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, ConverterAnnotation.ANNOTATION_NAME);
		this.customConverterContainer.remove(index);
	}

	public void moveCustomConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, ConverterAnnotation.ANNOTATION_NAME);
		this.customConverterContainer.move(targetIndex, sourceIndex);
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
		return this.buildSpecifiedContextListContainer(CUSTOM_CONVERTERS_LIST, new CustomConverterContainerAdapter());
	}

	/**
	 * custom converter container adapter
	 */
	public class CustomConverterContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkJavaCustomConverter, ConverterAnnotation>
	{
		public EclipseLinkJavaCustomConverter buildContextElement(ConverterAnnotation resourceElement) {
			return EclipseLinkJavaConverterContainerImpl.this.buildCustomConverter(resourceElement);
		}
		public ListIterable<ConverterAnnotation> getResourceElements() {
			return EclipseLinkJavaConverterContainerImpl.this.getCustomConverterAnnotations();
		}
		public ConverterAnnotation extractResourceElement(EclipseLinkJavaCustomConverter contextElement) {
			return contextElement.getConverterAnnotation();
		}
	}

	// ********** object type converters **********

	public ListIterable<EclipseLinkJavaObjectTypeConverter> getObjectTypeConverters() {
		return this.objectTypeConverterContainer;
	}
	
	public int getObjectTypeConvertersSize() {
		return this.objectTypeConverterContainer.size();
	}

	public EclipseLinkJavaObjectTypeConverter addObjectTypeConverter(String name) {
		return this.addObjectTypeConverter(name, this.getObjectTypeConvertersSize());
	}

	public EclipseLinkJavaObjectTypeConverter addObjectTypeConverter(String name, int index) {
		ObjectTypeConverterAnnotation annotation = this.addObjectTypeConverterAnnotation(name, index);
		return this.objectTypeConverterContainer.addContextElement(index, annotation);
	}

	protected ObjectTypeConverterAnnotation addObjectTypeConverterAnnotation(String name, int index) {
		ObjectTypeConverterAnnotation annotation =  (ObjectTypeConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeObjectTypeConverter(EclipseLinkObjectTypeConverter objectTypeConverter) {
		this.removeObjectTypeConverter(this.objectTypeConverterContainer.indexOf((EclipseLinkJavaObjectTypeConverter) objectTypeConverter));
	}

	public void removeObjectTypeConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		this.objectTypeConverterContainer.remove(index);
	}

	public void moveObjectTypeConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		this.objectTypeConverterContainer.move(targetIndex, sourceIndex);
	}

	protected EclipseLinkJavaObjectTypeConverter buildObjectTypeConverter(ObjectTypeConverterAnnotation converterAnnotation) {
		return new EclipseLinkJavaObjectTypeConverter(this, converterAnnotation);
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

	protected ContextListContainer<EclipseLinkJavaObjectTypeConverter, ObjectTypeConverterAnnotation> buildObjectTypeConverterContainer() {
		return this.buildSpecifiedContextListContainer(OBJECT_TYPE_CONVERTERS_LIST, new ObjectTypeConverterContainerAdapter());
	}

	/**
	 * object type converter container adapter
	 */
	public class ObjectTypeConverterContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkJavaObjectTypeConverter, ObjectTypeConverterAnnotation>
	{
		public EclipseLinkJavaObjectTypeConverter buildContextElement(ObjectTypeConverterAnnotation resourceElement) {
			return EclipseLinkJavaConverterContainerImpl.this.buildObjectTypeConverter(resourceElement);
		}
		public ListIterable<ObjectTypeConverterAnnotation> getResourceElements() {
			return EclipseLinkJavaConverterContainerImpl.this.getObjectTypeConverterAnnotations();
		}
		public ObjectTypeConverterAnnotation extractResourceElement(EclipseLinkJavaObjectTypeConverter contextElement) {
			return contextElement.getConverterAnnotation();
		}
	}

	// ********** struct converters **********

	public ListIterable<EclipseLinkJavaStructConverter> getStructConverters() {
		return this.structConverterContainer;
	}
	
	public int getStructConvertersSize() {
		return this.structConverterContainer.size();
	}

	public EclipseLinkJavaStructConverter addStructConverter(String name) {
		return this.addStructConverter(name, this.getStructConvertersSize());
	}

	public EclipseLinkJavaStructConverter addStructConverter(String name, int index) {
		StructConverterAnnotation annotation = this.addStructConverterAnnotation(name, index);
		return this.structConverterContainer.addContextElement(index, annotation);
	}

	protected StructConverterAnnotation addStructConverterAnnotation(String name, int index) {
		StructConverterAnnotation annotation = (StructConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, StructConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeStructConverter(EclipseLinkStructConverter structConverter) {
		this.removeStructConverter(this.structConverterContainer.indexOf((EclipseLinkJavaStructConverter) structConverter));
	}

	public void removeStructConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, StructConverterAnnotation.ANNOTATION_NAME);
		this.structConverterContainer.remove(index);
	}

	public void moveStructConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, StructConverterAnnotation.ANNOTATION_NAME);
		this.structConverterContainer.move(targetIndex, sourceIndex);
	}

	protected EclipseLinkJavaStructConverter buildStructConverter(StructConverterAnnotation converterAnnotation) {
		return new EclipseLinkJavaStructConverter(this, converterAnnotation);
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

	protected ContextListContainer<EclipseLinkJavaStructConverter, StructConverterAnnotation> buildStructConverterContainer() {
		return this.buildSpecifiedContextListContainer(STRUCT_CONVERTERS_LIST, new StructConverterContainerAdapter());
	}

	/**
	 * struct converter container adapter
	 */
	public class StructConverterContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkJavaStructConverter, StructConverterAnnotation>
	{
		public EclipseLinkJavaStructConverter buildContextElement(StructConverterAnnotation resourceElement) {
			return EclipseLinkJavaConverterContainerImpl.this.buildStructConverter(resourceElement);
		}
		public ListIterable<StructConverterAnnotation> getResourceElements() {
			return EclipseLinkJavaConverterContainerImpl.this.getStructConverterAnnotations();
		}
		public StructConverterAnnotation extractResourceElement(EclipseLinkJavaStructConverter contextElement) {
			return contextElement.getConverterAnnotation();
		}
	}


	// ********** type converters **********

	public ListIterable<EclipseLinkJavaTypeConverter> getTypeConverters() {
		return this.typeConverterContainer;
	}
	
	public int getTypeConvertersSize() {
		return this.typeConverterContainer.size();
	}

	public EclipseLinkJavaTypeConverter addTypeConverter(String name) {
		return this.addTypeConverter(name, this.getTypeConvertersSize());
	}

	public EclipseLinkJavaTypeConverter addTypeConverter(String name, int index) {
		TypeConverterAnnotation annotation = this.addTypeConverterAnnotation(name, index);
		return this.typeConverterContainer.addContextElement(index, annotation);
	}

	protected TypeConverterAnnotation addTypeConverterAnnotation(String name, int index) {
		TypeConverterAnnotation annotation =  (TypeConverterAnnotation) this.getJavaResourceAnnotatedElement().addAnnotation(index, TypeConverterAnnotation.ANNOTATION_NAME);
		annotation.setName(name);
		return annotation;
	}

	public void removeTypeConverter(EclipseLinkTypeConverter typeConverter) {
		this.removeTypeConverter(this.typeConverterContainer.indexOf((EclipseLinkJavaTypeConverter) typeConverter));
	}

	public void removeTypeConverter(int index) {
		this.getJavaResourceAnnotatedElement().removeAnnotation(index, TypeConverterAnnotation.ANNOTATION_NAME);
		this.typeConverterContainer.remove(index);
	}

	public void moveTypeConverter(int targetIndex, int sourceIndex) {
		this.getJavaResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, TypeConverterAnnotation.ANNOTATION_NAME);
		this.typeConverterContainer.move(targetIndex, sourceIndex);
	}

	protected EclipseLinkJavaTypeConverter buildTypeConverter(TypeConverterAnnotation converterAnnotation) {
		return new EclipseLinkJavaTypeConverter(this, converterAnnotation);
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

	protected ContextListContainer<EclipseLinkJavaTypeConverter, TypeConverterAnnotation> buildTypeConverterContainer() {
		return this.buildSpecifiedContextListContainer(TYPE_CONVERTERS_LIST, new TypeConverterContainerAdapter());
	}

	/**
	 * type converter container adapter
	 */
	public class TypeConverterContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkJavaTypeConverter, TypeConverterAnnotation>
	{
		public EclipseLinkJavaTypeConverter buildContextElement(TypeConverterAnnotation resourceElement) {
			return EclipseLinkJavaConverterContainerImpl.this.buildTypeConverter(resourceElement);
		}
		public ListIterable<TypeConverterAnnotation> getResourceElements() {
			return EclipseLinkJavaConverterContainerImpl.this.getTypeConverterAnnotations();
		}
		public TypeConverterAnnotation extractResourceElement(EclipseLinkJavaTypeConverter contextElement) {
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
