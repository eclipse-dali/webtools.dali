/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>org.eclipse.persistence.annotations.ObjectTypeConverter</code>
 */
public class EclipseLinkJavaObjectTypeConverter
	extends EclipseLinkJavaConverter<ObjectTypeConverterAnnotation>
	implements EclipseLinkObjectTypeConverter
{
	private String dataType;
	private String fullyQualifiedDataType;

	private String objectType;
	private String fullyQualifiedObjectType;

	protected final ContextListContainer<EclipseLinkJavaConversionValue, ConversionValueAnnotation> conversionValueContainer;

	private String defaultObjectValue;


	public EclipseLinkJavaObjectTypeConverter(EclipseLinkJavaConverterContainer parent, ObjectTypeConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation);
		this.dataType = converterAnnotation.getDataType();
		this.objectType = converterAnnotation.getObjectType();
		this.conversionValueContainer = this.buildConversionValueContainer();
		this.defaultObjectValue = converterAnnotation.getDefaultObjectValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setDataType_(this.converterAnnotation.getDataType());
		this.setObjectType_(this.converterAnnotation.getObjectType());
		this.syncConversionValues(monitor);
		this.setDefaultObjectValue_(this.converterAnnotation.getDefaultObjectValue());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setFullyQualifiedDataType(this.converterAnnotation.getFullyQualifiedDataType());
		this.setFullyQualifiedObjectType(this.converterAnnotation.getFullyQualifiedObjectType());
		this.updateModels(this.getConversionValues(), monitor);
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

	public ListIterable<EclipseLinkJavaConversionValue> getConversionValues() {
		return this.conversionValueContainer;
	}

	public int getConversionValuesSize() {
		return this.conversionValueContainer.size();
	}

	public EclipseLinkConversionValue getConversionValue(int index) {
		return this.conversionValueContainer.get(index);
	}

	public EclipseLinkJavaConversionValue addConversionValue() {
		return this.addConversionValue(this.getConversionValuesSize());
	}

	public EclipseLinkJavaConversionValue addConversionValue(int index) {
		ConversionValueAnnotation annotation = this.converterAnnotation.addConversionValue(index);
		return this.conversionValueContainer.addContextElement(index, annotation);
	}
  
	public void removeConversionValue(EclipseLinkConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValueContainer.indexOf((EclipseLinkJavaConversionValue) conversionValue));
	}

	public void removeConversionValue(int index) {
		this.converterAnnotation.removeConversionValue(index);
		this.conversionValueContainer.remove(index);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		this.converterAnnotation.moveConversionValue(targetIndex, sourceIndex);
		this.conversionValueContainer.move(targetIndex, sourceIndex);
	}

	protected EclipseLinkJavaConversionValue buildConversionValue(ConversionValueAnnotation conversionValueAnnotation) {
		return new EclipseLinkJavaConversionValue(this, conversionValueAnnotation);
	}
	
	protected void syncConversionValues(IProgressMonitor monitor) {
		this.conversionValueContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<ConversionValueAnnotation> getConversionValueAnnotations() {
		return this.converterAnnotation.getConversionValues();
	}

	protected ContextListContainer<EclipseLinkJavaConversionValue, ConversionValueAnnotation> buildConversionValueContainer() {
		return this.buildSpecifiedContextListContainer(CONVERSION_VALUES_LIST, new ConversionValueContainerAdapter());
	}

	/**
	 * conversion value container adapter
	 */
	public class ConversionValueContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkJavaConversionValue, ConversionValueAnnotation>
	{
		public EclipseLinkJavaConversionValue buildContextElement(ConversionValueAnnotation resourceElement) {
			return EclipseLinkJavaObjectTypeConverter.this.buildConversionValue(resourceElement);
		}
		public ListIterable<ConversionValueAnnotation> getResourceElements() {
			return EclipseLinkJavaObjectTypeConverter.this.getConversionValueAnnotations();
		}
		public ConversionValueAnnotation extractResourceElement(EclipseLinkJavaConversionValue contextElement) {
			return contextElement.getConversionValueAnnotation();
		}
	}


	// ********** data values **********

	public Iterable<String> getDataValues() {
		return IterableTools.transform(this.getConversionValues(), EclipseLinkConversionValue.DATA_VALUE_TRANSFORMER);
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

	public Class<EclipseLinkObjectTypeConverter> getConverterType() {
		return EclipseLinkObjectTypeConverter.class;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.checkForDuplicateDataValues(messages);
		for (EclipseLinkJavaConversionValue conversionValue : this.getConversionValues()) {
			conversionValue.validate(messages, reporter);
		}
	}

	protected void checkForDuplicateDataValues(List<IMessage> messages) {
		for (ArrayList<EclipseLinkJavaConversionValue> dups : this.mapConversionValuesByDataValue().values()) {
			if (dups.size() > 1) {
				for (EclipseLinkJavaConversionValue dup : dups) {
					messages.add(
						this.buildValidationMessage(
							dup.getDataValueTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE,
							dup.getDataValue()
						)
					);
				}
			}
		}
	}

	protected HashMap<String, ArrayList<EclipseLinkJavaConversionValue>> mapConversionValuesByDataValue() {
		HashMap<String, ArrayList<EclipseLinkJavaConversionValue>> map = new HashMap<String, ArrayList<EclipseLinkJavaConversionValue>>(this.getConversionValuesSize());
		for (EclipseLinkJavaConversionValue conversionValue : this.getConversionValues()) {
			String dataValue = conversionValue.getDataValue();
			ArrayList<EclipseLinkJavaConversionValue> list = map.get(dataValue);
			if (list == null) {
				list = new ArrayList<EclipseLinkJavaConversionValue>();
				map.put(dataValue, list);
			}
			list.add(conversionValue);
		}
		return map;
	}

	@Override
	protected boolean isEquivalentTo_(EclipseLinkConverter other) {
		return super.isEquivalentTo_(other) &&
				this.isEquivalentTo_((EclipseLinkObjectTypeConverter) other);
	}

	protected boolean isEquivalentTo_(EclipseLinkObjectTypeConverter other) {
		return ObjectTools.equals(this.fullyQualifiedObjectType, other.getFullyQualifiedObjectType()) &&
				ObjectTools.equals(this.fullyQualifiedDataType, other.getFullyQualifiedDataType()) &&
				ObjectTools.equals(this.defaultObjectValue, other.getDefaultObjectValue()) &&
				this.conversionValuesAreEquivalentTo(other);
	}

	protected boolean conversionValuesAreEquivalentTo(EclipseLinkObjectTypeConverter converter) {
		// get fixed lists of the conversion values
		ArrayList<EclipseLinkJavaConversionValue> conversionValues1 = ListTools.arrayList(this.getConversionValues());
		ArrayList<? extends EclipseLinkConversionValue> conversionValues2 = ListTools.arrayList(converter.getConversionValues());
		if (conversionValues1.size() != conversionValues2.size()) {
			return false;
		}
		for (int i = 0; i < conversionValues1.size(); i++) {
			if ( ! conversionValues1.get(i).isEquivalentTo(conversionValues2.get(i))) {
				return false;
			}
		}
		return true;
	}


	// ********** metadata conversion **********

	@Override
	public void convertTo(EclipseLinkOrmConverterContainer ormConverterContainer) {
		ormConverterContainer.addObjectTypeConverter(this.getName()).convertFrom(this);
	}

	@Override
	public void delete() {
		this.parent.removeObjectTypeConverter(this);
	}
}
