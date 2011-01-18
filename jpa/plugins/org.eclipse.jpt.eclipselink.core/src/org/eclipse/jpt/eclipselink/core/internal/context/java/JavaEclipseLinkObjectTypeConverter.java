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
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkObjectTypeConverter
	extends JavaEclipseLinkConverter<EclipseLinkObjectTypeConverterAnnotation>
	implements EclipseLinkObjectTypeConverter
{
	private String dataType;
	private String fullyQualifiedDataType;
		public static final String FULLY_QUALIFIED_DATA_TYPE_PROPERTY = "fullyQualifiedDataType"; //$NON-NLS-1$

	private String objectType;
	private String fullyQualifiedObjectType;
		public static final String FULLY_QUALIFIED_OBJECT_TYPE_PROPERTY = "fullyQualifiedObjectType"; //$NON-NLS-1$

	private final Vector<JavaEclipseLinkConversionValue> conversionValues = new Vector<JavaEclipseLinkConversionValue>();
	private final ConversionValueContainerAdapter conversionValueContainerAdapter = new ConversionValueContainerAdapter();

	private String defaultObjectValue;


	public JavaEclipseLinkObjectTypeConverter(JavaJpaContextNode parent, EclipseLinkObjectTypeConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation);
		this.dataType = converterAnnotation.getDataType();
		this.objectType = converterAnnotation.getObjectType();
		this.initializeConversionValues();
		this.defaultObjectValue = converterAnnotation.getDefaultObjectValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setDataType_(this.converterAnnotation.getDataType());
		this.setObjectType_(this.converterAnnotation.getObjectType());
		this.syncConversionValues();
		this.setDefaultObjectValue_(this.converterAnnotation.getDefaultObjectValue());
	}

	@Override
	public void update() {
		super.update();
		this.setFullyQualifiedDataType(this.converterAnnotation.getFullyQualifiedDataType());
		this.setFullyQualifiedObjectType(this.converterAnnotation.getFullyQualifiedObjectType());
		this.updateNodes(this.getConversionValues());
	}


	// ********** data type **********

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.converterAnnotation.setDataType(dataType);
		this.setDataType_(dataType);
	}

	protected void setDataType_(String dataType) {
		String old = this.dataType;
		this.dataType = dataType;
		this.firePropertyChanged(DATA_TYPE_PROPERTY, old, dataType);
	}


	// ********** fully qualified data type **********

	public String getFullyQualifiedDataType() {
		return this.fullyQualifiedDataType;
	}

	protected void setFullyQualifiedDataType(String dataType) {
		String old = this.fullyQualifiedDataType;
		this.fullyQualifiedDataType = dataType;
		this.firePropertyChanged(FULLY_QUALIFIED_DATA_TYPE_PROPERTY, old, dataType);
	}


	// ********** object type **********

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.converterAnnotation.setObjectType(objectType);
		this.setObjectType_(objectType);
	}

	protected void setObjectType_(String objectType) {
		String old = this.objectType;
		this.objectType = objectType;
		this.firePropertyChanged(OBJECT_TYPE_PROPERTY, old, objectType);
	}


	// ********** fully qualified object type **********

	public String getFullyQualifiedObjectType() {
		return this.fullyQualifiedObjectType;
	}

	protected void setFullyQualifiedObjectType(String objectType) {
		String old = this.fullyQualifiedObjectType;
		this.fullyQualifiedObjectType = objectType;
		this.firePropertyChanged(FULLY_QUALIFIED_OBJECT_TYPE_PROPERTY, old, objectType);
	}


	// ********** conversion values **********

	@SuppressWarnings("unchecked")
	public ListIterator<JavaEclipseLinkConversionValue> conversionValues() {
		return this.getConversionValues().iterator();
	}

	public ListIterable<JavaEclipseLinkConversionValue> getConversionValues() {
		return new LiveCloneListIterable<JavaEclipseLinkConversionValue>(this.conversionValues);
	}

	public int conversionValuesSize() {
		return this.conversionValues.size();
	}

	public JavaEclipseLinkConversionValue addConversionValue() {
		return this.addConversionValue(this.conversionValues.size());
	}

	public JavaEclipseLinkConversionValue addConversionValue(int index) {
		EclipseLinkConversionValueAnnotation annotation = this.converterAnnotation.addConversionValue(index);
		return this.addConversionValue_(index, annotation);
	}

	public void removeConversionValue(EclipseLinkConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValues.indexOf(conversionValue));
	}

	public void removeConversionValue(int index) {
		this.converterAnnotation.removeConversionValue(index);
		this.removeConversionValue_(index);
	}

	protected void removeConversionValue_(int index) {
		this.removeItemFromList(index, this.conversionValues, CONVERSION_VALUES_LIST);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		this.converterAnnotation.moveConversionValue(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.conversionValues, CONVERSION_VALUES_LIST);
	}

	protected void initializeConversionValues() {
		for (Iterator<EclipseLinkConversionValueAnnotation> stream = this.converterAnnotation.conversionValues(); stream.hasNext(); ) {
			this.conversionValues.add(this.buildConversionValue(stream.next()));
		}
	}

	protected JavaEclipseLinkConversionValue buildConversionValue(EclipseLinkConversionValueAnnotation conversionValueAnnotation) {
		return new JavaEclipseLinkConversionValue(this, conversionValueAnnotation);
	}
	
	protected void syncConversionValues() {
		ContextContainerTools.synchronizeWithResourceModel(this.conversionValueContainerAdapter);
	}

	protected Iterable<EclipseLinkConversionValueAnnotation> getConversionValueAnnotations() {
		return CollectionTools.iterable(this.converterAnnotation.conversionValues());
	}

	protected void moveConversionValue_(int index, JavaEclipseLinkConversionValue conversionValue) {
		this.moveItemInList(index, conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}

	protected JavaEclipseLinkConversionValue addConversionValue_(int index, EclipseLinkConversionValueAnnotation conversionValueAnnotation) {
		JavaEclipseLinkConversionValue conversionValue = this.buildConversionValue(conversionValueAnnotation);
		this.addItemToList(index, conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
		return conversionValue;
	}

	protected void removeConversionValue_(JavaEclipseLinkConversionValue conversionValue) {
		this.removeConversionValue_(this.conversionValues.indexOf(conversionValue));
	}

	/**
	 * conversion value container adapter
	 */
	protected class ConversionValueContainerAdapter
		implements ContextContainerTools.Adapter<JavaEclipseLinkConversionValue, EclipseLinkConversionValueAnnotation>
	{
		public Iterable<JavaEclipseLinkConversionValue> getContextElements() {
			return JavaEclipseLinkObjectTypeConverter.this.getConversionValues();
		}
		public Iterable<EclipseLinkConversionValueAnnotation> getResourceElements() {
			return JavaEclipseLinkObjectTypeConverter.this.getConversionValueAnnotations();
		}
		public EclipseLinkConversionValueAnnotation getResourceElement(JavaEclipseLinkConversionValue contextElement) {
			return contextElement.getConversionValueAnnotation();
		}
		public void moveContextElement(int index, JavaEclipseLinkConversionValue element) {
			JavaEclipseLinkObjectTypeConverter.this.moveConversionValue_(index, element);
		}
		public void addContextElement(int index, EclipseLinkConversionValueAnnotation resourceElement) {
			JavaEclipseLinkObjectTypeConverter.this.addConversionValue_(index, resourceElement);
		}
		public void removeContextElement(JavaEclipseLinkConversionValue element) {
			JavaEclipseLinkObjectTypeConverter.this.removeConversionValue_(element);
		}
	}


	// ********** data values **********

	public Iterable<String> getDataValues() {
		return new TransformationIterable<JavaEclipseLinkConversionValue, String>(this.getConversionValues()) {
			@Override
			protected String transform(JavaEclipseLinkConversionValue conversionValue) {
				return conversionValue.getDataValue();
			}
		};
	}

	public int getDataValuesSize() {
		return this.conversionValuesSize();
	}


	// ********** default object value **********

	public String getDefaultObjectValue() {
		return this.defaultObjectValue;
	}

	public void setDefaultObjectValue(String value) {
		this.converterAnnotation.setDefaultObjectValue(value);
		this.setDefaultObjectValue_(value);
	}

	protected void setDefaultObjectValue_(String value) {
		String old = this.defaultObjectValue;
		this.defaultObjectValue = value;
		this.firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, old, value);
	}


	// ********** misc **********

	public Class<EclipseLinkObjectTypeConverter> getType() {
		return EclipseLinkObjectTypeConverter.class;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		for (JavaEclipseLinkConversionValue conversionValue : this.getConversionValues()) {
			conversionValue.validate(messages, reporter, astRoot);
		}
	}


	// ********** adapter **********

	public static class Adapter
		extends AbstractAdapter
	{
		private static final Adapter INSTANCE = new Adapter();
		public static Adapter instance() {
			return INSTANCE;
		}

		private Adapter() {
			super();
		}

		public Class<EclipseLinkObjectTypeConverter> getConverterType() {
			return EclipseLinkObjectTypeConverter.class;
		}

		@Override
		protected String getAnnotationName() {
			return EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME;
		}

		public JavaEclipseLinkConverter<? extends EclipseLinkNamedConverterAnnotation> buildConverter(EclipseLinkNamedConverterAnnotation converterAnnotation, JavaJpaContextNode parent) {
			return new JavaEclipseLinkObjectTypeConverter(parent, (EclipseLinkObjectTypeConverterAnnotation) converterAnnotation);
		}
	}
}
