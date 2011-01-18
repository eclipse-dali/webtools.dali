/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmTemporalConverter;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.text.edits.ReplaceEdit;

public class GenericOrmTemporalConverter
	extends AbstractOrmConverter
	implements OrmTemporalConverter
{
	protected TemporalType temporalType;


	public GenericOrmTemporalConverter(OrmAttributeMapping parent) {
		super(parent);
		this.temporalType = this.buildTemporalType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setTemporalType_(this.buildTemporalType());
	}


	// ********** temporal type **********

	public TemporalType getTemporalType() {
		return this.temporalType;
	}

	public void setTemporalType(TemporalType temporalType) {
		this.setTemporalType_(temporalType);
		this.setXmlTemporal(temporalType);
	}

	protected void setTemporalType_(TemporalType temporalType) {
		TemporalType old = this.temporalType;
		this.temporalType = temporalType;
		this.firePropertyChanged(TEMPORAL_TYPE_PROPERTY, old, temporalType);
	}

	protected void setXmlTemporal(TemporalType temporalType) {
		this.getXmlConvertibleMapping().setTemporal(TemporalType.toOrmResourceModel(temporalType));
	}

	protected TemporalType buildTemporalType() {
		return TemporalType.fromOrmResourceModel(this.getXmlConvertibleMapping().getTemporal());
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return TemporalConverter.class;
	}

	public void initialize() {
		// start with DATE(?)
		this.temporalType = TemporalType.DATE;
		this.setXmlTemporal(this.temporalType);
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlConvertibleMapping().getTemporalTextRange();
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
}
