/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.text.edits.ReplaceEdit;

public class EclipseLinkOrmTypeConverter
	extends EclipseLinkOrmConverter<XmlTypeConverter>
	implements EclipseLinkTypeConverter
{
	private String dataType;
	private String fullyQualifiedDataType;

	private String objectType;
	private String fullyQualifiedObjectType;


	public EclipseLinkOrmTypeConverter(JpaContextModel parent, XmlTypeConverter xmlConverter) {
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

	@Override
	public void update() {
		super.update();
		this.setFullyQualifiedDataType(this.buildFullyQualifiedDataType());
		this.setFullyQualifiedObjectType(this.buildFullyQualifiedObjectType());
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
	public String getFullyQualifiedDataType() {
		return this.fullyQualifiedDataType;
	}

	protected void setFullyQualifiedDataType(String dataType) {
		String old = this.fullyQualifiedDataType;
		this.fullyQualifiedDataType = dataType;
		this.firePropertyChanged(FULLY_QUALIFIED_DATA_TYPE_PROPERTY, old, dataType);
	}

	protected String buildFullyQualifiedDataType() {
		return this.getMappingFileRoot().qualify(this.dataType);
	}

	protected boolean dataTypeIsFor(String typeName) {
		return this.typeIsFor(this.getDataTypeJavaResourceType(), typeName);
	}

	protected boolean dataTypeIsIn(IPackageFragment packageFragment) {
		return this.typeIsIn(this.getDataTypeJavaResourceType(), packageFragment);
	}

	protected JavaResourceAbstractType getDataTypeJavaResourceType() {
		if (this.fullyQualifiedDataType == null) {
			return null;
		}
		return this.getJpaProject().getJavaResourceType(this.fullyQualifiedDataType);
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

	public String getFullyQualifiedObjectType() {
		return this.fullyQualifiedObjectType;
	}

	protected void setFullyQualifiedObjectType(String objectType) {
		String old = this.fullyQualifiedObjectType;
		this.fullyQualifiedObjectType = objectType;
		this.firePropertyChanged(FULLY_QUALIFIED_OBJECT_TYPE_PROPERTY, old, objectType);
	}

	protected String buildFullyQualifiedObjectType() {
		return this.getMappingFileRoot().qualify(this.objectType);
	}

	protected boolean objectTypeIsFor(String typeName) {
		return this.typeIsFor(this.getObjectTypeJavaResourceType(), typeName);
	}

	protected boolean objectTypeIsIn(IPackageFragment packageFragment) {
		return this.typeIsIn(this.getObjectTypeJavaResourceType(), packageFragment);
	}

	protected JavaResourceAbstractType getObjectTypeJavaResourceType() {
		if (this.fullyQualifiedObjectType == null) {
			return null;
		}
		return this.getJpaProject().getJavaResourceType(this.fullyQualifiedObjectType);
	}


	// ********** refactoring **********

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				this.createRenameDataTypeEdits(originalType, newName),
				this.createRenameObjectTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createRenameDataTypeEdits(IType originalType, String newName) {
		return this.dataTypeIsFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameDataTypeEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenameDataTypeEdit(IType originalType, String newName) {
		return this.xmlConverter.createRenameDataTypeEdit(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createRenameObjectTypeEdits(IType originalType, String newName) {
		return this.objectTypeIsFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameObjectTypeEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenameObjectTypeEdit(IType originalType, String newName) {
		return this.xmlConverter.createRenameObjectTypeEdit(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				this.createMoveDataTypeEdits(originalType, newPackage),
				this.createMoveObjectTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createMoveDataTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.dataTypeIsFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameDataTypePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenameDataTypePackageEdit(String newName) {
		return this.xmlConverter.createRenameDataTypePackageEdit(newName);
	}

	protected Iterable<ReplaceEdit> createMoveObjectTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.objectTypeIsFor(originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameObjectTypePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenameObjectTypePackageEdit(String newName) {
		return this.xmlConverter.createRenameObjectTypePackageEdit(newName);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				this.createRenameDataTypePackageEdits(originalPackage, newName),
				this.createRenameObjectTypePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createRenameDataTypePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.dataTypeIsIn(originalPackage) ?
				IterableTools.singletonIterable(this.createRenameDataTypePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected Iterable<ReplaceEdit> createRenameObjectTypePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.objectTypeIsIn(originalPackage) ?
				IterableTools.singletonIterable(this.createRenameObjectTypePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}


	// ********** misc **********

	public Class<EclipseLinkTypeConverter> getType() {
		return EclipseLinkTypeConverter.class;
	}

	protected boolean typeIsFor(JavaResourceAbstractType type, String typeName) {
		return (type != null) && type.getTypeBinding().getQualifiedName().equals(typeName);
	}

	protected boolean typeIsIn(JavaResourceAbstractType type, IPackageFragment packageFragment) {
		return (type != null) && type.isIn(packageFragment);
	}

	// ********** validation *********

	@Override
	public boolean isEquivalentTo(JpaNamedContextModel node) {
		return super.isEquivalentTo(node)
				&& this.isEquivalentTo((EclipseLinkTypeConverter) node);
	}

	protected boolean isEquivalentTo(EclipseLinkTypeConverter converter) {
		return ObjectTools.equals(this.fullyQualifiedDataType, converter.getFullyQualifiedDataType()) &&
				ObjectTools.equals(this.fullyQualifiedObjectType, converter.getFullyQualifiedObjectType());
	}

	// ********** metadata conversion **********

	public void convertFrom(EclipseLinkJavaTypeConverter javaConverter) {
		super.convertFrom(javaConverter);
		this.setDataType(javaConverter.getFullyQualifiedDataType());
		this.setObjectType(javaConverter.getFullyQualifiedObjectType());
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.objectTypeTouches(pos)) {
			return this.getCandidateTypeNames();
		}
		if (this.dataTypeTouches(pos)) {
			return this.getCandidateTypeNames();
		}
		return null;
	}

	protected boolean objectTypeTouches(int pos) {
		return this.xmlConverter.objectTypeTouches(pos);
	}
	
	protected boolean dataTypeTouches(int pos) {
		return this.xmlConverter.dataTypeTouches(pos);
	}

	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateTypeNames() {
		return IterableTools.concatenate(
				JavaProjectTools.getJavaClassNames(getJavaProject()),
				MappingTools.getPrimaryBasicTypeNames(),
				MappingTools.getCollectionTypeNames(),
				IterableTools.sort(this.getPersistenceUnit().getEclipseLinkDynamicPersistentTypeNames())
				);
	}
}
