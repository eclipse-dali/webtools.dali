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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
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

	private String objectType;
	private String fullyQualifiedObjectType;

	protected final ContextListContainer<JavaEclipseLinkConversionValue, EclipseLinkConversionValueAnnotation> conversionValueContainer;

	private String defaultObjectValue;


	public JavaEclipseLinkObjectTypeConverter(JavaEclipseLinkConverterContainer parent, EclipseLinkObjectTypeConverterAnnotation converterAnnotation) {
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
		this.updateModels(this.getConversionValues());
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

	public EclipseLinkConversionValue getConversionValue(int index) {
		return this.conversionValueContainer.get(index);
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

	protected ListIterable<EclipseLinkConversionValueAnnotation> getConversionValueAnnotations() {
		return this.converterAnnotation.getConversionValues();
	}

	protected ContextListContainer<JavaEclipseLinkConversionValue, EclipseLinkConversionValueAnnotation> buildConversionValueContainer() {
		ConversionValueContainer container = new ConversionValueContainer();
		container.initialize();
		return container;
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

	public Class<EclipseLinkObjectTypeConverter> getType() {
		return EclipseLinkObjectTypeConverter.class;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.checkForDuplicateDataValues(messages);
		for (JavaEclipseLinkConversionValue conversionValue : this.getConversionValues()) {
			conversionValue.validate(messages, reporter);
		}
	}

	protected void checkForDuplicateDataValues(List<IMessage> messages) {
		for (ArrayList<JavaEclipseLinkConversionValue> dups : this.mapConversionValuesByDataValue().values()) {
			if (dups.size() > 1) {
				for (JavaEclipseLinkConversionValue dup : dups) {
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

	@Override
	public boolean isEquivalentTo(JpaNamedContextModel node) {
		return super.isEquivalentTo(node)
				&& this.isEquivalentTo((EclipseLinkObjectTypeConverter) node);
	}

	protected boolean isEquivalentTo(EclipseLinkObjectTypeConverter converter) {
		return ObjectTools.equals(this.fullyQualifiedObjectType, converter.getFullyQualifiedObjectType()) &&
				ObjectTools.equals(this.fullyQualifiedDataType, converter.getFullyQualifiedDataType()) &&
				ObjectTools.equals(this.defaultObjectValue, converter.getDefaultObjectValue()) &&
				this.conversionValuesAreEquivalentTo(converter);
	}

	protected boolean conversionValuesAreEquivalentTo(EclipseLinkObjectTypeConverter converter) {
		// get fixed lists of the conversion values
		ArrayList<JavaEclipseLinkConversionValue> conversionValues1 = ListTools.list(this.getConversionValues());
		ArrayList<? extends EclipseLinkConversionValue> conversionValues2 = ListTools.list(converter.getConversionValues());
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
	public void convertTo(OrmEclipseLinkConverterContainer ormConverterContainer) {
		ormConverterContainer.addObjectTypeConverter(this.getName()).convertFrom(this);
	}

	@Override
	public void delete() {
		this.getParent().removeObjectTypeConverter(this);
	}
}
