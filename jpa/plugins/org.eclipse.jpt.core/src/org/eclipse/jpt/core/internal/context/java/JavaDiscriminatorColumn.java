/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentResource;

public class JavaDiscriminatorColumn extends JavaNamedColumn
	implements IJavaDiscriminatorColumn
{

	protected DiscriminatorType specifiedDiscriminatorType;


	protected int specifiedLength;

	public JavaDiscriminatorColumn(IJavaEntity parent) {
		super(parent);
	}

	@Override
	public void initializeFromResource(JavaPersistentResource persistentResource) {
		super.initializeFromResource(persistentResource);
		DiscriminatorColumn discriminatorColumn = columnResource();
		this.specifiedDiscriminatorType = this.discriminatorType(discriminatorColumn);
		this.specifiedLength = discriminatorColumn.getLength();		
	}
	
	protected IJavaEntity javaEntity() {
		return (IJavaEntity) super.parent();
	}

	@Override
	protected String annotationName() {
		return DiscriminatorColumn.ANNOTATION_NAME;
	}

	
	public DiscriminatorType getDiscriminatorType() {
		return (this.getSpecifiedDiscriminatorType() == null) ? this.getDefaultDiscriminatorType() : this.getSpecifiedDiscriminatorType();
	}

	public DiscriminatorType getDefaultDiscriminatorType() {
		return IDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
	}
		
	public DiscriminatorType getSpecifiedDiscriminatorType() {
		return this.specifiedDiscriminatorType;
	}
	
	public void setSpecifiedDiscriminatorType(DiscriminatorType newSpecifiedDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = newSpecifiedDiscriminatorType;
		columnResource().setDiscriminatorType(DiscriminatorType.toJavaResourceModel(newSpecifiedDiscriminatorType));
		firePropertyChanged(IDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
	}
		
	public int getLength() {
		return (this.getSpecifiedLength() == DiscriminatorColumn.DEFAULT_LENGTH) ? this.getDefaultLength() : this.getSpecifiedLength();
	}

	public int getDefaultLength() {
		return IDiscriminatorColumn.DEFAULT_LENGTH;
	}

	public int getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(int newSpecifiedLength) {
		int oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		columnResource().setLength(newSpecifiedLength);
		firePropertyChanged(IDiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	@Override
	protected DiscriminatorColumn columnResource() {
		return (DiscriminatorColumn) super.columnResource();
	}

	@Override
	protected String tableName() {
		return javaEntity().getTableName();
	}
	
	
	// ********** java annotations -> persistence model **********
	@Override
	public void update(JavaPersistentResource persistentResource) {
		super.update(persistentResource);
		DiscriminatorColumn discriminatorColumn = columnResource();
		this.setSpecifiedDiscriminatorType(this.discriminatorType(discriminatorColumn));
		this.setSpecifiedLength(discriminatorColumn.getLength());
	}
	
	protected DiscriminatorType discriminatorType(DiscriminatorColumn discriminatorColumn) {
		return DiscriminatorType.fromJavaResourceModel(discriminatorColumn.getDiscriminatorType());
	}

	@Override
	protected String defaultName() {
		return IDiscriminatorColumn.DEFAULT_NAME;
	}

}
