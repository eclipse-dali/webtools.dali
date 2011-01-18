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

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.ReplaceEdit;

public class OrmEclipseLinkTypeConverter
	extends OrmEclipseLinkConverter<XmlTypeConverter>
	implements EclipseLinkTypeConverter
{
	private String dataType;

	private String objectType;


	public OrmEclipseLinkTypeConverter(XmlContextNode parent, XmlTypeConverter xmlConverter) {
		super(parent, xmlConverter);
		this.dataType = xmlConverter.getDataType();
		this.objectType = xmlConverter.getObjectType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setDataType_(this.xmlConverter.getDataType());
		this.setObjectType_(this.xmlConverter.getObjectType());
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
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.dataType);
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
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.objectType);
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


	// ********** misc **********

	public Class<EclipseLinkTypeConverter> getType() {
		return EclipseLinkTypeConverter.class;
	}

	protected boolean typeIsFor(JavaResourcePersistentType type, String typeName) {
		return (type != null) && type.getQualifiedName().equals(typeName);
	}

	protected boolean typeIsIn(JavaResourcePersistentType type, IPackageFragment packageFragment) {
		return (type != null) && type.isIn(packageFragment);
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

		public Class<EclipseLinkTypeConverter> getConverterType() {
			return EclipseLinkTypeConverter.class;
		}

		public XmlTypeConverter getXmlConverter(XmlConverterHolder xmlConverterContainer) {
			return xmlConverterContainer.getTypeConverter();
		}

		public OrmEclipseLinkTypeConverter buildConverter(XmlNamedConverter xmlConverter, XmlContextNode parent) {
			return new OrmEclipseLinkTypeConverter(parent, (XmlTypeConverter) xmlConverter);
		}

		@Override
		protected XmlTypeConverter buildXmlConverter() {
			return EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		}

		@Override
		public void setXmlConverter(XmlConverterHolder xmlConverterContainer, XmlNamedConverter xmlConverter) {
			xmlConverterContainer.setTypeConverter((XmlTypeConverter) xmlConverter);
		}
	}
}
