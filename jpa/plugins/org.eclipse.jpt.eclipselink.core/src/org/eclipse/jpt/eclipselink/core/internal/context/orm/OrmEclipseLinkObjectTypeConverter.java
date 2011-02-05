/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkObjectTypeConverter
	extends OrmEclipseLinkConverter<XmlObjectTypeConverter>
	implements EclipseLinkObjectTypeConverter
{
	private String dataType;

	private String objectType;

	private final Vector<OrmEclipseLinkConversionValue> conversionValues = new Vector<OrmEclipseLinkConversionValue>();
	private final ConversionValueContainerAdapter conversionValueContainerAdapter = new ConversionValueContainerAdapter();

	private String defaultObjectValue;


	public OrmEclipseLinkObjectTypeConverter(XmlContextNode parent, XmlObjectTypeConverter xmlConverter) {
		super(parent, xmlConverter);
		this.dataType = xmlConverter.getDataType();
		this.objectType = xmlConverter.getObjectType();
		this.initializeConversionValues();
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
		this.updateNodes(this.getConversionValues());
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
		return this.typeIsFor(this.getDataTypeJavaResourcePersistentType(), typeName);
	}

	protected boolean dataTypeIsIn(IPackageFragment packageFragment) {
		return this.typeIsIn(this.getDataTypeJavaResourcePersistentType(), packageFragment);
	}

	protected JavaResourcePersistentType getDataTypeJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.getDataType());
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
		return this.typeIsFor(this.getObjectTypeJavaResourcePersistentType(), typeName);
	}

	protected boolean objectTypeIsIn(IPackageFragment packageFragment) {
		return this.typeIsIn(this.getObjectTypeJavaResourcePersistentType(), packageFragment);
	}

	protected JavaResourcePersistentType getObjectTypeJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.getObjectType());
	}


	// ********** conversion values **********

	@SuppressWarnings("unchecked")
	public ListIterator<OrmEclipseLinkConversionValue> conversionValues() {
		return this.getConversionValues().iterator();
	}

	public ListIterable<OrmEclipseLinkConversionValue> getConversionValues() {
		return new LiveCloneListIterable<OrmEclipseLinkConversionValue>(this.conversionValues);
	}

	public int conversionValuesSize() {
		return this.conversionValues.size();
	}

	public OrmEclipseLinkConversionValue addConversionValue() {
		return this.addConversionValue(this.conversionValues.size());
	}

	public OrmEclipseLinkConversionValue addConversionValue(int index) {
		XmlConversionValue xmlConversionValue = this.buildXmlConversionValue();
		OrmEclipseLinkConversionValue conversionValue = this.addConversionValue_(index, xmlConversionValue);
		this.xmlConverter.getConversionValues().add(index, xmlConversionValue);
		return conversionValue;
	}

	protected XmlConversionValue buildXmlConversionValue() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlConversionValue();
	}

	public void removeConversionValue(EclipseLinkConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValues.indexOf(conversionValue));
	}

	public void removeConversionValue(int index) {
		this.removeConversionValue_(index);
		this.xmlConverter.getConversionValues().remove(index);
	}

	protected void removeConversionValue_(int index) {
		this.removeItemFromList(index, this.conversionValues, CONVERSION_VALUES_LIST);
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.conversionValues, CONVERSION_VALUES_LIST);
		this.xmlConverter.getConversionValues().move(targetIndex, sourceIndex);
	}

	protected void initializeConversionValues() {
		for (XmlConversionValue xmlConversionValue : this.getXmlConversionValues()) {
			this.conversionValues.add(this.buildConversionValue(xmlConversionValue));
		}
	}

	protected OrmEclipseLinkConversionValue buildConversionValue(XmlConversionValue xmlConversionValue) {
		return new OrmEclipseLinkConversionValue(this, xmlConversionValue);
	}

	protected void syncConversionValues() {
		ContextContainerTools.synchronizeWithResourceModel(this.conversionValueContainerAdapter);
	}

	protected Iterable<XmlConversionValue> getXmlConversionValues() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlConversionValue>(this.xmlConverter.getConversionValues());
	}

	protected void moveConversionValue_(int index, OrmEclipseLinkConversionValue conversionValue) {
		this.moveItemInList(index, conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}

	protected OrmEclipseLinkConversionValue addConversionValue_(int index, XmlConversionValue xmlConversionValue) {
		OrmEclipseLinkConversionValue conversionValue = this.buildConversionValue(xmlConversionValue);
		this.addItemToList(index, conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
		return conversionValue;
	}

	protected void removeConversionValue_(OrmEclipseLinkConversionValue conversionValue) {
		this.removeConversionValue_(this.conversionValues.indexOf(conversionValue));
	}

	/**
	 * conversion value container adapter
	 */
	protected class ConversionValueContainerAdapter
		implements ContextContainerTools.Adapter<OrmEclipseLinkConversionValue, XmlConversionValue>
	{
		public Iterable<OrmEclipseLinkConversionValue> getContextElements() {
			return OrmEclipseLinkObjectTypeConverter.this.getConversionValues();
		}
		public Iterable<XmlConversionValue> getResourceElements() {
			return OrmEclipseLinkObjectTypeConverter.this.getXmlConversionValues();
		}
		public XmlConversionValue getResourceElement(OrmEclipseLinkConversionValue contextElement) {
			return contextElement.getXmlConversionValue();
		}
		public void moveContextElement(int index, OrmEclipseLinkConversionValue element) {
			OrmEclipseLinkObjectTypeConverter.this.moveConversionValue_(index, element);
		}
		public void addContextElement(int index, XmlConversionValue resourceElement) {
			OrmEclipseLinkObjectTypeConverter.this.addConversionValue_(index, resourceElement);
		}
		public void removeContextElement(OrmEclipseLinkConversionValue element) {
			OrmEclipseLinkObjectTypeConverter.this.removeConversionValue_(element);
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
		return this.conversionValuesSize();
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

	protected boolean typeIsFor(JavaResourcePersistentType persistentType, String typeName) {
		return (persistentType != null) && persistentType.getQualifiedName().equals(typeName);
	}

	protected boolean typeIsIn(JavaResourcePersistentType persistentType, IPackageFragment packageFragment) {
		return (persistentType != null) && persistentType.isIn(packageFragment);
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
		for (OrmEclipseLinkConversionValue conversionValue : this.getConversionValues()) {
			conversionValue.validate(messages, reporter);
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
