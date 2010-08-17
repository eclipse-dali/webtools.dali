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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.ReplaceEdit;

public class OrmEclipseLinkTypeConverter extends OrmEclipseLinkConverter<XmlTypeConverter> 
	implements EclipseLinkTypeConverter
{	
	private String dataType;
	
	private String objectType;

	private JavaResourcePersistentType dataTypePersistentType;

	private JavaResourcePersistentType objectTypePersistentType;
	
	
	
	public OrmEclipseLinkTypeConverter(XmlContextNode parent) {
		super(parent);
	}
		
	public String getType() {
		return EclipseLinkConverter.TYPE_CONVERTER;
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
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(XmlTypeConverter xmlResource) {
		super.initialize(xmlResource);
		this.dataType = getResourceDataType();
		this.objectType = getResourceObjectType();
		this.dataTypePersistentType = this.getDataTypeJavaResourcePersistentType();
		this.objectTypePersistentType = this.getObjectTypeJavaResourcePersistentType();
	}
	
	@Override
	public void update() {
		super.update();
		setDataType_(getResourceDataType());
		setObjectType_(getResourceObjectType());
		updateDataTypePersistentType();
		updateObjectTypePersistentType();
	}
	
	protected String getResourceDataType() {
		return this.resourceConverter.getDataType();
	}
	
	protected String getResourceObjectType() {
		return this.resourceConverter.getObjectType();
	}

	protected JavaResourcePersistentType getDataTypeJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.getDataType());
	}

	protected JavaResourcePersistentType getObjectTypeJavaResourcePersistentType() {
		return this.getEntityMappings().resolveJavaResourcePersistentType(this.getObjectType());
	}
	
	protected void updateDataTypePersistentType() {
		this.dataTypePersistentType = this.getDataTypeJavaResourcePersistentType();
	}

	protected void updateObjectTypePersistentType() {
		this.objectTypePersistentType = this.getObjectTypeJavaResourcePersistentType();
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
}