/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.EnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEnumeratedConverter;
import org.eclipse.text.edits.ReplaceEdit;

public class GenericOrmEnumeratedConverter
	extends AbstractOrmConverter
	implements OrmEnumeratedConverter
{
	protected EnumType specifiedEnumType;
	protected EnumType defaultEnumType;


	public GenericOrmEnumeratedConverter(OrmAttributeMapping parent) {
		super(parent);
		this.specifiedEnumType = this.buildSpecifiedEnumType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedEnumType_(this.buildSpecifiedEnumType());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultEnumType(this.buildDefaultEnumType());
	}


	// ********** enum type **********

	public EnumType getEnumType() {
		return (this.specifiedEnumType != null) ? this.specifiedEnumType : this.getDefaultEnumType();
	}

	public EnumType getSpecifiedEnumType() {
		return this.specifiedEnumType;
	}

	public void setSpecifiedEnumType(EnumType enumType) {
		this.setSpecifiedEnumType_(enumType);
		this.setXmlEnumerated(enumType);
	}

	protected void setSpecifiedEnumType_(EnumType enumType) {
		EnumType old = this.specifiedEnumType;
		this.specifiedEnumType = enumType;
		this.firePropertyChanged(SPECIFIED_ENUM_TYPE_PROPERTY, old, enumType);
	}

	protected void setXmlEnumerated(EnumType enumType) {
		this.getXmlConvertibleMapping().setEnumerated(EnumType.toOrmResourceModel(enumType));
	}

	protected EnumType buildSpecifiedEnumType() {
		return EnumType.fromOrmResourceModel(this.getXmlConvertibleMapping().getEnumerated());
	}

	public EnumType getDefaultEnumType() {
		return this.defaultEnumType;
	}

	protected void setDefaultEnumType(EnumType enumType) {
		EnumType old = this.defaultEnumType;
		this.defaultEnumType = enumType;
		this.firePropertyChanged(DEFAULT_ENUM_TYPE_PROPERTY, old, enumType);
	}

	/**
	 * There is no default enum type in XML.
	 * If you specify the enumerated element, you must
	 * specify either {@link EnumType#ORDINAL} or
	 * {@link EnumType#STRING}.
	 */
	protected EnumType buildDefaultEnumType() {
		return null;
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return EnumeratedConverter.class;
	}

	public void initialize() {
		this.specifiedEnumType = DEFAULT_ENUM_TYPE;
		this.setXmlEnumerated(this.specifiedEnumType);
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return EmptyIterable.instance();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlConvertibleMapping().getEnumeratedTextRange();
	}
}
