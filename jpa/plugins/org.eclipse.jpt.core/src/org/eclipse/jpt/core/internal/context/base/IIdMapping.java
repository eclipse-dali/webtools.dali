/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

public interface IIdMapping extends IAttributeMapping, IColumnMapping
{
//	IGeneratedValue getGeneratedValue();
//
//	void setGeneratedValue(IGeneratedValue value);

	TemporalType getTemporal();
	void setTemporal(TemporalType value);
		String TEMPORAL_PROPERTY = "temporalProperty";
	
	ITableGenerator getTableGenerator();
	ITableGenerator addTableGenerator();
	void removeTableGenerator();
		String TABLE_GENERATOR_PROPERTY = "tableGeneratorProperty";

	ISequenceGenerator getSequenceGenerator();
	ISequenceGenerator addSequenceGenerator();
	void removeSequenceGenerator();
		String SEQUENCE_GENERATOR_PROPERTY = "sequenceGeneratorProperty";

//	IGeneratedValue createGeneratedValue();
}
