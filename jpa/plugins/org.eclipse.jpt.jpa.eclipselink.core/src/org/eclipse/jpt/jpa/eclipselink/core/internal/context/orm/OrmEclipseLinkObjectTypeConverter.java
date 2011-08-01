/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkObjectTypeConverter
	extends OrmEclipseLinkConverter<XmlObjectTypeConverter>
	implements EclipseLinkObjectTypeConverter
{
	private String dataType;

	private String objectType;


	protected final ContextListContainer<OrmEclipseLinkConversionValue, XmlConversionValue> conversionValueContainer;

	private String defaultObjectValue;


	public OrmEclipseLinkObjectTypeConverter(XmlContextNode parent, XmlObjectTypeConverter xmlConverter) {
		super(parent, xmlConverter);
		this.dataType = xmlConverter.getDataType();
		this.objectType = xmlConverter.getObjectType();
		this.conversionValueContainer = this.buildConversionValueContainer();
		this.defaultObjectValue = xmlConverter.getDefaultObjectValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setDataType_(this.xmlConverter.getDataType());
		this.setObjectType_(this.xmlConverter.getObjectType());
		this.syncConversionValues();
		this.setDefaultObjectValue_(this.xmlConverter.getDefaultObjectValue());
	}

	@Override
	public void update() {
		super.update();
		this.updateConversionValues();
	}


	// ********** data type **********

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.setDataType_(dataType);
		this.xmlConverter.setDataType(dataType);
	}

	protected void setDataType_(String dataType) {
		String old = this.dataType;
		this.dataType = dataType;
		this.firePropertyChanged(DATA_TYPE_PROPERTY, old, dataType);
	}

	protected boolean dataTypeIsFor(String typeName) {
		return this.typeIsFor(this.getDataTypeJavaResourceType(), typeName);
	}

	protected boolean dataTypeIsIn(IPackageFragment packageFragment) {
		return this.typeIsIn(this.getDataTypeJavaResourceType(), packageFragment);
	}

	protected JavaResourceAbstractType getDataTypeJavaResourceType() {
		return this.getMappingFileRoot().resolveJavaResourceType(this.getDataType());
	}


	// ********** object type **********

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.setObjectType_(objectType);
		this.xmlConverter.setObjectType(objectType);
	}

	protected void setObjectType_(String objectType) {
		String old = this.objectType;
		this.objectType = objectType;
		this.firePropertyChanged(OBJECT_TYPE_PROPERTY, old, objectType);
	}

	protected boolean objectTypeIsFor(String typeName) {
		return this.typeIsFor(this.getObjectTypeJavaResourceType(), typeName);
	}

	protected boolean objectTypeIsIn(IPackageFragment packageFragment) {
		return this.typeIsIn(this.getObjectTypeJavaResourceType(), packageFragment);
	}

	protected JavaResourceAbstractType getObjectTypeJavaResourceType() {
		return this.getMappingFileRoot().resolveJavaResourceType(this.getObjectType());
	}


	// ********** conversion values **********

	public ListIterable<OrmEclipseLinkConversionValue> getConversionValues() {
		return this.conversionValueContainer.getContextElements();
	}

	public int getConversionValuesSize() {
		return this.conversionValueContainer.getContextElementsSize();
	}

	public OrmEclipseLinkConversionValue addConversionValue() {
		return this.addConversionValue(this.getConversionValuesSize());
	}

	public OrmEclipseLinkConversionValue addConversionValue(int index) {
		XmlConversionValue xmlConversionValue = this.buildXmlConversionValue();
		OrmEclipseLinkConversionValue conversionValue = this.conversionValueContainer.addContextElement(index, xmlConversionValue);
		this.xmlConverter.getConversionValues().add(index, xmlConversionValue);
		return conversionValue;
	}

	protected XmlConversionValue buildXmlConversionValue() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlConversionValue();
	}

	public void removeConversionValue(EclipseLinkConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValueContainer.indexOfContextElement((OrmEclipseLinkConversionValue) conversionValue));
	}

	public void removeConversionValue(int index) {
		this.conversionValueContainer.removeContextElement(index);
		this.xmlConverter.getConversionValues().remove(index);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		this.conversionValueContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlConverter.getConversionValues().move(targetIndex, sourceIndex);
	}


	protected OrmEclipseLinkConversionValue buildConversionValue(XmlConversionValue xmlConversionValue) {
		return new OrmEclipseLinkConversionValue(this, xmlConversionValue);
	}

	protected void syncConversionValues() {
		this.conversionValueContainer.synchronizeWithResourceModel();
	}

	protected void updateConversionValues() {
		this.conversionValueContainer.update();
	}

	protected ListIterable<XmlConversionValue> getXmlConversionValues() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlConversionValue>(this.xmlConverter.getConversionValues());
	}

	protected ContextListContainer<OrmEclipseLinkConversionValue, XmlConversionValue> buildConversionValueContainer() {
		return new ConversionValueContainer();
	}

	/**
	 * conversion value container
	 */
	protected class ConversionValueContainer
			extends ContextListContainer<OrmEclipseLinkConversionValue, XmlConversionValue> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return CONVERSION_VALUES_LIST;
		}
		
		@Override
		protected OrmEclipseLinkConversionValue buildContextElement(XmlConversionValue resourceElement) {
			return OrmEclipseLinkObjectTypeConverter.this.buildConversionValue(resourceElement);
		}
		
		@Override
		protected ListIterable<XmlConversionValue> getResourceElements() {
			return OrmEclipseLinkObjectTypeConverter.this.getXmlConversionValues();
		}
		
		@Override
		protected XmlConversionValue getResourceElement(OrmEclipseLinkConversionValue contextElement) {
			return contextElement.getXmlConversionValue();
		}
	}


	// ********** data values **********

	public Iterable<String> getDataValues() {
		return new TransformationIterable<OrmEclipseLinkConversionValue, String>(this.getConversionValues()) {
			@Override
			protected String transform(OrmEclipseLinkConversionValue conversionValue) {
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
		this.setDefaultObjectValue_(value);
		this.xmlConverter.setDefaultObjectValue(value);
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

	protected boolean typeIsFor(JavaResourceAbstractType type, String typeName) {
		return (type != null) && type.getQualifiedName().equals(typeName);
	}

	protected boolean typeIsIn(JavaResourceAbstractType type, IPackageFragment packageFragment) {
		return (type != null) && type.isIn(packageFragment);
	}


	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				this.createRenameDataTypeEdits(originalType, newName),
				this.createRenameObjectTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createRenameDataTypeEdits(IType originalType, String newName) {
		return this.dataTypeIsFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameDataTypeEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameDataTypeEdit(IType originalType, String newName) {
		return this.xmlConverter.createRenameDataTypeEdit(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createRenameObjectTypeEdits(IType originalType, String newName) {
		return this.objectTypeIsFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameObjectTypeEdit(originalType, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameObjectTypeEdit(IType originalType, String newName) {
		return this.xmlConverter.createRenameObjectTypeEdit(originalType, newName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
				this.createMoveDataTypeEdits(originalType, newPackage),
				this.createMoveObjectTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createMoveDataTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.dataTypeIsFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameDataTypePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameDataTypePackageEdit(String newName) {
		return this.xmlConverter.createRenameDataTypePackageEdit(newName);
	}

	protected Iterable<ReplaceEdit> createMoveObjectTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.objectTypeIsFor(originalType.getFullyQualifiedName('.')) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameObjectTypePackageEdit(newPackage.getElementName())) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createRenameObjectTypePackageEdit(String newName) {
		return this.xmlConverter.createRenameObjectTypePackageEdit(newName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				this.createRenameDataTypePackageEdits(originalPackage, newName),
				this.createRenameObjectTypePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createRenameDataTypePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.dataTypeIsIn(originalPackage) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameDataTypePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected Iterable<ReplaceEdit> createRenameObjectTypePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.objectTypeIsIn(originalPackage) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameObjectTypePackageEdit(newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.checkForDuplicateDataValues(messages);
		for (OrmEclipseLinkConversionValue conversionValue : this.getConversionValues()) {
			conversionValue.validate(messages, reporter);
		}
	}

	protected void checkForDuplicateDataValues(List<IMessage> messages) {
		for (ArrayList<OrmEclipseLinkConversionValue> dups : this.mapConversionValuesByDataValue().values()) {
			if (dups.size() > 1) {
				for (OrmEclipseLinkConversionValue dup : dups) {
					messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE,
							new String[] {dup.getDataValue()},
							this,
							dup.getDataValueTextRange()
						)
					);
				}
			}
		}
	}

	protected HashMap<String, ArrayList<OrmEclipseLinkConversionValue>> mapConversionValuesByDataValue() {
		HashMap<String, ArrayList<OrmEclipseLinkConversionValue>> map = new HashMap<String, ArrayList<OrmEclipseLinkConversionValue>>(this.getConversionValuesSize());
		for (OrmEclipseLinkConversionValue conversionValue : this.getConversionValues()) {
			String dataValue = conversionValue.getDataValue();
			ArrayList<OrmEclipseLinkConversionValue> list = map.get(dataValue);
			if (list == null) {
				list = new ArrayList<OrmEclipseLinkConversionValue>();
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

		public XmlObjectTypeConverter getXmlConverter(XmlConverterHolder xmlConverterContainer) {
			return xmlConverterContainer.getObjectTypeConverter();
		}

		public OrmEclipseLinkConverter<? extends XmlNamedConverter> buildConverter(XmlNamedConverter xmlConverter, XmlContextNode parent) {
			return new OrmEclipseLinkObjectTypeConverter(parent, (XmlObjectTypeConverter) xmlConverter);
		}

		@Override
		protected XmlObjectTypeConverter buildXmlConverter() {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		}

		@Override
		protected void setXmlConverter(XmlConverterHolder xmlConverterContainer, XmlNamedConverter xmlConverter) {
			xmlConverterContainer.setObjectTypeConverter((XmlObjectTypeConverter) xmlConverter);
		}
	}
}
