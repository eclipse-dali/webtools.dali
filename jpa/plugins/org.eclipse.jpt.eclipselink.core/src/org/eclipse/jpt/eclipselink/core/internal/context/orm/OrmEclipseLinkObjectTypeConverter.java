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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkObjectTypeConverter extends OrmEclipseLinkConverter<XmlObjectTypeConverter>
	implements EclipseLinkObjectTypeConverter
{	
	private String dataType;
	
	private String objectType;

	private JavaResourcePersistentType dataTypePersistentType;

	private JavaResourcePersistentType objectTypePersistentType;
	
	private final List<OrmEclipseLinkConversionValue> conversionValues;
	
	private String defaultObjectValue;
	
	
	public OrmEclipseLinkObjectTypeConverter(XmlContextNode parent) {
		super(parent);
		this.conversionValues = new ArrayList<OrmEclipseLinkConversionValue>();
	}
	
	public String getType() {
		return EclipseLinkConverter.OBJECT_TYPE_CONVERTER;
	}	
	
	// **************** data type **********************************************
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		getXmlResource().setDataType(newDataType);
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	protected void setDataType_(String newDataType) {
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	
	// **************** object type ********************************************
	
	public String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		getXmlResource().setObjectType(newObjectType);
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	protected void setObjectType_(String newObjectType) {
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	
	// **************** conversion values **************************************
	
	public ListIterator<OrmEclipseLinkConversionValue> conversionValues() {
		return new CloneListIterator<OrmEclipseLinkConversionValue>(this.conversionValues);
	}
	
	public int conversionValuesSize() {
		return this.conversionValues.size();
	}
	
	public OrmEclipseLinkConversionValue addConversionValue(int index) {
		XmlConversionValue resourceConversionValue = EclipseLinkOrmFactory.eINSTANCE.createXmlConversionValue();
		OrmEclipseLinkConversionValue contextConversionValue = buildConversionValue(resourceConversionValue);
		this.conversionValues.add(index, contextConversionValue);
		getXmlResource().getConversionValues().add(index, resourceConversionValue);
		fireItemAdded(CONVERSION_VALUES_LIST, index, contextConversionValue);
		return contextConversionValue;
	}
	
	public OrmEclipseLinkConversionValue addConversionValue() {
		return this.addConversionValue(this.conversionValues.size());
	}
	
	protected void addConversionValue(int index, OrmEclipseLinkConversionValue conversionValue) {
		addItemToList(index, conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}
	
	protected void addConversionValue(OrmEclipseLinkConversionValue conversionValue) {
		this.addConversionValue(this.conversionValues.size(), conversionValue);
	}
	
	public void removeConversionValue(int index) {
		OrmEclipseLinkConversionValue removedJoinColumn = this.conversionValues.remove(index);
		getXmlResource().getConversionValues().remove(index);
		fireItemRemoved(CONVERSION_VALUES_LIST, index, removedJoinColumn);
	}
	
	public void removeConversionValue(EclipseLinkConversionValue conversionValue) {
		this.removeConversionValue(this.conversionValues.indexOf(conversionValue));
	}
	
	protected void removeConversionValue_(EclipseLinkConversionValue conversionValue) {
		removeItemFromList(conversionValue, this.conversionValues, CONVERSION_VALUES_LIST);
	}
	
	public void moveConversionValue(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.conversionValues, targetIndex, sourceIndex);
		getXmlResource().getConversionValues().move(targetIndex, sourceIndex);
		fireItemMoved(CONVERSION_VALUES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<String> dataValues() {
		return new TransformationListIterator<OrmEclipseLinkConversionValue, String>(conversionValues()) {
			@Override
			protected String transform(OrmEclipseLinkConversionValue next) {
				return next.getDataValue();
			}
		};
	}
	
	
	// **************** default object value ***********************************
	
	public String getDefaultObjectValue() {
		return this.defaultObjectValue;
	}
	
	public void setDefaultObjectValue(String newDefaultObjectValue) {
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		getXmlResource().setDefaultObjectValue(newDefaultObjectValue);
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	protected void setDefaultObjectValue_(String newDefaultObjectValue) {
		String oldDefaultObjectValue = this.defaultObjectValue;
		this.defaultObjectValue = newDefaultObjectValue;
		firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, oldDefaultObjectValue, newDefaultObjectValue);
	}
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(XmlObjectTypeConverter xmlResource) {
		super.initialize(xmlResource);
		this.dataType = this.getResourceDataType();
		this.objectType = this.getResourceObjectType();
		this.dataTypePersistentType = this.getDataTypeJavaResourcePersistentType();
		this.objectTypePersistentType = this.getObjectTypeJavaResourcePersistentType();
		this.defaultObjectValue = this.getResourceDefaultObjectValue();
		this.initializeConversionValues();
	}
	
	protected void initializeConversionValues() {	
		for (XmlConversionValue resourceConversionValue : getXmlResource().getConversionValues()) {
			this.conversionValues.add(buildConversionValue(resourceConversionValue));
		}
	}
	
	@Override
	public void update() {
		super.update();
		setDataType_(getResourceDataType());
		setObjectType_(getResourceObjectType());
		updateDataTypePersistentType();
		updateObjectTypePersistentType();
		setDefaultObjectValue_(getResourceDefaultObjectValue());
		updateConversionValues();
	}
	
	protected void updateDataTypePersistentType() {
		this.dataTypePersistentType = this.getDataTypeJavaResourcePersistentType();
	}

	protected void updateObjectTypePersistentType() {
		this.objectTypePersistentType = this.getObjectTypeJavaResourcePersistentType();
	}

	protected void updateConversionValues( ) {
		// make a copy of the XML conversion values (to prevent ConcurrentModificationException)
		Iterator<XmlConversionValue> xmlConversionValues = new CloneIterator<XmlConversionValue>(this.getXmlResource().getConversionValues());
		
		for (Iterator<OrmEclipseLinkConversionValue> contextConversionValues = this.conversionValues(); contextConversionValues.hasNext(); ) {
			OrmEclipseLinkConversionValue contextConversionValue = contextConversionValues.next();
			if (xmlConversionValues.hasNext()) {
				contextConversionValue.update(xmlConversionValues.next());
			}
			else {
				removeConversionValue_(contextConversionValue);
			}
		}
		
		while (xmlConversionValues.hasNext()) {
			addConversionValue(buildConversionValue(xmlConversionValues.next()));
		}
	}
	
	protected OrmEclipseLinkConversionValue buildConversionValue(XmlConversionValue resourceConversionValue) {
		OrmEclipseLinkConversionValue conversionValue = new OrmEclipseLinkConversionValue(this);
		conversionValue.initialize(resourceConversionValue);
		return conversionValue;
	}
	
	protected String getResourceDataType() {
		return this.resourceConverter.getDataType();
	}

	protected String getResourceObjectType() {
		return this.resourceConverter.getObjectType();
	}

	protected String getResourceDefaultObjectValue() {
		return this.resourceConverter.getDefaultObjectValue();
	}
	

	protected JavaResourcePersistentType getDataTypeJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.getDataType());
	}

	protected JavaResourcePersistentType getObjectTypeJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.getObjectType());
	}

	protected boolean dataTypeIsFor(String typeName) {
		return this.isFor(this.dataTypePersistentType, typeName);
	}

	protected boolean objectTypeIsFor(String typeName) {
		return this.isFor(this.objectTypePersistentType, typeName);
	}

	protected boolean isFor(JavaResourcePersistentType persistentType, String typeName) {
		if (persistentType != null && persistentType.getQualifiedName().equals(typeName)) {
			return true;
		}
		return false;
	}

	protected boolean dataTypeIsIn(IPackageFragment packageFragment) {
		return this.isIn(this.dataTypePersistentType, packageFragment);
	}

	protected boolean objectTypeIsIn(IPackageFragment packageFragment) {
		return this.isIn(this.objectTypePersistentType, packageFragment);
	}

	protected boolean isIn(JavaResourcePersistentType persistentType, IPackageFragment packageFragment) {
		if (persistentType != null) {
			return persistentType.isIn(packageFragment);
		}
		return false;
	}


	//************************* refactoring ************************

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			this.createRenameDataTypeEdits(originalType, newName),
			this.createRenameObjectTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createRenameDataTypeEdits(IType originalType, String newName) {
		if (this.dataTypeIsFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenameDataTypeEdit(originalType, newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenameDataTypeEdit(IType originalType, String newName) {
		return getXmlResource().createRenameDataTypeEdit(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createRenameObjectTypeEdits(IType originalType, String newName) {
		if (this.objectTypeIsFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenameObjectTypeEdit(originalType, newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenameObjectTypeEdit(IType originalType, String newName) {
		return getXmlResource().createRenameObjectTypeEdit(originalType, newName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			this.createMoveDataTypeEdits(originalType, newPackage),
			this.createMoveObjectTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createMoveDataTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.dataTypeIsFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenameDataTypePackageEdit(newPackage.getElementName()));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenameDataTypePackageEdit(String newName) {
		return getXmlResource().createRenameDataTypePackageEdit(newName);
	}

	protected Iterable<ReplaceEdit> createMoveObjectTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.objectTypeIsFor(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenameObjectTypePackageEdit(newPackage.getElementName()));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenameObjectTypePackageEdit(String newName) {
		return getXmlResource().createRenameObjectTypePackageEdit(newName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			this.createRenameDataTypePackageEdits(originalPackage, newName),
			this.createRenameObjectTypePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createRenameDataTypePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.dataTypeIsIn(originalPackage)) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenameDataTypePackageEdit(newName));
		}
		return EmptyIterable.instance();
	}

	protected Iterable<ReplaceEdit> createRenameObjectTypePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.objectTypeIsIn(originalPackage)) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenameObjectTypePackageEdit(newName));
		}
		return EmptyIterable.instance();
	}

	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (Iterator<OrmEclipseLinkConversionValue> stream = conversionValues(); stream.hasNext();) {
			stream.next().validate(messages, reporter);
		}
	}
}