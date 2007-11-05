/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;



public interface IDiscriminatorColumn extends INamedColumn
{
	String DEFAULT_NAME = "DTYPE";

	DiscriminatorType getDiscriminatorType();

	DiscriminatorType getDefaultDiscriminatorType();
		String DEFAULT_DISCRIMINATOR_TYPE_PROPERTY = "defaultDiscriminatorTypeProperty";
		DiscriminatorType DEFAULT_DISCRIMINATOR_TYPE = DiscriminatorType.STRING;
		
	DiscriminatorType getSpecifiedDiscriminatorType();
	void setSpecifiedDiscriminatorType(DiscriminatorType newSpecifiedDiscriminatorType);
		String SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY = "specifiedDiscriminatorTypeProperty";


	int getLength();

	int getDefaultLength();
		int DEFAULT_LENGTH = 31;
		String DEFAULT_LENGTH_PROPERTY = "defaultLengthProperty";

	int getSpecifiedLength();
	void setSpecifiedLength(int value);
		String SPECIFIED_LENGTH_PROPERTY = "spcifiedLengthProperty";

//
//	class Owner implements INamedColumn.Owner
//	{
//		private IEntity entity;
//
//		public Owner(IEntity entity) {
//			super();
//			this.entity = entity;
//		}
//
//		public Table dbTable(String tableName) {
//			return this.entity.dbTable(tableName);
//		}
//
//		public ITextRange validationTextRange() {
//			return this.entity.getDiscriminatorColumn().validationTextRange();
//		}
//
//		public ITypeMapping getTypeMapping() {
//			return this.entity;
//		}
//	}
}
