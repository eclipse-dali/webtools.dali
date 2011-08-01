/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>org.eclipse.persistence.annotations.ObjectTypeConverter</code>
 */
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

	protected final ContextListContainer<JavaEclipseLinkConversionValue, EclipseLinkConversionValueAnnotation> conversionValueContainer;

	private String defaultObjectValue;


	public JavaEclipseLinkObjectTypeConverter(JavaJpaContextNode parent, EclipseLinkObjectTypeConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation);
		this.dataType = converterAnnotation.getDataType();
		this.objectType = converterAnnotation.getObjectType();
		this.conversionValueContainer = this.buildConversionValueContainer();
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
		this.updateConversionValues();
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

	public ListIterable<JavaEclipseLinkConversionValue> getConversionValues() {
		return this.conversionValueContainer.getContextElements();
	}

	public int getConversionValuesSize() {
		return this.conversionValueContainer.getContextElementsSize();
	}

	public JavaEclipseLinkConversionValue addConversionValue() {
		return this.addConversionValue(this.getConversionValuesSize());
	}

	public JavaEclipseLinkConversionValue addConversionValue(int index) {
		EclipseLinkConversionValueAnnotation annotation = this.converterAnnotation.addConversionValue(index);
		return this.conversionValueContainer.addContextElement(index, annotation);
	}
  
	public void removeConversionValue(EclipseLinkConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValueContainer.indexOfContextElement((JavaEclipseLinkConversionValue) conversionValue));
	}

	public void removeConversionValue(int index) {
		this.converterAnnotation.removeConversionValue(index);
		this.conversionValueContainer.removeContextElement(index);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		this.converterAnnotation.moveConversionValue(targetIndex, sourceIndex);
		this.conversionValueContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaEclipseLinkConversionValue buildConversionValue(EclipseLinkConversionValueAnnotation conversionValueAnnotation) {
		return new JavaEclipseLinkConversionValue(this, conversionValueAnnotation);
	}
	
	protected void syncConversionValues() {
		this.conversionValueContainer.synchronizeWithResourceModel();
	}
	
	protected void updateConversionValues() {
		this.conversionValueContainer.update();
	}

	protected ListIterable<EclipseLinkConversionValueAnnotation> getConversionValueAnnotations() {
		return this.converterAnnotation.getConversionValues();
	}

	protected ContextListContainer<JavaEclipseLinkConversionValue, EclipseLinkConversionValueAnnotation> buildConversionValueContainer() {
		return new ConversionValueContainer();
	}

	/**
	 * conversion value container
	 */
	protected class ConversionValueContainer
			extends ContextListContainer<JavaEclipseLinkConversionValue, EclipseLinkConversionValueAnnotation> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return CONVERSION_VALUES_LIST;
		}
		
		@Override
		protected JavaEclipseLinkConversionValue buildContextElement(EclipseLinkConversionValueAnnotation resourceElement) {
			return JavaEclipseLinkObjectTypeConverter.this.buildConversionValue(resourceElement);
		}
		
		@Override
		protected ListIterable<EclipseLinkConversionValueAnnotation> getResourceElements() {
			return JavaEclipseLinkObjectTypeConverter.this.getConversionValueAnnotations();
		}
		
		@Override
		protected EclipseLinkConversionValueAnnotation getResourceElement(JavaEclipseLinkConversionValue contextElement) {
			return contextElement.getConversionValueAnnotation();
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
		return this.getConversionValuesSize();
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
		this.checkForDuplicateDataValues(messages, astRoot);
		for (JavaEclipseLinkConversionValue conversionValue : this.getConversionValues()) {
			conversionValue.validate(messages, reporter, astRoot);
		}
	}

	protected void checkForDuplicateDataValues(List<IMessage> messages, CompilationUnit astRoot) {
		for (ArrayList<JavaEclipseLinkConversionValue> dups : this.mapConversionValuesByDataValue().values()) {
			if (dups.size() > 1) {
				for (JavaEclipseLinkConversionValue dup : dups) {
					messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE,
							new String[] {dup.getDataValue()},
							this,
							dup.getDataValueTextRange(astRoot)
						)
					);
				}
			}
		}
	}

	protected HashMap<String, ArrayList<JavaEclipseLinkConversionValue>> mapConversionValuesByDataValue() {
		HashMap<String, ArrayList<JavaEclipseLinkConversionValue>> map = new HashMap<String, ArrayList<JavaEclipseLinkConversionValue>>(this.getConversionValuesSize());
		for (JavaEclipseLinkConversionValue conversionValue : this.getConversionValues()) {
			String dataValue = conversionValue.getDataValue();
			ArrayList<JavaEclipseLinkConversionValue> list = map.get(dataValue);
			if (list == null) {
				list = new ArrayList<JavaEclipseLinkConversionValue>();
				map.put(dataValue, list);
			}
			list.add(conversionValue);
		}
		return map;
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
